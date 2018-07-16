<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	<script src="./scripts/ckeditor.js"></script>
	
	<title>Communication</title>
</head>
<body>
<div id="wrapper">
	<div id="sidebar-wrapper">
		<ul class="sidebar-nav">
			<li class="sidebar-brand">
				<a href="#">
					Actions
				</a>
			</li>
			<li>
				<a href="#" data-toggle="modal" data-target="#newMessage"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>New Message</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>Refresh</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>Select All</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>Delete Selected Message(s)</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>Empty Folder</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true"></i>  &nbsp;<span>Extract  <input class="input-sm" type="text" value="500" id="rowsNumber"> rows</span></a>
			</li>
		</ul>
	</div>

	<!--<a href="#menu-toggle" class="glyphicon glyphicon-menu-left" id="menu-toggle"></a>-->
	<a href="#menu-toggle" id="menu-toggle"><i class="fa fa-caret-square-o-left text-gray" aria-hidden="true"></i></a>
	<div id="page-content-wrapper">
		<div class="container-fluid">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#home">In Box</a></li>
					<li><a data-toggle="tab" href="#menu1">Sent Items</a></li>
				</ul>
				<div class="tab-content">
					<div id="home" class="tab-pane fade in active">
						<table id="example" class="display cell-border compact" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th><input type="checkbox"/>Id</th><th></th><th></th><th>From</th><th>Subject</th><th>Date</th><th>Time</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
						</table>
					</div>
					<div id="menu1" class="tab-pane fade">
						<div class="dataTables_info" id="example_info" role="status" aria-live="polite">Showing 1 to 10 of 1 entries</div>
						<div class="dataTables_paginate paging_simple_numbers" id="example_paginate"><a class="paginate_button previous disabled" aria-controls="example" data-dt-idx="0" tabindex="0" id="example_previous">Previous</a><span><a class="paginate_button current" aria-controls="example" data-dt-idx="1" tabindex="0">1</a><a class="paginate_button " aria-controls="example" data-dt-idx="2" tabindex="0">2</a><a class="paginate_button " aria-controls="example" data-dt-idx="3" tabindex="0">3</a><a class="paginate_button " aria-controls="example" data-dt-idx="4" tabindex="0">4</a><a class="paginate_button " aria-controls="example" data-dt-idx="5" tabindex="0">5</a><a class="paginate_button " aria-controls="example" data-dt-idx="6" tabindex="0">6</a></span><a class="paginate_button next" aria-controls="example" data-dt-idx="7" tabindex="0" id="example_next">Next</a></div>
					</div>
				</div>
		</div>
	</div>
</div>
<script>
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
		if($("#wrapper")[0].className=="toggled"){
			$("#menu-toggle").find("i").removeClass("fa-caret-square-o-left");
			$("#menu-toggle").find("i").addClass("fa-caret-square-o-right");
		}else{
			$("#menu-toggle").find("i").addClass("fa-caret-square-o-left");
			$("#menu-toggle").find("i").removeClass("fa-caret-square-o-right");
		}
	});
	var data = [
			[
				'<input type="checkbox"/>1',
					'',
				'<span class="	glyphicon glyphicon-envelope text-yellow"></span>',
					'System',
				'Invalid logon for user handcj',
				'26/Jul/16',
				'10:29'
			],
			[
				'<input type="checkbox"/>1',
					'',
				'<i class="fa fa-envelope-open text-gray" aria-hidden="true"></i>',
					'System',
				'Invalid logon for user handcj',
				'26/Jul/16',
				'10:29'
			]
	]
	var table = $('#example').DataTable( {
		data: data,
		"searching":false,
		"bFilter" : false,
		"ordering":false,
		"bLengthChange": false,
		"oLanguage": {
			"sInfo": "_START_ - _END_ of _TOTAL_ items",
			"oPaginate": {
				"sNext": '<i class="fa fa-caret-right main-text-color" aria-hidden="true"></i>',
				"sPrevious": '<i class="fa fa-caret-left main-text-color" aria-hidden="true"></i>',
				"sFirst": '<i class="fa fa-step-backward" aria-hidden="true"></i>',
				"sLast": '<i class="fa fa-step-forward" aria-hidden="true"></i>'
			}
		},
		"dom": '<"top"ip><"clear">'
	} );
	window.onload=function(){
		console.log('this');
		CKEDITOR.replace("message",{
			toolbar: [
				[ 'Cut', 'Copy', 'Paste','Bold', 'Italic' ,"Underline","Strike",'Subscript', 'Superscript',"Indent","Outdent",'JustifyLeft', 'JustifyCenter', 'JustifyRight']
			]
		});
	}
</script>

<div id="newMessage" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Send Message</h4>
      </div>
      <div class="modal-body">
      <div class="row">
      	<div class="col-xs-3 text-right main-text-color">User:</div>
      	<div class="col-xs-9">
      	  <textarea rows="3" id="comment" style="width:70%;"></textarea><a href="#">Search</a>
      	</div>
      </div>
     <div class="row">
      	<div class="col-xs-3 text-right main-text-color">Destination List:</div>
      	<div class="col-xs-9">
      	  <input type="text" id="comment"  style="width:60%;"/><a href="#">Search</a>
      	</div>
      </div>
     <div class="row">
      	<div class="col-xs-3 text-right main-text-color">Message Type:</div>
      	<div class="col-xs-9">
      	  <select id="comment" style="width:50%; margin-top:3px;margin-bottom:3px;">
      	  	<option value="systemMessage">System Message</option>
      	  </select>
      	</div>
      </div>
      
     <div class="row">
      	<div class="col-xs-3 text-right main-text-color">Subject:</div>
      	<div class="col-xs-9">
      	  <input type="text" class="form-control" id="comment" style="margin-bottom:3px"/>
      	  
      	</div>
      </div>
      
     <div class="row">
      	<div class="col-xs-3 text-right main-text-color">Message:</div>
      	<div class="col-xs-9">
      	  <textarea rows="3" id="message" class="form-control"></textarea>
      	</div>
      </div>
		
		<div class="row">
			<div class="col-xs-3 text-right main-text-color">Attach:</div>
			<div class="col-xs-9"><input type="file" id="file"/></div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Send</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

</body>
</html>