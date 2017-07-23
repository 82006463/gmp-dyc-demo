<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>${process.name}：${process.displayName}</title>
	<%@ include file="/common/common-edit.jsp"%>
</head>

<body>
	<form id="inputForm" action="${ctx}/flow/task/approval" enctype="multipart/form-data" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="processId" name="processId" value="${process.id}"/>
		<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
		<input type="hidden" id="taskId" name="taskId" value="${task.id}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">${process.displayName}：${empty task ? '开始':task.displayName}</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			${jsonEdit}
			<c:if test="${!empty task}">
				<c:if test="${!empty entity.uploadFiles && fn:length(entity.uploadFiles)>0}">
					<tr>
						<td class="td_table_1">附件列表：</td>
						<td class="td_table_2" colspan="3">
							<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin: 0">
								<tr>
									<td align=center width=90% class="td_list_1">附件名</td>
									<td align=center width=10% class="td_list_1">操作</td>
								</tr>
								<c:forEach items="${entity.uploadFiles}" var="_file">
									<tr>
										<td class="td_list_2">${_file.fileName}</td>
										<td align="center" class="td_list_2">
											<a href="${ctx}/file/download?fileName=${_file.fileName}" class="btnDownload" title="下载" target="_blank">下载</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="td_table_1">拒绝节点：</td>
					<td class="td_table_2" colspan="3">
						<select name="nodeNameDest" class="input_select">
							<c:forEach items="${buttonsOfReject}" var="btn" varStatus="btnIndex">
								<option value="${btn.key}" <c:if test="${btnIndex.index == 0}">selected="selected"</c:if>>${btn.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:if>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<c:if test="${!empty task}">
						<%--<input type="submit" class="button_70px" name="submit" value="保存">&nbsp;&nbsp;--%>
						<input type="button" class="button_70px" name="submit" value="保存" data-toggle="modal" data-target="#myModal">&nbsp;&nbsp;
					</c:if>
					<c:forEach items="${buttons}" var="btn">
						<%--<input type="submit" class="button_70px" name="submit" value="${btn.value}" onclick="return Ops.submit();">&nbsp;&nbsp;--%>
						<input type="button" class="button_70px" name="submit" value="${btn.value}" data-toggle="modal" data-target="#myModal">&nbsp;&nbsp;
					</c:forEach>
					<c:if test="${!empty task}">
						<%--<input type="submit" class="button_70px" name="submit" value="拒绝">&nbsp;&nbsp;--%>
						<input type="button" class="button_70px" name="submit" value="拒绝" data-toggle="modal" data-target="#myModal">&nbsp;&nbsp;
					</c:if>
					<input type="button" class="button_70px" name="submit" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
		<%@ include file="/common/common-modal.jsp"%>
	</form>
</body>
</html>
