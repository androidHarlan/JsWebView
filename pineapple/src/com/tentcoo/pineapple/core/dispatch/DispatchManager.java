package com.tentcoo.pineapple.core.dispatch;


import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.initiator.DispatchManagerInitiator;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;

public class DispatchManager extends BasicDelegatedFactoryObject implements ICore {
	@Autowired(id="dispatchWorker")
	public DispatchWorker dispatchWorker;		//单例自动注入
	public DispatchWorker getDispacher(){
		return dispatchWorker;
	}
	@Autowired(mode=true)
	public DispatchManagerInitiator initiator = new DispatchManagerInitiator(); //自动注入，静态单例
	
	public BasicFactory getDispatchWorkerFactoryBean(){
		return null;
	}
	/**初始化，用于在bean环境准备好之后的初始化工作*/
	public void init(){
		Logger.d("--DispatchManager.init()");
		if(dispatchWorker!=null){
		dispatchWorker.init();
		}else{
			throw new PAException("DispatchWorker不能为null!");
		}
	}

	@Override
	public void onCreated() {
		Logger.d("------DispatcchManager.OnCreate()");
		initiator.setToken(this);
		initiator.setDispatchManager(this);
		PineApple.getApplication().registerInitiator(initiator);
	}
	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}
	
}
