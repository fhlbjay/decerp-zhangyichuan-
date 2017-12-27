<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2017/12/20
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <!-- 使用EasyUI的主题 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/gray/easyui.css">
    <!-- EasyUI的图标样式 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/icon.css">
    <%--<link rel="stylesheet" type="text/css" href="/static/css/reset.css">
    <link rel="stylesheet" type="text/css" href="/static/css/public.css">--%>
    <!-- 依赖的Jquery核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <!-- EasyUI的核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.easyui.min.js"></script>
    <!-- 国际化文件 -->
    <script type="text/javascript" src="/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" src="../../static/js/index.js"></script>

    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <style>
        td[field=text] {
            font-size: 12px !important;
        }
        .panel-title{
            color: #ffffff !important;
        }
        div.datagrid-cell{
            color: #ffffff!important;
        }
        .datagrid-body .datagrid-row-selected{
            background-color: #3c3c3c!important;
        }

    </style>
    <%--日期时间获取--%>
    <script>
        Date.prototype.Format = function (fmt) { //
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "H+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
        $(function () {
            $(".badge").text(new Date().Format("HH:mm:ss"));
            /*$(".days").text(new Date().Format("yyyy-MM-dd"));*/
            window.setInterval(function () {
                $(".badge").text(new Date().Format("HH:mm:ss"));
                /*$(".days").text(new Date().Format("yyyy-MM-dd"));*/
            }, 1000)
        })
    </script>
</head>
<body>
<%--<div class="public-header-warrp">
    <div class="public-header">
        <div class="content">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img src=""/>
            <div class="public-header-admin fr">
                <p class="admin-name"><font  color ="green">您好！<shiro:principal property="username"/></font> </p>
                <div class="public-header-fun fr">
                    <a href="/logOut" class="public-header-loginout">安全退出</a>
                </div>
            </div>
        </div>
    </div>
</div>--%>

<div class="easyui-layout" data-options="fit:true">
    <%--    <div data-options="region:'north',height:70">
            <h1 align="center">叩丁狼员工管理系统</h1>
        </div>
        <div data-options="region:'south',height:30">
            <h4 align="center">版权声明</h4>
        </div>--%>
    <div data-options="region:'west',width:200,border:false,bodyCls:'theme-left-layout'">
        <div class="theme-left-normal">
            <div class="easyui-layout" data-options="border:false,fit:true">
                <%--增加的--%>
                <div data-options="region:'north',border:false" style="height:120px;">
                    <!--start theme-left-user-panel-->
                    <div class="theme-left-user-panel">
                        <dl>
                            <dt>
                                <img src="/static/image/touxaing.png" width="43" height="43">
                            </dt>
                            <dd>
                                <b class="badge-prompt"><shiro:principal property="username"/><i class="badge color-important"></i></b>
                                <span>
                                    <shiro:hasRole name="admin">管理员</shiro:hasRole>
                                    <shiro:lacksRole name="admin">员工</shiro:lacksRole><%--<i class="days color-important" style="float: right;border-radius: 15px;width: 70px;font-size: 12px;"></i>--%>
                                </span>
                                <p>
                                    <button onclick="javascript:window.location.href='/logOut'"
                                            style="cursor: pointer;background: transparent;color: red;">注销
                                    </button>
                                </p>
                            </dd>

                        </dl>
                    </div>
                    <div style="height: 20px;width: 179px;overflow: hidden;margin-left: 15px">
                        <iframe width="220" scrolling="no" height="20" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=10&color=%23FFFFFF&bgc=%1b1b1b;&icon=1&site=12"></iframe>
                    </div>

                </div>
                <div data-options="region:'center',border:false">
                    <div class="easyui-accordion" data-options="border:false,fit:true">
                        <div title="收银功能">
                            <ul id="countTree" class="easyui-datalist" style="font-size: 12px"></ul>
                        </div>
                        <div title="会员管理">
                            <ul id="vipTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="礼品管理">
                            <ul id="giftTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="商品管理">
                            <ul id="prouductTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="库存管理">
                            <ul id="productStackTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="日常支出">
                            <ul id="dayTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="智能分析">
                            <ul id="smartTree" class="easyui-datalist"></ul>
                        </div>
                        <div title="系统菜单">
                            <ul id="systemTree" class="easyui-datalist"></ul>
                        </div>
                        <div></div>
                    </div>
                </div>
            </div>
        </div>
        <!--end theme-left-user-panel-->
    </div>
    <div data-options="region:'center'">
        <div id="myTabs">
            <div title="主页">
                <iframe src='/main.do' frameborder="0" style="width: 100%; height: 100%"></iframe>
            </div>
        </div>
    </div>
    <script>
        $(function () {
            //初始化
            $("#countTree").datalist({
                    url: 'menu/getMenus.do?id=11&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#vipTree").datalist({
                    url: 'menu/getMenus.do?id=19&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#giftTree").datalist({
                    url: 'menu/getMenus.do?id=24&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#prouductTree").datalist({
                    url: 'menu/getMenus.do?id=29&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#productStackTree").datalist({
                    url: 'menu/getMenus.do?id=33&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#dayTree").datalist({
                    url: 'menu/getMenus.do?id=15&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            //初始化
            $("#smartTree").datalist({
                    url: 'menu/getMenus.do?id=38&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
            $("#systemTree").datalist({
                    url: 'menu/getMenus.do?id=1&data=' + new Date().getMilliseconds(),
                    onClickRow: function (index, row) {
                        var target = row.url;
                        //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                        if ($("#myTabs").tabs("exists", row.text)) {
                            //console.debug(node);
                            $("#myTabs").tabs("select", row.text);
                            return;
                        }
                        $("#myTabs").tabs("add", {
                            closable: true,
                            title: row.text,
                            content: '<iframe src=' + target + ' frameborder="0" style="width: 100%; height: 100%"></iframe>',
                        })
                    }
                }
            );
        })
    </script>
</div>
</body>
</html>
