<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>菜单管理</title>
		<%@ include file="/common/common-view.jsp"%>
	</head>

	<body>
		<form id="inputForm">
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">菜单名称：</td>
					<td class="td_table_2" colspan="3">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">上级菜单：</td>
					<td class="td_table_2" colspan="3">${entity.parentMenu.name}</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
