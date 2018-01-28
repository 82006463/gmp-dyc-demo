<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>备份文档</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
	<script type="text/javascript">
		function check() {
			var result = false;
			var _measureRangeMin = $('#measureRangeMin').val();
            var _measureRangeMax = $('#measureRangeMax').val();
			if(Ops.submit()) {
			    if (parseInt(_measureRangeMin) > parseInt(_measureRangeMax)) {
					alert('使用范围下限不能大于使用范围上限');
				} else {
                    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '${ctx}/custom/dm/backupDoc/check',
                        data: {id:$('#id').val(), code:$('#code').val()},
                        success: function(data){
                            if(data.result == 1){
                                result = true;
                            } else {
                                alert(data.msg);
                            }
                        }
                    });
				}
			}
			return result;
		}
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/custom/dm/backupDoc/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="status" name="status" value="${entity.status}"/>
		<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
		<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">备份文档</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">本机MAC地址<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="macAddr" name="macAddr" class="input_520 validate[required,minSize[1],maxSize[50]]" value="${entity.macAddr}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">本机客户端的名称<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="host" name="host" class="input_520 validate[required,minSize[1],maxSize[30]]" value="${entity.host}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">任务名称<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" class="input_520 validate[required,minSize[1],maxSize[50]]" value="${entity.name}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">备份源<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="bakSource" name="bakSource" class="input_520 validate[required,minSize[1],maxSize[120]]" value="${entity.bakSource}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">备份路径<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="sourcePath" name="sourcePath" class="input_520 validate[required,minSize[1],maxSize[260]]" value="${entity.sourcePath}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">备份格式<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="includeFormat" name="includeFormat" class="input_520 validate[required,minSize[1],maxSize[40]]" value="${entity.includeFormat}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">排除备份格式<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="excludeFormat" name="excludeFormat" class="input_520 validate[minSize[1],maxSize[40]]" value="${entity.excludeFormat}" />
				</td>
			</tr>
			<tr>
				<td class="td_table_1">操作方式<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<select id="backupMode" name="backupMode" class="input_select validate[required]">
						<option value="">-请选择-</option>
						<option value="1" ${entity.backupMode==1 ? 'selected="selected"':''}>自动备份</option>
						<option value="2" ${entity.backupMode==2 ? 'selected="selected"':''}>手动备份</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">备份频次<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="cron" name="cron" class="input_520 validate[required,minSize[1],maxSize[50]]" value="${entity.cron}" />
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<input type="hidden" id="updaterId" name="updaterId" value="${userId}"/>
					<shiro:hasPermission name="dm_backupDoc_edit">
						<input type="submit" class="button_70px" name="submit" value="提交" onclick="$('#status').val(1); return Ops.submit();">
					</shiro:hasPermission>
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
