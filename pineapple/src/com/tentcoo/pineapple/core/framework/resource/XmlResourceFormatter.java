package com.tentcoo.pineapple.core.framework.resource;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import com.tentcoo.pineapple.utils.FileUtil;

/**Xml类型的文件解析器format方法输出一个Dom解析的Xml数据结构*/
public class XmlResourceFormatter implements IResourceFormatter {

	private static  XmlResourceFormatter instance;
	public static XmlResourceFormatter getInstance(){
		if(instance==null){
			instance = new XmlResourceFormatter();
		}
		return instance;
	}
	@Override
	public Object format(IResource resource) {
		InputStream inputStream = resource.getInputStream();
		String readFile = FileUtil.readFile(inputStream);
		try {
			Document document = null;
			document = DocumentHelper.parseText(readFile);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
