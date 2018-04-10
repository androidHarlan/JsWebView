package com.tentcoo.pineapple.core.dispatch;

import java.util.List;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerExceptionChain;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerMapping;
import com.tentcoo.pineapple.core.dispatch.mapping.IHandler;
import com.tentcoo.pineapple.core.dispatch.mapping.IHandlerInterceptor;
import com.tentcoo.pineapple.core.dispatch.rendering.IRenderer;
import com.tentcoo.pineapple.core.dispatch.rendering.IRendererResolver;
import com.tentcoo.pineapple.core.dispatch.view.BasicViewContainer;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.utils.Logger;

public class DispatchWorker extends BasicDelegatedFactoryObject implements
		ICore {

	@Autowired
	public HandlerMapping handlerMapping; // 单例，自动注入
	private BasicViewContainer viewContainer;

	public List<IRendererResolver> resolvers;

	/** 分发transaction */
	public void dispatch(Transaction transaction,TransactionEnvironment environment) {
		try {
			Request request = transaction.getRequest(); // 请求
			Response response = transaction.getResponse(); // 返回
			Logger.e("resovlers的个数：" + resolvers.size());
			HandlerExceptionChain chain = getHandlerChain(request); // 筛选到对应的多个拦截器和单个处理器

			boolean preHandlerReturn = doPreHandle(request, response, chain,environment);// 判断是否往下执行
			ResponseSchema schema = null;
			if (preHandlerReturn) {
				schema = doHandle(request, response, chain, environment);
				doPostHandle(request, response, chain, schema, environment);
			}
			doResponse(request, response, schema, environment);
			System.out.println("完美执行dispatch方法");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("dispatcher执行过程中出现错误");
		}
	}

	public void setViewContainer(BasicViewContainer viewContainer) {
		this.viewContainer = viewContainer;
	}

	/** map出拦截器和处理器 */
	public HandlerExceptionChain getHandlerChain(Request request) {
		String url = request.getUrl();
		// HandlerMapping hMap = new HandlerMappingFactory().build();
		HandlerExceptionChain chain = handlerMapping.map(url);
		return chain;
	}

	public boolean doPreHandle(Request request, Response response,HandlerExceptionChain chain, TransactionEnvironment environment) {
		List<IHandlerInterceptor> interceptors = chain.getInterceptors(); // 获取拦截器列表
		if (interceptors != null) {
			for (IHandlerInterceptor interceptor : interceptors) {
				boolean result = interceptor.preHandle(request, response,environment);// 执行preHandler,只要其中任何一个迭代返回false，则直接返回false
				if (!result) {
					return false;
				}
			}
		}
		return true;
	}

	public ResponseSchema doHandle(Request request, Response response,
			HandlerExceptionChain chain, TransactionEnvironment environment) {
		ResponseSchema schema = null;
		if (chain != null) {
			IHandler handler = chain.getHandler(); // 获取处理器
			if (handler != null) {
				schema = handler.handle(request, response, environment);
			}else{
				Logger.e("chain.handler为空！");
			}
		}
		return schema;
	}

	public void doPostHandle(Request request, Response response,HandlerExceptionChain chain, ResponseSchema schema,TransactionEnvironment environment) {
		List<IHandlerInterceptor> interceptors = chain.getInterceptors();
		if(interceptors!=null){
		for (int i = interceptors.size(); i >= 0; i--) {
			IHandlerInterceptor interceptor = interceptors.get(i);
			interceptor.postHandle(request, response, schema, environment); // 这里是倒序
		}
		}else{
			Logger.e("chain.interceptors为空！");
		}
	}

	public void doResponse(Request request, Response response,
			ResponseSchema schema, TransactionEnvironment environment) {
		if (schema != null) {
			IRenderer renderer = schema.getRenderer();
			if (renderer == null) {
				Logger.e("schema的Renderer为空!");
				if (resolvers != null && resolvers.size() > 0) {
					for (IRendererResolver resolver : resolvers) {
						renderer = resolver.resolve(schema);
						if(renderer!=null){
							break;
						}
					
					}
				}
				renderer.render(schema.getModel(), request, response);
			}else{
				renderer.render(schema.getModel(), request, response);
				
			}
		}else{
			Logger.e("schema为空！");
		}
	}

	public void init() {
		handlerMapping.init();
	}

	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
