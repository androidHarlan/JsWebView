package com.tentcoo.pineapple.core.dispatch.mapping;

import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;


/**处理器的执行链*/
public class HandlerExceptionChain extends BasicFactoryObject{

	/**拦截器*/
	private  List<IHandlerInterceptor> interceptors; 
	/**处理器*/
	private IHandler handler;
	
	public List<IHandlerInterceptor> getInterceptors(){
		return interceptors;
	}
	public void addInterceptor(IHandlerInterceptor interceptor){
		if(interceptors==null){
			interceptors = new ArrayList<IHandlerInterceptor>();
		}
		interceptors.add(interceptor);
	}
	public IHandler getHandler(){
		return handler;
	}
	public void setHandler(IHandler handler){
		this.handler = handler;
	}
	
	
}
