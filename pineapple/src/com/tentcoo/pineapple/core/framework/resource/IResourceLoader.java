package com.tentcoo.pineapple.core.framework.resource;

import android.content.Context;

/**资源加载器*/
public interface IResourceLoader {
	IResource load(Context context,String locations);
	Object loadAndFormat(Context context,String location,IResourceFormatter formatter);
}
