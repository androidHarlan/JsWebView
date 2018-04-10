package com.tentcoo.pineapple.core.framework.ioc.dependency.reference;

import java.util.Map;

import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractReferenceDependency;
import com.tentcoo.pineapple.utils.Logger;


/**基于id的依赖*/
public class IdDependency extends AbstractDependency {
	private String beanId;

	@Override
	public AbstractBeanDefinition resolveBeanDefinition() {
		Map<String, AbstractBeanDefinition> beanIdDecorationMap = PineApple.getApplication().getIocManager().getBeanIdDecorationMap();
		AbstractBeanDefinition beanDecoration = beanIdDecorationMap.get(beanId);
		if(beanDecoration==null){
			Logger.e("IdDependency找不到BeanId="+beanId+"的Decoration");
		}
		return beanDecoration;
	}
	
	public String getBeanId() {
		return beanId;
	}
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	@Override
	public String toString() {
		return "IdDependency [beanId=" + beanId + "]";
	}
	

	
	
}
