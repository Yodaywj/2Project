//从缓存中获取数据并渲染
let msgBoxList = JSON.parse(window.localStorage.getItem('msgBoxList')) || [];
innerHTMl(msgBoxList)
//点击发表按扭，发表内容
$("span.submit").click(function () {
    let txt = $(".message").html(); //获取输入框内容

    let obj = {
        msg: txt
    }
    msgBoxList.unshift(obj) //添加到数组里
    window.localStorage.setItem('msgBoxList', JSON.stringify(msgBoxList)) //将数据保存到缓存
    innerHTMl([obj]) //渲染当前输入框内容
    $('.message').html('') // 清空输入框

});

//删除当前数据
$("body").on('click', '.del', function () {
    let index = $(this).parent().parent().index();
    msgBoxList.splice(index, 1)
    window.localStorage.setItem('msgBoxList', JSON.stringify(msgBoxList)) //将数据保存到缓存
    $(this).parent().parent().remove()
})

//渲染html
function innerHTMl(List) {
    List = List || []
    List.forEach(item => {
        let str =
            `<div class='msgBox'>
					<div class="headUrl">
						<div>
							<span class="title">评论</span>
							<span class="time"></span>
						</div>
						<a class="del">删除</a>
					</div>
					<div class='msgTxt'>
						${item.msg}
					</div>
				</div>`
        $(".msgCon").prepend(str);
    })
}