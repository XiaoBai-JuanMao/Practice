package com.gec.dao;

import java.util.List;

import com.gec.bean.PageModel;

public interface BaseDao<T> {

	PageModel<T> findPageEntity(int pageIndex,T entity);
	
	List<T> findAll();
	
	T findById(int id);
	
	boolean save(T entity);
	
	boolean update(T entity);
	
	boolean delete(int id);
}
