package com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter;

import com.tentcoo.pineapple.core.dispatch.view.hybrid.IResponseCallback;
import com.tentcoo.pineapple.jsbridge.CallBackFunction;


public class BridgeResponseCallbackAdapter implements IResponseCallback{
	private CallBackFunction function;
	public BridgeResponseCallbackAdapter(CallBackFunction function){
		this.function=function;
	}
	@Override
	public void callback(Object data) {
		if(function!=null){
			function.onCallBack((String)data);
		}
	}
}
