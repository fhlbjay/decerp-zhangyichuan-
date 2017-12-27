$(function () {
    //1.变量抽取
    var pur_form = $("#pur_form");
    var pur_datagrid = $("#pur_datagrid");
    var pur_dialog = $("#pur_dialog");
    var combobox = $("#role_combobox");
    var updownload = $("#up-download-dialog");
    var bill_datagrid = $("#bill_datagrid");
    var item_dialog = $("#item_dialog");
    var item_datagrid = $("#item_datagrid");
    var pur_d_combobox = $("#pur_d_combobox");
    var pur_s_combobox = $("#pur_s_combobox");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //清空表单
            pur_form.form("clear");

            //清空明细
            bill_datagrid.datagrid("loadData",[]);

            //设置标题
            pur_dialog.dialog("setTitle", "新增订单");
            //打开弹出框
            pur_dialog.dialog("open");
        },

        edit: function () {
            var row = pur_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            //清空表单
            pur_form.form("clear");

            //清空明细
            bill_datagrid.datagrid("loadData",[]);

            row["depot.id"] = row.depot.id;
            row["supplier.id"] = row.supplier.id;

            //bill_datagrid.datagrid("load")

            bill_datagrid.datagrid({
                //url:row.items
                url:'/purchasingOrderBill/getItemsById.do?id=' + row.id
            })

            //回显数据
            pur_form.form("load", row);

            //设置标题
            pur_dialog.dialog("setTitle", "编辑订单");
            //打开弹出框
            pur_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            pur_dialog.dialog("close");
        },
        save: function () {
            pur_form.form("submit", {
                url: '/purchasingOrderBill/saveOrUpdate.do',
                onSubmit: function(param){
                    var dRow =pur_d_combobox.combobox("getValue");
                    param["depot.id"] = dRow;
                    var sRow =pur_s_combobox.combobox("getValue");
                    param["supplier.id"] = sRow;
                    var items = bill_datagrid.datagrid("getRows")
                    for(var i = 0;i<items.length;i++){
                        param["items["+i+"].costPrice"]=items[i].costPrice;
                        param["items["+i+"].number"]=items[i].number;
                        param["items["+i+"].amount"]=items[i].amount;
                        param["items["+i+"].remark"]=items[i].remark;
                        param["items["+i+"].product.id"]=items[i].id;
                    }
                },
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            pur_dialog.dialog("close");
                            //重新加载数据表格
                            pur_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        delete: function () {

            var row = pur_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/purchasingOrderBill/delete.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                pur_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            pur_datagrid.datagrid("reload");
        },

        searchForm: function () {
            //获取关键字input
            var keyword = $("#keyword").textbox("getValue");
            var beginDate = $("#beginDate").textbox("getValue");
            var endDate = $("#endDate").textbox("getValue");

            //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
            pur_datagrid.datagrid("load", {
                keyword: keyword,
                beginDate:beginDate,
                endDate:endDate
            });
        },
        exportXls:function () {
            window.location.href="/purchasingOrderBill/exportXls.do";
        },
        importXls:function () {
            $("#up-download-dialog").dialog("open");
        },
        updownload:function(){
            $("#updownloadForm").form("submit", {
                url: '/purchasingOrderBill/importXls.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            updownload.dialog("close");
                            //重新加载数据表格
                            pur_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },
        attend:function(){
            item_dialog.dialog("open");
        },
        remove:function () {
            var row = bill_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            var index = bill_datagrid.datagrid("getRowIndex",row);
            bill_datagrid.datagrid("deleteRow",index);
        },
        sure:function(){
            item_dialog.dialog("close")
        }
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    pur_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#pur_toolbar',
        striped: true,
        url: '/purchasingOrderBill/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'sn', title: '订单编号', width: 100},
            {field: 'vdate', title: '业务时间', width: 100},
            {
                field: 'supplier', title: '供应商', width: 100, formatter: function (value, row, index) {
                    return value ? value.name : "";
                }
            },
            {field: 'totalNumber', title: '总数量', width: 100},
            {field: 'totalAmount', title: '总金额', width: 100},
            {
                field: 'depot', title: '仓库', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'inputUser', title: '录入人', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
            }
            },
            {
                field: 'auditor', title: '审核人', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
            }
            },
            {
                field: 'status', title: '审核状态', width: 100, formatter: function (value, row, index) {
                return value ? "<font color='green'>已审核入库</font>" : "<font color='red'>待审核</font>";
            }
            },
            {
                field: 'returnState', title: '退回状态', width: 100, formatter: function (value, row, index) {
                return value ? "<font color='red'>已退货</font>" : "<font color='green'>未退货</font>";
            }
            },
            {field: 'asd', title: '操作', width: 200,formatter: function (value, row, index) {
                return "<a href='#' onclick='open_items()'>查看明细<a/>&nbsp;&nbsp;<a href='#' onclick='audit()'>审核<a/>&nbsp;&nbsp;<a href='#' onclick='returnBill()'>退货<a/>";
            }
            },
        ]],
    });
    var editIndex = undefined;

    bill_datagrid.datagrid({
        fit:true,
        fitColumns: true,
        striped: true,
        title:'商品明细',
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'id', title: '商品ID', width: 100},
            {field: 'sn', title: '商品编号', width: 100},
            {field: 'name', title: '商品名称', width: 100},
            {field: 'costPrice', title: '进价', width: 100,editor:'numberbox'},
            {field: 'number', title: '数量', width: 100,editor:'numberbox'},
            {field: 'amount', title: '金额', width: 100,editor:{
                type:'numberbox',
            }},
            {field: 'remark', title: '备注', width: 100,editor:'text'},
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
        onDblClickRow:function (index, row) {
            if(row.costPrice != 0 && row.number != 0){
                row.amount = row.costPrice * row.number;
                $(this).datagrid("refreshRow",index)
            }
        },
    });

    item_datagrid.datagrid({
        fit:true,
        fitColumns: true,
        striped: true,
        url: '/product/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        buttons: '#item_toolbar',
        scrollbarSize:0,
        columns: [[
            {field: 'id', title: '商品ID', width: 100},
            {field: 'sn', title: '商品编码', width: 100},
            {field: 'name', title: '商品名称', width: 100},
            {field: 'costPrice', title: '商品进价', width: 100},
        ]],
        onSelect:function(index,row){
            var rows = bill_datagrid.datagrid("getRows");
            var index = $.inArray(row,rows);
            if(index == -1){
                bill_datagrid.datagrid("appendRow",row)
            }
        }
    });


    pur_dialog.dialog({
        width: 1000,
        height: 500,
        buttons: '#pur_buttons',
        toolbar: '#pur_toolbar2',
        closed: true
    });
    updownload.dialog({
        width:200,
        height:200,
        buttons: '#updownload_buttons',
        closed: true
    });

    item_dialog.dialog({
        width:800,
        height:400,
        title:'新增',
        buttons: '#item_buttons',
        closed: true
    })

});

//查看明细
function open_items() {
    var row = $("#pur_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    };

    $("#getItem_dialog").dialog({
        width: 800,
        height: 400,
        title:'商品明细',
    });

    $("#getItem_datagrid").datagrid({
        fit:true,
        fitColumns: true,
        striped: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        url:'/purchasingOrderBill/getItemsById.do?id=' + row.id,
        columns: [[
            {field: 'id', title: '商品ID', width: 100},
            {field: 'sn', title: '商品编号', width: 100},
            {field: 'name', title: '商品名称', width: 100},
            {field: 'costPrice', title: '进价', width: 100,editor:'numberbox'},
            {field: 'number', title: '数量', width: 100,editor:'numberbox'},
            {field: 'amount', title: '金额', width: 100,editor:{
                type:'numberbox',
            }},
            {field: 'remark', title: '备注', width: 100,editor:'text'},
        ]],
    });
}

//审核
function audit() {
    var row = $("#pur_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }

    $.messager.confirm('确认', '您确认审核该数据吗？', function (r) {
        if (r) {
            //发送请求修改状态
            $.get("/purchasingOrderBill/audit.do", {id: row.id}, function (data) {
                if (data.success) {
                    $.messager.alert('温馨提示', "审核成功!", 'info', function () {
                        //重新加载数据表格
                        $("#pur_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert('温馨提示', data.message, 'error');
                }
            }, "json")
        }
    });
}

//退货
function returnBill() {
    var row = $("#pur_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }

    $.messager.confirm('确认', '您确认退货吗？', function (r) {
        if (r) {
            //发送请求修改状态
            $.get("/purchasingOrderBill/returnBill.do", {id: row.id}, function (data) {
                if (data.success) {
                    $.messager.alert('温馨提示', "退货成功!", 'info', function () {
                        //重新加载数据表格
                        $("#pur_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert('温馨提示', data.message, 'error');
                }
            }, "json")
        }
    });
}