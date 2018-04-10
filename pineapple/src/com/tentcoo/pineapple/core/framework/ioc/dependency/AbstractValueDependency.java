package com.tentcoo.pineapple.core.framework.ioc.dependency;

import java.lang.reflect.Type;



/**直接值得依赖*/
public abstract class AbstractValueDependency extends AbstractDependency {
 	public abstract Object resolveForValue(Type f);
}
