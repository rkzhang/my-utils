package com.apep.security.test;

import junit.framework.TestCase;
import org.junit.Test;

import com.apep.util.security.algorithm.DESedeCoder;

public class DESedeCoderTest extends TestCase{

	@Test
	public final void test() throws Exception { 
		
		String inputStr = "DES加密解密";
		System.out.println("加密字符串:\t" + inputStr);
		
		DESedeCoder desCoder = new DESedeCoder();
		String key = desCoder.initKeyToBase64Str();
		System.out.println("密钥:\t" + key);
		
		String encryptData = desCoder.encrypt(inputStr, key);
		System.out.println("加密:\t" + encryptData);
		
		String decryptData = desCoder.decrypt(encryptData, key);
		System.err.println("解密:\t" + decryptData);
		
		assertEquals(inputStr, decryptData);
	}
}
