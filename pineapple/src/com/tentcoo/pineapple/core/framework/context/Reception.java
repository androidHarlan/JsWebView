package com.tentcoo.pineapple.core.framework.context;


import java.lang.reflect.Method;

/**
 * Created by SilenceDut on 16/8/1.
 */

public class Reception {
    private Object mReceiver;
    private Method mInvokedMethod;
    private Object[] mArgs;

    
    
    public Method getmInvokedMethod() {
		return mInvokedMethod;
	}

	public void setmInvokedMethod(Method mInvokedMethod) {
		this.mInvokedMethod = mInvokedMethod;
	}

	/**
     * 
     * @param receiver			 委托类
     * @param invokedMethod		反射方法
     * @param args				方法的参数
     */
    public Reception(Object receiver,Method invokedMethod,Object[] args) {
        this.mReceiver = receiver;
        this.mInvokedMethod = invokedMethod;
        this.mArgs = args;
        initReception();
    }

    private void initReception() {
        mInvokedMethod.setAccessible(true);  //setAccessible是启用和禁用访问安全检查的开关
      
    }

    public Object dispatchEvent() {
    	Object invoke =null;
		try {
			invoke = mInvokedMethod.invoke(mReceiver, mArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return invoke;
    }
}