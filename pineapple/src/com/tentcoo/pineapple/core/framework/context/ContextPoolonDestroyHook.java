package com.tentcoo.pineapple.core.framework.context;

import com.tentcoo.pineapple.core.common.factory.delegate.CutPoint;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicHook;

public class ContextPoolonDestroyHook extends BasicHook{
	private Pool pool;
	
	public void setPool(Pool pool){
		this.pool=pool;
	}

	@Override
	public void callback(CutPoint cutPoint) {
		
	}

}
