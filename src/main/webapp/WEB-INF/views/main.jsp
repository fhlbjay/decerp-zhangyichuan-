<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>会员管理软件</title>
    <link href="/static/css/giftOrder/bootstrap3.css" rel="stylesheet">
    <!--字体图标样式表-->
    <link href="/static/css/giftOrder/font-awesome.css" rel="stylesheet">
    <!--模板页的样式表-->
    <link href="/static/css/giftOrder/share_N3.css" rel="stylesheet">
    <!--content共用的样式表-->
    <link href="/static/css/giftOrder/base_N3.css" rel="stylesheet">
    <script src="/static/js/jquery.js"></script>
    <script type="text/javascript" src="/static/echarts/build/dist/echarts.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <style>
        .content-info .infolist li .infoleft {
            bottom: 16px;
            box-sizing: border-box;
            color: rgb(102, 102, 102);
            font-family: "微软雅黑";
            font-size: 14px;
            height: 48px;
            left: 10px;
            line-height: 20px;
            list-style-image: none;
            list-style-position: outside;
            list-style-type: none;
            margin-bottom: 0px;
            margin-left: 0px;
            margin-right: 0px;
            margin-top: 0px;
            position: absolute;
            top: 16px;
            width: 48px;
            background: url(/static/image/titlenav.png) no-repeat;
            background-attachment: scroll;
            background-clip: border-box;
            background-color: rgba(0, 0, 0, 0);
            background-origin: padding-box;
            background-position: 0px 0px;
            background-position-x: 0px;
            background-position-y: 0px;
            background-repeat: no-repeat;
            background-size: cover;
        }

        .content-info .infolist li .nav2 {
            background-position-y: -52px;
        }

        .content-info .infolist li .nav3 {
            background-position-y: -103px;
        }

        .content-info .infolist li .nav4 {
            background-position-y: -152px;
        }

        .content-info .infolist li .nav5 {
            background-position-y: -203px;
        }

        .centertext {
            padding-left: 65px !important;
        }
    </style>
</head>
<body>


<!--外卖订单详情-->
<div class="decerp-content decerpindex">
    <!--decerpindex-->
    <div class="center-content" id="index-content">
        <!--图表模式-->
        <div class="content container-fluid" style="position:relative;">
            <div class="leftdataconente" style="padding: 0;">
                <!--导航栏-->
                <div class="content-title-nav">
                    <div class="fl">
                        <span class="titleleft">店铺概括</span>
                    </div>
                    <div class="fr otherDate" id="searchOtherDate" style="display: none;">
                        <input class="form-control dateNumber" id="dateNumber" placeholder="自定义天数" type="number">
                        <button type="button" class="btn searchBtn" id="searchSalesBtn">搜索</button>
                    </div>
                    <div class="fr titleright">
                        <ul class="row" id="selectDate">
                            <input type="hidden" value="${days}" id="days">
                            <li data-type="1" class="col-xs-3">今天</li>
                            <li data-type="7" class="col-xs-3 active">近7天</li>
                            <li data-type="30" class="col-xs-3">近30天</li>
                        </ul>
                    </div>
                </div>
                <!--导航栏-->
                <!--数据统计图-->
                <div class="content-info">
                    <ul class="infolist">
                        <li>
                            <div class="infoleft nav1"></div>
                            <div class="centertext">
                                <p class="user-analysis">会员充值</p>
                                <div class="user-amount amountcolor1" id="expense_other">${totalRecharge}元</div>
                            </div>
                        </li>
                        <li>
                            <div class="infoleft nav2"></div>
                            <div class="centertext">
                                <p class="user-analysis">会员消费</p>
                                <div class="user-amount amountcolor2" id="expense_member">${totalConsum}元</div>
                            </div>
                        </li>
                        <li>
                            <div class="infoleft nav3"></div>
                            <div class="centertext">
                                <p class="user-analysis">新增会员</p>
                                <div class="user-amount amountcolor3" id="count_newmember">${totalNumber}位</div>
                            </div>
                        </li>
                        <li>
                            <div class="infoleft nav4"></div>
                            <div class="centertext">
                                <p class="user-analysis">成交笔数</p>
                                <div class="user-amount amountcolor4"><i id="count_order">${totalBill}</i><span class="unit">笔</span></div>
                            </div>
                        </li>
                        <li>
                            <div class="infoleft nav5"></div>
                            <div class="centertext">
                                <p class="user-analysis">综合总收入</p>
                                <div class="user-amount amountcolor5" id="amount_all">${totalIncome}元</div>
                            </div>
                        </li>
                    </ul>
                </div>
                <!--数据统计图-->
                <!--中间图标-->
                <div class="center-chart" id="recentSevenDaySalesData"
                     style="-moz-user-select: none; position: relative; background: white;height:450px;">
                    <script type="text/javascript">
                        // 路径配置
                        require.config({
                            paths: {
                                echarts: '/static/echarts/build/dist'
                            }
                        });

                        // 使用
                        require(
                            [
                                'echarts',
                                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('recentSevenDaySalesData'));

                                var option = {
                                    backgroundColor:"#ffffff",
                                    title : {
                                        text: '销售报表',
                                        subtext: '充值总额',
                                        x:'center'
                                    },
                                    tooltip : {
                                        trigger: 'axis'
                                    },
                                    legend: {
                                        data:['充值总额'],
                                        x:'left'
                                    },
                                    toolbox: {
                                        show : true,
                                        feature : {
                                            mark : {show: true},
                                            dataView : {show: true, readOnly: false},
                                            magicType : {show: true, type: ['line', 'bar']},
                                            restore : {show: true},
                                            saveAsImage : {show: true}
                                        }
                                    },
                                    calculable : true,
                                    xAxis : [
                                        {
                                            type : 'category',
                                            data : ${groupByNames}
                                        }
                                    ],
                                    yAxis : [
                                        {
                                            type : 'value'
                                        }
                                    ],
                                    series : [
                                        {
                                            name:'充值总额',
                                            type:'bar',
                                            data:${totalRecharges},

                                            markLine : {
                                                data : [
                                                    {type : 'average', name: '平均值'}
                                                ]
                                            },
                                            itemStyle: {
                                                normal: {
                                                    //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                                                    color: function(params) {
                                                        // build a color map as your need.
                                                        var colorList = [
                                                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                                            '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                                        ];
                                                        return colorList[params.dataIndex]
                                                    },
                                                    //以下为是否显示，显示位置和显示格式的设置了
                                                    label: {
                                                        show: true,
                                                        position: 'top',
                                                        formatter: '{b}\n{c}'
                                                    }
                                                }
                                            }
                                        }
                                    ]
                                };

                                // 为echarts对象加载数据
                                myChart.setOption(option);
                            }
                        );
                    </script>
                </div>
                <!--中间图标-->
            </div>
        </div>
        <!--图表模式-->
    </div>
</div>
<script>
    $(function () {
        $("#selectDate li").each(function (index, ele) {
            $(ele).click(function () {
                $("#selectDate li").removeClass("active");
                $(this).addClass("active");
                //拿到每个li上的type
                var days = $(this).data("type");
                //ajax更新页面数据
                window.location.href="/main.do?days="+days;
            });

        });
        $("#selectDate li").each(function (index, ele) {
            //先拿到天数
            var days = $("#days").val();
            //再拿到标签上的天数
            var days2 =$(this).data("type");
            if(days==days2){
                $("#selectDate li").removeClass("active");
                $(this).addClass("active");
            }
        });

    })
</script>
</body>
</html>