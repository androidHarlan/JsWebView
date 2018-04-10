package com.tentcoo.pineapple.core.dispatch.service;

import com.tentcoo.pineapple.core.common.bean.IComponent;

public interface IService extends IComponent{
	String getMappedName();	//Service及其子接口专用，用于获取该Service对象映射之后的名称
}
