package com.tentcoo.pineapple.core.dispatch.rendering;

import java.util.Map;
import java.util.Map.Entry;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.utils.FileUtil;

/**内部页面包渲染器,直接显示文件内容*/
public class InternalResourceRenderer extends BasicDelegatedFactoryObject implements IRenderer {
	String fileName;

	@Override
//		TODO 具体的实现不清楚() key由我们定
	public void render(Map<String,Object> model, Request request, Response response) {
//		model.get("")
//		找到本地的页面,进行读取, 读取成String , 设置给response的 message;
		Entry<String, Object> next = model.entrySet().iterator().next();
		Object value = next.getValue();
		String message = FileUtil.GetReadFile(PineApple.getContext(),"demo1.html");
		response.setMessage(message);
	}
}
