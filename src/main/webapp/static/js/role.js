$(function () {
    //1.变量抽取
    var role_form = $("#role_form");
    var role_datagrid = $("#role_datagrid");
    var role_dialog = $("#role_dialog");

    var allRole_datagrid = $("#allRole_datagrid");
    var partRole_datagrid = $("#partRole_datagrid");
    var allPermissions;
    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //显示密码框
            $("#password").show();
            //编辑页面的已拥有权限页面会影响新增页面,清除影响  清空已有权限的数据
            partRole_datagrid.datagrid("loadData",[]);
            //重新加载所有权限的数据
            allRole_datagrid.datagrid("load");
            //清空表单
            role_form.form("clear");

            //设置标题
            role_dialog.dialog("setTitle", "新增员工");
            //打开弹出框
            role_dialog.dialog("open");
        },

        edit: function () {
            var row = role_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }
            //编辑页面的已拥有权限页面会影响新增页面,清除影响  清空已有权限的数据
            partRole_datagrid.datagrid("loadData",[]);
            //重新加载所有权限的数据
           // allRole_datagrid.datagrid("load");
            allRole_datagrid.datagrid("loadData",allPermissions);
            //清空表单
            role_form.form("clear");


            //回显数据
            role_form.form("load", row);
            //回显已拥有的权限
            partRole_datagrid.datagrid("reload",{id:row.id});


            //设置标题
            role_dialog.dialog("setTitle", "编辑员工");
            //打开弹出框
            role_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            role_dialog.dialog("close");
        },

        save: function () {
            role_form.form("submit", {
                url: '/role/saveOrUpdate.do',
                onSubmit: function(param){
                    // do some check
                    // return false to prevent submit;
                    //param['permission[0].id'] = 1;
                    //获取拥有权限列表中所有权限对象集合
                    var rows = partRole_datagrid.datagrid("getRows");
                    for(var i = 0;i<rows.length;i++){

                            //添加额外的参数
                            param["permissions["+i+"].id"]=rows[i].id;

                    }

                },
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            role_dialog.dialog("close");
                            //重新加载数据表格
                            role_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
        },

        changeState: function () {

            var row = role_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/role/changeState.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                role_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            role_datagrid.datagrid("load");
        }
    };


    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    role_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#role_toolbar',
        striped: true,
        url: '/role/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'name', title: '角色名称', width: 100},
            {field: 'sn', title: '角色编码', width: 100},
        ]],

    });
    allRole_datagrid.datagrid({
        fitColumns: true,
        width:220,
        height:200,
        striped: true,
        url: '/permission/selectAll.do',
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'name', title: '全部权限', width: 100,align:"center"}
        ]],
        onClickRow:function(index,row){
            //如果已经添加过,就设置该条数据为选中状态
           /*var rows = partRole_datagrid.datagrid("getRows");
            for(var i = 0;i<rows.length;i++){
                if (rows[i].id ==row.id){
                    partRole_datagrid.datagrid("selectRow",i);
                    return;
                }
            }*/
            //往已有权限中添加点击的数据
            partRole_datagrid.datagrid("appendRow",row);
            //在所有权限中删除点击的这条数据
            allRole_datagrid.datagrid("deleteRow",index);
        },
        onLoadSuccess:function(data){
            //把后台返回的数据放到变量中去
            //allPermissions=data;
            //深度克隆
            allPermissions=$.extend(true,{},data);
        }
    });
    partRole_datagrid.datagrid({
        fitColumns: true,
        width:220,
        height:200,
        striped: true,
        url: '/permission/selectPermissionByRoleId.do',
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'name', title: '拥有权限', width: 100,align:"center"}
        ]],
        onClickRow:function(index,row){
            //如果已经添加过,就设置该条数据为选中状态
            /*var rows = partRole_datagrid.datagrid("getRows");
             for(var i = 0;i<rows.length;i++){
                 if (rows[i].id ==row.id){
                     partRole_datagrid.datagrid("selectRow",i);
                     return;
                 }
             }*/
            //往所有权限中添加点击的数据
            allRole_datagrid.datagrid("appendRow",row);
            //在已有权限中删除点击的这条数据
            partRole_datagrid.datagrid("deleteRow",index);
        },
        onLoadSuccess:function(data){
            //当拥有权限页面加载完毕后,对所有权限页面做过滤
            var ids = $.map(data.rows,function(permission){
                return permission.id;
            });
            //循环所有权限,判断该权限是否存在ids数组中
            var rows = allRole_datagrid.datagrid("getRows");
            for(var i=rows.length-1;i>=0;i--){
                if ($.inArray(rows[i].id,ids)!=-1){
                    //如果存在就从所有权限中删除
                    allRole_datagrid.datagrid("deleteRow",i);
                }
            }
        }
    });

    role_dialog.dialog({
        width: 500,
        height: 350,
        buttons: '#role_buttons',
        closed: true
    })

});

