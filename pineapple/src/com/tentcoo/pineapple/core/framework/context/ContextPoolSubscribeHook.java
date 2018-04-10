package com.tentcoo.pineapple.core.framework.context;


import java.util.List;

import com.tentcoo.pineapple.core.common.factory.delegate.CutPoint;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicHook;

public class ContextPoolSubscribeHook extends BasicHook {
	private Pool pool;
	
	public void setPool(Pool pool){
		this.pool=pool;
	}

	@Override
	public void callback(CutPoint cutPoint) {
		cutPoint.proceed();
		Object object = cutPoint.getObject(); 
		if ("addObject".equals(getRule())) {
			if (object instanceof Pool) {
				Pool ap = ((Pool) object);   
				List<Subscribe> subscribes = ap.getSubscribes();
				Object[] message = cutPoint.getParameters();	
				ap.matchSubscribersAndNotify(message);
			}
		}
	}

}
