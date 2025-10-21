package com.bank.exceptions;

public class InvalidCaptchaException extends RuntimeException{
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClass()+" Entered Captcha is Invalid...";
	}
}
