<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>报表管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/styles/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>

<body>
	<form id="inputForm" action="${ctx }/dyc/report/update" method="post">
		<input type="hidden" name="id" value="${entity.id}"/>
		<input type="hidden" name="processType" value="${entity.processType}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">编号：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="processNo" value="${entity.processNo}" />
				</td>
				<td class="td_table_1">发现时间：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="occurTime" value="${entity.occurTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">部门：</td>
				<td class="td_table_2">
					<select name="deptId" class="input_select">
						<option value="">------请选择------</option>
						<c:forEach items="${orgList}" var="item">
							<option value="${item.id}" ${entity.deptId==item.id ? 'selected="selected"':''}>${item.name}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_table_1">级别：</td>
				<td class="td_table_2">
					<frame:dict name="level" type="select" typeCode="devLevel" value="${entity.level}" cssClass="input_select"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">物料产品名称：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="materielName" value="${entity.materielName}" />
				</td>
				<td class="td_table_1">发现人：</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="occurPerson" value="${entity.occurPerson}" />
				</td>
			</tr>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="submit" class="button_70px" name="submit" value="提交">
					&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
