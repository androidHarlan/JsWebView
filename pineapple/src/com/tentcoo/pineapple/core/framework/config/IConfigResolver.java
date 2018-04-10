package com.tentcoo.pineapple.core.framework.config;


import java.util.Map;



public interface IConfigResolver {
	
//	Map<String, Object> resolverConfig(Map<String,Object> configMap,String resolverBody,String parentKey,AbstractConfigResolver abstractConfigResolver);
	
	Map<String, Object> resolve(String resolverBody);
	
//	Map<String, Object> resolve(String resolverBody,IConfigResolver abstractConfigResolver);
	
//	String readRootResolver(Map<String,Object> configMap,String resolverBody,AbstractConfigResolver abstractConfigResolver);
}
