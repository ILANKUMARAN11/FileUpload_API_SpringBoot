package com.cts.rabo.model.exception;

public class RaboRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int status;

	public RaboRuntimeException(String exceptionMeassage) {
		super(exceptionMeassage);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
