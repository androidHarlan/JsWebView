package com.tentcoo.pineapple.core.common.factory.delegate;


public  class  BasicHook{
	private String rule; // 方法名
	private CutPoint cutPoint; //
	
	public String getRule(){
		return rule;
	}
	
	public void setRule(String rule) {
		this.rule = rule;
	}

	public void callback(CutPoint cutPoint){}
	
}
