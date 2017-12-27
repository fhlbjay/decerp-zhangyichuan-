<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <script type="text/javascript" src="/static/js/menu.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<table id="emp_datagrid"></table>

<div id="emp_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain=true  data-cmd="edit">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="changeState" id="changState_btn">设置离职</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  data-cmd="reload">刷新</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-redo" data-cmd="nextmenu">下级菜单</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-undo" data-cmd="prevmenu">上级菜单</a>
</div>
<div id="emp_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>

<!-- 表单弹出框 -->
<div id="emp_dialog">
    <form id="emp_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>菜单姓名:</td>
                <td>
                    <input  name="text" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>url:</td>
                <td>
                    <input  name="url" class="easyui-textbox"  />
                </td>
            </tr>
            <tr>
                <td>请选择父菜单:</td>
                <td>
                    <input type="text" name="parent.id"  class="easyui-combobox"
                           data-options="url:'/menu/selectAll.do',textField:'text',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
