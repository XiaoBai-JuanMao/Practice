package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageModel;
import com.gec.dao.DocumentDao;
import com.gec.dao.UserDao;
import com.gec.util.JDBCUtil;

public class DocumentDaoImpl extends JDBCUtil<Document> implements DocumentDao {
	UserDao ud = new UserDaoImpl();
	
	@Override
	public PageModel<Document> findPageEntity(int pageIndex, Document entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Document> pm = new PageModel<Document>();
		
		String sqlCount = "select count(id) from document_inf where 1=1";
		String sql = "select * from document_inf where 1=1";
		if (entity.getTitle()!=null && !"".equals(entity.getTitle())) {
			sqlCount += " and TITLE like ?";
			sql += " and TITLE like ?";
			obj.add("%"+entity.getTitle()+"%");
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
	public List<Document> findAll() {
		return query("select * from document_inf");
	}

	@Override
	public Document findById(int id) {
		List<Document> list = query("select * from document_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean save(Document entity) {
		return update("insert into document_inf values(null,?,?,?,?,?,?,?)",
				entity.getTitle(),entity.getFileName(),entity.getFiletype(),
				entity.getFileUrl(),entity.getRemark(),entity.getCreateDate(),
				entity.getUser().getId());
	}

	@Override
	public boolean update(Document entity) {
		return update("update document_inf set "
				+ "TITLE=?,filename=?,filetype=?,fileUrl=?,REMARK=?,"
				+ "USER_ID=? where id=?",
				entity.getTitle(),entity.getFileName(),entity.getFiletype(),
				entity.getFileUrl(),entity.getRemark(),entity.getUser().getId(),
				entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from document_inf where id=?", id);
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document document = new Document();
		document.setId(rs.getInt(1));
		document.setTitle(rs.getString(2));
		document.setFileName(rs.getString(3));
		document.setFiletype(rs.getString(4));
		document.setFileUrl(rs.getString(5));
		document.setRemark(rs.getString(6));
		document.setCreateDate(rs.getDate(7));
		document.setUser(ud.findById(rs.getInt(8)));
		return document;
	}


}
