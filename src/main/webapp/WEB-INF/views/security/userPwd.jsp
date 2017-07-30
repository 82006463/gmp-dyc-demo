<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>帐号管理</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">
			function updateForm() {
				if($.trim($('#oldPlainPassword').val()).length==0) {
					alert('旧密码不能为空');
					$('#oldPlainPassword').focus();
				} else if($.trim($('#plainPassword').val()).length==0) {
					alert('新密码不能为空');
					$('#plainPassword').focus();
				} else if($('#plainPassword').val() == $('#oldPlainPassword').val()) {
					alert('新旧密码不能一样');
					$('#plainPassword').focus();
				} else if($.trim($('#passwordConfirm').val()).length==0) {
					alert('确认密码不能为空');
					$('#passwordConfirm').focus();
				} else 	if($('#plainPassword').val().length>0 && $('#plainPassword').val() != $('#passwordConfirm').val()) {
					alert('密码和确认密码不一致,请重新输入');
					$('#passwordConfirm').focus();
				} else {
					$.getJSON('${ctx}/security/user/checkpwd', {userId:'${entity.id}', oldPlainPassword:$('#oldPlainPassword').val(), plainPassword:$('#plainPassword').val()},function (data) {
					    if(data.result == 1) {
					        window.location.href = '${ctx}/logout';
							//$('#inputForm').submit();
						} else {
							alert(data.msg);
						}
					});
				}
			}
		</script>
	</head>
	<body>
		<form id="inputForm" action="${ctx}/security/user/uppwd" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">用户管理</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">旧密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="oldPlainPassword" name="oldPlainPassword" value="" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">新密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="plainPassword" name="plainPassword" value="${entity.plainPassword}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">确认密码：</td>
					<td class="td_table_2">
						<input type="password" class="input_240" id="passwordConfirm" name="passwordConfirm" value="${entity.plainPassword}" />
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" id="submit" name="submit" value="提交" onclick="updateForm();">&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
