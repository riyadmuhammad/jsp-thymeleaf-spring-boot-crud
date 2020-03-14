package com.ibcsprimax.hrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibcsprimax.hrm.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	public List<Employee> findByIdNot(Long id);
	
	public List<Employee> findByCodeNot(String code);

}
