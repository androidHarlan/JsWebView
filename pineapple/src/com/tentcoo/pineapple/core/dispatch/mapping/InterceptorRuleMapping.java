package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;

/**
 * 
 * @author Administrator
 *
 */
public class InterceptorRuleMapping extends BasicFactoryObject{

	public String rule;

	public IHandlerInterceptor interceptor;
	
	public IHandlerInterceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(IHandlerInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}
