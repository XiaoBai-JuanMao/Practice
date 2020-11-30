package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Employee;
import com.gec.bean.PageModel;
import com.gec.dao.DeptDao;
import com.gec.dao.EmployeeDao;
import com.gec.dao.JobDao;
import com.gec.util.JDBCUtil;

public class EmployeeDaoImpl extends JDBCUtil<Employee> implements EmployeeDao {
	DeptDao dd = new DeptDaoImpl();
	JobDao jd = new JobDaoImpl();
	
	@Override
	public PageModel<Employee> findPageEntity(int pageIndex, Employee entity) {
		List<Object> obj = new ArrayList<Object>();
		PageModel<Employee> pm = new PageModel<Employee>();
		
		String sqlCount = "select count(id) from employee_inf where 1=1";
		String sql = "select * from employee_inf where 1=1";
		if (entity.getJob()!=null && entity.getJob().getId()>0) {
			sqlCount += " and job_id=?";
			sql += " and job_id=?";
			obj.add(entity.getJob().getId());
		}
		if (entity.getName()!=null && !"".equals(entity.getName())) {
			sqlCount += " and name like ?";
			sql += " and name like ?";
			obj.add("%"+entity.getName()+"%");
		}
		if (entity.getCardId()!=null && !"".equals(entity.getCardId())) {
			sqlCount += " and CARD_ID like ?";
			sql += " and CARD_ID like ?";
			obj.add("%"+entity.getCardId()+"%");
		}
		if (entity.getSex()!=null && entity.getSex()>0) {
			sqlCount += " and SEX=?";
			sql += " and SEX=?";
			obj.add(entity.getSex());
		}
		if (entity.getPhone()!=null && !"".equals(entity.getPhone())) {
			sqlCount += " and PHONE like ?";
			sql += " and PHONE like ?";
			obj.add("%"+entity.getPhone()+"%");
		}
		if (entity.getDept()!=null && entity.getDept().getId()>0) {
			sqlCount += " and dept_id=?";
			sql += " and dept_id=?";
			obj.add(entity.getDept().getId());
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
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findById(int id) {
		List<Employee> list = query("select * from employee_inf where id=?", id);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Employee findByCarId(String carId) {
		List<Employee> list = query("select * from employee_inf where CARD_ID=?", carId);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public boolean save(Employee entity) {
		return update("insert into employee_inf values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?)",
				entity.getName(),entity.getCardId(),entity.getAddress(),
				entity.getPostCode(),entity.getTel(),entity.getPhone(),
				entity.getQqNum(),entity.getEmail(),entity.getSex(),
				entity.getParty(),entity.getBirthday(),entity.getRace(),
				entity.getEducation(),entity.getSpeciality(),entity.getHobby(),
				entity.getRemark(),entity.getCreateDate(),entity.getDept().getId(),
				entity.getJob().getId());
	}

	@Override
	public boolean update(Employee entity) {
		return update("update employee_inf set"
				+ " NAME=?,CARD_ID=?,ADDRESS=?,POST_CODE=?,TEL=?,"
				+ " PHONE=?,QQ_NUM=?,EMAIL=?,SEX=?,PARTY=?,"
				+ " BIRTHDAY=?,RACE=?,EDUCATION=?,SPECIALITY=?,HOBBY=?,"
				+ " REMARK=?,dept_id=?,job_id=? where id=?",
				entity.getName(),entity.getCardId(),entity.getAddress(),
				entity.getPostCode(),entity.getTel(),entity.getPhone(),
				entity.getQqNum(),entity.getEmail(),entity.getSex(),
				entity.getParty(),entity.getBirthday(),entity.getRace(),
				entity.getEducation(),entity.getSpeciality(),entity.getHobby(),
				entity.getRemark(),entity.getDept().getId(),entity.getJob().getId(),
				entity.getId());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from employee_inf where id=?", id);
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee employee = new Employee();
		employee.setId(rs.getInt(1));
		employee.setName(rs.getString(2));
		employee.setCardId(rs.getString(3));
		employee.setAddress(rs.getString(4));
		employee.setPostCode(rs.getString(5));
		employee.setTel(rs.getString(6));
		employee.setPhone(rs.getString(7));
		employee.setQqNum(rs.getString(8));
		employee.setEmail(rs.getString(9));
		employee.setSex(rs.getInt(10));
		employee.setParty(rs.getString(11));
		employee.setBirthday(rs.getDate(12));
		employee.setRace(rs.getString(13));
		employee.setEducation(rs.getString(14));
		employee.setSpeciality(rs.getString(15));
		employee.setHobby(rs.getString(16));
		employee.setRemark(rs.getString(17));
		employee.setCreateDate(rs.getDate(18));
		//employee.setState(rs.getString(19));
		employee.setDept(dd.findById(rs.getInt(20)));
		employee.setJob(jd.findById(rs.getInt(21)));
		return employee;
	}
}
