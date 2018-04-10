package com.tentcoo.pineapple.core.framework.ioc.definition;

import java.lang.reflect.Type;

import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;

/**简单值的definition*/
public class SimpleValueBeanDefinition extends ValueBeanDefinition {
	public String value;   //序列化的值

	public String getValue() {
		return value;
	}


	@Override
	public Object createObject() {
		Object convert = Convert.getInstance().getConvert(type, value);
		return convert;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SimpleValueBeanDefinition [value=" + value + "]";
	}

	
}
