<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>德客会员管理系统</title>

    <link rel="stylesheet" href="static/css/style_login.css">
    <script src="/jquery-easyui/jquery.min.js"></script>
    <%--<script src="static/js/common_login.js"></script>--%>
    <!--背景图片自动更换-->
    <script src="static/js/supersized.3.2.7.min.js"></script>
    <script src="static/js/supersized-init.js"></script>
    <!--表单验证-->
    <script src="/static/js/plugins/validate/jquery.validate.js"></script>
    <%--登录JS--%>
    <script type="text/javascript">
        function login() {
            //使用ajax来进行登录
            $.post("/login.do", $("input").serialize(), function (data) {
                if (data.success) {
                    window.parent.location.href = "/index.do";
                } else {
                    var loginuser=$(".username").val();
                    $(".username").val('');
                    $(".password").val('');
                    $(".username").attr("placeholder","用户名")
                    $(".password").attr("placeholder","密码")
                    if(data.message=="用户名不存在!"){
                        $(".username").attr("placeholder","用户名:"+loginuser+"不存在!")
                        $(".username").addClass("invalid");
                        $(".password").removeClass("invalid");
                    }else{
                        $(".password").attr("placeholder","密码错误!")
                        $(".password").addClass("invalid");
                        $(".username").removeClass("invalid");
                    }
                }
            }, "json");
        }
        window.onload = function(){
            $(".connect p").eq(0).animate({"left":"0%"}, 1200);
            $(".connect p").eq(1).animate({"left":"0%"}, 1200);
        };
    </script>
    <%--定义invalid样式修改placeholder颜色--%>
    <style>
        .invalid:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: red;
        }

        .invalid::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: red;
        }

        input.invalid:-ms-input-placeholder{
            color: red;
        }

        input.invalid::-webkit-input-placeholder {
            color: red;
        }
    </style>
</head>
<body>

<br class="login-container">
    <h1 style="margin-top: 10%">德客会员管理系统</h1>
    <div class="connect">
        <p>欢迎光临</p>
    </div>
    <div>
        <input type="text" name="username" class="username" placeholder="用户名" value="admin" autocomplete="off"/>
    </div>
    <div>
        <input type="password" name="password" class="password" placeholder="密码" value="1" oncontextmenu="return false" onpaste="return false"/>
    </div>
    <button onclick="login()">登 陆</button></br>
    <a href="register.jsp">
        <button type="button" class="register-tis">还有没有账号？</button>
    </a>
</div>
</body>

</html>