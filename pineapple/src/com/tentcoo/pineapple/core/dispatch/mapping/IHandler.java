package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;

/**处理器（控制器）*/
public interface IHandler extends ICore {
	public ResponseSchema handle(Request request,Response response,TransactionEnvironment environment);
}
