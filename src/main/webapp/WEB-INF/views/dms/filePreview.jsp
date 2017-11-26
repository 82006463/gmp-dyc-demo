<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<script type="text/javascript" src="${ctx}/styles/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		$(function () {
			alert($('#preview').text());
		});
	</script>
</head>

<body>
	<iframe id="preview" src="${ctx}/custom/dms/file/up/preview/${id}" width="100%" height="100%"></iframe>
</body>
</html>
