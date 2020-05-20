$(function(){
    var url="InitializeBlockRequest";
    $.ajax({
        type:'POST',
        url:url,
        dataType: "text",
        success:function(data){
            var arrayObj =JSON.parse(data);
            for(var i in arrayObj) {
                var li ="<li><div class=\"author-thumb\"><img src=\"${pageContext.request.contextPath}/images/avatar-sm.jpg\" alt=\"author\"></div><div class=\"notification-event\"><a href=\"#\" class=\"h6 notification-friend\" value="+arrayObj[i].uid+">"+arrayObj[i].username+"</a></div><span class=\"notification-icon\">\n" +
                "                    <a onclick=\"accept()\" id=\"acc\" class=\"accept-request\" value="+arrayObj[i].bmaid+">Accept Request</a>\n" +
                "                <a onclick=\"refuse()\" id=\"ref\" class=\"accept-request request-del\" value="+arrayObj[i].bmaid+">Refuse Request</a>\n" +
                "                </span></li>"
                $("#BlockMemAppList").append(li);
            }
        },
        error:function(result){
            alert("error!!!!!!!!!!")
        }
    });
});
function accept() {
    var bmaid=$("#acc").attr("value");
    var is_approved="accepted";
    backcotroller(bmaid ,is_approved);
}
function refuse() {
    var bmaid=$("#ref").attr("value");
    var is_approved="refused";
    backcotroller(bmaid,is_approved);
}
function backcotroller(bmaid,is_approved){
    var url="ans_block_request";
    $.ajax({
        type:'POST',
        url:url,
        data:  {"is_approved":is_approved,"bmaid":bmaid},
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