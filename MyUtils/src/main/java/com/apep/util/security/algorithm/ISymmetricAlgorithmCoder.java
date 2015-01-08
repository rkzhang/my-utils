package com.apep.util.security.algorithm;

import com.apep.util.base64.Base64Coder;
import com.apep.util.base64.UrlBase64Coder;

public abstract class ISymmetricAlgorithmCoder {
	
	private final static String ENCODING = "UTF-8";

	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public abstract byte[] decrypt(byte[] data, byte[] key) throws Exception;
	
	public String decrypt(String data, String key) throws Exception {
		byte[] encryptStr = decrypt(Base64Coder.decodeToByte(data), Base64Coder.decodeToByte(key));
		return new String(encryptStr, ENCODING);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public abstract byte[] encrypt(byte[] data, byte[] key) throws Exception;
	
	public String encrypt(String data, String key) throws Exception{
		byte[] encryptStr = encrypt(data.getBytes(ENCODING), Base64Coder.decodeToByte(key));
		return UrlBase64Coder.encode(encryptStr);
	}
	
	/**
	 * 初始化密钥
	 * @return
	 * @throws Exception
	 */
	public abstract byte[] initKey() throws Exception;
	
	public String initKeyToBase64Str() throws Exception {
		return UrlBase64Coder.encode(initKey());
	}
	
}
