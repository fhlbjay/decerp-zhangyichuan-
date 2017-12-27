$(function () {
    //1.变量抽取
    var root_form = $("#root_form");
    var root_datagrid = $("#root_datagrid");
    var root_dialog = $("#root_dialog");


    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //清空表单
            root_form.form("clear");
            //设置标题
            root_dialog.dialog("setTitle", "新增一级目录");
            //打开弹出框
            root_dialog.dialog("open");
        },

        edit: function () {
            var row = root_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //清空表单
            root_form.form("clear");

            //回显数据
            root_form.form("load", row);

            //设置标题
            root_dialog.dialog("setTitle", "编辑目录");
            //打开弹出框
            root_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            root_dialog.dialog("close");
        },
        save: function () {
            root_form.form("submit", {
                url: '/linkageMenu/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            root_dialog.dialog("close");
                            //重新加载数据表格
                            root_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },
            //加载所有分类
        reload:function () {
            //隐藏id为pa2的div
              $("#pa2").hide();
            root_datagrid.datagrid("unselectAll");
              //显示id为pa1的div
              $("#pa1").show();
        },
        //删除按钮
         remove:function(){
        //判断用户是否选中数据
        var row = $("#root_datagrid").datagrid("getSelected");
        if (!row){//没有选中
            //弹出提示信息
            $.messager.alert("温馨提示","请选中一条数据","warning");
            return;
        }
        //发送ajax请求 自动将返回值转成json对象
        $.post("/linkageMenu/delete.do",{id:row.id},function (data) {
            if (data.success){
                //弹出提示信息
                $.messager.alert("温馨提示","删除成功","info",function () {
                    //重新加载数据
                    $("#root_datagrid").datagrid("reload");
                    $("#parent_datagrid").datagrid("reload");
                })
            }else {
                //弹出提示信息
                $.messager.alert("温馨提示",data.message,"error")
            }
        },"json");
    }
    };
    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function() {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    root_datagrid.datagrid({
         fit: true,
        fitColumns: true,
        toolbar: '#root_toolbar',
        striped: true,

        url: '/linkageMenu/view.do',
        singleSelect: true,
        columns: [[
            {field: 'name', title: '一级目录名称', width: 100},
        ]],
        onClickRow: function (index, row) {
            //隐藏id为pa1的div
            $("#pa1").hide();
            //显示id为pa2的div
            $("#pa2").show();
            $("#parent_datagrid").datagrid({
                url:'/dirname/view.do?id='+ row.id
            })
           $("#parent_datagrid").datagrid({
                fit: true,
                fitColumns: true,
                toolbar: '#parent_toolbar',
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
                    {field: 'name', title: '二级目录名称', width: 100},
                ]],

            });
        }
    });
    $("#parentAll_datagrid").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        url: '/product/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'name', title: '商品名称', width: 100},

            {field: 'sn', title: '商品编码', width: 100},
            {
                field: 'root', title: '一级类型', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'parent', title: '二级类型', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            }
        ]]
    });
    root_dialog.dialog({
        width: 300,
        height: 150,
        buttons: '#root_buttons',
        closed: true
    });
});

