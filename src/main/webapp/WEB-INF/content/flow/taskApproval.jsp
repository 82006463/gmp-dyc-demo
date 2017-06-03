<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>${process.name}：${process.displayName}</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript">

	</script>
</head>

<body>
	<form id="inputForm" action="${ctx }/flow/task/exec" method="post">
		<input type="hidden" id="processId" name="processId" value="${processId}"/>
		<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
		<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">${process.name}：${process.displayName}</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr align="left"><td colspan="1">
			${jsonEdit}fsafasfsafs
			</td></tr>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="button" class="button_70px" name="button" value="保存">&nbsp;&nbsp;
					<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
