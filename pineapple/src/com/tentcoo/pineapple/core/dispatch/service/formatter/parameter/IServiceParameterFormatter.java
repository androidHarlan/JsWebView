package com.tentcoo.pineapple.core.dispatch.service.formatter.parameter;

import java.util.List;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.Request;

public interface IServiceParameterFormatter extends ICore {
	public List<Object> format(Request request);
}
