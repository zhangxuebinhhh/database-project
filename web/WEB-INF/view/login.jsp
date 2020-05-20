<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Login and Registration</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Login and Registration Form with HTML5 and CSS3" />
        <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/animate-custom.css" />
    </head>
    <body>
        <div class="container">
            <!-- Codrops top bar -->
            <header>
                <h1>nextdoor <span>Discover your neighborhood</span></h1>
            </header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <div autocomplete="on">
                                <h1>Log in</h1> 
                                <p> 
                                    <label for="userid" class="uname" data-icon="u" > Your email or username </label>
                                    <input id="userid" name="userid" required="required" type="text" placeholder="myusername or mymail@mail.com"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> Your password </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                                </p>

                                <p class="login button"> 
                                    <input type="submit" value="Login" onclick="signin()"/>
								</p>
                                <p class="change_link">
									Not a member yet ?
									<a href="#toregister" class="to_register">Join us</a>
								</p>
                            </div>
                        </div>

                        <div id="register" class="animate form">
                            <div autocomplete="on">
                                <h1> Sign up </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                                    <input id="usernamesignup" name="usernamesignup" required="required" type="text" placeholder="mysuperusername690" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
                                    <input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                                    <input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                                    <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="Sign up" onclick="signup()"/>
								</p>
                                <p class="change_link">  
									Already a member ?
									<a href="#tologin" class="to_register"> Go and log in </a>
								</p>
                            </div>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
        <script>
            function signin() {
                var user={};
                user["id"]= $('#userid').val();
                user["password"]=$('#password').val();
                var url="login";
                $.ajax({
                    type:'POST',
                    url:  url,
                    data:  user,
                    dataType: "text",
                    success:function(data){
                        if(data=="failed"){
                            alert("failed, check your password or userId");
                        }else{
                            window.location.href='AllFriendsMessage';
                        }
                    },
                    error:function(){
                        alert("error!!!!!!!!!!");
                    }
                });
            }
            function signup() {
                var usernamesignup=$('#usernamesignup').val();
                var emailsignup=$('#emailsignup').val();
                var passwordsignup=$('#passwordsignup').val();
                var passwordsignup_confirm=$('#passwordsignup_confirm').val();
                if(passwordsignup!=passwordsignup_confirm){
                    alert("password different!");
                    return;
                }
                var user={};
                user["password"]=passwordsignup;
                user["uEmail"]=emailsignup;
                user["username"]=usernamesignup;
                var url="signup"
                $.ajax({
                    type:'POST',
                    url:url,
                    data:  user,
                    dataType: "text",
                    success:function(data){
                        if(data=="failed"){
                            alert("failed, try again");
                        }else{
                            alert("Your ID is:"+data);
                            window.location.href='loginpage'
                        }
                    },
                    error:function(){
                        alert("error!!!!!!!!!!")
                    }
                });
            }
        </script>
    </body>
</html>
