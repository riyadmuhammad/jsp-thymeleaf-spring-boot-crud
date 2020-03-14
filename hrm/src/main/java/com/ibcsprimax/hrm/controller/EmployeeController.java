package com.ibcsprimax.hrm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibcsprimax.hrm.common.BaseResponse;
import com.ibcsprimax.hrm.common.ItemResponse;
import com.ibcsprimax.hrm.model.request.DepartmentCreateRequest;
import com.ibcsprimax.hrm.model.request.DepartmentUpdateRequest;
import com.ibcsprimax.hrm.model.request.EmployeeAddRequest;
import com.ibcsprimax.hrm.model.request.EmployeeUpdateRequest;

import com.ibcsprimax.hrm.service.HrmService;

@Controller
public class EmployeeController {
	
	 
	 @Autowired
	 public HrmService hrmService;
	 
	
	
	@GetMapping("/")
    public String showIndex() {
        return "index";
    }
	
	@GetMapping("/department")
    public String showDepartment(DepartmentCreateRequest dept,Model model) {
		model.addAttribute("dept", dept);
        return "add-department";
    }
	

	@PostMapping("/adddepartment")
    public String addDept(@Valid @ModelAttribute("dept") DepartmentCreateRequest dept, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("dept", dept);
            return "add-department";
        }
		
		BaseResponse baseResponse= hrmService.addDepartment(dept);
		
		if(baseResponse.getMessageType()==0) {
			model.addAttribute("dept", dept);
			model.addAttribute("msg",baseResponse.getMessage());
            return "add-department";
			
		}
		
		model.addAttribute("deptlist",hrmService.departmentList().getItem());
        return "redirect:/showdept";
    }
	
	

	@GetMapping("/showdept")
    public String showDept(Model model) {
		
		model.addAttribute("deptlist",hrmService.departmentList().getItem());
        return "list-department";
    }
	
	@GetMapping("/department/edit/{id}")
    public String showEditDepartment(@PathVariable("id") Long id,Model model) {
		
		model.addAttribute("dept",hrmService.findSingleDepartment(id).getItem());
        return "edit-department";
    }
	
	@PostMapping("/update/department")
    public String updateDepartment(@Valid @ModelAttribute("dept") DepartmentUpdateRequest dept, BindingResult result,  Model model) {
		
		
		if (result.hasErrors()) {
			model.addAttribute("dept", dept);
            return "edit-department";
        }
		
		
        BaseResponse baseResponse=hrmService.updateDepartment(dept);
        if(baseResponse.getMessageType()==0) {
			model.addAttribute("dept", dept);
			model.addAttribute("msg",baseResponse.getMessage());
            return "edit-department";
			
		}
		
		return "redirect:/showdept";
    }


	
	@GetMapping("/show-employee-input")
    public String showEmployeePage(EmployeeAddRequest employeeAddRequest ,Model model) {
		
		model.addAttribute("deptlist",hrmService.departmentList().getItem());
		model.addAttribute("employee", employeeAddRequest);
		return "add-employee";
    }
	
	@PostMapping("/add-employee")
    public String addEmployee(@Valid @ModelAttribute("employee") EmployeeAddRequest employee, BindingResult result,  Model model) {
        
		if (result.hasErrors()) {
			model.addAttribute("employee",employee);
			model.addAttribute("deptlist",hrmService.departmentList().getItem());
            return "add-employee";
        }
		
        BaseResponse baseresponse=hrmService.addEmployee(employee);
        
        if(baseresponse.getMessageType()==0) {
        	model.addAttribute("employee",employee);
			model.addAttribute("deptlist",hrmService.departmentList().getItem());
			model.addAttribute("msg",baseresponse.getMessage());
            return "add-employee";
        	
        }
        
        return "redirect:/show-employee-list";
    }
	
	
	
	@GetMapping("/show-employee-list")
    public String showEmployeeListPage(Model model) {
		
		model.addAttribute("employees", hrmService.findEmployeeList().getItem());
		return "employee-list";
    }
	
	
	@RequestMapping(value = "/download/employee-list", method = RequestMethod.GET)
    public String downloadEmployeeListPage() {
		
		hrmService.downloadEmployeeList();
		return "redirect:/show-employee-list";
        
    }
	
	
	
	
	
	@GetMapping("/show/employee/edit/{id}")
    public String showEmployeeListPage(@PathVariable("id") Long id, Model model) {
			
		model.addAttribute("employee", hrmService.findSingleEmployee(id).getItem());
		model.addAttribute("deptlist",hrmService.departmentList().getItem());
		return "edit-employee";
    }
	
	
	
	
	@PostMapping("/update-employee")
    public String updateEmployee(@Valid @ModelAttribute("employee") EmployeeUpdateRequest employee, BindingResult result,  Model model) {
        
		
		if (result.hasErrors()) {
			model.addAttribute("employee", employee);
			model.addAttribute("deptlist",hrmService.departmentList().getItem());
			return "edit-employee";
        }
		
        BaseResponse baseResponse=hrmService.updateEmployee(employee);
        
        if(baseResponse.getMessageType()==0) {        	
        	model.addAttribute("employee", employee);
    		model.addAttribute("deptlist",hrmService.departmentList().getItem());
    		model.addAttribute("msg",baseResponse.getMessage());
    		return "edit-employee";
        	
        }

        return "redirect:/show-employee-list";
    }
	
	
	
	
	
	
	
	
	
	
	
}

