<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/static/js/myorderbill/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/js/myorderbill/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/myorderbill/jquery.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/jquery.artDialog.js?skin=aero"></script>
    <script type="text/javascript" src="/static/js/myorderbill/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/commonAll.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/common.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>

    <title>订单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>


    <%--<script type="text/javascript" src="/js/system/common.js"></script>--%>
</head>
<body>
<form id="searchForm" action="#" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        开始时间
                        <input type="text" class="ui_input_txt02 Wdate" name="beginDate" value="${qo.beginDate}"
                               onclick="WdatePicker()"/>
                        结束时间
                        <input type="text" class="ui_input_txt02 Wdate" name="endDate" value="${qo.endDate}"
                               onclick="WdatePicker()"/>
                        会员
                        <select class="ui_select03" name="vipId">

                            <option value="-1">--请选择会员--</option>
                            <c:forEach items="${vips}" var="vip">
                                <option value="${vip.id}" ${qo.vipId == vip.id ?"selected=selected" : ""}>${vip.name}</option>

                            </c:forEach>
                        </select>
                        状态
                        <select name="status" class="ui_select03">
                            <option value="-1">---所有状态---</option>
                            <option value="0" ${qo.status==0?"selected='selected'":""}>挂单</option>
                            <option value="1" ${qo.status==1?"selected='selected'":""}>已出单</option>
                        </select>


                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/orderbill/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>订单编号</th>
                        <th>订单时间</th>
                        <th>会员</th>
                        <th>总金额</th>
                        <th>总数量</th>
                        <th>录入人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <tbody>

                    <c:forEach items="${result.list}" var="orderbill">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${orderbill.sn}</td>
                            <td><fmt:formatDate value="${orderbill.vdate}" pattern="yyyy-MM-dd"/></td>
                            <td>${orderbill.vip.name}</td>
                            <td>${orderbill.totalAmount}</td>
                            <td>${orderbill.totalNumber}</td>
                            <td>${orderbill.inputUser.username}</td>
                            <td>
                                <c:if test="${orderbill.status == 0}">
                                    <span style="color: red;">
                                        挂单
                                    </span>

                                </c:if>

                                <c:if test="${orderbill.status== 1}">
                                    <span style="color: green;">
                                        已出单
                                    </span>
                                </c:if>


                            </td>
                            <td>

                                <c:if test="${orderbill.status  == 0}">
                                    <a href="/orderbill/input.do?id=${orderbill.id}">编辑</a>
                                    <a href="javascript:" class="btn_delete"
                                       data-url="/orderbill/delete.do?id=${orderbill.id}"
                                    >删除</a>

                                </c:if>

                                <c:if test="${orderbill.status== 1}">
                                    <a href="/orderbill/view.do?id=${orderbill.id}">查看</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>


                    </tbody>
                </table>
            </div>
            <%--分页条--%>
            <jsp:include page="/WEB-INF/views/common/common-page.jsp"></jsp:include>
        </div>
    </div>
</form>
</body>
</html>
