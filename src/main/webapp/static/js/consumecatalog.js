$(function () {
    //初始化网格
    $("#newcatalog_datagrid").datagrid({
        fit:true,
        fitColumns:true,
        // url:'/consumecatalog/view.do',
        columns:[[
            {field:'id',width:100,checkbox:true},
            {field:'name',title:'支出分类',width:100}
        ]],
        scrollbarSize:0,
        toolbar:'#catalog_toolbar',
        //隔行斑马线
        striped:true,
        //分页条
        pagination:true,
        //显示行号
        rownumbers:true
    })
    //初始化新增/编辑分类弹出框
    $("#catalog_dialog").dialog({
        title:'新增支出大分类',
        buttons:'#catalog_buttons',
        closed:true
    })
})
//新增按钮
function editParent(one){
    //清空表单数据
    $("#newcatalog_form").form("clear");
    //回显当前按钮的值(使用onclick方法的话, 调用的是window, 所有要给他回传一个自己,回调函数,名称放在了span里面,使用text()获取名称

    // var theValue = $(this.target).val();
    console.log($(one).text());
    var theValue = $(one).text();
    var theId = $(one)[0].getAttribute('value');

    console.log(theId);
    console.log($(one));
    //$("#form_name").textbox()
    console.log(theValue);
    //回显名称
    $("#form_name").textbox("setValue",theValue);
    //回显id
    $("#form_id").textbox("setValue",theId);




    //编辑框设置值
    $("#catalog_dialog").dialog("setTitle","编辑大分类");
    //打开弹出框
    $("#catalog_dialog").dialog("open");
}
//编辑弹窗
function editChildren(one) {
    //清空表单数据
    $("#newcatalog_form").form("clear");
    //数据回显
    var theValue = $(one).text();
    var theId = $(one)[0].getAttribute('value');
    var theParentId = $(one)[0].getAttribute('parentId');

    console.log(theParentId);
    //回显parentId
    $("#form_parentId").textbox("setValue",theParentId);
    //回显对应数据
    $("#form_id").textbox("setValue",theId);
    //回显名称
    $("#form_name").textbox("setValue",theValue);
    //编辑框设置值
    $("#catalog_dialog").dialog("setTitle","编辑子分类");
    //打开弹出框
    $("#catalog_dialog").dialog("open");
}


//删除按钮
function remove(){
    $("#newcatalog_form").form("submit",{
        url:'/consumecatalog/delete.do',
        success:function(data){
            //这里的data是字符传形式,所以要通过jquery转成json
            var data = $.parseJSON(data);
            if(data.success){
                //关闭弹出框
                $("#catalog_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示","操作成功!","info",function(){
                    //重新加载数据表格
                    // $("#newcatalog_datagrid1").datagrid("reload");
                    window.location.reload();
                });
            }else{
                //弹出提示信息
                $.messager.alert("温馨提示",data.message,"error");
            }
        }
    })
}

//刷新按钮
function reload(){
    //重新加载数据
    window.location.reload();
}
//导
//保存按钮
function save(){
    //使用easyui的form表单做提交(ajax)
    $("#newcatalog_form").form("submit",{
        url:'/consumecatalog/saveOrUpdate.do',
        success:function(data){
            //这里的data是字符传形式,所以要通过jquery转成json
            var data = $.parseJSON(data);
            if(data.success){
                //关闭弹出框
                $("#catalog_dialog").dialog("close");
                //弹出提示信息
                $.messager.alert("温馨提示","操作成功!","info",function(){
                    //重新加载数据表格
                    // $("#newcatalog_datagrid1").datagrid("reload");
                    window.location.reload();
                });
            }else{
                //弹出提示信息
                $.messager.alert("温馨提示",data.message,"error");
            }
        }
    })
}

//取消按钮
function cancel() {
    //关闭弹出框
    $("#catalog_dialog").dialog("close");
}

//新增大分类
function add() {
    //清空表单数据
    $("#newcatalog_form").form("clear");
    //打开新增分类的dialog
    $("#catalog_dialog").dialog("open");
}


//添加子分类
function addChildren(one) {
    //清空表单数据
    $("#newcatalog_form").form("clear");
    console.log($(one));

    //数据回显parentId
    // var theParentId = $(one).closest('td').find('a:first').getAttribute('parentId');
    //var theParentId = $('#addChildren').attr('parentId');
    var theParentId = $(one).attr('parentId');


    console.log(theParentId);
    //回显parentId
    $("#form_parentId").textbox("setValue",theParentId);
    //新增框title设置名称
    $("#catalog_dialog").dialog("setTitle","新增子分类");
    //打开
    $("#catalog_dialog").dialog("open");
}

//