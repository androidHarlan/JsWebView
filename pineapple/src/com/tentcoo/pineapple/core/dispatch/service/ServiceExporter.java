package com.tentcoo.pineapple.core.dispatch.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.common.bean.IComponent;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerRuleMapping;
import com.tentcoo.pineapple.core.dispatch.mapping.IExporter;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.template.annotation.Config;
import com.tentcoo.pineapple.core.framework.template.annotation.Mapping;
import com.tentcoo.pineapple.utils.Logger;
import com.tentcoo.pineapple.utils.StringUtils;

public class ServiceExporter extends BasicDelegatedFactoryObject implements IExporter {
//	TODO  用@Config 注入值
	@Config(id="serviceExporter.basicUrl")
	public String basicUrl;           //TODO basicUrl没有设置
	
	@Override
	public boolean support(IComponent component) {
//		支持的规则  //判断是否是实现了IService接口		
		if(component instanceof IService){
			return true;
		}
		return false;
	}
	@Override
	public List<HandlerRuleMapping> export(IComponent component) {
//		 将component转换成HandlerRuleMapping集合
		List<HandlerRuleMapping> mappings = new ArrayList<HandlerRuleMapping>();

		Class clazz = component.getClass();
		Method[] methods = clazz.getMethods();
		for(Method m:methods){
			Mapping mapping = m.getAnnotation(Mapping.class); 
			if(mapping!=null){
				String name = mapping.name();

				ServiceMethodHandler handler = BeanFactory.getInstance().build(PineApple.getContext(), ServiceMethodHandler.class,null,null);  
				handler.setMethodName(name);			// 这里的名字用注解的名字
				handler.setServiceBean(component);
				HandlerRuleMapping ruleMapping = BeanFactory.getInstance().build(PineApple.getContext(),HandlerRuleMapping.class,null,null);  
				
				ruleMapping.setRule(getRule(clazz,name));
				ruleMapping.setHandler(handler);
				Logger.i(ruleMapping);
				mappings.add(ruleMapping);
			}
		}
		
		return mappings;
	}
	
	/** basicUrl+类名+方法名 */
	private String getRule(Class clazz, String methodName) {
//		1、获取类名  （首字母大写,单单就类名就行，不用完整的路径）
		String className = getClassName(clazz);
//		2、方法名
		return basicUrl+className+"."+methodName;
	}
	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}

	/***
	 * 获取类名
	 * @param clazz
	 * @return
	 */
	public String getClassName(Class<?> clazz){
		String className = null;
//		Mapping mapping = (Mapping) clazz.getAnnotation(Mapping.class);
//		if(mapping!=null){
//			className=mapping.name();
//		}
		try {
			Object newInstance = clazz.newInstance();
			className=((IService)(newInstance)).getMappedName();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(className==null){
			className = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
		}
		return className;
	}

}
