function addSendsetWin(){
    $("#win_add_sendset").dialog({
        title: '',
        width: 760,
        height: 528,
        closed: false,
        cache: false,
        href: "../sendset/add_sendset.html",
        modal: true
    });
    selectId = undefined;
}

$.extend({deleteSendset:function(id){
	$.ajax({
        type: 'get',
        url: "/spring4mybatis3/rest/sendset/delete/" +id,
        //data: {'id': id},
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data){
        	if(data.status){
        		$('#project-table').datagrid('reload');
        	}
        }
    });
}});