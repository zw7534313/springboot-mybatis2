


function addUserWin(){
    $("#win_add_user").dialog({
        title: '',
        width: 760,
        height: 528,
        closed: false,
        cache: false,
        href: "../user/add_user.html",
        modal: true
    });
    selectId = undefined;
}

function updateUserWin(id){
    $("#win_add_user").dialog({
        title: '',
        width: 760,
        height: 528,
        closed: false,
        cache: false,
        href: "../user/add_user.html",
        modal: true
    });
    selectId = id;
    //var url = "/spring4mybatis3/rest/user/" + id;
		/*$.ajax({
            type: 'get',
            url: url,
            data: '',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            success: function(data){
            	debugger;
            	if(data.status){
	               var user = data.result;
	               $("#userId").val(user.userId);
	               $("#userNo").val(user.userNo);
	               $("#userName").val(user.userName);
	               $("#email").val(user.email);
	               $("#phone").val(user.officeTel);
            	}
            }
        });
        */
    //$('#ff').form('load',url);
}

function lookUser(id){
	updateUserWin(id);
}

$.extend({deleteUser:function(id){
	$.ajax({
        type: 'get',
        url: "/boot/user/delete/",
        data: {'id': id},
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data){
        	//if(data.status){
        	//	$('#project-table').datagrid('reload');
        	//}
        }
    });
}});


function addAuthorityWin(userId){
    $("#win_add_auth").dialog({
        title: '',
        width: 760,
        height: 528,
        closed: false,
        cache: false,
        href: "../user/add_auth.html",
        modal: true
    });
    queryMyRoles(userId);
}

function queryMyRoles(userId){
	var roles = [];
	 var url = "/spring4mybatis3/rest/role/querymyroles";
		$.ajax({
	        type: 'get',
	        url: url,
	        data: '',
	        dataType: 'json',
	        contentType: 'application/json; charset=UTF-8',
	        //async: true,
	        success: function(data){
	        	if(data.status){
	               roles = data.result;
	               
	               var str = "";
	               for(var i=0;i<roles.length;i++){
	            	   str += "<input name='role' type='checkbox' value='"+roles[i].id+"'> ";
	            	   str += roles[i].roleName;
	            	   if((i+1)%4==0){
	            		   str += "<br/>";
	            	   }
	               }
	               
	               $("#rolelist").append(str);
	               $("#userId").val(userId);
	        	}
	        }
	    });
}

function exportUser(){
	var url = "/spring4mybatis3/rest/user/export";
	window.location = url;
}


