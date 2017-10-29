<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>器具</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="inputForm" action="" method="post">
			<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
				<tr>
					<td class="td_table_top" align="center">器具</td>
				</tr>
			</table>
			<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_table_1">器具编号：</td>
					<td class="td_table_2">${entity.code}</td>
					<td class="td_table_1">器具名称：</td>
					<td class="td_table_2">${entity.name}</td>
				</tr>
				<tr>
					<td class="td_table_1">型号：</td>
					<td class="td_table_2">${entity.model}</td>
					<td class="td_table_1">出厂编号：</td>
					<td class="td_table_2">${entity.factoryCode}</td>
				</tr>
				<tr>
					<td class="td_table_1">所在房间：</td>
					<td class="td_table_2">${entity.room}</td>
					<td class="td_table_1">所属设备：</td>
					<td class="td_table_2">${entity.equipment}</td>
				</tr>
				<tr>
					<td class="td_table_1">功能：</td>
					<td class="td_table_2">${entity.func}</td>
					<td class="td_table_1">精度：</td>
					<td class="td_table_2">${entity.precision}</td>
				</tr>

				<tr>
					<td class="td_table_1">校准：</td>
					<td class="td_table_2">${entity.calibration}</td>
					<td class="td_table_1">校准名称：</td>
					<td class="td_table_2">${entity.calibrationName}</td>
				</tr>
				<tr>
					<td class="td_table_1">测量范围上限：</td>
					<td class="td_table_2">${entity.measureRangeMin}</td>
					<td class="td_table_1">测量范围下限：</td>
					<td class="td_table_2">${entity.measureRangeMax}</td>
				</tr>
				<tr>
					<td class="td_table_1">校准方式：</td>
					<td class="td_table_2">${entity.calibrationMode}</td>
					<td class="td_table_1">校准周期：</td>
					<td class="td_table_2">${entity.calibrationCycle}</td>
				</tr>
				<tr>
					<td class="td_table_1">上次校准时间：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.lastExpectDate}" pattern="yyyy-MM-dd"/></td>
					<td class="td_table_1">待校准时间：</td>
					<td class="td_table_2"><fmt:formatDate value="${entity.expectDate}" pattern="yyyy-MM-dd"/></td>
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
