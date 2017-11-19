<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>通知</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/compNotify" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">通知</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">通知标题：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_subject" value="${param['filter_LIKES_subject']}"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
						<shiro:hasPermission name="sec_auth_edit">
							<input type='button' onclick="addNew('${ctx}/custom/cms/compNotify/create')" class='button_70px' value='新建'/>
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
				<td align=center width=7% class="td_list_1">发送日期</td>
				<td align=center width=15% class="td_list_1">通知标题</td>
				<td align=center width=5% class="td_list_1">系统版本</td>
				<td align=center width=8% class="td_list_1">模块-模块版本</td>
				<td align=center class="td_list_1">通知内容</td>
				<td align=center width=8% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.sendTime}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.subject}</td>
					<td class="td_list_2" align=left>${item.sysVer}</td>
					<td class="td_list_2" align=left>${item.module}-v${item.moduleVer}</td>
					<td class="td_list_2" align=left>${item.content}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<c:if test="${item.status > 0}">
								<shiro:hasPermission name="cms_compNotify_delete">
									<a href="${ctx}/custom/cms/compNotify/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="cms_compNotify_edit">
									<a href="${ctx}/custom/cms/compNotify/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
								</shiro:hasPermission>
							</c:if>
							<a href="${ctx}/custom/cms/compNotify/view/${item.id}" class="btnView" title="查看">查看</a>
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
