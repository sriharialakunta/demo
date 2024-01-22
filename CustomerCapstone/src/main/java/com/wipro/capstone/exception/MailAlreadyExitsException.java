package com.wipro.capstone.exception;

public class MailAlreadyExitsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public MailAlreadyExitsException(String message) {
		super(message);
	}

	public MailAlreadyExitsException() {
		
	}
}
