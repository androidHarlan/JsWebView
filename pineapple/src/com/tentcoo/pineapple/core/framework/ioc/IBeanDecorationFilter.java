package com.tentcoo.pineapple.core.framework.ioc;

import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;

public interface IBeanDecorationFilter {
	boolean onFilter(AbstractBeanDefinition beanDecoration);
	String getId();
	Class getBeanType();
	}
