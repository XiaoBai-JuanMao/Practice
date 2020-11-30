<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 定义全局变量描述站点名 -->
<c:set var="ctx" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.media.js"></script>
<script type="text/javascript">
	$(function () {
		$('#handout_wrap_inner').media({
 	        width: '100%',
 	        autoplay: true,
 	        src:'http://storage.xuetangx.com/public_assets/xuetangx/PDF/PlayerAPI_v1.0.6.pdf',
	    });
	})
</script>
<head>
<meta charset="UTF-8">
<title>预览页面</title>
</head>
<body>
	<div id="handout_wrap_inner"></div>
</body>
</html>