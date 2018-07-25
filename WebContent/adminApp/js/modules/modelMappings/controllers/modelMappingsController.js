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
        .module('adminApp.modelMappings')
        .controller('ModelMappingsController', ModelMappingsController);

    /* @ngInject */
    function ModelMappingsController($rootScope, $scope,Flash, $compile, $modal, PageService, FilterService, ModelMappingsService, CoreCommonsService, ContextMenuService) {

        var mappedModels = ModelMappingsService.getMappedModels();
        $scope.selectedMappedModelId = -1;
        $scope.selectedMappedModelVisId = "";
        $scope.selectedMappedModelGlobal = null;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_modelMapping";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteModelMapping = deleteModelMapping;
        $scope.addImport = addImport;
        $scope.multiImport = localStorage['multiImport']==undefined?false:JSON.parse(localStorage['multiImport']);
        $scope.multiImport = !$scope.multiImport ;
        $scope.cancelMultiImport = cancelMultiImport;
        $scope.startMultiImport = startMultiImport;
        $scope.clearMultiImport = clearMultiImport;
        $scope.allCheck = allCheck;
        $scope.exportReport = exportReport;
        $scope.viewDetail = viewDetail;
        
        var allImport = false;
        var maxLeng = 1000;
        $scope.multiImportChk = [];
        var checkedModelMapIdsInFunc = localStorage['checkedModelMapIdsInFunc']==undefined?[]:JSON.parse(localStorage['checkedModelMapIdsInFunc']);
        var checkedModelMapIds = localStorage['checkedModelMapIds']==undefined?[]:JSON.parse(localStorage['checkedModelMapIds']);//for display check box
        activate();
        //mutiImportSafe();
        
        function activate() {
            PageService.setCurrentPageService(ModelMappingsService);
            $scope.globalSettings = CoreCommonsService.globalSettings;
            
            $scope.ctx = {
                flex: null,
                filter: '',
                data: mappedModels
            };
            ContextMenuService.initialize($('.grid'));
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }

        /**
         * Set selected table row as current.
         * If previous selected row id is different from current, change selectedFinanceCubeId.
         * Otherwise reset selectedFinanceCubeId ('cause FinanceCube was deselected).
         */
        function selectionChangedHandler() {
        	if(!$scope.multiImport){
                var flex = $scope.ctx.flex;
                var current = flex.collectionView ? flex.collectionView.currentItem : null;
                if (current !== null) {
                    $scope.selectedMappedModelId = current.mappedModelId;
                    $scope.selectedMappedModelVisId = current.mappedModelVisId;
                    $scope.selectedMappedModelGlobal = current.global;
                } else {
                    $scope.selectedMappedModelId = -1;
                    $scope.selectedMappedModelVisId = "";
                    $scope.selectedMappedModelGlobal = "";
                }
                manageActions();
        	}
        }

        /**
         * Call "open modal" for update after click on button on the listing.
         */
        function open() {
            openModal($scope.selectedMappedModelId, $scope.selectedMappedModelGlobal);
        }

        /**
         * [create description]
         */
        var create = function() {
            swal({
                title: "Choose Model Type...",
                text: "Choose the model mapping type you want to create.",
                showCancelButton: true,
                confirmButtonColor: "#A7D5EA",
                confirmButtonText: "Global Model Mapping",
                cancelButtonColor: "#A7D5EA",
                cancelButtonText: "Model Mapping"
            }, function(isConfirm) {
                var global = null;
                if (isConfirm) {
                    global = true;
                } else {
                    global = false;
                }
                openModal(-1, global);
            });
        };

        function importSafe() {
            if ($scope.selectedMappedModelId === -1) {
                return;
            }
            var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, $scope.selectedMappedModelId, 'mappedModelId');
            if (mappedModelToImport) {
                ModelMappingsService.importSafe(mappedModelToImport,null);
            }
        }

        function importNotSafe() {
            if ($scope.selectedMappedModelId === -1) {
                return;
            }
            var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, $scope.selectedMappedModelId, 'mappedModelId');
            if (mappedModelToImport) {
                ModelMappingsService.importNotSafe(mappedModelToImport);
            }
        }

        /**
         * [delete description]
         */
        function deleteModelMapping() {
            if ($scope.selectedMappedModelId === -1) {
                return;
            }
            var mappedModelToDelete = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, $scope.selectedMappedModelId, 'mappedModelId');
            if (mappedModelToDelete) {
                swal({
                    title: "Are you sure",
                    text: "you want to delete Mapped Model \"" + $scope.selectedMappedModelVisId + "\"?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#d9534f"
                }, function(isConfirm) {
                    if (isConfirm) {
                        ModelMappingsService.deleteModelMapping(mappedModelToDelete);
                    }
                });
            }
        }

        /**
         * Enable or disable top buttons due to selected row (enable if any is selected).
         */
        var manageActions = function() {
            var actions = ModelMappingsService.getActions();

            ModelMappingsService.isOpenDisabled = $scope.selectedMappedModelId === -1;

            if ($scope.selectedMappedModelId !== -1) {
                PageService.enableActions(actions);
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Open modal (for insert or update). Insert when mappedModelId equals -1, update otherwise.
         * @param  {Integer} mappedModelId
         */
        var openModal = function(mappedModelId, global) {
            var modalInstance = $modal.open({
                template: '<model-mappings-details model="mappedModelId" global="global"></model-mappings-details>',
                windowClass: 'model-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.mappedModelId = mappedModelId;
                        $scope.global = global;
                        $scope.$on('ModelMappingsDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };
        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byModelFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        $rootScope.$watch(function() {
            return $rootScope.currentModel;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
        });

        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'index':
                        html = "" + (r + 1);
                        break;
                    case 'buttons':
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open()">Open</button>' +
                            ' <button type="button" class="btn btn-danger btn-xs" ng-click="deleteModelMapping()">Delete</button>';
                        break;
                    case 'checkbox':
                    	var flex = $scope.ctx.flex;
                    	var modelId = flex.rows[r]._data.mappedModelId;
                    	var index = checkModelMapContains(modelId);
                    	if(index > -1){
                    		if(checkedModelMapIds[index].status=='failed')
                    			html='<input type="checkbox" checked ng-click="addImport($event,'+r+')"/><a ng-click="viewDetail(\''+index+'\')" style="cursor:pointer" title="Click to view Details">'+checkedModelMapIds[index].status+'</a>';
                    		else 
                    			html='<input type="checkbox" checked ng-click="addImport($event,'+r+')"/><span>'+checkedModelMapIds[index].status+'</span>';
                    	}else
                    		html='<input type="checkbox" ng-click="addImport($event,'+r+')"/>';                    	
                    	break;
                }

                if (html != cell.innerHTML) {
                    cell.innerHTML = html;
                    $compile(cell)($scope);
                }
            }else if(panel.cellType == wijmo.grid.CellType.ColumnHeader){
                var col = panel.columns[c],
                	html = cell.innerHTML;
                if(col.name=="checkbox"){
                	if(allImport)
                    	cell.innerHTML="<input type='checkbox' checked ng-click='allCheck($event)'/>";
                	else 
                		cell.innerHTML="<input type='checkbox' ng-click='allCheck($event)'/>"
                	 $compile(cell)($scope);
                }

            }
        }
        /******************************************************** WATCHERS ********************************************************/
        $scope.$watch('ctx.flex', function() {
            var flex = $scope.ctx.flex;
            if (flex) {
                flex.isReadOnly = true;
                flex.selectionMode = "Row";
                flex.headersVisibility = "Column";
                flex.itemFormatter = itemFormatter;
                CoreCommonsService.initializeResizeFlexGrid($scope.ctx, $scope.currentCookie);
            }
        });
        $scope.$watch('mappedModels.sourceCollection',function(){
        	console.log("mappedModels changed");
        	setTimeout(function(){
        		mutiImportSafe();
        	},1000);
        	
        });
        /**
         * Call "open" for update after click on button on the submenu.
         */
        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on('SubMenuController:create', function() {
            create();
        });

        $scope.$on("SubMenuController:importSafe", function() {
            importSafe();
        });

        $scope.$on("SubMenuController:importNotSafe", function() {
            importNotSafe();
        });

        $scope.$on('SubMenuController:deleteModelMapping', function() {
            $scope.deleteModelMapping();
        });

        $scope.$on("SubMenuController:refresh", function() {
            ModelMappingsService.getMappedModels(true);
        });
        $scope.$on("SubMenuController:mutiImportSafe",function(){
        	mutiImportSafe();
        });
        function checkModelMapContains(modelMapId){
        	for(var i = 0 ; i < checkedModelMapIds.length;i++){
        		if(checkedModelMapIds[i].id==modelMapId)
        			return i;
        	}
        	return -1;
        }
        function mutiImportSafe(){
    		var flex = $scope.ctx.flex;
        	$scope.multiImport = !$scope.multiImport;
        	localStorage['multiImport'] = JSON.stringify($scope.multiImport);
        	if($scope.multiImport)
                flex.columns.push(new wijmo.grid.Column({ header: '<input type="checkbox"/>',name:'checkbox',align:'center'}));
        	else 
        		flex.columns.splice(-1,1);
        }

        //by arnold
        function cancelMultiImport(){
            var flex = $scope.ctx.flex;
        	$scope.multiImport = false;
        	flex.columns.splice(-1,1);
        	localStorage['multiImport'] = JSON.stringify($scope.multiImport);
        }
        function addImport(e,index){
            var flex = $scope.ctx.flex;
            var flag = e.target.checked;
            var selectedModelMapId = flex.rows[index]._data.mappedModelId;
        	var indexInArray = checkedModelMapIdsInFunc.indexOf(selectedModelMapId);
            if(flag){
            	if(indexInArray<0){
            		checkedModelMapIdsInFunc.push(selectedModelMapId);
            		var temp = {id:selectedModelMapId,status:'queued','mappedModelVisId':flex.rows[index]._data.mappedModelVisId,taskId:0,time:''};
            		checkedModelMapIds.push(temp)
            	}
            }
            else {
            	checkedModelMapIdsInFunc.splice(indexInArray,1);
            	var modelMapIndex = checkModelMapContains(selectedModelMapId);
            	checkedModelMapIds.splice(modelMapIndex,1);
            } 
            refreshAndSave();
        }
        function setTaskId(id){
        	var modelMapIndex = checkModelMapContains(checkedModelMapIdsInFunc[0]);
        	checkedModelMapIds[modelMapIndex].taskId=id;
        	var timer = setInterval(function(){
        		ModelMappingsService.getTaskStatus(id,function(response){        		
	        		if(response==5 || response==9){
	        			checkedModelMapIds[modelMapIndex].status='completed';
	        			checkedModelMapIdsInFunc.splice(0,1);
	        			if(checkedModelMapIdsInFunc.length>0)
	        				startMultiImport();
	        			refreshAndSave();
	        			ModelMappingsService.getTaskTime(id,function(response){
	        				checkedModelMapIds[modelMapIndex].time=response;
		        			if(checkedModelMapIdsInFunc.length<1)
		        				$scope.exportReport();
	        			});
	        			clearInterval(timer);

	        		}else if(response==4 || response==7){
	        			 Flash.create('danger', "The task "+id+" is failed");
	        			checkedModelMapIds[modelMapIndex].status='failed';
	        			checkedModelMapIdsInFunc.splice(0,1);
	        			refreshAndSave();
	        			if(checkedModelMapIdsInFunc.length<1)
	        				$scope.exportReport();
	                	ModelMappingsService.deleteFailedTask(id,function(response){
	                		checkedModelMapIds[modelMapIndex].task = response;
	                		startMultiImport();
	                		clearInterval(timer);
	        			});
	        			 

	        		}
	        	});
        	},2000);
        }
        function refreshAndSave(){
        	var flex = $scope.ctx.flex;
			flex.invalidate(true);
        	localStorage["checkedModelMapIdsInFunc"] = JSON.stringify(checkedModelMapIdsInFunc);
        	localStorage["checkedModelMapIds"] = JSON.stringify(checkedModelMapIds);
        }
        function startMultiImport(){
        	if(checkedModelMapIdsInFunc.length<1){
        		swal("Please select at least 1 model mapping");
        		return;
        	}
        	//get checked modelMapping ids.
			var modelMapIndex = checkModelMapContains(checkedModelMapIdsInFunc[0]);
			checkedModelMapIds[modelMapIndex].status='running';
			refreshAndSave();
        	if(checkedModelMapIdsInFunc.length<1){
        		return;
        	}

            var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, checkedModelMapIdsInFunc[0], 'mappedModelId');
            if (mappedModelToImport) {
                ModelMappingsService.importSafe(mappedModelToImport,setTaskId);
            }
        }

        function allCheck(e){
            allImport = e.target.checked;
            var flex = $scope.ctx.flex;
        	checkedModelMapIdsInFunc = [];
        	checkedModelMapIds = [];
            if(allImport){
            	for(var i=0;i<flex.rows.length; i++){
            		checkedModelMapIdsInFunc.push(flex.rows[i]._data.mappedModelId);
            		var temp = {id:flex.rows[i]._data.mappedModelId,status:'queued','mappedModelVisId':flex.rows[index]._data.mappedModelVisId,taskId:0,time:''};
            		checkedModelMapIds.push(temp)

            	}
            }
            refreshAndSave();
            }
        function clearMultiImport(){
        	checkedModelMapIdsInFunc = [];
        	checkedModelMapIds = [];
        	refreshAndSave();
        }
        function exportReport(){
			var spread = $scope.spread = new GC.Spread.Sheets.Workbook(document.getElementById('spreadsheet'));
        	var sheet = spread.getActiveSheet();                              
        	sheet.setValue(0,0,'Task Id');
        	sheet.setValue(0,1,'Model Mapping');
        	sheet.setValue(0,2,'Time Taken');
        	sheet.setValue(0,3,'Status');
            sheet.setColumnWidth(1, 120.0,GC.Spread.Sheets.SheetArea.viewport);
            sheet.setColumnWidth(2, 120.0,GC.Spread.Sheets.SheetArea.viewport);
            sheet.setColumnWidth(3, 120.0,GC.Spread.Sheets.SheetArea.viewport);
            for(var i=0; i <checkedModelMapIds.length;i++) {
            	sheet.setValue(i+1,0,checkedModelMapIds[i].taskId);
            	sheet.setValue(i+1,1,checkedModelMapIds[i].mappedModelVisId);
            	sheet.setValue(i+1,2,checkedModelMapIds[i].time);
            	sheet.setValue(i+1,3,checkedModelMapIds[i].status);
            	if(checkedModelMapIds[i].status=='failed'){
            		document.getElementById("failedContent").innerHTML = checkedModelMapIds[i].task.taskEvents;
            		var text = document.getElementById("failedContent").textContent;
            		sheet.setValue(i+1,4,text);
            	}
            		
            }
        	var excelIO = new GC.Spread.Excel.IO();
            var json = JSON.stringify(spread.toJSON());
            var fileName="report.xlsx";
            excelIO.save(json, function (blob) {
                saveAs(blob, fileName);
            }, function (e) {
                console.log(e);
            });
        }
        function viewDetail(taskId){
        	console.log(taskId);
        	var info = {'taskId':taskId,'userName':'fc1'};
        	openModal(taskId, 'fc1');
        }
        var openModal = function(index, userName) {
            var modalInstance = $modal.open({
                template: '<failed-task-details id="taskId" task="task"></failed-task-details>',
                windowClass: 'task-viewer-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.taskId = checkedModelMapIds[index].taskId;
                        $scope.task = checkedModelMapIds[index].task;
                        $scope.$on('TaskViewerDetails:close', function(event, args) {
                            $modalInstance.close();
                        });
                    }
                ]
            });
        };

    }
})();