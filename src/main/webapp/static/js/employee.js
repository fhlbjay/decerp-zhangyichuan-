$(function () {
    //1.变量抽取
    var emp_form = $("#emp_form");
    var emp_datagrid = $("#emp_datagrid");
    var emp_dialog = $("#emp_dialog");
    var combobox = $("#role_combobox");
    var updownload = $("#up-download-dialog");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        add: function () {
            //显示密码框
            $("#password").show();

            //清空表单
            emp_form.form("clear");
            //设置标题
            emp_dialog.dialog("setTitle", "新增员工");
            //打开弹出框
            emp_dialog.dialog("open");
        },

        edit: function () {
            var row = emp_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            //隐藏密码框
            $("#password").hide();

            //清空表单
            emp_form.form("clear");

            //处理部门数据
            row["department.id"] = row.department.id;

            //回显角色信息
            //先将本员工的所拥有的角色id查询出来
            //再给combobox设置值
            $.post("/employee/getRoleIdByemployeeId.do",{id:row.id},function(data){
                combobox.combobox("setValues",data);
            });

            //回显数据
            emp_form.form("load", row);

            //设置标题
            emp_dialog.dialog("setTitle", "编辑员工");
            //打开弹出框
            emp_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            emp_dialog.dialog("close");
        },
        save: function () {
            emp_form.form("submit", {
                url: '/employee/saveOrUpdate.do',
                onSubmit: function(param){
                    // do some check
                    // return false to prevent submit;
                    //param['permission[0].id'] = 1;
                    //获取角色复选框中所有角色id
                    var rows =combobox.combobox("getValues");
                    for(var i = 0;i<rows.length;i++){

                        //添加额外的参数
                        param["roles["+i+"].id"]=rows[i];

                    }

                },
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

        changeState: function () {

            var row = emp_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', "请选择一条数据!", 'warning');
                return;
            }

            $.messager.confirm('确认', '您确认想要执行该操作吗？', function (r) {
                if (r) {
                    //发送请求修改状态
                    $.get("/employee/changeState.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                emp_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.message, 'error');
                        }
                    }, "json")
                }
            });
        },

        reload: function () {
            emp_datagrid.datagrid("reload");
        },

        searchForm: function () {
            //获取关键字input
            var keyword = $("#keyword").textbox("getValue");

            //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
            emp_datagrid.datagrid("load", {
                keyword: keyword
            });
        },
        exportXls:function () {
            var keyword = $("#keyword").textbox("getValue");
            window.location.href="/employee/exportXls.do?keyword="+keyword;
        },
        importXls:function () {
            $("#up-download-dialog").dialog("open");
        },
        updownload:function(){
            $("#updownloadForm").form("submit", {
                url: '/employee/importXls.do',
                success: function (data) {
                    data = $.parseJSON(data);
                    if (data.success) {
                        //提示信息
                        $.messager.alert('温馨提示', '操作成功', 'info', function () {
                            //关闭弹出框
                            updownload.dialog("close");
                            //重新加载数据表格
                            emp_datagrid.datagrid("reload");
                        });
                    } else {
                        $.messager.alert('温馨提示', data.message, 'error');
                    }
                }

            })
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
        url: '/employee/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'username', title: '用户名', width: 100},
            {field: 'realname', title: '真实姓名', width: 100},
            {field: 'tel', title: '电话', width: 100},
            {field: 'email', title: '邮箱', width: 100},
            {
                field: 'department', title: '部门', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {field: 'inputtime', title: '入职时间', width: 100},
            {
                field: 'admin', title: '是否管理员', width: 100, formatter: function (value, row, index) {
                return value ? "是" : "否";
            }
            },
            {
                field: 'state', title: '状态', width: 100, formatter: function (value, row, index) {
                    return value ? "在职" : "<font color='red'>离职</font>";
                }
            }
        ]],
        onClickRow: function (index, row) {
            if (row.state) {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '离职'
                })

            } else {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '复职'
                })
            }


            /*if(!row.state){
             //禁用离职按钮
             $("#changState_btn").linkbutton("disable");
             }else{
             //启用按钮
             $("#changState_btn").linkbutton("enable");
             }*/
        }
    });

    emp_dialog.dialog({
        width: 300,
        height: 380,
        buttons: '#emp_buttons',
        closed: true
    });
    updownload.dialog({
        width:200,
        height:200,
        closed:true,
        buttons: '#updownload_buttons',
        closed: true
    })

    $("#ss").searchbox({
        searcher:function (value,name) {

        }
    })

});

