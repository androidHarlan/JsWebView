package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.bean.ICore;

public interface IRuleMatcher extends ICore {
	public boolean match(String url,String rule);
}
