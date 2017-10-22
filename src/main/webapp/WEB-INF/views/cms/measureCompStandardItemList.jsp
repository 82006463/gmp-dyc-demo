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
	<form id="mainForm" action="${ctx}/custom/cms/measureComp/standardItem" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<input type="hidden" name="measureCompId" id="measureCompId" value="${measureCompId}"/>
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
					<input type='submit' class='button_70px' value='查询'/>
					<input type='button' onclick="addNew('${ctx}/custom/cms/measureComp/add')" class='button_70px' value='添加'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=5% class="td_list_1">选择</td>
				<td align=center width=15% class="td_list_1">标准项编号</td>
				<td align=center width=30% class="td_list_1">标准项名称</td>
				<td align=center width=30% class="td_list_1">测量范围上限-下限</td>
				<td align=center width=10% class="td_list_1">不确定度</td>
				<td align=center width=10% class="td_list_1">校准参量</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>
						<c:set var="checked" value="false"></c:set>
						<c:forEach items="${standardItems}" var="standardItem">
							<c:if test="${item.id==standardItem.measureCompId}"><c:set var="checked" value="true"></c:set></c:if>
						</c:forEach>
						<input type="checkbox" id="standardItemIds" name="standardItemIds" value="${item.id}" ${checked ? 'checked="checked"':''}>
					</td>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>【${item.measureRangeMin}】至【${item.measureRangeMax}】</td>
					<td class="td_list_2" align=left>${item.uncertainty}</td>
					<td class="td_list_2" align=left>${item.param}</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
