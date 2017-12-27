<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <script type="text/javascript" src="/static/js/product.js"></script>
</head>
<body>
<div style="height: 29%;width:23%;float: left">
    <div class="easyui-panel" data-options=
            "closable:true,collapsible:true,fit:true">
        <div class="memberreporttitle">共有商品</div>
        <div id="productAmout"
             style='height:70px;line-height:70px;text-align:center;font-size: 20px;color: red'>
            ${productAmount}种
        </div>
    </div>
</div>
<div style="height: 29%;width:23%;float:left">
    <div class="easyui-panel" data-options=
            "closable:true,collapsible:true,fit:true">
        <div>
           库存商品总成本:
        </div>
        <div  style='height:70px;line-height:70px;text-align:center;font-size: 20px;color: red'>
            ${sumAmount}元
        </div>
    </div>
</div>
<div style="height: 29%;width:23%;float:left">
    <div class="easyui-panel" data-options=
            "closable:true,collapsible:true,fit:true">
        <div>
            <span >库存总数低于<span style="color: red">100</span>件有</span>
        </div>
        <div  style='height:70px;line-height:70px;text-align:center;font-size: 20px;color: red'>
            ${selectProductAmount}种
        </div>
    </div>
</div>
<div style="height: 29%;width:29%;float: left">
    <div class="easyui-panel" data-options="closable:true,collapsible:true,fit:true">
        <div class="memberreporttitle">销售热卖的(TOP3)商品有</div>
        <ul type="none">
            <table>
                <c:forEach items="${maps}" var="map">
                <tr>
                    <td style="font-size: small">${map.name}</td>
                    <td>&nbsp;&nbsp;</td>
                    <td style="color: red">${map.totalNumber}件</td>
                </tr>
                </c:forEach>
            </table>
        </ul>
    </div>
</div>
<div style="height: 70%;width:100%">
    <table id="product_datagrid"></table>
</div>

<div id="product_toolbar">
    <shiro:hasPermission name="product:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true data-cmd="edit">修改</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true data-cmd="changeState"
       id="changState_btn">商品状态</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true data-cmd="remove">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true data-cmd="reload">刷新</a>
    <input class="easyui-textbox" name="keyword" id="keyword" data-options="
    prompt:'请输入商品编码/名称'"/>
    <input type="text" name="rootId" class="easyui-combobox" id="parent_root_combox"
           data-options=" prompt:'一级分类',url:'/linkageMenu/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
    <input type="text" class="easyui-combobox" id="parent_dirname_combox" name="ParentId"
           data-options=" prompt:'二级分类'"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchForm">搜索</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-redo" data-cmd="exportXls">导出</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-undo" data-cmd="importXls">导入</a>


</div>
<div id="product_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true data-cmd="cancel">取消</a>
</div>
<div id="updownload_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="updownload">上传</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true data-cmd="cancel">取消</a>
</div>
<div class="easyui-dialog"  id="up-download-dialog">
    <form action="/product/importXls.do" method="post" enctype="multipart/form-data" id="updownloadForm">
        <input type="file" name="file">
    </form>
</div>
<!-- 表单弹出框 -->
<div id="product_dialog">
    <form id="product_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>一级目录:</td>
                <td>
                    <input type="text" name="root.id" class="easyui-combobox" id="root_combox" required="true"
                    data-options="url:'/linkageMenu/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            <tr>
                <td>二级目录:</td>
                <td>
                    <input type="text" name="parent.id" id="parent_combox" class="easyui-combobox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>商品名称:</td>
                <td>
                    <input name="name" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>商品编码:</td>
                <td>
                    <input name="sn" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>进 价:</td>
                <td>
                    <input name="costPrice" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>售 价:</td>
                <td>
                    <input name="salePrice" class="easyui-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>计量单位:</td>
                <td>
                    <input type="text" name="unit.id" class="easyui-combobox"
                           data-options="url:'/unit/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'" required="true"/>
                </td>
                </td>
            </tr>
            <tr>
                <td>录入时间:</td>
                <td>
                    <input type="text" name="inputTime" class="easyui-datebox"/>
                </td>
            </tr>

            <tr>
                <td>是否上架:</td>
                <td>
                    <select class="easyui-combobox" required="true" name="state" panelHeight="auto" style="width:146px;">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
