package com.tentcoo.pineapple.core.framework.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.dispatch.DispatchWorker;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerMapping;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.WebviewJsBridgeAdapter;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
import com.tentcoo.pineapple.core.framework.ioc.factory.FrameworkFactory;
import com.tentcoo.pineapple.core.framework.template.IocTemplate;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;

public class BeanDependencyBuilder extends
		ConfigAnnotationDrivenBuilder<BasicFactoryObject, Object> {

	private static BeanDependencyBuilder builder;

	public static BeanDependencyBuilder getInstance() {
		if (builder == null) {
			builder = new BeanDependencyBuilder();
		}

		return builder;
	}
	@Override
	public void build(BasicFactoryObject b, Object obj) {
		super.build(b, obj);
		FactoryBeanDefinition beanDefinition = null;
		if(b instanceof HandlerMapping){
			System.out.println();
		}
		if (obj instanceof FactoryBeanDefinition) {
			beanDefinition = (FactoryBeanDefinition) obj;
		}
		Field[] properties = b.getClass().getFields();
		for (Field f : properties) {
			Object bean = null;
			if("transactionHandlers".equals(f.getName())){
				System.out.println();
			}
			// 1、如果已经在decoration中定义了依赖，则使用decoration中定义的依赖来注入
			if (beanDefinition != null && beanDefinition.getDependency(f.getName())!=null) {
				AbstractDependency dependency = beanDefinition.getDependency(f.getName());
				if (dependency != null) {
					Type genericType = f.getGenericType();
					bean = Convert.getInstance().getConvert(genericType,dependency);
				}
			}
			// 2、根据注解注入
			else if (f.getAnnotation(Autowired.class) != null) {
				Autowired autowired = f.getAnnotation(Autowired.class);
				String beanId = autowired.id();
				Class beanType = getType(f);
				if(beanType == WebviewJsBridgeAdapter.class){
					System.out.println("-----------");
				}
				BasicBeanDecorationFilter beanDecorationFilter = FrameworkFactory.getInstance().build(b.getContext(),BasicBeanDecorationFilter.class, null, null);
				beanDecorationFilter.setBeanId(beanId);
				beanDecorationFilter.setBeanType(beanType);
				beanDecorationFilter.setMatchSubtype(false);
				List<Object> searchBean = IocTemplate.getInstance().searchBean(beanDecorationFilter, 1);
				if (searchBean != null && searchBean.size() > 0) {
					bean = searchBean.get(0);
				}
			}
			
			try {
				if(bean!=null){
					f.set(b, bean);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	private Class getType(Field f) {
		Autowired autowired = f.getAnnotation(Autowired.class);
		Class<?> clazz = autowired.clazz();
		
		if (clazz != Object.class) {
			if (!clazz.isInterface()) {
				return clazz;
			}
		} else {
			Class<?> type = f.getType();
			if (!type.isInterface()) {
				return type;
			}
		}
		return null;
	}

}
