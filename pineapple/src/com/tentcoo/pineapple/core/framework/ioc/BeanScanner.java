package com.tentcoo.pineapple.core.framework.ioc;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tentcoo.pineapple.core.common.bean.IBean;
import com.tentcoo.pineapple.core.common.factory.BasicFactory;
import com.tentcoo.pineapple.core.dispatch.DispatchManager;
import com.tentcoo.pineapple.core.dispatch.DispatchWorker;
import com.tentcoo.pineapple.core.dispatch.TransactionBuilder;
import com.tentcoo.pineapple.core.dispatch.initiator.BasicInitiator;
import com.tentcoo.pineapple.core.dispatch.initiator.DispatchManagerInitiator;
import com.tentcoo.pineapple.core.dispatch.mapping.AntWildcardMatcher;
import com.tentcoo.pineapple.core.dispatch.mapping.DefaultPathMatcher;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerMapping;
import com.tentcoo.pineapple.core.dispatch.rendering.InternalResourceResolver;
import com.tentcoo.pineapple.core.dispatch.rendering.JsonResolver;
import com.tentcoo.pineapple.core.dispatch.rendering.ObjectResolver;
import com.tentcoo.pineapple.core.dispatch.service.ServiceExporter;
import com.tentcoo.pineapple.core.dispatch.service.ServiceMethodHandler;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.DirectObjectParameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.JsonParameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.RuquestHeaderNegotiationPamameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.DirectObjectSchemaFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.JsonSchemaFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.RequestHeaderNegotiationSchemaFormatter;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.BridgeHandlerAdapter;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.BridgeTransactionHandler;
import com.tentcoo.pineapple.core.dispatch.view.hybrid.webviewJsBridgeAdapter.WebviewJsBridgeAdapter;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.utils.StringUtils;
public class BeanScanner {

	@SuppressWarnings("unused")
	public Map<String, AbstractBeanDefinition> scan(Context context,IPackageLoader packageLoader, List<String> packageLocations) {
		Map<String,AbstractBeanDefinition> beanIdDecorationMap = new HashMap<String, AbstractBeanDefinition>();
		List<Class> classes = packageLoader.load(context,packageLocations);
		autoAdd(classes);
		if (classes != null) {
			for (Class c : classes) {
				String beanId = getBeanIdFromType(c);
				FactoryBeanDefinition beanDecoration = BasicFactory.getInstance().build(context, FactoryBeanDefinition.class, null, null);
				beanDecoration.setBeanType(c);
				beanDecoration.setBeanId(beanId);
//				添加到beanIdDecorationMap中去 ，key 是id
				beanIdDecorationMap.put(beanId, beanDecoration);
			}
		}
		return beanIdDecorationMap;
	}

	private void autoAdd(List<Class> classes) {
		isEx(DispatchManagerInitiator.class,classes);
		isEx(ObjectResolver.class,classes);
		isEx(JsonResolver.class,classes);
		isEx(JsonSchemaFormatter.class,classes);
		isEx(RuquestHeaderNegotiationPamameterFormatter.class,classes);
		isEx(DefaultPathMatcher.class,classes);
		isEx(RequestHeaderNegotiationSchemaFormatter.class,classes);
		isEx(BasicInitiator.class,classes);
		isEx(AntWildcardMatcher.class,classes);
		isEx(BridgeTransactionHandler.class,classes);
		isEx(TransactionBuilder.class,classes);
		isEx(DispatchWorker.class,classes);
		isEx(ServiceMethodHandler.class,classes);
		isEx(DispatchManager.class,classes);
		isEx(BridgeHandlerAdapter.class,classes);
		isEx(HandlerMapping.class,classes);
		isEx(JsonParameterFormatter.class,classes);
		isEx(InternalResourceResolver.class,classes);
		isEx(DirectObjectSchemaFormatter.class,classes);
		isEx(ServiceExporter.class,classes);
		isEx(DirectObjectParameterFormatter.class,classes);
		isEx(WebviewJsBridgeAdapter.class,classes);

	}
	private void isEx(Class clazz ,List<Class> classes){
		if(!classes.contains(clazz)){
			classes.add(clazz);
		}
	}

	/** 通过反射获取对象的getId()方法获取id，如果id不为null则以id作为beanId,否则以类名首字母小写的方式作为beanId*/
	@SuppressWarnings("unused")
	private String getBeanIdFromType(Class<?> clazz) {
		String id =null;
		try {
			IBean bean = (IBean) clazz.newInstance();
			id = bean.getId();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(id==null){
			id = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
		}
		return id;
	}
}
