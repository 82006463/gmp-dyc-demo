<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>部门管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	<link rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/wbox/wbox/wbox.css" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/styles/plugin/js/jquery.validationEngine.js" type="text/javascript"></script>
	<script src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/styles/wbox/wbox.js"></script>
	<script>
		var iframewbox;
		function openOrg() {
			iframewbox = $("#selectOrgBtn").wBox({
				requestType: "iframe",
				iframeWH:{width:800,height:400},
				show: true,
				title:"选择上级部门",
				target:"${ctx}/security/org?lookup=1"
			});
		}

		function callbackProcess(id, name) {
			if(iframewbox) {
				document.getElementById("pid").value = id;
				document.getElementById("pname").value = name;
				iframewbox.close();
			}
		}

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
	<form id="inputForm" action="${ctx }/security/org/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="rootId" name="rootId" value="${entity.rootId}"/>
		<input type="hidden" id="level" name="level" value="${entity.level}"/>
		<input type="hidden" id="levelNo" name="levelNo" value="${entity.levelNo}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">部门管理</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">部门编号：</td>
				<td class="td_table_2">
					<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" ${empty entity.code ? '':'readonly="readonly"'}/>
				</td>
				<td class="td_table_1">部门名称：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required]"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">上级部门：</td>
				<td class="td_table_2" colspan="3">
					<input type="hidden" id="pid" name="pid" value="${entity.pid}">
					<input type="hidden" id="pcode" name="pcode" value="${entity.pcode}">
					<input type="text" id="pname" name="pname" value="${entity.pname}" class="input_240" readonly="readonly">
					<input type='button' class='button_70px' value='上级部门' id="selectOrgBtn" onclick="openOrg();"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">部门描述：</td>
				<td class="td_table_2" colspan="3">
					<input type="text" class="input_520" id="remark" name="remark" value="${entity.remark}">
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
