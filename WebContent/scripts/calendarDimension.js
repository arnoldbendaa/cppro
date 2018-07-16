var currentYear = new Date().getFullYear()
var years = [{year:currentYear,datas:["Y","Y","N"]}];
var minYear = currentYear;
var maxYear = currentYear;
var elementData = [];var selected = [];
var table,calendarTable
var operation = "";
var calendarData;
var selectedDimensionIndex = -1;
var selectedDimensionTrIndex = -1;
var dimensionId = -1;

function drawCalendarTable(){
	if(calendarTable!=""&&calendarTable!=undefined){
		calendarTable.destroy();
	}
	calendarTable = $("#tblCalendarDimension").DataTable({
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
		url:localStorage["cp_base_url"]+"calendarDimension",
		type:"POST",
		data:{
			operation:"read",
		},
		success:function(data){
			calendarData = JSON.parse(data);
			drawCalendarTable();
		}
	})
}
function create(){
	operation = "add";
	$("#dimensionVisId").val("");
	$("#dimensionDescription").val("");
	years = [{year:currentYear,datas:["Y","Y","N"]}];
	drawTable();
	$("#openDialog").modal("show");
}
getDimensionDatas();
function drawTable(){
	var len = years.length; 
	var html1="",html2="",html3="",html4="";
	html1 = "<th>Level</th>";
	html2 = "<td>Year</td>";
	html3 = "<td>Month</td>";
	html4 = "<td>Opening Balance</td>";
	for(var i = 0 ; i < len; i++){
		console.log(years[i]);
		html1 += "<th>" + years[i]["year"]+"</th>";
		html2 += years[i]["datas"][0]=='Y'?"<td><input id='"+years[i]["year"]+"_"+"year'"+"  type='checkbox' checked/></td>":"<td><input id='"+years[i]["year"]+"_"+"year'"+" type='checkbox'/></td>";
		html3 += years[i]["datas"][1]=='Y'?"<td><input id='"+years[i]["year"]+"_"+"month'"+"  type='checkbox' checked/></td>":"<td><input id='"+years[i]["year"]+"_"+"month'"+" type='checkbox'/></td>";
		html4 += years[i]["datas"][2]=='Y'?"<td><input id='"+years[i]["year"]+"_"+"open'"+"  type='checkbox' checked/></td>":"<td><input id='"+years[i]["year"]+"_"+"open'"+" type='checkbox'/></td>";
	}
	html = "<table class='table table-bordered'><thead><tr>"+html1+"</tr>"+
			"<tbody><tr class='clickable-row'>"+html2+"</tr>"+
			"<tr class='clickable-row'>"+html3+"</tr>"+
			"<tr class='clickable-row'>"+html4+"</tr></tbody></table>";
	$("#calendarTable").html(html);
}
$('#calendarTable').on('click', '.clickable-row', function(event) {
	  $(this).addClass('active').siblings().removeClass('active');
	});
drawTable();
function addYearLeft(){
	minYear--;
	var temp = [{year:minYear,datas:["Y","Y","N"]}];
	years = temp.concat(years);
	drawTable();
}
function deleteYearLeft(){
	if(minYear>=maxYear) return;
	minYear++;
	years.splice(0,1);
	drawTable();
}
function addYearRight(){
	maxYear++;
	var temp = [{year:maxYear,datas:["Y","Y","N"]}];
	years = years.concat(temp);
	drawTable();
}
function deleteYearRight(){
	if(minYear>=maxYear) return;
	maxYear--;
	var len = years.length;
	years.splice(len-1,1);
	drawTable();
}

function save(){
	var visId = $("#dimensionVisId").val();
	var visDesc = $("#dimensionDescription").val();
	var model = $("#modelVisId").val();
	if(visDesc==""||visDesc==null||visDesc==undefined){
		vex.dialog.alert("please input Description field");
		return;
	}
	$.ajax({
		url:localStorage["cp_base_url"]+"calendarDimension",
		type:"POST",
		data:{
			operation:operation,
			dimensionVisId:visId,
			dimensionDesc:visDesc,
			elements:JSON.stringify(years),
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
$('#tblCalendarDimension tbody').on( 'click', 'tr', function () {
    if ( $(this).hasClass('selected') ) {
       // $(this).removeClass('selected');
    }
    else {
    	calendarTable.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    }
    selectDimensionRow($(this));
} );
function selectDimensionRow(tr){
	selectedDimensionTrIndex = tr[0]._DT_RowIndex;
	selectedDimensionIndex = calendarData[selectedDimensionTrIndex][0];
}
function editDimension(){
	setTimeout(function(){
		operation = "editDimension";
		dimensionId = selectedDimensionIndex; 
		$.ajax({
			url:localStorage["cp_base_url"]+"calendarDimension",
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
				years = jdata.elements;
				if(years.length==0)
					years = [{year:currentYear,datas:["N","N","N"]}];
				getMaxAndMin();
				$("#dimensionVisId").val(visId);
				$("#dimensionDescription").val(description);
				$("#modelVisId").val(model);
				drawTable();
				$("#openDialog").modal("show");
			}
		});
	},300);
}
function getMaxAndMin(){
	var len = years.length;
	minYear = years[0]["year"];
	maxYear = years[0]["year"];
	for(var i = 0 ; i < len ; i ++){
		minYear = minYear<years[i]["year"]?minYear:years[i]["year"];
		maxYear = maxYear>years[i]["year"]?maxYear:years[i]["year"];
	}
}
$("#calendarTable").on("change","input[type=checkbox]",function(){
	var id = (this).id;
	var year = id.split("_")[0];
	var row = id.split("_")[1];
	var val = $(this).prop("checked")==true?"Y":"N";
	var len = years.length;
	for(var i=0; i < len; i++){
		if(years[i]["year"]==year){
			if(row=="year")
				years[i]["datas"][0]=val;
			else if(row=="month")
				years[i]["datas"][1]=val;
			else 
				years[i]["datas"][2]=val;
			break;
		}
	}
})
function deleteDimension(){
	vex.dialog.confirm({
		message:"sure delete?",
		callback:function(value){
			if(value){
				operation = "delete";
				$.ajax({
					url:localStorage["cp_base_url"]+"calendarDimension",
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
