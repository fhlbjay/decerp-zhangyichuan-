$(function () {
    $("#emp_datagrid").datagrid({
        fit:true,
        fitColumns:true,
        url:'/permission/view.do',
        columns:[[
            {field:'name',title:'权限名称',width:100},
            {field:'expression',title:'权限表达式',width:100}
        ]],
        scrollbarSize:0,
        toolbar:'#emp_toolbar',
        //隔行斑马线
        striped:true,
        //分页条
        pagination:true,
        //显示行号
        rownumbers:true
    });
    //初始化弹窗控件
    $("#emp_dialog").dialog({
        width:200,
        height:100,
        buttons:'#emp_buttons',
        closed:true,
        content:"加载需要几分钟时间,是否继续"
    })

});
//新增按钮
function add(){
    //打开弹出框
    $("#emp_dialog").dialog("open");
}
function install(){
    //异步加载所有权限
    $.post("/permission/loadPermission.do",function(data){
        if (data.success) {
            //提示信息
            $.messager.alert('温馨提示', '操作成功', 'info', function () {
                //关闭弹出框
                $("#emp_dialog").dialog("close");
                //重新加载数据表格
                $("#emp_dialog").datagrid("reload");
            });
        } else {
            //关闭弹出框
            $("#emp_dialog").dialog("close");
            $.messager.alert('温馨提示', data.message, 'error');
        }
    })
}
function cancel(){
    //关闭弹出框
    $("#emp_dialog").dialog("close");
}

