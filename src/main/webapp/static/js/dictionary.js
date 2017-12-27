$(function () {
    //1.变量抽取
    var dic_form = $("#dic_form");
    var dic_datagrid = $("#dic_datagrid");
    var dic_dialog = $("#dic_dialog");


    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //清空表单
            dic_form.form("clear");
            //设置标题
            dic_dialog.dialog("setTitle", "新增员工");
            //打开弹出框
            dic_dialog.dialog("open");
        },

        edit: function () {
            var row = dic_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //清空表单
            dic_form.form("clear");

            //回显数据
            dic_form.form("load", row);

            //设置标题
            dic_dialog.dialog("setTitle", "编辑目录");
            //打开弹出框
            dic_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            dic_dialog.dialog("close");
        },
        save: function () {
            dic_form.form("submit", {
                url: '/dictionary/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            dic_dialog.dialog("close");
                            //重新加载数据表格
                            dic_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        reload: function () {
            dic_datagrid.datagrid("load");
        },
        //删除按钮
         remove:function(){
        //判断用户是否选中数据
        var row = $("#dic_datagrid").datagrid("getSelected");
        if (!row){//没有选中
            //弹出提示信息
            $.messager.alert("温馨提示","请选中一条数据","warning");
            return;
        }
        //发送ajax请求 自动将返回值转成json对象
        $.post("/dictionary/delete.do",{id:row.id},function (data) {
            if (data.success){
                //弹出提示信息
                $.messager.alert("温馨提示","删除成功","info",function () {
                    //重新加载数据
                    $("#dic_datagrid").datagrid("reload");
                    $("#item_datagrid").datagrid("reload");
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


    dic_datagrid.datagrid({
         fit: true,
        fitColumns: true,
        toolbar: '#dic_toolbar',
        striped: true,
        url: '/dictionary/view.do',
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'sn', title: '目录编号', width: 100},
            {field: 'name', title: '目录名称', width: 100},
            {field: 'intro', title: '目录简介', width: 100},
        ]],
        onClickRow: function (index, row) {
            $("#item_datagrid").datagrid({
                url:'/dictionaryitem/view.do?sn='+ row.sn
            })
        }
    });

    dic_dialog.dialog({
        width: 300,
        height: 380,
        buttons: '#dic_buttons',
        closed: true
    });
});

