package com.ibcsprimax.hrm.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class DepartmentCreateRequest {

	@NotBlank(message = "Name is mandatory")
	@Size(max=30,message = "Name Length Must be Within 30 Character.")
	private String name;
	
	@NotBlank(message = "Code is mandatory")
	@Size(max=5,message = "Code Length Must be Within 5 Character.")
	private String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
