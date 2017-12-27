//设置全局查询参数
var keyword = null;
$(function () {
    //商品选择
    $(".searchGift").click(function () {
        var trObject = $(this).closest("tr");
        $.dialog.open("/giftOrder/selectGiftList.do", {
            id: "ajxList",
            title: '礼品选择列表',
            lock:true,
            background: '#000',
            opacity:0.5,
            width: 1000,
            height: 500,
            close: function () {
                var json = $.dialog.data("json");
                if (!json) {
                    return;
                }
                var flag = false
                //如果已经选择该产品则不添加
                if ($("[tag=gid]").each(function (index, ele) {
                        if (json.id == $(ele).val()) {
                            flag = true;
                        }
                    }))
                    if (flag) {
                        $.dialog({
                            title: "温馨提示",
                            content: "你已经选择了该礼品",
                            icon: "face-smile",
                            ok: true
                        });
                        return;
                    }
                //如果产品数量为0不添加,窗口提示不添加.
                if (json.surplus == 0) {
                    $.dialog({
                        title: "温馨提示",
                        content: "该礼品目前无货",
                        icon: "face-smile",
                        ok: true
                    });
                    return;
                }
                trObject.find("[tag=gid]").val(json.id);
                trObject.find("[tag=name]").text(json.name);
                trObject.find("[tag=integral]").text(json.integral);
                trObject.find("[tag=surplus]").text(json.surplus);
            }

        })
    })
    //金额计算
    $("[tag=number]").blur(function () {
        var tr = $(this).closest("tr");
        //如果兑换量大于库存量,修改兑换量
        var number = parseInt(tr.find("[tag=number]").val()) || 0;
        var surplus = parseInt(tr.find("[tag=surplus]").text()) || 0;
        if (number > surplus) {
            tr.find("[tag=number]").val(0);
            $.dialog({
                title: "温馨提示",
                content: "兑换量不能高于剩余量",
                icon: "face-smile",
                ok: true
            });
            return;
        }
        integralCount();
    })
    //添加更多
    $("#selectgift").click(function () {
        var newTr = $("#edit_table_body tr:first-child").clone(true);
        $(newTr).find("input").val("");
        $(newTr).find("span").text("");
        $("#edit_table_body").append(newTr);
    })
    //删除明细
    $(".removeItem").click(function () {
        console.log($("#edit_table_body tr").size());
        if ($("#edit_table_body tr").size() == 1) {
            var fisrtTr = $(this).closest("tr");
            fisrtTr.find("input").val("");
            fisrtTr.find("span").text("");
            return;
        }
        $(this).closest("tr").remove();
        //删除之后金额计算
        integralCount();
    })
})

//金额计算函数
function integralCount() {
    var totalIntegral = 0;
    $("#edit_table_body tr").each(function (index, ele) {
        var tr = $(ele);
        var costIntegral = parseInt(tr.find("[tag=integral]").text()) || 0;
        var number = parseInt(tr.find("[tag=number]").val()) || 0;
        var amount = costIntegral * number;
        totalIntegral += amount;
    });

    $("[tag=totalintegral]").text(totalIntegral);
}

//访问问页面时,发送ajax获取数据
$(function () {
    //vip缓存数据
    window.dataCache = '';
    $.post("/vip/view.do", function (data) {
        dataCache = data.rows;
        for (var i = 0; i < dataCache.length; i++) {
            appendVipList(dataCache[i]);
        }
        memberListOnclick();
    });
    /*绑定点击事件*/
    $("#query_user_btn").click(function () {
        //获取查询条件
        var keyword = $("#query_user").val();
        /*if(!keyword){
            return;
        }*/
        //查询
        $.post("/vip/view.do", {keyword: keyword}, function (data) {
            dataCache = data.rows;
            if (dataCache.length == 0) {
                $.dialog({
                    title: "温馨提示",
                    content: "查无结果",
                    icon: "face-smile",
                    ok: true
                });
                return;
            }
            $("#memberList").empty();
            for (var i = 0; i < dataCache.length; i++) {
                appendVipList(dataCache[i]);
            }
            queryAjax(1, 3, null);
            memberListOnclick();
        })
    });
    //页面初始化进行的查询
    queryAjax(1, 3, keyword);
})

function appendVipList(data) {
    var html = "<li id=" + data.id + ">" +
        "<div class='fl miinfo'>" +
        "<img src='/static/image/001.png' alt='' class='memberIntegralimg fl'>" +
        "<div class='fl name-money'><span>" + data.name + "</span> <span>储值:<i>￥" + data.vipcard.balance + "</i></span></div>" +
        "</div>" +
        "<div class='fr'>" +
        "<div class='fr name-money'><i class='integralgrade diamond'>" + data.vipgrade + "</i>" +
        "<span class='memberphone'>" + data.tel + "</span></div>" +
        "</div>" +
        "</li>";
    $("#memberList").append(html);
}

function changeVipDetail(data) {
    $("#vipID").val(data.id);
    $("#vipName").text(data.name);
    $("#vipCard").text(data.vipcard.sn);
    $("#vipGrade").text(data.vipgrade);
    $("#vipCardBalance").text(data.vipcard.balance);
    $("#vipCardCurrentIntegral").text(data.vipcard.currentintegral);
    $("#vipCardConsumptionAmount").text(data.vipcard.consumptionamount);
    $("#vipCardIntegral").text(data.vipcard.integral);
    $("#expensePoint").text(data.vipcard.integral - data.vipcard.currentintegral);
}

//确定兑换
$(function () {
    var params = {};
    $("#exchangeGiftBtn").click(function () {
        $.dialog({
            title: "温馨提示",
            content: "你确定要兑换礼品吗",
            icon: "face-smile",
            ok: function () {
                $("#edit_table_body tr").each(function (index, item) {
                    params["list[" + index + "].vip.id"] = $("#vipID").val();
                    params["list[" + index + "].gift.id"] = $(item).find("[tag=gid]").val();
                    params["list[" + index + "].number"] = $(item).find("[tag=number]").val();
                })
                $.post("/giftOrder/giftExchange.do", params, function (data) {
                    if (data.success) {
                        $.dialog({
                            title: "温馨提示",
                            content: "兑换成功",
                            icon: "face-smile",
                            ok: function () {
                                //清楚页面缓存
                                window.location.reload(force = true);
                            }
                        })
                    } else {
                        $.dialog({
                            title: "温馨提示",
                            content: data.message,
                            icon: "face-smile",
                            ok: function () {
                                //清楚页面缓存
                                window.location.reload(force = true);
                            }
                        })
                    }
                })
            },
            cancel: true
        })

    })
})

// 分页操作
$(function () {
    // 分页按钮(上一页,下一页)
    $(".btn_page").click(function () {
        var page = $(this).attr("data-page") || 1;
        $("input[name='currentPage']").val(page);
        queryAjax($("input[name='currentPage']").val(), $("#pageSize").val(), keyword);
    });
    // 更换分页大小
    $("#pageSize").change(function () {
        $("input[name='currentPage']").val(1);
        queryAjax(1, $(this).val(), keyword);
    })
    //点击查询按钮
    $("#queryGiftItem").click(function () {
        queryAjax($("input[name='currentPage']").val(), $("#pageSize").val(), keyword);
    })
});

//Ajax请求兑换信息
function queryAjax(currentPage, pageSize, keyword) {
    $.post("/giftOrder/selectGiftOrderBillItem.do", {currentPage: currentPage, pageSize: pageSize, keyword: keyword}, function (data) {
        $("#giftExchangelisthtml").empty();
        for (var i = 0; i < data.list.length; i++) {
            appendGiftItemList(data.list[i]);
        }
        //页面分页数据回显
        $("#prevPage").attr("data-page", data.prevPage);
        $("#nextPage").attr("data-page", data.nextPage);
        $("#totalPage").attr("data-page", data.totalPage);
        //分页大小回显
        $.each($("#pageSize option"), function (index, item) {
            if (item.value == data.pageSize) {
                item.selected = true;
            }
        })
        //单前页回显
        $("input[name='currentPage']").val(data.currentPage);
        //页面记录回显
        $("#totalCount").text(data.totalCount);
        $("#currentPageResult").text(data.currentPage + "/" + data.totalPage);
    }, "json")

}

//礼品列表添加
function appendGiftItemList(data) {
    var html = "<tr>" +
        "<td>" + data.vip.vipcard.sn + "</td>" +
        "<td class='amountcolor2'>" + data.vip.name + "</td>" +
        "<td>" + data.gift.name + "</td>" +
        "<td>" + data.number + "</td>" +
        "<td class='colorff'>" + data.gift.integral + "</td>" +
        "<td>" + data.remainIntegral + "</td>" +
        "</tr>";
    $("#giftExchangelisthtml").append(html);
}

function memberListOnclick() {
    //成员列表切换
    $("#memberList li").each(function (index, ele) {
        if (index == 0) {
            changeVipDetail(dataCache[0]);
            $("#memberList li").first().addClass("active");
        }
        $(ele).click(function () {
            $("#memberList li").removeClass("active");
            $(this).addClass("active");
            changeVipDetail(dataCache[index]);
            keyword = $("#vipID").val();
            queryAjax($("input[name='currentPage']").val(), $("#pageSize").val(), keyword);
        })
    })
}