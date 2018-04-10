package com.tentcoo.pineapple.core.common.constant;

public class Symbol {
	public static final String XML_ELEMENT_ROOT = "root"; 	// root配置
	public static final String XML_ELEMENT_CONFIG = "config"; // config,config节点入口
	public static final String XML_PROPERTY_CONFIG_KEY = "key"; // key,config节点key属性的属性名
	public static final String XML_PROPERTY_CONFIG_RESOLVERT = "resolver"; // resolver,config标签中的resovler属性
	public static final String XML_PROPERTY_CONFIG_FILE = "file"; // file,config标签的file属性的属性名
	public static final String XML_PROPERTY_CONFIG_VALUE = "value"; // value,config标签的value属性
	public static final String XML_ELEMENT_BEANS = "beans"; // beans解析器的入口标签
	public static final String XML_ELEMENT_BEAN = "bean"; // bean,bean的节点入口
	public static final String XML_PROPERTY_BEAN_ID = "id"; // id,bean的标签的id属性；
	public static final String XML_PROPERTY_BEAN_CLASS = "class"; // class,bean标签的bean类型属性；
	public static final String XML_PROPERTY_BEAN_SINGLETON = "isSingleton"; // isSingleton,bean标签中表示是否单例的属性
	public static final String XML_PROPERTY_BEAN_VALUE = "value"; // value,bean标签中标识bean值的属性
	public static final String XML_ELEMENT_LIST = "list"; // list，列表数据标签
	public static final String XML_ELEMENT_MAP = "map"; // map，字典数据标签
	public static final String XML_ELEMENT_ITEM = "item"; // item ，列表或map的数据项的标签
	public static final String XML_PROPERTY_ITEM_KEY = "key"; // KEY,在map中的键属性
	public static final String XML_PROPERTY_ITEM_VALUE = "value"; // value,item标签的值属性，标识该item的数值
	public static final String XML_PROPERTY_ITEM_REFERENCE = "reference"; // reference，item标签的引用标签，表示引用某个bean作为这个item的值
	public static final String XML_ELEMENT_BEAN_PROPERTY = "property"; // property，bean属性标签，表示一个bean的属性装配的定义
	public static final String XML_PROPERTY_BEAN_PROPERTY_REFERENCE = "reference";// reference，property标签的引用属性，表示将某个id的bean装配为该property的值
	public static final String XML_PROPERTY_BEAN_PROPERTY_VLAUE = "value"; // value,property标签的数值属性，表示将一个固定值作为该属性的值
	public static final String XML_PROPERTY_BEAN_PROPERTY_NAME = "name"; // name,属性的名称，对应一个被装配的类的属性名
	public static final String XML_PROPERTY_BEAN_PROPERTY_CLASS = "class"; // class，属性类型
	public static final String XML_ELEMENT_CONFIG_REF_ROOT="config_ref_root";
}
