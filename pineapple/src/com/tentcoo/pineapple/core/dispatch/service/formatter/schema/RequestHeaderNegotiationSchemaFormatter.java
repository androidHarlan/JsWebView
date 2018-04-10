package com.tentcoo.pineapple.core.dispatch.service.formatter.schema;
import java.util.Map;
import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.constant.Value;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.framework.application.PineApple;


public class RequestHeaderNegotiationSchemaFormatter extends BasicDelegatedFactoryObject implements IServiceSchemaFormatter {
	
	private IServiceSchemaFormatter defaultFormatter=getDefaultFormatter();
	@Override
	public ResponseSchema format(Request request,Object returnD) {
		Map<String, Object> header = request.getHeader();
		if(header!=null){
		String requestFormatter = (String)header.get("request_formatter");
		if(Value.SCHEMA_NAME_JSON.equals(requestFormatter)){   //JSON
			defaultFormatter = BasicFactory.getInstance().build(getContext(), JsonSchemaFormatter.class);
		}else if(Value.SCHEMA_NAME_OBJECT.equals(requestFormatter)){	//OBJECT
			defaultFormatter = BasicFactory.getInstance().build(getContext(), DirectObjectSchemaFormatter.class);
		}
		}
		ResponseSchema schema =null;
		if(defaultFormatter!=null){
			schema = defaultFormatter.format(request,returnD);
		}
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

	private IServiceSchemaFormatter getDefaultFormatter(){
		return BasicFactory.getInstance().build(PineApple.getContext(), JsonSchemaFormatter.class);
	}
}
