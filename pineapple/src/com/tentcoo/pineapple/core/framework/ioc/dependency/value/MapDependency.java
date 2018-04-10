//package com.tentcoo.pineapple.core.framework.ioc.dependency.value;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractValueDependency;
//import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
//import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
//
///***
// * map数据依赖
// */
//public class MapDependency extends AbstractValueDependency {
//
//	public Map<String, AbstractDependency> dependencyMap = new HashMap<String, AbstractDependency>();
//
//	@Override
//	public Object resolveForValue(Type f) {
//		// 遍历dependencyMap
//		Map<String, Object> value = new HashMap<String, Object>();
//		Type tType = null;
//		if (f != null) {
//			String typeName = Convert.getInstance().getTypeName(f.toString());
//			if (f instanceof ParameterizedType) {
//				Type[] actualTypeArguments = ((ParameterizedType) f).getActualTypeArguments();
//				if (actualTypeArguments.length > 0) {
//					tType = actualTypeArguments[0];
//				}
//			}
//		}
//
//		Set<String> keySet = dependencyMap.keySet();
//		for (String key : keySet) {
//			AbstractDependency basicDependency = dependencyMap.get(key);
//			Object convert = Convert.getInstance().getConvert(tType,basicDependency);
//			value.put(key, convert);
//		}
//		return value;
//	}
//
//	public Map<String, AbstractDependency> getDependencyMap() {
//		return dependencyMap;
//	}
//
//	public void setDependencyMap(Map<String, AbstractDependency> dependencyMap) {
//		this.dependencyMap = dependencyMap;
//	}
//
//	@Override
//	public String toString() {
//		return "MapDependency [dependencyMap=" + dependencyMap + "]";
//	}
//
//}
