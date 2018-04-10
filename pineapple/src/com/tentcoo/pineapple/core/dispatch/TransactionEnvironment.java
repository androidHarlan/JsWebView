package com.tentcoo.pineapple.core.dispatch;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.core.dispatch.view.ITransactionHandler;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.BridgeResponseCallbackAdapter;

public class TransactionEnvironment extends BasicFactoryObject{
	private ITransactionHandler transactionHandler;
	private BridgeResponseCallbackAdapter responseCallbackAdapter;
	private BasicViewContainer viewContainer;
	
	public void setTransactionHandler(ITransactionHandler transactionHandler){
		this.transactionHandler=transactionHandler;
	}
	public void setTransactionCallback(BridgeResponseCallbackAdapter responseCallbackAdapter){
		this.responseCallbackAdapter=responseCallbackAdapter;
	}
	public void setViewContaion(BasicViewContainer viewContainer){
		this.viewContainer=viewContainer;
	}
	public BridgeResponseCallbackAdapter getResponseCallbackAdapter() {
		return responseCallbackAdapter;
	}
	public void setResponseCallbackAdapter(
			BridgeResponseCallbackAdapter responseCallbackAdapter) {
		this.responseCallbackAdapter = responseCallbackAdapter;
	}
	public BasicViewContainer getViewContainer() {
		return viewContainer;
	}
	public void setViewContainer(BasicViewContainer viewContainer) {
		this.viewContainer = viewContainer;
	}
	public ITransactionHandler getTransactionHandler() {
		return transactionHandler;
	}
	
	
}
