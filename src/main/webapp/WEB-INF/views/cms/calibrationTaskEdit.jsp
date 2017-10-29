<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>待办任务</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/calibrationTask/update" method="get">
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">待办任务</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=10% class="td_list_1">器具编号</td>
				<td align=center width=20% class="td_list_1">器具名称</td>
				<td align=center width=10% class="td_list_1">所在房间</td>
				<td align=center width=10% class="td_list_1">上次校准时间</td>
				<td align=center width=10% class="td_list_1">上次校准单位</td>
				<td align=center width=10% class="td_list_1">校准有效期</td>
				<td align=center width=10% class="td_list_1">校准结果</td>
				<td align=center width=10% class="td_list_1">实际校准时间</td>
				<td align=center width=10% class="td_list_1">动作</td>
			</tr>
			<c:forEach items="${children}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${item.equipment.code}</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.lastActualDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.equipment.calibrationCycle}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.calibrationResult}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>

					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	</body>
</html>
