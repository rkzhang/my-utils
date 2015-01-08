package com.apep.util.security.algorithm;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeCoder extends ISymmetricAlgorithmCoder{
	
	/**
	 * 
	 */
	public static final String KEY_ALGORITHM = "DESede";
	
	/**
	 * 
	 */
	public static final String CIPHER_ALOGRITHM = "DESede/ECB/PKCS5Padding";
		
	/**
	 * 创建密钥
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return keyFactory.generateSecret(dks);	
	}

	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  byte[] decrypt(byte[] data, byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALOGRITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data, byte[] key) throws Exception{
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALOGRITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 
	 */
	public byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(168);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
}
