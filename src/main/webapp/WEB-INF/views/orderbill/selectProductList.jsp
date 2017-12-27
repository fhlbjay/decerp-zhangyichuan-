<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/static/js/myorderbill/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/js/myorderbill/common_style.css" rel="stylesheet" type="text/css">

    <link href="/static/js/myorderbill/fancybox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/static/js/myorderbill/jquery.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/fancybox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/jquery.artDialog.js?skin=aero"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/jquery.artDialog.source.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/plugins/iframeTools.source.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/commonAll.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <title>选择商品</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>

    <script type="text/javascript">


        /*    function commitSave() {
                var ajxxUuid = jQuery("#grid_ajxx").jqGrid('getGridParam','selrow');
                var nsrmc = $("#grid_ajxx").jqGrid('getCell',ajxxUuid,'xaAy.nsrmc');

                if(ajxxUuid) {
                    artDialog.data("ajxxUuid", ajxxUuid); //将值存起来，供父页面读取
                    artDialog.data("nsrmc", nsrmc);
                    art.dialog.close();
                }else {
                    showTopMsg("请选中一行再提交！", 4000, 'error');
                    return false;
                }
            }*/

        $(function () {
            $(".left2right").click(function () {

                var jsonObj = $(this).data("json");
                $.dialog.data("jsonObj", jsonObj);
                $.dialog.close();

            })
        })


    </script>

    <script type="text/javascript" src="/js/system/common.js"></script>
</head>
<body>
<form id="searchForm" action="#" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>

                    <div id="box_center">
                        关键字
                        <input type="text" class="ui_input_txt02" name="keyword"<%-- value="${qo.keyword}--%>"/>
                        货品品牌
                        <select class="ui_select01" name="deptId">
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}" <%--${qo.deptId == brand.id ?"selected=selected" : ""}--%>>${brand.name}</option>
                            </c:forEach>
                        </select>
                    </div>


                    <div id="box_bottom">

                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <%--<th>图片</th>--%>
                        <th>名称</th>
                        <th>编码</th>
                        <th>品牌</th>
                        <th>成本价</th>
                        <th>售价</th>
                        <th></th>
                    </tr>
                    <tbody>


                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <%--<td>--%>
                                <%--<li><a class="fancybox" href="${product.imagePath}" title="${product.name}"> <img--%>
                                        <%--src="${product.smallImagePath}" class="list_img_min"></a></li>--%>


                            <%--</td>--%>
                            <td>${product.name}</td>
                            <td>${product.sn}</td>
                            <td>${product.brandName}</td>
                            <td>${product.costPrice}</td>
                            <td>${product.salePrice}</td>

                            <td>
                                <input type="button" value="选择该货品" class="left2right btn_select"
                                       data-json='${product.jsonProduct}'>
                            </td>
                        </tr>
                    </c:forEach>


                    </tbody>
                </table>
            </div>
            <%--分页条--%>
            <jsp:include page="/WEB-INF/views/orderbill/commons_page.jsp"></jsp:include>
        </div>
    </div>
</form>
</body>
</html>
