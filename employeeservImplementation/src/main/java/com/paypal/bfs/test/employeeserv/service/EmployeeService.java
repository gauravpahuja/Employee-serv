package com.paypal.bfs.test.employeeserv.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.exception.EmployeeResourceException;
import com.paypal.bfs.test.employeeserv.models.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class  EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee getEmployee(Integer id) {
		Optional<Employee> empById = employeeRepository.findById(id);
		if (empById.isPresent()) {
			return empById.get();
		}
		return null;
	}
	
	public Employee createEmployee(Employee emp) {
		//handling the duplicacy of resource(idemptocy)
		Employee existingEmp = employeeRepository.findAllByFirstNameAndLastName(emp.getFirstName(), emp.getLastName());
		if(Objects.nonNull(existingEmp)) {
			log.error("Employee already exists: id: {}", emp.getId());
			throw new EmployeeResourceException("Employee already exists..");
		}
		
		return employeeRepository.save(emp);
	}
}
