<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-伸缩表-${item=='search' ? '搜索项':item=='list' ? '列表项':'编辑页'}</title>
		<%@ include file="/common/common-edit.jsp"%>
	</head>

	<body>
		<form id="inputForm" action="${ctx}/config/meta/${entity.type}/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity._id}"/>
			<input type="hidden" id="type" name="type" value="${entity.type}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-伸缩表-${item=='search' ? '搜索项':item=='list' ? '列表项':'编辑页'}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">
						<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">是否独立表：</td>
					<td class="td_table_2" colspan="3">
						<select class="input_select" id="oneTable" name="oneTable">
							<option value='' selected>--请选择--</option>
							<option value='1' ${entity.oneTable==1 ? 'selected="selected"':''}>是</option>
							<option value='0' ${entity.oneTable==0 ? 'selected="selected"':''}>否</option>
						</select>
					</td>
				</tr>

				<%--<tr>
					<td class="td_table_1">列表搜索URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_url" name="list_url" value="${entity.list_url}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">列表创建URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_create_url" name="list_create_url" value="${entity.list_create_url}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">编辑链接URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_update_url" name="list_update_url" value="${entity.list_update_url}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">删除链接URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_delete_url" name="list_delete_url" value="${entity.list_delete_url}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">查看链接URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_view_url" name="list_view_url" value="${entity.list_view_url}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">提交链接URL：</td>
					<td class="td_table_2">
						<input type="text" id="update_submit_url" name="update_submit_url" value="${entity.update_submit_url}" class="input_240 validate[required]" />
					</td>
				</tr>--%>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1" rowspan="2">编辑页面：选项列表</td>
					<td class="td_table_2" colspan="3">
						<span style="font-weight: bold; color: red;">编辑页面</span>：<input type="button" class="button_70px" value="添加选项" onclick="editTable();">
					</td>
				</tr>
				<tr>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="editTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">编号</td>
								<td align=center class="td_list_1">标题</td>
								<td align=center class="td_list_1">数据类型</td>
								<td align=center class="td_list_1">标签类型</td>
								<td align=center class="td_list_1">是否必填</td>
								<td align=center class="td_list_1">子表单</td>
								<td align=center class="td_list_1">模糊搜索</td>
								<td align=center class="td_list_1">数组</td>
								<td align=center class="td_list_1">显示方式</td>
								<td align=center width=6% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${entity.editItems}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="text" name='editCodes' value='${item.code}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<input type="text" name='editNames' value='${item.name}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<frame:dict name="editDataTypes" type="select" typeCode="dataType" value="${item.dataType}" defaultVal="dataType_String" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editTagTypes" type="select" typeCode="tagType" value="${item.tagType}" defaultVal="tagType_Input" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editRequireds" type="select" typeCode="yesNo" value="${item.required}" defaultVal="yesNo_yes" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editSubForms" type="select" typeCode="subForm" value="${item.subForm}" defaultVal="subForm_no" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editFuzzys" type="select" typeCode="fuzzySearch" value="${item.fuzzy}" defaultVal="fuzzySearch_no" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editArrays" type="select" typeCode="yesNo" value="${item.array}" defaultVal="yesNo_no" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="editDisplayModes" type="select" typeCode="displayMode" value="${item.displayMode}" defaultVal="displayMode_normal" cssClass="validate[required]"/>
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
				<tr>
					<td class="td_table_1" rowspan="2">搜索页面：选项列表</td>
					<td class="td_table_2" colspan="3">
						<span style="font-weight: bold; color: red;">搜索页面</span>：<input type="button" class="button_70px" value="添加选项" onclick="queryTable();">
					</td>
				</tr>
				<tr>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="queryTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">编号</td>
								<td align=center class="td_list_1">标题</td>
								<td align=center class="td_list_1">数据类型</td>
								<td align=center class="td_list_1">比较条件</td>
								<td align=center class="td_list_1">标签类型</td>
								<td align=center width=6% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${entity.queryItems}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="text" name='queryCodes' value='${item.code}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<input type="text" name='queryNames' value='${item.name}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<frame:dict name="queryDataTypes" type="select" typeCode="dataType" value="${item.dataType}" defaultVal="dataType_String" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="queryCompareTypes" type="select" typeCode="compareType" value="${item.compareType}" defaultVal="compareType_Eq" cssClass="validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="queryTagTypes" type="select" typeCode="tagType" value="${item.tagType}" defaultVal="tagType_Input" cssClass="validate[required]"/>
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
				<tr>
					<td class="td_table_1" rowspan="2">列表页面：选项列表</td>
					<td class="td_table_2" colspan="3">
						<span style="font-weight: bold; color: red;">列表页面</span>：<input type="button" class="button_70px" value="添加选项" onclick="listTable();">
					</td>
				</tr>
				<tr>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="listTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">编号</td>
								<td align=center class="td_list_1">标题</td>
								<td align=center class="td_list_1">数据类型</td>
								<td align=center width=6% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${entity.listItems}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="text" name='listCodes' value='${item.code}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<input type="text" name='listNames' value='${item.name}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<frame:dict name="listDataTypes" type="select" typeCode="dataType" value="${item.dataType}" defaultVal="dataType_String" cssClass="validate[required]"/>
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

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<%--<shiro:hasPermission name="config_meta_${type}_edit">--%>
							<input type="submit" class="button_70px" name="submit"value="提交" onclick="return Ops.submit();">&nbsp;&nbsp;
						<%--</shiro:hasPermission>--%>
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			function editTable() {
				var table = document.getElementById("editTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='editCodes' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='editNames' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editDataTypes" type="select" typeCode="dataType" value="" defaultVal="dataType_String" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editTagTypes" type="select" typeCode="tagType" value="" defaultVal="tagType_Input" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editRequireds" type="select" typeCode="yesNo" value="" defaultVal="yesNo_yes" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editSubForms" type="select" typeCode="subForm" value="" defaultVal="subForm_no" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editFuzzys" type="select" typeCode="fuzzySearch" value="" defaultVal="fuzzySearch_no" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editArrays" type="select" typeCode="yesNo" value="" defaultVal="yesNo_no" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="editDisplayModes" type="select" typeCode="displayMode" value="${item.hidden}" defaultVal="displayMode_normal" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='Ops.removeTr(this,1);' class='btnDel' title='删除'>删除</a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>";
				cell.className = "td_list_2";
			}

			function queryTable() {
				var table = document.getElementById("queryTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='queryCodes' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='queryNames' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="queryDataTypes" type="select" typeCode="dataType" value="" defaultVal="dataType_String" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="queryCompareTypes" type="select" typeCode="compareType" value="" defaultVal="compareType_Eq" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="queryTagTypes" type="select" typeCode="tagType" value="" defaultVal="tagType_Input" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='Ops.removeTr(this,1);' class='btnDel' title='删除'>删除</a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>";
				cell.className = "td_list_2";
			}

			function listTable() {
				var table = document.getElementById("listTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='listCodes' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='listNames' value='' class='input_120 validate[required]'>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="listDataTypes" type="select" typeCode="dataType" value="" defaultVal="dataType_String" cssClass="validate[required]"/>";
				cell.className = "td_list_2";
				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='Ops.removeTr(this,1);' class='btnDel' title='删除'>删除</a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>";
				cell.className = "td_list_2";
			}
		</script>
	</body>
</html>
