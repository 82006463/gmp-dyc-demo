<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-编码规则值</title>
		<%@ include file="/common/common-edit.jsp"%>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/config/codeValue/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-编码规则值</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">名称<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_520 validate[required]" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">部门编号：</td>
					<td class="td_table_2">
						<input type="text" id="orgCode" name="orgCode" value="${entity.orgCode}" class="input_240" />
					</td>
					<td class="td_table_1">功能编号：</td>
					<td class="td_table_2">
						<input type="text" id="funcCode" name="funcCode" value="${entity.funcCode}" class="input_240" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">当前时间值：</td>
					<td class="td_table_2">
						<input type="text" id="timeValue" name="timeValue" value="${entity.timeValue}" class="input_240" />
					</td>
					<td class="td_table_1">当前流水号：</td>
					<td class="td_table_2">
						<input type="text" id="serialValue" name="serialValue" value="${entity.serialValue}" class="input_240" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">编码规则<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<select id="ruleId" name="ruleId" class="input_select validate[required]">
							<option value=''>--请选择--</option>
							<c:forEach items="${codeRules}" var="rule">
								<option value="${rule.id}" ${entity.ruleId==rule.id ? 'selected="selected"':''}>${rule.name}</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_table_1">当前值：</td>
					<td class="td_table_2">
						<input type="text" id="currentValue" name="currentValue" value="${entity.currentValue}" class="input_520"/>
					</td>
				</tr>
			</table>

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<%--<shiro:hasPermission name="config_codeValue_edit">--%>
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						<%--</shiro:hasPermission>--%>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
