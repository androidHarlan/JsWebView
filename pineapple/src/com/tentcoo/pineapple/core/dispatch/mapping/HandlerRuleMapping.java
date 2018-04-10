package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;


public class HandlerRuleMapping extends BasicFactoryObject{
	String rule;
	
	IHandler handler;
	
	public IHandler getHandler() {
		return handler;
	}

	public void setHandler(IHandler handler) {
		this.handler = handler;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "HandlerRuleMapping [rule=" + rule + ", handler=" + handler
				+ "]";
	}
	
}
