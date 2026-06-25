package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sun.net.httpserver.HttpsConfigurator;

@ControllerAdvice
public class ExceptionHandling{

	@ExceptionHandler(EmployeeNotFoundException.class)
	public  ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex){
		
		ErrorResponse response = new ErrorResponse();
		response.setStatus(404);
		response.setError(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
