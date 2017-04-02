<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-台账结构</title>
		<%@ include file="/common/common-edit.jsp"%>
		<script type="text/javascript">
			function checkTimePattern(thisTag) {
				var _val = $(thisTag).val();
				if(_val.length>0 && _val!='yyyy' && _val!='yyyyMM' && _val!='yyyyMMdd') {
					alert('时间模式必须符合(y:年,M:月,d:日)：yyyy | yyyyMM | yyyyMMdd');
					$(thisTag).val('');
				}
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/config/processNo/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<input type="hidden" id="timeValue" name="timeValue" value="${entity.timeValue}"/>
			<input type="hidden" id="indexValue" name="indexValue" value="${entity.indexValue}"/>
			<input type="hidden" id="currentValue" name="currentValue" value="${entity.currentValue}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-台账结构</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">
						<c:if test="${empty entity.code}">
							<frame:dict name="code" type="select" typeCode="processType" value="${entity.code}" cssClass="input_select validate[required]"/>
						</c:if>
						<c:if test="${!empty entity.code}">
							<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" readonly="readonly"/>
						</c:if>
					</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required]" />
					</td>
				</tr>

				<tr>
					<td class="td_table_1">流程前缀：</td>
					<td class="td_table_2">
						<input type="text" id="prefix" name="prefix" value="${entity.prefix}" class="input_240 validate[required,minSize[1],maxSize[8]]"/>
					</td>
					<td class="td_table_1">时间模式：</td>
					<td class="td_table_2">
						<input type="text" id="timePattern" name="timePattern" value="${entity.timePattern}" class="input_240 validate[required,minSize[2],maxSize[8]]" onblur="checkTimePattern(this);"/>
					</td>
				</tr>

				<tr>
					<td class="td_table_1">部门状态：</td>
					<td class="td_table_2">
						<input type="radio" id="orgState1" name="orgState" value="1" ${empty entity.orgState || entity.orgState==1 ? 'checked="checked"':''}/> 启用
						<input type="radio" id="orgState2" name="orgState" value="0" ${entity.orgState==0 ? 'checked="checked"':''}/> 停用
					</td>
					<td class="td_table_1">部门编号：</td>
					<td class="td_table_2">
						<select name="orgId" class="input_select validate[required]" onchange="$('#orgCode').val($(this).find('option:selected').text());">
							<option value="">------请选择------</option>
							<c:forEach items="${orgList}" var="item">
								<option value="${item.id}" ${entity.orgId==item.id ? 'selected="selected"':''}>${item.code}：${item.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>

				<tr>
					<td class="td_table_1">流水号长度：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="indexLength" name="indexLength" value="${entity.indexLength}" class="input_240 validate[required,custom[integer],min[1]]"/>
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
