package com.tentcoo.pineapple.utils;

public class TypeUtils {
	private static final String CHAR = "char";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";

	/**
	 * 获取
	 * 
	 * @param type
	 * @return
	 */
	public static Class getClassType(String type) {
		Class clazz = null;
		if (CHAR.equalsIgnoreCase(type)) {
			clazz = Character.class;
		} else if (BYTE.equalsIgnoreCase(type)) {
			clazz = Byte.class;
		} else if (SHORT.equalsIgnoreCase(type)) {
			clazz = Short.class;
		} else if (INT.equalsIgnoreCase(type)) {
			clazz = Integer.class;
		} else if (LONG.equalsIgnoreCase(type)) {
			clazz = Long.class;
		} else if (FLOAT.equalsIgnoreCase(type)) {
			clazz = Float.class;
		} else if (DOUBLE.equalsIgnoreCase(type)) {
			clazz = Double.class;
		}
		return clazz;
	}

	public static boolean isNumType(String type) {
		if (CHAR.equalsIgnoreCase(type) 
				|| BYTE.equalsIgnoreCase(type)
				|| INT.equalsIgnoreCase(type) 
				|| SHORT.equalsIgnoreCase(type)
				|| LONG.equalsIgnoreCase(type) 
				|| FLOAT.equalsIgnoreCase(type)
				|| DOUBLE.equalsIgnoreCase(type)) {
			return true;
		}
		return false;
	}
}
