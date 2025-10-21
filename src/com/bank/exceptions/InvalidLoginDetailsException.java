package com.bank.exceptions;

public class InvalidLoginDetailsException extends RuntimeException{
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClass()+" Entered Login Details are Invalid....";
	}
}
