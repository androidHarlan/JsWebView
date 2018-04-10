package com.tentcoo.pineapple.core.dispatch;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.tentcoo.pineapple.core.common.bean.ICore;
import com.tentcoo.pineapple.core.common.constant.Key;
import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.common.factory.IBuilder;
import com.tentcoo.pineapple.utils.JsonUtil;
import com.tentcoo.pineapple.utils.Logger;

/**交易的构建器*/
public class TransactionBuilder extends BasicFactoryObject implements IBuilder<Transaction,Object>,ICore{
	public static TransactionBuilder transactionBuilder;
	public  static synchronized TransactionBuilder getInstance() {
		if(transactionBuilder==null){
			transactionBuilder=new TransactionBuilder();
		}
		return transactionBuilder;
	}
	public String url;
	public Map<String, Object> header;
	public String params;
	public List<Object> objects;
	public String id;
	@Override
	public void build(Transaction bean, Object object) {
		 id = retrieveIdFromReqeustData((String)object);
		 url=retrieveUrlFromRequestData((String)object);
		 header=retrieveHeaderFromRequestData((String)object);
		 params=retrieveMessageFromRequestData((String)object);
		 objects=retrieveObjectFromRequestData((String)object);
		 
		 Request request = new Request();
		 request.setUrl(url);
		 request.setHeader(header);
		 request.setMessage(params);
		 request.setObject(objects);
		 Logger.d(request);
		 Response response = new Response();
		 bean.setRequest(request);
		 bean.setResponse(response);
		 bean.setId(id);
	}
	
	/**获取id*/
	private String retrieveIdFromReqeustData(String data) {
		try {
			JSONObject object=new JSONObject(data);
			String id=(String) object.optString(Key.TRANSACTION_ID,"");
			if(id==null || "".equals(id)){
				System.out.println("请求url为空!");
			}
			return id;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**从请求信息中获取请求头*/
	public Map<String,Object> retrieveHeaderFromRequestData(String data){
		try {
			JSONObject object=new JSONObject(data);
			String json_header = object.optString(Key.TRANSACTION_REQUEST_HEADER,"");
			if(json_header==null || "".equals(json_header)){
				System.out.println("请求header为空!");
				return null;
			}
			Map<String,Object> headerMap = JsonUtil.getMapForJson(json_header);
			return headerMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**从请求数据中提取请求的消息*/
	public String retrieveMessageFromRequestData(String data){
		try {
			JSONObject object=new JSONObject(data);
			String message=(String) object.optString(Key.TRANSACTION_PARAMETER,"");
			if(message==null || "".equals(message)){
				System.out.println("请求params为空!");
				return null;
			}
			return message;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**从请求数据中提取请求对象*/
	public List<Object> retrieveObjectFromRequestData(String data){
		try {
			JSONObject object=new JSONObject(data);
			String json_objects = object.optString("objects","");
			if(json_objects==null || "".equals(json_objects)){
				System.out.println("请求objects为空!");
				return null;
			}
			List<Object> objects = JsonUtil.getListForJson(json_objects);
			return objects;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**从请求数据中获取url*/
	public String retrieveUrlFromRequestData(String data){
		try {
			JSONObject object=new JSONObject(data);
			String url=(String) object.optString(Key.TRANSACTION_URL,"");
			if(url==null || "".equals(url)){
				System.out.println("请求url为空!");
				return null;
			}
			return url;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getId() {
		// TODO 这里需要设置id
		return null;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}

	
}
