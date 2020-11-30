package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageModel;
import com.gec.dao.JobDao;
import com.gec.dao.impl.JobDaoImpl;
import com.gec.service.JobService;

public class JobServiceImpl implements JobService {
	JobDao jd = new JobDaoImpl();
	
	@Override
	public PageModel<Job> findPageEntity(int pageIndex, Job job) {
		return jd.findPageEntity(pageIndex, job);
	}
	
	@Override
	public List<Job> findAll() {
		return jd.findAll();
	}
	
	@Override
	public Job findById(int id) {
		return jd.findById(id);
	}

	@Override
	public boolean save(Job entity) {
		return jd.save(entity);
	}

	@Override
	public boolean update(Job entity) {
		return jd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		return jd.delete(id);
	}
}
