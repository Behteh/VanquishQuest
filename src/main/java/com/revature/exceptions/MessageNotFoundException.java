package com.revature.exceptions;

public class MessageNotFoundException extends Exception {

	public MessageNotFoundException() {
	}

	public MessageNotFoundException(String message) {
		super(message);
	}

	public MessageNotFoundException(Throwable cause) {
		super(cause);
	}

	public MessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
