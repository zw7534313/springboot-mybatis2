
function addFuncWin(){
    $("#win_add_func").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../func/add_func.html",
        modal: true
    });
}

function updateFuncWin(id){
    $("#win_add_func").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../func/add_func.html",
        modal: true
    });
    debugger;
    var url = "/spring4mybatis3/rest/func/" + id;
		$.ajax({
            type: 'get',
            url: url,
            data: '',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            success: function(data){
            	debugger;
            	if(data.status){
	               var func = data.result;
	               $("#id").val(func.id);
	               $("#funcName").val(func.funcName);
	               $("#value").val(func.value);
	               $("#type").val(func.type);
	               $("#supName").val(func.supName);
	               $("#status").val(func.status);
            	}
            }
        });
}

function lookFunc(id){
	updateFuncWin(id);
}

$.extend({deleteFunc:function(id){
	$.ajax({
        type: 'get',
        url: "/spring4mybatis3/rest/func/delete/",
        data: {'id': id},
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data){
        	debugger;
        	if(data.status){
        		 //$('#project-table').datagrid('reload');
                 //$('#project-table').datagrid('clearSelections');
        	}
        }
    });
}});

