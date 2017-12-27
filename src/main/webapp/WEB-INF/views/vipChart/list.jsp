<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" 
    src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js">
    </script>
    <script type="text/javascript" src="/js/plugins/echarts/build/dist/echarts.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <title>叩丁狼教育PSS（演示版）-订货报表管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script type="text/javascript" src="/js/system/common.js"></script>
    <script type="text/javascript">
    	$(function(){
    		 //显示柱状图
            $(".btn_bar").click(function () {
                var param = $("#searchForm").serialize();
                var url = "/chart/saleChartByBar.do?" + param;
                $.dialog.open(url, {
                    id: 'ooxx',
                    title: '销售报表柱状图',
                    width: 850,
                    height: 900,
                    close: function () {//子窗口关闭事件
                    }
                });
            });
            //显示餅图
            $(".btn_pie").click(function () {
                var param = $("#searchForm").serialize();
                var url = "/chart/saleChartByPie.do?" + param;
                $.dialog.open(url, {
                    id: 'ooxx',
                    title: '销售报表餅图',
                    width: 850,
                    height: 650,
                    close: function () {//子窗口关闭事件
                    }
                });
            });
    	})
    </script>
</head>
<body>
<form id="searchForm" action="/chart/saleChart.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate"/>
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
                        业务时间: <input type="text" class="ui_input_txt02 Wdate" name="beginDate"
                                     value="${beginDate}" onclick="WdatePicker();" readonly="readonly"/>

                        ~ <input type="text" class="ui_input_txt02 Wdate" name="endDate"
                                 value="${endDate}" onclick="WdatePicker({maxDate:new Date()});" readonly="readonly"/>


                        货品 <input type="text" class="ui_input_txt02" name="productName"
                                  value="${qo.productName}"/>
                        客户 <select name="clientId"
                                    class="ui_select01">
                        <option value="-1">--请选择--</option>
                        <c:forEach items="${clients}" var="client">
                            <option value="${client.id}"
                                ${qo.clientId==client.id?"selected='selected'":""}>${client.name}
                            </option>
                        </c:forEach>
                    </select>
                        品牌 <select name="brandId"
                                   class="ui_select01">
                        <option value="-1">--请选择--</option>
                        <c:forEach items="${brands}" var="brand">
                            <option value="${brand.id}"
                                ${qo.brandId==brand.id?"selected='selected'":""}>${brand.name}
                            </option>
                        </c:forEach>
                    </select>

                        分组
                        <select name="groupBy"
                                class="ui_select01" id="op">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${qo.groupByMap}" var="map">
                                <option  value="${map.key}" ${qo.groupBy==map.key?"selected='selected'":""}>
                                	${map.value}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                     <div class="box_bottom" style="padding-right: 10px;text-align: right;">
                        <input type="button" value="柱状报表" class="left2right btn_bar"/>
                        <input type="button" value="饼图报表" class="left2right btn_pie"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%"
                       align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th id="th">
                       		<%-- ${qo.getGroupByMap.get(${qo.groupBy})} --%>
                       		<script type="text/javascript">
                       			var va = $("#op option[selected='selected']").text();
                       			$("#th").text(va);
                       		</script>
                       		  
                        </th>
                        <th>销售数量</th>
                        <th>销售总金额</th>
                        <th>毛利润</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${charts}" var="saleChart">
                        <tr>
                            <td>
                                <input type="checkbox" name="IDCheck" class="acb" data-id="${saleChart.id}"/>
                            </td>
                            <td>${saleChart.groupByName}</td>
                            <td>${saleChart.totalNumber}</td>
                            <td>${saleChart.totalAmount}</td>
                            <td>${saleChart.grossProfit}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
