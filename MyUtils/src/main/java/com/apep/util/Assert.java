package com.apep.util;

import com.apep.util.exception.DataException;

public class Assert {
	
	public static void notNull(Object obj){
		if(obj == null){
			throw new DataException("obj can not be null");
		}
	}
}
