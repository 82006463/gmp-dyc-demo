<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>德亦诚全面质量管理平台</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link href="${ctx}/styles/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/styles/css/login.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/styles/bootstrap/2.2.2/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		if(window != top) {
			top.location.href = location.href;
		}
	</script>
</head>

<body style="background-image:url(styles/images/dyc-bg.jpg); background-size:100% 100%;-moz-background-size:100% 100%;">
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
				</a>
				<a class="brand" href="./" style="font-weight: bold; ">德亦诚全面质量管理平台</a>
				<div class="nav-collapse">
					<!-- 
						<ul class="nav pull-right">
							<li class="">
								<a href="javascript:;"><i class="icon-chevron-left"></i> </a>
							</li>
						</ul>
						 -->
				</div>
			</div>
		</div>
	</div>

	<div id="login-container">
		<div id="login-header">
			<h3>登录</h3>
		</div>
		<div id="login-content" class="clearfix">
			<form action="${ctx}/login" method="post">
				<fieldset>
					<div class="control-group">
						<label class="control-label" for="username">账号：</label>
						<div class="controls">
							<input type="text" class="" id="username" name="username" value="superadmin" autocomplete="off"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">密码：</label>
						<div class="controls">
							<input type="password" class="" id="password" name="password" value="123456"/>
						</div>
					</div>
				</fieldset>

				<div id="remember-me" class="pull-left" style="display: none;">
					<input type="checkbox" name="remember" id="remember" value="on"/>
					<label id="remember-label" for="remember">记住我</label>
				</div>

				<div class="pull-right">
					<button type="submit" class="btn btn-warning btn-large">登录</button>
				</div>
			</form>
		</div>

		<div id="login-extra">
<!-- 	<p>
				没有账号? <a href="javascript:;">注册</a>
			</p> -->
			<p>
				忘记密码? <a href="forgot_password.html">找回密码</a>
			</p>
		</div>
	</div>
</body>
</html>