package com.tentcoo.pineapple.core.common.factory.delegate;

import java.util.List;

import leo.android.cglib.proxy.MethodProxy;


public class CutPoint {
	public List<BasicHook> unexecutedHookList;
	
	private int index = 0; // 指针
	private Object object;
	private MethodProxy methodProxy ;
	private Object[] parameters;

	public Object result; // 执行结果
	public String exceptions; // 异常

	public boolean isExceptions = false; // 是否发生异常
	public boolean isInvoked = false; // 原方法是否被调用

	
	public void setInvoke(Object obj,MethodProxy method, Object[] params){
		this.object = obj;
		this.methodProxy = method;
		this.parameters = params;
	}
	public String getExceptions() {
		return exceptions;
	}
	
	public MethodProxy getInvokedMethod() {
		return methodProxy;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	public boolean isExceptions() {
		return isExceptions;
	}

	public void setExceptions(boolean isExceptions) {
		this.isExceptions = isExceptions;
	}

	public Object getObject() {
		return object;
	}
	
	public Object[] getParameters() {
		return parameters;
	}



	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}



	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	public List<BasicHook> getUnexecutedHookList() {
		return unexecutedHookList;
	}

	public void setUnexecutedHookList(List<BasicHook> unexecutedHookList) {
		this.unexecutedHookList = unexecutedHookList;
	}
	
	/**
	 * 获取下一个钩子，同时将指针移动到下一个钩子 ps：这里不能用删除最顶部的钩子的方法来实现获取下一个钩子
	 */
	public BasicHook getNextHook() {
		BasicHook hook = unexecutedHookList.get(index);
		index++;
		return hook;
	}

	/**
	 * 检查是否还有下一个钩子对象
	 */
	public boolean hasNextHook() {
		if (unexecutedHookList!=null && unexecutedHookList.size() > index) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断该方法是否异常退出
	 * 
	 * @return
	 */
	public boolean hasExceptions() {
		return false;
	}

	/**
	 * 判断原方法是否已经被调用
	 * 
	 * @return
	 */
	public boolean isInvoked() {
		return false;
	}

	/**
	 * 执行原方法
	 * 
	 * @return
	 */
	public Object invoke() {
		Object invoke =null;
		try {
			invoke = methodProxy.invokeSuper(object, parameters);
			isInvoked = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return invoke;
	}

	public void proceed() {
		if (hasNextHook()) { // 检查是否还有钩子
			BasicHook nextHook = getNextHook();
			nextHook.callback(this);
		} else { // 如果执行到最后了直接执行原方法
			result = invoke();
			this.isInvoked = true;
		}
	}
}
