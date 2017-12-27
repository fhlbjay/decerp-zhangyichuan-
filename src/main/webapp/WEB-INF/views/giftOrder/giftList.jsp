<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script type="text/javascript" src="/static/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/static/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <title>礼品列表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script type="text/javascript">
        //页面回显
        $(function () {
            $.each($("#pageSize option"), function (index, item) {
                if (item.value ==${qo.pageSize}) {
                    item.selected = true;
                }
            })
            $('.fancybox').fancybox();

        })
        //页面传值
        $(function() {
            $(".btn_select").click(function(){
                var json=$(this).data("json");
                $.dialog.data("json",json);
                $.dialog.close();
                $.dialog.data("json","");
            })
        })
    </script>
</head>
<body>
<form id="searchForm" action="/giftOrder/selectGiftList.do" method="post" enctype="application/x-www-form-urlencoded">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        关键字 <input type="text" class="ui_input_txt02" name="keyword"
                                   value="${qo.keyword}" placeholder="输入礼品名称、编码"/>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
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
                            <td>
                                <input type="button" value="选择该礼品" class="left2right btn_select" data-json='${gift.jsonString}'/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_flt" style="height: 30px; line-height: 30px;">
                    共有
                    <span class="ui_txt_bold04">${result.totalCount}</span>
                    条记录，当前第
                    <span class="ui_txt_bold04">${result.currentPage}/${result.totalPage}</span>
                    页
                </div>
                <div class="ui_frt">
                    <input type="button" value="首页" class="ui_input_btn01 btn_page"
                           data-page="1" />
                    <input type="button" value="上一页" class="ui_input_btn01 btn_page"
                           data-page="${result.prevPage}" />
                    <input type="button" value="下一页" class="ui_input_btn01 btn_page"
                           data-page="${result.nextPage}" />
                    <input type="button" value="尾页" class="ui_input_btn01 btn_page"
                           data-page="${result.totalPage}" />
                    <select id="pageSize" name="pageSize" class="ui_select02">
                        <option value="3">3</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                    </select>
                    转到第
                    <input type="number" name="currentPage" value="${qo.currentPage}"
                           class="ui_input_txt01" min="1" max="${result.totalPage}" />
                    页
                    <input type="submit" class="ui_input_btn01" value="跳转" />
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
