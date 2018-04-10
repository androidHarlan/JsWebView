	package com.tentcoo.pineapple.core.framework.application;

import java.util.ArrayList;
import java.util.List;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicHook;
import com.tentcoo.pineapple.core.dispatch.initiator.BasicInitiator;
import com.tentcoo.pineapple.core.framework.config.ConfigManager;
import com.tentcoo.pineapple.core.framework.context.ContextManager;
import com.tentcoo.pineapple.core.framework.context.ContextPoolonDestroyHook;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.core.framework.ioc.IocManager;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
/**
 * app Manager是整个app的管理器，复制APP的初始化和销毁
 */
public class Application extends BasicDelegatedFactoryObject {
	/**初始值为false，framework是否初始化完成的标识符。*/
	public boolean isFrameworkReady;  
	
	/**这个configManager属性不是通过注解注入，而是在applicationBuilder中设进去*/
	public ConfigManager configManager; 
	
	/**通过注解注入*/
	@Autowired
	public ContextManager contextManager;
	
	/**通过注解注入*/
	@Autowired(id ="iocManager",clazz=IocManager.class)
	public IocManager iocManager;
	
	public static Application instance;
	public List<BasicInitiator> initiators = new ArrayList<BasicInitiator>();  //初始化顺序列表
	public List<Object>  handleList = new ArrayList<Object>();					 //已经初始化了的对象
	public ContextPoolonDestroyHook hook;
	private List<BasicHook> allHooks = new ArrayList<BasicHook>(); // 钩子列表

	@Override
	public void onCreated() {
		iocManager.loadBenas(getContext());
//		dispatchManager = BeanFactory.getInstance().build(getContext(), DispatchManager.class);
		Object bean=iocManager.getBeanById("dispatchManager");
		System.out.print(bean.toString());
		handleInitiators();  //在检查依赖关系的前提下，遍历inititor
		setFrameworkReady(true);  //到此，底层初始化完成
	}

	/**在检查依赖关系的前提下，遍历inititor*/
	public void handleInitiators() {
		while(true){
			int count = initiators.size();
			for(int i = 0 ;i< initiators.size();i++){
				BasicInitiator initiator = initiators.get(i);
				if(initiator.isClearedToGo(handleList)){		//
					initiator.init();
					initiators.remove(initiator);
					handleList.add(initiator.getToken());
					i--;
				}
			}
//			如果依赖对象列表数量为0，则跳出循环
			if(initiators.size()==0){break;}
//			如果经过一轮迭代，仍然没有任何initator被处理，说明存在永远无法处理的initiator。抛出异常。
			if(initiators.size()==count){ throw new PAException("存在无法处理的initiator");}
		}
	}

	public ContextManager getContextManager() {
		return contextManager;
	}
	public IocManager getIocManager(){
		return iocManager;
	}

	/**注册一个初始化器，在application构建完成后,就会得到执行*/
	public void registerInitiator(BasicInitiator initiator){
		initiators.add(initiator);
	}
	
	public void setFrameworkReady(boolean bool){
		isFrameworkReady = bool;
	}
	public void setIocManager(IocManager iocManager){
		this.iocManager = iocManager;
	}
	public void setConfigManger(ConfigManager configManager){
		this.configManager = configManager;
	}

	public  ConfigManager getConfigManager() {
		return configManager;
	}

	public  void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}
	
	
}
