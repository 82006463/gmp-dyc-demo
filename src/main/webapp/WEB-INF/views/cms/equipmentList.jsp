<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>器具</title>
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
	<form id="mainForm" action="${ctx}/custom/cms/equipment" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">器具</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">器具编号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_EQS_code" value="${param['filter_EQS_code']}"/>
				</td>
				<td class="td_table_1">器具名称：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="filter_LIKES_name" value="${param['filter_LIKES_name']}"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">校准方式：</td>
				<td class="td_table_2">
					<select name="filter_EQI_calibrationMode" class="input_select">
						<option value="">-请选择-</option>
						<option value="1" ${param['filter_EQI_calibrationMode']==1 ? 'selected="selected"':''}>内校</option>
						<option value="2" ${param['filter_EQI_calibrationMode']==2 ? 'selected="selected"':''}>外校</option>
					</select>
				</td>
				<td class="td_table_1">器具状态：</td>
				<td class="td_table_2">
					<select name="filter_EQI_status" class="input_select">
						<option value="">-请选择-</option>
						<option value="-1" ${param['filter_EQI_status']==-1 ? 'selected="selected"':''}>失效</option>
						<option value="1" ${param['filter_EQI_status']==1 ? 'selected="selected"':''}>暂存中</option>
						<option value="2" ${param['filter_EQI_status']==2 ? 'selected="selected"':''}>拒绝中</option>
						<option value="3" ${param['filter_EQI_status']==3 ? 'selected="selected"':''}>复核中</option>
						<option value="4" ${param['filter_EQI_status']==4 ? 'selected="selected"':''}>正常</option>
						<option value="5" ${param['filter_EQI_status']==5 ? 'selected="selected"':''}>延期</option>
					</select>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<c:if test="${empty lookup}">
						<shiro:hasPermission name="cms_equipment_edit">
							<input type='button' onclick="addNew('${ctx}/custom/cms/equipment/create')" class='button_70px' value='新建'/>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${!empty lookup}">
						<input type='button' onclick="javascript:bringback('','')" class='button_70px' value='重置'/>
					</c:if>
					<input type='submit' class='button_70px' value='查询'/>
					<input type="button" class='button_70px' value="导出Excel" onclick="exportFile();"/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=15% class="td_list_1">器具编号</td>
				<td align=center width=25% class="td_list_1">器具名称</td>
				<td align=center width=15% class="td_list_1">使用范围下限-上限</td>
				<td align=center width=5% class="td_list_1">校准方式</td>
				<td align=center width=10% class="td_list_1">上次校准时间</td>
				<td align=center width=10% class="td_list_1">待校准时间</td>
				<td align=center width=5% class="td_list_1">状态</td>
				<td align=center width=10% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="item">
				<tr>
					<td class="td_list_2" align=left>${item.code}</td>
					<td class="td_list_2" align=left>${item.name}</td>
					<td class="td_list_2" align=left>【${item.measureRangeMin}】至【${item.measureRangeMax}】</td>
					<td class="td_list_2" align=left>${item.calibrationMode == 1 ? '内校':item.calibrationMode == 2 ? '外校':''}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty item.lastActualDate}">N.A.</c:if>
						<c:if test="${!empty item.lastActualDate}"><fmt:formatDate value="${item.lastActualDate}" pattern="yyyy-MM-dd"/></c:if>
					</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.status <= 0 ? '失效':item.status==1 ? '暂存中':item.status==2 ? '拒绝中':item.status==3 ? '复核中':'正常'}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty lookup}">
							<c:if test="${item.status > 0}">
								<shiro:hasPermission name="cms_equipment_delete">
									<a href="${ctx}/custom/cms/equipment/delete/${item.id}" class="btnDel" title="删除" onclick="return confirmDel();">删除</a>
								</shiro:hasPermission>
								<c:if test="${(item.status==1 || item.status==2 || item.status>=4)}">
									<shiro:hasPermission name="cms_equipment_edit">
										<a href="${ctx}/custom/cms/equipment/update/${item.id}" class="btnEdit" title="编辑">编辑</a>
									</shiro:hasPermission>
								</c:if>
								<c:if test="${(item.status==3) && userId!=item.createrId}">
									<shiro:hasPermission name="cms_equipment_review">
										<a href="${ctx}/custom/cms/equipment/update/${item.id}" class="btnEdit" title="复核">复核</a>
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
