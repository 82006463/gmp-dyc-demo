<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>通知</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">通知</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">系统版本<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">${entity.sysVer}</td>
					<td class="td_table_1">发送日期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.sendTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="td_table_1">模块<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">${entity.module}</td>
					<td class="td_table_1">模块版本<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">${entity.moduleVer}</td>
				</tr>

				<tr>
					<td class="td_table_1">通知标题：</td>
					<td class="td_table_2" colspan="3">${entity.subject}</td>
				</tr>
				<tr>
					<td class="td_table_1">通知内容：</td>
					<td class="td_table_2" colspan="3">${entity.content}</td>
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
