$(function(){
    var url="InitializeFriendRequest";
    $.ajax({
        type:'POST',
        url:url,
        dataType: "text",
        success:function(data){
            alert(data)
            var arrayObj =JSON.parse(data);
            for(var i in arrayObj) {
                var li ="<li><div class=\"author-thumb\"><img src=\"../images/avatar-" + arrayObj[i].uid2 +
                    ".jpg\" alt=\"author\"></div><div class=\"notification-event\"><a href=\"#\" class=\"h6 notification-friend\" value="+arrayObj[i].uid2+">"+arrayObj[i].username+"</a></div><span class=\"notification-icon\">\n" +
                "                    <a onclick=\"accept()\" id=\"acc\" class=\"accept-request\" value="+arrayObj[i].faid+">Accept Friend Request</a>\n" +
                "                <a onclick=\"refuse()\" id=\"ref\" class=\"accept-request request-del\" value="+arrayObj[i].faid+">Refuse Friend Request</a>\n" +
                "                </span></li>"
                $("#frlist").append(li);
            }
        },
        error:function(result){
            alert("error!!!!!!!!!!")
        }
    });
});
function accept() {
    var faid=$("#acc").attr("value");
    var is_approved="accepted";
    backcotroller(faid,is_approved);
}
function refuse() {
    var faid=$("#ref").attr("value");
    var is_approved="refused";
    backcotroller(faid,is_approved);
}
function backcotroller(faid,is_approved){
    var url="ans_friend_request";
    $.ajax({
        type:'POST',
        url:url,
        data:  {"is_approved":is_approved,"faid":faid},
        dataType: 'text',
        success:function(data){
            if(data=='success'){
                alert("success");
                location.reload();
            }else if(data=='wrong'){
                alert("try again!");
            }
        }
    });
}