<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>标准项</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
		<script type="text/javascript">

		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/custom/cms/standardItem/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>

			<input type="hidden" id="op" name="op" value="${op}"/>
			<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
			<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">标准项</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">标准项编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">标准项名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_520 validate[required,minSize[1],maxSize[100]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">测量范围上限<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="measureRangeMin" name="measureRangeMin" value="${entity.measureRangeMin}" class="input_240 validate[required,custom[number]]" />
					</td>
					<td class="td_table_1">测量范围下限<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="measureRangeMax" name="measureRangeMax" value="${entity.measureRangeMax}" class="input_240 validate[required,custom[number]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">不确定度<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="uncertainty" name="uncertainty" value="${entity.uncertainty}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">校准参量<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="param" name="param" value="${entity.param}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="cms_standardItem_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<c:if test="${entity.status == 1}">
							<shiro:hasPermission name="cms_standardItem_review">
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
