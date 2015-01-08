package com.apep.security.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.apep.util.security.algorithm.AESCoder;



public class AESCoderTest extends TestCase{

	@Test
	public final void test() throws Exception { 
		String inputStr = "AES测试用例";
		System.out.println("输入:\t" + inputStr);
		
		AESCoder desCoder = new AESCoder();
		String key = desCoder.initKeyToBase64Str();
		System.out.println("KEY:\t" + key);
		
		String encryptData = desCoder.encrypt(inputStr, key);
		System.out.println("密文:\t" + encryptData);
		
		String decryptData = desCoder.decrypt(encryptData, key);
		System.err.println("解密:\t" + decryptData);
		
		//
		assertEquals(inputStr, decryptData);
	}
}
