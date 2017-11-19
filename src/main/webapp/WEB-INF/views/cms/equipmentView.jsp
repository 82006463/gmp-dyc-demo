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
					<td class="td_table_2">${empty entity.model ? 'N.A.':entity.model}</td>
					<td class="td_table_1">所属设备：</td>
					<td class="td_table_2">${empty entity.equipment ? 'N.A.':entity.equipment}</td>
				</tr>
				<tr>
					<td class="td_table_1">厂区：</td>
					<td class="td_table_2">${empty entity.factoryArea ? 'N.A.':entity.factoryArea}</td>
					<td class="td_table_1">车间：</td>
					<td class="td_table_2">${empty entity.workshop ? 'N.A.':entity.workshop}</td>
				</tr>
				<tr>
					<td class="td_table_1">所属部门：</td>
					<td class="td_table_2">${empty entity.deptName ? 'N.A.':entity.deptName}</td>
					<td class="td_table_1">所在房间：</td>
					<td class="td_table_2">${empty entity.room ? 'N.A.':entity.room}</td>
				</tr>
				<tr>
					<td class="td_table_1">使用方式：</td>
					<td class="td_table_2">${entity.calibrationMode==1 ? '常规':entity.calibrationMode==2 ? '替换':''}</td>
					<td class="td_table_1">级别：</td>
					<td class="td_table_2">${empty entity.level ? 'N.A.':entity.level}</td>
				</tr>
				<tr>
					<td class="td_table_1">功能：</td>
					<td class="td_table_2">${empty entity.func ? 'N.A.':entity.func}</td>
					<td class="td_table_1">精度：</td>
					<td class="td_table_2">${empty entity.precision ? 'N.A.':entity.precision}</td>
				</tr>
				<tr>
					<td class="td_table_1">器具类型：</td>
					<td class="td_table_2">${empty entity.type ? 'N.A.':entity.type.name}</td>
					<td class="td_table_1">校准公司：</td>
					<td class="td_table_2">${empty entity.calibrationComp ? 'N.A.':entity.calibrationComp}</td>
				</tr>
				<tr>
					<td class="td_table_1">校准规范编号：</td>
					<td class="td_table_2">${entity.calibration}</td>
					<td class="td_table_1">校准规范名称：</td>
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
					<td class="td_table_1">校准周期(月)：</td>
					<td class="td_table_2">${entity.calibrationCycle}</td>
				</tr>
				<tr>
					<td class="td_table_1">上次校准时间：</td>
					<td class="td_table_2">
						<c:if test="${empty entity.lastActualDate}">N.A.</c:if>
						<fmt:formatDate value="${entity.lastActualDate}" pattern="yyyy-MM-dd"/>
					</td>
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
