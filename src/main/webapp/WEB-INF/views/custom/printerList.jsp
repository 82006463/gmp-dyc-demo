<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>打印机</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/equipment" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">打印机</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">打印机编号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQS_code" value="${param['filter_EQS_code']}"/>
				</td>
				<td class="td_table_1">打印机名称：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_name" value="${param['filter_LIKES_name']}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
						<shiro:hasPermission name="custom_printer_edit">
							<input type='button' onclick="addNew('${ctx}/custom/printer/create')" class='button_70px' value='新建'/>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${!empty lookup}">
						<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:if>
					<input type='submit' class='button_70px' value='查询' onclick="search();"/>
					<shiro:hasPermission name="cms_equipment_export">
						<input type="button" class='button_70px' value="导出Excel" onclick="exportFile();"/>
					</shiro:hasPermission>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=15% class="td_list_1">打印机编号</td>
				<td align=center width=25% class="td_list_1">打印机名称</td>
				<td align=center width=15% class="td_list_1">打印机IP</td>
				<td align=center width=5% class="td_list_1">打印机端口</td>
				<td align=center width=10% class="td_list_1">打印机RMI端口</td>
				<td align=center width=5% class="td_list_1">状态</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>${item.ip}</td>
					<td class="td_list_2" align=left>${item.port}</td>
					<td class="td_list_2" align=left>${item.rmiPort}</td>
					<td class="td_list_2" align=left>${item.status <= 0 ? '失效':item.status==1 ? '暂存中':item.status==2 ? '拒绝中':item.status==3 ? '复核中':'正常'}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<c:if test="${item.status > 0}">
								<shiro:hasPermission name="custom_printer_delete">
									<a href="${ctx}/custom/printer/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
								</shiro:hasPermission>
								<c:if test="${(item.status == 1 || item.status == 2 || item.status >= 4)}">
									<shiro:hasPermission name="custom_printer_edit">
										<a href="${ctx}/custom/printer/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
									</shiro:hasPermission>
								</c:if>
								<c:if test="${(item.status == 3) && userId != item.updaterId}">
									<shiro:hasPermission name="custom_printer_review">
										<a href="${ctx}/custom/printer/update/${item.id}" class="btnEdit" title="复核">复核</a>
									</shiro:hasPermission>
								</c:if>
							</c:if>
							<a href="${ctx}/custom/cms/equipment/view/${item.id}" class="btnView" title="查看">查看</a>
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
