package com.tentcoo.pineapple.core.dispatch;

import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.view.ITransactionHandler;

/**代表向底层发出一次请求，底层对该请求进行响应的过程*/
public class Transaction extends BasicFactoryObject{
	private String id;
	
	private Request request;
	
	private Response response;
	
	private ITransactionHandler transactionHandler;
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public void setTransactionHandler(ITransactionHandler transactionHandler){
		
	}

	public ITransactionHandler getTransactionHandler() {
		return transactionHandler;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void onDestroyed() {
		
	}

	

}
