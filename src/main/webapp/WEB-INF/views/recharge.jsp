<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2017/12/23
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
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

    <script type="text/javascript" src="/static/js/recharge.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
</head>
<div id="toolbar">
    <input class="easyui-textbox" name="keyword" id="keyword" data-options="
    prompt:'请输入卡号或手机号'"/>
    <a class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="mySearch()">搜索</a>
</div>
<body class="easyui-layout">
<div data-options="region:'south',title:'充值记录',split:true" style="height:230px;" >
    <div id="record"></div>
</div>
<div data-options="region:'west',title:'会员信息',split:true" style="width:50%;">
    <div style="margin-top: 60px;">
        <div class="memberphoto" style="margin:auto;width: 190px">
            <img src="/static/image/001.png"  onclick="listVip()">
            <div id="vipList">
                <div id="v"></div>
            </div>
        </div>
        <div class="infolist" style="margin:auto;width: 290px">
            <ul id="" type="none">
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>会员卡号:</span>
                    <span id="m_sv_mr_cardno"></span>
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>会员姓名:</span>
                    <span id="m_sv_mr_name"></span>
                    <input id="m_member_id" name="m_member_id" value="481616010" type="hidden">
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>会员等级:</span>
                    <span id="m_sv_ml_name"></span>
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>会员卡余额:</span>
                    <span class="colorff">￥<i id="m_sv_mw_availableamount"></i></span>
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>当前积分:</span>
                    <span id="m_sv_mw_availablepoint"></span>
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>累计积分:</span>
                    <span id="m_sv_mw_sumpoint"></span>
                </li>
                <li style="float: left;width: 145px;padding-top: 5px;">
                    <span>累计消费:</span>
                    <span class="colorff">￥<i id="m_sv_mw_sumamount"></i></span>
                </li>
            </ul>
        </div>
    </div>

</div>
<div data-options="region:'center',title:'会员充值'" style="padding:5px;margin:auto;width: 350px">
    <div style="margin:auto;width: 350px;margin-top: 60px;"><span><font color="red" style="font-size: 18px;">请选择支付方式:</font></span></div>
    <div style="margin:auto;width: 350px">
        <form id="rechargeItem">
            <table>
                <input type="hidden" name="vipId" id="vipId"/>
                <input type="hidden" name="vipcardId" id="vipcardId"/>
                <tr>
                    <td style="width: 60px;padding: 20px 0;">
                        <input type="radio" name="payment" value="现金" checked="checked">现金
                    </td>
                    <td style="width: 60px;padding: 20px 0;">
                        <input type="radio" name="payment" value="微信">微信
                    </td>
                    <td style="width: 80px;padding: 20px 0;">
                        <input type="radio" name="payment" value="支付宝">支付宝
                    </td>
                    <td style="width: 80px;padding: 20px 0;">
                        <input type="radio" name="payment" value="银行卡">银行卡
                    </td>
                </tr>
                <tr >
                    <td colspan="4" style="padding: 5px 0;">
                        充值金额:<input class="easyui-textbox" name="rechargemount" id="rechargemount"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="4" style="padding: 5px 0;">
                        充值备注:<input class="easyui-textbox" name="remarks" id="remarks"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a class="easyui-linkbutton" iconCls="icon-save" plain=true id="save">保存</a>
                    </td>
                    <td colspan="2">
                        <a class="easyui-linkbutton" iconCls="icon-cancel" plain=true id="cancel">取消</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>

</html>
