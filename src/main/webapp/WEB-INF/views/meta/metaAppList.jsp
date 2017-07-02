<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>${metaTag.name}</title>
	<%@ include file="/common/common-list.jsp"%>
</head>

<body>
	<form id="mainForm" action="${ctx}/meta/app/${metaType}/${cmcode}/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">${metaTag.name}</td>
			</tr>
		</table>
		${jsonSearch}
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${!empty metaTag.list_create_url}">
						<input type='button' onclick="addNew('${ctx}/meta/app/${metaType}/${cmcode}/create')" class='button_70px' value='新建'/>
					</c:if>
					<c:if test="${fn:contains(metaType,'flow')}">
						<input type="button" onclick="addNew('${ctx}/flow/task/approval?processName=${cmcode}')" class="button_70px" value="申请">
					</c:if>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			${jsonList}
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages}" totalRecords="${page.totalCount}" lookup="${lookup}"/>
		</table>
	</form>
</body>
</html>
