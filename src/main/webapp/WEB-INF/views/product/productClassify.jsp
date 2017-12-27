<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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

    <script type="text/javascript" src="/static/js/productClassify/productRoot.js"></script>
    <script type="text/javascript" src="/static/js/productClassify/productParent.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<div id="cc" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',title:'一级分类',split:true" style="width:200px;">
        <table id="root_datagrid"></table>
    </div>
    <div data-options="region:'center',title:'分类列表'" style="padding:5px;background:#eee;">
        <div id="pa1" style="height: 450px;width: auto">
            <table id="parentAll_datagrid"></table>
        </div>
        <div id="pa2" style="height: 450px;width: auto">
            <table id="parent_datagrid"></table>
        </div>
    </div>
</div>
</div>
<div id="root_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain=true data-cmd="edit">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true data-cmd="remove">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-more" plain=true data-cmd="reload">所有分类</a>
</div>
<div id="parent_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="add_parent()">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain=true onclick="edit_parent()">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true onclick="remove_parent()">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true onclick="reload_parent()">刷新</a>
</div>
<div id="parent_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true onclick="save_parent()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="cancel_parent()">取消</a>
</div>
<div id="root_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true data-cmd="cancel">取消</a>
</div>
<!-- 表单弹出框 -->
<div id="root_dialog">
    <form id="root_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>一级目录名称:</td>
                <td>
                    <input name="name" class="easyui-textbox"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <!-- 表单弹出框 -->
    <div id="parent_dialog">
        <form id="parent_form" method="post">
            <table align='center' style="margin-top: 20px">
                <tbody>
                <input type="hidden" name="id"/>
                <tr>
                    <td>二级分类名称:</td>
                    <td>
                        <input name="name" class="easyui-textbox"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>
