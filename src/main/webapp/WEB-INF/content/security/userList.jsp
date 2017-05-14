<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>账号管理</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/security/user" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">账号管理</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">账号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_username" value="${param['filter_LIKES_username']}"/>
				</td>
				<td class="td_table_1">姓名：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_fullname" value="${param['filter_LIKES_fullname']}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
					<shiro:hasPermission name="sec_user_edit">
						<input type='button' onclick="addNew('${ctx}/security/user/create')" class='button_70px' value='新建'/>
					</shiro:hasPermission>
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
				<td align=center width=20% class="td_list_1">账号</td>
				<td align=center width=20% class="td_list_1">姓名</td>
				<td align=center width=30% class="td_list_1">是否可用</td>
				<td align=center width=20% class="td_list_1">部门</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="user">
				<tr>
					<td class="td_list_2" align=left>${user.username}</td>
					<td class="td_list_2" align=left>${user.fullname}</td>
					<td class="td_list_2" align=left>${user.enabled=='1' ? '是':user.enabled=='0' ? '否':''}</td>
					<td class="td_list_2" align=left>${user.org.name}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<shiro:hasPermission name="sec_user_delete">
								<a href="${ctx}/security/user/delete/${user.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sec_user_edit">
								<a href="${ctx}/security/user/update/${user.id}" class="btnEdit" title="编辑">编辑</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sec_user_view">
								<a href="${ctx}/security/user/view/${user.id}" class="btnView" title="查看">查看</a>
							</shiro:hasPermission>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup }"/>
		</table>
	</form>
</body>
</html>
