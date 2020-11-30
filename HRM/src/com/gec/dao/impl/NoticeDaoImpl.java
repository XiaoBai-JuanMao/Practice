package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Notice;
import com.gec.bean.PageModel;
import com.gec.dao.NoticeDao;
import com.gec.dao.TypeDao;
import com.gec.dao.UserDao;
import com.gec.util.JDBCUtil;

public class NoticeDaoImpl extends JDBCUtil<Notice> implements NoticeDao {
	TypeDao td = new TypeDaoImpl();
	UserDao ud = new UserDaoImpl();
	
	@Override
	public PageModel<Notice> findPageEntity(int pageIndex, Notice entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Notice> pm = new PageModel<Notice>();
		
		String sqlCount = "select count(id) from notice_inf where 1=1";
		String sql = "select * from notice_inf where 1=1";
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
	public List<Notice> findAll() {
		return query("select * from notice_inf");
	}

	@Override
	public Notice findById(int id) {
		List<Notice> list = query("select * from notice_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Notice entity) {
		return update("insert into notice_inf values(null,?,?,?,?,?,?)",
				entity.getName(),entity.getCreateDate(),entity.getType().getId(),
				entity.getContent(),entity.getUser().getId(),entity.getModifyDate());
	}

	@Override
	public boolean update(Notice entity) {
		return update("update notice_inf set name=?,type_id=?,content=?,"
				+ "user_id=?,modify_date=? where id=?",
				entity.getName(),entity.getType().getId(),entity.getContent(),
				entity.getUser().getId(),entity.getModifyDate(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from notice_inf where id=?", id);
	}

	@Override
	public Notice getEntity(ResultSet rs) throws Exception {
		Notice notice = new Notice();
		notice.setId(rs.getInt(1));
		notice.setName(rs.getString(2));
		notice.setCreateDate(rs.getDate(3));
		notice.setType(td.findById(rs.getInt(4)));
		notice.setContent(rs.getString(5));
		notice.setUser(ud.findById(rs.getInt(6)));
		notice.setModifyDate(rs.getDate(7));
		return notice;
	}


}
