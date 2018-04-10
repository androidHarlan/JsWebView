package com.tentcoo.pineapple.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ToolUtil {
	public static String readFile(String filename) throws FileNotFoundException,IOException{
	    File file =new File(filename);
	    if(filename==null || filename.equals("")){
	      throw new NullPointerException("无效的文件路径");
	    }
	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
	    int r = bufferedInputStream.read( bytes );
	    if (r != len){
	      throw new IOException("读取文件不正确");
	    }
	    bufferedInputStream.close();
	    return new String(bytes);
	}
	
	//工具类,反射
	public static Class<?> getJavaClass(String path) throws ClassNotFoundException{
			Class<?> classEntity = Class.forName(path);
			return classEntity;
	}
		
	//工具类
	public static boolean isXml(String xml){
		try{			
			DocumentHelper.parseText(xml);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static String checkAttribute(Object object){
		if(object instanceof List){
			return "LIST";
		}else if(object instanceof String){
			return "STRING";
		}else if(object instanceof Map){
			return "MAP";
		}else if(object instanceof Integer){
			return "INT";
		}
		return "";
	}
	
	public static boolean isKeyValue(String data){
		String[] resolverKeyValueArray = data.split("\r\n");
		boolean isKeyValue = false;
		if(resolverKeyValueArray!=null&&resolverKeyValueArray.length>0){
			for (String keyValue : resolverKeyValueArray) {
				if(keyValue.trim().equals("")){
					continue;
				}
				if(String.valueOf(keyValue.trim().charAt(0)).equals("#")){
					continue;
				}else{
					if(keyValue.indexOf("=")<0){
						isKeyValue=false;
						break;
					}else{
						isKeyValue = true;
					}
				}
			}
		}
		return isKeyValue;
	}
	
	public static boolean isJson(String jsonStr){
		if(isObject(jsonStr)||isArray(jsonStr)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isArray(String jsonStr){
		try {
			JSONArray jArray = new JSONArray(jsonStr);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isObject(String jsonStr){
		try {
			JSONObject jArray = new JSONObject(jsonStr);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
	public static boolean isJSON(String jsonStr){
		if(!isArray(jsonStr)&&!isObject(jsonStr)){
			return false;
		}
		return true;
	}
}
