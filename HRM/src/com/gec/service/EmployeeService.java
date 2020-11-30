package com.gec.service;

import com.gec.bean.Employee;
import com.gec.bean.PageModel;

public interface EmployeeService {
	PageModel<Employee> findPageEntity(int pageIndex,Employee employee);
	
	Employee findById(int id);
	
	Employee findByCarId(String carId);
	
	boolean save(Employee entity);
	
	boolean update(Employee entity);
	
	boolean delete(int id);
}
