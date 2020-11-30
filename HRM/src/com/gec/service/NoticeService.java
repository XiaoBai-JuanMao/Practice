package com.gec.service;

import java.util.List;

import com.gec.bean.Notice;
import com.gec.bean.PageModel;

public interface NoticeService {
	PageModel<Notice> findPageEntity(int pageIndex,Notice notice);
	
	Notice findById(int id);
	
	List<Notice> findAll();
	
	boolean save(Notice entity);
	
	boolean update(Notice entity);
	
	boolean delete(int id);
}
