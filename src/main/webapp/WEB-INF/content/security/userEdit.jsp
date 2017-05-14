<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>帐号管理</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/wbox/wbox/wbox.css" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/wbox/wbox.js"></script>
		<script>
		var iframewbox;
	function openOrg() {
 		iframewbox=$("#selectOrgBtn").wBox({
			   	requestType: "iframe",
			   	iframeWH:{width:800,height:400},
			   	title:"选择上级部门",
			   	show: true,
				target:"${ctx}/security/org?lookup=1"
			   });
	}
	
	function callbackProcess(id, name) {
		if(iframewbox) {
			document.getElementById("parentOrgId").value=id;
			document.getElementById("parentOrgName").value=name;
			iframewbox.close();
		}
	}
</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/security/user/update" method="post">
			<input type="hidden" name="id" id="id" value="${id }"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">用户管理</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">账号：</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="username" name="username" value="${user.username }" />
					</td>
					<td class="td_table_1">姓名：</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="fullname" name="fullname" value="${user.fullname }" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="plainPassword" name="plainPassword" value="${user.plainPassword }" />
					</td>
					<td class="td_table_1">确认密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="passwordConfirm" name="passwordConfirm" value="${user.plainPassword }" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">邮箱：</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="email" name="email" value="${user.email }" />
					</td>
					<td class="td_table_1">性别：</td>
					<td class="td_table_2">
						<frame:dict name="sex" type="radio" typeCode="sex" value="${user.sex == null ? '1' : user.sex }" cssClass="input_radio"/>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">是否可用：</td>
					<td class="td_table_2" colspan="3">
						<frame:dict name="enabled" type="radio" typeCode="yesNo" value="${user.enabled == null ? '1' : user.enabled }" cssClass="input_radio"/>
					</td>
				</tr>
 				<tr>
					<td class="td_table_1">部门：</td>
					<td class="td_table_2" colspan="3">
						<input type="hidden" id="parentOrgId" name="parentOrgId" value="${user.org.id }">
						<input type="text" id="parentOrgName" readonly="readonly" name="parentOrgName" class="input_520" value="${user.org.name }">
						<input type='button' class='button_70px' value='选择部门' id="selectOrgBtn" onclick="openOrg()"/>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="sec_user_edit">
							<input type="submit" class="button_70px" name="submit" value="提交">&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
			
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align=center width=10% class="td_list_1" nowrap>
						<input type="checkbox" title="全选" id="selectAll">
					</td>
					<td align=center width=45% class="td_list_1" nowrap>
						<a href="javascript:sort('name','asc')">角色名称</a>
					</td>
				</tr>

				<c:forEach items="${roles}" var="role">
					<tr>
						<td class="td_list_2" align=center>
							<label class="checkbox">
								<input type="checkbox" name="orderIndexs" value="${role.id}" ${role.selected== 1 ? 'checked=true' : '' }>
							</label>
						</td>
						<td class="td_list_2" align=left>${role.name}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		$("#selectAll").click(function(){
			var status = $(this).attr("checked");
			if(status) {
				$("input[name='orderIndexs']").attr("checked",true);
			} else {
				$("input[name='orderIndexs']").attr("checked",false);
			}
		});
	</script>
</html>
