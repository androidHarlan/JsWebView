package com.tentcoo.pineapple.core.dispatch.mapping;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.utils.Logger;


@SuppressWarnings("unused")
public class AntWildcardMatcher extends BasicFactoryObject implements IRuleMatcher{

	@Override
	public boolean match(String url, String rule) {
		Logger.e("AntWildcardMatcher参数url:"+url+",rule："+rule);
		if(url==null || rule==null ||"".equals(url) || "".equals(rule)){
			return false;
		}
		String[] urlStrArray = url.split("/");
		String[] ruleStrArray = rule.split("/");
		String ruleStr = ruleStrArray[ruleStrArray.length-1];   //获取数组的最后一个值
		String urlStr = urlStrArray[urlStrArray.length-1];		//获取数组的最后一个值
		int urlStart = 0;
		int urlEnd = urlStrArray.length-1;				//url拆分数组的最大索引
		int ruleStart = 0;
		int ruleEnd = ruleStrArray.length-1;
		while(urlStart<=urlEnd && ruleStart<=ruleEnd){
			if(ruleStrArray[ruleStart].equals(urlStrArray[urlStart])){
			}else if(ruleStrArray[ruleStart].equals("**")){
				//璇存槑涓嶆槸锟??鍚庝竴涓簡,鍙兘锟??/**/a/?.j鐨勬牸锟??
				if(ruleEnd>=ruleStart+1){
//					if(isMatch(ruleStrArray[ruleStart+1], urlStrArray[urlStart])){
					if(urlStrArray[urlStart].equals(ruleStrArray[ruleStart+1])){
						ruleStart+=2;
						urlStart++;
						if(ruleEnd==ruleStart){
							break;
						}
						continue;
					}else{
						if(urlStart==urlEnd){
//							return false;
							break;
						}else{
							urlStart++;
							continue;
						}
					}
				}else{//璇存槑宸茬粡鍖归厤瀹屾瘯
					break;
				}
			}else{
				String ruleStrTemp = ruleStrArray[ruleStart];
				String urlStrTemp = urlStrArray[urlStart];
				if(!isMatch(ruleStrTemp,urlStrTemp)){
					return false;
				}
			}
			urlStart++;
			ruleStart++;
		}
		//璇存槑涔嬪墠鐨勯兘鍖归厤瀹屼簡锛屽紑濮嬪尮閰嶆渶鍚庝竴锟??
		if(!isMatch(ruleStr, urlStr)){			
			return false;
		}else{
			return true;
		}
	}
	
	public  boolean isMatch(String ruleStr,String urlStr){
		char[] ruleChars = ruleStr.toCharArray();
		char[] urlChars = urlStr.toCharArray();
		int ruleCharStart = 0;
		int urlCharStart = 0;
		int ruleCharEnd = ruleChars.length-1;
		int urlCharEnd = urlChars.length-1;
		boolean isMatch = true;
		while(ruleCharStart<=ruleCharEnd&&urlCharStart<=urlCharEnd){
			String ruleCharStr = String.valueOf(ruleChars[ruleCharStart]);
			String urlCharStr = String.valueOf(urlChars[urlCharStart]);
			if(ruleCharStr.equals(urlCharStr)||ruleCharStr.equals("?")){
				if(ruleCharStr.equals(".")&&ruleCharStart==(ruleCharEnd)){
					return true;
				}
			}else if(ruleCharStr.equals("*")){
				if(ruleCharStart+1<=ruleCharEnd){
					String ruleNewCharStr =  String.valueOf(ruleChars[ruleCharStart+1]);
					//璇存槑涓嶆槸锟??鍚庝竴涓簡,鍙兘锟??/**/a/?.j鐨勬牸锟??
					if(ruleCharEnd>=ruleCharStart+1&&!ruleNewCharStr.equals("?")){
						if(urlCharStr.equals(ruleNewCharStr)&&urlCharStart!=0||ruleNewCharStr.equals("*")){
							ruleCharStart+=2;
							urlCharStart++;
							if(ruleNewCharStr.equals(".")){
								return true;
							}
							if(ruleCharEnd==(ruleCharStart-1)){
								break;
//								continue;
							}
							continue;
						}else{
							if(urlCharStart==urlCharEnd){
								return false;
							}else{
								urlCharStart++;
								continue;
							}
						}
					}else {//璇存槑宸茬粡鍖归厤瀹屾瘯
//						break;
						urlCharStart++;
						continue;
					}
				}else{
					break;
				}
			}else{
				isMatch = false;
				break;
			}
			urlCharStart++;
			ruleCharStart++;
		}
		if((ruleCharStart-1)!=ruleCharEnd||(urlCharStart-1)!=urlCharEnd
				||(ruleCharStart>ruleCharEnd&&urlCharStart==urlCharEnd)
				||(ruleCharStart<ruleCharEnd&&urlCharStart==urlCharEnd)
				||(ruleCharStart==ruleCharEnd&&urlCharStart>urlCharEnd)
				||(ruleCharStart==ruleCharEnd&&urlCharStart<urlCharEnd)){//rule鍖归厤瀹屾瘯浜嗭紝浣嗗疄闄呰繕娌℃湁瀹屾瘯
			return false;
		}
		if(!isMatch){					
			return false;
		}
		return true;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
