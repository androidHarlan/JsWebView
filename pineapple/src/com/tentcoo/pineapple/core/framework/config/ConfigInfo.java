package com.tentcoo.pineapple.core.framework.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigInfo {
	
	Map<String, Object> configInfo = new HashMap<String, Object>();
	ConfigInfo parentConfigInfo = null;
	
	public ConfigInfo(Map<String, Object> configMap){
		configInfo = configMap;
	}
	
	public ConfigInfo(Map<String, Object> configMap,ConfigInfo parentConfigInfo){
		this.configInfo = configMap;
		this.parentConfigInfo = parentConfigInfo;
	}
	
	public ConfigInfo getParent(){
		return parentConfigInfo;
	}
	
	private Object getObject(String key){
		return configInfo.get(key);
	}
	
	public ConfigInfo getConfigInfo(String key){
		Object object = getObject(key);
		if(object instanceof Map){
			ConfigInfo configInfo = new ConfigInfo((Map<String, Object>) object,this);
			return configInfo;
		}
		return null;
	}
	
	public List<ConfigInfo> getList(String key){
		Object object = getObject(key);
		if(object instanceof List){
			return (List<ConfigInfo>) object;
		}
		return null;
	}
	
	public String getString(String key){
		Object object = getObject(key);
		if(object instanceof String){
			return String.valueOf(object);
		}
		return null;
	}
	
	public Integer getInteger(String key){
		Object object = getObject(key);
		if(object instanceof List){
			return (Integer) object;
		}
		return null;
	}
	
	public Map<String, Object> toMap(){
		return this.configInfo;
	}
	
	public void checkObjectType(String key){
		Object object = getObject(key);
		if(object instanceof List){
			System.out.println("**************List************");
		}else if(object instanceof String){
			System.out.println("**************String************");
		}else if(object instanceof Map){
			System.out.println("**************Map************");
		}else if(object instanceof Integer){
			System.out.println("**************Integer************");
		}
	}
	
}
