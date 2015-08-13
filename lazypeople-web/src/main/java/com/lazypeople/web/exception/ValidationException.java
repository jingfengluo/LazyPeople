package com.lazypeople.web.exception;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6203580931465771923L;
	
	private int code;
	
	public ValidationException(){}
	
	public ValidationException(String msg){
		super(msg);
	}
	
	public ValidationException(int code){
		this.code = code;
	}
	
	public ValidationException(String msg, int code){
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
