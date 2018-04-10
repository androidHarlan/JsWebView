package com.tentcoo.pineapple.core.dispatch.mapping;

import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.core.common.bean.IComponent;
import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.common.constant.Value;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.service.ServiceExporter;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.ioc.BasicBeanDecorationFilter;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.ioc.factory.FrameworkFactory;
import com.tentcoo.pineapple.core.framework.template.IocTemplate;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;

public class HandlerMapping extends BasicDelegatedFactoryObject implements
		ICore {
	
	public List<InterceptorRuleMapping> interceptorRuleMappingList;

	public List<HandlerRuleMapping> handlerRuleMappingList;

	@Autowired(clazz = AntWildcardMatcher.class)
	public IRuleMatcher interceptorMatcher;
	@Autowired(clazz = DefaultPathMatcher.class)
	public IRuleMatcher handlerMatcher;

	public List<IExporter> exporters; // 导出器队列【通过数组注入获得注入】

	// 筛选出拦截器和 处理器
	public HandlerExceptionChain map(String url) {
		HandlerExceptionChain chain = new HandlerExceptionChain();
		if (interceptorRuleMappingList != null) {
			Logger.d("HandlerMapping.interceptorRuleMappingList的数量："+ interceptorRuleMappingList.size());
			for (InterceptorRuleMapping interceptorRuleMapping : interceptorRuleMappingList) {
				if (interceptorMatcher == null) {
					Logger.e("handlerMapping.interceptorMatcher为空！");
					break;
				}
				String rule = interceptorRuleMapping.getRule();
				boolean isMatch = interceptorMatcher.match(url, rule);
				if (isMatch) {
					IHandlerInterceptor interceptor = interceptorRuleMapping.getInterceptor();
					if(interceptor!=null){
						chain.addInterceptor(interceptor);
					}else{
						Logger.e("url匹配但拦截器为空！");
					}
				}
			}
		} else {
			Logger.e("HandlerMapping.interceptorRuleMappingList为空");
		}
		if (handlerRuleMappingList != null) {
			Logger.d("HandlerMapping.handlerRuleMappingList的数量："+ handlerRuleMappingList.size());
			for (HandlerRuleMapping handlerRuleMapping : handlerRuleMappingList) {
				if (handlerMatcher == null) {
					Logger.e("handlerMapping.handlerMatcher为空");
					break;
				}
				String rule = handlerRuleMapping.getRule();
				boolean isMatch = handlerMatcher.match(url, rule);
				if (isMatch) { // 拿到一个就可以了
					IHandler handler = handlerRuleMapping.getHandler();
					chain.setHandler(handler);
					break;
				}
			}
		} else {
			Logger.e("HandlerMapping.handlerRuleMappingList为空");
		}
		return chain;
	}

	public void add(List<HandlerRuleMapping> handlerRuleMappings) {
		if (handlerRuleMappingList == null) {
			handlerRuleMappingList = new ArrayList<HandlerRuleMapping>();
		}
		handlerRuleMappingList.addAll(handlerRuleMappings);
	}

	public void add(HandlerRuleMapping handlerRuleMapping){
		if (handlerRuleMappingList == null) {
			handlerRuleMappingList = new ArrayList<HandlerRuleMapping>();
		}
		handlerRuleMappingList.add(handlerRuleMapping);
	}
	
	public void init() {
		exportComponents();
//		addHttpHandlerRuleMapping();
	}


	
//	/**添加网络处理器*/
//	private void addHttpHandlerRuleMapping() {
//		HandlerRuleMapping ruleMapping = BeanFactory.getInstance().build(PineApple.getContext(),HandlerRuleMapping.class);
//		String rule = Value.REQUEST_TYPE_HTTP;
//		HttpHandler httpHandler = BeanFactory.getInstance().build(PineApple.getContext(), HttpHandler.class);
//		ruleMapping.setRule(rule);
//		ruleMapping.setHandler(httpHandler);
//		add(ruleMapping);
//	}

	private void exportComponents() {
		BasicBeanDecorationFilter beanDecorationFilter = FrameworkFactory.getInstance().build(PineApple.getContext(),BasicBeanDecorationFilter.class, null, null);
		beanDecorationFilter.setBeanType(IComponent.class);
		beanDecorationFilter.setMatchSubtype(true);
		List<Object> components = IocTemplate.getInstance().searchBean(beanDecorationFilter, -1);
		if (components != null) {
			for (Object object : components) {
				IComponent component = null;
				if (object instanceof IComponent) {
					component = (IComponent) object;
				} else {
					continue;
				}


				if (exporters != null) {
					for (IExporter exporter : exporters) {
						boolean support = exporter.support(component);
						if (support) {
							List<HandlerRuleMapping> handlerRuleMappings = exporter.export(component);

							add(handlerRuleMappings);
						}
					}
				} else {
					Logger.e("handlerMapping.exporters为空！");
				}
			}
		}else{
			Logger.e("找不到bean");
		}
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
