package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.PageModel;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.JDBCUtil;

public class UserDaoImpl extends JDBCUtil<User> implements UserDao {

	@Override
	public PageModel<User> findPageEntity(int pageIndex, User entity) {
		List<Object> obj = new ArrayList<>();
		PageModel<User> pm = new PageModel<User>();
		
		String sqlCount = "select count(id) from user_inf where 1=1";
		String sql = "select * from user_inf where 1=1";
		if(entity.getLoginname()!=null && !entity.getLoginname().equals("")) {
			sqlCount += " and loginname like ?";
			sql +=" and loginname like ?";
			obj.add("%"+entity.getLoginname()+"%");
		}
		if(entity.getUsername()!=null && !entity.getUsername().equals("")) {
			sqlCount += " and username like ?";
			sql +=" and username like ?";
			obj.add("%"+entity.getUsername()+"%");
		}
		if(entity.getStatus()!=null && entity.getStatus()>0) {
			sqlCount += " and status like ?";
			sql +=" and status like ?";
			obj.add("%"+entity.getStatus()+"%");
		}
		
		pm.setPageIndex(pageIndex);
		pm.setTotalRecordSum(queryCount(sqlCount, obj.toArray()));
		
		sql +=" limit ?,?";
		obj.add((pageIndex-1)*pm.getPageSize());
		obj.add(pm.getPageSize());
		pm.setList(query(sql, obj.toArray()));
		return pm;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(int id) {
		List<User> list = query("select * from user_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(User entity) {
		return update("insert into user_inf values(null,?,?,?,?,?)", 
				entity.getLoginname(),entity.getPassword(),entity.getStatus(),
				entity.getCreatedate(),entity.getUsername());
	}

	@Override
	public boolean update(User entity) {
		System.out.println(entity.toString());
		return update("update user_inf set loginname=?,password=?,status=?,username=? where id=?",
				entity.getLoginname(),entity.getPassword(),entity.getStatus(),
				entity.getUsername(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from user_inf where id=?", id);
	}

	@Override
	public User login(String loginname, String password) {
		List<User> list = query("select * from user_inf where loginname=? and password=?", loginname,password);
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	@Override
	public User findByLoginname(String loginname) {
		List<User> list = query("select * from user_inf where loginname=?", loginname);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setLoginname(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(rs.getInt(4));
		user.setCreatedate(rs.getDate(5));
		user.setUsername(rs.getString(6));
		return user;
	}
}
