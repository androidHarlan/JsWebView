package com.tentcoo.pineapple.core.dispatch.rendering;


import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;

/** 解析以internal：开头的schemaName,返回InternalResourceRenderer */
public class InternalResourceResolver extends BasicDelegatedFactoryObject implements IRendererResolver {

	@Override
	public boolean canSupport(String schemaName) {
		if (schemaName.startsWith("internal:")) {
			return true;
		}
		return false;
	}
	@Override
	public IRenderer resolveSchemaName(String schemaName) {
		return new InternalResourceRenderer();
	}
	@Override
	public IRenderer resolve(ResponseSchema schema) {
		IRenderer renderer = null;
		String schemaName = schema.getSchemaName();
		boolean canSupport = canSupport(schemaName);
		if(!canSupport){
			return null;
		}
		renderer = resolveSchemaName(schemaName);
		return renderer;
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
