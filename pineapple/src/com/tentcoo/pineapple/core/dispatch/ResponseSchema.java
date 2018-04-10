package com.tentcoo.pineapple.core.dispatch;

import java.util.Map;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.rendering.IRenderer;
import com.tentcoo.pineapple.core.dispatch.rendering.IRendererResolver;
import com.tentcoo.pineapple.core.dispatch.rendering.JsonResolver;

public class ResponseSchema  extends BasicFactoryObject{
	
//	返回的schemaName有一定的规范
//	比如："internal:aaa"，此时应该由internalResourceResolver来解析并返回internalResourceRenderer
//	"json:bbb"，此时应该由jsonResolver来解析并返回jsonRenderer
	private String SchemaName;
	
	private Map<String,Object> model;
//	private Map<String,Object> model;
	private  IRenderer renderer;
	public IRendererResolver getRendererResolver(){
		return new JsonResolver();
	}
	
	public IRenderer getRenderer(){
		return renderer;
	}
	public void setRenderer(IRenderer renderer){
		this.renderer=renderer;
	}
	
	public Map<String,Object>  getModel(){
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	public String getSchemaName(){
		return SchemaName;
	}
	public void setSchemaName(String schemaName) {
		SchemaName = schemaName;
	}
}
