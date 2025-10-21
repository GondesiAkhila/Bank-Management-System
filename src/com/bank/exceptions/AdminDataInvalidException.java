package com.bank.exceptions;

public class AdminDataInvalidException extends RuntimeException{
	public String getMessage;
	
	public AdminDataInvalidException() {}
	
	public String getGetMessage() {
		return getMessage;
	}

	public void setGetMessage(String getMessage) {
		this.getMessage = getMessage;
	}

	public AdminDataInvalidException(String getMessage) {
		super();
		this.getMessage = getMessage;
	}

	@Override
	public String toString() {
		return getClass()+ "AdminDataInvalidException [getMessage=" + getMessage + "]";
	}
	
	
}
