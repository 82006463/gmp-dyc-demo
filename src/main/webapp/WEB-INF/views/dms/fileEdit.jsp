<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>文件</title>
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
                        url: '${ctx}/custom/dms/file/up/check',
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
	<form id="inputForm" enctype="multipart/form-data" action="${ctx}/custom/dms/file/up/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="status" name="status" value="${entity.status}"/>
		<input type="hidden" id="fileName" name="fileName" value="${entity.fileName}"/>
		<input type="hidden" id="filePath" name="filePath" value="${entity.filePath}"/>

		<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
		<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<c:if test="${!empty entity.id}">
			<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<input type="hidden" id="updaterId" name="updaterId" value="${userId}"/>
		</c:if>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">文件</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">文件编号<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="code" name="code" class="input_240 validate[required,minSize[1],maxSize[50]]" value="${entity.code}" <c:if test="${entity.status==3 || !empty entity.id}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">文件名称<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" class="input_240 validate[required,minSize[1],maxSize[100]]" value="${entity.name}" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">文件版本：</td>
				<td class="td_table_2">
					<input type="text" id="fileVer" name="fileVer" value="${entity.fileVer}" class="input_240 validate[required,minSize[1],maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">上传：</td>
				<td class="td_table_2">
					<input type="file" name="files" class="input_240" style="width: 100%;"/>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<c:if test="${entity.status!=3}">
						<shiro:hasPermission name="cms_equipment_edit">
							<input type="submit" class="button_70px" name="submit" value="暂存" onclick="$('#status').val(1); return check();">&nbsp;&nbsp;
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="$('#status').val(3); return check();">
						</shiro:hasPermission>
					</c:if>
					<c:if test="${entity.status==3 && userId!=entity.updaterId}">
						<shiro:hasPermission name="cms_equipment_review">
							<input type="submit" class="button_70px" name="submit" value="拒绝" onclick="$('#status').val(2); return check();">&nbsp;&nbsp;
							<input type="submit" class="button_70px" name="submit" value="复核" onclick="$('#status').val(4); return check();">
						</shiro:hasPermission>
					</c:if>
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
