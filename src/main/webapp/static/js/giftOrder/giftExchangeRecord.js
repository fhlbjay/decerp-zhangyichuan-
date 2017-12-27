//选择日期类型
$(function () {
    $("#querydate li").each(function (index, ele) {
        $(ele).click(function () {
            $("#querydate li").removeClass("active");
            $(this).addClass("active");
        })
        if (index == 3) {
            $(ele).click(function () {
                $("#queryortherdate").show();
            })
        } else {
            $(ele).click(function () {
                $("#queryortherdate").hide();
            })
        }
    })
})
//访问问页面时,发送ajax获取数据
$(function () {
    //页面初始化进行的查询
    queryAjax(1, 10);
})
// 分页操作
$(function () {
    // 分页按钮(上一页,下一页)
    $(".btn_page").click(function () {
        var page = $(this).attr("data-page") || 1;
        $("input[name='currentPage']").val(page);
        queryAjax($("input[name='currentPage']").val(), $("#pageSize").val());
    });
    // 更换分页大小
    $("#pageSize").change(function () {
        $("input[name='currentPage']").val(1);
        queryAjax(1, $(this).val());
    })
});
//统一Ajax查询
$(function () {
    $(".uniformQuery").click(function () {
        queryAjax(1, $("#pageSize").val());
    })
})

//Ajax请求兑换信息
function queryAjax(currentPage, pageSize) {
    $.post("/giftOrder/selectGiftExchangeRecord.do", {
        currentPage: currentPage,
        pageSize: pageSize,
        keyword: $("#query_member").val(),
        dataType: $(".active").data("datetype"),
        beginDate: $("#begindateval").val(),
        endDate: $("#enddateval").val()
    }, function (data) {
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

//礼品兑换记录添加
function appendGiftItemList(data) {
    var html = "<tr>" +
        "<td>" + data.vip.vipcard.sn + "</td>" +
        "<td class='amountcolor2'>" + data.vip.name + "</td>" +
        "<td>" + data.gift.name + "</td>" +
        "<td>" + data.number + "</td>" +
        "<td class='colorff'>" + data.gift.integral * data.number + "</td>" +
        "<td>" + data.date + "</td>" +
        "<td>" + data.operation + "</td>" +
        "</tr>";
    $("#giftExchangelisthtml").append(html);
}

//导出列表
$(function () {
    $("#exportRecord").click(function () {
        var dataType;
        if(!$(".active").data("datetype")){
            console.log("-------------");
            dataType='';
        }else {
            dataType=$(".active").data("datetype");
        }
        window.location.href = "/giftOrder/export.do?keyword=" + $("#query_member").val() + "&dataType=" + dataType + "&beginDate=" + $("#begindateval").val() + "&endDate=" + $("#enddateval").val();
    })
})

