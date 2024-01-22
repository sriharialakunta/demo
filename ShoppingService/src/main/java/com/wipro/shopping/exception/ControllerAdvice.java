package com.wipro.shopping.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import feign.RetryableException;
import feign.FeignException.InternalServerError;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntitytNotFound(EntityNotFoundException ex) {
    	
    	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(FeignException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
   	
   	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
       return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(RetryableException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ResponseEntity<ErrorResponse> handleRetryableException(RetryableException ex) {
   	
   	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
       return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
   }


   @ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleInsufficientBalanceExceptions(HttpMessageNotReadableException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<Object> handleInternalServerError(InternalServerError ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
}

