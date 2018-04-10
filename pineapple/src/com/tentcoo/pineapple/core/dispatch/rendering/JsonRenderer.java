package com.tentcoo.pineapple.core.dispatch.rendering;
import java.util.Map;
import java.util.Map.Entry;


import com.alibaba.fastjson.JSON;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;

/** 将model里的第一个对象渲染成json数据格式 */
public class JsonRenderer extends BasicDelegatedFactoryObject implements
		IRenderer {
	@Override
	public void render(Map<String, Object> model, Request request,
					   Response response) {
		// model 取第一个item 变成json， 设置给response 的message。
		Entry<String, Object> next = model.entrySet().iterator().next();
		Object value = next.getValue();
		String Message = JSON.toJSONString(value);
		response.setMessage(Message);
		response.setUrl(request.getUrl());
	}
}
