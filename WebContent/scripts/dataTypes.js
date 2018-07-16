	var self = this;
	var datas = [];var selected = [];
	var table;
	var addOrEdit = "";
	function save(){
		var identifier = $("#dataTypeVisId").val();
		var desc = $("#dataTypeDes").val();
		var subType = $("#subTypes").val();
		var importData = $("#importSetting").prop("checked")==true?"1":"0";
		var exportData = $("#exportSetting").prop("checked")==true?"1":"0";
		var readOnly = $("#readOnly").prop("checked")==true?"1":"0";
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
		}else if(addOrEdit=="edit"){
			var id = table.row(".selected").id();
			var index = id.replace("row_","");
			var selectedData = datas[index];
			$.ajax({
				url:"/cppro/DataType",
				type:"POST",
				data:{
					type_id:selectedData[0],
					identifier:identifier,
					desc:desc,
					subType:subType,
					importData:importData,
					exportData:exportData,
					readOnly:readOnly,
					operation:"edit"
				},
				success:function(data){
					$("#openDialog").modal("hide");
					drawTable();
				}
			})
		}else if(addOrEdit=="delete"){
			var id = table.row(".selected").id();
			var index = id.replace("row_","");
			var selectedData = datas[index];
			$.ajax({
				url:"/cppro/DataType",
				type:"POST",
				data:{
					type_id:selectedData[0],
					operation:"delete"
				},
				success:function(data){
					$("#confirmModal").modal("hide");
					drawTable();
				}
			})
		}
	}
	$(document).ready(function(){
		drawTable();
	});
	function drawTable(){
		$.ajax({
			url:"/cppro/getDataTypes",
			type:"POST",
			success:function(data){
				datas = JSON.parse(data);
				var tableData = getChartDatas(datas);
				if(table!=""&&table!=undefined){
					table.destroy();
				}
				table = $('#example').DataTable( {
					data:tableData,
					"processing":true,
				    "searching":false,
				    "bFilter" : false,
				    "ordering":false,
				    "bLengthChange": false,
				    "info":     false,
				    "paging":   false,
				    "dom": '<"top"ip><"clear">',
			        "rowCallback": function( row, data ) {
			            if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
			                $(row).addClass('selected');
			            }
			        }
				} );
			    $('#example tbody').on( 'click', 'tr', function () {
			        if ( $(this).hasClass('selected') ) {
			           // $(this).removeClass('selected');
			        }
			        else {
			            table.$('tr.selected').removeClass('selected');
			            $(this).addClass('selected');
			        }
			    } );
			}
		})
	}
	function getChartDatas(jsonDatas){
		var len = jsonDatas.length;
		var result=[]; var temp={};
		for(var i=0; i < len; i++){
			temp = {};
			var jsonData = jsonDatas[i];
			temp["0"] = (jsonData[0]);//id
			temp["1"] = (jsonData[1]);//vis id in db
			switch(jsonData[6]){//sub type
				case 0:
	        		temp["2"] = ("Financial Value");
	        		break;
	        	case 1:
	        		temp["2"] = ("Budget Transfer Temporary");
	        		break;
	        	case 2:
	        		temp["2"] = ("Budget Transfer Permanent");
	        		break;
	        	case 3:
	        		temp["2"] = ("virtual");
	        		break;
	        	case 4:
	        		temp["2"] = ("Measure");
	        		break;
	        	default:
	        		temp["2"] = ("Financial Value");
	        		break;
        	}
			temp["3"] = "";//measure class
			temp["4"] = (jsonData[2]);//description
			temp["5"]= ('&nbsp;&nbsp;<input type="button" class="btn btn-warning editButton" onclick="edit();" value="Open" data-id="'+jsonData[0]+'"/>&nbsp;&nbsp;'+
					'<input type="button" class="btn btn-danger deleteButton" value="Delete" class="btn btn-info btn-lg" data-toggle="modal" data-target="#confirmModal" data-id="'+jsonData[0]+'"/>');
			temp["DT_RowId"] = "row_"+i;
			result.push(temp);
		}
		return result;
	}
	function edit(){
		addOrEdit = "edit";
		setTimeout(function(){
			var id = table.row(".selected").id();
			var index = id.replace("row_","");
			var selectedData = datas[index];
			console.log(selectedData);
			$("#dataTypeVisId").val(selectedData[1]);//selected datas;
			$("#dataTypeDes").val(selectedData[2]);//description
			$("#subTypes").val(selectedData[6]);//sub type;
			if(selectedData[4]==1)//import check
				$("#importSetting").prop("checked",true);
			else 
				$("#importSetting").prop("checked",false);
			if(selectedData[5]==1)//export check
				$("#exportSetting").prop("checked",true);
			else 
				$("#exportSetting").prop("checked",false);
			if(selectedData[3]==1)//ready only
				$("#readOnly").prop("checked",true);
			else 
				$("#readOnly").prop("checked",false);
			
			$("#openDialog").modal("show");
		},300);
	}
	function create(){
		addOrEdit = "add";
		$("#dataTypeVisId").val("");//selected datas;
		$("#dataTypeDes").val("");//description
		$("#subTypes").val("");//sub type;
		$("#importSetting").prop("checked",false);
		$("#exportSetting").prop("checked",false);
		$("#readOnly").prop("checked",false);
		$("#openDialog").modal("show");
	}
	
	function deleteDataType(){
		addOrEdit = "delete";
		save();
	}