package com.tentcoo.pineapple.core.dispatch.view.hybrid;



public interface  IBridge {

	public  void init();
	
	public  void registerHandler(String name,IBridgeHandler handler);
	
	public  void callHandler(String name,String param,IResponseCallback clllback);
}
