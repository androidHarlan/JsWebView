package com.tentcoo.pineapple.core.dispatch;

import java.util.List;
import java.util.Map;

public class Response {
	
	private String url;

	private List<Object> Object;

	private Map<String, Object> header;

	private String message;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public List<Object> getObject() {
		return Object;
	}

	public void setObject(List<Object> object) {
		Object = object;
	}

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [url=" + url + ", Object=" + Object + ", header="
				+ header + ", message=" + message + "]";
	}
	
	
	
}
