<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单明细</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/static/js/myorderbill/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/js/myorderbill/common_style.css" rel="stylesheet" type="text/css">



    <script type="text/javascript" src="/static/js/myorderbill/jquery.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/commonAll.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/jquery.form.min.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/jquery.artDialog.js?skin=aero"></script>

    <script type="text/javascript" src="/static/js/myorderbill/artDialog/jquery.artDialog.source.js"></script>
    <script type="text/javascript" src="/static/js/myorderbill/artDialog/plugins/iframeTools.source.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <script type="text/javascript">


        $(function () {

            //删除一行
            $(".removeItem").click(function () {

                if ($("#edit_table_body tr").size() > 1) {
                    $(this).closest("tr").remove();

                } else {
                    var firstTr = $("#edit_table_body tr:first");

                    firstTr.find("[tag=name]").val("");
                    firstTr.find("[tag=pId]").val("");
                    firstTr.find("[tag=brand]").text("");
                    firstTr.find("[tag=costPrice]").val("");
                    firstTr.find("[tag=number]").val("");
                    firstTr.find("[tag=amount]").text("");
                    firstTr.find("[tag=remark]").val("");
                }
            });


            $(".searchproduct").click(function () {
                //找到当前td  所在的tr
                var targetEle = $(this).closest("tr");
                $.dialog.open("selectProductList.do", {
                    id: 'ajxxList',
                    title: '选择商品',
                    width: 650,
                    height: 460,
                    background: '#000000',
                    close: function () {
                        var jsonProduct = $.dialog.data("jsonObj");
                        targetEle.find("[tag=pid]").val(jsonProduct.id);
                        targetEle.find("[tag=brand]").text(jsonProduct.brandName);
                        targetEle.find("[tag=costPrice]").val(jsonProduct.costPrice);
                        targetEle.find("[tag=name]").val(jsonProduct.name);
                    }
                });

                //价格和数量失去焦点   自动计算
                $("[tag=costPrice],[tag=number]").blur(function () {
                    var targetTr = $(this).closest("tr");
                    var costPrice = parseFloat(targetTr.find("[tag=costPrice]").val()) || 0;
                    var number = parseFloat(targetTr.find("[tag=number]").val()) || 0;
                    var amount = (costPrice * number).toFixed(2);  //保留两位小数
                    targetTr.find("[tag=amount]").text(amount);  //span  使用text
                })


            });


            /*添加明细先将第一个tr复制一份  然后清空   放到对应其中去*/
            $(".appendRow").click(function () {
                var newTr = $("#edit_table_body tr:first").clone(true);
                newTr.find("[tag=name]").val("");
                newTr.find("[tag=pId]").val("");
                newTr.find("[tag=brand]").text("");
                newTr.find("[tag=costPrice]").val("");
                newTr.find("[tag=number]").val("");
                newTr.find("[tag=amount]").text("");
                newTr.find("[tag=remark]").val("");
                newTr.appendTo($("#edit_table_body"))


                /*添加多条数据  添加之前将所有的表单中的name属性  名字盖好*/


            })


        });

        $(function () {

            /*这一步要在ajaxform执行之前执行 否则报错*/
            $("#editForm").submit(function () {
                $.each($("#edit_table_body tr"), function (index, item) {
                    console.log(item);
                    console.log(index);
                    $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                    $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
                    $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
                    $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
                });
            });


        });


            $(function () {
                $("#editForm").ajaxForm(function (data) {
                    if (data.flag) {
                        $.dialog({
                            title: "提示",
                            content: "保存成功",
                            icon: "11",
                            ok: function () {
                                window.location.href = "/orderbill/list.do"
                            }
                        })
                    }
                });
            })
    </script>
</head>
<body>
<form name="editForm" action="/orderbill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${orderbill.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">订单编辑</span>
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
                    <td class="ui_text_rt" width="140">会员</td>
                    <td class="ui_text_lt">

                        <select id="vipId" name="vip.id" class="ui_select03">

                            <c:forEach items="${vips}" var="vip">

                                <option value="${vip.id}" ${orderbill.vip.id == vip.id ? "selecected='selected'":""}>${vip.name}</option>

                            </c:forEach>
                        </select>
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
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
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
                            <c:if test="${empty orderbill}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" name="items.product.id" tag="pid"/>
                                    </td>

                                    <td><span tag="brand"></span></td>

                                    <td><input tag="costPrice" name="items.costPrice"
                                               class="ui_input_txt00"/></td>

                                    <td><input tag="number" name="items.number"
                                               class="ui_input_txt00"/></td>

                                    <td><span tag="amount"></span></td>

                                    <td><input tag="remark" name="items.remark"
                                               class="ui_input_txt02"/></td>

                                    <td>
                                        <a href="javascript:" class="removeItem">删除明细</a>
                                    </td>

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

                                        <td>
                                            <a href="javascript:" class="removeItem">删除明细</a>
                                        </td>
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
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>