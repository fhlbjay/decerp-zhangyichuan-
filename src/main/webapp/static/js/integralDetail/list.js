//设置全局查询参数
var keyword = null;

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
        $.post("vip/view.do", {keyword: keyword}, function (data) {
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

// 分页操作
$(function () {
    // 分页按钮(上一页,下一页)
    $(".btn_page").click(function () {
        var page = $(this).attr("data-page")||1;
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
    $.post("/integralDetail/selectIntegralDetail.do", {currentPage: currentPage, pageSize: pageSize, vipId: keyword}, function (data) {
        $("#giftExchangelisthtml").empty();
        for (var i = 0; i < data.list.length; i++) {
            appendGiftItemList(data.list[i]);
        }
        //页面分页数据回显
        $("#prevPage").attr("data-page",data.prevPage);
        $("#nextPage").attr("data-page",data.nextPage);
        $("#totalPage").attr("data-page",data.totalPage);
        //分页大小回显
        $.each($("#pageSize option"), function(index, item) {
            if(item.value==data.pageSize){
                item.selected=true;
            }
        })
        //单前页回显
        $("input[name='currentPage']").val(data.currentPage);
        //页面记录回显
        $("#totalCount").text(data.totalCount);
        $("#currentPageResult").text(data.currentPage+"/"+data.totalPage);
    },"json")

}
//礼品列表添加
function appendGiftItemList(data) {
        var html = "<tr>" +
            "<td>"+data.vip.vipcard.sn+"</td>" +
            "<td class='amountcolor2'>"+data.vip.name+"</td>" +
            "<td>"+data.vip.vipgrade+"</td>" +
            "<td>"+data.operationType+"</td>" +
            "<td class='colorff'>"+data.amountChange+"</td>" +
            "<td>"+data.remark+"</td>" +
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
//点击积分清零
function clearIntegral(){
    //提示是否要积分清零
    //获取单前积分,如果单前积分为0则提示,会员积分为0,不需要做清楚操作
    //如果积分不为0,则发ajax请求,根据ajax请求提示用户是否操作成功
    $.dialog({
        title: "温馨提示",
        content: "你确定要清楚积分吗",
        icon: "face-smile",
        ok: function () {
            var currentIntegral=$("#vipCardCurrentIntegral").text();
            if(currentIntegral==0){
                $.dialog({
                    title: "温馨提示",
                    content: "会员积分为0,不需要做清楚操作",
                    icon: "face-smile",
                    ok:true
                })
                return;
            }
            //拿到会员ID
            var vipID=$("#vipID").val();
            $.post("/integralDetail/clearIntegral.do",{vipId:vipID}, function (data) {
                if (data.success) {
                    $.dialog({
                        title: "温馨提示",
                        content: "操作成功",
                        icon: "face-smile",
                        ok: function () {
                            //清楚页面缓存
                            window.location.reload(force = true);
                        }
                    })
                } else {//失败则说明,积分变动需要重新加载数据
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
}
//点击改变积分
function changeIntegral() {
    //拿到选择充值还是扣除
    var changeType=$(".shareIntegral:checked").data("type");
    /*获取改变数量*/
    var amount=$("#integralnumber").val();

    if(!amount){
        $.dialog({
            title: "温馨提示",
            content: "请输入变动数额",
            icon: "face-smile",
            ok: true
        })
        return;
    }
    //如果是扣除,则判断当前积分是否能扣
    if(changeType==0){
        var currentIntegral=$("#vipCardCurrentIntegral").text();
        //数字比较先转换类型
        console.log(parseInt(currentIntegral)<parseInt(amount))
        if(parseInt(currentIntegral)<parseInt(amount)){
            $.dialog({
                title: "温馨提示",
                content: "该会员的拥有的积分小于扣除积分",
                icon: "face-smile",
                ok: true
            })
            return;
        }
    }
    //如果能则,提示用户是否要继续操作,发送ajax请求去数据库中扣除
    $.dialog({
        title: "温馨提示",
        content: "确定要操作该会员积分吗?",
        icon: "face-smile",
        ok: function () {
            //拿到会员ID
            var vipID=$("#vipID").val();
            $.post("/integralDetail/changeIntegral.do",{vipId:vipID,amount:amount,changeType:changeType}, function (data) {
                if (data.success) {
                    $.dialog({
                        title: "温馨提示",
                        content: "操作成功",
                        icon: "face-smile",
                        ok: function () {
                            //清楚页面缓存
                            window.location.reload(force = true);
                        }
                    })
                } else {//失败则说明,积分变动需要重新加载数据
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
}