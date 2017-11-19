<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>标准项</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/standardItem" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">标准项</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">标准项编号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQS_code" value="${param['filter_EQS_code']}"/>
				</td>
				<td class="td_table_1">标准项名称：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_name" value="${param['filter_LIKES_name']}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
						<shiro:hasPermission name="cms_standardItem_edit">
							<input type='button' onclick="addNew('${ctx}/custom/cms/standardItem/create')" class='button_70px' value='新建'/>
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
				<td align=center width=25% class="td_list_1">标准项编号</td>
				<td align=center width=30% class="td_list_1">标准项名称</td>
				<td align=center width=15% class="td_list_1">测量范围下限-上限</td>
				<td align=center width=5% class="td_list_1">不确定度</td>
				<td align=center width=10% class="td_list_1">校准参量</td>
				<td align=center width=5% class="td_list_1">状态</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>【${item.measureRangeMin}】至【${item.measureRangeMax}】</td>
					<td class="td_list_2" align=left>${item.uncertainty}</td>
					<td class="td_list_2" align=left>${item.param}</td>
					<td class="td_list_2" align=left>${item.status == 0 ? '删除':item.status == 1 ? '复核中':'正常'}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<c:if test="${item.status > 0}">
								<shiro:hasPermission name="cms_standardItem_delete">
									<a href="${ctx}/custom/cms/standardItem/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="cms_standardItem_edit">
									<a href="${ctx}/custom/cms/standardItem/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
								</shiro:hasPermission>
								<c:if test="${item.status == 1}">
									<shiro:hasPermission name="cms_standardItem_review">
										<a href="${ctx}/custom/cms/standardItem/update/${item.id}?op=2" class="btnEdit" title="复核">复核</a>
									</shiro:hasPermission>
								</c:if>
							</c:if>
							<a href="${ctx}/custom/cms/standardItem/view/${item.id}" class="btnView" title="查看">查看</a>
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
