package com.tentcoo.pineapple.core.dispatch.service.formatter.parameter;


import java.util.List;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;


public class DirectObjectParameterFormatter extends BasicDelegatedFactoryObject implements IServiceParameterFormatter {

	@Override
	public List<Object> format(Request request) {
		//FIXME 不确定有没有问题
		List<Object> object = request.getObject();
		return object;
	}

	@Override
	public String getId() {
		//这里需要设置
		
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
