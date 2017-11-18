<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>待办任务</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
			function updateTask(status) {
			    var result = true;
			    if(status==3) {
					$('[name=certCode]').each(function () {
						if($.trim($(this).val())==''){
						    alert('记录/证书编号不能为空');
						    result = false;
						}
                    });
                    $('[name=calibrationResult]').each(function () {
                        if($.trim($(this).val())==''){
                            alert('校验结果不能为空');
                            result = false;
                        }
                    });
                    $('[name=actualDate]').each(function () {
                        if($.trim($(this).val())==''){
                            alert('实际校验日期不能为空');
                            result = false;
                        }
                    });
				}
				return result;
			}

            function exportFile() {
                $('#downloadBtn').prop('href', '${ctx}/custom/cms/calibrationTask/exportFile?taskId='+$('#id').val());
                $('#downloadBtn').trigger('click');
                $('#downloadBtn').prop('href', '#');
                return false;
            }
		</script>
	</head>

	<body>
	<form id="mainForm" enctype="multipart/form-data" action="${ctx}/custom/cms/calibrationTask/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}" class="input_240" />
		<input type="hidden" id="status" name="status" value="${entity.status}" class="input_240" />
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">待办任务</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=10% class="td_list_1">器具编号</td>
				<td align=center width=20% class="td_list_1">器具名称</td>
				<td align=center width=10% class="td_list_1">所在房间</td>
				<td align=center width=10% class="td_list_1">出厂编号</td>
				<td align=center width=10% class="td_list_1">记录/证书编号</td>
				<td align=center width=10% class="td_list_1">校准结果</td>
				<td align=center width=10% class="td_list_1">实际校准时间</td>
				<td align=center width=10% class="td_list_1">备注</td>
				<td align=center width=10% class="td_list_1">动作</td>
			</tr>
			<c:forEach items="${children}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>
						${item.equipment.code}
						<input type="hidden" name="planId" value="${item.id}" class="input_240" />
					</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left>${item.equipment.factoryCode}</td>
					<c:if test="${(entity.status==1 || entity.status==2) && fn:toLowerCase(username)==fn:toLowerCase(entity.approver)}">
						<td class="td_list_2" align=left>
							<input type="text" id="certCode" name="certCode" value="${item.certCode}" class="input_240" style="width: 100%;"/>
						</td>
						<td class="td_list_2" align=left>
							<select name="calibrationResult" class="input_select" style="width: 100%;">
								<option value="">-请选择-</option>
								<option value="1" ${item.calibrationResult==1 ? 'selected="selected"':''}>合格</option>
								<option value="-1" ${item.calibrationResult==-1 ? 'selected="selected"':''}>不合格</option>
							</select>
						</td>
						<td class="td_list_2" align=left>
							<input type="text" name="actualDate" value="<fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/>" class="input_240" style="width: 100%;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d'});" readonly="readonly"/>
						</td>
                        <td class="td_list_2" align=left>
                            <input type="text" name="remark" value="${item.remark}" class="input_240" style="width: 100%;" />
                        </td>
					</c:if>
					<c:if test="${(entity.status==3) && fn:toLowerCase(username)!=fn:toLowerCase(entity.approver)}">
						<td class="td_list_2" align=left>${item.certCode}
							<input type="hidden" name="certCode" value="${item.certCode}" class="input_240" />
							<input type="hidden" name="calibrationResult" value="${item.calibrationResult}" class="input_240" />
							<input type="hidden" name="actualDate" value="<fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/>" class="input_240" />
                            <input type="hidden" name="remark" value="${item.remark}" class="input_240" style="width: 100%;" />
						</td>
						<td class="td_list_2" align=left>${item.calibrationResult==1 ? '合格':'不合格'}</td>
						<td class="td_list_2" align=left><fmt:formatDate value="${item.actualDate}" pattern="yyyy-MM-dd"/></td>
                        <td class="td_list_2" align=left>${item.remark}</td>
					</c:if>

					<td class="td_list_2" align=left>
						<%--<input type="file" name="files" class="input_240" style="width: 100%;"/>--%>
						<input type=button class='button_70px' value="上传" onclick="alert('正式版功能');"/>
					</td>
				</tr>
			</c:forEach>

			<tr>
				<td class="td_table_2">
				</td>
				<td class="td_table_2" colspan="8">
					<c:if test="${(entity.status==1 || entity.status==2) && fn:toLowerCase(username)==fn:toLowerCase(entity.approver)}">
						<input type='submit' id="tempBtn" class='button_70px' value="暂存"/>
						<input type='submit' id="submitBtn" class='button_70px' value="提交" onclick="$('#status').val(3);"/>
					</c:if>
					<c:if test="${(entity.status==3) && fn:toLowerCase(username)!=fn:toLowerCase(entity.approver)}">
						<shiro:hasPermission name="cms_calibrationTask_review">
							<input type='submit' id="reviewBtn" class='button_70px' value="复核" onclick="$('#status').val(4);"/>
							<input type='submit' id="rejectBtn" class='button_70px' value="拒绝" onclick="$('#status').val(2);"/>
						</shiro:hasPermission>
					</c:if>
					<a id="downloadBtn" class='button_70px' href="#" target="_blank" onclick="return exportFile();">导出Excel</a>
				</td>
			</tr>
		</table>
	</form>
	</body>
</html>
