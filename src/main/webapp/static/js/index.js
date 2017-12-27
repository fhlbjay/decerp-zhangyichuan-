$(function(){

   /* //初始化
    $("#countTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=11&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //则新增
                if ($("#myTabs").tabs("exists", row.text)) {判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    console.log(new Date().getMilliseconds());
    //初始化
    $("#vipTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=19&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    //初始化
    $("#giftTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=24&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    //初始化
    $("#prouductTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=29&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    //初始化
    $("#productStackTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=33&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    //初始化
    $("#dayTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=15&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
    //初始化
    $("#smartTree").datalist({
            url: 'http://localhost/menu/getMenus.do?id=38&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );
   $("#systemTree").datalist({
            url: '/menu/getMenus.do?id=1&data='+new Date().getMilliseconds(),
            onClickRow: function (index, row) {
                var target = row.url;
                //判断对应节点文本内容的选项卡面板是否已存在,如果已存在就选中,不存在则新增
                if ($("#myTabs").tabs("exists", row.text)) {
                    //console.debug(node);
                    $("#myTabs").tabs("select", row.text);
                    return;
                }
                $("#myTabs").tabs("add",{
                    closable:true,
                    title:row.text,
                    content:'<iframe src='+target+' frameborder="0" style="width: 100%; height: 92%"></iframe>',
                })
            }
        }
    );*/
    /*//初始化系统菜单树
	$("#systemTree").tree({
		url:"http://localhost/menu/getMenus.do?id=1",
		onClick:function(node){
            if(node.url){ //有url是子菜单,继续往下设计
				//如果选项卡已经存在,就设置选中的状态
				var tag = $("#myTabs").tabs("exists",node.text);
				if(tag){
				   $("#myTabs").tabs("select",node.text);
				}else{
					//创建新的选项卡
					$("#myTabs").tabs("add",{
						title:node.text,
						//href:node.url,//只引入body内容,并且可能出现id冲突的问题
						content:'<iframe src='+node.url+' width=100% height=100% frameborder=0></iframe>',
						closable:true
					})
				}
			}else{//无url的是父级菜单,加个折叠,展开的功能
				$("#myTree").tree("toggle",node.target);
			}
			
		}
	});*/

	//初始化选项卡
	$("#myTabs").tabs({
		fit:true,
        closable:true
	})
});