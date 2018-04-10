package com.tentcoo.pineapple.core.framework.ioc.dependency.reference;

import java.util.Map;

import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.utils.Logger;

/** 基于id和类型的依赖 */
public class IdAndTypeDependency extends AbstractDependency {
	private String id;
	private Class type;

	@Override
	public AbstractBeanDefinition resolveBeanDefinition() {
		Map<String, AbstractBeanDefinition> beanIdDecorationMap = PineApple.getApplication().getIocManager().getBeanIdDecorationMap();
		AbstractBeanDefinition beanDecoration = beanIdDecorationMap.get(id);

		if (beanDecoration != null) {
			if (type == beanDecoration.getBeanType()) {
				Logger.e("IdAndTypeDependcy的Id相同，type不同");
			}
		}else{
			Logger.e("找不到id:"+id+",type"+type.getSimpleName()+"的beanDecoration");
		}
		return beanDecoration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "IdAndTypeDependency [id=" + id + ", type=" + type + "]";
	}

	

}
