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
	<form id="mainForm" action="${ctx}/custom/cms/calibrationTask" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">待办任务</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">计量公司：</td>
				<td class="td_table_2">
					<select id="filter_EQL_measureCompId" name="filter_EQL_measureCompId" class="input_select">
						<option value="">-请选择-</option>
						<c:forEach items="${measureComps}" var="item">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_table_1">计量公司负责人：</td>
				<td class="td_table_2">
					<input type="text" id="filter_EQS_approver" name="filter_EQS_approver" value="${param['filter_EQS_approver']}" class="input_240"/>
				</td>
				<td class="td_table_2">
					<input type='submit' id="searchBtn" class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=10% class="td_list_1">任务编号</td>
				<td align=center width=70% class="td_list_1">计量公司</td>
				<td align=center width=10% class="td_list_1">任务生成日期</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${index.count}</td>
					<td class="td_list_2" align=left>${item.measureComp.name}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="td_list_2" align=left>
						<a href="${ctx}/custom/cms/calibrationTask/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
						<a href="${ctx}/custom/cms/calibrationTask/view/${item.id}" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages}" totalRecords="${page.totalCount}" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
