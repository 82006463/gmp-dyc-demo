<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-图表</title>
		<%@ include file="/common/common-list.jsp"%>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/config/meta/${type}/list" method="get">
		<input type="hidden" name="type" value="${type}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">配置管理-图表</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">编号：</td>
				<td class="td_table_2">
					<input type="text" name="Eq_String_code" value="${param['Eq_String_code']}" class="input_240"/>
				</td>
				<td class="td_table_1">名称：</td>
				<td class="td_table_2">
					<input type="text" name="Like_String_name" value="${param['Like_String_name']}" class="input_240"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
						<%--<shiro:hasPermission name="config_meta_${type}_edit">--%>
							<input type='button' onclick="addNew('${ctx}/config/meta/${type}/create?item=edit')" class='button_70px' value='新建'/>
						<%--</shiro:hasPermission>--%>
					</c:if>
					<c:if test="${!empty lookup}">
						<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:if>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center class="td_list_1">编号</td>
				<td align=center class="td_list_1">名称</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.title}</td>

					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<shiro:hasPermission name="config_meta_${type}_delete">
								<a href="${ctx}/config/meta/${type}/delete/${item._id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
							</shiro:hasPermission>
							<%--<shiro:hasPermission name="config_meta_${type}_edit">--%>
								<%--<a href="${ctx}/config/meta/${type}/update/${item._id}?item=edit" class="btnEdit" title="编辑">编辑</a>--%>
								<a href="${ctx}/config/meta/${type}/update/${item._id}?item=search" class="btnEdit" title="编辑搜索"></a>
							<%--</shiro:hasPermission>--%>
							<shiro:hasPermission name="config_meta_${type}_view">
								<a href="${ctx}/config/meta/${type}/view/${item._id}" class="btnView" title="查看">查看</a>
							</shiro:hasPermission>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages}" totalRecords="${page.totalCount}" lookup="${lookup} "/>
		</table>
	</form>
	</body>
</html>
