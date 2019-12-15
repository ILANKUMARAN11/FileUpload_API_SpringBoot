package com.cts.rabo.exception;

import com.cts.rabo.model.exception.InvalidFileException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.cts.rabo.exception.handler.RestExceptionHandler;
import com.cts.rabo.model.exception.ApiErrorResponse;
import com.cts.rabo.model.exception.RaboRuntimeException;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviseTest {
	
	@InjectMocks
    RestExceptionHandler restExceptionHandler;
	
	@Mock
	WebRequest request;


	@Test
	public void invalidFileExceptionTest() {

		ResponseEntity<ApiErrorResponse> rabo=restExceptionHandler.invalidFileException(new InvalidFileException("Test"), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}

	@Test
	public void raboExceptionTest() {
		
		ResponseEntity<ApiErrorResponse> rabo=restExceptionHandler.raboException(new RaboRuntimeException("Test"), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}


	@Test
	public void globalExceptionTest() {

		ResponseEntity<ApiErrorResponse> rabo=restExceptionHandler.globalException(new Throwable("Test"), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}

}
