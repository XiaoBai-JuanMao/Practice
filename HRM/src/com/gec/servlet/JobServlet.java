package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Job;
import com.gec.bean.PageModel;
import com.gec.service.JobService;
import com.gec.service.impl.JobServiceImpl;

@WebServlet(urlPatterns = {"/job/selectJob.action","/joblist.action","/job/addJob.action",
							"/addOrUpdate.action","/viewJob.action","/jobdel.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JobService js = new JobServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if ("selectJob.action".equals(uri)) {
			//【菜单打开职位列表】
			request.getRequestDispatcher("/joblist.action").forward(request, response);
		}else if ("joblist.action".equals(uri)) {
			//【查询职位列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String name = request.getParameter("name");
			
			Job job = new Job();
			job.setName(name);
			request.setAttribute("job", job);
			
			PageModel<Job> pm = js.findPageEntity(pageIndex, job);
			request.setAttribute("pageModel", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if ("addJob.action".equals(uri)) {
			//【进入部门添加页面】
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if ("addOrUpdate.action".equals(uri)) {
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			
			Job job = new Job();
			job.setId(id);
			job.setName(name);
			job.setRemark(remark);
			
			if (flag==0) {
				js.save(job);				
			}else {
				js.update(job);
			}
			
			request.getRequestDispatcher("/joblist.action").forward(request, response);
		}else if ("viewJob.action".equals(uri)) {
			//【进入部门编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Job job = js.findById(id);
			request.setAttribute("job", job);
			request.setAttribute("val", "edit");
			
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if ("jobdel.action".equals(uri)) {
			//【删除部门】
			String[] ids = request.getParameterValues("jobIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				js.delete(id);
			}
			request.getRequestDispatcher("/joblist.action").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
