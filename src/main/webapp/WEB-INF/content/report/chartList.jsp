<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>图表管理-${etab.name}</title>
	<%@ include file="/common/common-list.jsp"%>
	<script type="text/javascript">
		var Chart = {}
		Chart.draw = function () {

		}
	</script>
</head>
<body>
	<form id="mainForm" action="${ctx}/dyc/chart/list" method="get">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}"/>
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}"/>
		<input type="hidden" name="order" id="order" value="${page.order}"/>
		<input type="hidden" class="input_240" name="type" value="${param['type']}"/>
		<input type="hidden" class="input_240" name="view" value="${param['view']}"/>
		${jsonSearch}
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
			${jsonList}
		</table>
	</form>
</body>
</html>
