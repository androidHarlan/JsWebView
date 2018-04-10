package com.tentcoo.pineapple.core.framework.resource;


public abstract class AbstractClassPathKeyValueFileResolver extends AbstractResourceResolver {

	@Override
	public IResourceFormatter getFormatter() {
		return KeyValueResourceFormatter.getInstance();
	}

	@Override
	public IResourceLoader getLoader() {
		return ClassPathPattenFileLoader.getInstance();
	}

}
