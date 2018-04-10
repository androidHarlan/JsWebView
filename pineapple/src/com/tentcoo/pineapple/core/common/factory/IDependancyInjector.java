package com.tentcoo.pineapple.core.common.factory;

import android.content.Context;


public interface IDependancyInjector {
	

	public <T> void inject(T bean,Context context) ;

	
}
