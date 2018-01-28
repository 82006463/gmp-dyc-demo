<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>备份文档</title>
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
	<form id="mainForm" action="${ctx}/custom/dm/backupDoc" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">备份文档</td>
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
					<c:if test="${empty lookup}">
						<shiro:hasPermission name="dm_backupDoc_edit">
							<input type='button' onclick="addNew('${ctx}/custom/dm/backupDoc/create')" class='button_70px' value='新建'/>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${!empty lookup}">
						<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:if>
					<input type='submit' class='button_70px' value='查询' onclick="search();"/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=15% class="td_list_1">任务编号</td>
				<td align=center width=25% class="td_list_1">任务名称</td>
				<td align=center width=15% class="td_list_1">客户端</td>
				<td align=center width=5% class="td_list_1">备份源</td>
				<td align=center width=10% class="td_list_1">备份频次</td>
				<td align=center width=10% class="td_list_1">创建时间</td>
				<td align=center width=5% class="td_list_1">状态</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>${item.host}</td>
					<td class="td_list_2" align=left>${item.bakSource}</td>
					<td class="td_list_2" align=left>${item.cron}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="td_list_2" align=left>${item.status <= 0 ? '失效':'正常'}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<c:if test="${item.status > 0}">
								<shiro:hasPermission name="dm_backupDoc_delete">
									<a href="${ctx}/custom/dm/backupDoc/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="dm_backupDoc_edit">
									<a href="${ctx}/custom/dm/backupDoc/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
								</shiro:hasPermission>
							</c:if>
							<a href="${ctx}/custom/dm/backupDoc/view/${item.id}" class="btnView" title="查看">查看</a>
						</c:if>
						<c:if test="${!empty lookup}">
							<a href="javascript:void(0)" class="btnSelect" title="选择" onclick="bringback('${item.id}','${item.name}')">选择</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
