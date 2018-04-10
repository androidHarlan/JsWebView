//package com.tentcoo.pineapple.core.framework.ioc.dependency.value;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractValueDependency;
//import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
//import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
//
//
///***
// * 列表数据
// */
//public  class ListDependency extends AbstractValueDependency {
//
//	List<AbstractDependency> list = new ArrayList<AbstractDependency>();
//	@SuppressWarnings({ "unused", "static-access" })
//	@Override
//	public Object resolveForValue(Type f) {
//		List<Object> valueList = new ArrayList<Object>();
//
//		Type tType  = null;
//		String typeName = Convert.getInstance().getTypeName(f.toString());
//		if(f instanceof ParameterizedType){
//			Type[] actualTypeArguments = ((ParameterizedType)f).getActualTypeArguments();
//			if(actualTypeArguments.length>0){
//				tType = actualTypeArguments[0];
//			}
//		}
//		for(AbstractDependency dependency:list){
//			Object convert = Convert.getInstance().getConvert(tType,dependency);
//			valueList.add(convert);
//		}
//		return valueList;
//	}
//	public List<AbstractDependency> getList() {
//		return list;
//	}
//	public void setList(List<AbstractDependency> list) {
//		this.list = list;
//	}
//	@Override
//	public String toString() {
//		return "ListDependency [list=" + list + "]";
//	}	
//	
//	
//	
//	
//}
