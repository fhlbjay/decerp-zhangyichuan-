$(function () {
    $("#parent_dialog").dialog({
        width: 300,
        height: 150,
        buttons: '#parent_buttons',
        closed: true
    });
});
function add_parent() {
    //清空表单
    $("#parent_form").form("clear");
    //设置标题
    $("#parent_dialog").dialog("setTitle", "新增二级目录");
    //打开弹出框
    $("#parent_dialog").dialog("open");
}

function edit_parent() {
    var row = $("#parent_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }
    //清空表单
    $("#parent_form").form("clear");
    //回显数据
    $("#parent_form").form("load", row);

    //设置标题
    $("#parent_dialog").dialog("setTitle", "编辑二级目录");
    //打开弹出框
    $("#parent_dialog").dialog("open");
}

function cancel_parent() {
    //关闭弹出框
    $("#parent_dialog").dialog("close");
}

function save_parent() {
    $("#parent_form").form("submit", {
        url: '/dirname/saveOrUpdate.do',
        onSubmit: function(param){
    var row=$("#root_datagrid").datagrid("getSelected");
            param['parent.id'] =row.id;


        },
        success: function (data) {
            data = $.parseJSON(data);
            if (data.success) {
                //提示信息
                $.messager.alert('温馨提示', '操作成功', 'info', function () {
                    //关闭弹出框
                    $("#parent_dialog").dialog("close");
                    //重新加载数据表格
                    $("#parent_datagrid").datagrid("reload");
                });
            } else {
                $.messager.alert('温馨提示', data.message, 'error');
            }
        }

    })
}

function reload_parent() {
    $("#parent_datagrid").datagrid("load");
}
function remove_parent() {
    //判断用户是否选中数据
    var row = $("#parent_datagrid").datagrid("getSelected");
    if (!row) {//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示", "请选中一条数据", "warning");
        return;
    }
    //发送ajax请求 自动将返回值转成json对象
    $.post("/dirname/delete.do",{id:row.id},function (data) {
        if (data.success){
            //弹出提示信息
            $.messager.alert("温馨提示","删除成功","info",function () {
                //重新加载数据
                $("#parent_datagrid").datagrid("reload");
            })
        }else {
            //弹出提示信息
            $.messager.alert("温馨提示",data.message,"error")
        }
    },"json");
}

