package com.tentcoo.pineapple.core.framework.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * 实现了IService接口的对象专用，有这个注解的方法，会被导出器导出。
 * 		name参数用于配置service对象中的方法被映射出去之后的url路径。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
	String name();
}
