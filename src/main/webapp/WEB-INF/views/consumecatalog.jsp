<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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

    <script type="text/javascript" src="/static/js/consumecatalog.js"></script>

    <%--&lt;%&ndash;easyUI增强&ndash;%&gt;--%>
    <%--<link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">--%>
    <%--<link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">--%>
    <%--<script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>--%>
</head>
<body>
<%--<div class="easyui-layout" data-options="fit:true">--%>
<%--<div data-options="region:'west',height:40">--%>
<%--<h1 align="center">叩丁狼员工管理系统</h1>--%>
<%--</div>--%>
<%--<div data-options="region:'east',height:60">--%>
<%--<h4 align="center">版权声明</h4>--%>
<%--</div>--%>


<div id="catalog_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="add()">新增大分类</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true onclick="reload()">刷新</a>
</div>
<div id="catalog_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true onclick="save()">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="cancel()">取消</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true onclick="remove()">删除</a>
</div>

<table  width="90%">
    <tbody >
    <c:forEach items="${parent}" var="item">
        <tr style="height: auto">
            <td width="20%" >
                <table cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <a class="easyui-linkbutton" href="#" onclick="editParent(this)" id="parent_button" value="${item.id}">${item.name}</a>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="80%">
                <c:forEach items="${item.children}" var="item1">
                    <a class="easyui-linkbutton" id="children_button" onclick="editChildren(this)"
                       parentId="${item.id}" value="${item1.id}">${item1.name}</a>
                    <span> &nbsp;</span>
                </c:forEach>
                <a id="addChildren" class="easyui-linkbutton" iconCls="icon-add"
                   parentId="${item.id}" plain=true onclick="addChildren(this)"></a>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <hr>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!--分类新增/编辑弹出框 -->
<div id="catalog_dialog" class="easyui-dialog" data-options="width:240,height:170">
    <form id="newcatalog_form" method="post">
        <tbody>
        <input type="hidden" name="id" class="easyui-textbox one" id="form_id"/>
        <input type="hidden" name="parent_id" class="easyui-textbox one" id="form_parentId"/>
        <table align="center">
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><input  type="text" id="form_name" name="name" class="easyui-textbox two" value="${item.name}"></td>
            </tr>
        </table>
        </tbody>
    </form>
</div>

</body>
</html>
