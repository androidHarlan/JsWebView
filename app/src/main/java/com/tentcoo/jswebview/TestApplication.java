package com.tentcoo.jswebview;

import android.app.Application;
import android.content.Context;

import com.tentcoo.pineapple.core.framework.application.PineApple;

public class TestApplication extends Application {
	public static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext= getApplicationContext();
		PineApple.Init("xml/config_config.properties", getApplicationContext());

	}
}
