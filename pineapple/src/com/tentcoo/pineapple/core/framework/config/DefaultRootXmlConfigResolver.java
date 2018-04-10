package com.tentcoo.pineapple.core.framework.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;


import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.constant.Symbol;
import com.tentcoo.pineapple.core.framework.resource.AbstractClassPathXmlFileResolver;
import com.tentcoo.pineapple.core.framework.resource.IResourceResolver;
import com.tentcoo.pineapple.utils.Logger;
import com.tentcoo.pineapple.utils.ToolUtil;

/** 根据类名，获取出来 */
public class DefaultRootXmlConfigResolver extends AbstractClassPathXmlFileResolver {

	private static DefaultRootXmlConfigResolver instance;

	public static DefaultRootXmlConfigResolver getInstance() {
		if (instance == null) {
			instance = new DefaultRootXmlConfigResolver();
		}
		return instance;
	}

	/***
	 * 解析所有节点
	 * @param xmlMap		进行填充的Map
	 * @param element
	 * @return
	 */
	public Map<String, Object> onElement(Map<String, Object> xmlMap,Element element) {
		String elementName = element.getName();
		if(Symbol.XML_ELEMENT_CONFIG_REF_ROOT.equals(elementName)){			//解析引用文件的xml
			resolveNodeElement(xmlMap,element);
		}else if (Symbol.XML_ELEMENT_ROOT.equalsIgnoreCase(elementName)) {  		  
			resolveRootElement(xmlMap, element);					//解析root节点
		} else if (Symbol.XML_ELEMENT_CONFIG.equalsIgnoreCase(elementName)) { 
			resolveConfigElement(xmlMap, element);					//解析Config节点
		} 
		return xmlMap;
	}

	/***
	 * 解析引用文件,如果是以 <config_ref_root> 包裹
	 * @param xmlMap	进行填充的Map
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private void resolveNodeElement(Map<String, Object> xmlMap, Element element) {
		List<Element> elements = element.elements();
		for(Element ele:elements){
			onElement(xmlMap, ele);
		}
	}

	/***
	 * 解析root节点
	 * @param xmlMap     	进行填充的Map
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private void resolveRootElement(Map<String, Object> xmlMap, Element element) {
		List<Element> configElements = element.selectNodes(Symbol.XML_ELEMENT_CONFIG); // 获取指定名字的下一级节点
		for (Element ele : configElements) {
			onElement(xmlMap, ele);
		}
	}

	/***
	 * 解析Config 节点
	 * @param xmlMap		进行填充的Map
	 * @param element
	 */
	private void resolveConfigElement(Map<String, Object> xmlMap,Element element) {
		resolveElement(xmlMap, element);
	}
	/***
	 * 解析其他节点
	 * @param xmlMap		进行填充的Map
	 * @param element
	 */
	private void resolveOtherElement(Map<String, Object> xmlMap, Element element) {
		String elementName = element.getName();
		String key = null;
		Attribute attribute = element.attribute("key");
		if (attribute != null) {
			key = attribute.getStringValue();
		} else {
			key = elementName;
		}
		if (Symbol.XML_ELEMENT_LIST.equalsIgnoreCase(elementName)) {
			List<String> listValue = resolveListElement(element);
			xmlMap.put(key, listValue);
		} else {
			resolveElement(xmlMap, element);
		}
	}

	/***
	 * 解析List节点
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private List<String> resolveListElement(Element element) {
		List<String> values = new ArrayList<String>();
		List<Element> valueElements = element.selectNodes(Symbol.XML_ELEMENT_ITEM);
		if (valueElements != null & valueElements.size() > 0) {
			for (Element e : valueElements) {
				String value = getElementValue(e);
				values.add(value);
			}
		}
		return values;
	}
	/***
	 * 没有file没有resolver向下解析
	 * @param xmlMap
	 * @param element
	 * @param key
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void toChildElement(Map<String, Object> xmlMap,Element element, String key) {
		// 获取config节点的属性
		Attribute keyAttribute = element.attribute("key"); // 属性1：map的key
		Attribute fileAttribute = element.attribute("file"); // 属性2：是否有引用文件
		Attribute resolverAttribute = element.attribute("resolver"); // 属性3：是否有解析器
		// 1、判断是否有子节点
		boolean hasChildElement = hasChildElement(element);
		if (hasChildElement) { // 存在
			List<Element> elements = element.elements();
			boolean hasListChildElement = hasListChildElement(element);

			if(!hasListChildElement && elements.size()>0){	  //有且只有一个list节点
				Map<String, Object> childMap = new HashMap<String, Object>();
//				判断是否有子节点
				for (Element e : elements) {
					resolveElement(childMap, e);
				}
				xmlMap.put(key, childMap);
			}else if(hasListChildElement && elements.size()==1){
				 List<Element> listElements = element.selectNodes(Symbol.XML_ELEMENT_LIST);
				 Element listElement = listElements.get(0);
				 List<String> listValue = resolveListElement(listElement);
				 xmlMap.put(key, listValue);
			}else{
				Logger.e(element.asXML()+"--不符合规范！");
			}

		} else {
			// 2、判断是否有内容
			xmlMap.put(key, element.getText());
		}
	}

	

	/***
	 * 判断是否存在子节点
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean hasChildElement(Element element) {
		List<Element> elements = element.elements();
		if (elements != null && elements.size() > 0) {
			return true;
		}
		return false;
	}
	
	/***
	 * 判断是否有list子节点
	 * @param element
	 * @return
	 */
	private boolean hasListChildElement(Element element){
		List<Element> elements = element.selectNodes(Symbol.XML_ELEMENT_LIST);
		if (elements != null && elements.size() > 0) {
			return true;
		}
		return false;
	}
	

	/***
	 * 解析节点，config 及其子节点都是相同的逻辑
	 * @param xmlMap
	 * @param element
	 */
	@SuppressWarnings("rawtypes")
	private void resolveElement(Map<String, Object> xmlMap, Element element) {

		// 获取config节点的属性
		Attribute keyAttribute = element.attribute("key"); // 属性1：map的key
		Attribute fileAttribute = element.attribute("file"); // 属性2：是否有引用文件
		Attribute resolverAttribute = element.attribute("resolver"); // 属性3：是否有解析器
		String key = null;
		// 1、判断是否有key 没有就抛异常
		if (keyAttribute == null) {
			key  = element.getName();
		} else {
			key = keyAttribute.getStringValue();
		}

		if (fileAttribute != null) { // 如果file 不为空
			String filePath = fileAttribute.getStringValue();
			if (resolverAttribute != null) {
				String resolverName = resolverAttribute.getStringValue();
				IResourceResolver resolver = null;
				try {
					resolver = (IResourceResolver) ToolUtil.getJavaClass(resolverName).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (resolver != null) {					//存在解析器
					// resolver.resolve(resource)
					String configText = element.getText();
					if (configText != null && !"".equals(configText)) {
						resovleFile(xmlMap, key, filePath,resolver);
					} else {
						resovleFile(xmlMap, key, filePath,this);
					}
				} else {
					resovleFile(xmlMap, key, filePath,this);
				}
			} else {
				resovleFile(xmlMap, key, filePath,this);
			}
		} else { // 如果file 为空的话
			if (resolverAttribute != null) {
				String resolverName = resolverAttribute.getStringValue();
				IResourceResolver resolver = null;
				try {
					resolver = (IResourceResolver) ToolUtil.getJavaClass(resolverName).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (resolver != null) {
					// resolver.resolve(resource)
					String configText = element.getText();
					if (configText != null && !"".equals(configText)) {
						xmlMap.put(keyAttribute.getStringValue(),resolver.resolve(configText));
					} else {
						toChildElement(xmlMap, element, key);
					}
				} else {
					toChildElement(xmlMap, element, key);
				}
			} else {
				toChildElement(xmlMap, element, key);
			}
		}
	}

	/***
	 * 指定解析器进行解析file
	 * @param xmlMap
	 * @param keyAttribute
	 * @param filePath
	 */
	@SuppressWarnings("rawtypes")
	private void resovleFile(Map<String, Object> xmlMap,String key, String filePath,IResourceResolver resolver) {
		xmlMap.put(key,resolver.resolve(filePath));
	}
	@Override
	public Map<String, Object> onResolve(Object formattedinput) {
		Document document = null;
		if(formattedinput instanceof Document){
			document = (Document)formattedinput;
		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		onElement(rootMap, document.getRootElement());
		Logger.i("Config的xml解析出来的Map:"+rootMap);
		return rootMap;
	}
	

	/***
	 * 获取property的value ，这里的value不单单指属性值
	 * 
	 * @param element
	 * @return
	 */
	private String getElementValue(Element element) {
		String value = null;
		value = getAttributeValue(element, "value");
		if (value == null || "".equals(value)) {
			String text = element.getText().trim();
			if (text != null && !"".equals(text)) {
				value = text;
			}
		}
		return value;
	}
	/***
	 * 获取节点的String 类型的属性值
	 * 
	 * @param element
	 *            节点对象
	 * @param attributeName
	 *            属性名称
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getAttributeValue(Element element, String attributeName) {
		String attributeValue = null;
		Attribute attribute = element.attribute(attributeName);
		if (attribute != null) {
			attributeValue = attribute.getValue();
		}
		return attributeValue;
	}
}
