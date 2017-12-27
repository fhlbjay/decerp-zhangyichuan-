<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- 使用EasyUI的主题 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/gray/easyui.css">
    <!-- EasyUI的图标样式 -->
    <link rel="stylesheet" type="text/css" href="/jquery-easyui/themes/icon.css">

    <!-- 重置文件 -->
    <link rel="stylesheet" href="css/dailyCon/normalize.css">
    <link rel="stylesheet" href="css/dailyCon/style.css">

    <!-- 依赖的Jquery核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <!-- EasyUI的核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.easyui.min.js"></script>
    <!-- 国际化文件 -->
    <script type="text/javascript" src="/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" src="/static/js/dailyconsume.js"></script>


</head>
<body style="background:url(/static/image/3sYf4ZTyYeo.jpg)" style="background-image:url(../images/a.jpg)">
<table id="daily_datagrid"></table>

<div id="daily_toolbar">
    <a class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="add()">新增</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" plain=true onclick="edit()">编辑</a>
    <a class="easyui-linkbutton" iconCls="icon-remove" plain=true onclick="remove()">删除</a>
    <a class="easyui-linkbutton" iconCls="icon-reload" plain=true onclick="reload()">刷新</a>
    <a class="easyui-linkbutton" iconCls="icon-redo" plain=true onclick="importXls()">导入</a>

    <input class="easyui-datebox" name="beginDate" id="beginDate" prompt="请输入起始日期"/>-
    <input class="easyui-datebox" name="endDate" id="endDate" prompt="请输入终止日期"/>
    <input class="easyui-textbox" name="keyword" id="keyword"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="searchForm()">搜索</a>
</div>
<div id="daily_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" plain=true onclick="save()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="cancel()">取消</a>

</div>

<!--文件上传弹出框 -->
<div id="upload_dialog" class="easyui-dialog" data-options="width:500,height:250">
    <form id="upload_form" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <table border="1" cellpadding="0" cellspacing="1" width="90%" align="center">
            <tr style="background-color: orange">
                <td colspan="6" align="center">请务必对应以下字段及格式上传文件(例)</td>
            </tr>
            <tr>
                <td>支出名称</td>
                <td>支出金额</td>
                <td>日期</td>
                <td>备注</td>
                <td>员工编号</td>
                <td>支出编号</td>
            </tr>
            <tr>
                <td>员工旅游支出</td>
                <td>500</td>
                <td>2017-12-13</td>
                <td>出国游</td>
                <td>2</td>
                <td>67</td>
            </tr>
        </table>
        <button class="easyui-button" onclick="return checkAndUpload(this.form)">文件上传</button>
    </form>
    <button class="easyui-button" onclick="downloadTemplate()">模板下载</button>
</div>

<!-- 表单弹出框 -->
<div id="daily_dialog">
    <form id="daily_form" method="post">
        <table align='center' style="margin-top: 20px">
            <tbody>
            <input type="hidden" name="id"/>
            <tr>
                <td>支出类别:</td>
                <td>
                    <input type="text" name="consumecatalog.id" class="easyui-combobox" required="true"
                           data-options="url:'/consumecatalog/selectAll.do',textField:'name',valueField:'id'"/>
                </td>
            </tr>
            <tr>
                <td>支出金额:</td>
                <td>
                    <input type="text" name="amount" class=" easyui-numberbox" prompt="请输入数字" required="true"/>
                </td>
            </tr>
            <tr>
                <td>日期:</td>
                <td>
                    <input type="text" name="date" class=" easyui-datebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input class="easyui-textbox" name="memo" required="true" data-options="multiline:true"
                           style="height:60px"/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>


</body>
</html>
