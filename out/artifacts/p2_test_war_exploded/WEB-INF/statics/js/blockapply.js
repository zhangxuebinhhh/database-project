$(function(){
    var url="InitializeBlockList";
    $.ajax({
        type:'POST',
        url:url,
        dataType: "text",
        success:function(data){
            var arrayObj =JSON.parse(data);
            for(var i in arrayObj) {
                var li ="<li><div class=\"author-thumb\"></div><div class=\"notification-event\"><a href=\"#\" class=\"h6 notification-friend\" value="+arrayObj[i].bid+">"+arrayObj[i].bname+"</a><span class=\"chat-message-item\">"+arrayObj[i].blocation+"</span></div><span class=\"notification-icon\">\n" +
                "                    <a onclick=\"apply()\" id=\"acc\" class=\"accept-request\" value="+arrayObj[i].bid+">Apply</a>\n" +
                "                </span></li>"
                $("#BlockList").append(li);
            }
        },
        error:function(result){
            alert("error!!!!!!!!!!")
        }
    });
});
function apply() {
    var bid=$("#acc").attr("value");
   // alert(bid);
    var url="apply_block";
    $.ajax({
        type:'POST',
        url:url,
        data:  {"bid":bid},
        dataType: 'text',
        success:function(data){
            if(data=='success'){
                alert("success");
                //location.reload();
            }else if(data=='in'){
                alert("you're already in this block");
            }else{
                alert("try again");
            }
        }
    });
}
/*
function refuse() {
    var bmaid=$("#ref").attr("value");
    var is_approved="refused";
    backcotroller(bmaid,is_approved);
}
function backcotroller(bmaid,is_approved){
}*/
