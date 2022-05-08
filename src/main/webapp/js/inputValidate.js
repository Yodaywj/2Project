$(document).ready(function () {
    //检查session是否含用户名
    $.ajax({
        url:"/user/session",
        method: "POST",
        dataType: "json",
        success: function (res) {
            console.log(res.result);
            console.log(res.user);
            if (res.result) {
                $("#loginDisplay").html("  ");
                    $("#sayHi").text("欢迎登陆，"+res.user.userName);
                $("#quit").text("退出");
                $("#center").text("用户中心");
                if (res.user.admin==='管理员'||res.user.admin==='root'){
                    $("#edit").text("用户管理");
                }
            } else {
                alert('123');
            }
        },
        error: function (){
            alert('wrong2')
        }
    });
    $("#center").click(function () {
        window.open("/user/question");
    });
    //退出按钮
    $("#quit").click(function () {
        $.ajax({
            url:"/user/logout",
            type: "get",
            dataType:"json",
            success:function (){
                console.log("quit success");
                $("#loginDisplay").html("\t\t\t\t\t<li class=\"nav-item active border-right border-white\">\n" +
                    "\t\t\t\t\t\t<a class=\"badge text-white\" href=\"../login/login.html\">登录</a>\n" +
                    "\t\t\t\t\t</li>\n" +
                    "\t\t\t\t\t<li class=\"nav-item active\">\n" +
                    "\t\t\t\t\t\t<a class=\"badge  text-white\" href=\"../login/register.html\">注册</a>\n" +
                    "\t\t\t\t\t</li>");
                $("#quit").text("");
            }
        });
        window.location.reload()
    });
});