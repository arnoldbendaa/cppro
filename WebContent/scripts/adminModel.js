	var self = this;
	var datas = [];var selected = [];
	var modelTable;
	var addOrEdit = "";
	var modelData = [
		[
			"1",
			"1/1",
			"1 - GROUP REPORTS",
			"<input type='checkbox'/>",
			"<button class='btn-warning' onclick='editDimension();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteDimension();'>delete</button>"
		]
		]
function create(){
	addOrEdit = "add";
	$("#modelVisId").val("");//model id;
	$("#modelDesc").val("");//name
	$("#accountDimension").val("");//account dimension;
	$("#calendarList").val("");
	$("#businessList").val("");
	$("#hierarchy").val("");
	$("#chkLock").prop("checked",false);
	$("#budgetTransferEntry").prop("checked",false);
	
	$("#importSetting").prop("checked",false);
	$("#exportSetting").prop("checked",false);
	$("#readOnly").prop("checked",false);
	$("#openDialog").modal("show");
}
function save(){
	var visId = $("#modelVisId").val();
	var des = $("#modelDesc").val();
	var dimentsion = $("#accountDimension").val();
	var calendarList = $("#calendarList").val();
	var businessList = $("#businessList").val();
	var hierarchy = $("#hierarchy").val();
	var chkLock = $("#chkLock").prop("checked")==true?"1":"0";
	var budgetTransferEntry = $("#budgetTransferEntry").prop("checked")==true?"1":"0";
	//var 
	if(addOrEdit=="add"){
		$.ajax({
			url:"/cppro/DataType",
			type:"POST",
			data:{
				identifier:identifier,
				desc:desc,
				subType:subType,
				importData:importData,
				exportData:exportData,
				readOnly:readOnly,
				operation:"add",
			},
			success:function(data){
				$("#openDialog").modal("hide");
				drawTable();
			}
		})
	}
}
function getModelDatas(){
	$.ajax({
		url:localStorage["cp_base_url"]+"Model",
		type:"POST",
		data:{
			operation:"read",
		},
		success:function(data){
			var jData = JSON.parse(data);
			modelData = jData;
			drawModelTable();
		}
	})
}

function drawModelTable(){
	if(modelTable!=""&&modelTable!=undefined){
		modelTable.destroy();
	}
	modelTable = $("#example").DataTable({
		data:modelData,
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
getModelDatas();