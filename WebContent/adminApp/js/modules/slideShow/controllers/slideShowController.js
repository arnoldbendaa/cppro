/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
(function() {
	'use strict';

	angular
		.module('adminApp.slideShow')
		.controller('SlideShowController', SlideShowController);

	/* @ngInject */
	function SlideShowController($rootScope, $scope, $compile, $timeout, $modal, PageService, FilterService,  CoreCommonsService, ContextMenuService) {
		var spreadElement;
		var spread;
		activate();
		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			jQuery.ajax({
				url:localStorage["cp_base_url"] + "adminPanels?method1=getExcelFileName",
				type:"GET",
				success:function(response){
					var data = JSON.parse(response);
					var len = data.length;
					var html = "<option value=\"\">Please Select</option>";
					for(var i=0; i < len; i++){
						var item = data[i].split("---")[0];
						html += "<option value='"+data[i]+"'>"+item+"</option>";
					}
					jQuery("#fileSelectBox").html(html);
						
					
				}
			})
		}
		jQuery("#fileSelectBox").on("change",function(){
			var fileName = $("#fileSelectBox").val();
			if(fileName!=""){
		    	jQuery.ajax({
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
		});

	

	}

})();