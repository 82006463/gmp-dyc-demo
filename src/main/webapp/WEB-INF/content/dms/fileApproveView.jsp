<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>帐号查看</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm">
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">账号：</td>
					<td class="td_table_2">${entity.username}</td>
					<td class="td_table_1">姓名：</td>
					<td class="td_table_2">${entity.fullname}</td>
				</tr>
				<tr>
					<td class="td_table_1">邮箱：</td>
					<td class="td_table_2" colspan="3">${entity.email}</td>
				</tr>
				<tr>
					<td class="td_table_1">部门：</td>
					<td class="td_table_2" colspan="3">${entity.org.name}</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
			
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align=center width=40% class="td_list_1" >角色编号</td>
					<td align=center width=45% class="td_list_1">角色名称</td>
				</tr>

				<c:forEach items="${user.roles}" var="role">
					<tr>
						<td class="td_list_2" align=left>${role.code}</td>
						<td class="td_list_2" align=left>${role.name}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
