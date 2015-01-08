package com.apep.security.test;

import junit.framework.TestCase;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.apep.util.security.algorithm.DESCoder;


/**
 * DES��ȫ�������У��
 * @author rkzhang
 */
public class DESCoderTest extends TestCase{

	@Test
	public final void test() throws Exception { 
		String inputStr = "DES解密加密";
		byte[] inputData = inputStr.getBytes();
		System.out.println("加密字符串:\t" + inputStr);
		DESCoder desCoder = new DESCoder();
	
		byte[] key = desCoder.initKey();
		System.out.println("密钥:\t" + Base64.encodeBase64String(key));
		
		inputData = desCoder.encrypt(inputData, key);
		System.out.println("加密:\t" + Base64.encodeBase64String(inputData));
		
		byte[] outputData = desCoder.decrypt(inputData, key);
		String outputStr = new String(outputData);
		System.err.println("解密:\t" + outputStr);
		
		assertEquals(inputStr, outputStr);
	}
	
	@Test
	public final void test2() throws Exception { 
		String inputStr = "DES��ȫ�������У��";
		System.out.println("ԭ��:\t" + inputStr);
		
		DESCoder desCoder = new DESCoder();
		String key = desCoder.initKeyToBase64Str();
		System.out.println("��Կ:\t" + key);
		
		String encryptData = desCoder.encrypt(inputStr, key);
		System.out.println("���ܺ�:\t" + encryptData);
		
		String decryptData = desCoder.decrypt(encryptData, key);
		System.err.println("���ܺ�:\t" + decryptData);
		
		//У��
		assertEquals(inputStr, decryptData);
	}
}
