	var self = this;
	var datas = [];var selected = [];
	var table;
	var addOrEdit = "";
	
	
	function save(){
		
		if(addOrEdit!="delete"){
			var visId = $("#externalSystemVisId").val();
			var description = $("#externalSystemDescription").val();
			var connectorClass = $("#connectorClass").val();
			var location = $("#location").val();
			var enabled = $("input[name=optradio]:checked").val();
			if(visId==""){
				vex.dialog.alert("Please input visId");
				return;
			}
			if(description ==""){
				vex.dialog.alert("Please input description");
				return;
			}
			if(location==""){
				vex.dialog.alert("Please input location");
				return;
			}
			if(connectorClass==""){
				vex.dialog.alert("Please input connector class");
				return;
			}
		}
		
		if(addOrEdit=="add"){
			$.ajax({
				url:"/cppro/ExternalSystem",
				type:"POST",
				data:{
					visId:visId,
					description:description,
					systemType:"1",
					connectorClass:connectorClass,
					location:location,
					enabled:enabled,
					operation:"add"
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
				url:"/cppro/ExternalSystem",
				type:"POST",
				data:{
					visId:visId,
					description:description,
					systemType:"1",
					connectorClass:connectorClass,
					location:location,
					enabled:enabled,
					externalSystemId:selectedData[0],
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
				url:"/cppro/ExternalSystem",
				type:"POST",
				data:{
					id:selectedData[0],
					operation:"delete"
				},
				success:function(data){
					drawTable();
				}
			})
		}
	}
	$(document).ready(function(){
		drawTable();
	});
	function drawTable(){
		waitingDialog.show('Loading');
		$.ajax({
			url:"/cppro/ExternalSystem",
			type:"POST",
			data:{
				operation:"read"
			},
			success:function(data){
				waitingDialog.hide();
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
			temp["2"] = (jsonData[2]);
			temp["3"] = jsonData[3];//measure class
			temp["4"] = (jsonData[4]);//description
			temp["5"] = jsonData["5"];
			temp["6"]= ('&nbsp;&nbsp;<input type="button" class="btn btn-warning editButton" onclick="edit();" value="Open" data-id="'+jsonData[0]+'"/>&nbsp;&nbsp;'+
					'<input type="button" class="btn btn-danger deleteButton" value="Delete" class="btn btn-info btn-lg" onclick="deleteConfirm()" data-id="'+jsonData[0]+'"/>');
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
			$("#externalSystemVisId").val(selectedData[1]);
			$("#externalSystemDescription").val(selectedData[3]);
			$("#connectorClass").val(selectedData[6]);
			$("#location").val(selectedData[4]);
			$("input[name=optradio]:checked").val();
			if(selectedData[5]=='1'){
				$("#radioEnabled").prop("checked",true);
				$("#radioDisabled").prop("checked",false);
			}
			else{
				$("#radioDisabled").prop("checked",true);
				$("#radioEnabled").prop("checked",false);
			}
			$("#openDialog").modal("show");
		},300);
	}
	function create(){
		addOrEdit = "add";
		$("#externalSystemVisId").val("");
		$("#externalSystemDescription").val("");
		$("#connectorClass").val("");//sub type;
		$("#location").val("");
		$("#openDialog").modal("show");
	}
	
	function deleteData(value){
		if(value){
			addOrEdit = "delete";
			save();
		}
	}
	function deleteConfirm(){
		vex.dialog.confirm({
			message:"Sure delete?",
			callback:deleteData
		})
	}
