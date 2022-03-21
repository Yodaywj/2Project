function func() {
    var pwd = document.getElementById("dupPassword").value;
    var pwd1 = document.getElementById("dupPassword1").value;
    var user = document.getElementById("userName").value;
    if(pwd1 !== pwd){
        alert("两次密码不一致");
        return false;
    } else if(user == ""){
        alert("用户名未输入");
        return false;
    }
    else if (pwd == ""){
        alert("密码未输入");
        return false;
    }
    else {
        return true;
    }
}
function func1() {
    var pwd1 = document.getElementById("userPassword").value;
    var user1 = document.getElementById("userName").value;
    if(user1 == ""){
        alert("用户名未输入");
        return false;
    }
    else if (pwd1 == ""){
        alert("密码未输入");
        return false;
    }
    else {
        return true;
    }
}