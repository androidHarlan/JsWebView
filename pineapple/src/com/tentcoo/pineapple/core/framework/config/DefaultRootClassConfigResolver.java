package com.tentcoo.pineapple.core.framework.config;


import java.util.Map;

public class DefaultRootClassConfigResolver implements IConfigResolver{


	public String readRootResolver(Map<String, Object> configMap,
			String resolverBody, IConfigResolver abstractConfigResolver) {
		return null;
	}

	public Map<String, Object> resolverConfig(Map<String, Object> configMap,
			String resolverBody,String parentKey, IConfigResolver abstractConfigResolver) {
		return null;
	}

	@Override
	public Map<String, Object> resolve(String resolverBody) {
		return null;
	}


}
