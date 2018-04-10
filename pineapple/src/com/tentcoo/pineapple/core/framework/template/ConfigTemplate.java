package com.tentcoo.pineapple.core.framework.template;

import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.config.ConfigManager;
import com.tentcoo.pineapple.utils.Logger;

public class ConfigTemplate {
	private static ConfigTemplate configTemplate;
	public  static synchronized ConfigTemplate getInstance() {
		if(configTemplate==null){
			configTemplate=new ConfigTemplate();
		}
		return configTemplate;
	}
	/**根据key获取配置值，支持多级的key参数，如root.a.b*/
	public Object getConfigValueByKey(String key){
		Logger.d("获取config的key为"+key);
		Object value = getConfigManager().getValue(key);
		return value;
	}
	/**获取配置管理器对象*/
	public ConfigManager getConfigManager(){
		ConfigManager configManager = PineApple.getApplication().getConfigManager();
		return configManager;
	}
}
