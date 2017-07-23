<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>${metaTag.name}</title>
	<%@ include file="/common/common-edit.jsp"%>
</head>

<body>
	<form id="inputForm" action="${ctx}/meta/app/${entity.metaType}/${entity.cmcode}/update" method="post">
		<input type="hidden" name="id" value="${entity._id}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">${metaTag.name}</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			${jsonEdit}
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<%--<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">--%>
					<input type="button" class="button_70px" name="submit" value="提交" data-toggle="modal" data-target="#myModal">&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>

		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">确认</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<span class="input-group-addon">账号</span>
							<input type="text" id="base_username" name="base_username" value="" class="form-control">
						</div>
						<div class="input-group">
							<span class="input-group-addon">密码</span>
							<input type="text" id="base_password" name="base_password" value="" class="form-control">
						</div>
						<div class="input-group">
							<span class="input-group-addon">意见</span>
							<textarea rows="5" id="base_reason" name="base_reason" class="form-control"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" id="modal-close">关闭</button>
						<button type="button" class="btn btn-primary" onclick="return Ops.checkUser();">提交</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
	</form>
</body>
</html>
