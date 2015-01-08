package com.apep.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
/**
 * @author rkzhang
 *
 */
public class DynamicMethod {
	
	private static final Log logger = LogFactory.getLog(DynamicMethod.class);
	
	public DynamicMethod(){
	}
	/**
	 * @param specifiedclass
	 * @param method
	 * @param object
	 * @return
	 */
	public static Object invokeMethod(Object specifiedclass,String method,Object object[]){
		Class<? extends Object> cls = null;
		Method meth = null;
		Object obj = null;
		Class<?> partypes[] = null;
		cls = specifiedclass.getClass();
		if(object != null){
			partypes = new Class[object.length];
			for(int i = 0 ; i < partypes.length ; i++){
				partypes[i]=object[i].getClass();
			}
		}
		try {
			meth = cls.getMethod(method, partypes);
			obj = meth.invoke(specifiedclass, object);
		}catch (SecurityException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {	
			e.printStackTrace();
		}catch (IllegalAccessException e) {	
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;	
	}
	
	/**
	 * @param specifiedclass
	 * @param method
	 * @return
	 */
	public static Object invokeMethod(Object specifiedclass,String method){
		return invokeMethod(specifiedclass,method,null);
	}
	
	/**
	 * @param className
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object constructObject(String className,Object object[]){
		Constructor ct = null;
		Class cls = null;
		Object obj = null;
		Class partypes[] = null;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(object != null){
			partypes = new Class[object.length];
			for(int i = 0 ; i < partypes.length ; i++){
				partypes[i]=object[i].getClass();
			}
		}else{
			try {
				return cls.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		try {
			ct = cls.getConstructor(partypes);
			obj = ct.newInstance(object);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;		
	}
	
	/**
	 * @param className
	 * @return
	 */
	public Object constructObject(String className){
		return constructObject(className,null);	
	}
	
	/**
	 * 判断方法名在对象中是否存在
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static boolean containsMethod(Object obj, String methodName){
		Assert.notNull(obj);
		Assert.notNull(methodName);
		
		for(Method method : obj.getClass().getDeclaredMethods()){
			if(methodName.equals(method.getName())){
				return true;
			}
		}
		return false;
	}
}
