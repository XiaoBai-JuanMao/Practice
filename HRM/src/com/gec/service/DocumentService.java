package com.gec.service;

import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageModel;

public interface DocumentService {
	PageModel<Document> findPageEntity(int pageIndex,Document document);

	List<Document> findAll();
	
	Document findById(int id);
	
	boolean save(Document entity);
	
	boolean update(Document entity);
	
	boolean delete(int id);
}
