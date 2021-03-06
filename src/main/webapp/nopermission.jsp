<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>没有权限界面</title>
<link href="/static/css/404.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var h = $(window).height();
		$('body').height(h);
		$('.mianBox').height(h);
		centerWindow(".tipInfo");
	});

	//2.将盒子方法放入这个方，方便法统一调用
	function centerWindow(a) {
		center(a);
		//自适应窗口
		$(window).bind('scroll resize',
				function() {
					center(a);
				});
	}

	//1.居中方法，传入需要剧中的标签
	function center(a) {
		var wWidth = $(window).width();
		var wHeight = $(window).height();
		var boxWidth = $(a).width();
		var boxHeight = $(a).height();
		var scrollTop = $(window).scrollTop();
		var scrollLeft = $(window).scrollLeft();
		var top = scrollTop + (wHeight - boxHeight) / 2;
		var left = scrollLeft + (wWidth - boxWidth) / 2;
		$(a).css({
			"top": top,
			"left": left
		});
	}
</script>
</head>
<body>
<div class="mianBox">
	<img src="static/image/yun0.png" alt="" class="yun yun0" />
	<img src="static/image/yun1.png" alt="" class="yun yun1" />
	<img src="static/image/yun2.png" alt="" class="yun yun2" />
	<img src="static/image/bird.png" alt="" class="bird" />
	<img src="static/image/san.png" alt="" class="san" />
	<div class="tipInfo">
		<div class="in">
			<div class="textThis">
				<h2>您没有该操作权限！</h2>
                <p><span>请联系管理员进行处理</span></p>
			</div>
		</div>
	</div>
</div>

</body>
</html>