package com.gec.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.bean.Document;
import com.gec.bean.PageModel;
import com.gec.bean.User;
import com.gec.service.DocumentService;
import com.gec.service.impl.DocumentServiceImpl;

@WebServlet(urlPatterns = {"/selectDocument.action","/documentlist.action","/documentadd.action",
							"/document/saveOrUpdateDocument.action","/document/updateDocument.action",
							"/documentdownload.action","/document/removeDocument.action","/viewPdfFile.action"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DocumentService ds = new DocumentServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if ("selectDocument.action".equals(uri)) {
			//【菜单打开文档查询列表】
			request.getRequestDispatcher("/documentlist.action").forward(request, response);
		}else if ("documentlist.action".equals(uri)) {
			//【查询文档列表】
			int pageIndex = 1;
			String pageIndexString = request.getParameter("pageIndex");
			if (pageIndexString!=null && !"".equals(pageIndexString)) {
				pageIndex = Integer.parseInt(pageIndexString);
			}
			String title = request.getParameter("title");
			
			Document document = new Document();
			document.setTitle(title);
			request.setAttribute("document", document);
			
			PageModel<Document> pm = ds.findPageEntity(pageIndex, document);
			request.setAttribute("pageModel", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}else if ("documentadd.action".equals(uri)) {
			//【进入上传文档页面】
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
		}else if ("saveOrUpdateDocument.action".equals(uri)) {
			//【上传|更新文档】
			int flag = 0;
			
			Document document = new Document();
			if(ServletFileUpload.isMultipartContent(request)) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> list = upload.parseRequest(request);
					if(list!=null) {
						for (FileItem item : list) {
							if(item.isFormField()) {
								if(item.getFieldName().equals("id")) {
									String idString = item.getString("utf-8");
									int id = 0;
									if (idString!=null && !"".equals(idString)) {
										id = Integer.parseInt(idString);
										flag = 1;
									}
									document.setId(id);
								}
								if (item.getFieldName().equals("title")) {
									document.setTitle(item.getString("utf-8"));
								}
								if (item.getFieldName().equals("remark")) {
									document.setRemark(item.getString("utf-8"));
								}
								if (item.getFieldName().equals("userId")) {
									String userIdString = item.getString("utf-8");
									int userId = 0;
									if (userIdString!=null && !"".equals(userIdString)) {
										userId = Integer.parseInt(userIdString);
									}
									document.setUser(new User(userId));
								}
							}else {
								String filmUrl = "/ueditor/jsp/upload/file";
								String path = request.getServletContext().getRealPath(filmUrl);
								File file = new File(path);
								if(!file.exists())
									file.mkdirs();
								String fileName = item.getName();
								String filetype = fileName.substring(fileName.lastIndexOf(".")+1);
								fileName = fileName.substring(0,fileName.lastIndexOf(".")) 
										+ System.currentTimeMillis() 
										+ fileName.substring(fileName.lastIndexOf("."));
								File newFile = new File(file, fileName);
								item.write(newFile);
								System.out.println("上传成功!");
								document.setFileName(fileName);
								document.setFiletype(filetype);
								document.setFileUrl(filmUrl);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("非二进制文本");
			}
			
			if (flag==0) {
				document.setCreateDate(new Date());
				ds.save(document);				
			}else {
				ds.update(document);
			}
			
			request.getRequestDispatcher("/documentlist.action").forward(request, response);
		}else if ("updateDocument.action".equals(uri)) {
			//【进入文档编辑页面】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Document document = ds.findById(id);
			request.setAttribute("document", document);
			request.setAttribute("val", "edit");
			
			request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
		}else if ("documentdownload.action".equals(uri)) {
			//【下载文档】
			String idString = request.getParameter("id");
			int id = 0;
			if (idString!=null && !"".equals(idString)) {
				id = Integer.parseInt(idString);
			}
			
			Document document = ds.findById(id);
			
			String fileName = document.getFileName();
			String path = request.getServletContext().getRealPath(document.getFileUrl());
			//File.separator自适应斜线
			InputStream in = new FileInputStream(path+File.separator+fileName);
			byte[] b = new byte[1024];
			response.setHeader("Content-Disposition", "attachment;filename="+
					new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			int len = 0;
			while ((len=in.read(b))>0) {
				out.write(b,0,len);
			}
			in.close();
			out.flush();
			out.close();
		}else if ("removeDocument.action".equals(uri)) {
			//【删除文档】
			String idsString = request.getParameter("ids");
			String[] ids = idsString.split(",");
			for (String idString : ids) {
				int id = Integer.parseInt(idString);
				ds.delete(id);
			}
			request.getRequestDispatcher("/documentlist.action").forward(request, response);
		}else if ("viewPdfFile.action".equals(uri)) {
			//【预览Pdf文档】
			String filePath = request.getParameter("filePath");
			request.setAttribute("filePath", filePath);
			request.getRequestDispatcher("/WEB-INF/jsp/document/viewPdfFile.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
