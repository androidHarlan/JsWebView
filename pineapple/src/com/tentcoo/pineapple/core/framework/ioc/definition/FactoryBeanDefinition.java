package com.tentcoo.pineapple.core.framework.ioc.definition;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;

/**对象类的bean的定义*/
public class FactoryBeanDefinition extends AbstractBeanDefinition {
	Map<String,AbstractDependency> dependencies;   //<属性名，依赖>  //TODO 子类的属性
	Object prototype;		//原型对象，基于该对象进行构造		//TODO 子类的属性
	
	/****
	 * 合并dependency
	 * @param d
	 */
	public void mergeDependencies(Map<String,AbstractDependency> d){
		if(dependencies==null){
			dependencies  =new HashMap<String, AbstractDependency>();
		}
		dependencies.putAll(d);
	}


	public AbstractDependency getDependency(String propertyName){
		AbstractDependency basicDependency = null;
		if(dependencies!=null){
			basicDependency = dependencies.get(propertyName);
		}
		return basicDependency;
	}
	
	public Map<String, AbstractDependency> getDependencies() {
		return dependencies;
	}
	public void setDependencies(Map<String, AbstractDependency> dependencies) {
		this.dependencies = dependencies;
	}
	@Override
	public Object createObject() {
		Object bean = BeanFactory.getInstance().build(getContext(), this.beanType, null, this);
		return bean;
	}
	public Object getPrototype() {
		return prototype;
	}
	public void setPrototype(Object prototype) {
		this.prototype = prototype;
	}


	@Override
	public String toString() {
		return "FactoryBeanDefinition [dependencies=" + dependencies
				+ ", prototype=" + prototype + ", beanId=" + beanId
				+ ", BeanType=" + beanType + ", nameSpace=" + nameSpace
				+ ", isSingleton=" + isSingleton + "]";
	}


	
	
	
	
	
}
