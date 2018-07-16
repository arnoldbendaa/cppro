	var datas = [];var selected = [];
	var table;
	var addOrEdit = "";
	var selectId;
function drawTable(){
	if(table!=""&&table!=undefined){
		table.destroy();
	}
	table = $("#example").DataTable({
		//data:tblData,
		"ajax":{
			url:localStorage["cp_base_url"]+"SystemPropery",
			type:"POST",
			data:{
				operation:"read",
			}
		},
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
drawTable();
function create(){
	$("#openDialog").modal("show");
}
$('#example tbody').on( 'click', 'tr', function () {
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
function selectRow(tr){
	selectId = tr[0].id;
	selectId = selectId.replace("row_","");
}
function editProperty(){
	setTimeout(function(){
		var tr = $("#row_"+selectId);
		var property = tr.find("td:nth-child(2)").html();
		var value = tr.find("td:nth-child(3)").html();
		var desc = tr.find("td:nth-child(4)").html();
		$("#property").val(property);
		$("#value").val(value);
		$("#description").val(desc);
		$("#openDialog").modal("show");
		console.log(selectId);
	},300);
}
function save(){
	var value = $("#value").val();
	$.ajax({
			url:localStorage["cp_base_url"]+"SystemPropery",
			type:"POST",
			data:{
				operation:"edit",
				value:value,
				id:selectId
			},
			success:function(){
				$("#row_"+selectId).find("td:nth-child(3)").html(value);
				$("#openDialog").modal("hide");
			}
	})
}