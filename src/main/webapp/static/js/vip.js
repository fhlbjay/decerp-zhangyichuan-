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
            //设置会员卡编码可编辑
            $('#sn').textbox('readonly',false);
            //设置会员等级combox可读
            $("#cc").textbox("readonly",false);
            //设置标题
            emp_dialog.dialog("setTitle", "新增会员");
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
            //处理会员卡数据
            row["vipcard.sn"] = row.vipcard.sn;
            //设置会员卡编码只读
            $('#sn').textbox('readonly');
            //设置会员等级combox只读
            $("#cc").textbox("readonly");
            //回显数据
            emp_form.form("load", row);

            //设置标题
            emp_dialog.dialog("setTitle", "编辑会员");
            //打开弹出框
            emp_dialog.dialog("open");
        },

        cancel: function () {
            //关闭弹出框
            emp_dialog.dialog("close");
        },
        save: function () {
            emp_form.form("submit", {
                url: '/vip/saveOrUpdate.do',
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
                    $.get("/vip/changeState.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', "操作成功!", 'info', function () {
                                //重新加载数据表格
                                emp_datagrid.datagrid("reload");
                            });
                        } else {
                            $.messager.alert('温馨提示', data.msg, 'error');
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
            window.location.href="/vip/exportXls.do";
        },
        importXls:function () {
            $("#up-download-dialog").dialog("open");
        },
        updownload:function(){
            $("#updownloadForm").form("submit", {
                url: '/vip/importXls.do',
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
                        $.messager.alert('温馨提示', data.msg, 'error');
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
        url: '/vip/view.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {
                field: 'vipcard1', title: '会员卡号', width: 100, formatter: function (value, row, index) {
                return row ? row.vipcard.sn : "";
                }
            },
            {field: 'name', title: '会员姓名', width: 100},
            {field: 'vipgrade', title: '会员等级', width: 100},
            {field: 'tel', title: '电话', width: 100},
            {field: 'email', title: '邮箱', width: 100},
            {field: 'qq', title: 'qq', width: 100},
            {field: 'weixin', title: '微信', width: 100},
            {
                field: 'vipcard2', title: '会员可用积分', width: 100, formatter: function (value, row, index) {
                    return row ? row.vipcard.integral : "";
                }
            },
            {
                field: 'vipcard3', title: '可用余额', width: 100, formatter: function (value, row, index) {
                    return row ? row.vipcard.balance : "";
                }
            },
            {
                field: 'state', title: '会员卡状态', width: 100, formatter: function (value, row, index) {
                return value ? "正常" : "<font color='red'>丢失</font>";
                }
            },
            {field: 'birthday', title: '会员生日', width: 100}
        ]],
        onClickRow: function (index, row) {
            if (row.state) {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '丢失'
                })

            } else {
                //修改按钮
                $('#changState_btn').linkbutton({
                    text: '补办'
                })
            }
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
    });
    //加载面板数据:
    function loadData(){
        //会员总数
        var number;
        //总消费
        var consumptionamount;
        //加载面板一数据
        //发送ajax查询今日过生日的会员
        $.post("/vip/selectBirthdayOfToday.do",function(data){
            //给面板赋值
            $("#usercount").text(data);
        });
        //本月过生日的会员数量
        $.post("/vip/selectBirthdayOfMonth.do",function(data){
            //给面板赋值
            $("#usercount1").text(data);
        });
        //加载面板二数据
        $.post("/vip/selectVipNumber.do",function(data){
            //给面板赋值
            $("#usercount3").text(data);
            number=data;
        });
        $.post("/vip/selectConsumer.do",function(data){
            //给面板赋值
            $("#blanceamount").text(data);
        });
        //加载面板三数据
        $.post("/vip/selectConsumptionAmount.do",function(data){
            //给面板赋值
            $("#consumptionamount").text(data);
             consumptionamount = data;
        });
        $.post("/vip/selectOrderBilNum.do",function(data){
            //给面板赋值
            //订单总数
            $("#ordercount").text(data);
            //会员数量赋值
            $("#usercount2").text(number);
            //计算平均值
            var avg = consumptionamount/number;
            $("#average").text(avg);
        });
        //加载面板四数据
        $.post("/vip/selectConsumptionAmountTop.do",function(data){
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                $("#"+i).text(obj);
            }
        });

    }
    //自己调用加载数据方法
    loadData();


});

