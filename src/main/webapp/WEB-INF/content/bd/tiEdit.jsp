<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>基础数据-测试项</title>
	<%@ include file="/common/common-edit.jsp"%>
</head>

<body>
	<form id="inputForm" action="${ctx }/bd/ti/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">基础数据-测试项</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">编号：</td>
				<td class="td_table_2">
					<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]"/>
				</td>
				<td class="td_table_1">版本：</td>
				<td class="td_table_2">
					<input type="text" id="ver" name="ver" value="${entity.ver}" class="input_240 validate[required]" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">描述：</td>
				<td class="td_table_2">
					<input type="text" id="remark" name="remark" value="${entity.remark}" class="input_240"/>
				</td>
				<td class="td_table_1">名称：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required]" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">类型：</td>
				<td class="td_table_2">
					<frame:dict name="dataType" type="select" typeCode="dataType" value="${entity.dataType}" cssClass="input_select validate[required]"/>
				</td>
				<td class="td_table_1">运算符：</td>
				<td class="td_table_2">
					<frame:dict name="operator" type="select" typeCode="operator" value="${entity.operator}" cssClass="input_select validate[required]"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">内控标准：</td>
				<td class="td_table_2">
					<input type="text" id="innerQs" name="innerQs" value="${entity.innerQs}" class="input_240"/>
				</td>
				<td class="td_table_1">法定标准：</td>
				<td class="td_table_2">
					<input type="text" id="lawQs" name="lawQs" value="${entity.lawQs}" class="input_240 validate[required]" />
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
