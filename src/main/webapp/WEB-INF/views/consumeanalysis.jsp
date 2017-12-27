<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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

    <script type="text/javascript" src="/static/js/consumeanalysis.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>

</head>
<body>

<table id="emp_datagrid"></table>


<div class="easyui-panel" style="height: 200px;" align="center">
    <div class="amountbyday" id="" style="margin-top: 20px" align="center">
        <span class="fl">本日支出</span>
        <span class="fr" style="font-size: 20px;color: red"><i class="colorff" id="amountByDay">¥ ${amountByDay}</i></span> 元
    </div>
    <div class="center-box" id="" align="center" style="margin-top: 40px">
        <span class="fl">本月支出</span>
        <span class="fr" style="font-size: 20px;color: red"><i class="colorff" id="amountByMonth">¥ ${amountByMonth}</i></span> 元
    </div>
    <div class="center-footer-box" id="" style="margin-top: 40px" align="center">
        <span class="fl">本年支出</span>
        <span class="fr" style="font-size: 20px;color: red"><i class="colorff" id="amountByYear">¥ ${amountByYear}</i></span> 元
    </div>
</div>

<div title="主页">


<!--  line echarts line echarts line echarts line echarts line echarts line echarts line echarts line echarts line echarts    -->
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="theline" style="height:400px"></div>
    <!-- ECharts单文件引入 -->
    <script src="/static/js/plugins/echarts/build/dist/echarts.js"></script>
    <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('theline'));

                var option = {
                    title : {
                        text: '本年度支出统计',
                        subtext: '纯属扯淡',
                        x : 'left'
                    },
                    tooltip : {
                        trigger: 'axis'
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
                            boundaryGap : false,
                            data : ${months}
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel : {
                                formatter: '¥ {value}'
                            }
                        }
                    ],
                    series : [
                        {
                            name:'支出费用',
                            type:'line',
                            data:${amounts},
                            markPoint : {
                                data : [
                                    {type : 'max', name: '最大值'},
                                    {type : 'min', name: '最小值'}
                                ]
                            },
                            markLine : {
                                data : [
                                    {type : 'average', name: '平均值'}
                                ]
                            }
                        }
                    ]
                };

                // 为echarts对象加载数据
                myChart.setOption(option);
            }
        );
    </script>


<!--  line echarts line echarts line echarts line echarts line echarts line echarts line echarts line echarts line echarts     -->



<!--  bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts   -->

    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="thebar" style="height:400px"></div>
    <!-- ECharts单文件引入 -->
    <script src="/static/js/plugins/echarts/build/dist/echarts.js"></script>
    <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('thebar'));

                var option = {
                    tooltip: {
                        show: true
                    },
                    legend: {
                        data: ['支出排列前15(单位 元)'],
                        x:'left'
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: ${names}
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            "name": "销量",
                            "type": "bar",
                            "data": ${amounts2},
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
<!--  bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts bar echarts    -->



</div>


</body>
</html>
