package com.cts.rabo.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.rabo.constants.RaboConstants;
import com.cts.rabo.model.exception.ApiErrorResponse;
import com.cts.rabo.model.exception.RaboFileFormatException;
import com.cts.rabo.model.exception.RaboRuntimeException;

/**
 * 
 * @author ilankumaran
 *
 * Custom response handler when exception occurs.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 * @param e       Exception file other than CSV and XML.
	 * @param request Http request parameter.
	 * @return API response for file other than CSV and XML.
	 */
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(RaboFileFormatException.class)
	public ResponseEntity<ApiErrorResponse> invalidFileException(RaboFileFormatException e, WebRequest request) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setMessage(e.getMessage());
		apiErrorResponse.setError(RaboConstants.fileFormatMsg);
		apiErrorResponse.setStatus(HttpStatus.FORBIDDEN);
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * @param e       Exception for IO exception handler.
	 * @param request request Http request parameter.
	 * @return API response IO Exception.
	 */
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(RaboRuntimeException.class)
	public ResponseEntity<ApiErrorResponse> raboException(RaboRuntimeException e, WebRequest request) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setMessage(e.getMessage());
		apiErrorResponse.setError(RaboConstants.fileParsingMsg);
		apiErrorResponse.setStatus(HttpStatus.FORBIDDEN);
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * @param e       Global(any) exception handler.
	 * @param request Http request parameter.
	 * @return API response any other than RaboRuntimeException and
	 *         RaboFileFormatException.
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> globalException(Throwable e, WebRequest request) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
		apiErrorResponse.setMessage(e.getMessage());
		apiErrorResponse.setError(RaboConstants.unExpectedMsg);
		apiErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.OK);
	}

}
