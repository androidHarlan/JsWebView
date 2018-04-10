package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.utils.Logger;


public class DefaultPathMatcher extends BasicDelegatedFactoryObject implements IRuleMatcher{

	@Override
	public boolean match(String url, String rule) {
//		TODO 解析路径
		Logger.e("DefaultPathMatcher参数url:"+url+",rule："+rule);
		if(url==null || rule==null ||"".equals(url) || "".equals(rule)){
			return false;
		}
		if(url.startsWith(rule)||url.equals(rule)){
			return true;
		}
		return false;
	}

	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	
	
}
