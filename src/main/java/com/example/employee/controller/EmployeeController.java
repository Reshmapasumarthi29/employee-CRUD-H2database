package com.example.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {

	private EmployeeServiceImpl employeeServiceImpl;
	
	
	
	public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
		this.employeeServiceImpl = employeeServiceImpl;
	}


   @GetMapping("/getEmployees")
	public ResponseEntity<List<Employee>> allEmployees(){
	   List<Employee> allEmp = employeeServiceImpl.getAllEmployees();
		return new ResponseEntity<>(allEmp,HttpStatus.OK);
	}
   
   @GetMapping("/getEmployee/{id}")
   public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
	   Employee emp = employeeServiceImpl.getEmployee(id);
	   return new ResponseEntity<Employee>(emp, HttpStatusCode.valueOf(200)) ;
	   
   }
   
   @PostMapping("/add")
   public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
	   
	   Employee emp = employeeServiceImpl.addEmployee(employee);
	   return new ResponseEntity<Employee>(emp, HttpStatusCode.valueOf(201));
   }
   
   
   @PutMapping("/update/{id}")
   public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
	   Employee emp =employeeServiceImpl.updateEmployee(id, employee);
	   return new ResponseEntity<Employee>(emp, HttpStatus.OK);
   }
   
   @DeleteMapping("/delete/{id}")
   public String deleteEmployee(@PathVariable long id) {
	   employeeServiceImpl.deleteEmployee(id);
   return "deleted sucessfully";
   }
}
