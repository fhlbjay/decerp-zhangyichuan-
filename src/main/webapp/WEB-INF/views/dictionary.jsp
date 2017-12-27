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

    <script type="text/javascript" src="/static/js/dictionary.js"></script>
    <script type="text/javascript" src="/static/js/dictionaryitem.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<div id="cc" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',title:'字典目录',split:true" style="width:600px;">
        <table id="dic_datagrid"></table>
    </div>
    <div data-options="region:'center',title:'字典目录明细'" style="padding:5px;background:#eee;">
        <table id="item_datagrid" ></table>
    </div>
</div>
<div id="dic_toolbar">
    <shiro:hasPermission name="dictionary:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="remove">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true data-cmd="reload">刷新</a>
</div>
<div id="item_toolbar">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="add_item()">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true onclick="edit_item()">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  onclick="remove_item()">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true onclick="reload_item()">刷新</a>
</div>
<div id="item_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true onclick="save_item()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="cancel_item()">取消</a>
</div>
<div id="dic_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true data-cmd="cancel">取消</a>
</div>
<!-- 表单弹出框 -->
<div id="dic_dialog">
    <form id="dic_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>目录编码:</td>
                <td>
                    <input name="sn" class="easyui-textbox"/>
                </td>
            </tr>
            <tr>
                <td>目录名称:</td>
                <td>
                    <input name="name" class="easyui-textbox"/>
                </td>
            </tr>
            <tr >

                <td>目录简介:</td>
                <td> <input name="intro" class="easyui-textbox"/></td>
            </tr>
            </tbody>
        </table>
    </form>
<!-- 表单弹出框 -->
<div id="item_dialog">
    <form id="item_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>明细名称:</td>
                <td>
                    <input name="name" class="easyui-textbox"/>
                </td>
            </tr>
            <tr>
                <td>明细简介:</td>
                <td>
                    <input name="intro" class="easyui-textbox"/>
                </td>
            </tr>
            <tr >

                <td>明细所属目录:</td>
                <td>
                    <input type="text" name="dictionary.id"  class="easyui-combobox"
                           data-options="url:'/dictionary/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</div>
</body>
</html>
