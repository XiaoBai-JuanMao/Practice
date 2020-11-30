package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Type;
import com.gec.bean.PageModel;
import com.gec.dao.TypeDao;
import com.gec.dao.impl.TypeDaoImpl;
import com.gec.service.TypeService;

public class TypeServiceImpl implements TypeService {
	TypeDao td = new TypeDaoImpl();
	
	@Override
	public PageModel<Type> findPageEntity(int pageIndex, Type type) {
		return td.findPageEntity(pageIndex, type);
	}
	
	@Override
	public List<Type> findAll() {
		return td.findAll();
	}
	
	@Override
	public Type findById(int id) {
		return td.findById(id);
	}

	@Override
	public boolean save(Type entity) {
		return td.save(entity);
	}

	@Override
	public boolean update(Type entity) {
		return td.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return td.delete(id);
	}
}
