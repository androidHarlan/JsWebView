package com.tentcoo.pineapple.core.common.factory;


import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.utils.Logger;

import android.content.Context;


public class DefaultInstantializer implements IInstantializer{
	private static DefaultInstantializer instantializer;
	public static DefaultInstantializer getInstance(){
		if(instantializer==null){
			instantializer=new DefaultInstantializer();
		}
		return instantializer;
	}
	@Override
	public <T> T newInstance(Context context,Class<T> clazz){
		T newInstance = null;
			try {
				if(!clazz.isInterface())
				{
					newInstance =clazz.newInstance();
				}else{
					Logger.e(clazz.getName()+"是接口");
				}
			} catch (InstantiationException e) {
				throw new PAException(clazz.getName()+"--无法实例化");
			} catch (IllegalAccessException e) {
				throw new PAException(clazz.getName()+"--安全权限异常-请检查是否调用了private的方法");
			}catch(Exception e){
				e.printStackTrace();
				Logger.e(e.getMessage());
//				throw new PAException(clazz.getName()+"--未知异常");
			}
		return newInstance;
	}

	@Override
	public void returnInstance(BasicFactoryObject instance) {
		
	}

}
