<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html><head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>德客会员注册</title>
    <!--总样式-->
    <link rel="stylesheet" href="/static/css/index/index.css">
    <link rel="stylesheet" href="/static/css/index/common-use.css">
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/plugins/validate/jquery.validate.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#register").validate({
                //代替默认提交方式
                submitHandler:function(form){
                    //使用ajax来进行登录
                    $.post("/employee/saveOrUpdate.do",$("form").serialize(),function (data) {
                        if(data.success){
                            alert("注册成功");
                            window.parent.location.href = "/login.jsp";
                        }else{
                            alert(data.message);
                        }
                    },"json");
                },
                rules: {
                    username: {
                        required: true//必须填写
                    },
                    password: {
                        required: true
                    },
                    repassword: {
                        equalTo: "#password"//和该ID对应的元素值必须相等
                    },
                    email: {
                        required: true,
                        email: true//格式必须是emali格式
                    },
                    realname: {
                        required: true
                    },
                    tel:{
                        required: true,
                        number:true,
                        rangelength:[11,11]
                    },
                    chkAgreeLicense:{
                        required: true,
                    }
                },
                messages: {
                    username: {
                        required: "请输入账户名"//不符合要求的提示信息
                    },
                    password: {
                        required: "请输入你的密码"
                    },
                    repassword: {
                        equalTo: "密码不一致"
                    },
                    email: {
                        required: "请输入邮箱",
                        email: "请输入正确的邮箱格式"
                    },
                    realname: {
                        required: "请输入你的名字"
                    },
                    tel:{
                        required:"请输入你的电话",
                        number:"手机号必须是数字",
                        rangelength:"长度必须为11位"
                    },
                    chkAgreeLicense:{
                        required: "请接受用户协议"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo($(element).next());
                }
            })
        })
    </script>
    <style>
        .contner2 {
            width: 1000px;
        }
    </style>
    <script>
        function register() {
            $("form").submit();
        }
    </script>
    <style>
        label.error{ color:  red !important; }
    </style>
</head>

<body style="background:#F5F5F5;">
<!---这是头部 start---->
<header class="refbotheader">
    <div class="contner contner2">
        <div class="login" style="width: 210px;">

            <div class="bottom" style="padding-top: 22px;">
                <a href="/login.jsp" class="anmtin anmtin2">商户登录</a>
                <a href="#" class="anmtin bgrels">免费注册</a>
            </div>
        </div>
        <!--登录OR联系方式-->
    </div>
</header>
<!---这是头部 end------>
<!--这是内容 start-->
<section class="main-box">
    <form id="register">
    <div class="bg_reibox zhubox" style="height: 590px;">
        <div class="contner contner2">
            <div class="robox robox2">
                <div class="nafri nafri2">
                    <img class="registerbg" src="/static/image/registerbg.png">
                </div>
                <div class="nafle nafle2">
                    <h2 class="titie titie2">免费注册<span id="dealerid"></span></h2>
                    <ul>
                        <li class="font155">
                            <i class="posila"><img src="/static/image/user.png" style="width: 22px;margin-top: 3px;"></i>	<input class="tx2" name="username" placeholder="请输入账号名" type="text">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>
                        <li>
                            <i class="posila"><img src="/static/image/password.png" style="width: 22px;margin-top: 3px;"></i><input id="password" name="password" placeholder="输入您的登录密码" class="tx4 loginpassword"  type="password">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>
                        <li>
                            <i class="posila"><img src="/static/image/password.png" style="width: 22px;margin-top: 3px;"></i>	<input id="sv_ul_loginpwd2" name="repassword" placeholder="再次输入登录密码" class="tx4" type="password">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>
                        <li>
                            <i class="posila"><img src="/static/image/fe-mail.png" style="width: 22px;margin-top: 3px;"></i>	<input id="sv_us_name" name="email" class="tx1" placeholder="输入您的邮箱"  type="text">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>
                        <li class="font155">
                            <i class="posila"><img src="/static/image/user.png" style="width: 22px;margin-top: 3px;"></i>	<input id="sv_ul_name" name="realname" class="tx2" placeholder="输入你的的姓名"  type="text">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>

                        <li class="font155">
                            <i class="posila"><img src="/static/image/phone.png" style="width: 22px;margin-top: 3px;"></i>	<input id="sv_ul_mobile" name="tel" class="tx3" placeholder="输入注册手机号" type="text">
                            <div style="text-align: left;margin-left: 25px;"></div>
                        </li>
                        <li class="wty">
                            <p><input id="chkAgreeLicense" name="chkAgreeLicense" value="true"  type="checkbox">我接受并同意用户协议《服务条款》和 《私隐条款》<span></span></p>
                        </li>
                        <li class="rentn" style="padding-top: 25px;"><button id="UserRegister" >注册</button></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    </form>
</section>
<!--这是内容 end---->
<!--这是脚部 start-->
<footer class="rieifote">
    <div class="contner">
        <div class="fo2">

        </div>

    </div>
</footer>
</body></html>