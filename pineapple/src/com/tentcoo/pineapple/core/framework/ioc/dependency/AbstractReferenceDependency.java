package com.tentcoo.pineapple.core.framework.ioc.dependency;

import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;

/***
 * 对另一个bean的引用的依赖
 */
public abstract class AbstractReferenceDependency extends AbstractDependency {
	public abstract AbstractBeanDefinition resolveForDecoration();
}
