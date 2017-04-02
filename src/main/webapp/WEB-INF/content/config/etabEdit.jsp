<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-伸缩表</title>
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
		<form id="inputForm" action="${ctx }/wfc/etab/update" method="post">
			<input type="hidden" id="id" name="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-伸缩表</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">
						<frame:dict name="code" type="select" typeCode="reportType" value="${entity.code}" cssClass="input_select validate[required]"/>
					</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">
						<input type="text" id="name" name="name" value="${entity.name}" class="input_520 validate[required]" />
					</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">添加选项：</td>
					<td class="td_table_2" colspan="3">
						<input type="button" class="button_70px" value="添加选项" onclick="addItem()">
					</td>
				</tr>
				<tr>
					<td class="td_table_1">选项列表：</td>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="itemTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">编号</td>
								<td align=center class="td_list_1">名称</td>
								<td align=center class="td_list_1">是否必填</td>
								<td align=center class="td_list_1">数据类型</td>
								<td align=center class="td_list_1">渲染标签</td>
								<td align=center width=5% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${extAttrMap}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="text" name='itemCodes' value='${item.code}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<input type="text" name='itemNames' value='${item.name}' class='input_120 validate[required]'>
									</td>
									<td class="td_list_2">
										<frame:dict name="itemRequireds" type="select" typeCode="yesNo" value="${item.required}" cssClass="input_select validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="itemDataTypes" type="select" typeCode="dataType" value="${item.dataType}" cssClass="input_select validate[required]"/>
									</td>
									<td class="td_list_2">
										<frame:dict name="itemTagTypes" type="select" typeCode="tagType" value="${item.tagType}" cssClass="input_select validate[required]"/>
									</td>
									<td class="td_list_2">
										<a href='javascript:void(0)' onclick='$(this).parent().parent().remove();' class='btnDel' title='删除'>删除</a>
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
						<input type="submit" class="button_70px" name="submit"value="提交" onclick="return submitForm();">
						&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			function addItem() {
				var table = document.getElementById("itemTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='itemCodes' value='' class='input_240 validate[required]'>";
				cell.className = "td_list_2";

				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='itemNames' value='' class='input_240 validate[required]'>";
				cell.className = "td_list_2";

				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="itemDataTypes" type="select" typeCode="dataType" value="${item.dataType}" cssClass="input_select validate[required]"/>";
				cell.className = "td_list_2";

				cell = row.insertCell(-1);
				cell.innerHTML = "<frame:dict name="itemTagTypes" type="select" typeCode="tagType" value="${item.tagType}" cssClass="input_select validate[required]"/>";
				cell.className = "td_list_2";

				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='$(this).parent().parent().remove();' class='btnDel' title='删除'>删除</a>";
				cell.className = "td_list_2";
			}
		</script>
	</body>
</html>
