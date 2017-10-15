<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>通知</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/custom/cms/compNotify/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">通知</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">接收人<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<select id="notifyType" name="notifyType" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<option value="1" <c:if test="${entity.notifyType==1}">selected="selected"</c:if>>所有客户</option>
							<option value="2" <c:if test="${entity.notifyType==2}">selected="selected"</c:if>>所有计量服务商</option>
							<option value="3" <c:if test="${entity.notifyType==3}">selected="selected"</c:if>>所有系统用户</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">通知内容<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<textarea class="input_textarea_600 validate[maxSize[150]]" id="content" name="content">${entity.content}</textarea>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="sec_auth_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
