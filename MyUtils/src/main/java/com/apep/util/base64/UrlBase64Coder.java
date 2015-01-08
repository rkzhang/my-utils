package com.apep.util.base64;

import org.apache.commons.codec.binary.Base64;

public class UrlBase64Coder {
	

	public final static String ENCODING = "UTF-8";
	
	/**
	 * Url Base64
	 * @param data 
	 * @return Sring
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception{
		
		 byte[] b = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));
		 return new String(b, ENCODING);
	}
	
	public static String encode(byte[] data) throws Exception{
		
		byte[] b = Base64.encodeBase64URLSafe(data);
		return new String(b, ENCODING);
	}
	
	
	/**
	 * Url Base64
	 * @param data 
	 * @return String 
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception{
	
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(b,ENCODING);
	}
	
}
