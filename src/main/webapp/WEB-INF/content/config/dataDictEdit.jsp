<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-数据字典</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/config/dictionary/update" method="post">
			<input type="hidden" name="id" id="id" value="${entity.id}"/>
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-数据字典</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">数据字典编号：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_520" id="code" name="code" value="${entity.code}" />
					</td>
					<td class="td_table_1">数据字典名称：</td>
					<td class="td_table_2" colspan="3">
						<input type="text" class="input_520" id="name" name="name" value="${entity.name}" />
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
								<td align=center width=25% class="td_list_1">选项编号</td>
								<td align=center width=60% class="td_list_1">选项名称</td>
								<td align=center width=10% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${entity.items}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">
										<input type="hidden" value='${item.id}' name='itemIds' class='input_50'>
										<input type="text" value='${item.code}' name='itemCodes' class='input_520'>
									</td>
									<td class="td_list_2">
										<input type="text" value='${item.name}' name='itemNames' class='input_520'>
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
						<input type="submit" class="button_70px" name="submit" value="提交">
						&nbsp;&nbsp;
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
			
		<script type="text/javascript">
			function addItem() {
				var table = document.getElementById("itemTable");
				var row = table.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.innerHTML = "<input type='hidden' name='itemIds' value='' class='input_50'><input type='text' name='itemCodes' value='' class='input_520'>";
				cell.className = "td_list_2";
				
				cell = row.insertCell(-1);
				cell.innerHTML = "<input type='text' name='itemNames' value='' class='input_520'>";
				cell.className = "td_list_2";
				
				cell = row.insertCell(-1);
				cell.innerHTML = "<a href='javascript:void(0)' onclick='$(this).parent().parent().remove();' class='btnDel' title='删除'>删除</a>";
				cell.className = "td_list_2";
			}
			
			function validateInput(){
					var table = document.getElementById("itemTable");
					var rowLen = table.rows.length;
					if(rowLen == 0) {
						alert("请添加选项");
						return false;
					}
					var warning = "";
					$("input[name='itemNames']").each(function(){
						var item = $(this).val();
						if(item == '') {
							warning = "选项列表 不能为空";
						}
					});
					if(warning != '') {
						alert(warning);
						return false;
					}
			}
		</script>
		</form>
	</body>
</html>
