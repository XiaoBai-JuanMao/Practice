<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>人事管理系统——用户管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
	<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
	<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
	<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
	
	$(function(){
    	/** 员工表单提交 */
		$("#userForm").submit(function(){
			var statusReg = /^[12]$/;
			var loginnameReg = /^\w{6,20}$/;
			var passwordReg = /^\w{6,20}$/;
			
			var id = $("#id").val();
			var username = $("#username");
			var status = $("#status");
			var loginname = $("#loginname");
			var password = $("#password");
			var msg = "";
			if ($.trim(username.val()) == ""){
				msg = "姓名不能为空！";
				username.focus();
			}else if(!statusReg.test($.trim(status.val()))){
				msg = "状态只能为数字[1|2]！";
				status.focus();
			}else if (!loginnameReg.test($.trim(loginname.val()))){
				msg = "登录名长度必须是6~20之间！";
				loginname.focus();					
			}else if ((id==null||id=="")&&!checkLoginName()) {
				msg = "登录名已存在！";
				loginname.focus();
			}else if (!passwordReg.test($.trim(password.val()))){
				msg = "密码长度必须是6~20之间！";
				password.focus();
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#userForm").submit();
		})
    })
	function checkLoginName(){
		var loginname = $("#loginname").val();
		var flag = true;
		$.ajax({
			url:"${ctx}/loginnameAjax",
			type:"get",
			data:{"loginname":loginname},
			dataType:"json",
			success:function(data){
				if (data!=null) {
					flag = false;
				}
			},
			error:function(data){
				alert("Ajax请求失败！");
				flag = false;
			}
		})
		alert("校验表单中...");
		return flag;
	}
	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
    <c:choose>
		<c:when test="${val=='add' }">
			<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：用户管理  &gt; 添加用户</td>
		</c:when>
		<c:otherwise>
			<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：用户管理  &gt; 编辑用户</td>
		</c:otherwise>
	</c:choose>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
<form action="${ctx}/userAddOrEdit.action" id="userForm" method="post">
<table width="100%"  border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr><td>${message}</td></tr>
  <tr valign="top">
    <td>
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		<tr style="display: none;">
		    			<td class="font3 fftd">I&nbsp;D：<input type="text" name="id" id="id" size="20" value="${user.id }" /></td>
		    		<tr>
		    		
		    		<tr>
		    			<td class="font3 fftd">姓&nbsp;名：<input type="text" name="username" id="username" size="20" value="${user.username }" /></td>
		    			<td class="font3 fftd">状&nbsp;态：<input type="text" name="status" id="status" size="20" value="${user.status }" /></td>
		    		</tr>
		    			
		    		<tr>
		    			<td class="font3 fftd">登录名：<input name="loginname" id="loginname" size="20" value="${user.loginname }" /></td>
		    			<td class="font3 fftd">密&nbsp;码：<input name="password" id="password" size="20" value="${user.password }" /></td>
		    		</tr>
		    	
		    	</table>
		    </td></tr>
			<tr>
				<td align="left" class="fftd">
					<c:choose>
						<c:when test="${val=='add' }">
							<input type="submit" value="添加">
						</c:when>
						<c:otherwise>
							<input type="submit" value="修改">
						</c:otherwise>
					</c:choose>
					&nbsp;&nbsp;<input type="reset" value="取消 ">
				</td>
			</tr>
		  </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>