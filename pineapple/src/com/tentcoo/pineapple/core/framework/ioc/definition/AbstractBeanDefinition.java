package com.tentcoo.pineapple.core.framework.ioc.definition;

import java.lang.reflect.Type;

import com.tentcoo.pineapple.core.common.constant.ID;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;

/**获取这个definition所代表的对象*/
public abstract class AbstractBeanDefinition extends BasicFactoryObject{
	
	public Type type;         //用于判断List<T>
	
	public String beanId;			//bean的id			//不能为空
	public Class beanType;			//bean的类			//不能为空
	public String nameSpace = ID.IOC_POOL;		//该bean所属的容器的命名空间,默认Io
	public boolean isSingleton = true;		//默认为true，是否为单例
	
	public abstract Object createObject();	
	
	public void setBeanType(Class type){
		this.beanType = type;
	}
	public void setBeanId(String id){
		this.beanId = id;
	}

	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getBeanId() {
		return beanId;
	}
	public Class getBeanType() {
		return beanType;
	}
	
	public boolean isSingleton() {
		return isSingleton;
	}
	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
}
