package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.bean.PageModel;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;

@WebServlet(urlPatterns = {"/dept/selectDept.action","/deptlist.action","/dept/addDept.action",
							"/saveOrUpdate.action","/viewDept.action","/deptdel.action"})
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DeptService ds = new DeptServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if ("selectDept.action".equals(uri)) {
			//【菜单打开部门列表】
			request.getRequestDispatcher("/deptlist.action").forward(request, response);
		}else if ("deptlist.action".equals(uri)) {
			//【查询部门列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String name = request.getParameter("name");
			
			Dept dept = new Dept();
			dept.setName(name);
			request.setAttribute("dept", dept);
			
			PageModel<Dept> pm = ds.findPageEntity(pageIndex, dept);
			request.setAttribute("pageModel", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}else if ("addDept.action".equals(uri)) {
			//【进入部门添加页面】
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}else if ("saveOrUpdate.action".equals(uri)) {
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			
			Dept dept = new Dept();
			dept.setId(id);
			dept.setName(name);
			dept.setRemark(remark);
			
			if (flag==0) {
				ds.save(dept);				
			}else {
				ds.update(dept);
			}
			
			request.getRequestDispatcher("/deptlist.action").forward(request, response);
		}else if ("viewDept.action".equals(uri)) {
			//【进入部门编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Dept dept = ds.findById(id);
			request.setAttribute("dept", dept);
			request.setAttribute("val", "edit");
			
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}else if ("deptdel.action".equals(uri)) {
			//【删除部门】
			String[] ids = request.getParameterValues("deptIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				ds.delete(id);
			}
			request.getRequestDispatcher("/deptlist.action").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
