<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>文件复审</title>
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
					<td class="td_table_1">文件名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" name="fileName" value="${entity.fileName}" />
					</td>
					<td class="td_table_1">文件编码<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[30]]" name="fileCode" value="${entity.fileCode}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">复审结论<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[10]]" name="reapproveResult" value="${entity.reapproveResult}" />
					</td>
					<td class="td_table_1">复审人<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" name="reapprovePerson" value="${entity.reapprovePerson}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">复审日期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" name="reapproveDate" value="${entity.reapproveDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly='readonly'/>
					</td>
					<td class="td_table_1">是否需要修订<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<select name="isEdit" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<option value="是" <c:if test="${entity.isEdit=='是'}">selected="selected"</c:if>>是</option>
							<option value="否" <c:if test="${entity.isEdit=='否'}">selected="selected"</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">待修订日期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_240 validate[required]" name="nextEditDate" value="${entity.nextEditDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly='readonly'/>
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
