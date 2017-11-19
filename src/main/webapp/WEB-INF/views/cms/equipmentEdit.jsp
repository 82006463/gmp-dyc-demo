<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>器具</title>
	<%@ include file="/common/common-edit.jsp"%>
	<script type="text/javascript" src="${ctx}/styles/js/ops_base.js"></script>
	<script type="text/javascript">
		function check() {
			var result = false;
			var _measureRangeMin = $('#measureRangeMin').val();
            var _measureRangeMax = $('#measureRangeMax').val();
			if(Ops.submit()) {
			    if (parseInt(_measureRangeMin) > parseInt(_measureRangeMax)) {
					alert('使用范围下限不能大于使用范围上限');
				} else {
                    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '${ctx}/custom/cms/equipment/check',
                        data: {id:$('#id').val(), code:$('#code').val()},
                        success: function(data){
                            if(data.result == 1){
                                result = true;
                            } else {
                                alert(data.msg);
                            }
                        }
                    });
				}
			}
			return result;
		}

		function updateExpectDate() {
			var _cycle = $('#calibrationCycle').val();
			var _usageMode = $('[name=usageMode]:checked').val();
			var _lastActualDate = $('#lastActualDate').val();
			if(_lastActualDate) {
			    var _date = new Date(_lastActualDate);
			    if(_usageMode==1) {
                    _date.setMonth(_date.getMonth() + parseInt(_cycle));
				} else {
                    _date.setMonth(_date.getMonth() + parseInt(_cycle)*2);
				}
                _date.setDate(_date.getDate()-1);
                var _month = _date.getMonth()+1;
                if(_month < 10)
                    _month = '0' + _month;
                var _day = _date.getDate();
                if(_day < 10)
                    _day = '0' + _day;
			    $('#expectDate').val(_date.getFullYear() + '-' + _month + '-' + _day);
			}
        }
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/custom/cms/equipment/update" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id}"/>
		<input type="hidden" id="status" name="status" value="${entity.status}"/>
		<input type="hidden" id="tmpStatus" name="tmpStatus" value="${entity.tmpStatus}"/>

		<input type="hidden" id="tenantId" name="tenantId" value="${entity.tenantId}"/>
		<input type="hidden" id="createrId" name="createrId" value="${entity.createrId}"/>
		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<input type="hidden" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<table width="100%" border="0" align="center" cellpadding="0" class="table_all_border" cellspacing="0" style="margin-bottom: 0px;border-bottom: 0px">
			<tr>
				<td class="td_table_top" align="center">器具</td>
			</tr>
		</table>
		<table class="table_all" align="center" border="0" cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr>
				<td class="td_table_1">器具编号<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="code" name="code" class="input_240 validate[required,minSize[1],maxSize[50]]" value="${entity.code}" <c:if test="${entity.status==3 || !empty entity.id}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">器具名称<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="name" name="name" class="input_240 validate[required,minSize[1],maxSize[100]]" value="${entity.name}" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">生产厂家：</td>
				<td class="td_table_2">
					<input type="text" id="factory" name="factory" value="${entity.factory}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">出厂编号：</td>
				<td class="td_table_2">
					<input type="text" id="factoryCode" name="factoryCode" value="${entity.factoryCode}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">型号：</td>
				<td class="td_table_2">
					<input type="text" id="model" name="model" value="${entity.model}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">所属设备：</td>
				<td class="td_table_2">
					<input type="text" id="equipment" name="equipment" value="${entity.equipment}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">厂区：</td>
				<td class="td_table_2">
					<input type="text" id="factoryArea" name="roofactoryAream" value="${entity.factoryArea}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">车间：</td>
				<td class="td_table_2">
					<input type="text" id="workshop" name="workshop" value="${entity.workshop}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">所属部门：</td>
				<td class="td_table_2">
					<input type="text" id="deptName" name="deptName" value="${entity.deptName}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">所在房间：</td>
				<td class="td_table_2">
					<input type="text" id="room" name="room" value="${entity.room}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">使用方式：</td>
				<td class="td_table_2">
					<c:if test="${entity.status!=3}">
						<input type="radio" id="usageMode1" name="usageMode" value="1" class="validate[required]" ${empty entity.usageMode || entity.usageMode==1 ? 'checked="checked"':''} onclick="updateExpectDate();"/>&nbsp;常规
						<input type="radio" id="usageMode2" name="usageMode" value="2" class="validate[required]" ${entity.usageMode==2 ? 'checked="checked"':''} onclick="updateExpectDate();"/>&nbsp;替换
					</c:if>
					<c:if test="${entity.status==3}">
						<input type="hidden" name="usageMode" value="${entity.usageMode}" />
						${entity.usageMode==1 ? '常规':entity.usageMode==2 ? '替换':''}
					</c:if>
				</td>
				<td class="td_table_1">级别：</td>
				<td class="td_table_2">
					<input type="text" id="level" name="level" value="${entity.level}" class="input_240 validate[maxSize[10]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">功能：</td>
				<td class="td_table_2">
					<input type="text" id="func" name="func" value="${entity.func}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">精度：</td>
				<td class="td_table_2">
					<input type="text" id="precision" name="precision" value="${entity.precision}" class="input_240 validate[maxSize[20]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">器具类型：</td>
				<td class="td_table_2">
					<c:if test="${entity.status!=3}">
						<select name="typeId" class="input_select">
							<option value="">-请选择-</option>
							<c:forEach items="${equipTypes}" var="et">
								<option value="${et.id}" ${et.id==entity.typeId ? 'selected="selected"':''}>${et.name}</option>
							</c:forEach>
						</select>
					</c:if>
					<c:if test="${entity.status==3}">
						<input type="hidden" name="typeId" value="${entity.typeId}" />
						${empty entity.type ? 'N.A.':entity.type.name}
					</c:if>
				</td>
				<td class="td_table_1">校准公司：</td>
				<td class="td_table_2">
					<input type="text" id="calibrationComp" name="calibrationComp" value="${entity.calibrationComp}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">校准规范编号：</td>
				<td class="td_table_2">
					<input type="text" id="calibration" name="calibration" value="${entity.calibration}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">校准规范名称：</td>
				<td class="td_table_2">
					<input type="text" id="calibrationName" name="calibrationName" value="${entity.calibrationName}" class="input_240 validate[maxSize[50]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">使用范围下限：</td>
				<td class="td_table_2">
					<input type="text" id="measureRangeMin" name="measureRangeMin" value="${entity.measureRangeMin}" class="input_240 validate[custom[number]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
				<td class="td_table_1">使用范围上限：</td>
				<td class="td_table_2">
					<input type="text" id="measureRangeMax" name="measureRangeMax" value="${entity.measureRangeMax}" class="input_240 validate[custom[number]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if>/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">校准方式<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<c:if test="${entity.status!=3}">
						<input type="radio" id="calibrationMode1" name="calibrationMode" value="1" class="validate[required]" ${entity.calibrationMode==1 ? 'checked="checked"':''}/>&nbsp;内校
						<input type="radio" id="calibrationMode2" name="calibrationMode" value="2" class="validate[required]" ${entity.calibrationMode==2 ? 'checked="checked"':''}/>&nbsp;外校
					</c:if>
					<c:if test="${entity.status==3}">
						<input type="hidden" name="calibrationMode" value="${entity.calibrationMode}" />
						${entity.calibrationMode==1 ? '内校':entity.calibrationMode==2 ? '外校':''}
					</c:if>
				</td>
				<td class="td_table_1">校准周期(月)<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<input type="text" id="calibrationCycle" name="calibrationCycle" value="${entity.calibrationCycle}" class="input_240 validate[required,custom[number]]" <c:if test="${entity.status==3}">readonly="readonly"</c:if> onblur="updateExpectDate();"/>
				</td>
			</tr>
			<tr>
				<td class="td_table_1">上次校准时间<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<c:if test="${empty entity.lastActualDate}">N.A.</c:if>
					<c:if test="${!empty entity.lastActualDate}">
						<fmt:formatDate value="${entity.lastActualDate}" pattern="yyyy-MM-dd"/>
						<input type="hidden" id="lastActualDate" name="lastActualDate" value="<fmt:formatDate value="${entity.lastActualDate}" pattern="yyyy-MM-dd"/>" class="input_240" readonly="readonly"/>
					</c:if>
				</td>
				<td class="td_table_1">待校准时间<b class="requiredWarn">*</b>：</td>
				<td class="td_table_2">
					<c:if test="${entity.status!=3}">
						<c:if test="${empty entity.lastActualDate}">
							<input type="text" id="expectDate" name="expectDate" value="<fmt:formatDate value="${entity.expectDate}" pattern="yyyy-MM-dd"/>" class="input_240 validate[required]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'%y-%M-{%d+1}'});" readonly="readonly"/>
						</c:if>
						<c:if test="${!empty entity.lastActualDate}">
							<input type="text" id="expectDate" name="expectDate" value="<fmt:formatDate value="${entity.expectDate}" pattern="yyyy-MM-dd"/>" class="input_240" readonly="readonly"/>
						</c:if>
					</c:if>
					<c:if test="${entity.status==3}">
						<input type="text" id="expectDate" name="expectDate" value="<fmt:formatDate value="${entity.expectDate}" pattern="yyyy-MM-dd"/>" class="input_240" readonly="readonly"/>
					</c:if>
				</td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0">
			<tr align="left">
				<td colspan="1">
					<c:if test="${entity.status!=3}">
						<shiro:hasPermission name="cms_equipment_edit">
							<input type="hidden" id="updaterId" name="updaterId" value="${userId}"/>
							<input type="submit" class="button_70px" name="submit" value="暂存" onclick="$('#status').val(1); return check();">&nbsp;&nbsp;
							<input type="submit" class="button_70px" name="submit" value="提交" onclick="$('#status').val(3); return check();">
						</shiro:hasPermission>
					</c:if>
					<c:if test="${entity.status==3 && userId!=entity.updaterId}">
						<shiro:hasPermission name="cms_equipment_review">
							<input type="hidden" id="updaterId" name="updaterId" value="${entity.updaterId}"/>
							<input type="submit" class="button_70px" name="submit" value="拒绝" onclick="$('#status').val(2); return check();">&nbsp;&nbsp;
							<input type="submit" class="button_70px" name="submit" value="复核" onclick="$('#status').val(4); return check();">
						</shiro:hasPermission>
					</c:if>
					<input type="button" class="button_70px" name="reback" value="返回" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
