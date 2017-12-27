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


    <script type="text/javascript" src="/static/js/purchasingOrderBill.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<table id="pur_datagrid"></table>

<div id="pur_toolbar">
    <shiro:hasPermission name="purchasingOrderBill:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true  data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="delete">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  data-cmd="reload">刷新</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-redo" data-cmd="exportXls">导出</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-undo" data-cmd="importXls">导入</a>
    <input class="easyui-textbox" name="keyword" id="keyword" prompt="请输入订单编号、供应商"/>
    <input class="easyui-datebox" name="beginDate" id="beginDate" prompt="起始日期"/>
    <input class="easyui-datebox" name="endDate" id="endDate" prompt="结束日期"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchForm">搜索</a>
</div>
<div id="pur_toolbar2">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="attend">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="remove">删除</a>
</div>
<div id="item_buttons">
    <a class="easyui-linkbutton" iconCls="icon-ok" plain=true data-cmd="sure">确定</a>
</div>
<div id="pur_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div id="updownload_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="updownload">上传</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div class="easyui-dialog" data-options="" id="up-download-dialog">
    <form action="/purchasingOrderBill/importXls.do" method="post" enctype="multipart/form-data" id="updownloadForm">
        <input type="file" name="file">
    </form>
</div>
<!-- 表单弹出框 -->
<div id="pur_dialog">
    <form id="pur_form" method="post">
        <table>
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>订单编码</td>
                <td>
                    <input  name="sn" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>仓库</td>
                <td>
                    <input type="text" name="depot.id"   class="easyui-combobox" id="pur_d_combobox"
                           data-options="url:'/depot/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            <tr>
                <td>业务时间</td>
                <td>
                    <input  name="vdate" class="easyui-datebox" />
                </td>
            </tr>
            <tr>
                <td>供应商</td>
                <td>
                    <input type="text" name="supplier.id"  class="easyui-combobox" id="pur_s_combobox"
                           data-options="url:'/supplier/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table id="bill_datagrid"></table>
    </form>
</div>
<!--明细-->
<div id="item_dialog">
    <table id="item_datagrid"></table>
</div>

<div id="getItem_dialog">
    <table id="getItem_datagrid"></table>
</div>

</body>
</html>
