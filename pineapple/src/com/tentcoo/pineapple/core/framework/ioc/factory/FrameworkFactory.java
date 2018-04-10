package com.tentcoo.pineapple.core.framework.ioc.factory;

import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.IBuilder;
import com.tentcoo.pineapple.core.framework.ioc.FrameworkDependencyBuilder;

public class FrameworkFactory extends BasicFactory {

	private static FrameworkFactory instance;
	public static FrameworkFactory getInstance(){
		if(instance==null){
			instance = new FrameworkFactory();
		}
		return instance;
	}
	@Override
	public IBuilder<Object,Object> getDefaultBuilder() {
		return new FrameworkDependencyBuilder();
	}
	
}
