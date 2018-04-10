package com.tentcoo.pineapple.core.common.factory;


import android.content.Context;

public interface IInstantializer {
	<T> T newInstance(Context context,Class<T> clazz);

	void returnInstance(BasicFactoryObject instance);
}
