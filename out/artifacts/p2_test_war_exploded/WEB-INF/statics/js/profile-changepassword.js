function saveChang(){
    var current_password=$('#current_password').val();
    var new_password=$('#new_password').val();
    var confirm_pass=$('#confirm_pass').val();
    //var url = "home/updatePassword";
    if(confirm_pass!=new_password){
        alert("两次不一致！");
        return;


    }
    var url= "updatePassword";
    $.ajax({
        type:'POST',
        url:url,
        data:  {"new_password":new_password,"old_password":current_password},
        dataType: 'text',
        success:function(data){
            if(data=='success'){
                alert("chang saved!");
            }else if(data=='passWrong'){
                alert("current password is wrong");
            }else{
                alert("failed, try again");
            }
        }
    });
    /*$.post(
        url,
        {"new_password":new_password,"old_password":current_password},
         function(result) {
            if(result=='success'){
                alert("chang saved!");
            }else if(result=='passWrong'){
                alert("current password is wrong");
            }else{
                alert("failed, try again");
            }
        }
    );*/
}