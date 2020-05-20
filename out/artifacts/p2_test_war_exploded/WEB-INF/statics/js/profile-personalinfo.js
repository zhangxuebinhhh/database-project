function saveChang(){
    var uname=$('username').val();
    var email=$('#email').val();
    var website=$('#website').val();
    var birthday=$('#birthday').val();
    var address=$('#address').val();
    var description=$('#description').val();
    var gender=$('#gender').val();
    var religion=$('#religion').val();
    var birthplace=$('#birthplace').val();
    var occupation=$('#occupation').val();
    var status=$('#status').val();
    var pincline=$('#pincline').val();
    var user = {};
    user["username"]   =     $("#username").val();
    user["uaddress"]   =     $("#address").val();
    user["uEmail"]   =     $("#email").val();
    user["birthday"]   =     $("#birthday").val();
    user["description"]   =     $("#description").val();
    user["occupation"]   =     $("#occupation").val();
    user["birthplace"]   =     $("#birthplace").val();
    user["gender"]   =     $("#gender").val();
    user["status"]   =     $("#status").val();
    user["pincline"]   =     $("#pincline").val();
    user["website"]   =     $("#website").val();
    user["religion"]   =     $("#religion").val();
    if(user["username"]==""){
        alert("username is not valid");
        return;
    }
    var url= "updatePersonalinfo";
    $.ajax({
        type:'POST',
        url:url,
        data:  user,
        dataType: "text",
        success:function(data){
            if(data=="successttt"){
                alert("chang saved!");
            }else{
                alert("failed, try again");
            }
        },
        error:function(result){
            alert("error!!!!!!!!!!")
        }
    });
}