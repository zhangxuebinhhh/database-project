$(function(){
    var url="InitializeNotification";
    $.ajax({
        type:'POST',
        url:url,
        dataType: "text",
        success:function(data){
            var arrayObj =JSON.parse(data);
            for(var i in arrayObj) {
                var li ="<li>\n" +
                    "                        <div class=\"author-thumb\">\n" +
                    "                            <img src=\"../images/avatar-10010.jpg\" alt=\"author\">\n" +
                    "                        </div>\n" +
                    "                        <div class=\"notification-event\">\n" +
                    "                            <input type=\"text\" class=\"h6 notification-friend\" contentEditable='false' readonly=\"readonly\" value="+arrayObj[i].content +">\n" +
                    "                            <span class=\"notification-date\"><time class=\"entry-date updated\"></time>"+arrayObj[i].stime+"</span>\n" +
                    "                        </div>\n" +
                    "                    </li>"
                $("#notiList").append(li);
            }
        },
        error:function(result){
            alert("error!!!!!!!!!!")
        }
    });
});
