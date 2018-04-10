package com.tentcoo.pineapple.core.dispatch.service.formatter.schema;



import java.util.HashMap;
import java.util.Map;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;

public class JsonSchemaFormatter extends BasicDelegatedFactoryObject implements IServiceSchemaFormatter{

	@Override
	public ResponseSchema format(Request request,Object returnD) {
		ResponseSchema schema=BeanFactory.getInstance().build(getContext(), ResponseSchema.class, null, null);
		
		schema.setSchemaName("json:");
		Map<String,Object> modes=new HashMap<String, Object>();
		modes.put("returnValue", returnD);
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
