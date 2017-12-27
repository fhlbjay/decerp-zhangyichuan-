$(function () {
    //1.变量抽取
    var product_form = $("#product_form");
    var product_datagrid = $("#product_datagrid");
    var product_dialog = $("#product_dialog");
    var combobox = $("#role_combobox");
    var updownload = $("#up-download-dialog");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //清空表单
            product_form.form("clear");
            //设置标题
            product_dialog.dialog("setTitle", "新增商品");
            //打开弹出框
            product_dialog.dialog("open");
        },
        edit: function () {
            var row = product_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //清空表单
            product_form.form("clear");

            //处理一级菜单数据
            row["root.id"] = row.root.id;
            //处理二级菜单数据
            row["parent.id"] = row.parent.id;
            //处理单位
            row["unit.id"] = row.unit.id;

            //回显数据
            product_form.form("load", row);

            //设置标题
            product_dialog.dialog("setTitle", "修改商品");
            //打开弹出框
            product_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            product_dialog.dialog("close");
        },
        save: function () {
            product_form.form("submit", {
                url: '/product/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            product_dialog.dialog("close");
                            //重新加载数据表格
                            product_datagrid.datagrid("reload");
                            //重新刷新所有页面
                            window.location.reload(force = true);
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        changeState: function () {
            var row = product_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/product/changeState.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                product_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            product_datagrid.datagrid("reload");
        },
        searchForm: function () {
            //获取关键字input
            var keyword = $("#keyword").textbox("getValue");
            //获取一级联动id
            var rootId = $("#parent_root_combox").combobox("getValue");
            //获取二级联动id
            var parentId = $("#parent_dirname_combox").combobox("getValue");
            console.log(rootId);
            console.log(keyword);
            console.log(parentId);

            //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
            product_datagrid.datagrid("load", {
                    keyword: keyword,
                    rootId: rootId,
                    parentId: parentId
            });
        },
        exportXls: function () {
            //获取关键字input
            var keyword = $("#keyword").textbox("getValue");
            //获取一级联动id
            var rootId = $("#parent_root_combox").combobox("getValue");
            //获取二级联动id
            var parentId = $("#parent_dirname_combox").combobox("getValue");

            window.location.href = "/product/exportXls.do?keyword=" + keyword + "&rootId=" + rootId + '&parentId=' + parentId;
        },
        importXls: function () {
            $("#up-download-dialog").dialog("open");
        },
        updownload: function () {
            $("#updownloadForm").form("submit", {
                url: '/product/importXls.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            updownload.dialog("close");
                            //重新加载数据表格
                            product_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },
        //删除按钮
        remove: function () {
            //判断用户是否选中数据
            var row = product_datagrid.datagrid("getSelected");
            if (!row) {//没有选中
                //弹出提示信息
                $.messager.alert("温馨提示", "请选中一条数据", "warning");
                return;
            }
            //发送ajax请求 自动将返回值转成json对象
            $.post("/product/delete.do", {id: row.id}, function (data) {
                if (data.success) {
                    //弹出提示信息
                    $.messager.alert("温馨提示", "删除成功", "info", function () {
                        //重新加载数据
                        product_datagrid.datagrid("reload");
                        //刷新页面
                        window.location.reload(force = true);
                    })
                } else {
                    //弹出提示信息
                    $.messager.alert("温馨提示", data.message, "error")
                }
            }, "json");
        }
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });
    product_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#product_toolbar',
        striped: true,
        url: '/product/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
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
            {field: 'costPrice', title: '进价', width: 100},
            {field: 'salePrice', title: '售价', width: 100},
            {
                field: 'unit', title: '单位', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'productStock', title: '库存数量', width: 100, formatter: function (value, row, index) {
                return value ? value.storeNumber : "";
            }
            },
            {field: 'inputTime', title: '入库时间', width: 100},
            {
                field: 'state', title: '商品状态', width: 100, formatter: function (value, row, index) {
                return value ? "" : "<font color='red'>已下架</font>";
            }
            }
        ]],
        onClickRow: function (index, row) {
            if (row.state) {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '下架'
                })

            } else {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '上架'
                })
            }

        }
    });

    product_dialog.dialog({
        width: 300,
        height: 420,
        buttons: '#product_buttons',
        closed: true
    });
    updownload.dialog({
        width: 200,
        height: 200,
        closed: true,
        buttons: '#updownload_buttons',
        closed: true
    });
    //实现二级联动
    $("#root_combox").combobox({
        //选中下拉框中的一条数据
        onSelect: function (record) {
            $("#parent_combox").combobox({
                url: '/dirname/selectDirnameBymenuId.do?id=' + record.id,
                textField: 'name',
                valueField: 'id',
            })
        }
    });
    //高级查询的二级联动
    $("#parent_root_combox").combobox({
        //选中下拉框中的一条数据
        onSelect: function (record) {
            $("#parent_dirname_combox").combobox({
                url: '/dirname/selectDirnameBymenuId.do?id=' + record.id,
                textField: 'name',
                valueField: 'id',
            })
            product_datagrid.datagrid({
                fit: true,
                fitColumns: true,
                toolbar: '#product_toolbar',
                striped: true,
                url: '/product/view.do?rootId=' + record.id,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
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
                    {field: 'costPrice', title: '进价', width: 100},
                    {field: 'salePrice', title: '售价', width: 100},
                    {
                        field: 'unit', title: '单位', width: 100, formatter: function (value, row, index) {
                        return value ? value.name : "";
                    }
                    },
                    {field: 'stockQuantity', title: '库存数量', width: 100},
                    {field: 'inputTime', title: '入库时间', width: 100},
                    {
                        field: 'state', title: '商品状态', width: 100, formatter: function (value, row, index) {
                        return value ? "" : "<font color='red'>已下架</font>";
                    }
                    }
                ]],
                onClickRow: function (index, row) {
                    if (row.state) {
                        //修改按钮
                        $('#changState_btn').linkbutton({
                            text: '下架'
                        })

                    } else {
                        //修改按钮
                        $('#changState_btn').linkbutton({
                            text: '上架'
                        })
                    }

                }
            });
        }
    });
    $("#parent_dirname_combox").combobox({
        onSelect: function (record) {
            product_datagrid.datagrid({
                fit: true,
                fitColumns: true,
                toolbar: '#product_toolbar',
                striped: true,
                url: '/product/view.do?parentId=' + record.id,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
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
                    {field: 'costPrice', title: '进价', width: 100},
                    {field: 'salePrice', title: '售价', width: 100},
                    {
                        field: 'unit', title: '单位', width: 100, formatter: function (value, row, index) {
                        return value ? value.name : "";
                    }
                    },
                    {field: 'stockQuantity', title: '库存数量', width: 100},
                    {field: 'inputTime', title: '入库时间', width: 100},
                    {
                        field: 'state', title: '商品状态', width: 100, formatter: function (value, row, index) {
                        return value ? "" : "<font color='red'>已下架</font>";
                    }
                    }
                ]],
                onClickRow: function (index, row) {
                    if (row.state) {
                        //修改按钮
                        $('#changState_btn').linkbutton({
                            text: '下架'
                        })

                    } else {
                        //修改按钮
                        $('#changState_btn').linkbutton({
                            text: '上架'
                        })
                    }

                }
            });
        }
    })
});

