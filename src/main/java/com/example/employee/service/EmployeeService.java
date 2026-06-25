package com.example.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.employee.dtos.EmployeeRequestDto;
import com.example.employee.dtos.EmployeeResponseDto;
import com.example.employee.entity.Employee;

public interface EmployeeService {
	
	public List<EmployeeResponseDto> getAllEmployees();
	
	public EmployeeResponseDto getEmployee(long id);
	
	public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto, MultipartFile photoFile, MultipartFile resumeFile);

	public EmployeeResponseDto updateEmployee(long id, EmployeeRequestDto employeeRequestDto);
	
	public EmployeeResponseDto deleteEmployee(long id);

}
