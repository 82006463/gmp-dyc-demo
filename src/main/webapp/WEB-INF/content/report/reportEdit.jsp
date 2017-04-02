<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理</title>
	<%@ include file="/common/common-edit.jsp"%>
</head>

<body>
	<form id="inputForm" action="${ctx}/dyc/report/update" method="post">
		<input type="hidden" name="id" value="${entity.id}"/>
		<input type="hidden" name="processType" value="${entity.processType}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					${entity.processType=='dev' ? '偏差':entity.processType=='cc' ? '变更':entity.processType=='capa' ? 'CAPA':entity.processType=='oos'? 'OOS':''}编号：
				</td>
				<td class="td_table_2">
					<input type="text" name="processNo" value="${entity.processNo}" class="input_240" readonly="readonly"/>
				</td>
				<td class="td_table_1">${entity.processType=='dev' ? '偏差':entity.processType=='cc' ? '变更':entity.processType=='capa' ? 'CAPA':entity.processType=='oos'? 'OOS':''}名称：</td>
				<td class="td_table_2">
					<input type="text" name="processName" value="${entity.processName}" class="input_240"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">发现时间：</td>
				<td class="td_table_2">
					<input type="text" name="occurTime" value="<fmt:formatDate value="${entity.occurTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" class="input_240 validate[required]" readonly="readonly"/>
				</td>
				<td class="td_table_1">关闭时间：</td>
				<td class="td_table_2">
					<input type="text" name="closeTime" value="<fmt:formatDate value="${entity.closeTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" class="input_240" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">部门：</td>
				<td class="td_table_2">
					<select name="orgId" class="input_select validate[required]" onchange="$('#orgName').val($(this).find('option:selected').text());">
						<option value="">------请选择------</option>
						<c:forEach items="${orgList}" var="item">
							<option value="${item.id}" ${entity.orgId==item.id ? 'selected="selected"':''}>${item.name}</option>
						</c:forEach>
					</select>
					<input type="hidden" id="orgName" name="orgName" value="${entity.orgName}"/>
				</td>
				<td class="td_table_1">级别：</td>
				<td class="td_table_2">
					<frame:dict name="level" type="select" typeCode="devLevel" value="${entity.level}" cssClass="input_select validate[required]"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">物料产品名称：</td>
				<td class="td_table_2">
					<input type="text" name="materielName" value="${entity.materielName}" class="input_240 validate[required]"/>
				</td>
				<td class="td_table_1">发现人：</td>
				<td class="td_table_2">
					<input type="text" name="occurPerson" value="${entity.occurPerson}" class="input_240 validate[required]"/>
				</td>
			</tr>
			<c:set var="row" value="0"></c:set>
			<c:set var="rowCount" value="${fn:length(extAttrMap)}"></c:set>
			<c:forEach items="${extAttrMap}" var="item" varStatus="itemIndex">
				<c:if test="${row==2 || itemIndex.count==rowCount}">
					<c:set var="row" value="0"></c:set>
					<tr>
				</c:if>
				<td class="td_table_1">${item.name}:${rowCount}</td>
				<td class="td_table_2" <c:if test="${row==0 && itemIndex.count==rowCount}">colspan="3"</c:if>>
					<input type="text" name="${item.code}" value="" class="input_240 validate[required]"/>
				</td>
				<c:if test="${row==2 || itemIndex.count==rowCount}">
					</tr></c:if>
				<c:set var="row" value="${row+1}"></c:set>
			</c:forEach>
			<tr>
				<td class="td_table_1">描述：</td>
				<td class="td_table_2" colspan="3">
					<textarea class="input_textarea_600" name="processDesc">${entity.processDesc}</textarea>
				</td>
			</tr>
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="submit" class="button_70px" name="submit" value="提交" onclick="return submitForm();">
					&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
