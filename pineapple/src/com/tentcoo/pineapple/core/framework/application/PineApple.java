package com.tentcoo.pineapple.core.framework.application;

import java.util.Map;

import com.tentcoo.pineapple.core.framework.config.BasicConfigResolver;
import com.tentcoo.pineapple.core.framework.config.ConfigManager;
import com.tentcoo.pineapple.core.framework.config.ConfigManagerBuilder;
import com.tentcoo.pineapple.core.framework.ioc.factory.FrameworkFactory;
import com.tentcoo.pineapple.core.framework.resource.IResourceResolver;

import android.content.Context;
/***
 *  由于Application 代理不能存在静态方法，所以用一个类包装起来
 */
public class PineApple {
	
	public static Context context;
	
	public static Application getApplication(){
		return Application.instance;
	}
	
	
	public static Context getContext() {
		return context;
	}


	public static void setContext(Context c) {
		context = c;
	}

	/**
	 * 初始化 应用启动时调用
	 * @param basicConfigFileName assets配置文件路径
	 * @param context
	 */
	public static void Init(String basicConfigFileName, Context context) {
		setContext(context);
		ConfigManagerBuilder configManagerBuilder = ConfigManagerBuilder.getInstance();		//configManager的构造器
		ApplicationBuilder applicationBuilder = ApplicationBuilder.getInstance();			//Application的构造器
		FrameworkFactory factory = FrameworkFactory.getInstance();	//代理工厂
		IResourceResolver basicConfigResolver = BasicConfigResolver.getInstance();
		
		Map<String, Object> BasicConfigInfo = configManagerBuilder.buildConfigInfo(basicConfigFileName, basicConfigResolver);
		ConfigManager configManager = factory.build(context, ConfigManager.class,configManagerBuilder,BasicConfigInfo);
		Application.instance = factory.build(context, Application.class,applicationBuilder,configManager);
		
//		try {
//			Set<String> className = ClassUtils.getClassName(context,"com.tentcoo.jsbridgeapp.context");
//			FileUtil.copyFilesFassets(context, "xml",FileUtil.genrateVideoTempFilePath(context));
//			String fileStr = ToolUtil.readFile(FileUtil.genrateVideoTempFilePath(context) + "/textXml.xml");
//		} catch (IOException e) {
//			throw new PAException();
//		}
////		初始化ContextManager
//		BasicFactory factory = BasicFactory.getInstance();
//		contextManager = factory.build(context,ContextManager.class, null, null);
	}
	
}
