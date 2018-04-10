package com.tentcoo.pineapple.core.framework.ioc.dependency;

import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;

public class DefinitionDependency extends AbstractDependency {
	public AbstractBeanDefinition beanDefinition;

	@Override
	public AbstractBeanDefinition resolveBeanDefinition() {
		return beanDefinition;
	}
	
	public AbstractBeanDefinition getBeanDefinition() {
		return beanDefinition;
	}

	public void setBeanDefinition(AbstractBeanDefinition beanDefinition) {
		this.beanDefinition = beanDefinition;
	}

	
	
}
