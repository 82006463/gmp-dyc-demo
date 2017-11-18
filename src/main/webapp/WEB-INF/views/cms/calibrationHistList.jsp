<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>校准历史</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
            function exportFile() {
                $('#downloadBtn').prop('href', '${ctx}/custom/cms/calibrationHist/exportFile?'+$('#mainForm').serialize());
                $('#downloadBtn').trigger('click');
                $('#downloadBtn').prop('href', '#');
                return false;
            }
		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/calibrationHist" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">校准历史</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">实际校准时间：</td>
				<td class="td_table_2">
					<input type="text" name="filter_GED_actualDate" value="${param['filter_GED_actualDate']}" class="input_240" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>~
					<input type="text" name="filter_LED_actualDate" value="${param['filter_LED_actualDate']}" class="input_240" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
				</td>
				<td class="td_table_1">任务编号：</td>
				<td class="td_table_2">
					<input type="text" name="filter_EQS_taskCode" value="${param['filter_EQS_taskCode']}" class="input_240" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">器具编号：</td>
				<td class="td_table_2">
					<input type="text" name="filter_EQS_equipmentCode" value="${param['filter_EQS_equipmentCode']}" class="input_240" />
				</td>
				<td class="td_table_1">校准结果：</td>
				<td class="td_table_2">
					<select name="filter_EQS_calibrationResult" class="input_select">
						<option value="">-请选择-</option>
						<option value="1" ${param['filter_EQS_calibrationResult']==1 ? 'selected="selected"':''}>合格</option>
						<option value="0" ${param['filter_EQS_calibrationResult']==-1 ? 'selected="selected"':''}>不合格</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">校准方式：</td>
				<td class="td_table_2">
					<select name="filter_EQI_calibrationMode" class="input_select">
						<option value="">-请选择-</option>
						<option value="1" ${param['filter_EQI_calibrationMode']==1 ? 'selected="selected"':''}>内校</option>
						<option value="2" ${param['filter_EQI_calibrationMode']==2 ? 'selected="selected"':''}>外校</option>
						<option value="3" ${param['filter_EQI_calibrationMode']==3 ? 'selected="selected"':''}>临校</option>
					</select>
				</td>
				<td class="td_table_1">校准状态：</td>
				<td class="td_table_2">
					<select name="filter_EQI_calibrationStatus" class="input_select">
						<option value="">-请选择-</option>
						<option value="1" ${param['filter_EQI_calibrationStatus']==1 ? 'selected="selected"':''}>正常</option>
						<option value="0" ${param['filter_EQI_calibrationStatus']==-1 ? 'selected="selected"':''}>延期</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_table_2" align="center" colspan="4">
					<input type='submit' id="searchBtn" class='button_70px' value='查询'/>
					<a id="downloadBtn" class='button_70px' href="#" target="_blank" onclick="return exportFile();">导出Excel</a>
				</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=15% class="td_list_1">任务编号</td>
				<td align=center width=20% class="td_list_1">器具编号/器具名称</td>
				<td align=center width=8% class="td_list_1">所在房间</td>
				<td align=center width=6% class="td_list_1">待校准时间</td>
				<td align=center width=5% class="td_list_1">校准方式</td>
				<td align=center width=10% class="td_list_1">记录/证书编号</td>
				<td align=center width=5% class="td_list_1">校准结果</td>
				<td align=center width=7% class="td_list_1">实际校准时间</td>
				<td align=center width=6% class="td_list_1">校准状态</td>
				<td align=center width=10% class="td_list_1">备注</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${item.taskCode}</td>
					<td class="td_list_2" align=left>${item.equipmentCode}/${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left>
						<c:if test="${empty item.expectDate}">N.A.</c:if>
						<c:if test="${!empty item.expectDate}"><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></c:if>
					</td>
					<td class="td_list_2" align=left>${item.calibrationMode==1 ? '内校':item.calibrationMode==2 ? '外校':item.calibrationMode==3 ? '临校':'N.A.'}</td>
					<td class="td_list_2" align=left>${item.certCode}</td>
					<td class="td_list_2" align=left>${item.calibrationResult==1 ? '合格':item.calibrationResult<=0 ? '不合格':'N.A.'}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.calibrationStatus==1 ? '正常':item.calibrationStatus<=0 ? '延期':'N.A.'}</td>
					<td class="td_list_2" align=left>${empty item.remark ? 'N.A.':item.remark}</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
