<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>图表管理-${etab.name}</title>
	<%@ include file="/common/common-list.jsp"%>
</head>
<body>
	<form id="mainForm" action="${ctx}/dyc/chart/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<input type="hidden" class="input_240" name="type" value="${param['type']}"/>
		<input type="hidden" class="input_240" name="view" value="${param['view']}"/>
		${jsonSearch}
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" class="td_list_1">编号</td>
				<td align="center" class="td_list_1">名称</td>
				<td align="center" width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>
						<a href="${ctx}/dyc/chart/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
						<a href="${ctx}/dyc/chart/update/${item.id }" class="btnEdit" title="编辑">编辑</a>
						<a href="${ctx}/dyc/chart/view/${item.id}?view=${param['view']}" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
</body>
</html>
