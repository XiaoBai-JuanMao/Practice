package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageModel;
import com.gec.dao.DeptDao;
import com.gec.util.JDBCUtil;

public class DeptDaoImpl extends JDBCUtil<Dept> implements DeptDao {

	@Override
	public PageModel<Dept> findPageEntity(int pageIndex, Dept entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Dept> pm = new PageModel<Dept>();
		
		String sqlCount = "select count(id) from dept_inf where 1=1";
		String sql = "select * from dept_inf where 1=1";
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
	public List<Dept> findAll() {
		return query("select * from dept_inf");
	}

	@Override
	public Dept findById(int id) {
		List<Dept> list = query("select * from dept_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Dept entity) {
		return update("insert into dept_inf values(null,?,?,0)",
				entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Dept entity) {
		return update("update dept_inf set name=?,remark=? where id=?",
				entity.getName(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from dept_inf where id=?", id);
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dept = new Dept();
		dept.setId(rs.getInt(1));
		dept.setName(rs.getString(2));
		dept.setRemark(rs.getString(3));
		return dept;
	}


}
