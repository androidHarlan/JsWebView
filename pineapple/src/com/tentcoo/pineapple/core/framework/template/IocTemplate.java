package com.tentcoo.pineapple.core.framework.template;

import java.util.List;

import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.IBeanDecorationFilter;
import com.tentcoo.pineapple.core.framework.ioc.IocManager;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.utils.Logger;

/** 静态单例，直接在代码运行过程中通过getInstantce的方法获取 */
public class IocTemplate {
	public static IocTemplate instance;

	private IocTemplate() {
	}

	public static IocTemplate getInstance() {
		if (instance == null) {
			instance = new IocTemplate();
		}
		return instance;
	}

	public Object getBean(AbstractBeanDefinition beanDecoration) {
		Object bean = null;
		if (beanDecoration != null) {
			IocManager iocManager = PineApple.getApplication().getIocManager();
			bean = iocManager.getBean(beanDecoration);
		}
		return bean;
	}

	public List<Object> searchBean(IBeanDecorationFilter beanDecorationFilter,
			int count) {
		IocManager iocManager = PineApple.getApplication().getIocManager();
		List<Object> beans = null;
		if(iocManager!=null){
			beans = iocManager.searchBean(beanDecorationFilter, count);
		}else{ Logger.e("IocTemplate.searchBean.iocManager为空");}
		return beans;
	}
}
