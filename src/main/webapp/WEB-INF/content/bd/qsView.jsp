<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-质量标准</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	</head>

	<body>
		<form id="inputForm">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-质量标准</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">质量标准编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">质量标准版本：</td>
					<td class="td_table_2">${entity.ver}</td>
				</tr>

				<tr>
					<td class="td_table_1">物料产品名称：</td>
					<td class="td_table_2">${entity.materielName}</td>
					<td class="td_table_1">物料产品代码：</td>
					<td class="td_table_2">${entity.materielCode}</td>
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
