<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
</head>

<body>
	<form id="inputForm" action="${ctx}/dyc/report/update" method="post">
		<input type="hidden" name="id" value="${entity.id}"/>
		<input type="hidden" name="processType" value="${entity.processType}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					${entity.processType=='dev' ? '偏差':entity.processType=='cc' ? '变更':entity.processType=='capa' ? 'CAPA':entity.processType=='oos'? 'OOS':''}编号：
				</td>
				<td class="td_table_2">${entity.processNo}</td>
				<td class="td_table_1">发现时间：</td>
				<td class="td_table_2"><fmt:formatDate value="${entity.occurTime}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="td_table_1">部门：</td>
				<td class="td_table_2">${entity.deptName}</td>
				<td class="td_table_1">级别：</td>
				<td class="td_table_2">
					<frame:dict name="level" type="select" typeCode="devLevel" value="${entity.level}" displayType="1"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">物料产品名称：</td>
				<td class="td_table_2">${entity.materielName}
				</td>
				<td class="td_table_1">发现人：</td>
				<td class="td_table_2">${entity.occurPerson}</td>
			</tr>
			<tr>
				<td class="td_table_1">描述：</td>
				<td class="td_table_2" colspan="3">${entity.processDesc}</td>
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
