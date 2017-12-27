$(function () {
    //1.变量抽取
    var emp_form = $("#emp_form");
    var emp_datagrid = $("#emp_datagrid");
    var emp_dialog = $("#emp_dialog");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {

            //清空表单
            emp_form.form("clear");
            //设置标题
            emp_dialog.dialog("setTitle", "新增菜单");
            //打开弹出框
            emp_dialog.dialog("open");
        },
        edit: function () {
            var row = emp_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //清空表单
            emp_form.form("clear");
            //回显数据
            emp_form.form("load", row);

            //设置标题
            emp_dialog.dialog("setTitle", "编辑菜单");
            //打开弹出框
            emp_dialog.dialog("open");
        },
        cancel: function () {
            //关闭弹出框
            emp_dialog.dialog("close");
        },
        save: function () {
            emp_form.form("submit", {
                url: '/menu/saveOrUpdate.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            emp_dialog.dialog("close");
                            //重新加载数据表格
                            emp_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },
        reload: function () {
            emp_datagrid.datagrid("reload");
        },
        nextmenu:function () {
            //先选中行
            var row = emp_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //查询下级菜单
            emp_datagrid.datagrid("reload",{parentId:row.id});
        },
        prevmenu:function(){

            //查询当前parentId的菜单的parenttId
            /*$.post("/menu/getParentIdById",function(data){
                console.log(data);
            })*/
            //返回上级菜单
            emp_datagrid.datagrid("reload",{parentId:null});
        }
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });
    emp_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#emp_toolbar',
        striped: true,
        url: '/menu/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'text', title: '菜单名称', width: 100},
            {field: 'url', title: 'url', width: 100},
            {field: 'parent', title: '父级菜单', width: 100,formatter: function (value, row, index) {
                    return value ? value.text : "根目录"}}

        ]]
    });

    emp_dialog.dialog({
        width: 300,
        height: 250,
        buttons: '#emp_buttons',
        closed: true
    });
});

