<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-数据字典</title>
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
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-数据字典</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">流程类型：</td>
					<td class="td_table_2">
						<c:if test="${empty entity.type}">
							<frame:dict name="type" type="select" typeCode="processType" value="${entity.type}" cssClass="input_select validate[required]"/>
						</c:if>
						<c:if test="${!empty entity.type}">
							<input type="text" id="type" name="type" value="${entity.type}" class="input_240 validate[required]" readonly="readonly"/>
						</c:if>
					</td>
					<td class="td_table_1">流程前缀：</td>
					<td class="td_table_2">
						<input type="text" id="prefix" name="prefix" value="${entity.prefix}" class="input_240 validate[required]"/>
					</td>
				</tr>

				<tr>
					<td class="td_table_1">时间模式：</td>
					<td class="td_table_2">
						<input type="text" id="timePattern" name="timePattern" value="${entity.timePattern}" class="input_240 validate[required]"/>
					</td>
					<td class="td_table_1">流程号长度：</td>
					<td class="td_table_2">
						<input type="text" id="indexLength" name="indexLength" value="${entity.indexLength}" class="input_240 validate[required]"/>
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
