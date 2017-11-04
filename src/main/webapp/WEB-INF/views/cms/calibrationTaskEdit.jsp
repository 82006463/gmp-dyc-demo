<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>待办任务</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
			function updateTask() {
				bringback('${item.id}','${item.name}');
			}
		</script>
	</head>

	<body>
	<form id="mainForm" enctype="multipart/form-data" action="${ctx}/custom/cms/calibrationTask/update" method="post">
		<input type="hidden" name="id" value="${entity.id}" class="input_240" />
		<input type="hidden" name="status" value="${entity.status}" class="input_240" />
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">待办任务</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=10% class="td_list_1">器具编号</td>
				<td align=center width=20% class="td_list_1">器具名称</td>
				<td align=center width=10% class="td_list_1">所在房间</td>
				<td align=center width=10% class="td_list_1">上次校准时间</td>
				<td align=center width=10% class="td_list_1">上次校准单位</td>
				<td align=center width=10% class="td_list_1">校准有效期</td>
				<td align=center width=10% class="td_list_1">校准结果</td>
				<td align=center width=10% class="td_list_1">实际校准时间</td>
				<td align=center width=10% class="td_list_1">动作</td>
			</tr>
			<c:forEach items="${children}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>
						${item.equipment.code}
						<input type="hidden" name="planId" value="${item.id}" class="input_240" />
					</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.lastActualDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.equipment.calibrationCycle}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>
						<select name="calibrationResult" class="input_select" style="width: 100%;">
							<option value="">-请选择-</option>
							<option value="1" ${item.calibrationResult==1 ? 'selected="selected"':''}>合格</option>
							<option value="0" ${item.calibrationResult==0 ? 'selected="selected"':''}>不合格</option>
						</select>
					</td>
					<td class="td_list_2" align=left>
						<input type="text" name="actualDate" value="<fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/>" class="input_240" style="width: 100%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'%y-%M-{%d+1}'});" readonly="readonly"/>
					</td>
					<td class="td_list_2" align=left>
						<input type="file" name="files" class="input_240" style="width: 100%;"/>
					</td>
				</tr>
			</c:forEach>

			<tr>
				<td class="td_table_2">
				</td>
				<td class="td_table_2" colspan="8">
					<c:if test="${!empty entity.current && entity.current == entity.approver && (entity.status==1 || entity.status==2)}">
						<input type='submit' id="tempBtn" class='button_70px' value="暂存"/>
						<input type='submit' id="submitBtn" class='button_70px' value="提交"/>
					</c:if>
					<c:if test="${!empty entity.current && entity.current == entity.reviewer && entity.status==3}">
						<input type='submit' id="reviewBtn" class='button_70px' value="复核"/>
						<input type='submit' id="rejectBtn" class='button_70px' value="拒绝"/>
					</c:if>
				</td>
			</tr>
		</table>
	</form>
	</body>
</html>
