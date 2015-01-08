package com.apep.util.security.algorithm;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DH算法
 * @author rkzhang
 */
public abstract class DHCoder {
	
	public static final String KEY_ALGORITHM = "DH";
	
	public static final String SECRET_ALGORITHM = "AES";
	
	private static final int KEY_SIZE = 512;
	
	private static final String PUBLIC_KEY = "DHPulbicKey";
	
	private static final String PRIVATE_KEY = "DHPrivateKey";
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		DHPublicKey publicKey = (DHPublicKey)keyPair.getPublic();
		DHPrivateKey privateKey = (DHPrivateKey)keyPair.getPrivate();
		
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(byte[] key) throws Exception {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		DHParameterSpec dhParamSpec = ((DHPublicKey)pubKey).getParams();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
		keyPairGenerator.initialize(dhParamSpec);
		
		KeyPair keyPair = keyPairGenerator.genKeyPair();
				
		DHPublicKey publicKey = (DHPublicKey)keyPair.getPublic();
		
		DHPrivateKey privateKey = (DHPrivateKey)keyPair.getPrivate();
		
	
		Map<String, Object>keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 加密
	 * @param data 
	 * @param key 
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
	
		SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
		//��ݼ���
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		
		SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
		
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 
	 * @param publicKey 
	 * @param privateKey
	 * @return byte[] 
	 * @throws Exception
	 */
	public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception{

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
	
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
	
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		

		 SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);
		return secretKey.getEncoded();	
	}
	
	
}
