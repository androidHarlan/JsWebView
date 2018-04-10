package com.tentcoo.pineapple.core.framework.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

	String id();//value是Annotation的默认属性
}