package com.tentcoo.pineapple.core.framework.config;

import java.util.Map;
import java.util.Set;

import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.constant.Value;
import com.tentcoo.pineapple.core.common.factory.IBuilder;
import com.tentcoo.pineapple.core.framework.resource.IResourceResolver;
import com.tentcoo.pineapple.utils.Logger;
import com.tentcoo.pineapple.utils.ToolUtil;

public class ConfigManagerBuilder implements IBuilder<ConfigManager,Object>{
	private static  ConfigManagerBuilder instance;
	public static ConfigManagerBuilder getInstance(){
		if(instance==null){
			instance = new ConfigManagerBuilder();
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void build(ConfigManager configManager, Object basicConfigInfo) {
		try{
			Map<String,Object> configInfo = (Map<String, Object>) basicConfigInfo;
//			根据类名获取对象
			IResourceResolver defaultRootXmlResolver = createConfigResolverByName(Value.DEFAULT_CONFIG_RESOLVER);
			Map<String,Object> defaultConfigInfo = buildConfigInfo(Value.DEFAULT_CONFIG_INPUT,defaultRootXmlResolver);
			
			String customConfigInput = String.valueOf(configInfo.get(Key.CUSTOM_CONFIG_INPUT));
			String customConfigResolverName = String.valueOf(configInfo.get(Key.CUSTOM_CONFIG_RESOLVER));
//			根据类名获取对象
			IResourceResolver abstractConfigResolver = createConfigResolverByName(customConfigResolverName);
			Map<String,Object> customConfigInfo = buildConfigInfo(customConfigInput, abstractConfigResolver);
//			合并上边两步得到的ConfigInfo，然后将结果设置到ConfigManager中
			Map<String,Object> merge = mergeConfigInfo(defaultConfigInfo, customConfigInfo);
			configManager.resetConfigInfo(merge);
			Logger.i("合并后的Config---"+merge);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Map<String, Object> buildConfigInfo(String configString,IResourceResolver abstractConfigResolver){
		return (Map<String, Object>) abstractConfigResolver.resolve(configString);
	}
	
	/***
	 * 合并上边两步得到的ConfigInfo
	 * @param base
	 * @param update
	 * @return
	 */
	public Map<String, Object> mergeConfigInfo(Map<String, Object> base,Map<String, Object> update){
		mergeConfigInfo(null,base,update);
		return base;
	}
	
	/***
	 * 合并上边两步得到的ConfigInfo，如果value中同时存在Map，则递归
	 * @param originalMap 这里其实可以删除,这里没有用到
	 * @param base		     主Map
	 * @param update	     副Map
	 * @return
	 */
	public Map<String, Object> mergeConfigInfo(Map<String,Object> originalMap,Map<String, Object> base,Map<String, Object> update){
		try{
			Set<String> updateKeySet = update.keySet();
			for (String updateKey : updateKeySet) {
				if(base.containsKey(updateKey)){		//1、如果两个Map存在相同的key
					String baseAttributeType = ToolUtil.checkAttribute(base.get(updateKey));  		//检查Object类型，
					String updateAttributeType = ToolUtil.checkAttribute(update.get(updateKey)); 	//检查Object类型
					
					if(baseAttributeType.equals(updateAttributeType)){								//1_1、如果两者的类型是否相同
						if(baseAttributeType.equals("MAP")&&updateAttributeType.equals("MAP")){ 	//如果两者都是MAP
							mergeConfigInfo(base,(Map<String, Object>)base.get(updateKey), (Map<String, Object>)update.get(updateKey));
						}else{	//如果不是MAP的话就直接 覆盖
							base.put(updateKey, update.get(updateKey));
						}
					}else if(baseAttributeType.equals("MAP")||updateAttributeType.equals("MAP")){	//1_2、如果两者只有一个是Map 抛出异常
						throw new Exception("");
					}else{																			//1_3、其他情况，直接覆盖
						base.put(updateKey, update.get(updateKey));		
					}
				}else{								
					base.put(updateKey,update.get(updateKey));
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return originalMap;
		
	}

	public IResourceResolver createConfigResolverByName(String resolverName){
		try{			
			return (IResourceResolver) Class.forName(resolverName).newInstance();
		}catch(Exception e){
//			return null;
			e.printStackTrace();
			return new DefaultRootXmlConfigResolver();
		}
	}
}
