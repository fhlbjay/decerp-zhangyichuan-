<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>德客会员管理系统</title>
    <link rel="stylesheet" href="static/css/style_login.css">
    <script src="/jquery-easyui/jquery.min.js"></script>
    <script src="static/js/common_login.js"></script>
    <!--背景图片自动更换-->
    <script src="static/js/supersized.3.2.7.min.js"></script>
    <script src="static/js/supersized-init.js"></script>
    <!--表单验证-->
    <script src="/static/js/plugins/validate/jquery.validate.js"></script>
	<script>
		function registerSubmit() {
            //使用ajax来进行登录
			$("form").submit();
        }
	</script>
</head>
<body>

<div class="register-container">
	<h1>德客会员管理系统注册</h1>
	
	<div class="connect">
		<p>请注册</p>
	</div>
	
	<form id="register">
		<div>
			<input type="text" name="username" class="username loginForm" placeholder="您的用户名" autocomplete="off"/>
		</div>
		<div>
			<input type="password" name="password" class="password loginForm" placeholder="输入密码" oncontextmenu="return false" onpaste="return false" />
		</div>
		<div>
			<input type="password" name="confirm_password" class="confirm_password" placeholder="再次输入密码" oncontextmenu="return false" onpaste="return false" />
		</div>
		<div>
			<input type="text" name="tel" class="phone_number loginForm" placeholder="输入手机号码" autocomplete="off" id="number"/>
		</div>
		<div>
			<input type="email" name="email" class="email loginForm" placeholder="输入邮箱地址" oncontextmenu="return false" onpaste="return false" />
		</div>
	</form>
	<button onclick="registerSubmit()">注 册</button></br>
	<a href="login.jsp">
		<button type="button" class="register-tis">已经有账号？</button>
	</a>

</div>

</body>
</html>