package com.tentcoo.pineapple.core.framework.ioc;

import java.util.List;

import android.content.Context;

public interface IPackageLoader {
	List<Class> load(Context context,List<String> packageLocations);
}
