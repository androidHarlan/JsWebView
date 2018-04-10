package com.tentcoo.pineapple.core.dispatch.rendering;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;

/** 渲染器的解析器 */
public interface IRendererResolver extends ICore {

	public IRenderer resolve(ResponseSchema schema);

	public boolean canSupport(String schemaName);

	public IRenderer resolveSchemaName(String schemaName);
}
