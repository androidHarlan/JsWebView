package com.tentcoo.pineapple.core.framework.resource;

public interface IResourceFormatter {
	/**将输入的信息解析为响应格式的对象*/
	Object format(IResource resource);
}
