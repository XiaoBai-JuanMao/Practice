package com.gec.service.impl;

import com.gec.bean.Employee;
import com.gec.bean.PageModel;
import com.gec.dao.EmployeeDao;
import com.gec.dao.impl.EmployeeDaoImpl;
import com.gec.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDao ed = new EmployeeDaoImpl();
	
	@Override
	public PageModel<Employee> findPageEntity(int pageIndex, Employee employee) {
		return ed.findPageEntity(pageIndex, employee);
	}
	
	@Override
	public Employee findById(int id) {
		return ed.findById(id);
	}
	
	@Override
	public Employee findByCarId(String carId) {
		return ed.findByCarId(carId);
	}

	@Override
	public boolean save(Employee entity) {
		return ed.save(entity);
	}

	@Override
	public boolean update(Employee entity) {
		return ed.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return ed.delete(id);
	}
}
