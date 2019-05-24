package attributes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.Statement;

public class transform {
	static JSONParser parser = new JSONParser();
	static String callProcedure = null;
	static JSONObject jobj = null;
	static Connection conn = null;
	static Statement stmt;

	public static void dbconfig() throws IllegalAccessException, InstantiationException, SQLException, ParseException,
			UnsupportedEncodingException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://DESKTOP-8T43HRL:3306/wsimcpsn_shopnowee";
			conn = DriverManager.getConnection(url, "root", "Alfred_21");
			System.out.println("CONNECTION DONE");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());// from config file
		}

		stmt = (Statement) conn.createStatement();
	}

	@SuppressWarnings({ "rawtypes" })
	public static String doAttrStore(String str) throws ParseException {

		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");// size~color,4~black
		String[] data = x.split("_");
		String vendorId = data[0];
		String productId = data[1];
		// dbconfig();
		String total_att_name = "";
		String total_att_val = "";
		String toGroovy;
		// System.out.println(String.valueOf(jobj.get("specialPrice")));
		// String price = String.valueOf(jobj.get("specialPrice"));

		Set keySet = jobj.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String att_val;

			String att_name = (String) iterator.next();
			if (!att_name.equals("product_id") && !att_name.equals("productUrl") && !att_name.equals("inStock")
					&& !att_name.equals("specialPrice") && !att_name.equals("active_id")) {
				if (jobj.get(att_name).toString().length() < 1) {
					continue;
				} else {
					total_att_name = total_att_name.concat(att_name).concat("~");
					if (att_name.equals("color")) {
						String regEx = "[\\[|\\]|(|//|&|.|$|,|\\d|\\)|-]|(AND)|(and)|(And)|(with)|(With)";
						att_val = (String) jobj.get(att_name);
						String color = att_val;
						color = color.replaceAll(regEx, "&");
						if (color.indexOf("&") == 0) {
							color = color.replaceFirst(regEx, "");
						}
						while (color.contains("&&")) {

							color = color.replaceAll("&&", "&");

						}

						// att_val = att_val.replaceAll(regEx, "&").replaceAll("&&", "&");

						total_att_val = total_att_val.concat(color.trim()).concat("~");
					} else {
						att_val = (String) jobj.get(att_name);// Attribute Value
						total_att_val = total_att_val.concat(att_val).concat("~");

					}
				}
				// String productId = (String) jobj.get("product_id");// ProductId

				/*
				 * String regEx = "[\\[|\\]|(|/|&|.|$|\\s|,|\\d|)]"; String color =
				 * "(.BlaCk.REd/GrEen2Red//YeLlow)"; color.replaceAll(regEx,
				 * "&").replaceAll("&&", "&").replaceFirst("&", ""); System.out.println(color);
				 */
			}

		}
		if (total_att_name != "") {
			callProcedure = "call spInsUpdateProdAttr(\"" + total_att_name.substring(0, total_att_name.length() - 1)
					+ "\",\"" + total_att_val.substring(0, total_att_val.length() - 1) + "\",\"" + productId + "\",\""+ jobj.get("active_id") + "\",\""
					+ vendorId + "\",\"" + jobj.get("productUrl") + "\",\"" + jobj.get("inStock") + "\",\""
					+ String.valueOf(jobj.get("specialPrice")) + "\",@insProductID);";
			toGroovy = callProcedure.replace("[", "").replace("]", "");
		} else {
			toGroovy = "NO_ATTRIBUTES";
		}

		return toGroovy;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JSONArray doAttrStore1(String str) throws ParseException {
		JSONArray jArray = new JSONArray();
		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");// size~color,4~black
		String[] data = x.split("_");
		// String vendorId = data[0];
		String productId = data[1];
		// dbconfig();
		String total_att_name = "";
		String total_att_val = "";
		// String toGroovy;
		Set keySet = jobj.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String att_val;

			String att_name = (String) iterator.next();
			if (!att_name.equals("product_id")) {
				if (jobj.get(att_name).toString().length() < 1) {
					continue;
				} else {
					total_att_name = total_att_name.concat(att_name).concat("~");
					att_val = (String) jobj.get(att_name);// Attribute Value
					total_att_val = total_att_val.concat(att_val).concat("~");
				}
				// String productId = (String) jobj.get("product_id");// ProductId
				callProcedure = "call spInsUpdateProdAttr(\"" + att_name + "\",\"" + att_val + "\",\"" + productId
						+ "\",@insProductID);";
				jArray.add(callProcedure);
			}

		}

		return jArray;
	}

	@SuppressWarnings("unchecked")
	public static String doCatStore(String str) throws ParseException {
		jobj = (JSONObject) parser.parse(str);
		String parent = null;
		String child = null;
		String cMap = null;
		String vendorId = null;
		String productId = null;
		int index1 = 0;
		Integer[] a1 = new Integer[30];
		// String s = "Women's Topshop Slit Front Faux Leather Mini skirt, Size X-Small
		// - Beige";
		String cPath = (String) jobj.get("categoryPath");
		String s = (String) jobj.get("nameTerms");
		if (s != null) {

			String child_product;
			if (s.contains(", Size") || s.contains(", Created")) {
				String s1 = null;
				if (s.contains(", Size")) {
					s1 = s.substring(0, s.indexOf(", Size"));
				} else {
					s1 = s.substring(0, s.indexOf(", Created"));
				}
				for (int i = 0; i < s1.length(); i++) {
					if (s1.charAt(i) == ' ') {
						a1[index1] = i;
						index1++;

					}
				}
				child_product = s1.substring(a1[index1 - 2]);
				// System.out.println(s1.substring(a1[index1-2]).trim());
			} else {
				System.out.println("ELSE");
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						a1[index1] = i;
						index1++;

					}
				}
				child_product = s.substring(a1[index1 - 2]);
				// System.out.println(s.substring(a1[index1-2]));
			}
			cPath = cPath + " >" + child_product;
		}

		String x = (String) jobj.get("product_id");
		if (x.contains("_")) {
			String[] data = x.split("_");
			vendorId = data[0];
			productId = data[1];
		} else {
			productId = x;
			vendorId = (String) jobj.get("vendor_id");
		}
		// String cPath = (String) jobj.get("categoryPath");

		int i = 0;
		int index = 0;
		int find = 0;
		Integer[] a = new Integer[10];
		for (i = 0; i < cPath.length(); i++) {
			if (cPath.charAt(i) == '>') {
				find++;
				a[index] = i;
				// System.out.println(a[index]);
				index++;
			}
		}

		if (cPath.contains("Men")) {
			parent = "Men";
		}
		if (cPath.contains("Women")) {
			parent = "Women";
		}
		if (cPath.contains("Boys")) {
			parent = "Boys";
		}
		if (cPath.contains("Girls")) {
			parent = "Girls";
		}
		child = cPath.substring(cPath.lastIndexOf(">") + 1, cPath.length());

		if (parent == null) {
			parent = cPath;
		}
		if (child == null) {
			child = cPath;
		}
		if (find >= 3) {
			if (cPath.contains("Infant") || cPath.contains("Kids")) {
				cMap = cPath.substring((a[index - 2]) + 1, a[index - 1]);
				// System.out.println(cMap);
			} else {
				cMap = cPath.substring(a[1] + 1, a[2]);
				// System.out.println(cMap);
			}
		} else {
			cMap = child;
		}

		String description = null;
		JSONArray highlights = (JSONArray) jobj.get("highlights");
		// JSONArray productFamily = (JSONArray) jobj.get("productFamily");
		// String pf = productFamily.toString();

		if (highlights != null) {
			description = (String) jobj.get("description") + " " + highlights.toString().replaceAll("\"", "");
		} else {
			description = (String) jobj.get("description");
		}

		// System.out.println(highlights.toString().replaceAll("\"", ""));

		// System.out.println(description);
		// System.out.println(cPath.contains("Women"));
		JSONObject demo = new JSONObject();
		demo.put("name", jobj.get("name"));
		//System.out.println(demo);
		callProcedure = "call productInsUpdate(\"" + parent + "\", \"" + cMap.trim() + "\",\"" + child.trim() + "\",\""
				+ jobj.get("name") + "\",\"" + description + "\",\"" + jobj.get("regular_price") + "\",\""
				+ jobj.get("sale_price") + "\",\"" + jobj.get("stock") + "\",\"" + jobj.get("image") + "\",\""
				+ jobj.get("product_url") + "\",\"" + productId + "\",\"" + vendorId + "\",\"" + 0 + "\",\""
				+ jobj.get("similar_product_id") + "\",\"" + jobj.get("active_id")
				+ "\",@insProductID);";

		return callProcedure.replace("[", "").replace("]", "");
	}

	public static String doKeywordsStore(String str) throws ParseException {

		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");
		// String[] data = x.split("_");
		String productId = x.substring(x.lastIndexOf("_") + 1);

		// String vendorId = data[0];
		/*
		 * if (data.length == 3) { String productId = data[2]; callProcedure =
		 * "call keywordsProcedure(\"" + productId + "',\"" + jobj.get("keywords") +
		 * "\");"; } else { String productId = data[1]; callProcedure =
		 * "call keywordsProcedure(\"" + productId + "\",\"" + jobj.get("keywords") +
		 * "\");"; }
		 */
		callProcedure = "call keywordsProcedure(\"" + productId + "\",\"" + jobj.get("keywords") + "\");";
		return callProcedure.replaceAll("\"", "").replace("[", "").replace("]", "");

	}

	public static String doImageStore(String str) throws ParseException {
		jobj = (JSONObject) parser.parse(str);
		callProcedure = "call ImageProcedure(\"" + jobj.get("product_id") + "\",\"" + jobj.get("200x200") + "\",\""
				+ jobj.get("400x400") + "\",\"" + jobj.get("800x800") + "\");";
		return callProcedure;

	}

	public static String doImageKeywordsStore(String str) throws ParseException {
		jobj = (JSONObject) parser.parse(str);
		callProcedure = "call image_keywordsProcedure(\"" + jobj.get("product_id") + "\",\"" + jobj.get("keywords")
				+ "\");";
		return callProcedure;
	}

	public static String doDealStore(String str) throws ParseException, IOException {

		jobj = (JSONObject) parser.parse(str);
		String valid = "NULL";
		String parent = null;
		String cPath = null;
		String child = null;
		int index = 0;
		Integer[] a = new Integer[30];
		for (int i = 0; i < ((String) jobj.get("product_url")).length(); i++) {
			if (jobj.get("product_url").toString().charAt(i) == '/') {
				a[index] = i;
				index++;
			}
		}

		String start_time = jobj.get("start_date").toString();
		String end_time = jobj.get("end_date").toString();
		// long start_timeStamp = Long.parseLong((start_time.substring(0,
		// start_time.length() - 3)));
		// long end_timeStamp = Long.parseLong(end_time.substring(0, end_time.length() -
		// 3));

		// Date sdate = new Date(start_timeStamp * 1000L);
		// Date edate = new Date(end_timeStamp * 1000L);
		// format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		// String start_date = jdf.format(sdate);
		// String end_date = jdf.format(edate);

		File keywords = new File("E:\\Java_Jars\\include_keyword.txt");

		String spec = FileUtils.readFileToString(keywords, "UTF-8");

		String[] match = spec.split(",");

		String modified_name = ((String) jobj.get("name")).replace("'", "");

		if (a[3] != null) {
			String toCompare = (jobj.get("product_url")).toString().substring(a[2] + 1, a[3]).replace("-", " ");
			// System.out.println(toCompare);
			if (toCompare.equalsIgnoreCase("all")) {
				for (int k = 0; k < match.length; k++) {
					if (modified_name.equalsIgnoreCase(match[k])) {
						cPath = modified_name;
						// System.out.println(cPath);
						if (cPath.contains("Mens") || cPath.contains("Men")) {
							parent = "Men";
						}
						if (cPath.contains("Womens") || cPath.contains("Women")) {
							parent = "Women";
						}
						if (cPath.contains("Boys") || cPath.contains("Boy")) {
							parent = "Boys";
						}
						if (cPath.contains("Girls") || cPath.contains("Girl")) {
							parent = "Girls";
						}
						if (cPath.contains("Kids") || cPath.contains("Kid")) {
							parent = "Kids";
						}
						child = "Kids fashion";
						valid = "Matched";
						break;
					}
				}
			} else {
				valid = "SKIP";
			}

			for (int j = 0; j < match.length; j++) {
				if (toCompare.equalsIgnoreCase(match[j])) {
					if (toCompare.equalsIgnoreCase("clothing")) {
						cPath = (jobj.get("product_url")).toString().substring(a[3] + 1, a[4]).replace("-", " ")
								.replace("~", " ");
					} else {
						cPath = toCompare;
					}

					if (cPath.contains("mens") || cPath.contains("men")) {
						parent = "Men";
					}
					if (cPath.contains("womens") || cPath.contains("women")) {
						parent = "Women";
					}
					if (cPath.contains("boys") || cPath.contains("boy")) {
						parent = "Boys";
					}
					if (cPath.contains("girls") || cPath.contains("girl")) {
						parent = "Girls";
					}
					if (cPath.contains("kids") || cPath.contains("kid")) {
						parent = "Kids";
					}
					if (cPath.contains("bras")) {
						parent = "Women";
					}
					if (cPath.contains("jewellery")) {
						parent = "Women";
					}

					child = toCompare;
					valid = "Matched";
					break;
				}
			}
		}

		if (valid != "Matched") {

			callProcedure = "SKIPcall dealsProcedure(\"" + parent + "\", \"" + jobj.get("name") + "\",\"" + child
					+ "\",\"" + jobj.get("prodName") + "\",\"" + jobj.get("description") + "\",\"" + "0.0" + "\",\""
					+ "0.0" + "\",\"" + "LIVE" + "\",\"" + jobj.get("imageUrl") + "\",\"" + jobj.get("product_url")
					+ "\",\"" + jobj.get("product_id") + "\",\"" + 1 + "\",\"" + 0 + "\",\""
					+ jobj.get("similar_product_id") + "\",\"" + jobj.get("start_date") + "\",\"" + jobj.get("end_date")
					+ "\",@insProductID);";

		} else {

			callProcedure = "call dealsProcedure(\"" + parent + "\",\"" + jobj.get("name") + "\",\"" + child + "\",\""
					+ jobj.get("prodName") + "\",\"" + jobj.get("description") + "\",\"" + "0.0" + "\",\"" + "0.0"
					+ "\",\"" + "LIVE" + "\",\"" + jobj.get("imageUrl") + "\",\"" + jobj.get("product_url") + "\",\""
					+ jobj.get("product_id") + "\",\"" + 1 + "\",\"" + 0 + "\",\"" + jobj.get("similar_product_id")
					+ "\",\"" + jobj.get("start_date") + "\",\"" + jobj.get("end_date") + "\",@insProductID);";

		}
		return callProcedure;

	}

	public static String doDealStore1(String str) throws ParseException, IOException {

		String[] call_return = new String[2];
		jobj = (JSONObject) parser.parse(str);
		String valid = "NULL";
		String parent = null;
		String cPath = null;
		String child = null;
		int index = 0;
		Integer[] a = new Integer[30];
		for (int i = 0; i < ((String) jobj.get("product_url")).length(); i++) {
			if (jobj.get("product_url").toString().charAt(i) == '/') {
				a[index] = i;
				index++;
			}
		}

		String start_time = jobj.get("start_date").toString();
		String end_time = jobj.get("end_date").toString();
		long start_timeStamp = Long.parseLong((start_time.substring(0, start_time.length() - 3)));
		long end_timeStamp = Long.parseLong(end_time.substring(0, end_time.length() - 3));

		Date sdate = new Date(start_timeStamp * 1000L);
		Date edate = new Date(end_timeStamp * 1000L);
		// format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String start_date = jdf.format(sdate);
		String end_date = jdf.format(edate);

		File keywords = new File("E:\\Java_Jars\\include_keyword.txt");

		String spec = FileUtils.readFileToString(keywords, "UTF-8");

		String[] match = spec.split(",");

		String modified_name = ((String) jobj.get("name")).replace("'", "");

		if (a[3] != null) {
			String toCompare = (jobj.get("product_url")).toString().substring(a[2] + 1, a[3]).replace("-", " ");

			if (toCompare.equalsIgnoreCase("all")) {
				for (int k = 0; k < match.length; k++) {
					if (modified_name.equalsIgnoreCase(match[k])) {
						cPath = modified_name;
						if (cPath.contains("Mens")) {
							parent = "Men";
						}
						if (cPath.contains("Womens")) {
							parent = "Women";
						}
						if (cPath.contains("Boys")) {
							parent = "Boys";
						}
						if (cPath.contains("Girls")) {
							parent = "Girls";
						}
						if (cPath.contains("Kids")) {
							parent = "Kids";
						}
						child = "Kids fashion";
						valid = "Matched";
						break;
					}
				}
			} else {
				valid = "SKIP";
			}

			for (int j = 0; j < match.length; j++) {
				if (toCompare.equalsIgnoreCase(match[j])) {
					cPath = toCompare;
					if (cPath.contains("mens")) {
						parent = "Men";
					}
					if (cPath.contains("womens")) {
						parent = "Women";
					}
					if (cPath.contains("boys")) {
						parent = "Boys";
					}
					if (cPath.contains("girls")) {
						parent = "Girls";
					}
					if (cPath.contains("kids")) {
						parent = "Kids";
					}
					if (cPath.contains("bras")) {
						parent = "Women";
					}
					if (cPath.contains("jewellery")) {
						parent = "Women";
					}
					child = toCompare;
					valid = "Matched";
					break;
				}
			}
		}
		String like = "%\\_%\\_";
		if (valid != "Matched") {
			callProcedure = "SKIPcall dealsProcedure(\"" + jobj.get("category") + "\",\"" + jobj.get("product_id")
					+ "\",\"" + jobj.get("start_date") + "\",\"" + like + jobj.get("end_date") + "\",\""
					+ jobj.get("end_date") + "\");";

			String callProcedure1 = "SKIPcall productInsUpdate(\"" + parent + "\", \"" + jobj.get("name") + "\",\""
					+ child + "\",\"" + jobj.get("name") + "\",\"" + jobj.get("description") + "\",\"" + "0.0" + "\",\""
					+ "0.0" + "\",\"" + "LIVE" + "\",\"" + jobj.get("imageUrl") + "\",\"" + jobj.get("deal_url")
					+ "\",\"" + jobj.get("product_id") + "\",\"" + 1 + "\",\"" + 0 + "\",\""
					+ jobj.get("similar_product_id") + "\",@insProductID);";
			call_return[0] = callProcedure;
			call_return[1] = callProcedure1;

		} else {
			callProcedure = "call dealsProcedure(\"" + jobj.get("category") + "\",\"" + jobj.get("product_id") + "\",\""
					+ jobj.get("start_date") + "\",\"" + like + jobj.get("end_date") + "\",\"" + jobj.get("end_date")
					+ "\");";
			String callProcedure1 = "call productInsUpdate(\"" + parent + "\", \"" + jobj.get("name") + "\",\"" + child
					+ "\",\"" + jobj.get("name") + "\",\"" + jobj.get("description") + "\",\"" + "0.0" + "\",\"" + "0.0"
					+ "\",\"" + "LIVE" + "\",\"" + jobj.get("imageUrl") + "\",\"" + jobj.get("deal_url") + "\",\""
					+ jobj.get("product_id") + "\",\"" + 1 + "\",\"" + 0 + "\",\"" + jobj.get("similar_product_id")
					+ "\",@insProductID);";
			call_return[0] = callProcedure;
			call_return[1] = callProcedure1;

		}
		return callProcedure;

	}

	public static String comp(String str, String name) throws IOException {

		String s = "NULL";
		int index = 0;
		Integer[] a = new Integer[10];
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '/') {
				a[index] = i;
				index++;
			}
		}

		String sj = "1542274252031";

		int timeStamp = Integer.valueOf(sj.substring(0, sj.length() - 3));

		Date date = new Date(timeStamp * 1000L);
		// format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT"));//
		String java_date = jdf.format(date);
		System.out.println("\n" + java_date.replace(" GMT", "") + "\n");

		File keywords = new File("include_keyword.txt");

		String spec = FileUtils.readFileToString(keywords, "UTF-8");

		String[] match = spec.split(",");

		String toCompare = str.substring(a[2] + 1, a[3]).replace("-", " ");
		String modified_name = name.replace("'", "");
		if (toCompare.equalsIgnoreCase("all")) {
			for (int k = 0; k < match.length; k++) {
				if (modified_name.equalsIgnoreCase(match[k])) {
					s = "Matched";
					break;
				}
			}
		} else {
			s = "SKIP";
		}

		for (int j = 0; j < match.length; j++) {
			if (toCompare.equalsIgnoreCase(match[j])) {
				s = "Matched";
				break;
			}
		}

		if (s != "Matched") {
			s = "SKIP";
		}
		return s;
	}

	// new methods

	public static String test(String s) {
		String cPath = s;
		int find = 0;
		Integer[] a = new Integer[10];
		int index = 0;
		for (int i = 0; i < cPath.length(); i++) {

			if (cPath.charAt(i) == '\'') {
				find++;
				a[index] = i;
				// System.out.println(a[index]);
				index++;
			}
		}
		// if (find >= 3) {
		// System.out.println(cPath.indexOf("'"));
		String cMap = cPath.substring(a[2] + 1, a[3]);
		// System.out.println(cMap);

		int find1 = 0;
		Integer[] a1 = new Integer[10];
		int index1 = 0;
		for (int i = 0; i < cMap.length(); i++) {

			if (cMap.charAt(i) == '~') {
				find1++;
				a1[index1] = i;
				// System.out.println(a[index1]);
				index1++;
			}
		}
		// System.out.println(find1);
		String final_color = null;
		if (find1 > 1) {
			String cMap1 = cMap.substring(a1[0] + 1, a1[1]);
			String replace_color = regEx(cMap1);
			// System.out.println(replace_color);
			final_color = cMap.replace(cMap1, replace_color);
			// System.out.println(cPath.replace(cMap,final_color));
		} else if (find1 == 0) {
			final_color = cMap;
		} else {
			String cMap1 = cMap.substring(a1[0] + 1, cMap.length());
			String replace_color = regEx(cMap1);
			// System.out.println(replace_color);
			final_color = cMap.replace(cMap1, replace_color);
			// System.out.println(cPath.replace(cMap,final_color));
		}
		return cPath.replace(cMap, final_color);

		// }

	}

	static String regEx(String color) {
		// String regEx1 = "[\\[|\\]|(|//|&|.|$|,|\\d|\\)|-]|(AND)|(and)|(And)";
		String regEx = "[\\[|\\]|(|//|&|.|$|,|\\d|\\)|-]|(AND)|(and)|(And)|(with)|(With)";

		// color = "black, pinkANDredand white, And 12341Yellow";
		color = color.replaceAll(regEx, "&");
		if (color.indexOf("&") == 0) {
			color = color.replaceFirst(regEx, "");
		}
		while (color.contains("&&")) {
			color = color.replaceAll("&&", "&");
		}
		// System.out.println(color);
		return color;

	}

	public static String check_valid_json(String s) {
		// This code checks for entered string is valid or not. Condition is the every
		// line of input string should be separated by \r\n
		String[] match = s.split("\r\n");
		for (int i = 0; i < match.length; i++) {
			int find = 0;
			ArrayList<Integer> arl = new ArrayList<Integer>();
			String demo = match[i];
			for (int j = 0; j < demo.length(); j++) {

				if (demo.charAt(j) == '\"') {
					find++;
					arl.add(j);
				}
			}
			StringBuilder sb = new StringBuilder(match[i]);

			if (find > 4) {
				int count = 0;
				int n = 1;
				for (int k = 3; k < arl.size() - 1; k++) {

					if (count > 0) {

						sb.deleteCharAt(arl.get(k) - n);
						n++;
					} else {
						sb.deleteCharAt(arl.get(k));
					}
					count++;
				}

			}
			match[i] = sb.toString();
		}
		StringBuilder builder = new StringBuilder();
		for (String s1 : match) {
			builder.append(s1);
		}
		String str = builder.toString();
		return str;
	}

	@SuppressWarnings("rawtypes")
	public static String doOptionsStore(String str) throws ParseException {

		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");// size~color,4~black
		String[] data = x.split("_");
		String vendorId = data[0];
		String productId = data[1];
		// dbconfig();
		String total_att_name = "";
		String total_att_val = "";
		String toGroovy;

		Set keySet = jobj.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String att_val;

			String att_name = (String) iterator.next();
			if (!att_name.equals("product_id")) {
				if (jobj.get(att_name).toString().length() < 1) {
					continue;
				} else {
					total_att_name = total_att_name.concat(att_name).concat("~");
					att_val = (String) jobj.get(att_name);// Attribute Value
					total_att_val = total_att_val.concat(att_val).concat("~");

				}
			}

		}
		if (total_att_name != "") {
			callProcedure = "call optionsInsUpdate(\"" + total_att_name.substring(0, total_att_name.length() - 1)
					+ "\",\"" + total_att_val.substring(0, total_att_val.length() - 1) + "\",\"" + productId + "\",\""
					+ vendorId + "\",@insProductID);";
			toGroovy = callProcedure.replace("[", "").replace("]", "");
		} else {
			toGroovy = "NO_ATTRIBUTES";
		}

		return toGroovy;
	}

	public static String doOfferStore(String str) throws ParseException {

		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");
		String[] data = x.split("_");
		String vendorId = data[0];
		String productId = data[1];
		callProcedure = "call offersInsUpdate(\"" + jobj.get("offers").toString().replaceAll("\"", "") + "\",\""
				+ productId + "\",\"" + vendorId + "\",@insProductID);";

		return callProcedure.replace("[", "").replace("]", "");

	}

	public static void call_proc(String s) throws IllegalAccessException, InstantiationException,
			UnsupportedEncodingException, SQLException, ParseException {
		dbconfig();
		String[] data = s.split("~");
		for (int i = 0; i < data.length; i++) {
			CallableStatement cStmt = conn.prepareCall(data[i]);
			System.out.println(i);
			System.out.println(data[i]);
			cStmt.execute();
		}

	}

}
