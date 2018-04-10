package com.tentcoo.pineapple.core.framework.ioc.definition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;

public class MapBeanDefinition extends ValueBeanDefinition {
	public Map<String, AbstractDependency> map;

	
	@Override
	public Object createObject() {
		// 遍历dependencyMap
				Map<String, Object> value = new HashMap<String, Object>();
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

				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					AbstractDependency basicDependency = map.get(key);
					Object convert = Convert.getInstance().getConvert(tType,basicDependency);
					value.put(key, convert);
				}
				return value;
	}
	
	public Map<String, AbstractDependency> getMap() {
		return map;
	}

	public void setMap(Map<String, AbstractDependency> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "MapBeanDefinition [map=" + map + "]";
	}


	
	
	
}
