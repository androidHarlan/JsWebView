package com.tentcoo.pineapple.core.common.constant;


public class Key {
	
	/**key-value配置的文件路径 的  key,用于从map获取路径*/
	public static String CUSTOM_CONFIG_INPUT = "ROOT_CONFIG_CUSTOM_INPUT";
	/**key-value配置文件配置的解析器的 key,用于从map获取解析器类名*/
	public static String CUSTOM_CONFIG_RESOLVER = "ROOT_CONFIG_CUSTOM_RESOLVER";
	
	public static String TRANSACTION_URL ="url"; //请求的路径
	public static String TRANSACTION_ID="id";	//请求的编号，也是这个交易的编号，有页面决定
	public static String TRANSACTION_PARAMETER="params";	//这个交易的参数
	public static String TRANSACTION_REQUEST_HEADER="header";  //header的请求头信息
	
	public static String REQUEST_HEADER_REQUEST_FORMATTER="request_formatter"; //请求头部中，用于限定请求参数格式的键
	public static String REQUEST_HEADER_RESPONSE_FORMATTER="response_formatter"; //请求头部中，用于规范响应信息格式的键
	public static String SCHEMA_MODEL_RETURN_VALUE_KEY="returnValue";  //用于在model总存放service的返回对象的默认键
	public static String BRIDGE_HANDLER_REGISTER="submitFromWeb";  //submitFromWeb,jsbridge中注册为原生handler的key
	
//	public static String FORMAT_JSON = "json";
//	public static String FORMAT_OBJECT = "object";
	
	
	
}
