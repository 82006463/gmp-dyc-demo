<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>配置管理-数据字典</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">数据字典编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">数据字典名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">选项列表：</td>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="itemTable" style="margin: 0">
							<tr>
								<td align=center width=25% class="td_list_1">选项编号</td>
								<td align=center width=60% class="td_list_1">选项名称</td>
							</tr>
							<c:forEach items="${entity.items}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2" align=left>${item.code}</td>
									<td class="td_list_2" align=left>${item.name}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
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
