package com.tentcoo.pineapple.core.framework.template;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;

import com.tentcoo.pineapple.core.common.constant.ID;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.context.ContextManager;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.utils.Logger;

/** 静态单例 */
public class ContextTemplate {
	private BasicFactory factory;
	private static ContextTemplate instance;

	public static ContextTemplate getInstance() {
		if (instance == null) {
			instance = new ContextTemplate();
		}
		return instance;
	}

	public Object getBeanByNameAndType(String poolId, String beanId,String beanType) {
		Logger.i("查找poolId:"+poolId+",beanId:"+beanId+",beanType:"+beanType);
		ContextManager contextManager = getContextManager();
		Object bean = contextManager.getBeanByNameAndType(poolId, beanId,beanType);
		return bean;
	}

	public Object getBeanType(String poolId, String beanType) {
		ContextManager contextManager = getContextManager();
		Object bean = contextManager.getBeanByType(poolId, beanType);
		return bean;

	}

	public ArrayList<Object> getBeanTypeList(String poolId, String beanType) {
		ContextManager contextManager = getContextManager();
		ArrayList<Object> beans = contextManager.getBeanByTypeList(poolId,
				beanType);
		return beans;
	}

	

	public ContextManager getContextManager() {
		return PineApple.getApplication().getContextManager();
	}

	public BasicFactory createNewFactoryBean(String factoryType) {
		Class<?> forName;
		try {
			forName = Class.forName(factoryType);
			Object factory = forName.newInstance();
			BasicFactory factoryBean = (BasicFactory) factory;
			return factoryBean;
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return null;

	}

	/***
	 * 
	 * @param field
	 *            属性
	 * @param type
	 *            属性类型
	 * @param beanId
	 *            beanid
	 * @param mode
	 *            是否是单例

	 * @return
	 * @throws Exception
	 */
	public Object getBean(Context context, Field field, Class type,
			String beanId, boolean mode, Class clazz) throws Exception {
		Object obj = null;
		// 1、对象池中找
		/*
		 * Object obj = getBeanByNameAndType(ID.APPLICATION_POOL, value,
		 * type.getName());
		 */
		if (obj == null) {
			// 2、 去bean定义中找
		}

		Class classType = getClassType(type, clazz);
		if (obj == null && BasicFactoryObject.class.isAssignableFrom(classType)) {	//object ==null && 是BaiscFactoryObject的子类，（如ArrayList 就不符合要求）
			// 3、对象池中找Factory
			/*
			 * factory = (BasicFactory) getBeanType(ID.APPLICATION_POOL,
			 * BasicFactory.class.getName());
			 */
			if (factory == null) {
				factory = BasicFactory.getInstance();
				/*
				 * ContextManager contextManager2 = getContextManager();
				 * contextManager2.addObject2Pool(ID.APPLICATION_POOL, value,
				 * factory);
				 */
				obj = factory.build(context, classType, null, null);
			}

		}
		if (obj == null) {
			if (mode) {
				try {
					Method method = type.getMethod("getInstance", new  Class[ 0 ]);
					obj = method.invoke(null, new  Object[]{});
					System.out.println(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return obj;
			}
			try {
				obj = classType.newInstance();
			} catch (Exception e) {
				throw new PAException("-------创建对象失败");
			}
			System.out.println(obj.getClass().getName());
			/*
			 * ContextManager contextManager2 = getContextManager();
			 * contextManager2.addObject2Pool(ID.APPLICATION_POOL, value, obj);
			 */
		}
		return obj;
	}

	public void addObjectToPool(String beanId,Object bean,String poolId){
		getContextManager().addObject2Pool(poolId, beanId, bean);
	}
	
	/**判断实例化的class类型*/
	private Class getClassType(Class type, Class clazz) {
		Class classType = null;
		if (clazz != null && clazz != Object.class) {
			classType = clazz;
		} else {
			classType = type;
		}
		return classType;
	}

}
