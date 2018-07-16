<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
  <link href="./css/commons.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <title>user Info</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Hello: ${user.userName}</h3>
 
    User Name: <b>${user.userName}</b>
    <br />
    Gender: ${user.gender } <br />
    
    <table>
    
     <ul>
         <li>User Assignments - Check the User Budget Responsibility Area Assignments.</li>
         <li>User Settings - Check the User role settings.</li>
         <li>Logon History - Show the logon history.</li>
         <li>Create Product</li>
         <li>Edit Product</li>
         <li>Delete Product</li>
      </ul>
    
    
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>