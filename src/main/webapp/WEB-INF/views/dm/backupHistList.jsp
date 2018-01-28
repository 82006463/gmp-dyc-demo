<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>备份历史</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
            function exportFile() {
                exportExcel('${ctx}/custom/cms/equipment/exportFile?'+$('#mainForm').serialize());
            }
		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/dm/backupHist" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">备份历史</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">任务编号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQS_code" value="${param['filter_EQS_code']}"/>
				</td>
				<td class="td_table_1">任务名称：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_name" value="${param['filter_LIKES_name']}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<input type='submit' class='button_70px' value='查询' onclick="search();"/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=15% class="td_list_1">备份记录号</td>
				<td align=center width=25% class="td_list_1">客户端</td>
				<td align=center width=15% class="td_list_1">备份类型</td>
				<td align=center width=5% class="td_list_1">备份开始时间</td>
				<td align=center width=10% class="td_list_1">备份结束时间</td>
				<td align=center width=10% class="td_list_1">操作方式</td>
				<td align=center width=10% class="td_list_1">有无更新</td>
				<td align=center width=10% class="td_list_1">成功数量</td>
				<td align=center width=5% class="td_list_1">失败数量</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>${item.bakType == 1 ? '':item.bakType == 2 ? '':''}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="td_list_2" align=left>${item.sourceMd5}</td>
					<td class="td_list_2" align=left>${item.targetMd5}</td>
					<td class="td_list_2" align=left>${item.sourceMd5 == item.targetMd5 ? '一致':'不一致'}</td>
					<td class="td_list_2" align=left>${item.sourceMd5}</td>
					<td class="td_list_2" align=left>${item.targetMd5}</td>
					<td class="td_list_2" align=left>
						<a href="${ctx}/custom/dm/backupHist/view?id=${item.id}" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
