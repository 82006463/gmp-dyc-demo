<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>打印机</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
	<script type="text/javascript">

	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/custom/printer/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="status" name="status" value="${entity.status}"/>
		<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">打印机</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">打印机编号<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="code" name="code" class="input_240 validate[required,minSize[1],maxSize[50]]" value="${entity.code}" />
				</td>
				<td class="td_table_1">打印机名称<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" class="input_240 validate[required,minSize[1],maxSize[100]]" value="${entity.name}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">打印机IP：</td>
				<td class="td_table_2">
					<input type="text" id="ip" name="ip" value="${entity.ip}" class="input_240 validate[maxSize[50]]" />
				</td>
				<td class="td_table_1">打印机端口：</td>
				<td class="td_table_2">
					<input type="text" id="port" name="port" value="${entity.port}" class="input_240 validate[custom[number],min[1],max[65535]]" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">打印机RMI端口：</td>
				<td class="td_table_2" colspan="3">
					<input type="text" id="rmiPort" name="rmiPort" value="${entity.rmiPort}" class="input_240 validate[custom[number],min[1],max[65535]]" />
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<shiro:hasPermission name="cms_equipment_edit">
						<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">
					</shiro:hasPermission>
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
