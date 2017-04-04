<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript">
		$(function(){
			$('#operator').AutoComplete({
				'data': "${ctx}/wfc/common/select/auto",
				'ajaxDataType': 'json',
				'ajaxParams': {table:'wfs_user'},
				'width': 'auto',
				'maxHeight': 300,
				'maxItems': 100,
				'async': true
			}).AutoComplete('show');
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/dyc/report/update" method="post">
		<input type="hidden" name="id" value="${entity.id}"/>
		<input type="hidden" name="processType" value="${entity.processType}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">编号：</td>
				<td class="td_table_2">
					<input type="text" name="processNo" value="${entity.processNo}" class="input_240 validate[required]" />
				</td>
				<td class="td_table_1">名称：</td>
				<td class="td_table_2">
					<input type="text" name="processName" value="${entity.processName}" class="input_240"/>
				</td>
			</tr>
			${extAttr}
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">
					&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
