package com.example.employee.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl  implements EmployeeService{

	private EmployeeRepository employeeRepository;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class); 
	
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}



	@Override
	public List<Employee> getAllEmployees() {
		logger.info("Displaying all employee details....");
		
		List<Employee> allEmployees = employeeRepository.findAll();
		
		return allEmployees;
	}

	@Override
	public Employee getEmployee(long id) {
		
		logger.info("Fetching employee with ID: {}", id);
		
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(()->
				new EmployeeNotFoundException("Employee Not Found with id:" +id));
		
		logger.info("feteched Employee with id:" +id);
		return emp;
	}

	



	@Override
	public Employee addEmployee(Employee employee) {
		
		logger.info("creating a employee :" + employee.getName());
		
		Employee emp = employeeRepository.save(employee);
		
		logger.info("Employee added");
		return emp;
	}



	@Override
	public Employee updateEmployee(long id, Employee employee) {
		
		logger.info("Updating employee details of " +employee.getName());
		
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(()->
				new EmployeeNotFoundException("Employee not found with id:"+id));
		
		
		System.out.println(emp);
		System.out.println(employee);
		emp.setName(employee.getName());
		emp.setEmail(employee.getEmail());
		emp.setDepartment(employee.getDepartment());
		emp.setSalary(employee.getSalary());

		logger.info("updated " +emp.getName() +" details");
			employeeRepository.save(emp); 

		
		
		
		return emp;
	}



	@Override
	public void deleteEmployee(long id) {
		Optional<Employee> emp =employeeRepository.findById(id);
		if(emp.isPresent()) {

			logger.info("deleting employww with id:" +id);
			
		     employeeRepository.deleteById(id);
		     
		     logger.info("deleted employee");
		}
		else {
			logger.error("no such employee with id:" +id);
			throw new EmployeeNotFoundException("Employee Not found with id:" +id);
		}
	}








	
	
	

}
