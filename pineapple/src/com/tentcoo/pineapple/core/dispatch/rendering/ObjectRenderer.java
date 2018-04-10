package com.tentcoo.pineapple.core.dispatch.rendering;
import java.util.List;
import java.util.Map;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;

/** 将model里的第一个对象渲染成json数据格式 */
public class ObjectRenderer extends BasicDelegatedFactoryObject implements
		IRenderer {
	@Override
	public void render(Map<String, Object> model, Request request,
			Response response) {
		List<Object> object = request.getObject();
		response.setObject(object);
	}
}
