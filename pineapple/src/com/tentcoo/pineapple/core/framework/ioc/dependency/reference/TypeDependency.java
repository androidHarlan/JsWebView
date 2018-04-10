package com.tentcoo.pineapple.core.framework.ioc.dependency.reference;

import java.util.Map;
import java.util.Set;

import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractReferenceDependency;
import com.tentcoo.pineapple.utils.Logger;
/** 基于类型的依赖 */
public class TypeDependency extends AbstractDependency {
	private Class type;


	@Override
	public AbstractBeanDefinition resolveBeanDefinition() {
		AbstractBeanDefinition decoration = null;
		Map<String, AbstractBeanDefinition> beanIdDecorationMap = PineApple.getApplication().getIocManager().getBeanIdDecorationMap();
		Set<String> keySet = beanIdDecorationMap.keySet();
		for (String key : keySet) {
			AbstractBeanDefinition beanDecoration = beanIdDecorationMap.get(key);
			if (type == beanDecoration.getBeanType()) {
				decoration = beanDecoration;
				break;
			}
		}
		if(decoration==null){
			Logger.e("TypeDependency找不到Type:"+type.getSimpleName()+"的BeanDecoration");
		}

		return decoration;
	}
	
	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TypeDependency [type=" + type + "]";
	}

	

}
