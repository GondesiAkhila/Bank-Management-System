package com.bank.exceptions;

public class InvalidDetailsException extends RuntimeException{
@Override
public String toString() {
	// TODO Auto-generated method stub
	return getClass()+" Invalid Details Entered.....";
}
}
