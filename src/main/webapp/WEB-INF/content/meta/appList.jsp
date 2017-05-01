<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>${metaApp.name}</title>
	<%@ include file="/common/common-list.jsp"%>
	<link rel="stylesheet" href="${ctx}/styles/plugin/css/highcharts.css" type="text/css" media="all" />
	<script type="text/javascript" src="${ctx}/styles/plugin/js/highcharts.js"></script>
	<script type="text/javascript">
		var chart1;
		$(function () {
			chart1 = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					type: '${metaApp.chartType}'
				},
				title: {
					text: '${metaApp.name}'
				},
				xAxis: {
					categories: ['Apples', 'Bananas', 'Oranges']
				},
				yAxis: {
					title: {
						text: 'Fruit eaten'
					}
				},
				series: [{
					size: '80%',
					name: 'Jane',
					data: [1, 0, 4]
				}, {
					size: '60%',
					innerSize: '60%',
					name: 'John',
					data: [5, 7, 3]
				}]
			});
		});
	</script>
</head>

<body>
	<form id="mainForm" action="${ctx}${metaApp.list_url}" method="get">
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">${metaApp.name}</td>
			</tr>
		</table>
		${jsonSearch}
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<input type='submit' class='button_70px' value='查询'/>
				</td>
			</tr>
		</table>
		<div id="container" style="min-width: 400px; height: 400px;"></div>
	</form>
</body>
</html>
