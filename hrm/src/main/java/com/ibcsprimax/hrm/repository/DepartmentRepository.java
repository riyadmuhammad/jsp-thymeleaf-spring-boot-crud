package com.ibcsprimax.hrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibcsprimax.hrm.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

	public Department findByCode(String code);
	
	public List<Department> findByIdNot(Long id);

}
