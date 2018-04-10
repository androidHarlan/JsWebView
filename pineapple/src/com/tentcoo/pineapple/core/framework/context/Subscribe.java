package com.tentcoo.pineapple.core.framework.context;

public class Subscribe {
	INtificationHandler handler;
	String rule;
	private Object obj;
	private String className;

	/**订阅通知*/
	public void notify(Object obj){
		String message = "";
		handleNotification(message);
	}
	
	public String getSubscribeRule(){
		return rule;
	}
	
	/**通知的具体处理方法	，注意：这是一个异步调用，实际的通知业务在异步线程中执行*/
	public void onMessage(String message){
		
	}
	
	public INtificationHandler getHandler(){
		return handler;
	}
	
	public void handleNotification(String message){
//		
		
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
