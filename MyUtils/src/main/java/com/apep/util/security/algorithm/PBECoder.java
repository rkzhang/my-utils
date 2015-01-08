package com.apep.util.security.algorithm;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.apep.util.base64.Base64Coder;
import com.apep.util.base64.UrlBase64Coder;

public abstract class PBECoder {

	public static final String ALGORITHM = "PBEWITHMD5andDES";
	
	public static final int ITERATION_COUNT = 100;
	
	private final static String ENCODING = "UTF-8";
	
	public static byte[] initSalt() throws Exception {
		SecureRandom random = new SecureRandom();
		return random.generateSeed(8);
	}
	
	/**
	 * 密钥
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(String password) throws Exception {
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}
	
	/**
	 * 加密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception{
		Key key = toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}
	
	public static String encrypt(String data, String password, String salt) throws Exception{
		byte[] encryptStr = encrypt(data.getBytes(ENCODING), password, salt.getBytes());
		return UrlBase64Coder.encode(encryptStr);
	}
	
	
	/**
	 * 解密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception{
		Key key = toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}
	
	public static String decrypt(String data, String password, String salt) throws Exception {
		byte[] encryptStr = decrypt(Base64Coder.decodeToByte(data), password, salt.getBytes());
		return new String(encryptStr, ENCODING);
	}
}
