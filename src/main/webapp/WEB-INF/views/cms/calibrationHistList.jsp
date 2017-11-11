<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>校准历史</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/calibrationTmp" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">校准历史</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=25% class="td_list_1">器具编号</td>
				<td align=center width=35% class="td_list_1">器具名称</td>
				<td align=center width=10% class="td_list_1">上次校准时间</td>
				<td align=center width=5% class="td_list_1">校准方式</td>
				<td align=center width=10% class="td_list_1">记录/证书编号</td>
				<td align=center width=5% class="td_list_1">校准结果</td>
				<td align=center width=10% class="td_list_1">实际校准时间</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${item.equipment.code}</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty item.lastActualDate}">N.A.</c:if>
						<c:if test="${!empty item.lastActualDate}"><fmt:formatDate value="${item.lastActualDate}" pattern="yyyy-MM-dd"/></c:if>
					</td>
					<td class="td_list_2" align=left>${item.calibrationMode==1 ? '内校':item.calibrationMode==2 ? '外校':item.calibrationMode==3 ? '临校':'N.A.'}</td>
					<td class="td_list_2" align=left>${item.certCode}</td>
					<td class="td_list_2" align=left>${item.calibrationResult==1 ? '合格':item.calibrationResult==0 ? '不合格':'N.A.'}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
