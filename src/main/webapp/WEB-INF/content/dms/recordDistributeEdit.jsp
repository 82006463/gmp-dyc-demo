<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>记录分发</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">

		</script>
	</head>
	<body>
		<form id="inputForm" action="${ctx}/flow/task/approval" method="post">
			<input type="hidden" id="processId" name="processId" value="${process.id}"/>
			<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
			<input type="hidden" id="taskId" name="taskId" value="${task.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">${process.displayName}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">申请编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240 validate[required]" name="approveCode" value="${entity.approveCode}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">记录名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" name="recordName" value="${entity.recordName}" />
					</td>
					<td class="td_table_1">记录编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" name="recordCode" value="${entity.recordCode}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">申请份数：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240" name="approveCount" value="${entity.approveCount}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">理由：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240" name="approveReason" value="${entity.approveReason}" />
					</td>
				</tr>
			</table>

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="submit" value="保存">&nbsp;&nbsp;
						<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						<c:if test="${!empty task && task.displayName != '申请人'}">
							<input type="submit" class="button_70px" name="submit" value="拒绝">&nbsp;&nbsp;
						</c:if>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
