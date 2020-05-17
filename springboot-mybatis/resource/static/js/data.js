var data_params = {};

function addFileWin(){
    $("#win_add_data").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../data/add_data.html",
        modal: true
    });
}

function updateFileWin(id){
    $("#win_add_data").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../data/add_data.html",
        modal: true
    });
    debugger;
    var url = "/spring4mybatis3/rest/data/" + id;
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

function lookFile(id){
	updateFileWin(id);
}

$.extend({deleteFile:function(id){
	$.ajax({
        type: 'get',
        url: "/spring4mybatis3/rest/data/delete/",
        data: {'id': id},
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data){
        	debugger;
        	if(data.status){
               
        	}
        }
    });
}});

function exportData(){
	var url = "/spring4mybatis3/rest/data/export";
	window.location = url;
}

function sendData(ids){
	$.ajax({
        type: 'get',
        url: "/spring4mybatis3/rest/data/senddata/",
        data: {'id': ids},
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data){
        	debugger;
        	if(data.status){
               
        	}
        }
    });
}

function openSendSetPage(ids){
	$("#win_add_sendset").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../data/add_sendset.html",
        modal: true
    });
	
	data_params.fileid = ids;
}

function confirmSend(setids){
	data_params.setid = setids;
	$("#win_confirm_send").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../data/confirm_send.html",
        modal: true
    });
}
