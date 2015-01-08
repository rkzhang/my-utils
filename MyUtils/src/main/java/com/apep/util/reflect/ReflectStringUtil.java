package com.apep.util.reflect;

public class ReflectStringUtil {

	/**
	 * 获取get方法名称
	 * @param fieldName
	 * @return
	 */
	public static String populateGetMethodName(String fieldName){
		return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
	
	/**
	 * 获取set方法名称
	 * @param fieldName
	 * @return
	 */
	public static String populateSetMethodName(String fieldName){
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}

}
