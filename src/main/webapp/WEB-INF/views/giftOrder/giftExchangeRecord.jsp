<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>会员管理软件</title>
    <link href="/static/css/giftOrder/bootstrap3.css" rel="stylesheet">
    <!--字体图标样式表-->
    <link href="/static/css/giftOrder/font-awesome.css" rel="stylesheet">
    <!--模板页的样式表-->
    <link href="/static/css/giftOrder/share_N3.css" rel="stylesheet">
    <!--content共用的样式表-->
    <link href="/static/css/giftOrder/base_N3.css" rel="stylesheet">
    <!-- 依赖的Jquery核心库 -->
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <%--dialog库--%>
    <script type="text/javascript" src="/static/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/static/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <script src="/static/js/giftOrder/giftExchangeRecord.js"></script>
    <script language="javascript" type="text/javascript" src="/static/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <style>
        .searchGift {
            width: 22px;
            height: 22px;
            margin-right: 20px;
            cursor: pointer;
        }

        .ui_flt {
            float: left;
            margin-left: 14px;
        }

        .ui_frt {
            float: right;
            margin-right: 100px;
        }

        .ui_input_txt01 {
            width: 30px;
            text-align: center;
        }

    </style>
</head>
<body>
<div class="decerp-content decerpindex">
    <!--decerpindex-->
    <div class="center-content" id="index-content">
        <div class="content container-fluid">
            <div class="share-container integral">
                <div class="memberIntegral row">
                    <div class="content-Secondary-nav" style="margin-bottom: 12px;">
                        <button type="button" class="btn donwbtn fl" id="exportRecord"><i class="positionicon"><img src="/static/image/export.png" style="width: 22px;margin-bottom: 5px;"></i>导出列表</button>
                        <ul class="selectday fl" id="querydate">
                            <li class="day uniformQuery" data-datetype="1">最近1天</li>
                            <li class="day uniformQuery" data-datetype="3">最近3天</li>
                            <li class="day uniformQuery" data-datetype="7">最近7天</li>
                            <li data-datetype="-1">其他</li>
                        </ul>
                        <!--其他的时间-->
                        <div class="querydate fl" id="queryortherdate" >
                            <ul>
                                <li>
                                    <div class="input-group date" id="begindate" style="width:130px;" data-date="" data-date-format=""
                                         data-link-field="" data-link-format="">
                                        <input class="form-control Wdate" size="16" value="" id="begindateval" readonly="" type="text" onClick="WdatePicker({maxDate:'%y-%M-%d'})">
                                    </div>
                                </li>
                                <li><span class="padding10">至</span></li>
                                <li>
                                    <div class="input-group date" id="enddate" style="width:130px;" data-date="" data-date-format=""
                                         data-link-field="" data-link-format="">
                                        <input class="form-control Wdate" size="16" value="" id="enddateval" readonly="" type="text" onClick="WdatePicker({maxDate:'%y-%M-%d'})">

                                    </div>
                                </li>
                                <li><input class="btn margin15 uniformQuery" id="otherday" value="搜索" type="button"></li>
                            </ul>
                        </div>
                        <!--其他的时间-->
                        <!--搜索栏-->
                        <div class="fr search-input" style="margin-right: 20px;">
                            <i class="positionicon"><img src="/static/image/search.png" style="width: 22px;margin-bottom: 5px;"></i>
                            <input name="query_member" id="query_member" value="" placeholder="输入会员卡号、名称" type="text">
                            <a href="javascript: void(0);" id="query_member_btn" class="positionicontype uniformQuery">搜索</a>
                        </div>
                        <!--搜索栏-->
                    </div>
                    <!--会员积分列表-->
                    <div class="integraltable memberlist table-responsive" style="padding-bottom: 20px">
                        <table class="table memberlistheade table-hover">
                            <thead>
                            <tr id="rowlength">
                                <th>会员卡号</th>
                                <th>会员名称</th>
                                <th>礼品名称</th>
                                <th>兑换数量</th>
                                <th>消费积分</th>
                                <th>操作时间</th>
                                <th>操作人员</th>
                            </tr>
                            </thead>
                            <tbody id="giftExchangelisthtml">

                            </tbody>
                        </table>
                        <div class="ui_tb_h30">
                            <div class="ui_flt" style="height: 30px; line-height: 30px;">
                                共有
                                <span id="totalCount"></span>
                                条记录，当前第
                                <span id="currentPageResult"></span>
                                页
                            </div>
                            <div class="ui_frt">
                                <input type="button" value="首页" id="firstPage" class="btn_page"
                                       data-page="1"/>
                                <input type="button" value="上一页" id="prevPage" class="btn_page"
                                       data-page=""/>
                                <input type="button" value="下一页" id="nextPage" class="btn_page"
                                       data-page=""/>
                                <input type="button" value="尾页" id="totalPage" class="btn_page"
                                       data-page=""/>
                                <select id="pageSize" name="pageSize" class="ui_select02">
                                    <option value="3">3</option>
                                    <option value="10">10</option>
                                    <option value="15">15</option>
                                </select>
                                转到第
                                <input type="number" name="currentPage" value=""
                                       class="ui_input_txt01" min="1"/>
                                页
                                <input type="button" id="queryGiftItem"  class="uniformQuery" value="跳转"/>
                            </div>
                        </div>
                    </div>
                    <!--会员积分列表-->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
