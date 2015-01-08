package com.apep.security.test;

import org.junit.Test;

import com.apep.util.UUIDGenerator;
import com.apep.util.security.algorithm.PBECoder;

import junit.framework.TestCase;

public class PBECoderTest  extends TestCase{

	@Test
	public final void test() throws Exception { 
		String inputStr = "DES解密加密数据测试法顺风使舵斯蒂芬斯蒂法是";
		System.out.println("密文:\t" + inputStr);
	
		String salt = "afkx0830";
		String password = "zhangrongk@126.com";	
		
		String encryptData = PBECoder.encrypt(inputStr, password, salt);
		System.out.println("加密:\t" + encryptData);
		
		String decryptData = PBECoder.decrypt(encryptData, password, salt);
		System.err.println("解密:\t" + decryptData);
		
		assertEquals(inputStr, decryptData);

	}

}
