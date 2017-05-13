<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>角色管理</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/security/role/update" method="post">
			<input type="hidden" name="id" id="id" value="${id }"/>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">角色编号：</td>
					<td class="td_table_2">${role.code}</td>
					<td class="td_table_1">角色名称：</td>
					<td class="td_table_2">${role.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">角色描述：</td>
					<td class="td_table_2" colspan="3">${role.remark}</td>
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
					<td align=center width=45% class="td_list_1">权限编号</td>
					<td align=center width=55% class="td_list_1">权限名称</td>
				</tr>
				<c:forEach items="${role.authorities}" var="item">
					<tr>
						<td class="td_list_2" align=left>${item.code}</td>
						<td class="td_list_2" align=left>${item.name}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
