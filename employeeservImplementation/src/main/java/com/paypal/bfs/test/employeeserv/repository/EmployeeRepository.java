package com.paypal.bfs.test.employeeserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Employee findAllByFirstNameAndLastName(String firstName, String lastName);
}
