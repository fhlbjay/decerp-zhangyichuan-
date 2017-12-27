/*jquery会在参数名后添加[],所有通过下面这命令去除[]*/
jQuery.ajaxSettings.traditional = true;

/** table鼠标悬停换色* */
$(function() {
	// 如果鼠标移到行上时，执行函数
	$(".table tr").mouseover(function() {
		$(this).css({
			background : "#CDDAEB"
		});
		$(this).children('td').each(function(index, ele) {
			$(ele).css({
				color : "#1D1E21"
			});
		});
	}).mouseout(function() {
		$(this).css({
			background : "#FFF"
		});
		$(this).children('td').each(function(index, ele) {
			$(ele).css({
				color : "#909090"
			});
		});
	});
});
// 分页操作
$(function() {
	// 分页按钮(上一页,下一页)
	$(".btn_page").click(function() {
		var page = $(this).data("page")||1;
		$("input[name='currentPage']").val(page);
		$("#searchForm").submit();
	});
	// 更换分页大小
	$("#pageSize").change(function() {
		$("input[name='currentPage']").val(1);
		$("#searchForm").submit();
	})
});
// 删除按钮
$(function() {
	$(".btn_delete").click(function() {
		var deleteurl = $(this).data("url");
		$.dialog({
			title : "温馨提示",
			content : "你确定要删除该数据吗",
			icon : "face-smile",
			ok : function() {
				$.get(deleteurl, function(data) {
					if (data.success) {
						$.dialog({
							title : "温馨提示",
							content : "删除成功",
							icon : "face-smile",
							ok : function() {
								window.location.reload();
							},
							okVal : "朕知道了"
						})
					} else {
						$.dialog({
							title : "温馨提示",
							content : data.message,
							icon : "face-smile",
							ok : true
						})
					}
				})
			},
			cancel : true
		})
	})
});

$(function() {
	// 编辑或者新增按钮
	$(".btn_input").click(function() {
		window.location.href = $(this).data("url")
	})
});

/* 源去目标 */
function moveAll(srcId, targetId) {
	$("#" + srcId + " option").appendTo($("#" + targetId));
}
/* 源选中的去目标 */
function moveSelected(srcId, targetId) {
	$("#" + srcId + " option:selected").appendTo($("#" + targetId));
}
// 左右-><-
$(function() {
	$("#select").click(function() {
		moveSelected('select1', 'select2')
	});
	$("#selectAll").click(function() {
		moveAll('select1', 'select2')
	});
	$("#deselect").click(function() {
		moveSelected('select2', 'select1')
	});
	$("#deselectAll").click(function() {
		moveAll('select2', 'select1')
	});
	// 页面加载的时候删除左边已经分配的权限
	var ids = [];
	$("#select2 option").each(function(index, option) {
		ids[index] = $(option).val();
	});

	$("#select1 option").each(function(index, option) {
		if ($.inArray($(option).val(), ids) > -1) {
			$(option).remove();
		}
	});

	// 提交时候
	$("#editForm").submit(function() {
		$("#select2 option").each(function(index, item) {
			$(item).prop("selected", true);
		})
	});
	$("#editForm").submit(function() {
		// 提交时候
		$("#select4 option").each(function(index, item) {
			$(item).prop("selected", true);
		})

	});

	/* 提交JSON插件的使用 */
	/*点击提交后使用的函数*/
	try {
		$("#editForm").ajaxForm(function(data) {
			var url = $("#editForm").data("url");
			if (data.success) {
				$.dialog({
					title : "温馨提示",
					content : "保存成功",
					icon : "face-smile",
					ok : function() {
						window.location.href = url
					}
				})
			} else {
				$.dialog({
					title : "温馨提示",
					content : data.message,
					icon : "face-smile",
					ok : true
				})
			}
		})
	} catch (e) {

	}

});

/* 删除操作 */
// 删除全显按钮
$(function() {
	$("#all").click(function() {
		$("input[name='IDCheck']").prop("checked", this.checked);
	})
});

// 批量删除
$(function() {
	$(".btn_batchDelete").click(function() {
		var url = $(this).data("url");
		if ($("input[name='IDCheck']:checked").size() == 0) {
			$.dialog({
				title : "温馨提示",
				content : "请选中要删除的数据",
				icon : "warning",
				ok : true
			});
			return;
		}
		$.dialog({
			title : "温馨提示",
			content : "确定要批量删除这些数据吗?",
			icon : "face-smile",
			ok : function() {
				var ids = [];
				$("input[name='IDCheck']:checked").each(function(index, item) {
					ids[index] = $(item).data("id");
				});
				$.post(url, {
					ids : ids
				}, function(data) {
					if (data.success) {
						$.dialog({
							title : "温馨提示",
							content : "删除成功",
							icon : "face-smile",
							ok : function() {
								window.location.reload();
							}
						})
					}
				})
			},
			cancel : true
		})
	})
});
