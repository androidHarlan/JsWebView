package com.tentcoo.pineapple.core.dispatch;

import java.util.List;
import java.util.Map;

public class Request {

	
	private String url;						//如： service.getUrl()
	
	private Map<String,Object> header;
	
	private List<Object> Object;		//告诉你请求体的内容格式  json/obect ,如 requestFormattre = json
	
	private String message;					//方法参数

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public List<Object> getObject() {
		return Object;
	}

	public void setObject(List<Object> object) {
		Object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	@Override
	public String toString() {
		return "Request [url=" + url + ", header=" + header + ", Object="
				+ Object + ", message=" + message + "]";
	}
}
