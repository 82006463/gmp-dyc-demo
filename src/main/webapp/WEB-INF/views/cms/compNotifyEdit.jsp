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

			<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
			<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">通知</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">接收角色<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<select id="roleId" name="roleId" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${roles}" var="role">
								<option value="${role.id}" ${entity.roleId==role.id ? 'selected="selected"':''}>${role.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>

				<tr>
					<td class="td_table_1">系统版本<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="sysVer" name="sysVer" value="${entity.sysVer}" class="input_240 validate[required,minSize[1],maxSize[20]]" />
					</td>
					<td class="td_table_1">发送时间<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="sendTime" name="sendTime" value="<fmt:formatDate value="${entity.sendTime}" pattern="yyyy-MM-dd"/>" class="input_240 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">模块<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="module" name="module" value="${entity.module}" class="input_240 validate[required,minSize[1],maxSize[20]]" />
					</td>
					<td class="td_table_1">模块版本<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="moduleVer" name="moduleVer" value="${entity.moduleVer}" class="input_240 validate[required,minSize[1],maxSize[20]]" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">通知标题<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="subject" name="subject" value="${entity.subject}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">通知内容<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<textarea id="content" name="content" class="input_textarea_600 validate[required,minSize[1],maxSize[490]]">${entity.content}</textarea>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="cms_compNotify_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
