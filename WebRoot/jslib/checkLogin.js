/**
 * 用户登陆系统相关验证
 * @author 孙红波
 * @Create Date：2011-05-14
 */



// 用户名验证
function validateUserName(){
	var un = $("#loginname");
    if (un.val() === "") {
      	$("#errorimage").addClass("errorFontStyle");
        $("#error").html("用户名不能为空！");
        un.focus();
        return false;
    }
    return true;
}

// 密码验证
function validatePassWord(){
	var pw = $("#password");
    if (pw.val()=== "") {
        $("#errorimage").addClass("errorFontStyle");
        $("#error").html("密码不能为空！");
        pw.focus();
        return false;
    }
    return true;
}


// 用户登录验证
function validateLogin(){
    var loginname = $("#loginname").val();
    var password = $("#password").val();
    if(!validateUserName()) {
    	return false;
    }
    if(!validatePassWord()){
    	return false;
    }
    $.ajax({
           type: "post",
           url: "login",  
           cache: false,
           data: {loginname:loginname,password:password},
           dataType: "text",
           success: function(response){
               if (response == "error") {
               		$("#errorimage").addClass("errorFontStyle");
               		$("#error").html("用户名或密码错误！");
               }else if(response == "admin"){  // 超级管理员
                	document.location.href = "index";
               }else if(response == 'normal'){  // 匿名调查
            	   document.location.href = "hiddenAskindex";
               }else{  //普通用户的调查
            	   document.location.href = "nomalindex";  
               }
               
           }
       });
}

//登录表单重置功能
function formReset(){
    var un = $("#userName").val("");
    var pw = $("#passWord").val("");
    un.focus();
}
