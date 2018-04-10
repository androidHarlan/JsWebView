package com.tentcoo.pineapple.jsbridge;

public interface BridgeHandler {
	/***
	 * web 发送消息的时候，会跑到该方法里来
	 * @param data		web 发送的消息内容
	 * @param function		//app端  可执行的回调，可以通过 function.onCallBack(String message) 返回给web
     */
	void handler(String data, CallBackFunction function);

}
