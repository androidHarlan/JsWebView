package com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter;

import android.annotation.SuppressLint;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.IBridgeAdapter;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.IBridgeHandler;
import com.tentcoo.pineapple.jsbridge.BridgeWebView;

@SuppressLint("NewApi")
public class WebviewJsBridgeAdapter extends BasicDelegatedFactoryObject implements IBridgeAdapter {
	public BasicViewContainer viewContainer;
	public BridgeWebView bridgeWebView;
	@Override
	public void initBridge(BridgeWebView bridgeWebView) {
		this.bridgeWebView = bridgeWebView;
		bridgeWebView.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
		bridgeWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		// webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		bridgeWebView.getSettings().setUseWideViewPort(true);
	}

	public void registerHandler(String handlerName, IBridgeHandler handler) {
		if (bridgeWebView != null) {
			bridgeWebView.registerHandler(handlerName,
					new WebViewJsBridgeHandler(handler));
		}
	}

	public void setViewContainer(BasicViewContainer viewContainer) {
		this.viewContainer = viewContainer;
	}

	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
