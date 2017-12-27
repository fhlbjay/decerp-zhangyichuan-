$(function () {
    $("#emp_datagrid").datagrid({
        fit:true,
        fitColumns:true,
        url:'/department/view.do',
        columns:[[
            {field:'id',width:100,checkbox:true},
            {field:'name',title:'部门名称',width:100},
            {field:'sn',title:'编码',width:100}
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
        width:300,
        height:250,
        buttons:'#emp_buttons',
        closed:true
    })
});
//新增按钮
function add(){
    //清空表单数据
    $("#emp_form").form("clear");
    //设置标题
    $("#emp_dialog").dialog("setTitle","新增员工");

    //打开弹出框
    $("#emp_dialog").dialog("open");
}

//编辑按钮
function edit(){
    //判断用户是否选中数据
    var row = $("#emp_datagrid").datagrid("getSelected");
    if (!row){//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示","请选中一条数据","warning");
        return;
    }
    //清空表单数据
    $("#emp_form").form("clear");
    //回显到表单中(根据同名匹配的原则来回显)
    $("#emp_form").form("load",row);
    //设置标题
    $("#emp_dialog").dialog("setTitle","编辑部门");
    //打开弹出框
    $("#emp_dialog").dialog("open");
}

//删除按钮
function remove(){
    //判断用户是否选中数据
    var row = $("#emp_datagrid").datagrid("getSelected");
    if (!row){//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示","请选中一条数据","warning");
        return;
    }
    //发送ajax请求 自动将返回值转成json对象
    $.post("/department/delete.do",{id:row.id},function (data) {
        if (data.success){
            //弹出提示信息
            $.messager.alert("温馨提示","删除成功","info",function () {
                //重新加载数据
                $("#emp_datagrid").datagrid("reload");
            })
        }else {
            //弹出提示信息
            $.messager.alert("温馨提示",data.message,"error")
        }
    },"json");
}

//刷新按钮
function reload(){
    //重新加载数据
    $("#emp_datagrid").datagrid("reload");
}
//保存按钮
function save(){
    //使用easyui的form表单做提交(ajax)
    $("#emp_form").form("submit",{
        url:'/department/saveOrUpdate.do',
        success:function(data){
            //这里的data是字符创形式,所以要通过jquery转成json
            var data = $.parseJSON(data);
            if(data.success){
                //关闭弹出框
                $("#emp_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示","操作成功!","info",function(){
                    //重新加载数据表格
                    $("#emp_datagrid").datagrid("reload");
                });
            }else{
                //弹出提示信息
                $.messager.alert("温馨提示",data.message,"error");
            }
        }
    })
}

/*function save() {
    console.log(666);
    $('#emp_form').form("submit", {
        url:'employee_saveOrUpdate.json',
        //可以提交额外的参数
       /!* onSubmit: function(param){
            param.p1 = 'value1';
            param.p2 = 'value2';
        },*!/
        success:function(data) {
            //data是url请求返回的值
            //将返回的字符创转成json对象
            data = $.parseJSON(data);
            if (data.success) {
                //关闭弹出框
                $("#emp_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示", "保存成功", "info", function () {
                    //重新加载数据
                    $("#emp_datagrid").datagrid("reload");
                })
            } else {
                //弹出提示信息
                $.messager.alert("温馨提示", data.message, "error")
            }
        }
    }
);
}*/
//取消按钮
function cancel() {
    //关闭弹出框
    $("#emp_dialog").dialog("close");
}