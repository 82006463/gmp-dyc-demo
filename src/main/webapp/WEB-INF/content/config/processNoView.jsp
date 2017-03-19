<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-流程编号</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		</script>
	</head>

	<body>
		<form id="inputForm">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-流程编号</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>

				<tr>
					<td class="td_table_1">流程前缀：</td>
					<td class="td_table_2">${entity.prefix}</td>
					<td class="td_table_1">时间模式：</td>
					<td class="td_table_2">${entity.timePattern}</td>
				</tr>

				<tr>
					<td class="td_table_1">部门状态：</td>
					<td class="td_table_2">${entity.orgState==1 ? '启用':'停用'}</td>
					<td class="td_table_1">部门编号：</td>
					<td class="td_table_2">${entity.orgCode}</td>
				</tr>

				<tr>
					<td class="td_table_1">流水号长度：</td>
					<td class="td_table_2" colspan="3">${entity.indexLength}</td>
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
