package com.paypal.bfs.test.employeeserv.impl;

import java.util.Objects;

import com.paypal.bfs.test.employeeserv.employee.helper.EmployeeResource;
import com.paypal.bfs.test.employeeserv.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class for employee resource.
 */
@RestController
@Slf4j
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeService empService;

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {
		Integer empId;
		try {
			empId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new DataNotFoundException("Invalid id..Please input the valid id " + id);
		}
		
		Employee employee = empService.getEmployee(empId);
		if (Objects.isNull(employee)) {
			log.error("Employee not found with id: {}", id);
			throw new DataNotFoundException("Employee not found for the given id: " + id);
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<Employee> createEmployee(Employee employee) {
		Employee emp = empService.createEmployee(employee);
		return new ResponseEntity<>(emp, HttpStatus.CREATED);
	}
}
