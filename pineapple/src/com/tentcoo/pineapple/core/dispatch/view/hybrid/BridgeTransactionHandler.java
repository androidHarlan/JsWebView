package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Looper;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.Transaction;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.view.ITransactionHandler;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.BridgeResponseCallbackAdapter;
import com.tentcoo.pineapple.utils.Logger;

public class BridgeTransactionHandler extends BasicDelegatedFactoryObject implements ITransactionHandler{

	@Override
	public void transactionWillDispatch(Transaction transaction,
			TransactionEnvironment environment) {
		//TODO
		System.out.println("dispatcher执行前");
	}

	@Override
	public void transactionDidDispatch(Transaction transaction,
			TransactionEnvironment environment) {
		//TODO
		Response response = transaction.getResponse();
		BridgeResponseCallbackAdapter callbackAdapter = environment.getResponseCallbackAdapter();
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("id", transaction.getId());
			jsonObj.put("method",response.getUrl());
			jsonObj.put("param", response.getMessage());
			jsonObj.put("callback", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.i(jsonObj.toString());
		Logger.i(response);
//		callbackAdapter.callback(jsonObj.toString());
//		TODO HTTP请求
		runCallbackOnMainThread(callbackAdapter, jsonObj.toString());
		System.out.println("dispatcher执行后");
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/***
	 * 在主线程上运行callback
	 * @param callbackAdapter
	 * @param callback
	 */
	public void runCallbackOnMainThread(final BridgeResponseCallbackAdapter callbackAdapter,final String callback){
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				callbackAdapter.callback(callback);
			}
		});
	}
}
