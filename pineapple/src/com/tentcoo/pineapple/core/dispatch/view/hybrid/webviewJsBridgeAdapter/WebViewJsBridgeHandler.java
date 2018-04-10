package com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter;

import com.tentcoo.pineapple.core.dispatch.view.hybrid.IBridgeHandler;
import com.tentcoo.pineapple.jsbridge.BridgeHandler;
import com.tentcoo.pineapple.jsbridge.CallBackFunction;

public class WebViewJsBridgeHandler implements BridgeHandler{
	private IBridgeHandler handler;
	public WebViewJsBridgeHandler(IBridgeHandler handler){
		this.handler=handler;
	}
	@Override
	public void handler(String data, CallBackFunction function) {
		if(handler!=null){
			BridgeResponseCallbackAdapter cba=new BridgeResponseCallbackAdapter(function);
			handler.handle(data, cba);
		}
	}
}
