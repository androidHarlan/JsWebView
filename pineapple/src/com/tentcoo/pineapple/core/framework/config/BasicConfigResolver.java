package com.tentcoo.pineapple.core.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.tentcoo.pineapple.core.framework.resource.AbstractClassPathKeyValueFileResolver;
import com.tentcoo.pineapple.utils.Logger;

public class BasicConfigResolver extends AbstractClassPathKeyValueFileResolver{

	private  static BasicConfigResolver instance;
	public static BasicConfigResolver getInstance(){
		if(instance==null){
			instance = new BasicConfigResolver();
		}
		return instance;
	}	
	
	
	@Override 
	public  Map<String, Object> onResolve(Object formattedInput) {
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		InputStream is = null;
		if(formattedInput instanceof InputStream){			
			is = (InputStream)formattedInput;
		}
		Map<String, Object> resolverConfig = resolverConfig(is);
		keyValueMap.putAll(resolverConfig);
		return keyValueMap; 
	}
	/***
	 * 解析keyvalue,然后保存到map中去，key对应key ，value对应value
	 * @param resolverBody	要解析的内容
	 * @return
	 */
	public Map<String, Object> resolverConfig(InputStream input) {
		Map<String,Object> map = new HashMap<String, Object>();
		if(input!=null){
			Properties	props = new Properties();
			try {
				props.load(input);
				Enumeration<?> en = props.propertyNames();
			    while (en.hasMoreElements()) {
	                String key = en.nextElement().toString();//key值
	                String Property = new String(props.getProperty(key).getBytes("ISO-8859-1"),"utf-8");//value值
	                Logger.i("键值对--"+key+"："+Property);
	                map.put(key, Property);
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}


	
}
