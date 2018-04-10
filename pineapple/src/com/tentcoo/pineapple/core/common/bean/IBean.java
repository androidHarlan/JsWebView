package com.tentcoo.pineapple.core.common.bean;

public interface IBean {
	 String getId();	 //获取这个bean在对象池中的Id
	 
	 boolean isSingleton();	//这个bean是否为单例
}
