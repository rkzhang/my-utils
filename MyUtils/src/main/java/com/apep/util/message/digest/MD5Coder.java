package com.apep.util.message.digest;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author rkzhang
 */
public abstract class MD5Coder {

	public static byte[] encodeMD5(String data) throws Exception{
		
		return DigestUtils.md5(data);
	}
	
	public static String encodeMD5Hex(String data) throws Exception{
		
		return DigestUtils.md5Hex(data);
	}
	
	public static String encodeMD5HexByFile(String filePath) throws Exception {
		FileInputStream fis = new FileInputStream(new File(filePath));
		String md5hex = DigestUtils.md5Hex(fis);
		fis.close();
		return md5hex;
	}
}
