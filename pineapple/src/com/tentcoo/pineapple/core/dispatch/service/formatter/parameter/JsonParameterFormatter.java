package com.tentcoo.pineapple.core.dispatch.service.formatter.parameter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;

public class JsonParameterFormatter extends BasicDelegatedFactoryObject
		implements IServiceParameterFormatter {

	@Override
	public List<Object> format(Request request) {
		ArrayList<Object> formart = new ArrayList<Object>();
		try {
			String paramStr = request.getMessage();
			if (paramStr != null && !"".equals(paramStr)) {
				JSONArray array = new JSONArray(request.getMessage());

				int length = array.length();
				for (int i = 0; i < length; i++) {
					Object object = array.get(i);
					formart.add(object);

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return formart;
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
