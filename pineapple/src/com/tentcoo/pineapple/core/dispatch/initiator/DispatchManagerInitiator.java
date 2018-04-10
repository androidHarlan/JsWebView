package com.tentcoo.pineapple.core.dispatch.initiator;

import com.tentcoo.pineapple.core.dispatch.DispatchManager;

/**
 * 静态单例
 * */
public class DispatchManagerInitiator extends BasicInitiator {
	private DispatchManager dispatchManager;

	public void setDispatchManager(DispatchManager dispatchManager){
		this.dispatchManager = dispatchManager;
	}
	@Override
	public void init() {
		super.init();
		dispatchManager.init();
	}
}	
