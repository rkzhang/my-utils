package com.apep.util.base64;

import org.apache.commons.codec.binary.Base64;

public abstract class Base64Coder {
	
	public final static String ENCODING = "UTF-8";
	
	/**
	 * Base64
	 * @param data 
	 * @return String
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception{
		
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	
	public static String encode(byte[] data) throws Exception{
	
		byte[] b = Base64.encodeBase64(data);
		return new String(b, ENCODING);
	}
	
	/**
	 * Base64
	 * RFC 2045
	 * @param data 
	 * @return String
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception{
		
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING),true);
		return new String(b , ENCODING);
	}
	
	public static String encodeSafe(byte[] data) throws Exception{
		
		byte[] b = Base64.encodeBase64(data, true);
		return new String(b , ENCODING);
	}
	
	/**
	 * Base64
	 * @param data 
	 * @return String 
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception{
		
		byte[] b = Base64.decodeBase64(data);
		return new String(b, ENCODING);
	}
	
	/**
	 * Base64
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeToByte(String data) throws Exception{		
		return Base64.decodeBase64(data);
	}
}
