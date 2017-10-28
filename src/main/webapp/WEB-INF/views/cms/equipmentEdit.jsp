<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>器具</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/custom/cms/equipment/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>

			<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
			<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
			<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">器具</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">器具编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="code" name="code" value="${entity.code}" />
					</td>
					<td class="td_table_1">器具名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_520 validate[required,minSize[1],maxSize[100]]" id="name" name="name" value="${entity.name}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">型号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="model" name="model" value="${entity.model}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">出厂编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="factoryCode" name="factoryCode" value="${entity.factoryCode}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">所在房间<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="room" name="room" value="${entity.room}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">所属设备<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="equipment" name="equipment" value="${entity.equipment}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">功能<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="func" name="func" value="${entity.func}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">精度<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="precision" name="precision" value="${entity.precision}" class="input_240 validate[required,minSize[1],maxSize[20]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">校准<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="calibration" name="calibration" value="${entity.calibration}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">校准名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="calibrationName" name="calibrationName" value="${entity.calibrationName}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">校准方式<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="calibrationMode" name="calibrationMode" value="${entity.calibrationMode}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">校准周期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="calibrationCycle" name="calibrationCycle" value="${entity.calibrationCycle}" class="input_240 validate[required,custom[number]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">测量范围上限<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="measureRangeMin" name="measureRangeMin" value="${entity.measureRangeMin}" class="input_240 validate[required,custom[number]]" />
					</td>
					<td class="td_table_1">测量范围下限<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="measureRangeMax" name="measureRangeMax" value="${entity.measureRangeMax}" class="input_240 validate[required,custom[number]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">上次校准时间<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="lastCalibrationDate" name="lastCalibrationDate" value="<fmt:formatDate value="${entity.lastCalibrationDate}" pattern="yyyy-MM-dd"/>" class="input_240 validate[required,minSize[1],maxSize[50]]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
					</td>
					<td class="td_table_1">待校准时间<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="expectCalibrationDate" name="expectCalibrationDate" value="<fmt:formatDate value="${entity.expectCalibrationDate}" pattern="yyyy-MM-dd"/>" class="input_240 validate[required,minSize[1],maxSize[20]]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="cms_equipment_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
