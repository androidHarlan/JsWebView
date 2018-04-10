//package com.tentcoo.pineapple.core.framework.ioc.dependency.value;
//
//import java.lang.reflect.Type;
//
//import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractValueDependency;
//import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
//
//
///**
// * 数值、字符串等简单值的依赖
// */
//public class SimpleValueDependency extends AbstractValueDependency {
//	String value;
//	@Override
//	public Object resolveForValue(Type f) {
//		Object convert = Convert.getInstance().getConvert(f, value);
//		return convert;
//	}
//	
//	public String getValue() {
//		return value;
//	}
//	public void setValue(String value) {
//		this.value = value;
//	}
//
//
//	@Override
//	public String toString() {
//		return "SimpleValueDependency [value=" + value + "]";
//	}
//	
//	
//}
