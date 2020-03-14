package com.ibcsprimax.hrm.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcsprimax.hrm.common.BaseResponse;
import com.ibcsprimax.hrm.common.ItemResponse;
import com.ibcsprimax.hrm.common.JasperUtils;
import com.ibcsprimax.hrm.model.entity.Department;
import com.ibcsprimax.hrm.model.entity.Employee;
import com.ibcsprimax.hrm.model.request.DepartmentCreateRequest;
import com.ibcsprimax.hrm.model.request.DepartmentUpdateRequest;
import com.ibcsprimax.hrm.model.request.EmployeeAddRequest;
import com.ibcsprimax.hrm.model.request.EmployeeUpdateRequest;
import com.ibcsprimax.hrm.model.response.DeptViewResponse;
import com.ibcsprimax.hrm.model.response.EmployeeViewResponse;
import com.ibcsprimax.hrm.repository.DepartmentRepository;
import com.ibcsprimax.hrm.repository.EmployeeRepository;


@Service
public class HrmService {
	
	Logger logger = LoggerFactory.getLogger(HrmService.class);
	
	@Autowired
	public DepartmentRepository departmentRepository;
	
	@Autowired
	public EmployeeRepository staffRepository;
	
	@Autowired
	public JasperUtils jasperUtils;
	
	
	@Transactional
	public BaseResponse addDepartment(DepartmentCreateRequest request) {
		BaseResponse baseResponse=new BaseResponse();
		List<Department> departments=departmentRepository.findAll();
		
		if(isCodeExistsInOthersDepartment(departments, request.getCode())) {
			baseResponse.setMessage("Code already exists in another department.");
			baseResponse.setMessageType(0);
			logger.error(baseResponse.getMessage());
			return baseResponse;
		}
		
		Department dept=new Department();
		dept.setCode(request.getCode());
		dept.setName(request.getName());
		dept.setActive(true);
		
		departmentRepository.save(dept);
		
		baseResponse.setMessage("Department Info Successfully Saved");
		baseResponse.setMessageType(1);
		
		return baseResponse;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ItemResponse departmentList() {
		ItemResponse itemResponse=new ItemResponse();
		
		List<Department> departments=departmentRepository.findAll();
		List<DeptViewResponse> deptListResponses=new ArrayList<>();
		for(Department dept:departments) {
			DeptViewResponse rs=new DeptViewResponse();
			rs.setCode(dept.getCode());
			rs.setId(dept.getId());
			rs.setName(dept.getName());
			rs.setActive(dept.getActive());
			if(dept.getActive().equals(true)) {
			 rs.setStatus("Active");	
			}else {
				rs.setStatus("Inactive");	
			}
			
			deptListResponses.add(rs);
			
		}
		
		itemResponse.setItem(deptListResponses);
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		
		return itemResponse;
	}
	
	
	public void copyDepartmentToDepartmentUpdateRequest(Department department,DepartmentUpdateRequest departmentUpdateRequest) {
		
		BeanUtils.copyProperties(department, departmentUpdateRequest);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findSingleDepartment(Long id) {
		
		ItemResponse itemResponse=new ItemResponse();
		
		Department department=departmentRepository.getOne(id);
		
		DepartmentUpdateRequest departmentUpdateRequest=new DepartmentUpdateRequest();
		
		copyDepartmentToDepartmentUpdateRequest(department, departmentUpdateRequest);
		
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		itemResponse.setItem(departmentUpdateRequest);
		return itemResponse;
		
	}
	
	
	@Transactional
	public BaseResponse updateDepartment(DepartmentUpdateRequest request) {
		
		BaseResponse baseResponse=new BaseResponse();
		
		Department department=departmentRepository.getOne(request.getId());
		
		List<Department> departments=departmentRepository.findByIdNot(request.getId());
		
		if(isCodeExistsInOthersDepartment(departments, request.getCode())) {
			
			baseResponse.setMessage("Code already exists in another department.");
			baseResponse.setMessageType(0);
			logger.error(baseResponse.getMessage());
			return baseResponse;
			
		}
		
		department.setActive(request.getActive());
		department.setName(request.getName());
		department.setCode(request.getCode());

		
		
		baseResponse.setMessage("Department Successfully Updated.");
		baseResponse.setMessageType(1);
		return baseResponse;
		
	}
	
	public boolean isCodeExistsInOthersDepartment(List<Department> departments,String code) {
		
		for(Department dept:departments) {
			
			if(dept.getCode().equals(code)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean isCodeExistsInOtherEmployees(List<Employee> employees,String code) {
		
		for(Employee emp:employees) {
			
			if(emp.getCode().equals(code)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public BaseResponse addEmployee(EmployeeAddRequest request) {
		BaseResponse baseResponse=new BaseResponse();
		List<Employee> employees=staffRepository.findAll();
		Department department=departmentRepository.getOne(request.getDepartmentId());
		
		if(department==null) {
			baseResponse.setMessage("No Department Found.");
			baseResponse.setMessageType(0);
			logger.error(baseResponse.getMessage());
			return baseResponse;	
		}
		
		if(isCodeExistsInOtherEmployees(employees,request.getCode())) {
			baseResponse.setMessage("Code Already exists in Other Employee.");
			baseResponse.setMessageType(0);
			logger.error(baseResponse.getMessage());
			return baseResponse;	
		}
		
		Employee employee=new Employee();
		employee.setCode(request.getCode());
		employee.setBasic(request.getBasic());
		employee.setDepartment(department);
		employee.setDesignation(request.getDesignation());
		employee.setDob(request.getDob());
		employee.setGender(request.getGender());
		employee.setJoiningDate(request.getJoiningDate());
		employee.setName(request.getName());
		
		staffRepository.save(employee);
		
		baseResponse.setMessage("Employee Successfully Added");
		baseResponse.setMessageType(1);
		
		return baseResponse;
		
	}
	
	
	
	
	public List<EmployeeViewResponse> prepareEmployeeList(){
		
		List<Employee> employees=staffRepository.findAll();
		List<EmployeeViewResponse> employeeList=new ArrayList<>();
		
		for(Employee emp:employees) {
			EmployeeViewResponse employeeView=new EmployeeViewResponse();
			copyEmployeeToEmployeeViewResponse(emp, employeeView);
			employeeList.add(employeeView);
		}
		
		return employeeList;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findEmployeeList() {
		
		ItemResponse itemResponse=new ItemResponse();
		List<EmployeeViewResponse> employeeList=prepareEmployeeList();
	
				
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		itemResponse.setItem(employeeList);
		return itemResponse;
		
	}
	
	@SuppressWarnings("rawtypes")
	public void downloadEmployeeList() {
		
		List<EmployeeViewResponse> employeeList=prepareEmployeeList();
		Map<String, Object> map=new HashMap<>();
		map.put("comapnyName", "IBCS PRIMAX SOFTWARE SOLUTION LTD.");
		map.put("companyAddress", "MOHAMMADPUR DHAKA-1214");
		
		
		try {
			 String fileLocation="jasper/EmployeeList.jasper";
			jasperUtils.jasperPrintWithList(employeeList, map, fileLocation, "Employee-List");
			logger.info("Report Successfully Downloaded");
		} catch (Exception e) {
			logger.error(e.getMessage());
		
		}

		

	}
	
   public void copyEmployeeToEmployeeViewResponse(Employee employee,EmployeeViewResponse employeeView) {
		
		employeeView.setBasic(employee.getBasic());
		employeeView.setCode(employee.getCode());
		employeeView.setDepartmentId(employee.getDepartment().getId());
		employeeView.setDepartmentName(employee.getDepartment().getName());
		employeeView.setDesignation(employee.getDesignation());
		employeeView.setDob(employee.getDob());
		employeeView.setGender(employee.getGender());
		employeeView.setId(employee.getId());
		employeeView.setJoiningDate(employee.getJoiningDate());
		employeeView.setName(employee.getName());
		employeeView.setJoiningDateString(employee.getJoiningDate()+"");
		employeeView.setDobString(employee.getDob()+"");
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemResponse findSingleEmployee(Long id) {
		
		ItemResponse itemResponse=new ItemResponse();
		Employee employee=staffRepository.getOne(id);
		
		EmployeeUpdateRequest employeeUpdateRequest=new EmployeeUpdateRequest();
		copyEmployeeToEmployeeUpdateRequest(employee, employeeUpdateRequest);
			
		itemResponse.setMessage("OK");
		itemResponse.setMessageType(1);
		itemResponse.setItem(employeeUpdateRequest);
		return itemResponse;
		
	}
	
	
	public void copyEmployeeToEmployeeUpdateRequest(Employee employee,EmployeeUpdateRequest employeeUpdateRequest) {
			
		employeeUpdateRequest.setBasic(employee.getBasic());
		employeeUpdateRequest.setCode(employee.getCode());
		employeeUpdateRequest.setDepartmentId(employee.getDepartment().getId());
		employeeUpdateRequest.setDesignation(employee.getDesignation());
		employeeUpdateRequest.setDob(employee.getDob());
		employeeUpdateRequest.setGender(employee.getGender());
		employeeUpdateRequest.setId(employee.getId());
		employeeUpdateRequest.setJoiningDate(employee.getJoiningDate());
		employeeUpdateRequest.setName(employee.getName());
			
		}



	@SuppressWarnings("rawtypes")
	@Transactional
	public BaseResponse updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
		
		BaseResponse baseResponse=new BaseResponse();
		List<Employee> employees=staffRepository.findByIdNot(employeeUpdateRequest.getId());
		Employee employee=staffRepository.getOne(employeeUpdateRequest.getId());
		Department department=departmentRepository.getOne(employeeUpdateRequest.getDepartmentId());
		
		if(isCodeExistsInOtherEmployees(employees,employeeUpdateRequest.getCode())) {
			baseResponse.setMessage("Code Already exists in Other Employee.");
			baseResponse.setMessageType(0);
			logger.error(baseResponse.getMessage());
			return baseResponse;	
		}
		
		employee.setBasic(employeeUpdateRequest.getBasic());
		employee.setCode(employeeUpdateRequest.getCode());
		employee.setDesignation(employeeUpdateRequest.getDesignation());
		employee.setDepartment(department);
		employee.setDob(employeeUpdateRequest.getDob());
		employee.setGender(employeeUpdateRequest.getGender());
		employee.setJoiningDate(employeeUpdateRequest.getJoiningDate());
		employee.setName(employeeUpdateRequest.getName());
		
			
		baseResponse.setMessage("Employee Successfully Updated.");
		baseResponse.setMessageType(1);
		return baseResponse;
		
	}

}
