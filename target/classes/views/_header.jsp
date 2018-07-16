<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
      <link href="./css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
    <link href="./bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.css" rel="stylesheet" type="text/css">
    <link href="./bower_components/components-font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="./css/theme.css" rel="stylesheet" type="text/css">
    <link href="./css/layout.css" rel="stylesheet" type="text/css">
    <link href="./css/simple-sidebar.css" rel="stylesheet" type="text/css">
    <link href="./css/common.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="./bower_components/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="./bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.js"></script>
    <script type="text/javascript" src="./scripts/jquery.dataTables.min.js"></script>
  
 <div style="background: #ffffff; height: 50px; padding: 3px;"> 
 
  <div style="float: left">
  <!--  <img src="${pageContext.request.contextPath}/images/bonhams.png" alt="logo" style="width:100px;height:50px;"> --> 
     	<tr>
				<td colspan="2" class="softproSplash">
					<img src="../cppro/images/home.png" alt="splash"/>
				</td>
			</tr>
  </div>
  
 
  <div style="float: right; padding: 10px; text-align: right;">
 
     <!-- User store in session with attribute: loginedUser -->
     Welcome <b>${loginedUser.userName}</b>
   
   </div>
 </div>