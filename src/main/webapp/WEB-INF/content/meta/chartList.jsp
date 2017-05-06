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
			if('${metaApp.chartType}' == 'pie') {
				chart1 = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						type: '${metaApp.chartType}'
					},
					title: {
						text: '${metaApp.title}'
					},
					tooltip: {
						valueSuffix: '个'
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<b>{point.name}</b>: {point.percentage:.1f} %',
								style: {
									color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
								}
							},
							slicedOffset: 20,
							point: {
								events: {
									mouseOver: function() {
										this.slice();
									},
									mouseOut: function() {
										this.slice();
									},
									click: function() {
										return false;
									}
								}
							}
						}
					},
					series: [{
						type: 'pie',
						name: '${metaApp.subtitle}',
						data: ${data}
					}]
				});
			} else {
				chart1 = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						type: '${metaApp.chartType}'
					},
					title: {
						text: '${metaApp.title}'
					},
					xAxis: {
						categories: ${categories}
					},
					credits: {
						enabled: true
					},
					plotOptions: {
						column: {
							dataLabels: {
								enabled: true,
								inside: true
							}
						}
					},
					series: ${data}
				});
			}
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