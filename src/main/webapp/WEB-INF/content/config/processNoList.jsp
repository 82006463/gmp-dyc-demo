<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-流程编号</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/config/processNo/list?lookup=${lookup }" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">配置管理-流程编号</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">流程类型：</td>
				<td class="td_table_2">
					<input type="text" name="filter_EQS_type" value="${param['filter_EQS_type']}" class="input_240"/>
				</td>
				<td class="td_table_1">流程前缀：</td>
				<td class="td_table_2">
					<input type="text" name="filter_EQS_prefix" value="${param['filter_EQS_prefix']}" class="input_240"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
				<c:choose>
					<c:when test="${empty lookup}">
						<input type='button' onclick="addNew('${ctx}/config/processNo/create')" class='button_70px' value='新建'/>
					</c:when>
					<c:otherwise>
						<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:otherwise>
				</c:choose>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center class="td_list_1">编号</td>
				<td align=center class="td_list_1">名称</td>
				<td align=center class="td_list_1">流程前缀</td>
				<td align=center class="td_list_1">时间模式</td>
				<td align=center class="td_list_1">部门编号</td>
				<td align=center class="td_list_1">流水号长度</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>${item.prefix}</td>
					<td class="td_list_2" align=left>${item.timePattern}</td>
					<td class="td_list_2" align=left>${item.orgCode}</td>
					<td class="td_list_2" align=left>${item.indexLength}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<a href="${ctx}/config/processNo/delete/${item.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
							<a href="${ctx}/config/processNo/update/${item.id }" class="btnEdit" title="编辑">编辑</a>
							<a href="${ctx}/config/processNo/view/${item.id }" class="btnView" title="查看">查看</a>
						</c:if>
						<c:if test="${!empty lookup}">
							<a href="javascript:void(0)" class="btnSelect" title="选择" onclick="bringback('${item.id}','${item.name}')">选择</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup }"/>
		</table>
	</form>
	</body>
</html>
