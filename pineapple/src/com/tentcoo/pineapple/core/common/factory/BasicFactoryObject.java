package com.tentcoo.pineapple.core.common.factory;

import android.content.Context;

public  class BasicFactoryObject {
	private Context context  = null;
	public void setContext(Context context){
		this.context = context;
	}
	public Context getContext(){
		return context;
	}
	public void onCreated(){
		System.out.print("");
	}

	public void onDestroyed(){

	}
}