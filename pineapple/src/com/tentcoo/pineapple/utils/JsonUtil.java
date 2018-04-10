package com.tentcoo.pineapple.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	/** 
     * Json 转成 Map<> 
     * @param jsonStr 
     * @return 
     */  
    public static Map<String, Object> getMapForJson(String jsonStr){  
        JSONObject jsonObject ;  
        try {  
            jsonObject = new JSONObject(jsonStr);  
            Iterator<String> keyIter= jsonObject.keys();  
            String key;  
            Object value ;  
            Map<String, Object> valueMap = new HashMap<String, Object>();  
            while (keyIter.hasNext()) {  
                key = keyIter.next();  
                value = jsonObject.get(key);  
                valueMap.put(key, value);  
            }  
            return valueMap;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
    public static List<Object> getListForJson(String jsonStr){
    	JSONArray jsonArray = null;
    	try {
			jsonArray = new JSONArray(jsonStr);
			List<Object> valueList = new ArrayList<Object>();
			for(int i =0;i<jsonArray.length();i++){
				Object object = jsonArray.get(i);
				valueList.add(object);
			}
			return valueList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
    	
    }
    
}
