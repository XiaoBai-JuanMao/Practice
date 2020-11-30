package com.gec.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.bean.PageModel;
import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/userlist.action","/useradd.action","/loginnameAjax",
							"/userAddOrEdit.action","/viewUser.action","/userdel.action"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if("userlist.action".equals(uri)) {
			//【查询用户】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String statusString = request.getParameter("status");
			int status = 0;
			if (statusString!=null && !"".equals(statusString)) {
				status = Integer.parseInt(statusString);
			}
			
			User user = new User();
			user.setLoginname(loginname);
			user.setUsername(username);
			user.setStatus(status);
			request.setAttribute("user", user);
			
			PageModel<User> pm = us.findPageEntity(pageIndex, user);
			request.setAttribute("pageModel", pm);
			request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		}else if ("useradd.action".equals(uri)) {
			//【用户添加页面】
			request.setAttribute("val","add");
			request.getRequestDispatcher("WEB-INF/jsp/user/useredit.jsp").forward(request, response);
		}else if ("loginnameAjax".equals(uri)) {
			//【校验登录名是否相等】
			String loginname = request.getParameter("loginname");
			User user = us.findByLoginname(loginname);
			
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(user);
			out.print(json);
			out.close();
		}else if ("userAddOrEdit.action".equals(uri)) {
			//【用户添加/修改】
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String username = request.getParameter("username");
			String statusString = request.getParameter("status");
			int status = Integer.parseInt(statusString);								
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setStatus(status);
			user.setLoginname(loginname);
			user.setPassword(password);		

			if (flag == 0) {
				user.setCreatedate(new Date());
				us.save(user);
			} else {
				us.update(user);
			}
			
			request.getRequestDispatcher("userlist.action").forward(request, response);
		}else if ("viewUser.action".equals(uri)) {
			//【用户修改页面】
			String idString = request.getParameter("id");
			int id = Integer.parseInt(idString);
			
			User user = us.findById(id);
			request.setAttribute("user", user);
			request.setAttribute("val","edit");
			request.getRequestDispatcher("WEB-INF/jsp/user/useredit.jsp").forward(request, response);
		}else if ("userdel.action".equals(uri)) {
			//【批量删除用户】
			String[] ids = request.getParameterValues("userIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				us.delete(id);
			}
			
			request.getRequestDispatcher("userlist.action").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
