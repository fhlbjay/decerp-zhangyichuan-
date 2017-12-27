$(function () {
    $("#item_datagrid").datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#item_toolbar',
        striped: true,
        // url: '//view.do',
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'name', title: '明细名称', width: 100},
            {field: 'intro', title: '明细简介', width: 100},
            {
                field: 'dictionary', title: '所属目录', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
        ]],


    });

    $("#item_dialog").dialog({
        width: 300,
        height: 380,
        buttons: '#item_buttons',
        closed: true
    });


});
function add_item() {
    //清空表单
    $("#item_form").form("clear");
    //设置标题
    $("#item_dialog").dialog("setTitle", "新增明细目录");
    //打开弹出框
    $("#item_dialog").dialog("open");
}

function edit_item() {
    var row = $("#item_datagrid").datagrid("getSelected");
    if (!row) {
        $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
        return;
    }
    //清空表单
    $("#item_form").form("clear");
    //处理部门数据
    row["dictionary.id"] = row.dictionary.id;
    //回显数据
    $("#item_form").form("load", row);

    //设置标题
    $("#item_dialog").dialog("setTitle", "编辑明细目录");
    //打开弹出框
    $("#item_dialog").dialog("open");
}

function cancel_item() {
    //关闭弹出框
    $("#item_dialog").dialog("close");
}

function save_item() {
    $("#item_form").form("submit", {
        url: '/dictionaryitem/saveOrUpdate.do',
        success: function (data) {
            data = $.parseJSON(data);
            if (data.success) {
                //提示信息
                $.messager.alert('温馨提示', '操作成功', 'info', function () {
                    //关闭弹出框
                    $("#item_dialog").dialog("close");
                    //重新加载数据表格
                    $("#item_datagrid").datagrid("reload");
                });
            } else {
                $.messager.alert('温馨提示', data.message, 'error');
            }
        }

    })
}

function reload_item() {
    $("#item_datagrid").datagrid("load");
}
function remove_item() {
    //判断用户是否选中数据
    var row = $("#item_datagrid").datagrid("getSelected");
    if (!row) {//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示", "请选中一条数据", "warning");
        return;
    }
    //发送ajax请求 自动将返回值转成json对象
    $.post("/dictionaryitem/delete.do",{id:row.id},function (data) {
        if (data.success){
            //弹出提示信息
            $.messager.alert("温馨提示","删除成功","info",function () {
                //重新加载数据
                $("#item_datagrid").datagrid("reload");
            })
        }else {
            //弹出提示信息
            $.messager.alert("温馨提示",data.message,"error")
        }
    },"json");
}

