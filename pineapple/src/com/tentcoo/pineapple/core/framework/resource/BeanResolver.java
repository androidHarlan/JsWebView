package com.tentcoo.pineapple.core.framework.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.tentcoo.pineapple.core.common.constant.Symbol;
import com.tentcoo.pineapple.core.framework.application.PineApple;
import com.tentcoo.pineapple.core.framework.context.PAException;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.ListBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.MapBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.SimpleValueBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.DefinitionDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.reference.IdAndTypeDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.reference.IdDependency;
import com.tentcoo.pineapple.core.framework.ioc.dependency.reference.TypeDependency;
import com.tentcoo.pineapple.core.framework.ioc.factory.FrameworkFactory;
import com.tentcoo.pineapple.utils.Logger;
import com.tentcoo.pineapple.utils.StringUtils;

/***
 * 
 * 
 */
public class BeanResolver extends AbstractClassPathXmlFileResolver {

	public static String REFERENCE = "reference";
	public static String VALUE = "value";

	private static BeanResolver instance;

	public static BeanResolver getInstance() {
		if (instance != null) {
			instance = new BeanResolver();
		}
		return instance;
	}

	@Override
	public Map<String, Object> onResolve(Object formattedinput) {
		Document document = null;
		if (formattedinput instanceof Document) {
			document = (Document) formattedinput;
		}
		Map<String, Object> beanMap = new HashMap<String, Object>(); // 被填充的map
		Element rootElement = document.getRootElement(); // 根标签
		onElement(beanMap, rootElement);
		return beanMap;
	}

	/***
	 * 解析各个节点对象
	 * 
	 * @param beanMap
	 * @param element
	 */
	private void onElement(Map<String, Object> beanMap, Element element) {
		String elementName = element.getName();
		// 如果是root标签
		if (Symbol.XML_ELEMENT_ROOT.equals(elementName)) {
			resolveRoot(beanMap, element);
		}
		// 如果是beans
		else if (Symbol.XML_ELEMENT_BEANS.equals(elementName)) {
			resolveBeans(beanMap, element);
		}
		// 如果是bean
		else if (Symbol.XML_ELEMENT_BEAN.equals(elementName)) {
			try {
				resolveBean(beanMap, element);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析root节点
	 * 
	 * @param beanMap
	 * @param element
	 * */
	@SuppressWarnings("unchecked")
	private void resolveRoot(Map<String, Object> beanMap, Element element) {
		List<Element> beansNodes = element.selectNodes(Symbol.XML_ELEMENT_BEANS);
		if (beansNodes.size() > 0) {
			Element beansElement = beansNodes.get(0);
			onElement(beanMap, beansElement);
		} else {
			throw new PAException("配置的xml文件没有找到<beans></beans>");
		}
	}

	/**
	 * 解析beans节点
	 * 
	 * @param beanMap
	 * @param element
	 * */
	@SuppressWarnings("unchecked")
	private void resolveBeans(Map<String, Object> beanMap, Element element) {
		List<Element> beanNodes = element.selectNodes(Symbol.XML_ELEMENT_BEAN);
		if (beanNodes.size() > 0) {
			for (Element e : beanNodes) {
				onElement(beanMap, e);
			}
		} else {
			Logger.e("配置的xml文件没有找到\r\n<beans>\r\n<bean></bean>\r\n</beans>");
		}

	}

	/**
	 * 解析bean节点
	 * 
	 * @param beanMap
	 * @param element
	 * @throws ClassNotFoundException
	 * */
	private void resolveBean(Map<String, Object> beanMap, Element element)
			throws ClassNotFoundException {

		AbstractBeanDefinition decoration = null;
		String id = getAttributeValue(element, Symbol.XML_PROPERTY_BEAN_ID);
		String type = getAttributeValue(element, Symbol.XML_PROPERTY_BEAN_CLASS);
		boolean isSingleton = getBooleanAttributeValue(element, Symbol.XML_PROPERTY_BEAN_SINGLETON);
		String value = getElementValue(element);
		if (value != null && type == null) { // simpleValueDefinition
			decoration = getSimpleValueBeanDefinition(value);
		} else if (isListOrMapElement(element) &&type==null) { // List或map 的definition
			decoration = getListOrMapBeanfinition(element);
		} else { // factoryDefinition
			decoration = getFactoryBeanfinition(element);
		}
		// 否则获取依赖去获取
		decoration.setBeanId(id);
		decoration.setBeanType(getClass(type));
		decoration.setSingleton(isSingleton);
		beanMap.put(getDecorationKey(id, type), decoration);
	}

	/***
	 * 解析property节点
	 * 
	 * @param dependencys
	 * @param element
	 *            property节点对象
	 * @throws ClassNotFoundException
	 */
	private void resolveProperty(Map<String, AbstractDependency> dependencys,
			Element element) throws ClassNotFoundException {
		AbstractDependency dependency = null;
		String name = getAttributeValue(element, Symbol.XML_PROPERTY_BEAN_PROPERTY_NAME);
		if (name != null) {
			dependency = resolveDependency(element);
		} else {

		}
		if (dependency != null) {
			dependencys.put(name, dependency);
			Logger.i("依赖：" + dependency.toString());
		} else {
			Logger.e("依赖：空");
		}
	}

	private AbstractDependency resolveDependency(Element element)
			throws ClassNotFoundException {
		AbstractDependency dependency = null;
		String value = getElementValue(element);
		String reference = getAttributeValue(element, Symbol.XML_PROPERTY_BEAN_PROPERTY_REFERENCE);
		String type = getAttributeValue(element, Symbol.XML_PROPERTY_BEAN_PROPERTY_CLASS);
		Logger.i("-------------------------分割线----------------------------");
		Logger.i("property节点" + element.asXML());

		// 如果存在value
		if (value != null && reference == null && type == null) { // 值依赖
			DefinitionDependency dd = FrameworkFactory.getInstance().build(
					PineApple.getContext(), DefinitionDependency.class);
			SimpleValueBeanDefinition simpleValueBeanDefinition = getSimpleValueBeanDefinition(value);
			dd.setBeanDefinition(simpleValueBeanDefinition);
			dependency = dd;
		} else if (value == null && (reference != null || type != null)) { // 引用依赖
			dependency = resolveReferenceItem(element);

		} else if (value == null && (reference == null && type == null)) { // 有可能是map或list（依赖类型不确定）
			if (hasChildElement(element) && element.elements().size() == 1) {
				DefinitionDependency dd = null;
				AbstractBeanDefinition listOrMapBeanfinition = getListOrMapBeanfinition(element);
				if (listOrMapBeanfinition != null) {
					dd = FrameworkFactory.getInstance().build(
							PineApple.getContext(), DefinitionDependency.class);
					dd.setBeanDefinition(listOrMapBeanfinition);
				}
				dependency = dd;
			} else if (element.elements().size() > 1) {
				Logger.e("property的子节点只能有一个！");
			} else {
				Logger.e("xml配置的信息不完整");
			}
		}
		return dependency;
	}

	/** 解析获取FactoryDefinition */
	private FactoryBeanDefinition getFactoryBeanfinition(Element element) {
		Map<String, AbstractDependency> dependencies = getDependencys(element);
		FactoryBeanDefinition factoryBeanDefinition = FrameworkFactory
				.getInstance().build(PineApple.getContext(),
						FactoryBeanDefinition.class);
		factoryBeanDefinition.setDependencies(dependencies);
		return factoryBeanDefinition;
	}

	/***
	 * 解析获取List或map 的beanfinition
	 */
	private AbstractBeanDefinition getListOrMapBeanfinition(Element element)
			throws ClassNotFoundException {
		String propertyChildElementType = getPropertyChildElementType(element);
		AbstractBeanDefinition rld = null;
		if (Symbol.XML_ELEMENT_LIST.equals(propertyChildElementType)) {
			rld = resolveListDependency(element);
		} else if (Symbol.XML_ELEMENT_MAP.equals(propertyChildElementType)) {
			rld = resolveMapDependency(element);
		} else {
			Logger.e(element.asXML() + "子节点只能是List 或 map");
		}
		return rld;
	}

	/***
	 * 获取SimpleValueBeanDefinition
	 * 
	 * @param value
	 * @return
	 */
	private SimpleValueBeanDefinition getSimpleValueBeanDefinition(String value) {
		SimpleValueBeanDefinition simpleValueBeanDefinition = FrameworkFactory
				.getInstance().build(PineApple.getContext(),
						SimpleValueBeanDefinition.class);
		simpleValueBeanDefinition.setValue(value);
		return simpleValueBeanDefinition;
	}

	/***
	 * 解析MapDependency
	 * 
	 * @param element
	 * @return
	 * @throws ClassNotFoundException
	 */
	private AbstractBeanDefinition resolveMapDependency(Element element)
			throws ClassNotFoundException {
		AbstractBeanDefinition definition = null;
		Element mapElement = element.element(Symbol.XML_ELEMENT_MAP);
		if (hasChildElement(mapElement)) {
			MapBeanDefinition md = FrameworkFactory.getInstance().build(
					PineApple.getContext(), MapBeanDefinition.class);
			Map<String, AbstractDependency> dependencyMap = new HashMap<String, AbstractDependency>();
			List<Element> itemElements = mapElement
					.selectNodes(Symbol.XML_ELEMENT_ITEM);
			for (Element itemElement : itemElements) {
				String key = getAttributeValue(itemElement, "key");
				if (key != null) {
					AbstractDependency itemDependency = resolveDependency(itemElement);
					dependencyMap.put(key, itemDependency);
				} else {
					Logger.e(itemElement.asXML() + "MapDependency的item缺少key");
				}
			}
			md.setMap(dependencyMap);
			definition = md;
		} else {
			Logger.e(element.asXML() + "没有子节点,不符合");
		}
		return definition;
	}

	/***
	 * 解析ListDependency
	 * 
	 * @param element
	 * @return
	 * @throws ClassNotFoundException
	 */
	private AbstractBeanDefinition resolveListDependency(Element element)
			throws ClassNotFoundException {
		AbstractBeanDefinition definition = null;
		Element listElement = element.element(Symbol.XML_ELEMENT_LIST);
		if (hasChildElement(listElement)) {
			ListBeanDefinition lbd = FrameworkFactory.getInstance().build(
					PineApple.getContext(), ListBeanDefinition.class);
			List<AbstractDependency> dependencieList = new ArrayList<AbstractDependency>();
			List<Element> itemElements = listElement
					.selectNodes(Symbol.XML_ELEMENT_ITEM);
			for (Element itemElement : itemElements) {
				AbstractDependency itemDependency = resolveDependency(itemElement);
				dependencieList.add(itemDependency);
			}
			lbd.setList(dependencieList);
			definition = lbd;
		} else {
			Logger.e(element.asXML() + "没有子节点,不符合");
		}
		return definition;
	}

	/***
	 * 获取子节点的节点名字
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getPropertyChildElementType(Element element) {
		String propertyChildElementType = null;
		List<Element> elements = element.elements();
		Element e = elements.get(0);
		if (Symbol.XML_ELEMENT_LIST.equalsIgnoreCase(e.getName())) {
			propertyChildElementType = Symbol.XML_ELEMENT_LIST;
		} else if (Symbol.XML_ELEMENT_MAP.equals(e.getName())) {
			propertyChildElementType = Symbol.XML_ELEMENT_MAP;
		} else {

		}
		return propertyChildElementType;
	}

	private AbstractDependency resolveReferenceItem(Element e)
			throws ClassNotFoundException {
		AbstractDependency dependency = null;
		String reference = getAttributeValue(e, "reference");
		String type = getAttributeValue(e, "class");
		if (reference != null && type != null) {
			IdAndTypeDependency itd = FrameworkFactory.getInstance().build(
					PineApple.getContext(), IdAndTypeDependency.class);
			itd.setId(reference);
			itd.setType(getClass(type));
			dependency = itd;
		} else if (reference != null) {
			IdDependency id = FrameworkFactory.getInstance().build(
					PineApple.getContext(), IdDependency.class);
			id.setBeanId(reference);
			dependency = id;
		} else if (type != null) {
			TypeDependency td = FrameworkFactory.getInstance().build(
					PineApple.getContext(), TypeDependency.class);
			td.setType(getClass(type));
			dependency = td;
		}
		return dependency;
	}

	// ---------------------------------------------------------------------------------

	/** 检查是否有id，如果有则以id作为beanId,否则以类名首字母小写的方式作为beanId */
	private String getDecorationKey(String id, String type) {
		String key = id;
		if (id == null) {
			if (type != null) {
				String className = type.substring(type.lastIndexOf(".") + 1);
				key = StringUtils.toLowerCaseFirstOne(className);
			} else {
				Logger.e("bean的id和type都为空");
			}
		}
		return key;
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
	 * 获取依赖对象
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, AbstractDependency> getDependencys(Element element) {
		Map<String, AbstractDependency> dependencys = new HashMap<String, AbstractDependency>();
		List<Element> propertyElements = element.selectNodes("property");
		if (propertyElements != null && propertyElements.size() > 0) {
			// 遍历property
			for (Element e : propertyElements) {
				try {
					resolveProperty(dependencys, e);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			Logger.i(element.getName() + "--没有依赖");
		}
		return dependencys;
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
			attributeValue = attribute.getValue().trim();
		}
		return attributeValue;
	}

	/***
	 * 获取节点的boolean 类型的属性值
	 * 
	 * @param element
	 *            节点对象
	 * @param attributeName
	 *            属性名称
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean getBooleanAttributeValue(Element element,
			String attributeName) {
		boolean attributeValue = true;
		Attribute attribute = element.attribute(attributeName);
		if (attribute != null) {
			String value = attribute.getValue();
			if (value.equalsIgnoreCase("false")) {
				attributeValue = false;
			}
		}
		return attributeValue;
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

	private Class getClass(String className) {
		if (className != null) {
			try {
				Class<?> forName = Class.forName(className);
				return forName;
			} catch (ClassNotFoundException e) {
				Logger.e(className + "找不到该类");
			}
		}
		return null;
	}

	/**
	 * 判断是否是List或map
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private boolean isListOrMapElement(Element element) {
		List<Element> elements = element.elements();
		if (elements.size() == 1
				&& (Symbol.XML_ELEMENT_LIST
						.equals(getPropertyChildElementType(element)) || Symbol.XML_ELEMENT_MAP
						.equals(getPropertyChildElementType(element)))) {
			return true;
		}
		return false;
	}
}
