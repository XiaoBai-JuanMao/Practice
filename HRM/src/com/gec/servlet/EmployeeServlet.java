package com.gec.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageModel;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceImpl;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.MySimpleDateFormat;

@WebServlet(urlPatterns = {"/employeelist.action","/employeeadd.action","/carIdAjax",
		"/saveOrUpdateEmployee.action","/updateEmployee.action","/employeedel.action"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService es = new EmployeeServiceImpl();
	JobService js = new JobServiceImpl();
	DeptService ds = new DeptServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if ("employeelist.action".equals(uri)) {
			//【查询员工列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String jobIdString = request.getParameter("job_id");
			int jobId = 0;
			if (jobIdString!=null && !"".equals(jobIdString)) {
				jobId = Integer.parseInt(jobIdString);
			}
			String name = request.getParameter("name");
			String cardId = request.getParameter("cardId");
			String sexString = request.getParameter("sex");
			int sex = 0;
			if (sexString!=null && !"".equals(sexString)) {
				sex = Integer.parseInt(sexString);
			}
			String phone = request.getParameter("phone");
			String deptIdString = request.getParameter("dept_id");
			int deptId = 0;
			if (deptIdString!=null && !"".equals(deptIdString)) {
				deptId = Integer.parseInt(deptIdString);
			}
			
			Employee employee = new Employee();
			employee.setJob(new Job(jobId));
			employee.setName(name);
			employee.setCardId(cardId);
			employee.setSex(sex);
			employee.setPhone(phone);
			employee.setDept(new Dept(deptId));
			request.setAttribute("employee", employee);
			
			PageModel<Employee> pm = es.findPageEntity(pageIndex, employee);
			request.setAttribute("pageModel", pm);
			
			List<Job> jobList = js.findAll();
			request.setAttribute("jobList", jobList);
			
			List<Dept> deptList = ds.findAll();
			request.setAttribute("deptList", deptList);
			
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if ("employeeadd.action".equals(uri)) {
			//【进入员工添加页面】
			List<Job> jobList = js.findAll();
			request.setAttribute("jobs", jobList);
			
			List<Dept> deptList = ds.findAll();
			request.setAttribute("depts", deptList);
			
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}else if ("carIdAjax".equals(uri)) {
			//【校验身份证号是否相等】
			String cardId = request.getParameter("cardId");
			Employee employee = es.findByCarId(cardId);
			
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(employee);
			out.print(json);
			out.close();
		}else if ("saveOrUpdateEmployee.action".equals(uri)) {
			//【添加|修改员工】
			int flag = 0;
			
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
				flag = 1;
			}
			String name = request.getParameter("name");
			String cardId = request.getParameter("cardId");
			String sexString = request.getParameter("sex");
			int sex = 0;
			if (sexString!=null && !"".equals(sexString)) {
				sex = Integer.parseInt(sexString);
			}
			String jobIdString = request.getParameter("job_id");
			int jobId = 0;
			if (jobIdString!=null && !"".equals(jobIdString)) {
				jobId = Integer.parseInt(jobIdString);
			}
			String education = request.getParameter("education");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String tel = request.getParameter("tel");
			String party = request.getParameter("party");
			String qqNum = request.getParameter("qqNum");
			String address = request.getParameter("address");
			String postCode = request.getParameter("postCode");
			String birthdayString = request.getParameter("birthday");
			Date birthday = new MySimpleDateFormat().formart(birthdayString);
			String race = request.getParameter("race");
			String speciality = request.getParameter("speciality");
			String hobby = request.getParameter("hobby");
			String remark = request.getParameter("remark");
			String deptIdString = request.getParameter("dept_id");
			int deptId = 0;
			if (deptIdString!=null && !"".equals(deptIdString)) {
				deptId = Integer.parseInt(deptIdString);
			}
			
			Employee employee = new Employee();
			employee.setId(id);
			employee.setName(name);
			employee.setCardId(cardId);
			employee.setAddress(address);
			employee.setPostCode(postCode);
			employee.setTel(tel);
			employee.setPhone(phone);
			employee.setQqNum(qqNum);
			employee.setEmail(email);
			employee.setSex(sex);
			employee.setParty(party);
			employee.setBirthday(birthday);
			employee.setRace(race);
			employee.setEducation(education);
			employee.setSpeciality(speciality);
			employee.setHobby(hobby);
			employee.setRemark(remark);
			if (flag == 0) {
				employee.setCreateDate(new Date());
			}
			employee.setDept(new Dept(deptId));
			employee.setJob(new Job(jobId));
			
			if (flag == 0) {
				es.save(employee);				
			}else {
				es.update(employee);
			}
			
			request.getRequestDispatcher("/employeelist.action").forward(request, response);
		}else if ("updateEmployee.action".equals(uri)) {
			//【进入员工编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Employee employee = es.findById(id);
			request.setAttribute("employee", employee);
			
			List<Job> jobList = js.findAll();
			request.setAttribute("jobs", jobList);
			
			List<Dept> deptList = ds.findAll();
			request.setAttribute("depts", deptList);
			
			request.setAttribute("val", "edit");
			
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}else if ("employeedel.action".equals(uri)) {
			//【删除部门】
			String[] ids = request.getParameterValues("employeeIds");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				es.delete(id);
			}
			request.getRequestDispatcher("/employeelist.action").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
