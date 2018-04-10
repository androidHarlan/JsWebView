package com.tentcoo.pineapple.core.dispatch.view;

import java.util.List;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.DispatchWorker;
import com.tentcoo.pineapple.core.dispatch.Transaction;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.BridgeTransactionHandler;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;


public  class BasicViewContainer extends BasicFactoryObject implements IDispatcher{
	@Autowired(id="dispatchWorker")
	public DispatchWorker dispatchWorker;
//	@Autowired(ID="AbstractTransactionHandler")//TODO
	@Autowired(id="transactionHandlers")
	public List<ITransactionHandler> transactionHandlers;
	
	@Override
	public void dispatch(Transaction transaction,TransactionEnvironment environment) {
		Logger.i("当前线程："+Thread.currentThread().getName());
		ITransactionHandler transactionHandler = environment.getTransactionHandler();
		transactionHandler.transactionWillDispatch(transaction, environment);		//dispatch执行前
		if(dispatchWorker!=null){
			dispatchWorker.dispatch(transaction, environment);							//执行
		}else{
			Logger.e("BasicViewContainer.DispatchWorker为空");
		}
		transactionHandler.transactionDidDispatch(transaction, environment);		//dispatch执行后
	}
 	
 	public ITransactionHandler getTransactionHandlerByType(Class clazz) {
 		if(transactionHandlers!=null){
		for (ITransactionHandler transactionHandler : transactionHandlers) {
			if(transactionHandler!=null){
			if (transactionHandler.getClass() == clazz) {
				return transactionHandler;
			}
			}else{
				Logger.e("集合中的transaction为null");
			}
		}
 		}else{
 			Logger.e("viewContainer.transactionHandlers为空！");
 		}
		return new BridgeTransactionHandler();
	}	
}
