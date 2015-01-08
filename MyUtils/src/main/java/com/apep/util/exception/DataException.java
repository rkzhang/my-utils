package com.apep.util.exception;

public class DataException extends RuntimeException {

	private String message;
	
	public DataException(){
		super();
	}
	
	public DataException(String message) {
        super(message);
        this.message = message;
    }
	
	 public String toString() {
	        return message;
	 }
}
