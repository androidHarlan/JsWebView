package com.tentcoo.pineapple.core.common.constant;

public class Value {
	public static final String DEFAULT_CONFIG_INPUT="xml/config.xml"; //默认的配置文件路径值
	public static final String DEFAULT_CONFIG_RESOLVER="com.tentcoo.pineapple.core.framework.config.DefaultRootXmlConfigResolver"; //默认配置文件解析器权限定类名
	public static final String BASIC_CONFIG_RESOLVER=""; //基础配置文件解析器权限定类名;
	public static final String REQUEST_FORMATTER_JSON = "json"; //将请求参数按json格式解析的请求头格式标志
	public static final String REQUEST_FORMATTER_OBJECT="object"; //将请求参数按对象格式解析的请求头格式标志
	public static final String REQUEST_FORMATTER_XML="XML";		//将请求参数按xml格式解析的请求头格式标志
	
	public static final String RESPONSE_FORMATTER_JSON = "json"; //将响应信息按json格式解析的请求头格式标志
	public static final String RESPONSE_FORMATTER_OBJECT="object"; //将响应信息按对象格式解析的请求头格式标志
	public static final String RESPONSE_FORMATTER_XML="xml";		//将响应信息按xml格式解析的请求头格式标志
	
	public static final String SCHEMA_NAME_JSON="json";  //用于将结果渲染为json格式的schemaName;
	public static final String SCHEMA_NAME_XML ="xml";  //用于将结果渲染为xml格式的schemaName
	
	public static final String SCHEMA_NAME_OBJECT="object"; //自己定的，
	public static final String REQUEST_TYPE_HTTP="http";
	
}
