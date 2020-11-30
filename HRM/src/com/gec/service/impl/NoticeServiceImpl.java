package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Notice;
import com.gec.bean.PageModel;
import com.gec.dao.NoticeDao;
import com.gec.dao.impl.NoticeDaoImpl;
import com.gec.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	NoticeDao nd = new NoticeDaoImpl();
	
	@Override
	public PageModel<Notice> findPageEntity(int pageIndex, Notice notice) {
		return nd.findPageEntity(pageIndex, notice);
	}
	
	@Override
	public List<Notice> findAll() {
		return nd.findAll();
	}
	
	@Override
	public Notice findById(int id) {
		return nd.findById(id);
	}

	@Override
	public boolean save(Notice entity) {
		return nd.save(entity);
	}

	@Override
	public boolean update(Notice entity) {
		return nd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return nd.delete(id);
	}
}
