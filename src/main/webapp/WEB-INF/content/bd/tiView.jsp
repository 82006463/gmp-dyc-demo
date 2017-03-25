<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-测试项</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	</head>

	<body>
		<form id="inputForm">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-测试项</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">版本：</td>
					<td class="td_table_2">${entity.ver}</td>
				</tr>
				<tr>
					<td class="td_table_1">描述：</td>
					<td class="td_table_2">${entity.remark}</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">类型：</td>
					<td class="td_table_2">
						<frame:dict name="dataType" type="select" typeCode="dataType" value="${entity.dataType}" displayType="1"/>
					</td>
					<td class="td_table_1">运算符：</td>
					<td class="td_table_2">
						<frame:dict name="operator" type="select" typeCode="operator" value="${entity.operator}" displayType="1"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">内控标准：</td>
					<td class="td_table_2">${entity.innerQs}</td>
					<td class="td_table_1">法定标准：</td>
					<td class="td_table_2">${entity.lawQs}</td>
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
