$(function () {
    //1.变量抽取
    var sup_form = $("#sup_form");
    var sup_datagrid = $("#sup_datagrid");
    var sup_dialog = $("#sup_dialog");
    var sup_search = $("#sup_search");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //显示密码框
            $("#password").show();

            //清空表单
            sup_form.form("clear");
            //设置标题
            sup_dialog.dialog("setTitle", "新增供应商");
            //打开弹出框
            sup_dialog.dialog("open");
        },

        edit: function () {
            var row = sup_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            //清空表单
            sup_form.form("clear");

            //回显数据
            sup_form.form("load", row);

            //设置标题
            sup_dialog.dialog("setTitle", "编辑供应商");
            //打开弹出框
            sup_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            sup_dialog.dialog("close");
        },
        save: function () {
            sup_form.form("submit", {
                url: '/supplier/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            sup_dialog.dialog("close");
                            //重新加载数据表格
                            sup_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        delete: function () {

            var row = sup_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/supplier/delete.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                sup_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            sup_datagrid.datagrid("load");
        },
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    sup_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#sup_toolbar',
        striped: true,
        url: '/supplier/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'id', width: 100,checkbox:true},
            {field: 'name', title: '供应商', width: 100},
            {field: 'arrearage', title: '应付欠款(人民币)', width: 100},
            {field: 'linkman', title: '联系人', width: 100},
            {field: 'tel', title: '电话', width: 100},
            {field: 'inputTime', title: '添加时间', width: 100},
            {
                field: 'employee', title: '操作人员', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
                }
            }
        ]],
    });

    sup_dialog.dialog({
        width: 300,
        height: 380,
        buttons: '#sup_buttons',
        closed: true
    });

    sup_search.searchbox({
        width:180,
        prompt:'输入供应商、联系人、电话',
        searcher:function (value,name) {
            sup_datagrid.datagrid("load",{
                "keyword":value
            })
        }
    })

});

