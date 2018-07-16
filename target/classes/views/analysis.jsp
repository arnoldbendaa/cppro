<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="./css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="./css/style.min.css" />
	<link rel="stylesheet" href="./css/font-awesome.min.css" />

	<link href="./bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.css" rel="stylesheet" type="text/css">
	<link href="./bower_components/components-font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="./css/theme.css" rel="stylesheet" type="text/css">
	<link href="./css/layout.css" rel="stylesheet" type="text/css">
	<link href="./css/simple-sidebar.css" rel="stylesheet" type="text/css">
	<link href="./css/common.css" rel="stylesheet" type="text/css">
    <link href="./css/ui.fancytree.css" rel="stylesheet"/>
    <link rel="stylesheet" href="./css/analysis.css"/>

	<script type="text/javascript" src="./bower_components/jquery/dist/jquery.min.js"></script>
	    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
	
	<script type="text/javascript" src="./bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./bower_components/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.js"></script>
	<script type="text/javascript" src="./scripts/jquery.dataTables.min.js"></script>
	    <script src="./scripts/jquery.fancytree.js"></script>
	        <script src="./scripts/jquery.fancytree.dnd.js"></script>
	    
    <script src="./scripts/jquery.fancytree.table.js"></script>
	
	<title>Communication</title>
</head>
	<script>
		var baseUrl = "${baseUrl}";
	</script>

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
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>New</a>
			</li>
			<li>
				<a href="#"><i class="fa fa-circle" aria-hidden="true">  &nbsp;</i>Refresh</a>
			</li>
		</ul>
	</div>
	<a href="#menu-toggle" id="menu-toggle"><i class="fa fa-caret-square-o-left text-gray" aria-hidden="true"></i></a>
	<div id="page-content-wrapper">
		<div class="container-fluid">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#home">Public Analysis</a></li>
					<li><a data-toggle="tab" href="#menu1">My Analysis</a></li>
				</ul>
				<div class="tab-content">
					<div id="home" class="tab-pane fade in active">
						<table id="treetable">
						    <colgroup>
						        <col class="col-md-8"></col>
						        <col class="col-md-3"></col>
						    </colgroup>
						    <thead>
						    <tr>
						        <th class="col-md-8 text-center">Analysis</th>
						        <th class="col-md-3">Action</th> </tr>
						    </thead>
						    <tbody>
						    </tbody>
						</table>
					</div>
					<div id="menu1" class="tab-pane fade">
					analysis
					</div>
				</div>
		</div>
	</div>
</div>

<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Create</h4>
            </div>
            <div class="modal-body">
                <input type="text" class="form-control" id="folderName"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="createNode();">Create</button>
            </div>
        </div>

    </div>
</div>
<div id="editModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Property</h4>
            </div>
            <div class="modal-body">
                <input type="text" class="form-control" id="propertyName"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="editNode();">Ok</button>
            </div>
        </div>
    </div>
</div>
<div id="moveModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Move Analysis To Another Folder</h4>
            </div>
            <div class="modal-body">
                <select type="text" class="form-control" id="folderSelect"></select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="moveNode();">Move Analysis</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
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
	
	
    $("#treetable").fancytree({
        extensions: ["table"],
        source: [
            {"title": "Public", "expanded": true, "folder": true, "children": [
                {"title": "Hammer", "folder": true, "children": [
                    {"title": "Hammer Sales"}
                ]}
            ]}
        ],
        icon: false,
        renderColumns: function(event, data) {
            var node = data.node,
                    $tdList = $(node.tr).find(">td");
            var level = node.getLevel();
            if(level==1)
                $tdList.eq(1).html('<a type="button" href="#" data-toggle="modal" data-target="#myModal"><span class="folderIcon createTreeColor">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a>');
            else if(level==2)
                $tdList.eq(1).html('<a type="button" href="#" data-toggle="modal" data-target="#myModal"><span class="folderIcon fa-2x createTreeColor">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a><a href="#" onclick="renameNode();"><span class="settingIcon createTreeColor">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a><a href="#" onclick="removeNode();"><span class="removeIcon deleteTreeColor">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a>');
            else if(level==3)
                $tdList.eq(1).html('<a href="#" onclick="openMoveDlg();"><span class="moveIcon createTreeColor">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a><a href="#"><span class="removeIcon deleteTreeColor" onclick="removeNode();">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;</a>');
            else
                    return;
        },
        table: {
            indentation: 20,      // indent 20px per node level
        },

        init: function(event, data){
        },
        keydown: function(event, data) {
            var KC = $.ui.keyCode,
                    oe = event.originalEvent;
            // Swap LEFT/RIGHT keys
            switch( event.which ) {
                case KC.LEFT:
                    oe.keyCode = KC.RIGHT;
                    oe.which = KC.RIGHT;
                    break;
                case KC.RIGHT:
                    oe.keyCode = KC.LEFT;
                    oe.which = KC.LEFT;
                    break;
            }
        }
    });
function createNode() {
    setTimeout(function(){
        console.log("create node called");
        var tree = $("#treetable").fancytree("getTree");
        var node = tree.getActiveNode();
        var title = $("#folderName").val();
        if(title==""||title==undefined) return;
        $("#folderName").val("");
        node.addChildren({
            title:title
        });
        console.log(baseUrl);
        var level = node.getLevel();
        $.ajax({
        	url:baseUrl+"/"+"CreateAnalysis",
        	type:"POST",
        	data:{title:title,level:level},
        	success:function(data){
        		console.log(data);
        	}
        });
    },300);
}
function removeNode() {
    setTimeout(function(){
        console.log("create node called");
        var tree = $("#treetable").fancytree("getTree");
        var node = tree.getActiveNode();
        node.remove();
    },300);
}
function renameNode() {
    setTimeout(function(){
        var tree = $("#treetable").fancytree("getTree");
        var node = tree.getActiveNode();
        $("#propertyName").val(node.title);
        $("#editModal").modal("show");
    },300);
}
function editNode() {
    var propertyName = $("#propertyName").val();
    if(propertyName==""||propertyName==undefined) return;
    var tree = $("#treetable").fancytree("getTree");
    var node = tree.getActiveNode();
    node.setTitle(propertyName);
}
function getDatas(){
    var tree = $("#treetable").fancytree("getTree");
    var root = tree.getRootNode();
    var nodes = root.children[0].getChildren();
    var len = nodes.length;
    var html ="";
    for(var i = 0 ; i < len; i++){
        var title = nodes[i].title;
        var value = nodes[i].getIndex();
        html = html + '<option value="'+value+'">'+title+'</option>';
    }
    return html;
}
function openMoveDlg() {
    setTimeout(function(){
        var html = getDatas();
        $("#folderSelect").html(html);
        $("#moveModal").modal("show");
        var tree = $("#treetable").fancytree("getTree");
        var node = tree.getActiveNode();
        var index = node.parent.getIndex();
        $("#folderSelect").val(index);
    },300);
}
function moveNode() {
    var tree = $("#treetable").fancytree("getTree");
    var node = tree.getActiveNode();
    var index = $("#folderSelect").val();
    var root = tree.getRootNode();
    var nodes = root.children[0].getChildren();

    node.moveTo(nodes[index],"over");
}
</script>

</body>
</html>