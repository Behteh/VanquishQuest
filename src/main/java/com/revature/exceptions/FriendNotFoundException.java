package com.revature.exceptions;

public class FriendNotFoundException extends Exception {

	public FriendNotFoundException(String message) {
		super(message);
	}

	public FriendNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FriendNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FriendNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FriendNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
