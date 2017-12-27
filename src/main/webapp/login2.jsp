<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>德客会员管理登录</title>
    <!--总样式-->
    <link rel="stylesheet" href="/static/css/index/index.css">
    <link rel="stylesheet" href="/static/css/index/common-use.css">
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <style>
        body {
            width: 100%;
            overflow: hidden;
        }

        .aaa {
            background: #b9b9b9;
        }

        .bbb {
            background: #4c83d9 !important;
            transition: all 0.3s;
        }

        .ccc {
            background: #5e5ca8 !important;
            transition: all 0.3s;
        }

        .ddd {
            opacity: 1 !important;
            z-index: 15;
        }
    </style>
    <script type="text/javascript">
        function login(){
            //使用ajax来进行登录
            $.post("/login.do",$("form").serialize(),function (data) {
                if(data.success){
                    window.parent.location.href = "/index.do";
                }else{
                    alert(data.message);
                }
            },"json");
        }
    </script>
</head>
<body style="background: #f5f5f5;">
<!--这是头部 start-->
<header class="refbotheader">
    <div class="contner contner2">
        <div class="login" style="width: 210px;">
            <div class="bottom" style="padding-top: 22px;">
                <a href="#" class="anmtin anmtin2 bgrels">商户登录</a>
                <a href="/register.jsp" class="anmtin anmtin2">免费注册</a>
            </div>
        </div>
    </div>
</header>
<section class="main-box">
    <div id="beg_efffff" class="bg_reibox bglogin" style="height:500px;">
        <div class="contner contner2" style="position:relative;">
            <div class="nobing nobing2" style="position:absolute;">
                <img src="/static/image/logerleft.png" class="ddd">
            </div>
            <form method="post">
                <div class="terbox" id="form_login" style="z-index:22">
                    <div class="txgg">账号登录</div>
                    <ul>
                        <li class="font155"><input  name="username" value="admin" class="tx1" placeholder="请输入您的账号" type="text"></li>
                        <li><input placeholder="请输入您的密码" name="password" value="1" class="tx1" type="password"></li>
                        <li class="lotbtn"><a href="javascript:" id="userLogin" onclick="login()">登录</a></li>
                        <li class="lotbtn"><a href="javascript:window.location.href='/register.jsp'" id="userRegist">注册</a></li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</section>

</body>
</html>