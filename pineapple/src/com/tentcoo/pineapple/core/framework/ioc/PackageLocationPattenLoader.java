package com.tentcoo.pineapple.core.framework.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tentcoo.pineapple.core.common.bean.IBean;
import com.tentcoo.pineapple.utils.ClassUtils;

import android.content.Context;

public class PackageLocationPattenLoader implements IPackageLoader {

	/** 扫描包中定义的实现了Bean接口或其子接口的类 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List<Class> load(Context context, List<String> packageLocations) {
		List<Class> beanClassList = new ArrayList<Class>();
		for (String packageLocation : packageLocations) {
			Set<String> classNameSet = ClassUtils.getClassName(context,packageLocation);
			for (String className : classNameSet) {
				try {
					Class<?> clazz = Class.forName(className);
					if (!clazz.isInterface()) {
						if (IBean.class.isAssignableFrom(clazz)) {
							beanClassList.add(clazz);
						} else {
							Class<?>[] interfaces = IBean.class.getInterfaces();
							for (Class<?> c : interfaces) {
								if (c.isAssignableFrom(clazz)) {
									beanClassList.add(clazz);
									break;
								}
							}
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return beanClassList;
	}

}
