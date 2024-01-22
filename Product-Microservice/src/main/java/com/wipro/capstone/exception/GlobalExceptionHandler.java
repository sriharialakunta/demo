package com.wipro.capstone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request)
	{
		ExceptionResponse myResponse = new ExceptionResponse();
		myResponse.setErrorMessage(exception.getMessage());
		myResponse.setRequestedURI(request.getRequestURI());
		return myResponse;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody ExceptionResponse handleException(Exception exception, HttpServletRequest request)
	{
		ExceptionResponse myResponse = new ExceptionResponse();
		myResponse.setErrorMessage(exception.getMessage());
		myResponse.setRequestedURI(request.getRequestURI());
		return myResponse;
	}

}
