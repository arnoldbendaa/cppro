	var datas = [];var selected = [];
	var table;
	var addOrEdit = "";
function drawTable(){
	if(table!=""&&table!=undefined){
		table.destroy();
	}
	table = $("#example").DataTable({
		"ajax":{
			url:localStorage["cp_base_url"]+"BudgetCycle",
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
function selectRow(tr){
	selectId = tr[0].id;
	selectId = selectId.replace("row_","");
}
$('#example tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
    }
    else {
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
    selectRow($(this));
} );

function edit(){
	setTimeout(function(){
		var tr = $("#row_"+selectId);
		$.ajax({
			url:localStorage["cp_base_url"]+"BudgetCycle",
			type:"POST",
			data:{
				operation:"getInfo",
			}
		})
	},300);
}
