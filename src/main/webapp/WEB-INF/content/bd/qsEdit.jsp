<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-质量标准</title>
		<%@ include file="/common/meta.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/css/style.css" media="all" />
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" />
		<script type="text/javascript" src="${ctx}/styles/js/jquery-1.8.3.min.js"></script>
		<script src="${ctx}/styles/plugin/js/jquery.validationEngine.js" type="text/javascript"></script>
		<script src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function() {
				$('#inputForm').validationEngine();
			});

			function submitForm() {
				if($('#inputForm').validationEngine('validate')) {
					$('#inputForm').submit();
				}
				return false;
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/config/processNo/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-质量标准</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">质量标准编号：</td>
					<td class="td_table_2">
						<c:if test="${empty entity.code}">
							<frame:dict name="code" type="select" typeCode="processType" value="${entity.code}" cssClass="input_select validate[required]"/>
						</c:if>
						<c:if test="${!empty entity.code}">
							<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" readonly="readonly"/>
						</c:if>
					</td>
					<td class="td_table_1">质量标准版本：</td>
					<td class="td_table_2">
						<input type="text" id="ver" name="ver" value="${entity.ver}" class="input_240 validate[required]" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">物料产品名称：</td>
					<td class="td_table_2">
						<input type="text" id="materielName" name="materielName" value="${entity.materielName}" class="input_240 validate[required]"/>
					</td>
					<td class="td_table_1">物料产品代码：</td>
					<td class="td_table_2">
						<input type="text" id="materielCode" name="materielCode" value="${entity.materielCode}" class="input_240 validate[required]" onblur="checkTimePattern(this);"/>
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
