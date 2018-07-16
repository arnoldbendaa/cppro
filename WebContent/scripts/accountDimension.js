
var elementData = [];var selected = [];
var selectedIndex = -1;
var selectedTrIndex = -1;
var table,dimensionTable
var operation = "";
var maxNum = 0;
var dimensionData;
var selectedDimensionIndex = -1;
var selectedDimensionTrIndex = -1;
var dimensionId = -1;
drawTable();

function drawTable(){
	if(table!=""&&table!=undefined){
		table.destroy();
	}
	table = $("#elementTable").DataTable({
		data:elementData,
		column:[
			{data:"0"},
			{data:"1"},
			{data:"2"},
			{data:"3"},
			{data:"4"},
			{data:"5"},
			{data:"6"},
			{data:"7"},
			{data:"8"},
		],
		"scrollY":"80px",
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
$('#elementTable tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
       // $(this).removeClass('selected');
    }
    else {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
   // selectedIndex = this._DT_RowIndex;
    selectRow($(this));
} );
$('#tblAccountDimension tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
       // $(this).removeClass('selected');
    }
    else {
    	dimensionTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
   // selectedIndex = this._DT_RowIndex;
    selectDimensionRow($(this));
} );

function insertElement(){	
	var elementVisId = $('#elementVisId').val();
	var elementDescription = $('#elementDescription').val();
	var notPlannable = $("#notPlannable").prop("checked");
	var disabled =  $("#disabled").prop("checked");
	
	var creditDebit = $('input[name=creditDebit]:checked').val();
	var nullElement = $("#nullElement").prop("checked");
	elementData.push({
		0:maxNum,
		1:elementVisId,
		2:elementDescription,
		3:notPlannable==true?"<input type='checkbox' checked />":"<input type='checkbox' />",
		4:disabled==true?"<input type='checkbox' checked />":"<input type='checkbox' />",
		5:creditDebit==1?"Credit":"Debit",
		6:nullElement==true?"<input type='checkbox' checked />":"<input type='checkbox' />",
		7:"No Override",
		8:'<input type="button" class="btn-danger" value="delete" onclick="deleteRow('+maxNum+');"/>',
		9:notPlannable,
		10:disabled,
		11:creditDebit,
		12:nullElement,
		"DT_RowId":"row_"+maxNum
	})
	maxNum++;

	drawTable();
}
function selectRow(tr){
	selectedIndex = tr.find("td:nth-child(1)").html();
	for(var i = 0 ; i < elementData.length;i++){
		if(elementData[i][0]==selectedIndex){
			selectedTrIndex = i;
			break;
		}
	}
	var identifier = tr.find("td:nth-child(2)").html();
	var description = tr.find("td:nth-child(3)").html();
	var notPlannable = $(tr.find("td:nth-child(4)").html()).prop("checked");
	var disabled = $(tr.find("td:nth-child(5)").html()).prop("checked");
	var CrDr = tr.find("td:nth-child(6)").html()=="Credit"?'1':'2';
	var nullElement = $(tr.find("td:nth-child(7)").html()).prop("checked");
	$("#elementVisId").val(identifier);
	$("#elementDescription").val(description);
	$("#notPlannable").prop("checked",notPlannable);
	$("#disabled").prop("checked",disabled);
    var $radios = $('input:radio[name=creditDebit]');
    if(CrDr=="1")
    	$radios.filter('[value="0"]').prop('checked', false);
    else
    	$radios.filter('[value="1"]').prop('checked', false);
    $radios.filter('[value="'+CrDr+'"]').prop('checked', true);
	$("#nullElement").prop("checked",nullElement);
}
$("#elementVisId").on("keyup",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var changedIdentifier = $(this).val();
	elementData[selectedTrIndex]["1"] = (changedIdentifier);
	tr.find("td:nth-child(2)").html(changedIdentifier);
})
$("#elementDescription").on("keyup",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var changeDescription = $(this).val();
	elementData[selectedTrIndex]["2"] = (changeDescription);
	tr.find("td:nth-child(3)").html(changeDescription);
});
$("#notPlannable").on("change",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var changeNotPlannable = $(this).prop('checked');
	elementData[selectedTrIndex]["9"] = changeNotPlannable;
	if(changeNotPlannable==true)
		tr.find("td:nth-child(4)").html("<input type='checkbox' checked />");
	else
		tr.find("td:nth-child(4)").html("<input type='checkbox'/>");
})
$("#disabled").on("change",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var disabled = $(this).prop('checked');
	elementData[selectedTrIndex]["10"] = disabled;
	if(disabled==true)
		tr.find("td:nth-child(5)").html("<input type='checkbox' checked />");
	else
		tr.find("td:nth-child(5)").html("<input type='checkbox'/>");
})
$("#nullElement").on("change",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var nullElement = $(this).prop('checked');
	elementData[selectedTrIndex]["12"] = nullElement;
	if(nullElement==true)
		tr.find("td:nth-child(7)").html("<input type='checkbox' checked />");
	else
		tr.find("td:nth-child(7)").html("<input type='checkbox'/>");
})
$('input[name=creditDebit]').on("change",function(){
	if(selectedIndex<0) return;
	var trId = "row_"+(selectedIndex);
	var tr = $("#"+trId);
	var CreditDebit = $('input[name=creditDebit]:checked').val();
	elementData[selectedTrIndex]["11"] = CreditDebit;
	if(CreditDebit=="1")
		tr.find("td:nth-child(6)").html("Credit");
	else
		tr.find("td:nth-child(6)").html("Debit");
})
function create(){
	operation = "add";
	dimensionId = -1;
	$("#dimensionVisId").val("");
	$("#dimensionDesc").val("");
	$("#modelVisId").val("Unassigned");
	$('#elementVisId').val("");
	$('#elementDescription').val("");
	$("#notPlannable").prop("checked",false);
	$("#disabled").prop("checked",false);
	$("#nullElement").prop("checked",false);
	$("#openDialog").modal("show");
	elementData = [];
	drawTable();
}
function save(){
	var dimensionVisId = $('#dimensionVisId').val();
	var deimensionDesc = $("#dimensionDesc").val();
	var model = $("#modelVisId").val();
	if(deimensionDesc==""||deimensionDesc==null||deimensionDesc==undefined){
		vex.dialog.alert("please input Description field");
		return;
	}
	if(elementData.length>0){
		for(var i = 0 ; i < elementData.length; i++){
			if(elementData[i][2]==""||elementData[i][2]==null||elementData[i][2]==undefined){
				vex.dialog.alert("Please input "+(i*1+1)+"st row's description field");
				return;
			}
		}
	}
	$.ajax({
		url:localStorage["cp_base_url"]+"accountDimension",
		type:"POST",
		data:{
			operation:operation,
			dimensionVisId:dimensionVisId,
			dimensionDesc:deimensionDesc,
			elements:JSON.stringify(elementData),
			model:model,
			id:selectedDimensionIndex
		},
		success:function(data){
			if(data=="Success")
				$("#openDialog").modal("hide");
			getDimensionDatas();
		}
	})
}
function deleteRow(rowNumber){
	var len = elementData.length;
	for(var i = 0; i < len; i++){
		if(elementData[i][0]==rowNumber){
			elementData.splice(i,1);
			drawTable();
			return;
		}
	}
}
function MessageBox(msg){
	$("#msgModal").find("h3").html(msg);
	$("#msgModal").modal("show");
}
function drawDimensionTable(){
	if(dimensionTable!=""&&dimensionTable!=undefined){
		dimensionTable.destroy();
	}
	dimensionTable = $("#tblAccountDimension").DataTable({
		data:dimensionData,
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
	operation = "read";
	$.ajax({
		url:localStorage["cp_base_url"]+"accountDimension",
		type:"POST",
		data:{
			operation:operation,
		},
		success:function(data){
			dimensionData = JSON.parse(data);
			drawDimensionTable();
		}
	})
}
function selectDimensionRow(tr){
	selectedDimensionTrIndex = tr[0]._DT_RowIndex;
	selectedDimensionIndex = dimensionData[selectedDimensionTrIndex][0];
}
function deleteDimension(){
	vex.dialog.confirm({
		message:"sure delete?",
		callback:function(value){
			if(value){
				operation = "delete";
				$.ajax({
					url:localStorage["cp_base_url"]+"accountDimension",
					type:"POST",
					data:{
						operation:operation,
						id:selectedDimensionIndex,
					},
					success:function(data){
						getDimensionDatas()
					}
				})
			}
		}
	})
}
function editDimension(){
	setTimeout(function(){
		operation = "editDimension";
		dimensionId = selectedDimensionIndex; 
		$.ajax({
			url:localStorage["cp_base_url"]+"accountDimension",
			type:"POST",
			data:{
				operation:"getInfo",
				id:selectedDimensionIndex,
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
getDimensionDatas();
