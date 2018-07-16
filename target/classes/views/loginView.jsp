<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
  <link href="./css/commons.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <title>Login</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
 <!--    <jsp:include page="_menu.jsp"></jsp:include> -->
 <div style="background: #4682b4; height: 30px; padding: 5px;"> </div>
      <br><br><br><br><br><br>
  
    <p align=center size=24> Note: user ids and passwords are case sensitive</p>
 
    <form method="POST" class="col-md-4 col-md-offset-4" action="doLogin">
    
      <div class="input-group">
		  <div class="input-group-addon">
			<i class="fa fa-user"></i> 
		  </div>
		  <input type="text" name="userName" value= "${user.userName}" placeholder="User Name" class="form-control" />
	  </div>
	  <br>
	    <div class="input-group">
			  <div class="input-group-addon">
				<i class="fa fa-key"></i> 
			  </div>			  
			  <input type="password" name="password" value= "${user.password}" placeholder="password" class="form-control" />
		  </div>
		  <br>
		          <p style="color: red;" class="text-center">${errorString}</p>
		  
  	    <div class="input-group col-md-8 col-md-offset-2">
			  <input type="submit"  value= "Login" placeholder="password" class="form-control btn-primary" />
		  </div>
		 <br>
  	    <div class="input-group col-md-12 text-center">
 	    	<a href="#" id="resetPassword">Reset Password</a>
	  </div>
		  
             <!-- <td><input type="checkbox" name="rememberMe" value= "Y" /> </td> -->
     </form>
    
       
    <style>
    	.input-group-addon{
    		border-radius:0;
    		background-color:transparent;
    	}
    	.form-control{
    		border-radius:0;
    	}
    </style>
 <script type="text/javascript">
 localStorage['cp_base_url'] = "/cppro/";
 </script>

    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>