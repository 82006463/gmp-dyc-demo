<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>基础数据-检验报告模板</title>
		<%@ include file="/common/common-edit.jsp"%>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/bd/trt/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">基础数据-检验报告模板</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">
						<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required,maxSize[50]]"/>
					</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required,maxSize[100]]"/>
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
