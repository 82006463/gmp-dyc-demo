<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>${metaTag.name}</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
	<script type="text/javascript">

	</script>
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
		<%@ include file="/common/common-modal.jsp"%>
	</form>
</body>
</html>
