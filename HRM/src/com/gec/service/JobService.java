package com.gec.service;

import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageModel;

public interface JobService {
	PageModel<Job> findPageEntity(int pageIndex,Job job);
	
	Job findById(int id);
	
	List<Job> findAll();
	
	boolean save(Job entity);
	
	boolean update(Job entity);
	
	boolean delete(int id);
}
