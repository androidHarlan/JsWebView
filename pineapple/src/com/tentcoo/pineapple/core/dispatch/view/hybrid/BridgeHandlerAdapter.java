package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import android.content.Context;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Transaction;
import com.tentcoo.pineapple.core.dispatch.TransactionBuilder;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;

public  class BridgeHandlerAdapter extends BasicDelegatedFactoryObject implements IBridgeHandlerAdapter{
	@Autowired(id="transactionBuilder",clazz=TransactionBuilder.class)
	public   TransactionBuilder transactionBuilder;		//静态单例
/*	@Autowired(ID="transactionFactory")
	public TransactionFactory transactionFactory;*/
	private BasicViewContainer viewContainer;
	@Override
	public  void handle(Context context,String data, final TransactionEnvironment environment){
		BeanFactory factory=BeanFactory.getInstance();
		final Transaction transaction = factory.build(context,Transaction.class,transactionBuilder, data);
		
//		TODO 异步请求
		new Thread(){
			public void run() {
				viewContainer.dispatch(transaction, environment);
			};
		}.start();

	}

	@Override
	public void setViewContainer(BasicViewContainer viewContainer) {
		this.viewContainer=viewContainer;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
