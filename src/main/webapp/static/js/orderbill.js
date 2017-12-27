$(function () {

    //提取常用字段
    var orderbill_datagrid = $("#orderbill_datagrid");

    //封装方法
    var objMethod = {};

    //调用方法

    $("[data-cmd]").click(function () {
            var methodname = $(this).data("cmd");
            //调用方法
            objectMethod[methodname]()
        }
    );


    //初始化数据页面
    orderbill_datagrid.datagrid({
        // width: 300 ,
        // height: 400,
        url: '/employee/list.do',
        fit: true,
        fitColumns: true,
        pagination: true,
        pagePosition: 'top',
        columns: [[

            {field: 'id', checkbox: true},
            {field: 'username', title: '用户名', width: 100},
            {field: 'password', title: '密码', width: 100},
            {field: 'realname', title: '真实姓名', width: 100},
            {field: 'tel', title: '电话', width: 100},
            {field: 'email', title: '邮箱', width: 100},
            {field: 'inputtime', title: '登记时间', width: 100},
            {
                field: 'state', title: '状态', width: 100, formatter: function (value, row, index) {
                return value ? "在职" : "<font color='red'>离职</font>"
            }
            },
            {
                field: 'admin', title: '是否管理员', width: 100, formatter: function (value, row, index) {
                return value ? "是" : "否"
            }
            },
            {
                field: 'dept', title: '部门', width: 100, formatter: function (value, row, index) {
                return value ? value.name : ""
            }
            }
        ]],

        striped: true,
        rownumbers: true,
        singleSelect: true,
        //    点击修改在职离职状况
        onClickRow: function (index, row) {
            if (row.state) {
                $("#changeState_btn").linkbutton({
                    text: "离职"
                })
            } else {
                $("#changeState_btn").linkbutton({
                    text: "入职"
                })

            }
        }


    });







});