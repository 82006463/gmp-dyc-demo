<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>备份详情</title>
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
	<form id="mainForm" action="${ctx}/custom/dm/backupDetail" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">备份详情</td>
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
				<td align=center width=15% class="td_list_1">文件/数据库名称</td>
				<td align=center width=25% class="td_list_1">文件源路径</td>
				<td align=center width=15% class="td_list_1">文件备份路径</td>
				<td align=center width=5% class="td_list_1">备份完成时间</td>
				<td align=center width=10% class="td_list_1">源文件MD5</td>
				<td align=center width=10% class="td_list_1">备份件MD5</td>
				<td align=center width=10% class="td_list_1">MD5一致</td>
				<td align=center width=10% class="td_list_1">结果</td>
				<td align=center width=5% class="td_list_1">说明</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>${item.sourcePath}</td>
					<td class="td_list_2" align=left>${item.targetPath}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="td_list_2" align=left>${item.sourceMd5}</td>
					<td class="td_list_2" align=left>${item.targetMd5}</td>
					<td class="td_list_2" align=left>${item.sourceMd5 == item.targetMd5 ? '一致':'不一致'}</td>
					<td class="td_list_2" align=left>${item.sourceMd5}</td>
					<td class="td_list_2" align=left>${item.targetMd5}</td>
					<td class="td_list_2" align=left>
						<a href="${ctx}/custom/dm/backupDoc/view/${item.id}" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
