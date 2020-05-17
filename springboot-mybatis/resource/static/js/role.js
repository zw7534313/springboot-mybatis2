
function addRoleWin(){
    $("#win_add_role").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../role/add_role.html",
        modal: true
    });
    
    var funcs = queryAllFuncs();
    showFuncs(funcs);
}

function queryAllFuncs(){
	var funcs = [];
	 var url = "/spring4mybatis3/rest/role/queryFuncs";
		$.ajax({
	        type: 'get',
	        url: url,
	        data: '',
	        dataType: 'json',
	        contentType: 'application/json; charset=UTF-8',
	        async: false,
	        success: function(data){
	        	debugger;
	        	if(data.status){
	               funcs = data.result;
	        	}
	        }
	    });
		return funcs;
}

function updateRoleWin(id, flag){
    $("#win_add_role").dialog({
        title: '',
        width: 760,
        height: 524,
        closed: false,
        cache: false,
        href: "../role/add_role.html",
        modal: true
    });
    debugger;
    var url = "/spring4mybatis3/rest/role/" + id;
		$.ajax({
            type: 'get',
            url: url,
            data: '',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            success: function(data){
            	debugger;
            	if(data.status){
	               var role = data.result;
	               $("#id").val(role.id);
	               $("#roleName").val(role.roleName);
	               $("input[name='status']").each(
	            		   function(index,e){
	            			   if(e.value == role.status){
	            				   e.checked = true;
	            			   }
	            		   }
	               );
	               
	               if(flag == "1"){ //查看
	            	   showFuncs(role.funcs);
	               }else{  //修改
	            	   var funcs = queryAllFuncs();
	           	       showFuncs(funcs);
	           	       
	           	       var myFuncs = role.funcs;
	           	       $("input[name='func']").each(
	           	    		   function(index, e){
	           	    			   var flag = hasFunc(id, myFuncs);
	           	    			   if(flag){
	           	    				   e.checked = true;
	           	    			   }
	           	    		   }
	           	       );
	               }
            	}
            }
        });
}

function lookRole(id){
	updateRoleWin(id, "1");
}

function showFuncs(funcs){
	debugger;
	var str = "";
    for(var i=0;i<funcs.length;i++){
 	   str += '<input name="func" type="checkbox" value="'+funcs[i].id+'"> '+ funcs[i].funcName;
 	   if((i+1)%4==0){
 		   str += "<br/>";
 	   }
    }
    $("#cdlist").append(str);
}

function hasFunc(id, funcs){
	var flag = false;
	for(var i=0;i<funcs.length;i++){
		if(id == funcs[i].id){
			flag = true;
			break;
		}
	}
	return flag;
}

function updateFuncs(funcs){
	debugger;
	var str = "";
    for(var i=0;i<funcs.length;i++){
 	   str += '<input name="func" type="checkbox" value="'+funcs[i].id+'"> '+ funcs[i].funcName;
 	   if((i+1)%4==0){
 		   str += "<br/>";
 	   }
    }
    $("#cdlist").append(str);
}

$.extend({deleteRole:function(id){
	$.ajax({
        type: 'get',
        url: "/spring4mybatis3/rest/role/delete/",
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

