<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>月度${fn:contains(type,'Ext') ? '外校':fn:contains(type,'In') ? '内校':fn:contains(type,'Tmp') ? '临校':''}${status==2 ? '任务':''}</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
		<script type="text/javascript">
			function generateTask(item) {
				$.getJSON('${ctx}/custom/cms/${type}/generateTask',{}, function (data) {
					alert(data.msg);
				});
			}
			function sendask(item) {
				if($('#measureCompId').val()=='') {
					alert('请选择计量公司');
				} else if($('#approver').val()=='') {
					alert('请输入计量公司负责人');
				} else {
					$.getJSON('${ctx}/custom/cms/${type}/sendask',{}, function (data) {
						alert(data.msg);
					});
				}
			}
		</script>
	</head>

	<body>
	<form id="mainForm" action="${ctx}/custom/cms/${type}/${status}" method="get">
		<input type="hidden" name="lookup" value="${lookup}" />
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">月度${fn:contains(type,'Ext') ? '外校':fn:contains(type,'In') ? '内校':fn:contains(type,'Tmp') ? '临校':''}${status==2 ? '任务':''}</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<c:if test="${status == 2}">
				<tr>
					<td class="td_table_1">计量公司：</td>
					<td class="td_table_2">
						<select id="measureCompId" name="measureCompId" class="input_select">
							<option value="">-请选择-</option>
							<c:forEach items="${measureComps}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_table_1">计量公司负责人：</td>
					<td class="td_table_2">
						<input type="text" id="approver" name="approver" class="input_240" value=""/>
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="td_table_2" align="center" colspan="4">
					<c:if test="${status != 2}">
						<input type="button" class='button_70px' value="生成任务" onclick="generateTask(this);"/>
					</c:if>
					<c:if test="${status == 2}">
						<input type="button" class='button_70px' value="发送任务" onclick="sendask(this);"/>
						<input type="button" class='button_70px' value="导出Excel"/>
					</c:if>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align=center width=5% class="td_list_1">序号</td>
				<td align=center width=25% class="td_list_1">器具编号</td>
				<td align=center width=35% class="td_list_1">器具名称</td>
				<td align=center width=20% class="td_list_1">所在房间</td>
				<td align=center width=10% class="td_list_1">待校准日期</td>
				<td align=center width=5% class="td_list_1">状态</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="index">
				<tr>
					<td class="td_list_2" align=left>${index.count}</td>
					<td class="td_list_2" align=left>${item.equipment.code}</td>
					<td class="td_list_2" align=left>${item.equipment.name}</td>
					<td class="td_list_2" align=left>${item.equipment.room}</td>
					<td class="td_list_2" align=left><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_list_2" align=left>${item.status == 0 ? '删除':item.status > 0 ? '正常':''}</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages}" totalRecords="${page.totalCount}" lookup="${lookup}"/>
		</table>
	</form>
	</body>
</html>
