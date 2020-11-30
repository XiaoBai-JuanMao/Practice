package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageModel;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImpl;
import com.gec.service.DocumentService;

public class DocumentServiceImpl implements DocumentService {
	DocumentDao dd = new DocumentDaoImpl();
	
	@Override
	public PageModel<Document> findPageEntity(int pageIndex, Document document) {
		return dd.findPageEntity(pageIndex, document);
	}
	
	@Override
	public List<Document> findAll() {
		return dd.findAll();
	}
	
	@Override
	public Document findById(int id) {
		return dd.findById(id);
	}

	@Override
	public boolean save(Document entity) {
		return dd.save(entity);
	}

	@Override
	public boolean update(Document entity) {
		return dd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return dd.delete(id);
	}
}
