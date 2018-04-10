package com.tentcoo.pineapple.core.common.factory.delegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tentcoo.pineapple.utils.Logger;

import android.content.Context;
import leo.android.cglib.proxy.Enhancer;
import leo.android.cglib.proxy.MethodInterceptor;
import leo.android.cglib.proxy.MethodProxy;

public class PoolProxy implements MethodInterceptor {
	private Context context;
	private Object target;

	public PoolProxy(Context context) {
		super();
		this.context = context;
	}

	@Override
	public Object intercept(Object object, Object[] args,MethodProxy methodProxy) throws Exception {
		target = object;
		Object result = null;
		if("getAllHooks".equals(methodProxy.getMethodName())){		//忽略该方法的监听
			return methodProxy.invokeSuper(object, args);
		}
		Logger.d("监听到了方法的调用" + target.getClass().getSimpleName() + "."+ methodProxy.getMethodName());
		List<BasicHook> unexecutedHookList = null;
		unexecutedHookList = matchHook(object, methodProxy.getMethodName()); // 匹配的钩子列表
		CutPoint cutPoint = new CutPoint();
		methodInvokeNotification(cutPoint, unexecutedHookList, methodProxy,
				args); // 方法调用通知
		result = cutPoint.getResult();
		return result;
	}

	/**
	 * 扫描钩子列表，匹配符合条件的钩子列表
	 * 
	 * @param methodName
	 *            方法名字
	 * @return
	 */
	public List<BasicHook> matchHook(Object object, String methodName) {
		BasicDelegatedFactoryObject bdfo = null;
		List<BasicHook> allHooks = null;
		List<BasicHook> unexecutedHookList = new ArrayList<BasicHook>();
		if (object instanceof BasicDelegatedFactoryObject) {
			bdfo = (BasicDelegatedFactoryObject) object;
			allHooks = bdfo.getAllHooks();
		}
		if (allHooks != null) { // 匹配的Hook 的集合
			for (BasicHook hook : allHooks) {
				if (methodName.equals(hook.getRule())) {
					unexecutedHookList.add(hook);
				}
			}
		}
		return unexecutedHookList;
	}

	/**
	 * 监听方法的调用
	 * 
	 * @param cutPoint
	 * @param unexecutedHookList
	 * @param method
	 * @param args
	 */
	public void methodInvokeNotification(CutPoint cutPoint,
			List<BasicHook> unexecutedHookList, MethodProxy methodProxy,
			Object[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		if (unexecutedHookList.size() > 0) { // 如果调用的方法存在钩子
			cutPoint.setInvoke(target, methodProxy, args);
			cutPoint.setUnexecutedHookList(unexecutedHookList);
			Logger.d("-------------------开始-----------------------\n");

			try {
				BasicHook hook = cutPoint.getNextHook();
				hook.callback(cutPoint);
			} catch (Exception e) {
				e.printStackTrace();
				String message = e.getMessage();
				cutPoint.setExceptions(true);
				cutPoint.setExceptions(message);
			}

			Logger.d("------------------结束-----------------------\n");
		} else { // 如果不存在就执行原方法
			Object invoke = methodProxy.invokeSuper(target, args);
			cutPoint.setResult(invoke);
		}
	}

	// 获取代理类是继承关系的
	public Object getProxy(Class cls) {
		if(context==null){
			Logger.e(cls.getSimpleName()+"的context为空");
		}
		Enhancer e = new Enhancer(context);
		e.setSuperclass(cls);
		e.setInterceptor(this);
		Object create = e.create();
		if (create == null) {
			Logger.e("代理类为空----" + cls.getName());
		}
		return create;
	}
}
