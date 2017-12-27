<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>erp</title>

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

    <script src="/static/js/cash.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>

</head>
<body>


<div id="my_layout" class="easyui-layout" data-options="fit:true">
    <%--  <div data-options="region:'north',title:'收银界面',split:true" style="height:90px;">

          <h1 align="center"></h1>
      </div>


      <div data-options="region:'south',split:true" style="height:50px;">
          <h4 align="center">版权声明</h4>
      </div>
  --%>

    <%--会员页面的高级查询--%>
    <div id="vip_buttons">
        <input id="keyword" name="keyword" class="easyui-textbox" prompt="电话/编号" data-options="width:300"/>
        <a class="easyui-linkbutton" iconCls="icon-search" plain="true" data-cmd="searchword"></a>
    </div>
    <%--商品界面高级查询--%>
    <div id="product_toolbar">
        <input class="easyui-textbox" name="keyword" id="productkeyword" data-options="width:300,
    prompt:'请输入商品编码/名称'"/>
        <%--     <input type="text" name="rootId" class="easyui-combobox" id="parent_root_combox"
                    data-options=" prompt:'一级分类',url:'/linkageMenu/selectAll.do',textField:'name',valueField:'id',panelHeight:'auto'"/>
             <input type="text" class="easyui-combobox" id="parent_dirname_combox" name="ParentId"
                    data-options=" prompt:'二级分类'"/>--%>
        <a class="easyui-linkbutton" plain="true" iconCls="icon-search" data-cmd="searchForm">搜索</a>
    </div>

    <%--左边收银--%>
    <div data-options="region:'west',title:'收银订单编辑',split:true" style="width:40%;">
        <div STYLE="height:20%;">
            <form id="amount_form" method="post">
                <div>
                    <table cellspacing="0" cellpadding="0" style="background-color: #f8f8f8 ;height: 20px; width: 100%;"
                           width="100%" align="left"
                           border="0">
                        <tr>
                            <td>会员编号:</td>
                            <td>
                                <%--<span id="vipSn"></span>--%>
                                <%--<input type="hidden" name="vip.id" id="vipid">--%>
                                <input id="vipid" name="vipid" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                            </td>
                            <td>会员姓名:</td>
                            <td>
                                <%--<span id="vipName"></span>--%>
                                <input id="vipName" name="vipName" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                            </td>
                        </tr>


                        <tr>
                            <td>卡内余额:</td>
                            <td>
                                <%--<span id="vitMoney"></span>--%>
                                <input id="vitMoney" name="vitMoney" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                            </td>
                            <td>卡折扣:</td>
                            <td>
                                <%--<span id="vitCut">1</span>--%>
                                <input id="vitCut" name="vitCut" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                            </td>
                        </tr>
                        <tr>
                            <td>数量:</td>
                            <td>
                                <input id="totalNumber" name="totalNumber" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                                <%--<span id="cutNumber"></span>--%>
                            </td>

                            <td>折扣:</td>
                            <td>
                                <input id="cutAmount" name="cutAmount" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                                <%--<span id="cutNumber"></span>--%>
                            </td>
                        </tr>
                        <tr>
                            <td>应付:</td>
                            <td>
                                <input id="tempTotalAmount" name="tempTotalAmount" class="easyui-textbox"
                                       type="text"
                                       data-options="readonly:true"/>
                                <%--<span id="tempTotalAmount"></span>--%>
                            </td>
                            <td>实付:</td>
                            <td>
                                <input id="totalAmount" name="totalAmount" class="easyui-textbox" type="text"
                                       data-options="readonly:true"/>
                                <%--<span id="totalAmount">1231</span>--%>
                            </td>
                        </tr>
                    </table>

                </div>
                <%--<div>

                    <div>
                        <table cellspacing="0" cellpadding="0" style="background-color: #f8f8f8 ;height: 30px; width: 100%;"
                               width="100%" align="left"
                               border="0">

                        </table>

                    </div>

                </div>--%>

            </form>

        </div>

        <div style="height: 70%;width: 100%;">
            <div id="alient_datagrid"></div>
        </div>


        <div style="height: 5%">


            <%--收银界面按钮--%>
            <div id="edit_buttons">

                <table>
                    <tr>
                        <td>
                            <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="cash_end"
                               style="background-color: #f8f8f8;width: 100px">结账</a>
                        </td>
                        <td>
                            <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="cadh_removeOne"
                               style="background-color: #f8f8f8;width: 100px">删除</a>
                        </td>
                        <td>
                            <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="cadh_tempbill"
                               style="background-color: #f8f8f8;width: 100px">挂单</a>
                        </td>
                        <td>
                            <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="cadh_getTempbill"
                               style="background-color: #f8f8f8;width: 100px">取单</a>
                        </td>


                        <td>
                            <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="clearall"
                               style="background-color: #f8f8f8;width: 100px">清空</a>
                        </td>
                    </tr>
                </table>

            </div>


        </div>
    </div>
    <%--左边收银--%>


    <%--中间页面--%>
    <div data-options="region:'center'">


        <div id="mytabs">
            <div title="所有商品">
                <table id="product_datagrid"></table>
            </div>
            <div title="会员">
                <table id="vip_cash"></table>
            </div>

        </div>

    </div>
    <%--中间页面--%>

</div>

<div id="gettempbill_dialog">
    <div id="temp_datagrid"></div>
</div>

<%--取单页面的按钮--%>
<div id="temp_buttons" style="height: 50px;width:70px">

    <table>
        <tr>
            <td>
                <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="getTemp">取单</a>
            </td>
            <td></td>
            <td>
                <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="getTempDelete">删除</a>
            </td>
            <td>
                <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="getTempcancel">取消</a>
            </td>
        </tr>

    </table>


</div>


<%--输入密码的界面--%>
<div id="vipPassword_dialog">
    <form id="vipPassword_form" method="post">
        <table align="center" style="margin-top: 30px">
            <tr align="center">
                <td>请输入密码,谢谢!</td>
            </tr>
            <tr align="center">
                <td>
                    <input id="vipcardSn" name="vipcardSn" class="easyui-datxtbox" type="hidden"/>
                </td>
            </tr>
            <tr align="center">
                <td>
                    <input id="password" name="password" class="easyui-passwordbox" data-options="width:200"/>
                </td>
            </tr>

        </table>
    </form>

</div>
<div id="vipPassword_buttons">
    <a class="easyui-linkbutton" iconCls="icon-save" data-cmd="cash_password"
       style="background-color: #f8f8f8;width: 100px">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" data-cmd="cash_cancel"
       style="background-color: #f8f8f8;width: 100px">取消</a>
</div>


</body>
</html>