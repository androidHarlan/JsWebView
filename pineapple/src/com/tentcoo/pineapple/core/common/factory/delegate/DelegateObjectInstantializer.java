package com.tentcoo.pineapple.core.common.factory.delegate;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.common.factory.IInstantializer;
import com.tentcoo.pineapple.utils.Logger;

import android.content.Context;


public class DelegateObjectInstantializer implements IInstantializer{
	private static DelegateObjectInstantializer instantializer;
	public static DelegateObjectInstantializer getInstance(){
		if(instantializer==null){
			instantializer=new DelegateObjectInstantializer();
		}
		return instantializer;
	}
	@Override
	public <T> T newInstance(Context context,Class<T> clazz) {
		T proxy = null;
		try {
			if (!clazz.isInterface()) {
				if(context==null){
					Logger.e("context为空");
				}
				PoolProxy PoolProxy = new PoolProxy(context);
				proxy = (T) PoolProxy.getProxy(clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}

	@Override
	public void returnInstance(BasicFactoryObject instance) {
		
	}

}
