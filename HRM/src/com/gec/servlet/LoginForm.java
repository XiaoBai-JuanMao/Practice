package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/loginForm.action","/login.action","/logout.action","/index.action"})
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if("loginForm.action".equals(uri)) {
			request.getRequestDispatcher("WEB-INF/jsp/loginForm.jsp").forward(request, response);
		}else if ("login.action".equals(uri)) {
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			User user = us.login(loginname, password);
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(600);
				session.setAttribute("user_session", user);
				request.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(request, response);
			}else {
				request.setAttribute("message", "用户名或密码错误!");
				request.getRequestDispatcher("WEB-INF/jsp/loginForm.jsp").forward(request, response);
			}
		}else if ("logout.action".equals(uri)) {
			//【注销用户】
			HttpSession session = request.getSession();
			session.invalidate();
			Cookie cSession = new Cookie("JSESSIONID", null);
			cSession.setMaxAge(0);
			response.addCookie(cSession);
			request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);;
		}else if ("index.action".equals(uri)) {
			//【显示商城首页】
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
