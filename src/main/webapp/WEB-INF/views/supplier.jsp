<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>Title</title>
    <!-- 使用EasyUI的主题 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/gray/easyui.css">
    <!-- EasyUI的图标样式 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/icon.css">
    <!-- 依赖的Jquery核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <!-- EasyUI的核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.easyui.min.js"></script>
    <!-- 国际化文件 -->
    <script type="text/javascript" src="/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>


    <script type="text/javascript" src="/static/js/supplier.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<table id="sup_datagrid"></table>

<div id="sup_toolbar">
    <shiro:hasPermission name="supplier:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true  data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="delete" id="delete_btn">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  data-cmd="reload">刷新</a>

    <input id="sup_search" type="text" name="keyword"/>
</div>
<div id="sup_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<!-- 表单弹出框 -->
<div id="sup_dialog">
    <form id="sup_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>供应商<span style="color: red;">*</span></td>
                <td>
                    <input  name="name" class="easyui-textbox" data-options="required:true" prompt="请输入供应商名称"/>
                </td>
            </tr>
            <tr>
                <td>联系人<span style="color: red;">*</span></td>
                <td>
                    <input  name="linkman" class="easyui-textbox" data-options="required:true" prompt="请输入联系人"/>
                </td>
            </tr>
            <tr>
                <td>联系电话<span style="color: red;">*</span></td>
                <td>
                    <input  name="tel" class="easyui-numberbox" data-options="validType:'length[1,11]',required:true" prompt="请输入联系电话"/>
                </td>
            </tr>
            <tr>
                <td>QQ号码</td>
                <td>
                    <input  name="qqNumber" class="easyui-numberbox" prompt="请输入QQ号码"/>
                </td>
            </tr>
            <tr>
                <td>客户地址</td>
                <td>
                    <input name="address" class="easyui-textbox" prompt="请输入地址"/>
                </td>
            </tr>
            <tr>
                <td>备注信息</td>
                <td>
                    <input name="remark" class="easyui-textbox" data-options="validType:'length[0,50]'" prompt="最多输入50字"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
