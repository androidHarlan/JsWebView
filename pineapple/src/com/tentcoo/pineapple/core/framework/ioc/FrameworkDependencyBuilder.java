package com.tentcoo.pineapple.core.framework.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;

import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.core.framework.ioc.factory.FrameworkFactory;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;

/**
 * 该builder在扫描到需要自动注入的属性时，就会自动通过basicFactory和FrameworkDependencyBuilder来创建新的依赖对象
 */
public class FrameworkDependencyBuilder<T extends BasicFactoryObject, O> extends ConfigAnnotationDrivenBuilder<T, O> {

	@SuppressWarnings("rawtypes")
	@Override
	public void build(T bean, O object) {
		super.build(bean, object);
		try {
			Class beanClazz = bean.getClass();
			// 拿到所有Field
			Field[] declaredFields = beanClazz.getFields();
			for (Field field : declaredFields) {
				// 获取Field上的注解
				field.setAccessible(true);
				Autowired annotation = field.getAnnotation(Autowired.class);
				if (annotation != null) {
					Class<?> type = field.getType(); // 属性的类型
					// 获取注解值
					String id = annotation.id(); // beanID
					boolean mode = annotation.mode();
					Class<?> clazz = annotation.clazz(); // 实际上的属性类型
					Object obj  = getBean(bean.getContext(), field, type, id,mode, clazz);
					field.set(bean, obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PAException(bean.getClass().getName()
					.substring(bean.getClass().getName().lastIndexOf(".") + 1)
					+ "--注解失败:----->" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Object getBean(Context context, Field field, Class type,String beanId, boolean mode, Class clazz) {
		Object obj = null;
		BasicFactory factory = null;
		Class classType = getClassType(type, clazz);
		if (obj == null && BasicFactoryObject.class.isAssignableFrom(classType)) { 
			if (factory == null) {
				factory = FrameworkFactory.getInstance();
				obj = factory.build(context, classType, null, null);
			}
		}
		if (obj == null) {
			if (mode) {
				try {
					Method method = type.getMethod("getInstance", new  Class[ 0 ]);
					obj = method.invoke(null, new  Object[]{});
				} catch (Exception e) {
					e.printStackTrace();
				}
				return obj;
			}
			try {
				obj = classType.newInstance();
			} catch (Exception e) {
				throw new PAException("-------创建对象失败");
			}

			
		}else{
			Logger.i("工厂创建成功"+obj.getClass().getName());
		}
		return obj;
	}

	/** 判断实例化的class类型 */
	private Class getClassType(Class type, Class clazz) {
		Class classType = null;
		if (clazz != null && clazz != Object.class) {
			classType = clazz;
		} else {
			classType = type;
		}
		return classType;
	}

}
