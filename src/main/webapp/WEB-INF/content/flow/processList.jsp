<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>流程定义</title>
	<%@ include file="/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/styles/js/table.js" type="text/javascript"></script>
</head>

<body>
	<form id="mainForm" action="${ctx}/flow/process/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">流程定义</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">
					<span>流程名称：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="name" value="${param['name']}"/>
				</td>
				<td class="td_table_1">
					<span>流程显示名称：</span>
				</td>
				<td class="td_table_2">
					<input type="text" class="input_240" name="displayName" value="${param['displayName']}"/>
				</td>
			</tr>
			<!-- 
			<tr>
				<td class="td_table_1">
					<span>状态：</span>
				</td>
				<td class="td_table_2">
					<input type="radio" name="state" value="1" ${param['state'] == null || param['state'] == 1 ? 'checked' : '' }/>启用
					<input type="radio" name="state" value="0" ${param['state'] == 0 ? 'checked' : '' }/>禁用	
				</td>
			</tr>
			 -->
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<shiro:hasPermission name="flow_process_conf">
						<input type='button' onclick="addNew('${ctx}/flow/process/conf')" class='button_70px' value='配置'/>
					</shiro:hasPermission>
					<%--<input type='button' onclick="addNew('${ctx}/flow/process/init')" class='button_70px' value='初始化'/>--%>
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td align=center width=15% class="td_list_1">名称</td>
				<td align=center width=35% class="td_list_1">流程显示名称</td>
				<td align=center width=10% class="td_list_1">状态</td>
				<td align=center width=10% class="td_list_1">版本号</td>
				<td align=center width=30% class="td_list_1">操作</td>
			</tr>
			<c:forEach items="${page.result}" var="process">
				<tr>
					<td class="td_list_2" align=left>${process.name}</td>
					<td class="td_list_2" align=left>${process.displayName}</td>
					<td class="td_list_2" align=left>${process.state == 1 ? '启用' : '禁用'}</td>
					<td class="td_list_2" align=left>${process.version}</td>
					<td class="td_list_2" align=left>
						<a href="${ctx}/flow/task/approval?processId=${process.id}" title="启动流程">启动</a>

							<%--<a href="${ctx}/flow/process/edit/${process.id }" class="btnEdit" title="编辑">编辑</a>--%>
						<c:if test="${process.state != 0}">
							<shiro:hasPermission name="flow_process_conf">
								<a href="${ctx}/flow/process/conf?processId=${process.id}&type=edit" title="配置流程">配置</a>
								<a href="${ctx}/flow/process/conf?processId=${process.id}&type=upgrade" title="升级流程：版本+1">升级</a>
								<a href="${ctx}/flow/process/conf?processId=${process.id}&type=copy" title="复制流程">复制</a>
								<a href="${ctx}/flow/process/delete/${process.id }" title="删除" onclick="return confirmDel();">删除</a>
							</shiro:hasPermission>
						</c:if>

					</td>
				</tr>
			</c:forEach>
			<frame:page curPage="${page.pageNo}" totalPages="${page.totalPages }" totalRecords="${page.totalCount }"/>
		</table>
	</form>
	</body>
</html>
