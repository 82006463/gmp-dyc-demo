<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>备份详情</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">备份详情</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">文件/数据库名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">文件源路径：</td>
					<td class="td_table_2">${entity.sourcePath}</td>
				</tr>
				<tr>
					<td class="td_table_1">文件备份路径：</td>
					<td class="td_table_2">${entity.targetPath}</td>
				</tr>
				<tr>
					<td class="td_table_1">备份完成时间：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td_table_1">源文件MD5：</td>
					<td class="td_table_2">${entity.sourceMd5}</td>
				</tr>
				<tr>
					<td class="td_table_1">备份件MD5：</td>
					<td class="td_table_2">${entity.targetMd5}</td>
				</tr>
				<tr>
					<td class="td_table_1">MD5一致：</td>
					<td class="td_table_2">${entity.sourceMd5 == entity.targetMd5 ? '一致':'不一致'}</td>
				</tr>
				<tr>
					<td class="td_table_1">结果：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">说明：</td>
					<td class="td_table_2">${entity.name}</td>
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
