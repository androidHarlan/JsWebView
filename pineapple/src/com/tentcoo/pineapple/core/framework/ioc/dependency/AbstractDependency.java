package com.tentcoo.pineapple.core.framework.ioc.dependency;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;

public abstract class AbstractDependency extends BasicFactoryObject {
	public abstract AbstractBeanDefinition resolveBeanDefinition();
}
