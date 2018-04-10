package com.tentcoo.jswebview.bean;

import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.mapping.IHandlerInterceptor;

public class MyHanderInterceter implements IHandlerInterceptor {

	@Override
	public boolean preHandle(Request request, Response response,
			TransactionEnvironment environment) {
		System.out.println("自己的Interceter-preHandle");
		return true;
	}

	@Override
	public void postHandle(Request request, Response response,
			ResponseSchema schema, TransactionEnvironment environment) {
		System.out.println("自己的Interceter-postHandle");

	}

}
