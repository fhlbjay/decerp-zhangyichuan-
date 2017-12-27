<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>Title</title>
    <!-- 使用EasyUI的主题 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/default/easyui.css">
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

    <script type="text/javascript" src="/static/js/inventory.js"></script>
</head>
<body>
<table id="in_datagrid"></table>

<div id="item_dialog">
    <table id="item_datagrid"></table>
</div>

<div id="item_buttons">
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true data-cmd="close">关闭</a>
</div>

<div id="in_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>

<!-- 表单弹出框 -->
<div id="in_dialog">
    <form id="in_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>商品名称<span style="color: red;">*</span></td>
                <td>
                    <input  name="product.name" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>原有库存<span style="color: red;">*</span></td>
                <td>
                    <input  name="productStock.storeNumber" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr id="password">
                <td>修改库存<span style="color: red;">*</span></td>
                <td>
                    <input  name="newNumber" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>仓库</td>
                <td>
                    <input  name="depot.name" class="easyui-textbox" />
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
