package com.tentcoo.pineapple.core.framework.resource;

import java.util.Map;

import com.tentcoo.pineapple.core.framework.application.PineApple;


public abstract class AbstractResourceResolver implements IResourceResolver<Object> {
	
	private IResourceLoader loader = getLoader();
	private IResourceFormatter formatter = getFormatter();
	
	@Override
	public Object resolve(String filePath) {
		Object loadAndFormat = loader.loadAndFormat(PineApple.getContext(),filePath, formatter);
		Map<String, Object> onResolve = onResolve(loadAndFormat);
		return onResolve;
	}
	public abstract Map<String, Object> onResolve(Object formattedinput);
	public abstract IResourceFormatter getFormatter(); 
	public abstract IResourceLoader getLoader();
}
