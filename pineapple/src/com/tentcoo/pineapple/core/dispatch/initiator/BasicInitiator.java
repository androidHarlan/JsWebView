package com.tentcoo.pineapple.core.dispatch.initiator;

import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.common.bean.ICore;

public class BasicInitiator implements ICore {
	private Object token;  //用于解析依赖关系的值
	private List<Object> depencies = new ArrayList<Object>();	//依赖对象列表

	
	public Object getToken() {
		return token;
	}
	public void setToken(Object token) {
		this.token = token;
	}
	/**判断所依赖的对象是否都已经初始化*/
	public  boolean isClearedToGo(List<Object> handledList){
		for(Object depencie :depencies){
			if(!handledList.contains(depencie)){
				return false;
			}
		}
		return true;
	}
	public  void init(){
//		在子类中实现了
	}
	@Override
	public String getId() {
		return null;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}
}
