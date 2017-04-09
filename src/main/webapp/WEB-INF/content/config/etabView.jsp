<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>配置管理-伸缩表</title>
		<%@ include file="/common/common-view.jsp"%>
	</head>

	<body>
		<form id="inputForm">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">配置管理-伸缩表</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
				<tr>
					<td class="td_table_1">编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
			</table>

			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">选项列表：</td>
					<td class="td_table_2" colspan="3">
						<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" id="itemTable" style="margin: 0">
							<tr>
								<td align=center class="td_list_1">字段</td>
								<td align=center class="td_list_1">类属性</td>
								<td align=center class="td_list_1">描述</td>
								<td align=center class="td_list_1">数据类型</td>
								<c:if test="${type == 'search'}">
									<td align=center class="td_list_1">比较条件</td>
									<td align=center class="td_list_1">标签类型</td>
								</c:if>
								<c:if test="${type == 'edit'}">
									<td align=center class="td_list_1">标签类型</td>
									<td align=center class="td_list_1">是否必填</td>
									<td align=center class="td_list_1">子表单</td>
									<td align=center class="td_list_1">模糊搜索</td>
								</c:if>
								<td align=center width=6% class="td_list_1">操作</td>
							</tr>
							<c:forEach items="${type=='search' ? jsonSearchMap : type=='list' ? jsonListMap : jsonEditMap}" var="item" varStatus="itemIndex">
								<tr>
									<td class="td_list_2">${item.code}</td>
									<td class="td_list_2">${item.name}</td>
									<td class="td_list_2">${item.desc}</td>
									<td class="td_list_2">${item.dataType}</td>
									<c:if test="${type == 'search'}">
										<td class="td_list_2">${item.compare}</td>
										<td class="td_list_2">${item.tagType}</td>
									</c:if>
									<c:if test="${type == 'edit'}">
										<td class="td_list_2">${item.tagType}</td>
										<td class="td_list_2">${item.required}</td>
										<td class="td_list_2">${item.subForm}</td>
										<td class="td_list_2">${item.fuzzy}</td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>

			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<tr align="left">
					<td colspan="1">
						<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
