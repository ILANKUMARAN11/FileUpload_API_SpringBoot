package com.cts.rabo.model.exception;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {

 
    private String message;
    private HttpStatus status;
    private String error;

    public ApiErrorResponse() {}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


    
	

}
