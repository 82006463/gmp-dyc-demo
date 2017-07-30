<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-编码规则</title>
		<%@ include file="/common/common-edit.jsp"%>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/config/codeRule/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-编码规则</td>
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

				<%--<tr>
					<td class="td_table_1">部门编号：</td>
					<td class="td_table_2">
						<input type="text" id="orgCode" name="orgCode" value="${entity.orgCode}" class="input_240" />
					</td>
					<td class="td_table_1">部门编号分隔符：</td>
					<td class="td_table_2">
						<input type="text" id="orgSeparator" name="orgSeparator" value="${entity.orgSeparator}" class="input_240" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">功能编号：</td>
					<td class="td_table_2">
						<input type="text" id="funcCode" name="funcCode" value="${entity.funcCode}" class="input_240" />
					</td>
					<td class="td_table_1">功能编号分隔符：</td>
					<td class="td_table_2">
						<input type="text" id="funcSeparator" name="funcSeparator" value="${entity.funcSeparator}" class="input_240" />
					</td>
				</tr>--%>

				<tr>
					<td class="td_table_1">时间模式<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<select name="timePattern" class="input_select validate[required]">
							<option value="">------请选择------</option>
							<option value="yyyy" ${entity.timePattern=='yyyy' ? 'selected="selected"':''}>四位年</option>
							<option value="yyyyMM" ${entity.timePattern=='yyyyMM' ? 'selected="selected"':''}>四位年两位月</option>
							<option value="yyyyMMdd" ${entity.timePattern=='yyyyMMdd' ? 'selected="selected"':''}>四位年两位月两位日</option>
							<option value="yy" ${entity.timePattern=='yy' ? 'selected="selected"':''}>两位年</option>
							<option value="yyMM" ${entity.timePattern=='yyMM' ? 'selected="selected"':''}>两位年两位月</option>
							<option value="yyMMdd" ${entity.timePattern=='yyMMdd' ? 'selected="selected"':''}>两位年两位月两位日</option>
						</select>
					</td>
					<td class="td_table_1">时间模式分隔符：</td>
					<td class="td_table_2">
						<input type="text" id="timeSeparator" name="timeSeparator" value="${entity.timeSeparator}" class="input_240 validate[required]"/>
					</td>
				</tr>

				<tr>
					<td class="td_table_1">流水号长度<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="serialLength" name="serialLength" value="${entity.serialLength}" class="input_240 validate[required,custom[integer],min[1]]"/>
					</td>
					<td class="td_table_1">规则字符串<b class='requiredWarn'>*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="ruleValue" name="ruleValue" value="${entity.ruleValue}" class="input_520 validate[required]"/>
					</td>
				</tr>
			</table>

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<%--<shiro:hasPermission name="config_codeRule_edit">--%>
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						<%--</shiro:hasPermission>--%>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
