package com.gec.service;

import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageModel;

public interface DeptService {
	PageModel<Dept> findPageEntity(int pageIndex,Dept dept);

	List<Dept> findAll();
	
	Dept findById(int id);
	
	boolean save(Dept entity);
	
	boolean update(Dept entity);
	
	boolean delete(int id);
}
