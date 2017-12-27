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
    <script src="/static/js/integralDetail/list.js"></script>
    <style>
        .searchGift{
            width: 22px;
            height:22px;
            margin-right: 20px;
            cursor:pointer;
        }
        .ui_flt{
            float: left;
            margin-left: 14px;
        }
        .ui_frt{
            float: right;
            margin-right: 100px;
        }
        .ui_input_txt01{
            width: 30px;
            text-align: center;
        }
        .checkinput:checked {
            background: #31c17b url(/static/image/checkinputbg.png) no-repeat center / 100%;
            border-color: #31c17b;
        }
        .name-money{
            padding-left: 15px !important;
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
                    <!--会员积分信息-->
                    <div class="memberIntegralinfo memberIntegralinfo2 fl">
                        <div class="memberphoto">
                            <img class="memberphotoimg" id="m_sv_mr_headimg"
                                 src="/static/image/001.png">
                        </div>
                        <div class="infolist">
                            <ul id="">
                                <li>
                                    <span>会员姓名:</span>
                                    <span id="vipName"></span>
                                    <input id="vipID" name="vipID"  type="hidden">
                                </li>
                                <li>
                                    <span>会员卡号:</span>
                                    <span id="vipCard"></span>
                                </li>
                                <li>
                                    <span>会员等级:</span>
                                    <span id="vipGrade"></span>
                                </li>
                                <li>
                                    <span>会员余额:</span>
                                    <span class="colorff">¥<i id="vipCardBalance"></i></span>
                                </li>
                                <li>
                                    <span>当前积分:</span>
                                    <span id="vipCardCurrentIntegral"></span>
                                </li>
                                <li>
                                    <span>累计消费:</span>
                                    <span class="colorff">¥<i id="vipCardConsumptionAmount"></i></span>
                                </li>
                                <li>
                                    <span>累计积分:</span>
                                    <span id="vipCardIntegral"></span>
                                </li>
                                <li>
                                    <span>已用积分:</span>
                                    <span id="expensePoint"></span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!--会员积分信息-->
                    <!--积分会员列表-->
                    <div class="MemberIntegrallist MemberIntegrallist3 fl">
                        <!--搜索/选择框-->
                        <div class="searchbox">
                            <div class="fr search-input selectmember">
                                <i class="positionicon"><img src="/static/image/search.png" style="width: 22px;margin-bottom: 5px;"></i>
                                <input name="query_user" id="query_user" placeholder="输入手机号、卡号" type="text">
                                <a href="javascript:;" id="query_user_btn" class="positionicontype">搜索</a>
                            </div>
                        </div>
                        <!--搜索/选择框-->
                        <!--会员积分列表-->
                        <div class="integralList">
                            <ul class="scrollstyle" id="memberList">
                                <%--<li class="active">
                                    <div class="fl miinfo">
                                        <img src="/static/image/001.png" alt="" class="memberIntegralimg fl">
                                        <div class="fl name-money"><span>围场满族蒙古族蒙古族自治县鑫汇</span> <span>储值:<i>￥6.00</i></span></div>
                                    </div>
                                    <div class="fr">
                                        <div class="fr name-money"><i class="integralgrade diamond">时尚卡</i><span
                                                class="memberphone">18888888887</span></div>
                                    </div>
                                </li>
                                <li data-membercardid="11" data-isoverdue="1">
                                    <div class="fl miinfo"><img src="/static/image/001.png" alt=""
                                                                class="memberIntegralimg fl">
                                        <div class="fl name-money"><span>围场满族蒙古族蒙古族自</span> <span>储值:<i>￥0.00</i></span></div>
                                    </div>
                                    <div class="fr">
                                        <div class="fr name-money"><i class="integralgrade diamond">时尚卡</i><span
                                                class="memberphone">18888888888</span></div>
                                    </div>
                                </li>--%>
                            </ul>
                        </div>
                        <!--会员积分列表-->

                    </div>
                    <!--积分会员列表-->

                    <%--积分兑换--%>
                    <div class="changeMemberIntegral changeMemberIntegral2 fl" style="margin-right:0;margin-left:0.5%;">
                        <ul>
                            <li>
                                <span>选择类型 :</span>
                                <span class="selecttypes"><input class="checkinput shareIntegral pad" checked="checked" name="integral" id="integraRecharge" data-type="1" type="radio">充值</span>
                                <span class="selecttypes"><input class="checkinput shareIntegral pad" name="integral" id="deductionRecharge" data-type="0" type="radio">扣除</span>
                            </li>
                            <li>
                                <span class="fl">变动数额 :</span>
                                <div class="integral-number">
                                    <input class="form-control" name="integralnumber" min="1" id="integralnumber" value="" placeholder="输入数额" type="number">
                                </div>
                            </li>
                            <li class="btn-box">
                                <button type="button" class="btn clearnumber" id="btnClearIntegral" onclick="clearIntegral()">积分清零</button>
                                <button type="button" class="btn successbtn" id="btnSaveIntegral" onclick="changeIntegral()">确定</button>
                            </li>
                        </ul>
                    </div>
                </div>

                <!--会员积分列表-->
                <div class="integraltable memberlist table-responsive" style="padding-bottom: 20px">
                    <table class="table memberlistheade table-hover">
                        <thead>
                        <tr id="rowlength">
                            <th>会员卡号</th>
                            <th>会员姓名</th>
                            <th>会员等级</th>
                            <th>操作类型</th>
                            <th>变动数额</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody id="giftExchangelisthtml">
                        <%--<tr>
                            <td>1474</td>
                            <td class="amountcolor2">围场满族蒙古族蒙古族自治县鑫汇</td>
                            <td>水杯</td>
                            <td>1块</td>
                            <td class="colorff">10</td>
                            <td>294</td>
                        </tr>
                        <tr>
                            <td>1474</td>
                            <td class="amountcolor2">围场满族蒙古族蒙古族自治县鑫汇</td>
                            <td>水杯</td>
                            <td>1块</td>
                            <td class="colorff">10</td>
                            <td>294</td>
                        </tr>--%>
                        </tbody>
                    </table>
                    <div class="ui_tb_h30" >
                        <div class="ui_flt" style="height: 30px; line-height: 30px;">
                            共有
                            <span id="totalCount"></span>
                            条记录，当前第
                            <span id="currentPageResult"></span>
                            页
                        </div>
                        <div class="ui_frt" >
                            <input type="button" value="首页" id="firstPage" class="btn_page"
                                   data-page="1" />
                            <input type="button" value="上一页" id="prevPage" class="btn_page"
                                   data-page="" />
                            <input type="button" value="下一页" id="nextPage" class="btn_page"
                                   data-page="" />
                            <input type="button" value="尾页" id="totalPage" class="btn_page"
                                   data-page="" />
                            <select id="pageSize" name="pageSize" class="ui_select02">
                                <option value="3">3</option>
                                <option value="10">10</option>
                                <option value="15">15</option>
                            </select>
                            转到第
                            <input type="number" name="currentPage" value=""
                                   class="ui_input_txt01" min="1"/>
                            页
                            <input type="button"  id="queryGiftItem" value="跳转" />
                        </div>
                    </div>
                </div>
                <!--会员积分列表-->
            </div>
        </div>
    </div>
</div>
</body>
</html>
