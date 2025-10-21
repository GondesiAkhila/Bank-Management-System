package com.bank.exceptions;

public class CustomerDataInvalidException extends RuntimeException{
	private String exceptiomMsg;
	public CustomerDataInvalidException() {};
	public CustomerDataInvalidException(String exceptiomMsg) {
		super();
		this.exceptiomMsg = exceptiomMsg;
	}

	public String getExceptiomMsg() {
		return exceptiomMsg;
	}

	public void setExceptiomMsg(String exceptiomMsg) {
		this.exceptiomMsg = exceptiomMsg;
	}

	@Override
	public String toString() {
		return getClass()+"CustomerDataInvalidException [exceptiomMsg=" + exceptiomMsg + "]";
	}
	
}
