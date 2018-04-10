package com.tentcoo.pineapple.core.common.factory.delegate;

import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;

public class BasicDelegatedFactoryObject extends BasicFactoryObject {
	public List<BasicHook> allHooks = new ArrayList<BasicHook>(); // 钩子列表

//	增
	public void addHook(BasicHook hook) {
		allHooks.add(hook);
	}

//	删 全部
	public void clearHook() {
		allHooks.clear();
	}

//	查
	List<BasicHook> getAllHooks() {
		return allHooks;
	}
//	删 单个
	public void removeHook(int index) {
		allHooks.remove(index);
	}
	
//	还不知道是干什么的
	public BasicHook matchHook(){
		return null;
	}
//	还不知道是干什么的
	public void  methodInvokeNotification(){
		
	}
}
