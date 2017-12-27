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

    <script type="text/javascript" src="/static/js/employee.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<body>
<table id="emp_datagrid"></table>

<div id="emp_toolbar">
    <shiro:hasPermission name="employee:saveOrUpdate">
        <a class="easyui-linkbutton" iconCls="icon-add" plain=true data-cmd="add">新增</a>
        <a class="easyui-linkbutton" iconCls="icon-edit" plain=true  data-cmd="edit">编辑</a>
    </shiro:hasPermission>
    <a class="easyui-linkbutton" iconCls="icon-remove"  plain=true  data-cmd="changeState" id="changState_btn">设置离职</a>
    <a class="easyui-linkbutton" iconCls="icon-reload"  plain=true  data-cmd="reload">刷新</a>
    <input class="easyui-textbox" name="keyword" id="keyword" data-options=" prompt:'请输入用户名/电话'"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchForm">搜索</a>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-redo" data-cmd="exportXls">导出</a>
  <%--  <a class="easyui-linkbutton" plain="true" iconCls="icon-undo" data-cmd="importXls">导入</a>--%>
</div>
<div id="emp_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div id="updownload_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true data-cmd="updownload">上传</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true   data-cmd="cancel">取消</a>
</div>
<div class="easyui-dialog" data-options="" id="up-download-dialog">
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
                <td>员工姓名:</td>
                <td>
                    <input  name="username" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>真实姓名:</td>
                <td>
                    <input  name="realname" class="easyui-textbox" />
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
                <td>邮箱:</td>
                <td>
                    <input  name="email" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>录入时间:</td>
                <td>
                    <input type="text" name="inputtime" class="easyui-datebox" />
                </td>
            </tr>
            <tr>
                <td>部门:</td>
                <td>
                    <input type="text" name="department.id"  class="easyui-combobox"
                           data-options="url:'/department/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
                </td>
            </tr>
            <tr>
                <td>是否管理员:</td>
                <td>
                    <select  class="easyui-combobox" name="admin" panelHeight="auto" style="width:146px;">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>角色:</td>
                <td>
                    <input type="text"   class="easyui-combobox" id="role_combobox"
                           data-options="url:'/role/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto',multiple:true"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
