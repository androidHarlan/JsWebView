package com.tentcoo.pineapple.core.framework.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tentcoo.pineapple.core.common.constant.ID;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;

public class ContextManager extends BasicDelegatedFactoryObject{
	public Map<String, Pool> poolManager=new HashMap<String, Pool>();
	@Autowired
	public Pool appPool;	//自动注入，非单例 - -! (自动注入时通过factory创建)
	@Autowired
	public Pool iocPool;	//自动注入，非单例 - -! (自动注入时通过factory创建)

	@Override
	public void onCreated() {
		poolManager.put(ID.APPLICATION_POOL, appPool);
		poolManager.put(ID.IOC_POOL, iocPool);
	}
//	public void addPool(Pool pool,BasicDelegatedFactoryObject application){
//		this.appPool=pool;
//		poolManager.put(ID.APPLICATION_POOL, pool);
//		ContextPoolonDestroyHook poolonDestroyHook=new ContextPoolonDestroyHook();
//		poolonDestroyHook.setPool(pool);
//		application.addHook(poolonDestroyHook);
//	}
	public  void   addObject2Pool(String poolId,String key,Object bean){
		if(poolId.equals(ID.APPLICATION_POOL)){
			appPool.addObject(key, bean);	
		}else if(poolId.equals(ID.IOC_POOL)){
			iocPool.addObject(key, bean);
		}
		
	}
	public Object getBeanByType(String poolId,String beanType){
		Pool poolById = getPoolById(poolId);
		Object obj=null ;
		if(poolById!=null){
			obj= poolById.getBeanByType(beanType);
			Logger.d("对象池中的对象："+obj);
		}
		return obj;
	}
	public ArrayList<Object> getBeanByTypeList(String poolId,String beanType){
		Pool poolById = getPoolById(poolId);
		ArrayList<Object> objs = new ArrayList<Object>();
		if(poolById!=null){
			objs= poolById.getBeanByTypeList(beanType);
			Logger.d("对象池中的对象list："+objs);
		}
		return objs;
	}
	public Object getBeanByNameAndType(String poolId,String beanId,String beanType){
		Pool poolById = getPoolById(poolId);
		Object obj=null ;
		if(poolById!=null){
			obj= poolById.getBeanByNameAndType(beanId, beanType);
			Logger.d("对象池中的对象："+obj);
		}
		return obj;
	}
	
	/***
	 * 根据id获取对象池 （目前有两个对象池）
	 * @param id
	 * @return
	 */
	public Pool getPoolById(String id){
		Pool p = poolManager.get(id);
		return p;
	}

}
