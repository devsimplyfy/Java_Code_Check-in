package attributes;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.Statement;

public class sql {
	static Connection conn = null;
	static Statement stmt;
	static JSONParser parser = new JSONParser();

	static JSONObject jobj = null;

	public static void dbconfig() throws IllegalAccessException, InstantiationException, SQLException, ParseException,
			UnsupportedEncodingException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://IWW-PC:3306/wsimcpsn_shopnowee";
			conn = DriverManager.getConnection(url, "root", "Alfred_21");
			System.out.println("CONNECTION DONE");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		stmt = (Statement) conn.createStatement();

	}

	public static void dbconfig2() throws IllegalAccessException, InstantiationException, SQLException, ParseException,
			UnsupportedEncodingException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://admin-PC:3306/test";
			conn = DriverManager.getConnection(url, "root", "Alfred_21");
			System.out.println("CONNECTION DONE");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		stmt = (Statement) conn.createStatement();
	}

	public static void doKeywordsStore(String str) throws IllegalAccessException, InstantiationException, SQLException,
			ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");
		String[] data = x.split("_");
		// String vendorId = data[0];
		String productId = data[1];
		dbconfig();
		String callProcedure = "call keywordsProcedure('" + productId + "','" + jobj.get("keywords") + "');";
		stmt.executeUpdate(callProcedure);

	}

	public static void doImageKeywordsStore(String str) throws IllegalAccessException, InstantiationException,
			SQLException, ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		jobj = (JSONObject) parser.parse(str);
		dbconfig();
		String callProcedure = "call image_keywordsProcedure('" + jobj.get("product_id") + "','" + jobj.get("keywords")
				+ "');";
		stmt.executeUpdate(callProcedure);

	}

	public static void doDealStore(String str) throws IllegalAccessException, InstantiationException, SQLException,
			ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		jobj = (JSONObject) parser.parse(str);
		dbconfig();
		String callProcedure = "call dealsProcedure('" + jobj.get("name") + "','" + jobj.get("imageUrl") + "','"
				+ jobj.get("show_homepage") + "','" + jobj.get("product_id") + "');";
		stmt.executeUpdate(callProcedure);
	}

	@SuppressWarnings("rawtypes")
	public static void doAttrStore(String str) throws IllegalAccessException, InstantiationException, SQLException,
			ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		jobj = (JSONObject) parser.parse(str);
		String x = (String) jobj.get("product_id");
		String[] data = x.split("_");
		// String vendorId = data[0];
		String productId = data[1];
		dbconfig();
		Set keySet = jobj.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String att_val;
			String att_name = (String) iterator.next();
			if (att_name != "product_id") {
				if (jobj.get(att_name) == "") {
					att_val = "NA";
				} else {
					att_val = (String) jobj.get(att_name);// Attribute Value
				}
				// String productId = (String) jobj.get("product_id");// ProductId
				String callProcedure = "call spInsUpdateProdAttr('" + att_name + "','" + att_val + "','" + productId
						+ "',@insProductID);";
				stmt.executeUpdate(callProcedure);

			}
		}
	}

	public static void doCatStore(String str) throws IllegalAccessException, InstantiationException, SQLException,
			ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		jobj = (JSONObject) parser.parse(str);
		dbconfig();
		String x = (String) jobj.get("product_id");
		String[] data = x.split("_");
		String vendorId = data[0];
		String productId = data[1];
		String parent = null;
		String cName = null;
		String cPath = (String) jobj.get("categoryPath");
		String child = null;
		if (cPath.toLowerCase().contains("men") || cPath.toLowerCase().contains("man")) {
			parent = "Men";
		}
		if (cPath.toLowerCase().contains("women") || cPath.toLowerCase().contains("woman")) {
			parent = "Women";
		}
		if (cPath.toLowerCase().contains("boys") || cPath.toLowerCase().contains("boy")) {
			parent = "Boys";
		}
		if (cPath.toLowerCase().contains("girls") || cPath.toLowerCase().contains("girl")) {
			parent = "Girls";
		}
		if (cPath.contains(">")) {
			child = cPath.substring(cPath.lastIndexOf(">") + 1, cPath.length());
		} else {
			child = cPath;
		}
		if (parent != null) {
			cName = parent;
		} else {
			cName = (String) jobj.get("category_name");
		}
		String callProcedure = "call productInsUpdate('" + cName + "', '" + child + "','" + jobj.get("name") + "','"
				+ jobj.get("description") + "','" + jobj.get("regular_price") + "','" + jobj.get("sale_price") + "','"
				+ jobj.get("stock") + "','" + jobj.get("image") + "','" + jobj.get("product_url") + "','" + productId
				+ "','" + vendorId + "','" + 0 + "','" + jobj.get("similar_product_id") + "',@insProductID);";
		System.out.println(callProcedure);
		stmt.executeUpdate(callProcedure);

	}

	public static void path(String str, String str1) throws IllegalAccessException, InstantiationException,
			SQLException, ParseException, UnsupportedEncodingException, UndeclaredThrowableException {
		dbconfig2();
		String callProcedure = "INSERT IGNORE INTO msg(Cpath,mainCat) VALUES ('" + str + "','" + str1 + "')";
		stmt.executeUpdate(callProcedure);
	}

	public static String subPath(String str) {
		// dbconfig2();
		String parent = null;
		String child = null;
		String cTree = str;

		if (str.contains("Men") || str.contains("Man")) {
			parent = "Men";
		}
		if (str.contains("Women") || str.contains("Woman")) {
			parent = "Women";
		}
		if (str.contains("Boys") || str.contains("Boy")) {
			parent = "Boys";
		}
		if (str.contains("Girls") || str.contains("Girl")) {
			parent = "Girls";
		}
		child = str.substring(str.lastIndexOf(">") + 1, str.length());
		if (parent == null) {
			parent = str;
		}
		if (child == null) {
			child = str;
		}
		String callProcedure = "INSERT IGNORE INTO levels(parent,child,cTree) VALUES ('" + parent + "','" + child
				+ "','" + cTree + "')";
		// stmt.executeUpdate(callProcedure);
		return callProcedure;
	}

	public static String cat_map(String str) {
		// dbconfig2();
		String parent = null;
		String child = null;
		String cMap = null;
		int i = 0;
		int index = 0;
		int find = 0;
		Integer[] a = new Integer[10];
		for (i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '>') {
				find++;
				a[index] = i;
				// System.out.println(a[index]);
				index++;
			}
		}

		if (str.contains("Men")) {
			parent = "Men";
		}
		if (str.contains("Women")) {
			parent = "Women";
		}
		if (str.contains("Boys")) {
			parent = "Boys";
		}
		if (str.contains("Girls")) {
			parent = "Girls";
		}
		child = str.substring(str.lastIndexOf(">") + 1, str.length());

		if (parent == null) {
			parent = str;
		}
		if (child == null) {
			child = str;
		}
		if (find >= 3) {
			if (str.contains("Infant") || str.contains("Kids")) {
				cMap = str.substring((a[index - 2]) + 1, a[index - 1]);
			} else {
				cMap = str.substring(a[1] + 1, a[2]);
			}
		} else {
			cMap = child;
		}
		System.out.println("Root(Parent)\t:\t" + parent);
		System.out.println("Sub(Map)Category:\t" + cMap);
		System.out.println("Category(Child)\t:\t" + child + "\n");
		String callProcedure = "INSERT IGNORE INTO catg_map(main,src,destn) VALUES ('" + parent + "','" + cMap + "','"
				+ child + "')";
		// stmt.executeUpdate(callProcedure);
		return callProcedure;
	}

	public static void call_proc(String s) throws IllegalAccessException, InstantiationException,
			UnsupportedEncodingException, SQLException, ParseException {
		dbconfig();
		CallableStatement cStmt = conn.prepareCall(s);
		cStmt.execute();

	}
}
