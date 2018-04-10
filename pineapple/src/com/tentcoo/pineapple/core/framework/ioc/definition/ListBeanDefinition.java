package com.tentcoo.pineapple.core.framework.ioc.definition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
import com.tentcoo.pineapple.core.framework.ioc.dependency.reference.IdDependency;

public class ListBeanDefinition extends ValueBeanDefinition {
	public List<AbstractDependency> list;

	@Override
	public Object createObject() {
		List<Object> valueList = new ArrayList<Object>();

		Type tType = null;
		if (type != null) {
			String typeName = Convert.getInstance().getTypeName(type.toString());
			if (type instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
				if (actualTypeArguments.length > 0) {
					tType = actualTypeArguments[0];
				}
			}
		}
		for (AbstractDependency dependency : list) {
			Object convert = Convert.getInstance().getConvert(tType, dependency);
			valueList.add(convert);
		}
		return valueList;
	}

	public List<AbstractDependency> getList() {
		return list;
	}

	public void setList(List<AbstractDependency> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ListBeanDefinition [list=" + list + "]";
	}
}
