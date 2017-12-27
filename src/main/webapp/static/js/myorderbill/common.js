$(function () {

    $(".btn_input").click(function () {
        window.location.href = $(this).data("url")
    })

    $(":input[name='pageSize']").change(function () {
        $("#searchForm").submit();
    })

    $(".btn_page").click(function () {
        var page = $(this).data("page");
        $(":input[name='currentPage']").val(page);
        $("#searchForm").submit();
    })

})

$(function () {
    $(".btn_delete").click(function () {
        var deleteurl = $(this).data("url");
        $.dialog({
                title: "hello",
                content: "你考虑好了吗",
                icon: "11",
                ok: function () {
                    $.get(deleteurl, function (data) {
                        if (data.flag) {
                            $.dialog({
                                title: "提示",
                                content: "delete success",
                                ok: function () {
                                    window.location.reload();
                                },
                                okVal: "我不后悔-确定"
                            })
                        } else {
                            $.dialog({
                                title: "提示",
                                content: data.message,
                                ok: true
                            })
                        }


                    }, "json");
                },
                cancel: true
            }
        )
    })
})

$(function () {
    $(".btn_shehe").click(function () {
        var deleteurl = $(this).data("url");
        $.dialog({
                title: "hello",
                content: "确定审核吗",
                icon: "11",
                ok: function () {
                    $.get(deleteurl, function (data) {
                        if (data.flag) {
                            $.dialog({
                                title: "提示",
                                content: "审核成功",
                                ok: function () {
                                    window.location.reload();
                                },
                                okVal: "确定"
                            })
                        } else {
                            $.dialog({
                                title: "提示",
                                content: data.message,
                                ok: true
                            })
                        }


                    }, "json");
                },
                cancel: true
            }
        )
    })
})