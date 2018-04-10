package com.tentcoo.pineapple.core.common.factory.delegate;

import com.tentcoo.pineapple.core.common.factory.BasicFactory;

/**静态单例*/
public class BasicDelegatedObjectFactory extends BasicFactory {
	private static BasicDelegatedObjectFactory instance;
	public static BasicDelegatedObjectFactory getInstance(){
		if(instance==null){
			instance = new BasicDelegatedObjectFactory();
		}
		return instance;
	}
	
}
