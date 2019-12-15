package com.cts.rabo.model.exception;

public class InvalidFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int status;

	public InvalidFileException(String exceptionMeassage) {
		super(exceptionMeassage);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}