package com.cts.rabo.exception;

/**
 * 
 * @author ilankumaran
 *
 */
public class RaboRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int status;

	public RaboRuntimeException(String exceptionMeassage, final int status) {
		super(exceptionMeassage);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
