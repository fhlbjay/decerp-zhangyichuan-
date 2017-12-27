$(function () {
    //1.变量抽取
    var pot_form = $("#pot_form");
    var pot_datagrid = $("#pot_datagrid");
    var pot_dialog = $("#pot_dialog");
    var pot_search = $("#pot_search");
    var stock_dialog = $("#stock_dialog");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //显示密码框
            $("#password").show();

            //清空表单
            pot_form.form("clear");
            //设置标题
            pot_dialog.dialog("setTitle", "新增供应商");
            //打开弹出框
            pot_dialog.dialog("open");
        },

        edit: function () {
            var row = pot_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            //清空表单
            pot_form.form("clear");

            //回显数据
            pot_form.form("load", row);

            //设置标题
            pot_dialog.dialog("setTitle", "编辑供应商");
            //打开弹出框
            pot_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            pot_dialog.dialog("close");
        },
        save: function () {
            pot_form.form("submit", {
                url: '/depot/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            pot_dialog.dialog("close");
                            //重新加载数据表格
                            pot_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        delete: function () {

            var row = pot_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/depot/delete.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                pot_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },
        
        changeStatus: function () {

            var row = pot_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/depot/changeStatus.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                pot_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            pot_datagrid.datagrid("load");
        },
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    pot_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#pot_toolbar',
        striped: true,
        url: '/depot/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'id', width: 100,checkbox:true},
            {field: 'name', title: '仓库名称', width: 100},
            {field: 'sn', title: '仓库编码', width: 100},
            {field: 'linkman', title: '联系人', width: 100},
            {field: 'tel', title: '联系电话', width: 100},
            {field: 'address', title: '仓库地址', width: 100},
            {field: 'inputTime', title: '添加时间', width: 100},
            {field: 'status', title: '仓库状态', width: 100, formatter: function (value, row, index) {
                return value ? "<font color='green'>启用</font>" : "<font color='red'>暂停</font>";
                }
            },
            {field: 'cz', title: '操作', width: 100,formatter:function (value,row,index) {
                return "<a href='#' onclick='getStock()'>查看库存<a/>";
            }},
        ]],
        onClickRow: function (index, row) {
            if (row.status) {
                //修改按钮
                $('#changStatus_btn').linkbutton({
                    text: '暂停'
                })

            } else {
                //修改按钮
                $('#changStatus_btn').linkbutton({
                    text: '启用'
                })
            }
        }
    });

    pot_dialog.dialog({
        width: 300,
        height: 380,
        buttons: '#pot_buttons',
        closed: true
    });

    pot_search.searchbox({
        width:230,
        prompt:'输入仓库名称、编码、联系人、电话',
        searcher:function (value,name) {
            pot_datagrid.datagrid("load",{
                "keyword":value
            })
        }
    })

});

function getStock() {
    var row = $("#pot_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }

    $("#stock_dialog").dialog({
        width: 800,
        height: 500,
        title:'库存列表'
    })

    $("#stock_datagrid").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        url: '/depot/getStock.do?id='+row.id,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'product', title: '商品名称', width: 100,formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {field: 'price', title: '进价', width: 100},
            {field: 'storeNumber', title: '数量', width: 100},
            {field: 'amount', title: '金额', width: 100},
            {field: 'inputTime', title: '仓库名称', width: 100},
        ]]
    })

}

