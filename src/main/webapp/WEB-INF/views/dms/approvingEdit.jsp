<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>文件审批</title>
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
					<td class="td_table_1">文件编码<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="fileCode" value="${entity.fileCode}" class="input_240 validate[required,minSize[1],maxSize[30]]" />
					</td>
					<td class="td_table_1">文件名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="fileName" value="${entity.fileName}" class="input_240 validate[required]" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">文件版本<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="fileVer" value="${entity.fileVer}" class="input_240 validate[required,minSize[1],maxSize[10]]" readonly="readonly"/>
					</td>
					<td class="td_table_1">批准日期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="approveDate" value="${entity.approveDate}" class="input_240 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly='readonly'/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">复审周期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="reviewCycle" value="${entity.reviewCycle}" class="input_240 validate[required]" readonly="readonly"/>年
					</td>
					<td class="td_table_1">生效日期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" name="effectiveDate" value="${entity.effectiveDate}" class="input_240 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">上级文件编码：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" name="parentFileCode" value="${entity.parentFileCode}" class="input_240" />
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
