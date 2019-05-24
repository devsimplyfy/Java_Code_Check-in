package attributes;

public class color_replace {

	public static String test(String s) {
		String cPath=s;
		int find=0;
		Integer[] a = new Integer[10];
		int index=0;
		for (int i = 0; i < cPath.length(); i++) {
			
			if (cPath.charAt(i) == '\'') {
				find++;
				a[index] = i;
				//System.out.println(a[index]);
				index++;
			}
		}
		//if (find >= 3) {
			//System.out.println(cPath.indexOf("'"));
			String cMap = cPath.substring(a[2] + 1, a[3]);
			//System.out.println(cMap);
			
			int find1=0;
			Integer[] a1 = new Integer[10];
			int index1=0;
			for (int i = 0; i < cMap.length(); i++) {
				
				if (cMap.charAt(i) == '~') {
					find1++;
					a1[index1] = i;
					//System.out.println(a[index]);
					index1++;
				}
			}
			String cMap1 = cMap.substring(a1[0] + 1, a1[1]);
			String replace_color = regEx(cMap1);
			//System.out.println(replace_color);
			String final_color = cMap.replace(cMap1,replace_color);
			//System.out.println(cPath.replace(cMap,final_color));
			
			return cPath.replace(cMap,final_color);
			
		
		//}
		
	}
	static String regEx(String color) {
		//String regEx1 = "[\\[|\\]|(|//|&|.|$|,|\\d|\\)|-]|(AND)|(and)|(And)";
		String regEx = "[\\[|\\]|(|//|&|.|$|,|\\d|\\)|-]|(AND)|(and)|(And)|(with)|(With)";

		// color = "black, pinkANDredand white, And 12341Yellow";
		color = color.replaceAll(regEx, "&");
		if (color.indexOf("&") == 0) {
			color = color.replaceFirst(regEx, "");
		}
		while (color.contains("&&")) {
			color = color.replaceAll("&&", "&");
		}
		//System.out.println(color);
		return color;

	}
	
}
