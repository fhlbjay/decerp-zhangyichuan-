$(function () {
    //1.变量抽取
    var in_form = $("#in_form");
    var in_datagrid = $("#in_datagrid");
    var in_dialog = $("#in_dialog");
    var item_datagrid = $("#item_datagrid");
    var item_dialog = $("#item_dialog");


    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        cancel: function () {
            //关闭弹出框
            in_dialog.dialog("close");
        },
        save: function () {
            in_form.form("submit", {
                url: '/inventory/changeNumber.do?pId='+row.product.id+'&dId='+row.depot.id+'&psId='+row.productStock.id+'&iId='+row.id+'&number='+row.productStock.storeNumber,
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            in_dialog.dialog("close");
                            //重新加载数据表格
                            in_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }
            })
        },
        close:function () {
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


    in_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        url: '/inventory/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'psn', title: '商品编码', width: 100,formatter: function (value, row, index) {
                return row ? row.product.sn : "";
            }
            },
            {field: 'pname', title: '商品名称', width: 100,formatter: function (value, row, index) {
                return row ? row.product.name : "";
            }
            },
            {field: 'psnumber', title: '库存数量', width: 100,formatter: function (value, row, index) {
                return row ? row.productStock.storeNumber : "";
            }
            },
            {field: 'depot', title: '仓库', width: 100,formatter: function (value, row, index) {
                return value ? value.name : "";
            }},
            {
                field: 'pstime', title: '入库时间', width: 100, formatter: function (value, row, index) {
                return row ? row.productStock.inputTime : "";
            }
            },
            {field: 'inputTime', title: '上次盘点时间', width: 100},
            {
                field: 'cz', title: '操作', width: 150, formatter: function (value, row, index) {
                    return "<a href='#' onclick='selectNumber()'>数量确认<a/>&nbsp;&nbsp;<a href='#' onclick='changeNumber()'>数量调整<a/>&nbsp;&nbsp;<a href='#' onclick='getItem()'>盘点记录<a/>";
                }
            }
        ]],
    });

    in_dialog.dialog({
        width: 250,
        height: 300,
        buttons: '#in_buttons',
        closed: true
    });

    item_dialog.dialog({
        width: 800,
        height: 600,
        title:'盘点记录',
        buttons: '#item_buttons',
        closed: true
    })

});

function selectNumber() {
    var row = $("#in_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }
    $.get('/inventory/selectNumber.do',{pId:row.product.id,dId:row.depot.id,iId:row.id,number:row.productStock.storeNumber},function (data) {
        $.messager.alert('温馨提示',data.message,'info',function () {
            $("#in_datagrid").datagrid("reload");
        })
    },"json")
}

function changeNumber() {
    row = $("#in_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }

    $("#in_form").form("clear")

    row["product.name"] = row.product.name;
    row["productStock.storeNumber"] = row.productStock.storeNumber;
    row["depot.name"] = row.depot.name;

    $("#in_form").form("load", row);

    $("#in_dialog").dialog("setTitle","库存盘点");

    $("#in_dialog").dialog("open");
}

function getItem() {
    var row = $("#in_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }

    $("#item_dialog").dialog("open");

    $("#item_datagrid").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        url: '/inventory/getItems.do?id='+row.id,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'oldNumber', title: '原有库存', width: 100},
            {field: 'nowNumber', title: '修改数量', width: 100},
            {field: 'inputUser', title: '操作人员', width: 100,formatter: function (value, row, index) {
                return value ? value.username : "";
            }
            },
            {field: 'inputTime', title: '盘点时间', width: 100}
        ]]
    })
}



