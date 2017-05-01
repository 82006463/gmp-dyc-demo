<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-伸缩表-${item=='search' ? '搜索项':item=='list' ? '列表项':'编辑页'}</title>
		<%@ include file="/common/common-edit.jsp"%>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/config/meta/app/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity._id}"/>
			<input type="hidden" id="type" name="type" value="${entity.type}"/>
			<input type="hidden" id="item" name="item" value="${item}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-应用-${item=='search' ? '搜索项':item=='list' ? '列表项':'编辑页'}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">
                        <c:if test="${empty entity.code}">
						    <frame:dict name="code" type="select" typeCode="${entity.type}" value="${entity.code}" cssClass="input_select validate[required]" />
                        </c:if>
                        <c:if test="${!empty entity.code}">
                            <input type="hidden" id="code" name="code" value="${entity.code}" class="input_240" />${entity.code}
                        </c:if>
					</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">列表搜索URL：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="list_url" name="list_url" value="${entity.list_url}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">SQL：</td>
					<td class="td_table_2" colspan="3">
						<textarea name="itemSql" class="input_textarea_600 validate[required]">${entity.itemSql}</textarea>
					</td>
				</tr>
			</table>

			<c:if test="${item=='search'}">
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">添加选项：</td>
					<td class="td_table_2" colspan="3">
						<input type="button" class="button_70px" value="添加选项" onclick="addItem();">
					</td>
				</tr>
				<tr>
					<td class="td_table_1">选项列表：</td>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="itemTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">字段</td>
								<td align=center class="td_list_1">描述</td>
								<td align=center class="td_list_1">数据类型</td>
								<td align=center class="td_list_1">比较条件</td>
								<td align=center class="td_list_1">标签类型</td>
								<td align=center width=6% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${entity.queryItems}" var="itemTmp" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="text" name='itemCodes' value='${itemTmp.code}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<input type="text" name='itemDescs' value='${itemTmp.desc}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<frame:dict name="itemDataTypes" type="select" typeCode="dataType" value="${itemTmp.dataType}" defaultVal="dataType_String" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="itemCompareTypes" type="select" typeCode="compareType" value="${itemTmp.compareType}" defaultVal="compareType_Eq" cssClass="validate[required]"/>
									</td>
                                    <td class="td_list_2">
                                        <frame:dict name="itemTagTypes" type="select" typeCode="tagType" value="${itemTmp.tagType}" defaultVal="tagType_Input" cssClass="validate[required]"/>
                                    </td>
									<td class="td_list_2">
										<a href='javascript:void(0)' onclick='Ops.removeTr(this,1);' class='btnDel' title='删除'>删除</a>
                                        <a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			</c:if>

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="submit" class="button_70px" name="submit"value="提交" onclick="return Ops.submit();">
						&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			$(document).ready(function () {
				$('select[name=code]').change(function () {
					var _pre = '/meta/${entity.type}/' + $(this).val().replace('${entity.type}_','');
                    if($.trim($('[name=list_url]').val()).length == 0) {
                        $('[name=list_url]').val(_pre + '/list');
                    }
				});
			});

			function addItem() {
				var table = document.getElementById("itemTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='itemCodes' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='itemDescs' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="itemDataTypes" type="select" typeCode="dataType" value="" defaultVal="dataType_String" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="itemCompareTypes" type="select" typeCode="compareType" value="" defaultVal="compareType_Eq" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="itemTagTypes" type="select" typeCode="tagType" value="" defaultVal="tagType_Input" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='Ops.removeTr(this,1);' class='btnDel' title='删除'>删除</a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>";
				cell.className = "td_list_2";
			}
		</script>
	</body>
</html>
