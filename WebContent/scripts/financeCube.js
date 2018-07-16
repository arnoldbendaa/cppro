	var datas = [];var selected = [];
	var table;
	var addOrEdit = "";
	function drawTable(){
		if(table!=""&&table!=undefined){
			table.destroy();
		}
		table = $("#example").DataTable({
			//data:tblData,
			"ajax":{
				url:localStorage["cp_base_url"]+"FinaceCube",
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
