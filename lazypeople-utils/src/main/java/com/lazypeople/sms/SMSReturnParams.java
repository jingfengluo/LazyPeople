package com.lazypeople.sms;

public class SMSReturnParams {
	
	private int httpRetCode;
	
	private String other;

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int getHttpRetCode() {
		return httpRetCode;
	}

	public void setHttpRetCode(int httpRetCode) {
		this.httpRetCode = httpRetCode;
	}
	
}
