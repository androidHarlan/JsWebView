package com.tentcoo.pineapple.core.framework.context;

import java.lang.reflect.Field;

import android.content.Context;

import com.tentcoo.pineapple.core.common.constant.ID;
import com.tentcoo.pineapple.core.common.factory.IDependancyInjector;
import com.tentcoo.pineapple.core.framework.template.ContextTemplate;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;

public class AnnotainDrivenDependencyInjector implements IDependancyInjector{
	private static AnnotainDrivenDependencyInjector injector;

	public static AnnotainDrivenDependencyInjector getInstance() {
		if (injector == null) {
			injector = new AnnotainDrivenDependencyInjector();
		}

		return injector;
	}
	public <T> void inject(T bean,Context context)  {
		try {
			Class beanClazz = bean.getClass();
			// 拿到所有Field
			Field[] declaredFields = beanClazz.getFields();
			for (Field field : declaredFields) {
				// 获取Field上的注解
				Autowired annotation = field.getAnnotation(Autowired.class);
				if (annotation != null) {
					Class<?> type = field.getType();		//属性的类型
					// 获取注解值
					String id = annotation.id();			//beanID
					boolean mode = annotation.mode();
					Class<?> clazz = annotation.clazz();	//实际上的属性类型
					// 去对象池找
					ContextTemplate contextTemplate = new ContextTemplate();
					Object obj = contextTemplate.getBean(context,field, type, id,mode,clazz);
					field.set(bean, obj);
				}
			}
		} catch (Exception e) {
			 throw new PAException(bean.getClass().getName().substring(bean.getClass().getName().lastIndexOf(".")+1)+"--注解失败:----->"+e.getMessage());
		}
	}
}
