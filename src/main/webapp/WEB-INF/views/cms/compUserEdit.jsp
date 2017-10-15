<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>用户</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/custom/cms/customerComp/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">用户</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="code" name="code" value="${entity.code}" />
					</td>
					<td class="td_table_1">姓名<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_520 validate[required,minSize[1],maxSize[100]]" id="name" name="name" value="${entity.name}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">计量公司<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="measureCompId" name="measureCompId" value="${entity.measureCompId}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">客户公司<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="customerCompId" name="customerCompId" value="${entity.customerCompId}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">岗位<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="post" name="post" value="${entity.post}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">邮箱<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="email" name="email" value="${entity.email}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
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
