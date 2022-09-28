package com.revature.exceptions;

public class GameUserNotFoundException extends Exception {

	public GameUserNotFoundException(String message) {
		super(message);
	}

	public GameUserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GameUserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public GameUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public GameUserNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
