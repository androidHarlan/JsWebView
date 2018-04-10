package com.tentcoo.pineapple.core.framework.resource;


public abstract class AbstractClassPathXmlFileResolver extends AbstractResourceResolver {

	@Override
	public IResourceFormatter getFormatter() {
		return XmlResourceFormatter.getInstance();
	}

	@Override
	public IResourceLoader getLoader() {
		return ClassPathPattenFileLoader.getInstance();
	}
	
}
