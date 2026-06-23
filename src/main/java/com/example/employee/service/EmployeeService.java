package com.example.employee.service;

import java.util.List;
import java.util.Optional;

import com.example.employee.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public Employee getEmployee(long id);
	
	public Employee addEmployee(Employee employee);

	public Employee updateEmployee(long id, Employee employee);
	
	public void deleteEmployee(long id);

}
