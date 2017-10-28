<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>计量公司</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">
			var iframewbox;
			function standardItemList() {
				iframewbox = $("#editStandardItem").wBox({
					requestType: "iframe",
					iframeWH:{width:1200,height:600},
					show: true,
					title:"编辑标准项",
					target:"${ctx}/custom/cms/measureComp/standardItem?measureCompId=${entity.id}"
				});
			}

			function callbackProcess(id, name) {
				if(iframewbox) {
					iframewbox.close();
				}
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/custom/cms/measureComp/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>

			<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
			<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
			<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">计量公司</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">企业编号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="code" name="code" value="${entity.code}" />
					</td>
					<td class="td_table_1">企业名称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_520 validate[required,minSize[1],maxSize[100]]" id="name" name="name" value="${entity.name}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">信用代码<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="creditCode" name="creditCode" value="${entity.creditCode}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
					<td class="td_table_1">企业简称<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="enterpriseShort" name="enterpriseShort" value="${entity.enterpriseShort}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">地址<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="addr" name="addr" value="${entity.addr}" class="input_240 validate[required,minSize[1],maxSize[100]]" />
					</td>
					<td class="td_table_1">网址<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="url" name="url" value="${entity.url}" class="input_240 validate[required,minSize[1],maxSize[50]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">认可有效期<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="expireDate" name="expireDate" value="<fmt:formatDate value="${entity.expireDate}" pattern="yyyy-MM-dd"/>" class="input_240 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" readonly="readonly"/>
					</td>
					<td class="td_table_1">建标数量<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" id="buildCount" name="buildCount" value="${entity.buildCount}" class="input_240 validate[required,custom[number],min[1]]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">审计报告<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="auditReport" name="auditReport" value="${entity.auditReport}" class="input_240 validate[required,minSize[1],maxSize[200]]" />
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="cms_measureComp_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<c:if test="${!empty entity.id}">
							<shiro:hasPermission name="cms_measureComp_edit">
								<input type="button" class="button_70px" id="editStandardItem" value="编辑标准项" onclick="standardItemList();">&nbsp;&nbsp;
							</shiro:hasPermission>
						</c:if>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>
