package com.apep.util;


import org.apache.commons.dbcp.BasicDataSource;

import com.apep.util.exception.DataException;
import com.apep.util.security.algorithm.AESCoder;
import com.apep.util.security.algorithm.ISymmetricAlgorithmCoder;

public class SecurityDataSource extends BasicDataSource{
	
	private static final String KEY = "DRiJ6zncgYxWiBQovpt9JQ";

	private ISymmetricAlgorithmCoder aesCoder = new AESCoder();

	@Override
	public synchronized void setPassword(String password) {
		try {
			password = aesCoder.decrypt(password, SecurityDataSource.KEY);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException(e.toString());
		}
		
		super.setPassword(password);
	}
	
	public static String getKey(){
		return KEY;
	}

	public ISymmetricAlgorithmCoder getAesCoder() {
		return aesCoder;
	}

	public void setAesCoder(ISymmetricAlgorithmCoder aesCoder) {
		this.aesCoder = aesCoder;
	}

}
