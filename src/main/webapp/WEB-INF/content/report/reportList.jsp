<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/styles/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
</head>

<body>
	<form id="mainForm" action="${ctx}/dyc/report/list" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>

		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>编号：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_processNo" value="${param['filter_LIKES_processNo']}"/>
				</td>
				<td class="td_table_1">
					<span>关键字：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_processName" value="${param['filter_LIKES_processName']}"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">
					<span>部门：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQL_deptId" value="${param['filter_EQL_deptId']}"/>
				</td>
				<td class="td_table_1">
					<span>级别：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQS_level" value="${param['filter_EQS_level']}"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">
					<span>发现时间起：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="startTime1" value="${param['startTime1']}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
				</td>
				<td class="td_table_1">
					<span>发现时间止：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="startTime2" value="${param['startTime2']}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">
					<span>物料名称：</span>
				</td>
				<td class="td_table_2" colspan="3">
					<input type="text" class="input_240" name="filter_LIKES_backup1" value="${param['filter_LIKES_backup1']}"/>
				</td>
			</tr>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=45% class="td_list_1" nowrap>编号</td>
				<td align=center width=45% class="td_list_1" nowrap>名称</td>
				<td align=center width=10% class="td_list_1" nowrap>操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left nowrap>${item.processNo}</td>
					<td class="td_list_2" align=left nowrap>${item.processName}</td>
					<td class="td_list_2" align=left nowrap></td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
</body>
</html>
