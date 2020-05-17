//var SERVER = "http://192.168.191.2:8080";

//分页
function grid_page(id,pageSize){
    $('#'+id).datagrid("getPager").pagination({
        pageSize: pageSize,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15, 20],//每页显示几条记录
        beforePageText: '',//页数文本框前显示的汉字
        showRefresh: false,
        afterPageText: '/{pages} ',
        displayMsg: '',
        onBeforeRefresh: function () {
            //$(this).pagination('loading');//正在加载数据中...
            //alert('before refresh');
            //$(this).pagination('loaded'); //数据加载完毕
        },
        layout: ['list', 'sep', 'first', 'sep', 'prev', 'sep', 'manual', 'sep', 'next', 'sep', 'last']
    });
    $('.'+id).find(".pagination table").append("<td class='pagination-textspan' >每页显示</td>")
    $('.'+id).find(".pagination table").append("<span class='pagination-textspan' style='left: 116px;'>条记录</span>")
    $('.'+id).find(".datagrid .datagrid-pager").append("<div class='pag-arrowbox'></div>");
    $('.'+id).find('.pagination table td:nth-child(2)').addClass("tdwidth-big");
    $('.'+id).find('.pagination table td:nth-child(9)>span').addClass("ft-16");
}
//弹窗
//显示
function showPanalAlert(width,height,alertEle,url){
    $(alertEle).dialog({
        title: '',
        width: width,
        height: height,
        closed: false,
        cache: false,
        href: url,
        modal: true,
        loadingMessage: ''
    });
}
//关闭
function hideAlert(ele){
    var windowParent=$(ele).parents(".window").parent();
    var needId=$(ele).parents(".window-body").attr("id");
    $("#"+$(ele).parents(".window-body").attr("id")).dialog('destroy');
    windowParent.append('<div id="'+needId+'"></div>');
}

//日期格式化
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()  
        // millisecond  
    }  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
            .substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
}  
function formatDatebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)  
} 

function formatDatebox2(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    } else {  
        dt = new Date(value);  
    }  
  
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)  
} 

var windowHeight=parseInt(window.innerHeight);
if(window.innerHeight==null){
    windowHeight=$(window).height();
}