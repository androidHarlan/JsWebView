package com.tentcoo.pineapple.core.framework.ioc.dependency.convert;

import java.lang.reflect.Type;

import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractReferenceDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractValueDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.DefinitionDependency;
import com.tentcoo.pineapple.core.framework.template.IocTemplate;

public class Convert {

	private static final String CHAR = "char";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";
	private static final String BOOLEAN = "boolean";

	private static final String CHAR_2 = "Character";
	private static final String BYTE_2 = "Byte";
	private static final String SHORT_2 = "Short";
	private static final String INT_2 = "Integer";
	private static final String LONG_2 = "Long";
	private static final String FLOAT_2 = "Float";
	private static final String DOUBLE_2 = "Double";
	private static final String BOOLEAN_2 = "Boolean";

	private static final String STRING = "string";

	private static Convert instance;

	private Convert() {
	}

	public static Convert getInstance() {
		if (instance == null) {
			instance = new Convert();
		}
		return instance;
	}

	/**
	 * 解析dependency 成 实体对象
	 * 
	 * @param dependency
	 * @return
	 */
	public Object getConvert(Type f, AbstractDependency dependency) {
		Object bean = null;
		AbstractBeanDefinition abd = dependency.resolveBeanDefinition();
		abd.setType(f);
		bean = IocTemplate.getInstance().getBean(abd);
		// if (dependency instanceof AbstractReferenceDependency) {
		// AbstractReferenceDependency referenceDependency =
		// (AbstractReferenceDependency) dependency;
		// AbstractBeanDefinition beanDecoration =
		// referenceDependency.resolveForDecoration();
		// bean = IocTemplate.getInstance().getBean(beanDecoration);
		// } else if (dependency instanceof AbstractValueDependency) {
		// AbstractValueDependency valueDependency = (AbstractValueDependency)
		// dependency;
		// bean = valueDependency.resolveForValue(f);
		// }
		return bean;
	}

	/**
	 * 字符串转化成各种基本类型
	 * 
	 * @param f
	 * @param value
	 * @return
	 */
	public Object getConvert(Type f, String value) {
		Object obj = null;
		if (value == null) {
			return null;
		}
		String typeName =null;
		if(f!=null){
			typeName = getTypeName(f.toString());
		}
		if (CHAR.equalsIgnoreCase(typeName)
				|| CHAR_2.equalsIgnoreCase(typeName)) { // Char 类型
			obj = value.length() > 0 ? value.charAt(0) : null;
		} else if (BYTE.equalsIgnoreCase(typeName)
				|| BYTE_2.equalsIgnoreCase(typeName)) { // byte 类型
			obj = Byte.valueOf(value);
		} else if (SHORT.equalsIgnoreCase(typeName)
				|| SHORT_2.equalsIgnoreCase(typeName)) { // short类型
			obj = Short.valueOf(value);
		} else if (INT.equalsIgnoreCase(typeName)
				|| INT_2.equalsIgnoreCase(typeName)) { // int 类型
			obj = Integer.valueOf(value);
		} else if (LONG.equalsIgnoreCase(typeName)
				|| LONG_2.equalsIgnoreCase(typeName)) { // long 类型
			obj = Long.valueOf(value);
		} else if (FLOAT.equalsIgnoreCase(typeName)
				|| FLOAT_2.equalsIgnoreCase(typeName)) { // float类型
			obj = Float.valueOf(value);
		} else if (DOUBLE.equalsIgnoreCase(typeName)
				|| DOUBLE_2.equalsIgnoreCase(typeName)) { // double类型的
			obj = Double.valueOf(value);
		} else if (BOOLEAN.equalsIgnoreCase(typeName)
				|| BOOLEAN_2.equalsIgnoreCase(typeName)) { // boolean
			obj = ("true".equalsIgnoreCase(value) || "1"
					.equalsIgnoreCase(value)) ? true : false;
		} else if (STRING.equalsIgnoreCase(typeName)) {
			obj = value;
		} else {
			obj = value;
		}
		return obj;
	}

	public static String getTypeName(String path) {
		if (path != null) {
			int indexOf = path.indexOf("<");
			if (indexOf != -1) {
				path = path.substring(0, indexOf);
			}
			path = path.substring(path.lastIndexOf(".") + 1);
		}
		return path;
	}
}
