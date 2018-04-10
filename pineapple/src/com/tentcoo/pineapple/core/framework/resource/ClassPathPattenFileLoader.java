package com.tentcoo.pineapple.core.framework.resource;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.tentcoo.pineapple.core.framework.context.PAException;

/**
 * 项目目录文件加载器，在项目类目录中搜索资源进行加载, 使用AntWildcardMatching作为路径匹配规则
 */
public class ClassPathPattenFileLoader implements IResourceLoader {
	private static ClassPathPattenFileLoader instance;

	public static ClassPathPattenFileLoader getInstance() {
		if (instance == null) {
			instance = new ClassPathPattenFileLoader();
		}
		return instance;
	}

	@Override
	public IResource load(Context context, String location) {
		LocalFileResource resource = null;
		try {
			InputStream open = context.getAssets().open(location);
			resource = new LocalFileResource();
			resource.setInputStream(open);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new PAException(location + "找不到该文件");
		}
		return resource;
	}

	/***
	 * 不同的Formmatter解析出来的不一样
	 * keyValue 解析出来的Map
	 * Xml 解析出来的是Document对象（Dom4j框架里的对象）
	 */
	@Override
	public Object loadAndFormat(Context context, String location,IResourceFormatter formatter) {
		Object obj = null;
		LocalFileResource resource = null;
		try {
			InputStream open = context.getAssets().open(location);
			resource = new LocalFileResource();
			resource.setInputStream(open);
			obj = formatter.format(resource);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new PAException(location + "找不到该文件");
		}
		return obj;
	}
}
