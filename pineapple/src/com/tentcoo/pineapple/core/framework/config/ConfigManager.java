package com.tentcoo.pineapple.core.framework.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tentcoo.pineapple.core.common.bean.IBean;
import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.core.framework.template.annotation.Config;
import com.tentcoo.pineapple.utils.Logger;
import com.tentcoo.pineapple.utils.ToolUtil;




public class ConfigManager extends BasicDelegatedFactoryObject{
	
	@Autowired
	private IConfigResolver rootResolver;
	
	public Map<String, Object> configInfo;
	
	public BasicFactory factory;


	/***
	 * 通过ConfigManager.getValue(id)方法获取Config项的值。对于多级别的配置项，可以通过点号（.）来表示。
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getValue(String id){
		Object value = null;
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.putAll(configInfo);
		value = configMap;
		
		String[] paths = id.split("\\.");
		if(paths!=null&&paths.length>0){
			for (String path : paths) {
				if(value instanceof Map){
					value = ((Map<String, Object>)value).get(path);
				}else{
					Logger.e("不存在下一级Map的Config\\"+id+"--"+path);
					throw new PAException("不存在下一级Map");
				}
			}
		}
		return value;
	}


	public void resetConfigInfo(Map<String, Object> configInfo){
		this.configInfo = configInfo;
	}
	
}
