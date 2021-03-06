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
			<input type="hidden" id="item" name="item" value="${item}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-应用-${item=='search' ? '搜索项':item=='list' ? '列表项':'编辑页'}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="code" name="code" value="${entity.code}" class="input_240 validate[required]" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">标题：</td>
					<td class="td_table_2">
						<input type="text" id="title" name="title" value="${entity.title}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">子标题：</td>
					<td class="td_table_2">
						<input type="text" id="subtitle" name="subtitle" value="${entity.subtitle}" class="input_240" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">Y轴标题：</td>
					<td class="td_table_2">
						<input type="text" id="ytitle" name="ytitle" value="${entity.ytitle}" class="input_240" />
					</td>
					<td class="td_table_1">图例：</td>
					<td class="td_table_2">
						<input type="text" id="xtitle" name="xtitle" value="${entity.xtitle}" class="input_240" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">值后缀：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" id="valueSuffix" name="valueSuffix" value="${entity.valueSuffix}" class="input_240" />
					</td>
				</tr>
				<tr>
					<td class="td_table_1">列表搜索URL：</td>
					<td class="td_table_2">
						<input type="text" id="list_url" name="list_url" value="${entity.list_url}" class="input_240 validate[required]" />
					</td>
					<td class="td_table_1">图表类型：</td>
					<td class="td_table_2">
						<select name="chartType" class="input_select validate[required]">
							<option value="">-请选择-</option>
							<option value="pie" <c:if test="${entity.chartType=='pie'}">selected="selected"</c:if>>饼图</option>
							<option value="column" <c:if test="${entity.chartType=='column'}">selected="selected"</c:if>>柱状图</option>
							<option value="line" <c:if test="${entity.chartType=='line'}">selected="selected"</c:if>>拆线图</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">SelectSQL：</td>
					<td class="td_table_2">
						<textarea name="selectSql" class="input_textarea_600 validate[required]">${entity.selectSql}</textarea>
					</td>
					<td class="td_table_1">InSQL：</td>
					<td class="td_table_2">
						<textarea name="inSql" class="input_textarea_600">${entity.inSql}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td_table_1">Count SQL：</td>
					<td class="td_table_2" colspan="3">
						<textarea name="countSql" class="input_textarea_600 validate[required]">${entity.countSql}</textarea>
					</td>
				</tr>
			</table>

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
								<td align=center class="td_list_1">编号</td>
								<td align=center class="td_list_1">标题</td>
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
										<input type="text" name='itemNames' value='${itemTmp.name}' class='input_120 validate[required]'>
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
			$(document).ready(function () {
				$('select[name=code]').change(function () {
					var _pre = '/${entity.type}/' + $(this).val().replace('${entity.type}_','').replace('_','/');
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
				cell.innerHTML = "<input type='text' name='itemNames' value='' class='input_120 validate[required]'>";
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
