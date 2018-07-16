var jsondatas;
var ind=0;
function changeFile(){
	var fileName = $("#activeProfieSelectBox").val();
	if(fileName!=""){
    	$.ajax({
        	url:localStorage["cp_base_url"]+"slideShow",
        	type:"POST",
        	data:{
        		fileName:fileName
        	},
        	success:function(data){
        		var bigHtml = '<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:1300px;height:500px;overflow:hidden;"><div data-u="loading" style="position: absolute; top: 0px; left: 0px;">'+
	            '<div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>'+
	            '<div style="position:absolute;display:block;background:url(\'images/loading.gif\') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>'+
	        '</div>'+
	        '<div data-u="slides" id="slides" style="cursor:default;position:relative;top:0px;left:0px;width:1300px;height:500px;overflow:hidden;">'+
	        '</div>'+
	        '<span data-u="arrowleft" class="jssora22l" style="top:0px;left:8px;width:40px;height:58px;" data-autocenter="2"></span>'+
	        '<span data-u="arrowright" class="jssora22r" style="top:0px;right:8px;width:40px;height:58px;" data-autocenter="2"></span></div>';
        		$("#main-container").html(bigHtml);
        		jsondatas = JSON.parse(data);
        		var length = jsondatas.length;
        		var html = "";
        		$("#slides").html(html);
        		jssor_1_slider_init();
        		for(var i = 0; i<length;i++){
        			html+="<div><img alt=\"Embedded Image\" class='slideImg' data-u='image' src=\""+jsondatas[i]+"\"/></div>";
        		}
        		$("#slides").html(html);
        		jssor_1_slider_init();
        	}
        })
	}
}

