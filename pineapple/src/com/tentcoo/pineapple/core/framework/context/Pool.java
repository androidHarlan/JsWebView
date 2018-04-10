package com.tentcoo.pineapple.core.framework.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.framework.application.Application;
import com.tentcoo.pineapple.core.framework.template.annotation.Config;
import com.tentcoo.pineapple.utils.PoolQueue;
 
public class Pool extends  BasicDelegatedFactoryObject /*implements Pool */{
	Map<String, PoolQueue> map=new HashMap<String, PoolQueue>();
	private ArrayList<Subscribe> subscribes=new ArrayList<Subscribe>();
	public boolean isValid;
	@Config(id="ddd.pool.maxQueueNum")
	public int maxQueueNum;
	
	@Config(id="ddd.pool.maxSubrscribeNum")
	public int maxSubrscribeNum;				//
	
	@Config(id="ddd.pool.maxQueueSize") 
	public int maxQueueSize;        			//对象池中所能申请的对象数目上限 

//  private List<IHook>  allHooks=new ArrayList<IHook>();	 //钩子列表
    public void setOwner(Application application){
    	ContextPoolonDestroyHook hook=new ContextPoolonDestroyHook();
    	application.addHook(hook);
    }
    public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}
	public void setMaxQueueNum(int maxQueueNum) {
		this.maxQueueNum = maxQueueNum;
	}
	public void setMaxSubrscribeNum(int MaxSubrscribeNum) {
		this.maxQueueNum = MaxSubrscribeNum;
	}
	public void AddSubscribes(Subscribe subscribe){
		if(maxSubrscribeNum>subscribes.size()){
			
		}
		subscribes.add(subscribe);
	}

	@Override
	public void onCreated() {
	    ContextPoolSubscribeHook contextPoolSubscribeHook = new ContextPoolSubscribeHook();
		contextPoolSubscribeHook.setRule("addObject");
		contextPoolSubscribeHook.setPool(this);
		addHook(contextPoolSubscribeHook);
		ContextPoolonDestroyHook contextPoolonDestroyHook=new ContextPoolonDestroyHook();
		contextPoolonDestroyHook.setPool(this);
	}

	public void addObject(String ID,Object obj) {
		String key = getKey(obj);
		 PoolQueue poolQueue = map.get(key);
    	 if(poolQueue==null){
    		 poolQueue=new PoolQueue(maxQueueNum); 
    		 Map<String, Object> value=new HashMap<String, Object>();
        	 value.put(ID, obj);
        	 poolQueue.offer(value);
        	 map.put(key, poolQueue);
    	 }else{
    		 Map<String, Object> value=new HashMap<String, Object>();
        	 value.put(ID, obj);
        	 poolQueue.offer(value);
        	// map.put(key, poolQueue);
    	 }
	}
	

	public List<Subscribe> getSubscribes() {
		return subscribes;
	}

	public void matchSubscribersAndNotify(Object[] messages) {
		
		for(Subscribe sub:subscribes){
			String rule = sub.getSubscribeRule();
			if(matchSubscribeRule(messages[0].getClass().getName(), rule)){
				INtificationHandler handler = sub.getHandler();
				handler.handler(messages);
			}
		}
	}

	public boolean matchSubscribeRule(String message, String rule) {
		if(rule.equals(message)){
			return true;
		}
		return false;
	}


	public Object getBeanByNameAndType(String beanId, String beanType) {
		 PoolQueue poolQueue = map.get(beanType);
    	 if(poolQueue==null){
    		 return null;
    	 }else{
    		 Object object = poolQueue.getObject(beanId);
    		 return object;
    		
    	 }
	}
	
	

	public Object getBeanByType(String beanType) {
		 PoolQueue poolQueue = map.get(beanType);
    	 if(poolQueue==null){
    		 return null;
    	 }else{
    		 Object object = poolQueue.getTypeObject(beanType);
    		 return object;
    		
    	 }
	}
	
	public ArrayList<Object> getBeanByTypeList(String beanType) {
		PoolQueue poolQueue = map.get(beanType);
		if(poolQueue==null){
			return null;
		}else{
		ArrayList<Object> object = poolQueue.getTypeObjectList(beanType);
			return object;
			
		}
	}
	
	private String getKey(Object obj) {
		String key = obj.getClass().getName();
		if(key.contains("$")){
			key = key.substring(0, key.indexOf("$"));
		}
		return key;
	}
	
	@Override
	public String toString() {
		return "Pool [map=" + map + ", subscribes=" + subscribes + ", isValid="
				+ isValid + ", maxQueueNum=" + maxQueueNum
				+ ", MaxSubrscribeNum=" + maxSubrscribeNum + ", maxQueueSize="
				+ maxQueueSize + "]";
	}
}