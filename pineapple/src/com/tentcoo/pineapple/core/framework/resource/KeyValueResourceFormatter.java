package com.tentcoo.pineapple.core.framework.resource;


/**键值对结构的文件解析器format方法输出为一个map结构*/
public class KeyValueResourceFormatter implements IResourceFormatter {

	private static KeyValueResourceFormatter instance;
	public static KeyValueResourceFormatter getInstance(){
		if(instance==null){
			instance = new KeyValueResourceFormatter();
		}
		return instance;
	}
	@Override
	public Object format(IResource resource) {
		return resource.getInputStream();
	}

}
