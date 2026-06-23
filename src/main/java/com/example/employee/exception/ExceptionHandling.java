package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sun.net.httpserver.HttpsConfigurator;

@ControllerAdvice
public class ExceptionHandling{

	@ExceptionHandler(EmployeeNotFoundException.class)
	public  ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
