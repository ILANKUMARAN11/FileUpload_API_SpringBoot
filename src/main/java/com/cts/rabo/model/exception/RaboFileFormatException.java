package com.cts.rabo.model.exception;

/**
 * 
 * @author ilankumaran
 *
 */
public class RaboFileFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int status;

	public RaboFileFormatException(String exceptionMeassage) {
		super(exceptionMeassage);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}