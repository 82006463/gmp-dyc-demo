<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理</title>
	<%@ include file="/common/common-list.jsp"%>
</head>

<body>
	<form id="mainForm" action="${ctx}/dyc/report/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<input type="hidden" class="input_240" name="processType" value="${param['processType']}"/>

		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					${param['processType']=='dev' ? '偏差':param['processType']=='cc' ? '变更':param['processType']=='capa' ? 'CAPA':param['processType']=='oos'? 'OOS':''}编号：
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_processNo" value="${param['filter_LIKES_processNo']}"/>
				</td>
				<td class="td_table_1">关键字：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_processName" value="${param['filter_LIKES_processName']}"/>
				</td>
			</tr>
			<%--<tr>
				<td class="td_table_1">部门：</td>
				<td class="td_table_2">
					<select name="filter_EQL_orgId" class="input_select">
						<option value="" selected="">------请选择------</option>
						<c:forEach items="${orgList}" var="item">
							<option value="${item.id}" ${param['filter_EQL_orgId']==item.id ? 'selected="selected"':''}>${item.name}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_table_1">级别：</td>
				<td class="td_table_2">
					<frame:dict name="filter_EQS_level" type="select" typeCode="devLevel" value="${param['filter_EQS_level']}" cssClass="input_select"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">发现时间起：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_GED_startTime" value="${param['filter_GED_startTime']}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
				</td>
				<td class="td_table_1">发现时间止：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LED_startTime" value="${param['filter_LED_startTime']}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">物料名称：</td>
				<td class="td_table_2" colspan="3">
					<input type="text" class="input_240" name="filter_LIKES_backup1" value="${param['filter_LIKES_backup1']}"/>
				</td>
			</tr>--%>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<input type='button' onclick="addNew('${ctx}/dyc/report/create?processType=${param['processType']}')" class='button_70px' value='新建'/>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" class="td_list_1">
					${param['processType']=='dev' ? '偏差':param['processType']=='cc' ? '变更':param['processType']=='capa' ? 'CAPA':param['processType']=='oos'? 'OOS':''}编号
				</td>
				<td align="center" class="td_list_1">
					${param['processType']=='dev' ? '偏差':param['processType']=='cc' ? '变更':param['processType']=='capa' ? 'CAPA':param['processType']=='oos'? 'OOS':''}名称
				</td>
				<td align="center" class="td_list_1">部门</td>
				<td align="center" class="td_list_1">级别</td>
				<td align="center" class="td_list_1">发现时间</td>
				<td align="center" class="td_list_1">关闭时间</td>
				<td align="center" width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.processNo}</td>
					<td class="td_list_2" align=left>${item.processName}</td>
					<td class="td_list_2" align=left>${item.orgName}</td>
					<td class="td_list_2" align=left>${item.level}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.occurTime}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.closeTime}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>
						<c:if test="${empty item.closeTime}">
							<a href="${ctx}/dyc/report/delete/${item.id }" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
							<a href="${ctx}/dyc/report/update/${item.id }" class="btnEdit" title="编辑">编辑</a>
						</c:if>
						<a href="${ctx}/dyc/report/view/${item.id }" class="btnView" title="查看">查看</a>
					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
</body>
</html>
