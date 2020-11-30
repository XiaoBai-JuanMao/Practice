package com.gec.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Type;
import com.gec.bean.Notice;
import com.gec.bean.PageModel;
import com.gec.bean.User;
import com.gec.service.NoticeService;
import com.gec.service.TypeService;
import com.gec.service.impl.NoticeServiceImpl;
import com.gec.service.impl.TypeServiceImpl;

@WebServlet(urlPatterns = {"/notice/selectNotice.action","/noticelist.action","/notice/addNotice.action",
							"/noticesaveOrUpdate.action","/viewNotice.action","/noticedel.action",
							"/notice/selectType.action","/typelist.action","/notice/addType.action",
							"/typesaveOrUpdate.action","/viewType.action","/typedel.action"})
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NoticeService ns = new NoticeServiceImpl();
	TypeService ts = new TypeServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if ("selectNotice.action".equals(uri)) {
			//【菜单打开公告列表】
			request.getRequestDispatcher("/noticelist.action").forward(request, response);
		}else if ("noticelist.action".equals(uri)) {
			//【查询公告列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String name = request.getParameter("name");
			
			Notice notice = new Notice();
			notice.setName(name);
			request.setAttribute("notice", notice);
			
			PageModel<Notice> pm = ns.findPageEntity(pageIndex, notice);
			request.setAttribute("pageModel", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}else if ("addNotice.action".equals(uri)) {
			//【进入公告添加页面】
			List<Type> types = ts.findAll();
			request.setAttribute("types", types);
			
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}else if ("noticesaveOrUpdate.action".equals(uri)) {
			//【添加|修改公告】
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String name = request.getParameter("name");
			String type_idString = request.getParameter("type_id");
			int type_id = 0;
			if (type_idString!=null && !"".equals(type_idString)) {
				type_id = Integer.parseInt(type_idString);
			}
			String content = request.getParameter("text");
			String userIdString = request.getParameter("userId");
			int userId = 0;
			if (userIdString!=null && !"".equals(userIdString)) {
				userId = Integer.parseInt(userIdString);
			}
			
			Notice notice = new Notice();
			notice.setId(id);
			notice.setName(name);
			if (flag==0) {
				notice.setCreateDate(new Date());
			}
			notice.setType(new Type(type_id));
			notice.setContent(content);
			notice.setUser(new User(userId));
			notice.setModifyDate(new Date());
			
			if (flag==0) {
				ns.save(notice);				
			}else {
				ns.update(notice);
			}
			
			request.getRequestDispatcher("/noticelist.action").forward(request, response);
		}else if ("viewNotice.action".equals(uri)) {
			//【进入公告编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Notice notice = ns.findById(id);
			request.setAttribute("notice", notice);
			
			List<Type> types = ts.findAll();
			request.setAttribute("types", types);
			
			request.setAttribute("val", "edit");	
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}else if ("noticedel.action".equals(uri)) {
			//【删除公告】
			String[] ids = request.getParameterValues("noticeIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				ns.delete(id);
			}
			
			request.getRequestDispatcher("/noticelist.action").forward(request, response);
		}else if ("selectType.action".equals(uri)) {
			//【菜单打开公告类型列表】
			request.getRequestDispatcher("/typelist.action").forward(request, response);
		}else if ("typelist.action".equals(uri)) {
			//【查询公告类型列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String name = request.getParameter("name");
			
			Type type = new Type();
			type.setName(name);
			request.setAttribute("type", type);
			
			PageModel<Type> pm = ts.findPageEntity(pageIndex, type);
			request.setAttribute("pageModel", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
		}else if ("addType.action".equals(uri)) {
			//【进入公告类型添加页面】
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
		}else if ("typesaveOrUpdate.action".equals(uri)) {
			//【添加|修改公告类型】
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String name = request.getParameter("name");
			String userIdString = request.getParameter("userId");
			int userId = 0;
			if (userIdString!=null && !"".equals(userIdString)) {
				userId = Integer.parseInt(userIdString);
			}
			
			Type type = new Type();
			type.setId(id);
			type.setName(name);
			if (flag == 0) {
				type.setCreateDate(new Date());
			}
			type.setUser(new User(userId));
			type.setModifyDate(new Date());
			
			if (flag==0) {
				ts.save(type);				
			}else {
				ts.update(type);
			}
			
			request.getRequestDispatcher("/typelist.action").forward(request, response);
		}else if ("viewType.action".equals(uri)) {
			//【进入公告类型编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Type type = ts.findById(id);
			request.setAttribute("type", type);
			request.setAttribute("val", "edit");
			
			request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
		}else if ("typedel.action".equals(uri)) {
			//【删除公告类型】
			String[] ids = request.getParameterValues("typeIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				ts.delete(id);
			}
			request.getRequestDispatcher("/typelist.action").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
