package com.tentcoo.pineapple.core.framework.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tentcoo.pineapple.utils.ToolUtil;


public class JsonConfigResolver implements IConfigResolver{

	private static JsonConfigResolver instance;
	public static JsonConfigResolver getInstance(){
		if(instance==null){
			instance =new JsonConfigResolver();
		}
		return instance;
	}
	public Map<String, Object> resolverConfig(Map<String, Object> configMap,
			String resolverBody, String parentKey,
			IConfigResolver abstractConfigResolver) {
		try {
			JSONObject jsonObj = new JSONObject(resolverBody);
			Iterator it =jsonObj.keys();
			while(it.hasNext()){
				String key = (String) it.next(); 
				String jsonStr = jsonObj.getString(key);
				if(ToolUtil.isObject(jsonStr)){
					configMap.put(key,abstractConfigResolver.resolve(jsonStr));
				}else if(ToolUtil.isArray(jsonStr)){
					JSONArray jsonArray = new JSONArray(jsonStr);
					List<Object> list = new ArrayList<Object>();
					for(int i=0;i<jsonArray.length();i++){
						if(!ToolUtil.isJSON(jsonArray.getString(i))){
							list.add(jsonArray.getString(i));
							continue;
						}
						list.add(abstractConfigResolver.resolve(jsonArray.getString(i)));
					}
					configMap.put(key, list);
				}else{
					configMap.put(key,jsonObj.getString(key));
				}
			}
			return configMap;
		} catch (JSONException e) {
			e.printStackTrace();
			return configMap;
		}
	}

	public Map<String, Object> resolve(String resolverBody,
			IConfigResolver abstractConfigResolver) {
		Map<String, Object> configMap = new HashMap<String, Object>();
		resolverConfig(configMap,resolverBody,"",abstractConfigResolver);
		return configMap;
	}

	public String readRootResolver(Map<String, Object> configMap,
			String resolverBody, IConfigResolver abstractConfigResolver) {
		return resolverBody;
	}
	


	@Override
	public Map<String, Object> resolve(String resolverBody) {
		return resolve(resolverBody, this);
	}

}
