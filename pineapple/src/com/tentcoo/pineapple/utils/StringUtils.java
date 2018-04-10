package com.tentcoo.pineapple.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringUtils {

	public String[] setStrToArray(String str, String type) {
		return str.split(type);
	}

	/** 首字母小写 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**字符串转成流*/
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream iss = new ByteArrayInputStream(sInputString.getBytes());
				return iss;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
