package com.apep.util.message.digest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA
 * @author rkzhang
 */
public abstract class SHACoder {

	/**
	 * SHA
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeSHA(String data) throws Exception {
		return DigestUtils.sha(data);
	}
	
	/**
	 * SHAHex
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHAHex(String data) throws Exception {
		return DigestUtils.sha256Hex(data);
	}
	
	/**
	 * SHA256
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeSHA256(String data) throws Exception {
		return DigestUtils.sha256(data);
	}
	
	/**
	 * SHA256Hex
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHA256Hex(String data) throws Exception {
		return DigestUtils.sha256Hex(data);
	}
	
	/**
	 * SHA384
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeSHA384(String data) throws Exception {
		 return DigestUtils.sha384(data);
	}
	
	/**
	 * SHA384Hex
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHA384Hex(String data) throws Exception {
		return DigestUtils.sha384Hex(data);
	}
	
	/**
	 * SHA512Hex
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeSHA512(String data) throws Exception {
		return DigestUtils.sha512(data);
	}
	
	/**
	 * SHA512Hex
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHA512Hex(String data) throws Exception {
		 return DigestUtils.sha512Hex(data);
	}
}
