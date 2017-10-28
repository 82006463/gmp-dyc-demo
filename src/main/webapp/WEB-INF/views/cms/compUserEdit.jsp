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
		<form id="inputForm" action="${ctx}/custom/cms/compUser/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>

			<input type="hidden" id="op" name="op" value="${op}"/>
			<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
			<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
						<select id="measureCompId" name="measureCompId" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${measureComps}" var="item">
								<option value="${item.id}" <c:if test="${entity.measureCompId==item.id}">selected="selected"</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_table_1">药企<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<select id="drugCompId" name="drugCompId" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${drugComps}" var="item">
								<option value="${item.id}" <c:if test="${entity.drugCompId==item.id}">selected="selected"</c:if>>${item.name}</option>
							</c:forEach>
						</select>
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
						<c:if test="${(empty entity.status || entity.status > 0) && op != 2}">
							<shiro:hasPermission name="cms_compUser_edit">
								<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
							</shiro:hasPermission>
						</c:if>
						<c:if test="${entity.status == 1 && op == 2}">
							<shiro:hasPermission name="cms_compUser_review">
								<input type="submit" class="button_70px" name="submit" value="复核" onclick="$('#status').val(2); return Ops.submit();">&nbsp;&nbsp;
							</shiro:hasPermission>
						</c:if>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
