package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageModel;
import com.gec.dao.JobDao;
import com.gec.util.JDBCUtil;

public class JobDaoImpl extends JDBCUtil<Job> implements JobDao {

	@Override
	public PageModel<Job> findPageEntity(int pageIndex, Job entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Job> pm = new PageModel<Job>();
		
		String sqlCount = "select count(id) from job_inf where 1=1";
		String sql = "select * from job_inf where 1=1";
		if (entity.getName()!=null && !"".equals(entity.getName())) {
			sqlCount += " and name like ?";
			sql += " and name like ?";
			obj.add("%"+entity.getName()+"%");
		}
		
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(queryCount(sqlCount, obj.toArray()));
		
		sql += " limit ?,?";
		obj.add((pageIndex-1)*pm.getPageSize());
		obj.add(pm.getPageSize());
		pm.setList(query(sql, obj.toArray()));
		return pm;
	}

	@Override
	public List<Job> findAll() {
		return query("select * from job_inf");
	}

	@Override
	public Job findById(int id) {
		List<Job> list = query("select * from job_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Job entity) {
		return update("insert into job_inf values(null,?,?,0)",
				entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Job entity) {
		return update("update job_inf set name=?,remark=? where id=?",
				entity.getName(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from job_inf where id=?", id);
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job job = new Job();
		job.setId(rs.getInt(1));
		job.setName(rs.getString(2));
		job.setRemark(rs.getString(3));
		return job;
	}


}
