package com.tentcoo.pineapple.core.dispatch.mapping;

import java.util.List;

import com.tentcoo.pineapple.core.common.bean.IComponent;
import com.tentcoo.pineapple.core.common.bean.ICore;

/**导出器,用于将服务导出为handler*/
public interface IExporter extends ICore{
	/**判断该exporter是否支持改component*/
	boolean support(IComponent component);		
	/**将commponent导出为handlerRuleMapping队列*/
	List<HandlerRuleMapping> export(IComponent component);
}
