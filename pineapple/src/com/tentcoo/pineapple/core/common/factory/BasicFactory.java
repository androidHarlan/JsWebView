package com.tentcoo.pineapple.core.common.factory;

import android.content.Context;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.common.factory.delegate.DelegateObjectInstantializer;
import com.tentcoo.pineapple.core.framework.context.AnnotainDrivenDependencyInjector;
import com.tentcoo.pineapple.core.framework.ioc.ConfigAnnotationDrivenBuilder;
import com.tentcoo.pineapple.utils.Logger;
/***
 * 静态单例 
 * 基本工厂，负责创建和销毁工厂对象
 */
public class BasicFactory {
	
	private static BasicFactory instance;
	private IInstantializer instantializer;		//生成器
	private IBuilder defualtBuilder = getDefaultBuilder();
	

	public static BasicFactory getInstance(){
		if(instance==null){
			instance = new BasicFactory();
		}
		return instance;
	}
	private final IDependancyInjector dependancyInjector = AnnotainDrivenDependencyInjector.getInstance();

	// 不同的类用不同的实例生成器
	public IInstantializer getInstantialzer(Class clazz) {
		if (BasicDelegatedFactoryObject.class.isAssignableFrom(clazz)&&false) {
			instantializer = DelegateObjectInstantializer.getInstance();
		} else {
			instantializer = DefaultInstantializer.getInstance();
		}
		return instantializer;
	}
	
	public void setInstantialzer(IInstantializer instantializer){
		this.instantializer = instantializer;
	}

	public IBuilder getDefaultBuilder(){
		return new ConfigAnnotationDrivenBuilder<Object,Object>();
	}
	public void setDefaultBuilder(IBuilder builder){
		this.defualtBuilder = builder;
	}
	
	// 默认的实例生成器
	public IDependancyInjector getDependancyInjector() {
		return dependancyInjector == null ? AnnotainDrivenDependencyInjector.getInstance() : dependancyInjector;
	}

	
	public <T> T build(Context context,Class<T> clazz){
		T build = build(context, clazz,null,null);
		return build;
	}
	/**
	 * 
	 * @param context	//上下文
	 * @param clazz		//工厂要创建的类
	 * @param builder	//
	 * @param obj		//
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T build(Context context,Class<T> clazz, IBuilder builder, Object obj) {
		T instance = null;
		instantializer = getInstantialzer(clazz);
		if (instantializer != null) {
			if(context==null){
				Logger.e("context为空");
			}
			T newInstance = instantializer.newInstance(context, clazz);	//创建实例对象
			instance =  newInstance;
		}
		IBuilder myBuilder = (builder == null) ? defualtBuilder : builder;
		if (instance != null && BasicFactoryObject.class.isAssignableFrom(clazz)) {
			if (instance instanceof BasicFactoryObject) {
				((BasicFactoryObject)instance).setContext(context);
			}
			
			if (myBuilder != null) {
				myBuilder.build(instance, obj);					//进行填充（初始化）
			}
			if (instance instanceof BasicFactoryObject) {
				((BasicFactoryObject)instance).onCreated();
			}
			
		}

		return instance;
	}

	public void destroy(BasicFactoryObject instance) {
		if (instance instanceof BasicFactoryObject) {
			instance.onDestroyed();
		}
		instantializer.returnInstance(instance);
	}
}
