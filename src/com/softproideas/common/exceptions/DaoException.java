package com.softproideas.common.exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = 197210645105433131L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}