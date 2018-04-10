package com.tentcoo.pineapple.core.dispatch.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.Request;
import com.tentcoo.pineapple.core.dispatch.Response;
import com.tentcoo.pineapple.core.dispatch.ResponseSchema;
import com.tentcoo.pineapple.core.dispatch.TransactionEnvironment;
import com.tentcoo.pineapple.core.dispatch.mapping.IHandler;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.IServiceParameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.parameter.RuquestHeaderNegotiationPamameterFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.IServiceSchemaFormatter;
import com.tentcoo.pineapple.core.dispatch.service.formatter.schema.RequestHeaderNegotiationSchemaFormatter;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.core.framework.template.annotation.Mapping;
import com.tentcoo.pineapple.utils.Logger;

public class ServiceMethodHandler extends BasicDelegatedFactoryObject implements
		IHandler {

	// 这里有两个实现类，暂时用这个
	@Autowired(clazz = RuquestHeaderNegotiationPamameterFormatter.class)
	public IServiceParameterFormatter parameterFormatter;

	// 这里有两个实现类，暂时用这个
	@Autowired(clazz = RequestHeaderNegotiationSchemaFormatter.class)
	public IServiceSchemaFormatter schemaFormatter;

	private Object serviceBean; // 创建的时候就有了
	private String methodName; // 创建的时候就有了

	@Override
	public ResponseSchema handle(Request request, Response response,TransactionEnvironment environment) {
		if(parameterFormatter==null){
			Logger.e("ServiceMethodHandler.parameterFormatter==null");
			return null;
		}
		List<Object> parameterObjects = parameterFormatter.format(request);
		Object serviceMethodReturn = null;
		ResponseSchema schema = null;
		try {
			serviceMethodReturn = invokeServiceMethod(parameterObjects);
		} catch (IllegalAccessException e) {
			Logger.e("方法不能为私有");
//			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Logger.e("参数类型或个数不匹配");
//			e.printStackTrace();
		} catch (InvocationTargetException e) {
//			e.printStackTrace();
			Logger.e("InvocationTargetException异常");
		}catch(Exception e){
			Logger.e("方法调用异常，请检查类型是否正确 ，获取方法修饰是否为public等");
//			e.printStackTrace();
		}
		if(serviceMethodReturn!=null){
			schema = schemaFormatter.format(request,serviceMethodReturn);
		}
		return schema;
	}

	/***
	 * 调用service的方法，通过反射调用
	 * 
	 * @param paramDefinitions
	 * @param parameterObjects
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Object invokeServiceMethod(List<Object> parameterObjects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//这里通过反射调用servicebean 的指定方法名的注解名的方法，找不到就不调用。
		Method method = getMethodByMethodName(methodName);
		Object invoke =  null;
		Object[] param= parameterObjects!=null?parameterObjects.toArray():null;
		if(method!=null){
			Logger.i("方法名"+method.getName()+"--参数"+param);
			invoke = method.invoke(serviceBean, param);
			Logger.i("方法返回"+invoke);
		}
		return invoke;
	}

	/**
	 * 通过注解名找方法
	 * 
	 * @param methodName2
	 */
	private Method getMethodByMethodName(String methodName2) {
		if (serviceBean != null) {
			Method[] methods = serviceBean.getClass().getMethods();
			for(Method m:methods){
				Mapping annotation = m.getAnnotation(Mapping.class);
				if(annotation!= null){
					String name = annotation.name();
					if(name.equals(methodName2)){
						return m;
					}
				}
			}
		}
		return null;
	}

	public IServiceParameterFormatter getParameterFormatter() {
		return parameterFormatter;
	}

	public void setParameterFormatter(
			IServiceParameterFormatter parameterFormatter) {
		this.parameterFormatter = parameterFormatter;
	}

	public IServiceSchemaFormatter getSchemaFormatter() {
		return schemaFormatter;
	}

	public void setSchemaFormatter(IServiceSchemaFormatter schemaFormatter) {
		this.schemaFormatter = schemaFormatter;
	}

	public void setServiceBean(Object serviceBean) {
		this.serviceBean = serviceBean;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
