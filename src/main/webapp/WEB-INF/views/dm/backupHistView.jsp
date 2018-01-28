<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>备份历史</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">备份历史</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">备份记录号：</td>
					<td class="td_table_2">${entity.code}</td>
				</tr>
				<tr>
					<td class="td_table_1">客户端：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">备份类型：</td>
					<td class="td_table_2">${entity.bakType == 1 ? '文档备份':entity.bakType == 2 ? '数据库备份':''}</td>
				</tr>
				<tr>
					<td class="td_table_1">备份开始时间：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td_table_1">备份结束时间：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td_table_1">操作方式：</td>
					<td class="td_table_2">${entity.backupMode == 1 ? '自动备份':entity.backupMode == 2 ? '手动备份':''}</td>
				</tr>
				<tr>
					<td class="td_table_1">有无更新：</td>
					<td class="td_table_2">${entity.sourceMd5 == entity.targetMd5 ? '无更新':'有更新'}</td>
				</tr>
				<tr>
					<td class="td_table_1">成功数量：</td>
					<td class="td_table_2">${entity.successCount}</td>
				</tr>
				<tr>
					<td class="td_table_1">失败数量：</td>
					<td class="td_table_2">${entity.failCount}</td>
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
