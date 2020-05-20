function search(){

    var uid = $('#neighboorsearchid').val();
    var url="searchUser";
    $.ajax({
        type:'POST',
        url:url,
        data : {'uid':uid},
        dataType: "text",
        success:function(data){
            var arrayObj =JSON.parse(data);
            var li ="<div class=\"friend-item\">\n" +
                    " <div class=\"friend-header-thumb\"><img src=\" "+
                    "../images/avatar-"+ arrayObj.id + ".jpg\" alt=\"friend\">\n </div>\n" +
                    " </div>"+
                "<div class=\"control-block-button\"  data-swiper-parallax=\"-100\" style=\"transform: translate3d(100px, 0px, 0px); transition-duration: 0ms;\">"
                + "<button onclick= \"apply_friend(" + arrayObj.id + ") \" > apply friend </button>"+
                "<button onclick= \"set_ne(" + arrayObj.id + ")\" >set neighbor </button>"+
                "</div>"
            $("#newguyitem").append(li);
        }
    });
};

function apply_friend(uid){

var url="add_friend_request";
$.ajax({
    type: 'GET',
    url: url,
    data: {'uid2': uid},
    dataType: "text",
    success: function (data) {
        alert(data)
    }
})

    location.reload();

}

function set_ne(uid){
    alert("set nieghbor")
    var url="set_neighbor_request";
    $.ajax({
        type: 'GET',
        url: url,
        data: {'uid': uid},
        dataType: "text",
        success: function (data) {
            alert(data)
        }
    })


    location.reload();

}