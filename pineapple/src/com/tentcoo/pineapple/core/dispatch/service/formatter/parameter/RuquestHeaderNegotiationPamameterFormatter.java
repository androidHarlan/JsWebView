package com.tentcoo.pineapple.core.dispatch.service.formatter.parameter;

import java.util.List;
import java.util.Map;

import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.constant.Value;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.framework.application.PineApple;

/**
 * 会根据请求头部的request_formatter字段自动选择上面的两个formatter jsonParameterFormatter
 * ->将request.message中的json数据解析成object数组 objectParameterForamtter
 * ->将request.object中的object数组直接取出 requestHeaderNegotiationFormatter
 * ->会根据请求头部的request_formatter字段自动选择上面的两个formatter钟的一个
 */
public class RuquestHeaderNegotiationPamameterFormatter extends
		BasicDelegatedFactoryObject implements IServiceParameterFormatter {
	/** 如果没有指定，则使用此默认的参数构造器 json */
	IServiceParameterFormatter defaultFormatter = new JsonParameterFormatter();

	@Override
	public List<Object> format(Request request) {
		Map<String, Object> header = request.getHeader();

		if (header != null) {
			String format = (String) header.get("request_formatter");
			// 特定格式的解析器
			if (Value.RESPONSE_FORMATTER_JSON.equals(format)) { // JSON
				defaultFormatter = BasicFactory.getInstance().build(getContext(), JsonParameterFormatter.class);
			} else if (Value.REQUEST_FORMATTER_OBJECT.equals(format)) { // OBJECT
				defaultFormatter = BasicFactory.getInstance().build(getContext(), DirectObjectParameterFormatter.class);
			}
		}
		List<Object> format2 = null;
		if (defaultFormatter != null) {
			format2 = defaultFormatter.format(request);
		}
		return format2;
	}

	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
	public IServiceParameterFormatter getDefaultFormatter() {
		return BasicFactory.getInstance().build(PineApple.getContext(), JsonParameterFormatter.class);
	}
}
