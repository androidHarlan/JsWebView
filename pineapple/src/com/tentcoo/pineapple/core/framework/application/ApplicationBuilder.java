package com.tentcoo.pineapple.core.framework.application;

import com.tentcoo.pineapple.core.framework.config.ConfigManager;
import com.tentcoo.pineapple.core.framework.ioc.FrameworkDependencyBuilder;

/**静态单例*/
public class ApplicationBuilder extends FrameworkDependencyBuilder<Application,ConfigManager>{

	private static  ApplicationBuilder instance;
	public static ApplicationBuilder getInstance(){
		if(instance==null){
			instance = new ApplicationBuilder();
		}
		return instance;
	}
	@Override
	public void build(Application application, ConfigManager configManger) {
		application.setConfigManger(configManger);
		Application.instance = application;
		super.build(application, configManger);		
	}
	
	
}
