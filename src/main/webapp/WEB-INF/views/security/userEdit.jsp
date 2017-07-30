<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>帐号管理</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">
			var iframewbox;
			function openOrg() {
				iframewbox = $("#selectOrgBtn").wBox({
					requestType: "iframe",
					iframeWH: {width: 800, height: 400},
					title: "选择上级部门",
					show: true,
					target: "${ctx}/security/org?lookup=1"
				});
			}

			function callbackProcess(id, name) {
				if (iframewbox) {
					document.getElementById("orgId").value = id;
					document.getElementById("orgName").value = name;
					iframewbox.close();
				}
			}
			$("#selectAll").click(function(){
				var status = $(this).attr("checked");
				if(status) {
					$("input[name='orderIndexs']").attr("checked",true);
				} else {
					$("input[name='orderIndexs']").attr("checked",false);
				}
			});
			
			function updateForm() {
				if($('#plainPassword').val().length>0 && $('#plainPassword').val() != $('#passwordConfirm').val()) {
					alert('密码和确认不一致,请重新输入');
					$('#passwordConfirm').focus();
					return false;
				}
				return Ops.submit();
			}
		</script>
	</head>
	<body>
		<form id="inputForm" action="${ctx }/security/user/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<input type="hidden" id="status" name="status" value="${entity.status}"/>
			<input type="hidden" id="password" name="password" value="${entity.password}"/>
			<input type="hidden" id="salt" name="salt" value="${entity.salt}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">用户管理</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">账号<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[30]]" id="username" name="username" value="${entity.username}" />
					</td>
					<td class="td_table_1">姓名<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="fullname" name="fullname" value="${entity.fullname}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="plainPassword" name="plainPassword" value="${entity.plainPassword}" />
					</td>
					<td class="td_table_1">确认密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="passwordConfirm" name="passwordConfirm" value="${entity.plainPassword}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">出生年月：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required]" id="birthDate" name="birthDate" value="<fmt:formatDate value="${entity.birthDate}" pattern="yyyy-MM-dd"/>"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});" readonly="readonly"/>
					</td>
					<td class="td_table_1">性别<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<select name="sex" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<option value="male" <c:if test="${entity.sex=='male'}">selected="selected"</c:if>>男</option>
							<option value="female" <c:if test="${entity.sex=='female'}">selected="selected"</c:if>>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">手机<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,custom[phone]]" id="mobile" name="mobile" value="${entity.mobile}" />
					</td>
					<td class="td_table_1">座机：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[custom[phone]]" id="phone" name="phone" value="${entity.phone}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">QQ号：</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="qq" name="qq" value="${entity.qq}" />
					</td>
					<td class="td_table_1">微信号：</td>
					<td class="td_table_2">
						<input type="text" class="input_240" id="weixin" name="weixin" value="${entity.weixin}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">邮箱<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,custom[email]]" id="email" name="email" value="${entity.email}" />
					</td>
					<td class="td_table_1">地址：</td>
					<td class="td_table_2">
						<input type="text" class="input_520" id="address" name="address" value="${entity.address}" />
					</td>
				</tr>
 				<tr>
					<td class="td_table_1">是否可用<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<select name="status" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<option value="1" <c:if test="${empty entity.status || entity.status==1}">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${entity.status==0}">selected="selected"</c:if>>否</option>
						</select>
					</td>
					<td class="td_table_1">部门<b class="requiredWarn">*</b>：</td>
					<td class="td_table_2">
						<input type="hidden" id="orgId" name="orgId" value="${entity.org.id}">
						<input type="text" id="orgName" readonly="readonly" name="orgName" class="input_240 validate[required]" value="${entity.org.name}">
						<input type='button' class='button_70px' value='选择部门' id="selectOrgBtn" onclick="openOrg()"/>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<shiro:hasPermission name="sec_user_edit">
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="return updateForm();">&nbsp;&nbsp;
						</shiro:hasPermission>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
			
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align=center width=10% class="td_list_1">
						<input type="checkbox" title="全选" id="selectAll">
					</td>
					<td align=center width=40% class="td_list_1" >角色编号</td>
					<td align=center width=50% class="td_list_1" >角色名称</td>
				</tr>

				<c:forEach items="${roles}" var="role">
					<tr>
						<td class="td_list_2" align=center>
							<label class="checkbox">
								<input type="checkbox" name="orderIndexs" value="${role.id}" ${role.selected== 1 ? 'checked=true' : '' }>
							</label>
						</td>
						<td class="td_list_2" align=left>${role.code}</td>
						<td class="td_list_2" align=left>${role.name}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
