<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/static/css/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" 
    src="/static/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/static/js/plugins/artDialog/plugins/iframeTools.js">
    </script>
    <script type="text/javascript" src="/static/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <title>德克会员管理系统-会员报表管理</title>
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
                var url = "/chart/vipChartByBar.do?" + param;
                $.dialog.open(url, {
                    id: 'ooxx',
                    title: '会员报表柱状图',
                    width: 850,
                    height: 900,
                    close: function () {//子窗口关闭事件
                    }
                });
            });
            //显示餅图
            $(".btn_pie").click(function () {
                var param = $("#searchForm").serialize();
                var url = "/chart/vipChartByPie.do?" + param;
                $.dialog.open(url, {
                    id: 'ooxx',
                    title: '会员报表餅图',
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
<form id="searchForm" action="/chart/vipchart.do" method="post">
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
                        <th>充值次数</th>
                        <th>累计充值</th>
                        <th>累计消费</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${charts}" var="saleChart">
                        <tr>
                            <td>
                                <input type="checkbox" name="IDCheck" class="acb" data-id="${saleChart.id}"/>
                            </td>
                            <td>${saleChart.groupByName}</td>
                            <td>${saleChart.totalNumber}</td>
                            <td>${saleChart.totalRecharge}</td>
                            <td>${saleChart.totalConsum}</td>
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
