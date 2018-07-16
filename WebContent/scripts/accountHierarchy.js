var elementData = [];var selected = [];
var dimensionTable,hierarchyTable
var operation = "";
var calendarData;
var selectedDimensionIndex = -1;
var selectedDimensionTrIndex = -1;
var selectedHiearchyIndex = -1;
var dimensionId = -1;
var dimensions;
var treeData =  {"title": "Public", "expanded": true, "folder": true,"credit":'1',"augCreditDebit":0}

function drawHierarchyTable(){
	if(hierarchyTable!=""&&hierarchyTable!=undefined){
		hierarchyTable.destroy();
	}
	hierarchyTable = $("#tblHierarchyDimension").DataTable({
		data:calendarData,
		"scrollY":"60vh",
		"bAutoWidth": true,
        "scrollCollapse":true,
        "processing":true,
	    "searching":false,
	    "bFilter" : false,
	    "ordering":false,
	    "bLengthChange": false,
	    "info":     false,
	    "paging":   false,
        "rowCallback": function( row, data ) {
            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                $(row).addClass('selected');
            }
        }
	});
}
function getDimensionDatas(){
	$.ajax({
		url:localStorage["cp_base_url"]+"accountHierarchy",
		type:"POST",
		data:{
			operation:"read",
		},
		success:function(data){
			var jData = JSON.parse(data);
			calendarData = jData.hierarchies;
			dimensions = jData.dimensions;
			var html = "";
			for(var i = 0 ; i < dimensions.length; i++){
				html+="<option value='"+dimensions[i][0]+"'>"+dimensions[i][1]+"</option>";
			}
			$("#dimensionVisId").html(html);
			drawHierarchyTable();
			drawDimensionTable();
		}
	})
}
function create(){
	operation = "add";
	$("#chooseDimension").modal("show");
}
function drawTree(){
	 $("#tree").fancytree({
        source: [treeData],
        activate:function(event,data){
        	var level = data.node.getLevel();
    		$("#treeIdentifier").prop("disabled",false);
    		$("#treeDescription").prop("disabled",false);

        	if(level==1){
        		$("#btnDelete").prop("disabled",true);
        		$("#btnInsertBefore").prop("disabled",true);
        		$('#btnInsertAfter').prop("disabled",true);
        		$("#btnmoveUp").prop("disabled",true);
        		$("#btnmoveDown").prop("disabled",true);
        		$('#btnInsertDependent').prop("disabled",false);
        		$("#treeIdentifier").val(treeData.title);
        		if(operation=="edit"){
            		$("#treeIdentifier").prop("disabled",true);
            		$("#treeDescription").prop("disabled",true);
        		}
        		$("#hierarchyVisId").prop("disabled",false);
        		$("#hierarchyDescription").prop("disabled",false);
        	}
        	else {
        		$("#btnDelete").prop("disabled",false);
        		$("#btnInsertBefore").prop("disabled",false);
        		$('#btnInsertAfter').prop("disabled",false);
        		$('#btnInsertDependent').prop("disabled",false);
        		$("#btnmoveUp").prop("disabled",false);
        		$("#btnmoveDown").prop("disabled",false);
        		$("#hierarchyVisId").prop("disabled",true);
        		$("#hierarchyDescription").prop("disabled",true);
        	}
        	var path = data.node.getIndexHier();
        	var paths = path.split(".");
        	var len = paths.length;
        	var temp = getNodeData(treeData,path);
        	var mergedString = temp.title;
        	$("#treeIdentifier").val(mergedString.split(" - ")[0]);
        	$('#treeDescription').val(mergedString.split(" - ")[1]);
        	$("input[name=creditDebit][value=" + temp.credit + "]").prop('checked', true);
        	$("input[name=augCreditDebit][value=" + temp.augCreditDebit + "]").prop('checked', true);
        	if(level==1){
        		$("#hierarchyVisId").val(mergedString.split(" - ")[0]);
        		$("#hierarchyDescription").val(mergedString.split(" - ")[1]);
        	}
        }
	 })
}
$("#hierarchyVisId").on("keyup",function(){
	rootChanged();
})
$("#hierarchyDescription").on("keyup",function(){
	rootChanged();
})
function rootChanged(){
	var tree = $("#tree").fancytree("getTree");
	var activeNode = tree.getActiveNode();
	if(activeNode==null) return;
	if(activeNode.getLevel()>1) return;
	var title = $("#hierarchyVisId").val() + " - " + $("#hierarchyDescription").val();
	treeData.title = title;
	if(activeNode==null||activeNode==undefined) return;
    activeNode.setTitle(title);
}
function createTreeElement(direction){
	var timeStamp = Date.now();
	var nodeName = "New "+timeStamp+" - .";
	var tree = $("#tree").fancytree("getTree");
	var activeNode = tree.getActiveNode();
	var path = activeNode.getIndexHier();
	var paths = path.split(".");
	var len = paths.length;
	var temp = treeData;
	var children = {"title": nodeName,"expanded": true,"credit":'1',"augCreditDebit":'0'};

	if(direction=="before"){
		if(activeNode.getParent()){
			activeNode.getParent().addChildren({
		        "title":nodeName,
		        "expanded":true,
		    },activeNode);
			for(var i =1; i< len-1; i++){
				temp =temp.children[paths[i]-1];
			}
			temp.children.splice(paths[len-1]-1,0,children);
			console.log(treeData);
		}
	}else if(direction=="after"){
		if(activeNode.getParent()){
			activeNode.getParent().addChildren({
		        "title":nodeName,
		        "expanded":true,
		    },activeNode.getNextSibling());
				for(var i =1; i< len-1; i++){
					temp =temp.children[paths[i]-1];
				}
				temp.children.splice(paths[len-1],0,children);
				console.log(treeData);
		}
	}else{
		activeNode.folder = true;
		for(var i =1; i< len; i++){
			temp =temp.children[paths[i]-1];
		}
		if(temp.children==undefined)
			temp.children = [];
		temp.children.push(children);
		temp.folder=true;
		console.log(treeData);
		
		activeNode.addChildren({
	        "title":nodeName,
	        "expanded":true,
	    });
		activeNode.setExpanded(true);
	}
	activeNode.getParent().setExpanded(true);

}
function getNodeData(treeData,path){
	var paths = path.split(".");
	var len = paths.length;
	var temp = treeData;
	for(var i =1; i< len; i++){
		temp =temp.children[paths[i]-1];
	}
	return temp;
}
function moveTreeElement(direction){
	var tree = $("#tree").fancytree("getTree");
	var activeNode = tree.getActiveNode();
	var path = activeNode.getIndexHier();
	var paths = path.split(".");
	var len = paths.length;
	if(len<2) return;
	var selectNodeParent = treeData;
	for(var i =1; i< len-1; i++){
		selectNodeParent =selectNodeParent.children[paths[i]-1];
	}
	
	if(direction=="before"){
		if(paths[len-1]<2) return;
		var tmp = selectNodeParent.children[paths[len-1]-2];
		selectNodeParent.children[paths[len-1]-2] = selectNodeParent.children[paths[len-1]-1];
		selectNodeParent.children[paths[len-1]-1] = tmp;
		activeNode.moveTo(activeNode.getPrevSibling(),"before");
	}else{
		if(paths[len-1]>selectNodeParent.children.length-1) return;
		var tmp = selectNodeParent.children[paths[len-1]];
		selectNodeParent.children[paths[len-1]] = selectNodeParent.children[paths[len-1]-1];
		selectNodeParent.children[paths[len-1]-1] = tmp;
		activeNode.moveTo(activeNode.getNextSibling(),"after");
	}
}
function deleteTreeElements(){
	var tree = $("#tree").fancytree("getTree");
	var activeNode = tree.getActiveNode();
	if(activeNode==null||activeNode==undefined) return;
	var path = activeNode.getIndexHier();
	var paths = path.split(".");
	var len = paths.length;
	var parentData = treeData;
	for(var i =1; i< len-1; i++){
		parentData =parentData.children[paths[i]-1];
	}
	parentData.children.splice(paths[len-1]-1,1);
	activeNode.remove();
	console.log(treeData);
}
$("#treeIdentifier").on("keyup",function(){
	nodeChanged();
})
$("#treeDescription").on("keyup",function(){
	nodeChanged();
})

$("input[name='creditDebit'").on("change",function(){
	nodeChanged();
})
function nodeChanged(){
	var id = $("#treeIdentifier").val();
	var desc = $('#treeDescription').val();
	if(desc==""||desc==undefined||desc==null)
		var title = id;
	else 
		var title = id + " - "+desc;
	var tree = $("#tree").fancytree("getTree");
	var activeNode = tree.getActiveNode();
	if(activeNode==null||activeNode==undefined) return;
    activeNode.setTitle(title);
    
	var creditDebit = $("input[name='creditDebit']:checked").val();
	var augCreditDebit = $("input[name='augCreditDebit']:checked").val();
	var path = activeNode.getIndexHier();
	var paths = path.split(".");
	var len = paths.length;
	var tmp = treeData;
	for(var i =1; i< len; i++){
		tmp =tmp.children[paths[i]-1];
	}
	tmp.credit = creditDebit;
	tmp.augCreditDebit = augCreditDebit;
	tmp.title = title;
    console.log(treeData);
}

function save(){
	var visId = $("#hierarchyVisId").val();
	if(visId==""||visId==null||visId==undefined){
		vex.dialog.alert("please input Identifer field");
		return;
	}
	var description = $("#hierarchyDescription").val();
	if(description==""||description==null||description==undefined){
		vex.dialog.alert("please input description field");
		return;
	}
	var dimension = $("#dimensionVisId").val();
	
	$.ajax({
		url:localStorage["cp_base_url"]+"accountHierarchy",
		type:"POST",
		data:{
			operation:operation,
			visId:visId,
			desc:description,
			dimensionId:dimension,
			elements:JSON.stringify(treeData)
		},
		success:function(data){
			calendarData = JSON.parse(data);
			drawHierarchyTable();
		}
	})
}

function drawDimensionTable(){
	if(dimensionTable!=""&&dimensionTable!=undefined){
		dimensionTable.destroy();
	}
	dimensionTable = $("#dimensionTable").DataTable({
		"data":dimensions,
		"scrollY":"60vh",
        "scrollCollapse":true,
        "processing":true,
	    "searching":false,
	    "bFilter" : false,
	    "ordering":false,
	    "bLengthChange": false,
	    "info":     false,
	    "paging":   false,
        "rowCallback": function( row, data ) {
            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
                $(row).addClass('selected');
            }
        }
	});

}

function selectDimension(tr){
	selectedDimensionIndex = tr.find("td:nth-child(1)").html();
	
}
$('#dimensionTable tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
    }
    else {
    	dimensionTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
    selectDimension($(this));
} );
//dimension select 
function choose(){
	if(selectedDimensionIndex == -1) return;
	$("#chooseDimension").modal("hide");
	$("#dimensionVisId").val(selectedDimensionIndex);
	$("#openDialog").modal("show");
}
//row select from account hierarchy table
$('#tblHierarchyDimension tbody').on( 'click', 'tr', function () {
if ( $(this).hasClass('selected') ) {
    // $(this).removeClass('selected');
 }
 else {
	 hierarchyTable.$('tr.selected').removeClass('selected');
     $(this).addClass('selected');
 }
// selectedIndex = this._DT_RowIndex;
 selectRow($(this));
} );
function selectRow(tr){
	selectedHiearchyIndex = tr.find("td:nth-child(1)").html();
}
function openHierarchy(){
	setTimeout(function(){
		dimensionId = selectedDimensionIndex; 
		$.ajax({
			url:localStorage["cp_base_url"]+"accountHierarchy",
			type:"POST",
			data:{
				operation:"getInfo",
				id:selectedHiearchyIndex,
			},
			success:function(data){
				var jdata = JSON.parse(data);
				var visId = jdata.vis_id;
				var model = visId.split("-")[0];
				var description = jdata.desc;
				elementData = jdata.elements;
				$("#dimensionVisId").val(visId);
				$("#dimensionDesc").val(description);
				$("#modelVisId").val(model);
				drawTable();
				$("#openDialog").modal("show");
			}
		});
	},300);

}
drawTree();
getDimensionDatas();
