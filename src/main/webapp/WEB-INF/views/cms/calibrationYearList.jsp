<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>年度校准计划</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
            function exportFile() {
                exportExcel('${ctx}/custom/cms/calibrationYear/exportFile?'+$('#mainForm').serialize());
            }
		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/calibrationYear" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">年度校准计划</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">待校准日期：</td>
				<td class="td_table_2">
					<input type="text" name="filter_GED_expectDate" value="${param['filter_GED_expectDate']}" class="input_240" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>~
					<input type="text" name="filter_LED_expectDate" value="${param['filter_LED_expectDate']}" class="input_240" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_2" align="center" colspan="2">
					<input type='submit' id="searchBtn" class='button_70px' value='查询'/>
					<input type="button" class='button_70px' value="导出Excel" onclick="exportFile();"/>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=5% class="td_list_1">序号</td>
				<td align=center width=20% class="td_list_1">器具编号</td>
				<td align=center width=35% class="td_list_1">器具名称</td>
				<td align=center width=20% class="td_list_1">所在房间</td>
				<td align=center width=10% class="td_list_1">待校准日期</td>
				<td align=center width=5% class="td_list_1">校准方式</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${index.count}</td>
					<td class="td_list_2" align=left>${item.equipment.code}</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.calibrationMode == 1 ? '内校':item.calibrationMode == 2 ? '外校':item.calibrationMode == 3 ? '临校':''}</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
