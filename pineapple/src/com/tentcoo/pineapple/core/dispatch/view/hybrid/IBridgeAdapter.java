package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.jsbridge.BridgeWebView;

public interface IBridgeAdapter extends ICore {
	public void  registerHandler(String mappingName,IBridgeHandler bridgeHandler);
	void initBridge(BridgeWebView bridgeWebView);
	void setViewContainer(BasicViewContainer viewContainer);
}
