package com.tentcoo.pineapple.core.dispatch.service.formatter.schema;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;

public interface IServiceSchemaFormatter extends ICore {
	ResponseSchema format(Request request,Object returnData);
}
