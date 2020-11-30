package com.gec.service;

import java.util.List;

import com.gec.bean.Type;
import com.gec.bean.PageModel;

public interface TypeService {
	PageModel<Type> findPageEntity(int pageIndex,Type type);

	List<Type> findAll();
	
	Type findById(int id);
	
	boolean save(Type entity);
	
	boolean update(Type entity);
	
	boolean delete(int id);
}
