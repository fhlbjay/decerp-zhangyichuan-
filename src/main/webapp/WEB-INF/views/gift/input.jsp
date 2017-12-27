<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/static/css/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/static/css/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/commonAll.js"></script>
    <script type="text/javascript"
            src="/static/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript"
            src="/static/js/plugins/jquery-form/jquery.form.min.js"></script>
    <%--easyUI增强--%>
    <link href="/jquery-easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="/jquery-easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/jquery-easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
    <script>
        $(function () {
            $("#cancelbutton").click(function () {
                window.history.back();
            })
        })

    </script>
</head>
<body>
<form name="editForm" action="/gift/saveOrUpdate.do"
      method="post" id="editForm" data-url="/gift/list.do" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${entity.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">礼品编辑</span>
            <div id="page_close">
                <a> <img src="/images/common/page_close.png" width="20"
                         height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left"
                   border="0">
                <tr>
                    <td class="ui_text_rt" width="140">礼品编号</td>
                    <td class="ui_text_lt"><input name="sn"
                                                  class="ui_input_txt02" value="${entity.sn}" required/></td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">礼品名称</td>
                    <td class="ui_text_lt"><input name="name" value="${entity.name}"
                                                  class="ui_input_txt02" required/></td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所需积分</td>
                    <td class="ui_text_lt"><input name="integral"
                                                  class="ui_input_txt02" value="${entity.integral}" required/></td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">礼品数量</td>
                    <td class="ui_text_lt"><input name="count" value="${entity.count}"
                                                  class="ui_input_txt02" required/></td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">礼品单位</td>
                    <td class="ui_text_lt">
                        <select class="ui_select01" name="unit">
                            <c:forEach var="unit" items="${entity.getUnits()}">
                                <option ${entity.unit==unit.key?'selected':''} value="${unit.key}">${unit.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <c:if test="${entity.id!=null}">
                    <tr>
                        <td class="ui_text_rt" width="140">剩余数量</td>
                        <td class="ui_text_lt"><input name="surplus" value="${entity.surplus}"
                                                      class="ui_input_txt02" required/></td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">礼品图片</td>
                    <td class="ui_text_lt">
                        <input type="file" name="pic" class="ui_file" accept="image/*">
                    </td>
                </tr>
                <c:if test="${entity.image!=null}">
                    <tr>
                        <td class="ui_text_rt" width="140">原图片</td>
                        <td class="ui_text_lt">
                            <input type="hidden" name="image" value="${entity.image}" class="ui_file" required>
                            <img alt="" src="${entity.image}" class="list_img">
                        </td>
                    </tr>
                </c:if>


                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">&nbsp;<input type="submit"
                                                        value="确定保存" class="ui_input_btn01"/> &nbsp;<input
                            id="cancelbutton" type="button" value="返回" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>