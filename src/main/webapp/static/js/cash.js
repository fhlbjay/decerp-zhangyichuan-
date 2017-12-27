// var isEditingRowIndex;

$(function () {

    var vip_cash = $("#vip_cash");
    var order_datagrid = $("#order_datagrid");
    var product_datagrid = $("#product_datagrid");
    var cash_end = $("#cash_end");
    var cadh_tempbill = $("#cadh_tempbill");
    var cadh_getTempbill = $("#cadh_getTempbill");
    var cadh_clear = $("#cadh_clear");
    var cash_form = $("#cash_form");
    var removeItem = $("#removeItem");
    var clearall = $("#clearall");
    var alient_datagrid = $("#alient_datagrid");
    //vip的查询
    var searchword = $("#searchword");
    //product的查询
    var searchForm = $("#searchForm");
    //总价表单
    var amount_form = $("#amount_form");
    //密码弹出框
    var vipPassword_dialog = $("#vipPassword_dialog");
    //密码弹出框表格
    var vipPassword_form = $("#vipPassword_form");
    // amount_form.form('load', "/cash/amountForm.do?vipid=" + vipid)

    //各种方法
    var objectMethod = {


        //删除一行
        remove: function () {
            if ($("#edit_table_body tr").size() > 1) {
                $(this).closest("tr").remove();

            } else {
                var firstTr = $("#edit_table_body tr:first");

                firstTr.find("[tag=productName]").val("");
                firstTr.find("[tag=pId]").val("");
                firstTr.find("[tag=costPrice]").val("");
                firstTr.find("[tag=number]").val("");
                firstTr.find("[tag=amount]").text("");
                firstTr.find("[tag=remark]").val("");
            }
        },


        //结账
        cash_end: function () {

            //会员卡编号
            var vipid = $("#vipid").val();
            var vipName = $("#vipName").val();

            //使用会员时候 要输入密码
            if (vipid) {
                vipPassword_form.form('load', {
                    vipcardSn: vipid
                });
                vipPassword_dialog.dialog("open")
            } else {
                $.post("/cash/saveOrUpdate.do", {vipid: vipid}, function (data) {
                    if (data.success) {
                        $.messager.alert("提示", "结账成功", "warning", function () {
                            window.location.href = "/cash/view.do"
                        })
                    } else {
                        $.messager.alert("提示", data.message, "warning")
                    }
                })
            }


            /*  cash_form.form('submit', {
                  url: '/cash/saveOrUpdate.do',
                  onSubmit: function (param) {
                      $.each($("#edit_table_body tr"), function (index, item) {
                          console.log(item);
                          console.log(index);
                          $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                          $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
                          $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
                          $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
                      });


                  },
                  success: function (data) {
                      //非常重要
                      var data = $.parseJSON(data);
                      if (data.success) {
                          $.messager.alert("提示", "保存成功", "warning", function () {
                              window.location.href = "/cash/view.do"
                          })
                      } else {
                          $.messager.alert("提示", data.message, "warning")
                      }

                  }
              });
  */

        },

        //会员结账页面 确定按钮
        cash_password: function () {
            var vipid = $("#vipid").val();
            var vipcardSn = $("#vipcardSn").val();
            var password = $("#password").val();


            vipPassword_form.form('submit', {
                url: "/cash/checkVip.do",
                /*onSubmit: function (param) {
                    param["vipcardSn"] = vipcardSn;
                    param["password"] = password;
                },*/
                success: function (data) {

                    //非常重要
                    var data = $.parseJSON(data);

                    if (data.success) {
                        vipPassword_dialog.dialog("close");

                        $.post("/cash/saveOrUpdate.do", {vipid: vipid}, function (data) {
                            if (data.success) {
                                $.messager.alert("提示", "结账成功", "warning", function () {
                                    window.location.href = "/cash/view.do"
                                })
                            } else {
                                $.messager.alert("提示", data.message, "warning")
                            }
                        })
                    } else {
                        $.messager.alert("提示", data.message, "warning")
                    }
                }
            });


        },
        //会员页面取消按钮
        cash_cancel: function () {
            vipPassword_dialog.dialog("close")
        },
        //删除其中的一行
        cadh_removeOne: function () {
            var row = alient_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert("提示", "请选择一条数据", "warning")
            } else {
                $.messager.confirm('确认', '您确认想要删除吗？', function (r) {
                        if (r) {
                            $.get("/cash/removeOne.do", {productId: row.productid}, function (data) {
                                //刷新总金额
                                $("#amount_form").form('load', "/cash/amountFormGetemp.do");
                                alient_datagrid.datagrid();
                                // amountAll();
                            })
                        }
                    }
                )
            }


        },
        //挂单
        cadh_tempbill: function () {
            var vipid = $("#vipid").val();

            $.messager.confirm('确认', '您确认想要挂单吗？', function (r) {
                if (r) {
                    $.post("/cash/tempbill.do", {vipid: vipid}, function (data) {
                        if (data.success) {
                            $.messager.alert("提示", "挂单成功", "warning", function () {
                                // alient_datagrid.datagrid()
                                window.location.href = "/cash/view.do"
                            })
                        } else {
                            $.messager.alert("提示", data.message, "warning")
                        }
                    })
                }
            })


            /* $.messager.confirm('确认', '您确认想要挂单吗？', function (r) {
                 if (r) {
                     cash_form.form('submit', {
                         url: '/cash/tempbill.do',
                         onSubmit: function (param) {
                             $.each($("#edit_table_body tr"), function (index, item) {
                                 console.log(item);
                                 console.log(index);
                                 $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                                 $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
                                 $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
                                 $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
                             });


                         },
                         success: function (data) {

                             //非常重要
                             var data = $.parseJSON(data);


                             if (data.success) {
                                 $.messager.alert("提示", "挂单成功", "warning", function () {
                                     window.location.href = "/cash/view.do"

                                 })
                             } else {
                                 $.messager.alert("提示", data.message, "warning")
                             }

                         }
                     });
                 }
             });*/

        },
        //取单
        cadh_getTempbill: function () {
            $("#temp_datagrid").datagrid("load");

            $("#gettempbill_dialog").dialog("open")
        },

        //清空
        clearall: function () {
            $.messager.confirm('确认对话框', '您确认清空该单吗？', function (r) {
                if (r) {
                    $.post("/cash/clearall.do", function (data) {
                        if (data.success) {
                            $.messager.alert("提示", "清空成功", "warning", function () {

                                alient_datagrid.datagrid("load");
                                cash_form.form("load", "/cash/amountFormGetemp.do")
                                window.location.href = "/cash/view.do"
                            })
                        } else {
                            $.messager.alert("提示", data.message, "warning")
                        }
                    })
                }
            });


        },

        //取单界面  取单按钮
        getTemp: function () {
            var row = $("#temp_datagrid").datagrid("getSelected");
            if (!row) {
                $.messager.alert("提示", "请选择一条数据", "warning")
            } else {
                $.get("/cash/getTempbill.do", {id: row.id}, function (data) {
                    if (data.success) {
                        $("#gettempbill_dialog").dialog("close");

                        //取单时候对会员总价界面进行初始化
                        $("#amount_form").form('load', "/cash/amountFormGetemp.do")
                        alient_datagrid.datagrid()
                    } else {
                        $.messager.alert("提示", data.msg, "warning")
                    }
                }, "json")
            }


        },
        //取单界面  取消
        getTempcancel: function () {
            $("#gettempbill_dialog").dialog("close")
        },
        //取单界面  删除
        getTempDelete: function () {
            var row = $("#temp_datagrid").datagrid("getSelected");
            if (!row) {
                $.messager.alert("提示", "请选择一条数据", "warning")
            } else {

                $.messager.confirm('确认对话框', '您确认删除该单吗？', function (r) {
                    if (r) {

                        $.post("/cash/deleteOneTempbill.do", {id: row.id}, function (data) {
                            if (data.success) {
                                $("#temp_datagrid").datagrid("load");

                            } else {
                                $.messager.alert("提示", data.msg, "warning")
                            }
                        }, "json")


                    }
                });


            }
        },
        //会员界面查询
        searchword: function () {
            vip_cash.datagrid("load", {
                "keyword": $("#keyword").textbox("getValue")

            })
        },
        searchForm: function () {
            //获取关键字input
            var keyword = $("#productkeyword").textbox("getValue");
            //获取一级联动id
            /*      var rootId = $("#parent_root_combox").combobox("getValue");
                  //获取二级联动id
                  var parentId = $("#parent_dirname_combox").combobox("getValue");
      */

            //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
            product_datagrid.datagrid("load", {
                keyword: keyword
                /*       rootId: rootId,
                       parentId: parentId*/
            });
        }


    };

    //调用各种方法
    $("[data-cmd]").click(function () {
            var methodname = $(this).data("cmd");
            //调用方法
            objectMethod[methodname]()
        }
    );


    //初始化中间选项卡

    $("#mytabs").tabs({
        fit: true
    })


    //会员界面
    vip_cash.datagrid({
        // width: 300 ,
        // height: 400,
        url: '/vip/view.do',
        fit: true,
        fitColumns: true,
        pagination: true,
        /*pagePosition: 'top',*/
        toolbar: '#vip_buttons',
        columns: [[
            {
                field: 'vipcard1', title: '会员卡号', width: 100, formatter: function (value, row, index) {
                return row ? row.vipcard.sn : "";
            }
            },
            {field: 'name', title: '会员姓名', width: 100},
            {field: 'vipgrade', title: '会员等级', width: 100},
            {field: 'tel', title: '电话', width: 300},
            {
                field: 'vipcard2', title: '会员积分', width: 100, formatter: function (value, row, index) {
                return row ? row.vipcard.integral : "";
            }
            },
            {
                field: 'vipcard3', title: '可用余额', width: 100, formatter: function (value, row, index) {
                return row ? row.vipcard.balance : "";
            }
            },
            {
                field: 'vipcard4', title: '会员折扣', width: 100, formatter: function (value, row, index) {
                return row ? row.vipcard.discount : "";
            }
            },
            {
                field: 'state', title: '状态', width: 100, formatter: function (value, row, index) {
                return value ? "正常" : "<font color='red'>丢失</font>";
            }
            }


        ]],

        striped: true,
        rownumbers: true,
        singleSelect: true,
        //    点击将数据放入左边的表单中
        onDblClickRow: function (index, row) {
            edit_vip(row);
            //调用计算总价方法
            var vipid = row.id;
            $("#amount_form").form('load', "/cash/amountForm.do?vipid=" + vipid)
            // amountAll();

        }
    });
    //会员界面

    //所有商品
    product_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        // toolbar: '#product_toolbar',
        striped: true,
        url: '/cash/productList.do',
        pagination: true,
        /*pagePosition: 'top',*/
        rownumbers: true,
        toolbar: '#product_toolbar',
        singleSelect: true,
        columns: [[
            {field: 'id', title: '商品id', width: 100},
            {field: 'name', title: '商品名称', width: 100},

            {
                field: 'root', title: '一级类型', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'parent', title: '二级类型', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {field: 'sn', title: '商品编码', width: 100},
            {field: 'salePrice', title: '售价', width: 100},
            {
                field: 'unit', title: '单位', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'state', title: '商品状态', width: 100, formatter: function (value, row, index) {
                return value ? "" : "<font color='red'>已下架</font>";
            }
            }
        ]],
        onClickRow: function (index, row) {
            //发送ajax请求到后台 然后存入购物车对象
            $.post("/cash/shopadd.do", {
                id: row.id

            }, function (data) {
                if (data) {

                    alient_datagrid.datagrid({url: "/cash/shopAll.do"});


                    //调用计算总价方法
                    amountAll();

                } else {
                    $.messager.alert("提示", data.message, "warning")
                }
            })
        }
    });
    //所有商品


    //结账界面输入密码的弹窗
    vipPassword_dialog.dialog({
        modal: true,
        title: "结账界面",
        width: 300,
        height: 300,
        buttons: '#vipPassword_buttons',
        closed: true,
        collapsible: true
    });


    //取单
    $("#gettempbill_dialog").dialog({
        modal: true,
        title: "取单界面",
        width: 600,
        height: 500,
        closed: true,
        buttons: '#temp_buttons',
        collapsible: true
    });

//    取单界面数据初始化
    $("#temp_datagrid").datagrid({
        // width: 300 ,
        // height: 400,
        url: '/cash/cashGetTemp.do',
        fit: true,
        fitColumns: true,
        pagination: true,

        pagePosition: 'top',
        columns: [[

            {field: 'id', checkbox: true},
            {
                field: 'vip', title: '会员名', width: 100, formatter: function (value, row, index) {
                return value ? value.name : ""
            }
            },
            {field: 'totalAmount', title: '总金额', width: 100},
            {field: 'totalNumber', title: '总数量', width: 100},
            {
                field: 'inputUser', title: '录入人', width: 100, formatter: function (value, row, index) {
                return value ? value.username : ""
            }
            },
            {field: 'vdate', title: '登记时间', width: 100}
        ]],

        striped: true,
        rownumbers: true,
        singleSelect: true,
        onClickRow: function (index, row) {
            console.log("1")
        }

    });

    // 初始化表格
    var editIndex = undefined;
    alient_datagrid.datagrid({
        // width: 600 ,
        // height: 300,
        fit: true,
        url: "/cash/shopAll.do",
        fitColumns: true,
        striped: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[

            {field: 'productid', checkbox: true},
            {field: 'name', title: '产品名', width: 100},
            {field: 'costPrice', title: '价格', width: 100},
            {field: 'number', title: '数量', width: 100, editor: "numberbox"},
            {field: 'amount', title: '小计', width: 100, editor: "numberbox"},
            // {field: 'deposit', title: '折扣额', width: 100, editor: "numberbox"},
            {field: 'remark', title: '备注', width: 100, editor: "text"}

        ]],
        onClickCell: function (index, field, value) {
            if (editIndex == undefined) {
                $(this).datagrid('beginEdit', index);
                var ed = $(this).datagrid('getEditor', {index: index, field: field});
                //$(ed.target).focus();
                editIndex = index;
                //alert("点击触发editIndex:" + editIndex);

            }
            else if (editIndex != undefined) {//如果不相等，说明已经打开编辑器了，需要关闭编辑器

                $(this).datagrid('endEdit', editIndex);
                editIndex = undefined;
                //alert("关闭编辑器");
            }
        },
        onDblClickRow: function (index, row) {
            if (row.costPrice != 0 && row.number != 0) {
                row.amount = row.costPrice * row.number;
                $(this).datagrid("refreshRow", index)
            }
        }


    });
    amountFormGetemp();
    // amountAll();


});

//加载运行结束


function edit_vip(row) {

    $("#vipSn").text(row.vipcard.sn);
    $("#vipName").text(row.name);
    $("#vitMoney").text(row.vipcard.balance);
    $("#vitCut").text(row.vipcard.discount);
    $("#vipid").val(row.id);

}


function amountAll() {
    //总价表单初始化
    var vipid = $("#vipid").val();
    $("#amount_form").form('load', "/cash/amountForm.do?vipid=" + vipid)
}

function amountFormGetemp() {
    $("#amount_form").form('load', "/cash/amountFormGetemp.do");
}










