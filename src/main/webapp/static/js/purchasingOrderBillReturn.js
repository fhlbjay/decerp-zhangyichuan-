$(function () {
    //1.变量抽取
    var pur_datagrid = $("#pur_datagrid");

    //2.把方法放进来(放到一个对象中统一管理方法)
    var methodObj = {
        reload: function () {
            pur_datagrid.datagrid("reload");
        },
    };

    //3.页面加载完后统一绑定事件
    $("[data-cmd]").click(function () {
        //获取到该按钮要执行的方法(cmd)
        var method = $(this).data("cmd");
        //调用方法
        methodObj[method]();
    });


    pur_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: '#pur_toolbar',
        striped: true,
        url: '/purchasingOrderBill/viewReturn.do',
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        scrollbarSize:0,
        columns: [[
            {field: 'sn', title: '订单编号', width: 100},
            {field: 'vdate', title: '业务时间', width: 100},
            {
                field: 'supplier', title: '供应商', width: 100, formatter: function (value, row, index) {
                    return value ? value.name : "";
                }
            },
            {field: 'totalNumber', title: '总数量', width: 100},
            {field: 'totalAmount', title: '总金额', width: 100},
            {
                field: 'depot', title: '仓库', width: 100, formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            },
            {
                field: 'inputUser', title: '录入人', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
            }
            },
            {
                field: 'auditor', title: '审核人', width: 100, formatter: function (value, row, index) {
                return value ? value.username : "";
            }
            },
            {
                field: 'status', title: '审核状态', width: 100, formatter: function (value, row, index) {
                return value ? "<font color='green'>已审核入库</font>" : "<font color='red'>待审核</font>";
            }
            },
            {
                field: 'returnState', title: '退回状态', width: 100, formatter: function (value, row, index) {
                return value ? "<font color='red'>已退货</font>" : "<font color='green'>未退货</font>";
            }
            },
        ]],
    });

});

