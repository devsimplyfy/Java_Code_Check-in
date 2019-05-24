package attributes;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class FlatWalmartProdCat {
	static String parentkey = "";
	
	final static String DEFAULT_PARENT_KEY = "0";
	final static String DEFAULT_PARENT_KEY_NAME = "parentid";
	final static String ROW_KEY = "id";
	final static String NESTED_ATTR_NAME = "children";
	
	static JsonArray arrFlatCatJson = new JsonArray();
    public static String doFlat(String categories) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
    	String s = categories;    	
        JsonObject objData = new Gson().fromJson(s, JsonObject.class);
        for (Map.Entry<String, JsonElement> entry : objData.entrySet()) {
        	JsonObject objFlatCat = new JsonObject();
             if(entry.getValue() instanceof JsonArray) {
            	 JsonArray jsonArrayval = entry.getValue().getAsJsonArray();
            	 for (int count = 0; count < jsonArrayval.size(); count++) {
            		 JsonElement objElement = jsonArrayval.get(count);
            		 JsonObject objJson = objElement.getAsJsonObject();
            		 System.out.println(objJson);
            		 objJson.entrySet().stream().forEach(row -> {
                         String attrName = row.getKey();
                         JsonElement attrVal = row.getValue();
                         if (attrVal instanceof JsonArray) {
                        	 printJson(attrVal, attrName,parentkey, attrName);
                         }
                         if(ROW_KEY.equals(attrName))
                        	 parentkey = attrVal.getAsString();
                         if(!NESTED_ATTR_NAME.equals(attrName)) {
                        	 objFlatCat.addProperty(attrName, attrVal.getAsString());
                         }
                     });
            		 objFlatCat.addProperty(DEFAULT_PARENT_KEY_NAME,DEFAULT_PARENT_KEY);
          	   		 arrFlatCatJson.add(objFlatCat);
            	 }
            	 
             }
        }
        printMap();
        return arrFlatCatJson.toString();
    } 

    public static String printMap() {
    	Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		//System.out.println(gson.toJson(arrFlatCatJson));
		return gson.toJson(arrFlatCatJson);
    }
    
 static void printJson(JsonElement jsonElement, String key, String parentid, String rowkey) {
	  String innerParentKey = parentid;
		String rowIdentifier = "";
    	if (jsonElement instanceof JsonArray) {
    		JsonArray jsonArr = jsonElement.getAsJsonArray();
           	for (int j = 0; j < jsonArr.size(); j++) {
	       		JsonObject objFlatCat = new JsonObject();
	   		 JsonElement objectData = jsonArr.get(j);
	   		 JsonObject joa = objectData.getAsJsonObject();
	   		 for (Entry<String, JsonElement> data : joa.entrySet()) {
	   	            String currKey = data.getKey();
	                JsonElement currElement = data.getValue();
	                if(ROW_KEY.equals(currKey)){	
	                	rowIdentifier = currElement.getAsString();
	                }
	                if (currElement instanceof JsonArray) {
	                	printJson(currElement, currKey, rowIdentifier, rowIdentifier);
	                }else {
	                	if(!NESTED_ATTR_NAME.equals(currKey)) {
	                		objFlatCat.addProperty(currKey, currElement.getAsString());
	                	}
	                }
	   		 } 
	   		objFlatCat.addProperty(DEFAULT_PARENT_KEY_NAME,innerParentKey);
	   		arrFlatCatJson.add(objFlatCat);
	   	  }
	    }
      }
 }