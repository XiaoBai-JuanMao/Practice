package com.gec.service;

import com.gec.bean.PageModel;
import com.gec.bean.User;

public interface UserService {

	PageModel<User> findPageEntity(int pageIndex,User user);
	
	User login(String loginname,String password);
	
	User findById(int id);
	
	boolean save(User entity);
	
	boolean update(User entity);
	
	boolean delete(int id);

	User findByLoginname(String loginname);
}
