package com.tentcoo.pineapple.core.framework.template.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
	public String id() default "";//value是Annotation的默认属性
	public boolean mode() default false;
	public Class<?> clazz() default Object.class;
}