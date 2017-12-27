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

    <script type="text/javascript" src="/static/js/department.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<table id="emp_datagrid"></table>

<div id="emp_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="add()">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain=true   onclick="edit()">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  onclick="remove()">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  onclick="reload()">刷新</a>
</div>
<div id="emp_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true onclick="save()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   onclick="cancel()">取消</a>
</div>

<!-- 表单弹出框 -->
<div id="emp_dialog">
    <form id="emp_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>部门名称:</td>
                <td>
                    <input type="text" name="name" class="easyui-textbox" prompt="请输入部门名称"/>
                </td>
            </tr>
            <tr>
                <td>编码:</td>
                <td>
                    <input type="text" name="sn" class=" easyui-textbox" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
