package com.tentcoo.pineapple.core.framework.resource;

import java.util.Map;

import android.content.Context;

/***
 * 抽象的配置信息解析器
 * @param <T>
 */
public interface IResourceResolver<T extends Object> {
	/**解析资源,有子类具体实现解析算法*/
	T resolve(String filePath);
	
	
//	问题一：要通过反射生成对象要不要 构造参数 Context
//	问题二：如果是路径的话，就一定要传Context ， 解析字符串的话就不要，这要如何做到，看起来顺眼点。
}
