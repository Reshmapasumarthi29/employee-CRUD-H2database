package com.example.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.employee.dtos.EmployeeRequestDto;
import com.example.employee.dtos.EmployeeResponseDto;
import com.example.employee.entity.Employee;
import com.example.employee.response.ApiResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	
	
	



   public EmployeeController(EmployeeService employeeService) {

		this.employeeService = employeeService;
	}

   @GetMapping("/")
	public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> allEmployees(){
	   List<EmployeeResponseDto> allEmp = employeeService.getAllEmployees();
	   ApiResponse<List<EmployeeResponseDto>> response = new ApiResponse<>();
	   response.setStatus(200);
	   response.setMessage("Getting all employees");
	   response.setData(allEmp);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
   
   @GetMapping("/{id}")
   public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployee(@PathVariable long id) {
	   EmployeeResponseDto emp = employeeService.getEmployee(id);
	   ApiResponse<EmployeeResponseDto> response = new ApiResponse<>();
	   response.setStatus(200);
	   response.setMessage("Getting employee by id");
	   response.setData(emp);
	   
	   return new ResponseEntity<ApiResponse<EmployeeResponseDto>>(response, HttpStatus.OK) ;
	   
   }
   
   @PostMapping(path = "/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<ApiResponse<EmployeeResponseDto>> addEmployee(@RequestPart EmployeeRequestDto employeeRequestDto,
		                                @RequestPart MultipartFile photo,
		                                @RequestPart MultipartFile resume) {
	   
	   EmployeeResponseDto emp = employeeService.addEmployee(employeeRequestDto, photo, resume);
	   ApiResponse<EmployeeResponseDto> response = new ApiResponse<>();
	   response.setStatus(201);
	   response.setMessage("added employee with id:"+emp.getEmpId());
	   response.setData(emp);
	   return new ResponseEntity<ApiResponse<EmployeeResponseDto>>(response, HttpStatus.CREATED);
   }
   
   
   @PutMapping("/{id}")
   public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(@PathVariable long id, @RequestBody EmployeeRequestDto employeeRequestDto) {
	   EmployeeResponseDto emp =employeeService.updateEmployee(id, employeeRequestDto);
	   ApiResponse<EmployeeResponseDto> response = new ApiResponse<>();
	   response.setStatus(200);
	   response.setMessage("Updating employee details");
	   response.setData(emp); 
	   return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   @DeleteMapping("/{id}")
   public ResponseEntity<ApiResponse<EmployeeResponseDto>> deleteEmployee(@PathVariable long id) {
	     EmployeeResponseDto emp = employeeService.deleteEmployee(id);
	     ApiResponse<EmployeeResponseDto> response = new ApiResponse<>();
		   response.setStatus(200);
		   response.setMessage("deleting employee");
		   response.setData(emp);
   return new ResponseEntity<>(response, HttpStatus.OK);
   }
}
