<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>Title</title>
    <!-- 使用EasyUI的主题 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/gray/easyui.css">
    <!-- EasyUI的图标样式 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/css/vip/vip.css">
    <!-- 依赖的Jquery核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <!-- EasyUI的核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.easyui.min.js"></script>
    <!-- 国际化文件 -->
    <script type="text/javascript" src="/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" src="/static/js/vip.js"></script>

    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<div style="height: 29%;width:24%;float: left">
    <div class="easyui-panel" title="今日过生日的会员" data-options=
            "fit:true" style="margin:auto;width: 145px;">

                <div style="margin:auto;width: 145px">今日过生日的会员:</div>
                <div style="margin:auto;width: 145px ;padding-left: 20px"><i  id="usercount" style="font-size: 50px;color: red"></i>位</div>
                <div style="margin:auto;width: 145px">本月还有<i id="usercount1" style="color: #0ca3d2;font-size: 20px"></i>位会员过生日</div>

    </div>
</div>
<div style="height: 29%;width:25%;float: left">
    <div class="easyui-panel" title="会员总数" data-options=
            "fit:true" style="margin:auto;width: 145px;">
        <div  id="" style="margin:auto;width: 145px">
            <span >会员总数:</span>
        </div>
        <div style="margin:auto;width: 145px;padding-left: 20px"><i  id="usercount3" style="font-size: 50px;color: red"></i>位</div>
        <div style="margin:auto;width: 145px">
            <span class="fr">储值卡总额:<i  id="blanceamount" style="color: #0ca3d2;font-size: 20px"></i></span>
        </div>

    </div>
</div>
<div style="height: 29%;width:25%;float: left">
    <div class="easyui-panel" title="累计消费" data-options=
            "fit:true" style="margin:auto;width: 180px;">
        <div  style="margin:auto;width: 145px">累计消费:</div>
        <div style="margin:auto;width: 180px">
            <span id="consumptionamount" style="font-size: 45px;color: red"></span>元
        </div>
        <div style="margin:auto;width: 200px">
            <i  id="usercount2" style="color: #0ca3d2;"></i>位会员,消费订单<i id="ordercount" style="color: #0ca3d2"></i>笔，平均消费<i  id="average" style="color: #0ca3d2;"></i>元
        </div>
    </div>
</div>
<div style="height: 29%;width:25%;float: left">
    <div class="easyui-panel" title="消费会员(top3):" data-options=
            "fit:true" style="margin:auto;width: 145px;padding-top: 10px">
        <div style="margin:auto;width: 145px">消费会员(TOP3)</div>
        <div style="margin:auto;width: 145px">
            <ul type="none">
                <li id="0" style="color: #0ca3d2;font-size: 15px">张老三</li>
                <li id="1" style="color: #0ca3d2;font-size: 15px">崔老四</li>
                <li id="2" style="color: #0ca3d2;font-size: 15px">小李敏</li>
            </ul>
        </div>
    </div>
</div>
<div style="height: 70%;width:100%">
    <table id="emp_datagrid"></table>
</div>
<div id="emp_toolbar">
    <shiro:hasPermission name="employee:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true  data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="changeState" id="changState_btn">设置状态</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  data-cmd="reload">刷新</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-redo" data-cmd="exportXls">导出</a>
    <%--<a class="easyui-linkbutton" plain="true" iconCls="icon-undo" data-cmd="importXls">导入</a>--%>
    <input class="easyui-textbox" name="keyword" id="keyword" data-options="
    prompt:'请输入卡号或手机号'"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchForm">搜索</a>
</div>
<div id="emp_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div id="updownload_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="updownload">上传</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div class="easyui-dialog" data-options="title:'文件导入'" id="up-download-dialog">
    <form action="/employee/importXls.do" method="post" enctype="multipart/form-data" id="updownloadForm">
        <input type="file" name="file">
    </form>
</div>
<!-- 表单弹出框 -->
<div id="emp_dialog">
    <form id="emp_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>会员卡号:</td>
                <td>
                    <input  name="vipcard.sn" class="easyui-textbox sn" id="sn" />
                </td>
            </tr>
            <tr>
                <td>会员姓名:</td>
                <td>
                    <input  name="name" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>会员等级:</td>
                <td>
                    <select id="cc" class="easyui-combobox" name="vipgrade" style="width:128px;" data-options="panelHeight:'auto'">
                        <option>普通会员</option>
                       <%-- <option>青铜会员</option>
                        <option>白银会员</option>
                        <option>黄金会员</option>
                        <option>白金会员</option>
                        <option>钻石会员</option>--%>
                    </select>

                </td>
            </tr>
            <tr id="password">
                <td>密码:</td>
                <td>
                    <input  name="password" class="easyui-passwordbox" />
                </td>
            </tr>
            <tr>
                <td>电话:</td>
                <td>
                    <input  name="tel" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>QQ:</td>
                <td>
                    <input  name="qq" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>微信:</td>
                <td>
                    <input  name="weixin" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td>
                    <input  name="email" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>会员生日:</td>
                <td>
                    <input  name="birthday" class="easyui-datebox" />
                </td>
            </tr>

            </tbody>
        </table>
    </form>
</div>
</body>
</html>
