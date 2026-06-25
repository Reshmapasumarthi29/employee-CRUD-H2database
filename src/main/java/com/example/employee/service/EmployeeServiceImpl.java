package com.example.employee.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.employee.dtos.EmployeeRequestDto;
import com.example.employee.dtos.EmployeeResponseDto;
import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl  implements EmployeeService{
    
	private EmployeeRepository employeeRepository;
	
	@Value("${file.upload.image_path}")
	private String imagePath;
	
	@Value("${file.upload.resume_path}")
	private String resumePath;
	
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class); 
	
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}



	@Override
	public List<EmployeeResponseDto> getAllEmployees() {
		logger.info("Displaying all employee details....");
		
		List<Employee> allEmployees = employeeRepository.findAll();
		List<EmployeeResponseDto> responseDto = new ArrayList<>();
		for(Employee employee: allEmployees) {
			EmployeeResponseDto dto = new EmployeeResponseDto();
			BeanUtils.copyProperties(employee, dto);
			responseDto.add(dto);
		}
		
		return responseDto;
	}

	@Override
	public EmployeeResponseDto getEmployee(long id) {
		
		logger.info("Fetching employee with ID: {}", id);
			
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(()->
				new EmployeeNotFoundException("Employee Not Found with id:" +id));
		
		EmployeeResponseDto responseDto = new EmployeeResponseDto();
		BeanUtils.copyProperties(emp, responseDto);
		
		logger.info("feteched Employee with id:" +id);
		return responseDto;
	}

	



	@Override
	public EmployeeResponseDto addEmployee(EmployeeRequestDto employeeRequestDto, MultipartFile photoFile, MultipartFile resumeFile) {
		
		logger.info("creating a employee :" + employeeRequestDto.getName());
		
		Employee emp = new Employee();
		
		BeanUtils.copyProperties(employeeRequestDto, emp);
		
		long timeStamp = System.currentTimeMillis();
		
		String imageName = timeStamp +"_" + photoFile.getOriginalFilename();
		String resumeName = timeStamp +"_" + resumeFile.getOriginalFilename();
		
		File imageDestinationFile = new File(imagePath,imageName);
		File resumeDestinationFile = new File(resumePath, resumeName);
		File imageFolder = new File(imagePath);
		File resumeFolder = new File(resumePath);
		
		if(!imageFolder.exists()) {
			imageFolder.mkdirs();
		}
		
		if(!resumeFolder.exists()) {
			resumeFolder.mkdirs();
		}
		
		try {
			photoFile.transferTo(imageDestinationFile);
			resumeFile.transferTo(resumeDestinationFile);
		} catch (Exception e) {
			throw new RuntimeException("File upload failed");
		}
		
		
		
		emp.setActiveSw("yes");
		emp.setPhotoPath("images/" + imageName);
		emp.setResumePath("resume" + resumeName); 
		
		
		Employee empAdd = employeeRepository.save(emp);
		
		EmployeeResponseDto responseDto = new EmployeeResponseDto();
		
		BeanUtils.copyProperties(empAdd, responseDto);
		
		logger.info("Employee added");
		return responseDto;
	}



	@Override
	public EmployeeResponseDto updateEmployee(long id, EmployeeRequestDto employeeRequestDto) {
		
		logger.info("Updating employee details of " +employeeRequestDto.getName());
		
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(()->
				new EmployeeNotFoundException("Employee not found with id:"+id));
		
		
		emp.setName(employeeRequestDto.getName());
		emp.setEmail(employeeRequestDto.getEmail());
		emp.setDepartment(employeeRequestDto.getDepartment());
		emp.setSalary(employeeRequestDto.getSalary());
		
	
		logger.info("updated " +emp.getName() +" details");
		
			employeeRepository.save(emp);
			
			EmployeeResponseDto responseDto = new EmployeeResponseDto();

			BeanUtils.copyProperties(emp, responseDto);

		return responseDto;
	}



	@Override
	public EmployeeResponseDto deleteEmployee(long id) {
		Employee emp =employeeRepository.findById(id)
				.orElseThrow(()->new EmployeeNotFoundException("Employee not found with id :" +id));
		emp.setActiveSw("No");
		employeeRepository.save(emp);
	    EmployeeResponseDto responsedto = new EmployeeResponseDto();
	    BeanUtils.copyProperties(emp, responsedto);
		return responsedto;
	
	}
	

}
