package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;

/** 请求拦截器 */
public interface IHandlerInterceptor {
	/** 前置处理 */
	public boolean preHandle(Request request, Response response,TransactionEnvironment environment);

	/** 后置处理 */
	public void postHandle(Request request, Response response,ResponseSchema schema, TransactionEnvironment environment);
}
