/*
package com.tentcoo.pineapple.core.dispatch.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.mapping.IHandler;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.IServiceParameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.RuquestHeaderNegotiationPamameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.IServiceSchemaFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.RequestHeaderNegotiationSchemaFormatter;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;

public class HttpHandler extends BasicFactoryObject implements IHandler {
	// 这里有两个实现类，暂时用这个
	@Autowired(clazz = RuquestHeaderNegotiationPamameterFormatter.class)
	public IServiceParameterFormatter parameterFormatter;

	// 这里有两个实现类，暂时用这个
	@Autowired(clazz = RequestHeaderNegotiationSchemaFormatter.class)
	public IServiceSchemaFormatter schemaFormatter;

	@Override
	public ResponseSchema handle(Request request, Response response,TransactionEnvironment environment) {
		String url = request.getUrl();   //请求链接
		String message = request.getMessage();
		Map<String, String> params=new HashMap<String, String>();
		try {
			JSONObject object=new JSONObject(message);
			Iterator iterator = object.keys();
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				String value = object.get(key)+"";
				params.put(key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String post = RequestHttpUtils.RequestHttps(url, params);
		ResponseSchema schema=BeanFactory.getInstance().build(getContext(), ResponseSchema.class, null, null);
		schema.setSchemaName("json:");
		Map<String,Object> modes=new HashMap<String, Object>();
		modes.put("returnValue", post);
		schema.setModel(modes);
		return schema;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
*/
