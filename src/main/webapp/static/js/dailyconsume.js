$(function () {
    $("#daily_datagrid").datagrid({
        fit:true,
        fitColumns:true,
        url:'/dailyconsume/view.do',
        columns:[[
            {field:'id',width:100,checkbox:true},
            {field:'consumecatalog',title:'支出分类',width:100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }},
            {field:'amount',title:'支出金额',width:100},
            {field:'date',title:'支出时间',width:100},
            {
                field: 'consumer', title: '支出人员', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
            }},
            {field:'memo',title:'备注',width:100}
        ]],
        scrollbarSize:0,
        toolbar:'#daily_toolbar',
        //隔行斑马线
        striped:true,
        //分页条
        pagination:true,
        //显示行号bar
        rownumbers:true
    })
    //初始化弹窗控件
    $("#daily_dialog").dialog({
        width:300,
        height:250,
        buttons:'#daily_buttons',
        closed:true
    })
    //初始化上传文件弹出框
    $("#upload_dialog").dialog({
        closed:true
    })
})
//新增按钮
function add(){
    //清空表单数据
    $("#daily_form").form("clear");
    //设置标题
    $("#daily_dialog").dialog("setTitle","新增日常支出");

    //打开弹出框
    $("#daily_dialog").dialog("open");
}

//编辑按钮
function edit(){
    //判断用户是否选中数据
    var row = $("#daily_datagrid").datagrid("getSelected");
    if (!row){//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示","请选中一条数据","warning");
        return;
    }
    //清空表单数据
    $("#daily_form").form("clear");
    //回显支出类别的数据
    row["consumecatalog.id"] = row.consumecatalog.id;
    //回显到表单中(根据同名匹配的原则来回显)
    $("#daily_form").form("load",row);
    //设置标题
    $("#daily_dialog").dialog("setTitle","编辑支出");
    //打开弹出框
    $("#daily_dialog").dialog("open");
}

//删除按钮
function remove(){
    //判断用户是否选中数据
    var row = $("#daily_datagrid").datagrid("getSelected");
    if (!row){//没有选中
        //弹出提示信息
        $.messager.alert("温馨提示","请选中一条数据","warning");
        return;
    }
    //发送ajax请求 自动将返回值转成json对象
    $.post("/dailyconsume/delete.do",{id:row.id},function (data) {
        if (data.success){
            //弹出提示信息
            $.messager.alert("温馨提示","删除成功","info",function () {
                //重新加载数据
                $("#daily_datagrid").datagrid("reload");
            })
        }else {
            //弹出提示信息
            $.messager.alert("温馨提示",data.msg,"error")
        }
    },"json");
}

//刷新按钮
function reload(){
    //重新加载数据
    $("#daily_datagrid").datagrid("reload");
}
//导入按钮
function importXls() {
    //显示上传弹出框
    $("#upload_dialog").dialog("setTitle","日常支出数据上传");
    $("#upload_dialog").dialog("open");
}
//模板下载
function downloadTemplate() {
    window.location.href="/dailyconsume/downloadTemplate.do";

    //该方式不能使用ajax请求!

    // $.post("/dailyconsume/downloadTemplate.do",function (data) {
    //     if(data.success){
    //         //弹出提示信息
    //         $.messager.alert("温馨提示","删除成功","info",function () {
    //             //重新加载数据
    //             $("#daily_datagrid").datagrid("reload");
    //         })
    //     }else{
    //         //弹出提示信息
    //         $.messager.alert("温馨提示",data.message,"error")
    //     }
    // },"json");
}
//文件导入前校验以及提交
function checkAndUpload(form) {
    if(form.file.value==''){
        alert("请选择文件上传!");
        return false;
    }

    //使用easyui的form表单做提交(ajax)   <-----  如果它提交成功 就会返回true 如果后面还返回true会提交两次
    $("#upload_form").form("submit",{
        url:'/dailyconsume/importXls.do',
        success:function(data){
            // alert(data);

            //这里的data是字符传形式,所以要通过jquery转成json
            var data = $.parseJSON(data);
            // alert(data);
            console.log(data);
            if(data.success){
                //关闭弹出框
                $("#upload_dialog").dialog("close");
                // alert("guanbi");
                //弹出提示信息
                $.messager.alert("温馨提示","操作成功","info","info",function(){
                    //重新加载数据表格
                    $("#daily_datagrid").datagrid("reload");
                });
            }else{
                //弹出提示信息
                $.messager.alert("温馨提示",data.message,"error");
            }
        }
    });
    return false;
};
//关键字查询
function searchForm () {
    //获取关键字input
    var keyword = $("#keyword").textbox("getValue");
    var beginDate = $("#beginDate").datebox("getValue");
    var endDate = $("#endDate").datebox("getValue");

    //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
   $("#daily_datagrid").datagrid("load", {
        keyword: keyword,
       beginDate: beginDate,
       endDate: endDate
    });
}

//保存按钮
function save(){
    //使用easyui的form表单做提交(ajax)
    $("#daily_form").form("submit",{
        url:'/dailyconsume/saveOrUpdate.do',
        success:function(data){
            //这里的data是字符传形式,所以要通过jquery转成json
            var data = $.parseJSON(data);
            if(data.success){
                //关闭弹出框
                $("#daily_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示","操作成功!","info",function(){
                    //重新加载数据表格
                    $("#daily_datagrid").datagrid("reload");
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
    $('#daily_form').form("submit", {
        url:'dailyloyee_saveOrUpdate.json',
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
                $("#daily_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示", "保存成功", "info", function () {
                    //重新加载数据
                    $("#daily_datagrid").datagrid("reload");
                })
            } else {
                //弹出提示信息
                $.messager.alert("温馨提示", data.msg, "error")
            }
        }
    }
);
}*/
//取消按钮
function cancel() {
    //关闭弹出框
    $("#daily_dialog").dialog("close");
}
