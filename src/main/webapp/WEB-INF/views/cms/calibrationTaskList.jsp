<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>待办任务</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/wbox/wbox/wbox.css" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/wbox/wbox.js"></script>
		<script type="text/javascript">
			var iframewbox;
			function openTasks(opTag, op, taskId) {
				iframewbox = $(opTag).wBox({
					requestType: "iframe",
					iframeWH: {width: 1200, height: 500},
					title: "任务审批",
					show: true,
					target: "${ctx}/custom/cms/calibrationTask/"+ op +"/" + taskId
				});
				return false;
			}

			function callbackProcess(id, name) {
				if (iframewbox) {
					iframewbox.close();
				}
			}
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
							<option value="${item.id}" ${param['filter_EQL_measureCompId']==item.id ? 'selected="selected"':''}>${item.name}</option>
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
						<a href="#" class="btnEdit" title="编辑" onclick="return openTasks(this, 'update', ${item.id});">编辑</a>
						<a href="#" class="btnView" title="查看" onclick="return openTasks(this, 'view', ${item.id});">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages}" totalRecords="${page.totalCount}" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
