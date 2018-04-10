package com.tentcoo.pineapple.core.dispatch.view.hybrid;


import com.tentcoo.pineapple.core.common.constant.ID;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.BridgeResponseCallbackAdapter;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.WebviewJsBridgeAdapter;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.jsbridge.BridgeWebView;
import com.tentcoo.pineapple.utils.Logger;

public class HybirdViewContainer extends BasicViewContainer implements IBridge,IBridgeHandler{

	public BridgeWebView bridgeWebView;
	
	@Autowired(clazz=WebviewJsBridgeAdapter.class)
	public  IBridgeAdapter bridgeAdapter;
	
	@Autowired(clazz=BridgeHandlerAdapter.class)
	public  IBridgeHandlerAdapter bridgeHandlerAdapter;
	
	public void setWebView(BridgeWebView bridgeWebView){
		this.bridgeWebView=bridgeWebView;
		System.out.println(bridgeWebView);
	}
	
	public TransactionEnvironment createTransactionEnvironment(){
		TransactionEnvironment transactionEnvironment=BeanFactory.getInstance().build(getContext(),TransactionEnvironment.class);//beanFactory
		return transactionEnvironment;
	} 
	@Override
	public void handle(String data, BridgeResponseCallbackAdapter function) {
		Logger.i("收到web端消息："+data);
		BridgeTransactionHandler transactionHandler = (BridgeTransactionHandler) getTransactionHandlerByType(BridgeTransactionHandler.class);
		TransactionEnvironment createTransactionEnvironment = createTransactionEnvironment();
		createTransactionEnvironment.setTransactionHandler(transactionHandler);
		createTransactionEnvironment.setTransactionCallback(function);
		createTransactionEnvironment.setViewContaion(this);
		bridgeHandlerAdapter.handle(getContext(),data, createTransactionEnvironment);
	}

	@Override
	public void registerHandler(String name, IBridgeHandler handler) {
	}

	@Override
	public void callHandler(String name, String param, IResponseCallback clllback) {
	}
	@Override
	public void onCreated() {
		err();
		bridgeAdapter.initBridge(bridgeWebView);
		bridgeAdapter.registerHandler(ID.BRIDGE_HANDLER_REGISTER, this);
		bridgeAdapter.setViewContainer(this);
		bridgeHandlerAdapter.setViewContainer(this);
		dispatchWorker.setViewContainer(this);
		
	}

	@Override
	public void init() {
		
	}
	
	private void err(){
		if(bridgeAdapter==null){
			Logger.e("HybridViewContainer.bridgeAdapter为空！");
		}
		if(bridgeHandlerAdapter==null){
			Logger.e("HybridViewContainer.bridgeHandlerAdapter为空！");
		}
		if(dispatchWorker==null){
			Logger.e("ViewContainer.dispatchWorker为空！");
		}
	}
}
