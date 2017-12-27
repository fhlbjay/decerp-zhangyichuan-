<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/static/css/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/commonAll.js"></script>
    <script type="text/javascript"
            src="/static/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <link href="/static/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript"
            src="/static/js/plugins/fancyBox/jquery.fancybox.pack.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>

    <title>礼品列表</title>
    <style>
        .alt td {
            background: black !important;
        }
        .name-money{
            padding-left: 15px !important;
        }
    </style>
    <script type="text/javascript">
        //页面回显
        $(function () {
            $.each($("#pageSize option"), function (index, item) {
                if (item.value ==${qo.pageSize}) {
                    item.selected = true;
                }
            });
            $('.fancybox').fancybox();
        })
    </script>
</head>
<body>
<form id="searchForm" action="/gift/list.do" method="post" enctype="application/x-www-form-urlencoded">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        关键字 <input type="text" class="ui_input_txt02" name="keyword"
                                   value="${qo.keyword}" placeholder="输入礼品名称、编码"/>
                        <input type="submit" value="查询" class="ui_input_btn01"/> <input
                            type="button" value="新增" class="ui_input_btn01 btn_input"
                            data-url="/gift/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%"
                       align="center" border="0">
                    <tr>
                        <th></th>
                        <th>图片</th>
                        <th>礼品名</th>
                        <th>礼品编号</th>
                        <th>积分</th>
                        <th>剩余数量</th>
                        <th>总量</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach var="gift" items="${result.list}" varStatus="vs">
                        <tr>
                            <td>
                                ${vs.count}
                            </td>
                            <td>
                                <a class="fancybox" href="${gift.image}"
                                   data-fancybox-group="gallery" title="${gift.name}">
                                    <img src="${gift.smallImagePath}" class="list_img_min"/>
                                </a></td>
                            <td>${gift.name}</td>
                            <td>${gift.sn}</td>
                            <td><span style="color: #0081c2">${gift.integral}</span></td>
                            <td>${gift.surplus}</td>
                            <td>${gift.count}</td>
                            <td><a href="javascript:" class="btn_input"
                                   data-url="/gift/input.do?id=${gift.id}">编辑</a> <a
                                    href="javascript:" class="btn_delete"
                                    data-url="/gift/delete.do?id=${gift.id}&imagePath=${gift.image}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@ include file="/WEB-INF/views/common/common-page.jsp" %>
        </div>
    </div>
</form>
</body>
</html>
