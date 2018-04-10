package com.tentcoo.pineapple.core.dispatch.rendering;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;



/**
 *  解析schemaName="JSON:(jsonTemplate)"的schema，会返回JsonRenderer
 */
public class JsonResolver extends BasicDelegatedFactoryObject implements IRendererResolver {
	@Override
	public boolean canSupport(String schemaName) {
		if(schemaName.startsWith("json:")){
			return true;
		}
		return false;
	}

	@Override
	public IRenderer resolveSchemaName(String schemaName) {
		JsonRenderer jsonRenderer = new JsonRenderer();
		return jsonRenderer;
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
		schema.setRenderer(renderer);
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
