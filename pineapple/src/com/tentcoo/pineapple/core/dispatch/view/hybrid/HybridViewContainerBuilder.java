package com.tentcoo.pineapple.core.dispatch.view.hybrid;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.framework.ioc.BeanDependencyBuilder;
import com.tentcoo.pineapple.core.framework.ioc.ConfigAnnotationDrivenBuilder;
import com.tentcoo.pineapple.core.framework.ioc.FrameworkDependencyBuilder;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.jsbridge.BridgeWebView;


public class HybridViewContainerBuilder extends BeanDependencyBuilder{

	private static HybridViewContainerBuilder builder;

	public static HybridViewContainerBuilder getInstance() {
		if (builder == null) {
			builder = new HybridViewContainerBuilder();
		}

		return builder;
	}
	@Override
	public void build(BasicFactoryObject b, Object object) { //判断Object 是不是beanDecoration
		super.build(b, object);
		HybirdViewContainer bean = null;
		if(b instanceof HybirdViewContainer){
			bean = (HybirdViewContainer) b;
		}
		BridgeWebView bridgeWebView=(BridgeWebView) object;
		bean.setWebView(bridgeWebView);
	}
	
}
