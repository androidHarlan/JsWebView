//package com.tentcoo.pineapple.utils;
//
//import java.security.KeyStore;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.HttpVersion;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.params.ConnManagerParams;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//public class RequestHttpUtils {
//	public static String RequestHttps(String httpUrl, Map<String, String> params) {
//		try {
//			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//			if (params != null && params.size() > 0) {
//				Iterator<Entry<String, String>> iterator = params.entrySet()
//						.iterator();
//				while (iterator.hasNext()) {
//					Entry<String, String> next = iterator.next();
//					NameValuePair pair = new BasicNameValuePair(next.getKey(),
//							next.getValue());
//					paramList.add(pair);
//				}
//
//			}
//
//			HttpParams httpParameters = new BasicHttpParams();
//			// 设置连接管理器的超时
//			ConnManagerParams.setTimeout(httpParameters, 10000);
//			// 设置连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
//			// 设置socket超时
//			HttpConnectionParams.setSoTimeout(httpParameters, 10000);
//			HttpClient hc = getHttpClient(httpParameters);
//			HttpPost post = new HttpPost(httpUrl);
//			post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
//			post.setParams(httpParameters);
//			HttpResponse response = null;
//			response = hc.execute(post);
//			int sCode = response.getStatusLine().getStatusCode();
//			if (sCode == HttpStatus.SC_OK) {
//				String result = EntityUtils.toString(response.getEntity(),
//						"utf-8");
//				return result;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return "";
//	}
//
//	/**
//	 * 获取HttpClient
//	 *
//	 * @param params
//	 * @return
//	 */
//	public static HttpClient getHttpClient(HttpParams params) {
//		try {
//			KeyStore trustStore = KeyStore.getInstance(KeyStore
//					.getDefaultType());
//			trustStore.load(null, null);
//
//			SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
//			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//			HttpProtocolParams.setUseExpectContinue(params, true);
//
//			// 设置http https支持
//			SchemeRegistry registry = new SchemeRegistry();
//			registry.register(new Scheme("http", PlainSocketFactory
//					.getSocketFactory(), 80));
//			registry.register(new Scheme("https", sf, 443));// SSL/TSL的认证过程，端口为443
//			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
//					params, registry);
//			return new DefaultHttpClient(ccm, params);
//		} catch (Exception e) {
//			return new DefaultHttpClient(params);
//		}
//	}
//}
