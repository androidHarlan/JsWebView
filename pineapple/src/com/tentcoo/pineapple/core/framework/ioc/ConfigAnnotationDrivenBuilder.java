package com.tentcoo.pineapple.core.framework.ioc;

import java.lang.reflect.Field;

import com.tentcoo.pineapple.core.common.factory.IBuilder;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.dependency.convert.Convert;
import com.tentcoo.pineapple.core.framework.template.ConfigTemplate;
import com.tentcoo.pineapple.core.framework.template.annotation.Config;
import com.tentcoo.pineapple.utils.Logger;

public class ConfigAnnotationDrivenBuilder<T, O> implements IBuilder<T, O> {
	private static ConfigAnnotationDrivenBuilder builder;

	public static ConfigAnnotationDrivenBuilder getInstance() {
		if (builder == null) {
			builder = new ConfigAnnotationDrivenBuilder();
		}

		return builder;
	}

	@Override
	public void build(T bean, O object) {
		try {
			Class clazz = bean.getClass();
			// 拿到所有Field
			Field[] declaredFields = clazz.getFields();
			for (Field field : declaredFields) {
				// 获取Field上的注解
				Config annotation = field.getAnnotation(Config.class);
				if (annotation != null) {
					// 获取注解值
					String id = annotation.id();
					
					Object object2 = ConfigTemplate.getInstance().getConfigValueByKey(id);
					if(object2 instanceof String){
						String value = null;
						value =  (String) object2;
						object2 = Convert.getInstance().getConvert(field.getGenericType(), value);
					}
					Logger.d("获取到的配置信息："+field.getType()+"--"+object2);
					if (object2 != null) {
						field.set(bean, object2);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
