package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Type;
import com.gec.bean.PageModel;
import com.gec.dao.TypeDao;
import com.gec.dao.UserDao;
import com.gec.util.JDBCUtil;

public class TypeDaoImpl extends JDBCUtil<Type> implements TypeDao {
	UserDao ud = new UserDaoImpl();
	
	@Override
	public PageModel<Type> findPageEntity(int pageIndex, Type entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Type> pm = new PageModel<Type>();
		
		String sqlCount = "select count(id) from type_inf where 1=1";
		String sql = "select * from type_inf where 1=1";
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
	public List<Type> findAll() {
		return query("select * from type_inf");
	}

	@Override
	public Type findById(int id) {
		List<Type> list = query("select * from type_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Type entity) {
		return update("insert into type_inf values(null,?,?,0,?,?)",
				entity.getName(),entity.getCreateDate(),
				entity.getUser().getId(),entity.getModifyDate());
	}

	@Override
	public boolean update(Type entity) {
		return update("update type_inf set name=?,user_id=?,modify_date=? where id=?",
				entity.getName(),entity.getUser().getId(),
				entity.getModifyDate(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from type_inf where id=?", id);
	}

	@Override
	public Type getEntity(ResultSet rs) throws Exception {
		Type type = new Type();
		type.setId(rs.getInt(1));
		type.setName(rs.getString(2));
		type.setCreateDate(rs.getDate(3));
		//type.setState(rs.getInt(4));
		type.setUser(ud.findById(rs.getInt(5)));
		type.setModifyDate(rs.getDate(6));
		return type;
	}
}
