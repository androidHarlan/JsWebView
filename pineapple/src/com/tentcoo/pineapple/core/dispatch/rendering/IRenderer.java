package com.tentcoo.pineapple.core.dispatch.rendering;

import java.util.Map;

import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;


/**
 * 响应数据的渲染器
 */
public interface IRenderer {
	/** 将模板和数据模型渲染到响应中 */
	public void render(Map<String,Object> model,Request request,Response response);
}
