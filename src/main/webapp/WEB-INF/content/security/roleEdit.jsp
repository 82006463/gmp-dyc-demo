<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>角色管理</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/wbox/wbox/wbox.css" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/wbox/wbox.js"></script> 
	</head>

	<body>
		<form id="inputForm" action="${ctx }/security/role/update" method="post">
			<input type="hidden" name="id" id="id" value="${id }"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">角色管理</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">角色编号：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[50]]" id="code" name="code" value="${role.code}" />
					</td>
					<td class="td_table_1">角色名称：</td>
					<td class="td_table_2">
						<input type="text" class="input_240 validate[required,minSize[1],maxSize[100]]" id="name" name="name" value="${role.name}" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">角色描述：</td>
					<td class="td_table_2" colspan="3">
						<textarea class="input_textarea_600 validate[maxSize[150]]" id="remark" name="remark">${role.remark}</textarea>
					</td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
			
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align=center width=10% class="td_list_1">
						<input type="checkbox" title="全选" id="selectAll">
					</td>
					<td align=center width=40% class="td_list_1">权限编号</td>
					<td align=center width=50% class="td_list_1">权限名称</td>
				</tr>
				<c:forEach items="${authorities}" var="item">
					<tr>
						<td class="td_list_2" align=center>
							<input type="checkbox" name="orderIndexs" value="${item.id}" ${item.selected== 1 ? 'checked' : ''}>
						</td>
						<td class="td_list_2" align=left>${item.code}</td>
						<td class="td_list_2" align=left>${item.name}</td>
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
