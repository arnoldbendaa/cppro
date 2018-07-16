package com.softproideas.common.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 197210645105433131L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}