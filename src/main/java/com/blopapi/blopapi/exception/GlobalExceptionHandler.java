package com.blopapi.blopapi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blopapi.blopapi.payload.ErrorDetails;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webrequest){
		ErrorDetails error=new ErrorDetails(new Date(),
											exception.getMessage(),
											webrequest.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webrequest){
		ErrorDetails error=new ErrorDetails(new Date(),
											exception.getMessage(),
											webrequest.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
}
