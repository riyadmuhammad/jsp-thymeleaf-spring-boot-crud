package com.ibcsprimax.hrm.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DepartmentUpdateRequest {
	
	
	private Long id;
	
	@NotBlank(message = "Code can not be blank")
	@Size(max=5,message = "Code Length Must be Within 5 Character.")
	private String code;

	@NotBlank(message = "name can not be blank")
	@Size(max=30,message = "Name Length Must be Within 30 Character.")
	private String name;

	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	

}
