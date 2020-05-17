
$(function(){
	
	initUser();
    //菜单栏加载
    function menu(userId){
    	userId = "100";
        $.ajax({
            type: 'get',
            url: SERVER_PATH + "/rest/menu/load?userId=" + userId,
            data:"",
            dataType: 'json',
            contentType: 'apllication/json; charset=utf-8',
            success: function(data){
                var menudata=data.result;
                var menuUl='<ul class="menu-list ft12">';
                for(var i=0;i<menudata.length;i++){
                    if(i==0){
                        menuUl+="<li class='active'><i class='menu-icon-"+menudata[i].name+"'></i><p>"+ menudata[i].text +"</p></li>";
                        submenuload(menudata[i].menus,4,i);
                    }else{
                        menuUl+='<li><i class="menu-icon-'+menudata[i].name+'"></i><p>'+ menudata[i].text +'</p></li>';
                        submenuload(menudata[i].menus,4,i);
                    }
                }
                menuUl+='</ul>';
                $('#west').empty().append(menuUl);
            },
            error: function(data){
                $("#west").empty().append("<div style='color:#000'>菜单加载失败！</div>")
            }
        });
    }
    
    function initUser(){
        $.ajax({
            type: 'get',
            url: SERVER_PATH + "/rest/user/login",
            data: {'userName': "zhangsan"},
            dataType: 'json',
            contentType: 'apllication/json; charset=utf-8',
            success: function(data){
                menu(data.result.userId);
            },
            error: function(data){
                alert(data);
            }
        });
    }
    

    $("body").on("mouseover",".menu-list li",function(){
        var index=$(this).index();
        var self=$(this);
        $(".submenu").eq(index).css({"bottom":"","top":""});
        if($(".submenu").eq(index).height()>$(window).height()-self.offset().top){
            $(".submenu").css({"bottom":0,"top":"auto"})
        }else{
            $(".submenu").css("top",self.offset().top+5);
        }
        $(".submenu").hide().eq(index).show();
        $(".submenu").eq(index).hover(function(){
            $(".submenu").hide().eq(index).show()
        },function(){
            $(".submenu").hide();
            self.siblings().removeClass("hover");
        });
        if(self.hasClass("active")){
            self.siblings().removeClass("hover");
        }else{
            self.addClass("hover").siblings().removeClass("hover")
        }
    });
    //添加首页tabs
    $("#center-tabs").tabs("add",{
        title:'首页',
        closable:false,
        content:'<iframe src="pages/user/index.html" frameborder="0" width="100%" height="100%"></iframe>'
    });
    $("#center-tabs").tabs("resize")
    //点击菜单新增tab
//    $("#west").on("click",".menu-list li",function(){
//        if($(this).index()==0){
//            $('#center-tabs').tabs('add',{
//                title:'个人工作台',
//                content:'<iframe src="person/index.html" frameborder="0" width="100%" height="100%"></iframe>',
//                closable:'true'
//            })
//        }
//        if($(this).index()==9){
//            $('#center-tabs').tabs('add',{
//                title:'企业文化',
//                content:'<iframe src="corporateculture/index.html" frameborder="0" width="100%" height="100%"></iframe>',
//                closable:'true'
//            })
//        }
//    });
    //头部搜索框
    $(".header .search i").click(function(){
        if($(".header .search-box").width()==0){
            $(".header .search-box").css('border',"1px solid #cbcbcd").animate({width:248},500)
        }else{
            $(".header .search-box").animate({width:0},500).css('border',"none");
        }
    });
});
//阻止事件冒泡
function stopEvent(event){
    var e=arguments.callee.caller.arguments[0]||event;
    if (e && e.stopPropagation) {
        e.stopPropagation();
    } else if (window.event) {
        window.event.cancelBubble = true;
    }
}
//加载子菜单
function submenuload(data,colsize,index){
//    var sMenu = '<div class="submenu ft14"><div class="submenu-bg"></div><i class="submenu-left-arrow"></i>';
//    for(var l=0;l<Math.ceil(data[index].items.length/colsize);l++){
//        sMenu += "<div class='row'>";
//        if(l<Math.ceil(data[index].items.length/colsize)-1){
//            sMenu+="<ul class='clear'>";
//            for(var m=0;m<colsize;m++){
//                sMenu+="<li><i></i>"+data[index].items[m+colsize*l].text+"</a></li>"
//            }
//            sMenu+="</ul>"
//        }else{
//            sMenu+="<ul class='clear'>";
//            for(var m=0;m<data[index].items.length-colsize*l;m++){
//                sMenu+="<li><i></i>"+data[index].items[m+colsize*l].text+"</li>"
//            }
//            sMenu+="</ul>"
//        }
//        sMenu+="</div>";
//    }
//    sMenu += '</div>';
//    $('body').append(sMenu);
	
    var sMenu = $('<div class="submenu ft14"><div class="submenu-bg"></div><i class="submenu-left-arrow"></i></div>');
    for(var l=0;l<Math.ceil(data.length/colsize);l++){
    	var rowDiv = $('<div class="row"></div>');
    	var ul= $('<ul class="clear"></ul>');
    	if(l<Math.ceil(data.length/colsize)-1){
    		for(var m=0;m<colsize;m++){
                var li = $("<li><i></i>"+data[m+colsize*l].text+"</li>");
                li.click(data[m+colsize*l],function(e){addOnClickForLiMenu(e)});
                ul.append(li);
            }
        }else{
        	for(var m=0;m<data.length-colsize*l;m++){
                var li = $("<li><i></i>"+data[m+colsize*l].text+"</li>");
                li.click(data[m+colsize*l],function(e){addOnClickForLiMenu(e)});
                ul.append(li);
            }
        }
    	rowDiv.append(ul);
        sMenu.append(rowDiv);
    }
    $('body').append(sMenu);
}

function addOnClickForLiMenu(e){
	
	if ($('#center-tabs').tabs('exists', e.data.text)){
		$('#center-tabs').tabs('select', e.data.text);
	}else {
		$("#center-tabs").tabs("add",{
			id:e.data.name,
			title:e.data.text,
			closable:'true',
			content:'<iframe src="'+e.data.url+'" frameborder="0" width="100%" height="100%"></iframe>'
		});
	}
}