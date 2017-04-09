<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>报表管理-${etab.name}</title>
	<%@ include file="/common/common-edit.jsp"%>
</head>

<body>
	<form id="inputForm" action="${ctx}/dyc/report/update" method="post">
		<input type="hidden" name="id" value="${entity.id}"/>
		<input type="hidden" name="processType" value="${entity.processType}"/>
		<input type="hidden" name="orgName" value="${entity.orgName}"/>
		<input type="hidden" name="level" value="${entity.level}"/>
		<input type="hidden" name="occurPerson" value="${entity.occurPerson}"/>
		<input type="hidden" name="type1" value="${entity.type1}"/>
		<input type="hidden" name="type2" value="${entity.type2}"/>
		<input type="hidden" name="str21" value="${entity.str21}"/>
		<input type="hidden" name="str22" value="${entity.str22}"/>
		<input type="hidden" name="str23" value="${entity.str23}"/>
		<input type="hidden" name="str24" value="${entity.str24}"/>
		<input type="hidden" name="str25" value="${entity.str25}"/>
		<input type="hidden" name="str51" value="${entity.str51}"/>
		<input type="hidden" name="str52" value="${entity.str52}"/>
		<input type="hidden" name="str53" value="${entity.str53}"/>
		<input type="hidden" name="str54" value="${entity.str54}"/>
		<input type="hidden" name="str55" value="${entity.str55}"/>
		<input type="hidden" name="int1" value="${entity.num1}"/>
		<input type="hidden" name="int2" value="${entity.num2}"/>
		<input type="hidden" name="int3" value="${entity.num3}"/>
		<input type="hidden" name="int4" value="${entity.num4}"/>
		<input type="hidden" name="int5" value="${entity.num5}"/>
		<input type="hidden" name="long1" value="${entity.num21}"/>
		<input type="hidden" name="long2" value="${entity.num22}"/>

		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">报表管理-${etab.name}</td>
			</tr>
		</table>

		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			${jsonEdit}
		</table>

		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="submit" class="button_70px" name="submit" value="提交" onclick="return Ops.submit();">
					&nbsp;&nbsp;
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
