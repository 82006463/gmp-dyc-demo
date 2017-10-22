<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>计量公司</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">计量公司</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">企业编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">企业名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">信用代码：</td>
					<td class="td_table_2">${entity.creditCode}</td>
					<td class="td_table_1">企业简称：</td>
					<td class="td_table_2">${entity.enterpriseShort}</td>
				</tr>
				<tr>
					<td class="td_table_1">地址：</td>
					<td class="td_table_2">${entity.addr}</td>
					<td class="td_table_1">网址：</td>
					<td class="td_table_2">${entity.url}</td>
				</tr>
				<tr>
					<td class="td_table_1">认可有效期：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.expireDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_table_1">建标数量：</td>
					<td class="td_table_2">${entity.buildCount}</td>
				</tr>
				<tr>
					<td class="td_table_1">审计报告：</td>
					<td class="td_table_2" colspan="3">${entity.auditReport}</td>
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
