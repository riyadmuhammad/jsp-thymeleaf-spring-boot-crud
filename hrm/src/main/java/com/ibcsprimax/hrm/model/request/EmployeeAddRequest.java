package com.ibcsprimax.hrm.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;



public class EmployeeAddRequest {
	
	
	@NotBlank(message = "Code is mandatory.")
	@Size(max=5,message = "Code Length Must be Within 5 Character.")
    private String code;

	@NotBlank(message = "Name is mandatory.")
	@Size(max=30,message = "Name Length Must be Within 30 Character.")
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joiningDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	@Size(max=30,message = "Designation Length Must be Within 20 Character.")
	@NotBlank(message = "Designation is mandatory.")
	private String designation;

	private Double basic;

	private String gender;
	
	
	private Long departmentId;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Double getBasic() {
		return basic;
	}

	public void setBasic(Double basic) {
		this.basic = basic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	
	
	

}
