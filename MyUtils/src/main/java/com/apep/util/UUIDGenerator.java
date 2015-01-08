package com.apep.util;

import java.util.UUID;

/**
 * 
 * @author rkzhang
 *
 */
public abstract class UUIDGenerator {

	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static String getUUID32Bit(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
