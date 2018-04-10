package com.tentcoo.pineapple.core.framework.ioc.factory;

import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.IBuilder;
import com.tentcoo.pineapple.core.framework.ioc.BeanDependencyBuilder;

public class BeanFactory extends BasicFactory {
	private static BeanFactory instance;
	public static BeanFactory getInstance(){
		if(instance==null){
			instance = new BeanFactory();
		}
		return instance;
	}
	@Override
	public IBuilder getDefaultBuilder() {
		return new BeanDependencyBuilder();
	}
	
}
