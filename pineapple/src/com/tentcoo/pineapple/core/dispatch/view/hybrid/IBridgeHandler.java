package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.BridgeResponseCallbackAdapter;


public interface  IBridgeHandler{

	 void handle(String data,BridgeResponseCallbackAdapter function);
}
