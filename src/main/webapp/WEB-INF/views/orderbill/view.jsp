<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/jquery/plugins/jQueryForm/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/jquery/plugins/artDialog/jquery.artDialog.js?skin=aero"></script>
    <script type="text/javascript" src="/js/jquery/plugins/artDialog/jquery.artDialog.source.js"></script>
    <script type="text/javascript" src="/js/jquery/plugins/artDialog/plugins/iframeTools.source.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
     <script type="text/javascript">


    </script>
</head>
<body>
<form name="editForm" action="/orderbill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${orderbill.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">订单查看</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">

                <input type="hidden" value="${orderbill.id}" name="id">

                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${orderbill.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <input type="text" value="${orderbill.supplier.name}" class="ui_input_txt02"/>
                    </td>

                </tr>


                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <input name="vdate" value="<fmt:formatDate value="${orderbill.vdate}" pattern="yyyy-MM-dd"/>"
                               class="ui_input_txt02"/>
                    </td>
                </tr>


                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <tr>
                                <c:if test="${empty orderbill}">
                            <tr>
                                <td></td>
                                <td>
                                    <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                    <input type="hidden" name="items.product.id" tag="pid"/>
                                </td>

                                <td><span tag="brand">${orderbill.product.brandName}</span></td>

                                <td><input tag="costPrice" name="items.costPrice"
                                           class="ui_input_txt00"/></td>

                                <td><input tag="number" name="items.number"
                                           class="ui_input_txt00"/></td>

                                <td><span tag="amount"></span></td>

                                <td><input tag="remark" name="items.remark"
                                           class="ui_input_txt02"/></td>
                            </tr>

                            </c:if>

                            <c:if test="${not empty orderbill}">
                                <c:forEach items="${orderbill.items}" var="item">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"
                                                   value=" ${item.product.name}"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <input type="hidden" name="items.product.id" tag="pid"
                                                   value="${item.product.id}"/>
                                        </td>

                                        <td><span tag="brand">${item.product.brandName}</span></td>

                                        <td><input tag="costPrice" name="items.costPrice" value="${item.costPrice}"
                                                   class="ui_input_txt00"/></td>

                                        <td><input tag="number" name="items.number" value="${item.number}"
                                                   class="ui_input_txt00"/></td>

                                        <td><span tag="amount"></span>${item.amount}</td>

                                        <td><input tag="remark" name="items.remark" value="${item.remark}"
                                                   class="ui_input_txt02"/></td>
                                    </tr>


                                </c:forEach>
                            </c:if>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>


                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="返回" class="ui_input_btn01" onclick="window.history.back()"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>