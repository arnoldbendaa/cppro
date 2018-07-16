$(document).ready(function(){
	$('#homeTabs').scrollingTabs({
		  scrollToTabEdge: true  
	});
	$("#homeTabs").find("li").first().addClass("active");
});
function getTabContent(modelId){
	$.ajax({
		url:localStorage["cp_base_url"]+"home",
		type:"POST",
		data:{
			modelId:modelId,
			operation:"getTabContent"
		},
		success:function(response){
			console.log(response);
			var lists = JSON.parse(response);
			var manageTableHtml = budgetTableHtml = forecastTableHtml ="";
			for(var i in lists){
				
				if(lists[i].category=="B")
					var html = '<tr><td></td>'+
						  '<td><a href="/cppro/Servlet2?budgetCycleId='+lists[i].budgetCycleId+'&structureElementId='+lists[i].structureElementId+'&addId='+lists[i].structureElementId+
						  '"> '+lists[i].budgetCycle+'</a></td><td></td>'+
						  '<td class="text-center">'+						  
						  	'<span >&nbsp;'+lists[i].childNotStarted+'&nbsp;</span>'+
							  '<span>&nbsp;&nbsp;'+lists[i].childPreparing+'</span>'+
							  '<span>&nbsp;&nbsp;'+lists[i].childSubmited+'&nbsp;</span>'+
							  '<span >&nbsp;&nbsp;'+lists[i].childAgreed+'&nbsp;</span>'+
						  '</td><td></td></tr>';
				else 
					var html= '<tr><td></td><td><a href="/cppro/Servlet2?budgetCycleId='+lists[i].budgetCycleId+'&structureElementId='+lists[i].structureElementId+'&addId='+lists[i].structureElementId+
					'"> '+lists[i].budgetCycle+'</a></td></tr>';
					if(lists[i].category=="M")
						manageTableHtml +=html;
					else if(lists[i].category=="B")
						budgetTableHtml +=html;
					else if(lists[i].category=="F")
						forecastTableHtml +=html;
			}
			$("#manageTable tbody").html(manageTableHtml);
			$("#forecastTable tbody").html(forecastTableHtml);
			$("#BudgetTable tbody").html(budgetTableHtml);
			

		}
	});
}
