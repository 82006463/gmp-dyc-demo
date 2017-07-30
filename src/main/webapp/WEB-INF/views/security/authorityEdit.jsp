<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>权限管理</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">
			var iframewbox;
			function openMenu() {
				iframewbox=$("#selectMenu").wBox({
					requestType: "iframe",
					iframeWH:{width:800,height:400},
					show: true,
					title:"选择菜单",
					target:"${ctx}/security/menu?lookup=1"
				});
			}
			function callbackProcess(id, name) {
				if(iframewbox) {
					document.getElementById("menuId").value=id;
					document.getElementById("menuName").value=name;
					iframewbox.close();
				}
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/security/authority/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<input type="hidden" name="pid" id="pid" value="${entity.pid}"/>
			<input type="hidden" name="status" id="status" value="${entity.status}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">权限管理</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">权限编号：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="code" name="code" value="${entity.code}" />
					</td>
					<td class="td_table_1">权限名称：</td>
					<td class="td_table_2">
						<input type="text" class="input_520 validate[required,minSize[1],maxSize[100]]" id="name" name="name" value="${entity.name}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">所属菜单：</td>
					<td class="td_table_2">
						<input type="hidden" id="menuId" name="menuId" value="${entity.menu.id}">
						<input type="text" id="menuName" name="menuName" value="${entity.menu.name}" class="input_240" readonly="readonly">
						<input type='button' class='button_70px' value='选择菜单' id="selectMenu" onclick="openMenu()"/>
					</td>
					<td class="td_table_1">资源值：</td>
					<td class="td_table_2">
						<input type="text" class="input_520 validate[required,minSize[1],maxSize[200]]" id="source" name="source" value="${entity.source}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">权限描述：</td>
					<td class="td_table_2" colspan="3">
						<textarea class="input_textarea_600 validate[maxSize[150]]" id="remark" name="remark">${entity.remark}</textarea>
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
