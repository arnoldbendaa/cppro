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
        $scope.multiImport = false;
        $scope.cancelMultiImport = cancelMultiImport;
        $scope.startMultiImport = startMultiImport;
        $scope.allCheck = allCheck;
        var allImport = false;
        var maxLeng = 1000;
        $scope.multiImportChk = [];
        var checkedModelMapIds = [];
        var runningTaskId = 0;
        activate();

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
                    	var index = checkedModelMapIds.indexOf(modelId);
                    	if(index>-1)
                        	html='<input type="checkbox" checked ng-click="addImport($event,'+r+')"/>';
                    	else 
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
        function mutiImportSafe(){
        	if(!runningTaskId){
        		var flex = $scope.ctx.flex;
	        	$scope.multiImport = !$scope.multiImport;
	        	if($scope.multiImport)
	                flex.columns.push(new wijmo.grid.Column({ header: '<input type="checkbox"/>',name:'checkbox',align:'center'}));
	        	else 
	        		flex.columns.splice(-1,1);
        	}else{
        		Flash.create('danger', "Now running multiple import.Please try again");
        	}
        }

        //by arnold
        function cancelMultiImport(){
            var flex = $scope.ctx.flex;
        	$scope.multiImport = false;
        	flex.columns.splice(-1,1);
        }
        function addImport(e,index){
            var flex = $scope.ctx.flex;
            var flag = e.target.checked;
            var selectedModelMapId = flex.rows[index]._data.mappedModelId;
        	var indexInArray = checkedModelMapIds.indexOf(selectedModelMapId);
            if(flag){
            	if(indexInArray<0)
            		checkedModelMapIds.push(selectedModelMapId);
            }
            else {
            	checkedModelMapIds.splice(indexInArray,1);
            } 
            console.log(checkedModelMapIds);
        }
//        function startMultiImport(){
//        	//get checked modelMapping ids.
////        	for(var i = 0 ; i < checkedModelMapIds.length;i++){
////                var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, checkedModelMapIds[i], 'mappedModelId');
////                if (mappedModelToImport) {
////                    ModelMappingsService.importSafe(mappedModelToImport);
////                }
////        	}
//        	
//        	var i = 0 ; 
//        	var timer = setInterval(function(){
//                var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, checkedModelMapIds[i], 'mappedModelId');
//                if (mappedModelToImport) {
//                    ModelMappingsService.importSafe(mappedModelToImport);
//                }
//                i++;
//                if(i>=checkedModelMapIds.length)
//                	clearInterval(timer);
//        	},2000);
//        }
        function setTaskId(id){
        	runningTaskId = id;
        }
        function startMultiImport(){
        	//get checked modelMapping ids.
        	if(checkedModelMapIds.length<1){
        		return;
        	}
        	if(runningTaskId==0){
        		
                var mappedModelToImport = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, checkedModelMapIds[0], 'mappedModelId');
                if (mappedModelToImport) {
                    ModelMappingsService.importSafe(mappedModelToImport,setTaskId);
                    var timer = setInterval(function(){
                    	ModelMappingsService.getTaskStatus(runningTaskId,function(response){
                    		if(runningTaskId==0)
                    			clearInterval(timer);
                    		
                    		if(response==5 || response==9){
                    			clearInterval(timer);
                    			checkedModelMapIds.splice(0,1);
                    			setTaskId(0);
                    			startMultiImport();
                    		}else if(response==4){
                    			 Flash.create('danger', "The task "+runningTaskId+" is failed");
                    			 clearInterval(timer);
                    			 setTaskId(0);
                    		}
                    	});
                    },2000);
                }
        	}
        }

        function allCheck(e){
            allImport = e.target.checked;
            var flex = $scope.ctx.flex;
        	checkedModelMapIds = [];
            if(allImport){
            	for(var i=0;i<flex.rows.length; i++){
            		checkedModelMapIds.push(flex.rows[i]._data.mappedModelId);
            	}
            }
            flex.invalidate(true);
        }
    }
})();