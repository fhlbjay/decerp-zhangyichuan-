$(function () {
    //初始化datagrid
    $("#record").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'payment', title: '支付方式', width: 100},
            {field: 'oldmount', title: '充值前余额', width: 100},
            {field: 'rechargemount', title: '充值金额', width: 100},
            {field: 'currentmount', title: '当前余额', width: 100},
            {field: 'remarks', title: '备注', width: 100},
            {field: 'rechargetime', title: '充值时间', width: 100}
        ]]
    });
    $("#save").click(function () {
        $("#rechargeItem").form("submit", {
            url: '/rechargeitem/save.do',
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    console.log(vipId);
                    //提示信息
                    $.messager.alert('温馨提示', '操作成功', 'info', function () {
                        //刷新充值记录表
                        $("#record").datagrid("reload");
                    });
                } else {
                    $.messager.alert('温馨提示', data.message, 'error');
                }
            }

        })
    });
    $("#cancel").click(function () {
        alert(66);
        $("#_easyui_textbox_input1").val("");
        $("#_easyui_textbox_input2").val("");
    });

});

function listVip() {
    //初始化弹窗
    $("#vipList").dialog({
        title: '会员信息列表',
        width: 800,
        height: 500,
        closed: false,
        cache: false
    });
    //初始化列表
    $("#v").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        toolbar: '#toolbar',
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
            {
                field: 'vipcard2', title: '会员积分', width: 100, formatter: function (value, row, index) {
                    return row ? row.vipcard.integral : "";
                }
            },
            {
                field: 'vipcard3', title: '可用余额', width: 100, formatter: function (value, row, index) {
                    return row ? row.vipcard.balance : "";
                }
            },
            {
                field: 'state', title: '状态', width: 100, formatter: function (value, row, index) {
                    return value ? "正常" : "<font color='red'>丢失</font>";
                }
            }
        ]],
        onDblClickRow: function (index, row) {
            edit(row);
            //关闭弹窗
            $("#vipList").dialog("close");

        }
    })
}

var vipId;
//var vipcardId;
function edit(row) {
    console.log(row);
    vipId = row.id;
    //vipcardId =row.vipcard.id;
    //给父页面传值
    //会员id
    $("#vipId").val(row.id);
    $("#vipcardId").val(row.vipcard.id);
    //会员卡id
    $("#vipcardId").val(row.vipcard.id);
    //会员卡号赋值
    $("#m_sv_mr_cardno").text(row.vipcard.sn);
    //会员姓名赋值
    $("#m_sv_mr_name").text(row.name);
    //会员等级赋值
    $("#m_sv_ml_name").text(row.vipgrade);
    //会员卡余额
    $("#m_sv_mw_availableamount").text(row.vipcard.balance);
    //当前积分
    $("#m_sv_mw_availablepoint").text(row.vipcard.currentintegral);
    //累计积分
    $("#m_sv_mw_sumpoint").text(row.vipcard.integral);
    //累计消费
    $("#m_sv_mw_sumamount").text(row.vipcard.consumptionamount);
    //初始化datagrid
    $("#record").datagrid({
        fit: true,
        fitColumns: true,
        striped: true,
        url: '/rechargeitem/selectRechargeitemByVipId.do?vipId=' + vipId,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [[
            {field: 'payment', title: '支付方式', width: 100},
            {field: 'oldmount', title: '充值前余额', width: 100},
            {field: 'rechargemount', title: '充值金额', width: 100},
            {field: 'currentmount', title: '当前余额', width: 100},
            {field: 'remarks', title: '备注', width: 100},
            {field: 'rechargetime', title: '充值时间', width: 100}
        ]]
    })
}

function mySearch() {
    //获取关键字input
    var keyword = $("#keyword").textbox("getValue");
    //让数据表格重新加载,并且带上查询的参数(会帮你带上分页的参数到后台,直接把后台返回的数据封装到datagrid中)
    $("#v").datagrid("load", {
        keyword: keyword
    });

}
