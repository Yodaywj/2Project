$(function ()
{
    $('.change a').click(function ()
    {
        $('.signform').animate({height: 'toggle', opacity: 'toggle'}, 'slow');
    });
})

function start() {
    document.getElementById('signform').style.display=""
}

function signclose() {
    document.getElementById('signform').style.display="none"
    document.getElementById('registerform').style.display="none"
}
function loading() {
    document.getElementById('registerloading').style.display=""
}
function type1() {
    document.getElementById('type-counter').innerText="财产案件"
    document.getElementById('case').value="财产案件"
}
function type2() {
    document.getElementById('type-counter').innerText="离婚案件"
    document.getElementById('case').value="离婚案件"
}
function type3() {
    document.getElementById('type-counter').innerText="人格权案件"
    document.getElementById('case').value="人格权案件"
}
function type4() {
    document.getElementById('type-counter').innerText="其他非财产案件"
    document.getElementById('case').value="其他非财产案件"
}

$("#count").click(function (){
    var amount=document.getElementById("amount").value;
    var type=document.getElementById("case").value;
    $.ajax({
        url:"/Counter",    //请求的url地址
        dataType:"json",   //返回格式为json
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data:{"amount":amount,"type":type},    //参数值
        type:"POST",   //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            var fee = data.fee;
            var fee1 = data.fee1;
            var standard=document.getElementById('type-counter').innerText
            if (standard=="财产案件")
                document.getElementById("feeresult").innerText=fee+"元";
            else
                document.getElementById("feeresult").innerText=fee+"元~"+fee1+"元";
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
        }
    });
})
function resetall(){
    document.getElementById('type-counter').innerText="财产案件"
    document.getElementById('amount').value=0
    document.getElementById("feeresult").innerText=""
}
const bo = document.getElementById("body");
const signform = document.getElementById("signform");
document.body.addEventListener("click", function (e) {
    if (!bo.contains(e.target)) {
        signform.style.display = "none";
        console.log("当前节点被包含")
    } else {
        console.log("当前点击的节点不被其包含")
    }
})


