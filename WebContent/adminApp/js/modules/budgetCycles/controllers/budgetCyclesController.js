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
        .module('adminApp.budgetCycle')
        .controller('BudgetCyclesController', BudgetCyclesController);

    /* @ngInject */
    function BudgetCyclesController($rootScope,$window, $scope, $compile, $modal, Flash, PageService, FilterService, CoreCommonsService, BudgetCycleService, ContextMenuService, DictionaryService) {

        var budgetCycles = {};
        $scope.selectedBudgetCycleId = -1;
        $scope.ctx = {};
        // parameters to save cookie for flex grid
        $scope.cookieName = "adminPanel_budgetCycles";
        $scope.resizedColumn = resizedColumn;
        $scope.sortedColumn = sortedColumn;
        $scope.selectionChangedHandler = selectionChangedHandler;
        $scope.open = open;
        $scope.deleteBudget = deleteBudget;
        $scope.manageStatusName = manageStatusName;
        $scope.editPeriod = editPeriod;
        $scope.editCategory = editCategory;
        $scope.changePeriod = changePeriod;
        $scope.onPeriodChange = onPeriodChange;
        $scope.add = add;
        $scope.edit = edit;
        $scope.save = save;
        $scope.btnNextText = "Next";
        var step = 0;
        var bEdit = false;
        var tempArrays = null;
        
        var allImport = false;
        $scope.btnText = "Edit";
        var checkedBudgetCycleIds = [];
        $scope.allCheck = allCheck;
        $scope.addImport = addImport;
        $scope.bShow = false;
        $scope.onCategoryChange = onCategoryChange;
        activate();

        function activate() {
            PageService.setCurrentPageService(BudgetCycleService);
            budgetCycles = BudgetCycleService.getBudgetCycles();
            $scope.ctx = {
                flex: null,
                filter: '',
                data: budgetCycles
            };
            tempArrays = $scope.ctx.data.sourceCollection;
            $scope.globalSettings = CoreCommonsService.globalSettings;
            ContextMenuService.initialize($('.grid'));
            // try to resize and sort colums based on the cookie
            $scope.currentCookie = CoreCommonsService.getCookie($scope.cookieName);
            $scope.$parent.onFilterWordChange("");
        }

        function resizedColumn(sender, args) {
            CoreCommonsService.resizedColumn(args, $scope.cookieName);
        }

        function sortedColumn(sender, args) {
            CoreCommonsService.sortedColumn(args, $scope.cookieName, $scope.ctx);
        }
        function edit(){
        	bEdit = !bEdit;
    		var flex = $scope.ctx.flex;
        	if(bEdit){
        		tempArrays = $scope.ctx.data.sourceCollection;
        		$scope.btnText = "Cancel";
                flex.columns.push(new wijmo.grid.Column({ header: '<input type="checkbox" />',name:'checkbox',align:'center'}));
        	}
        	else {
        		$scope.btnText = "Edit";
        		flex.columns.splice(-1,1);
        		checkedBudgetCycleIds = [];
        		$scope.bShow = checkedBudgetCycleIds.length>0;
        		step = 0;
        		$scope.btnNextText = "Next";
        		$scope.ctx.data.sourceCollection = tempArrays;
        		var flex = $scope.ctx.flex;
        		flex.invalidate(true);

        	}
        }

        /**
         * Handles when row with budget cycle is selected. Updates selectedBudgetCycleId.
         * If user clicks in the same element, controller deselects row.
         */
        function selectionChangedHandler() {
            var flex = $scope.ctx.flex;
            var current = flex.collectionView ? flex.collectionView.currentItem : null;
            $scope.current = current;
            if (current !== null) {
                $scope.selectedBudgetCycleId = current.budgetCycleId;
            } else {
                $scope.selectedBudgetCycleId = -1;
            }
            manageActions();
        }

        /**
         * Function is invoked after click in open button (on the list of budget cycles
         * or stored in submenu)
         */
        function open(bOpen) {
            openModal($scope.selectedBudgetCycleId,bOpen);
        }

        /**
         * Delete selected budget cycle (if it status is completed)
         * Function is invoked after click in delete button (on the list of budget cycles
         * or stored in submenu)
         */
        function deleteBudget() {
            if ($scope.selectedBudgetCycleId == -1) {
                return;
            }
            var budgetCycleToDelete = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, $scope.selectedBudgetCycleId, 'budgetCycleId');
            if (budgetCycleToDelete.status != 2) {
                var messageToReturn = 'You can\'t delete budget cycle (ID = ' + budgetCycleToDelete.budgetCycleId + '). Budget cycle is not completed.';
                Flash.create('danger', messageToReturn);
            } else {
                swal({
                    title: "Are you sure",
                    text: "to delete budget cycle (ID = \"" + budgetCycleToDelete.budgetCycleId + "\") ?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#d9534f"
                }, function(isConfirm) {
                    if (isConfirm) {
                        BudgetCycleService.deleteBudget(budgetCycleToDelete.budgetCycleId, budgetCycleToDelete.model.modelId);
                        $scope.selectedBudgetCycleId = -1;
                    }
                });
            }
        }

        /**
         * Method disables or enables buttons in submenu. When none of budget cycle is selected,
         * buttons are disabled
         */
        var manageActions = function() {
            var actions = BudgetCycleService.getActions();

            BudgetCycleService.isOpenDisabled = $scope.selectedBudgetCycleId === -1;

            if ($scope.selectedBudgetCycleId !== -1) {
                if ($scope.ctx.data.currentItem.status != 1) {
                    PageService.enableActions(actions);
                } else {
                    PageService.enableAction(actions, "submit");
                    PageService.enableAction(actions, "submitRebuild");
                    PageService.disableAction(actions, "deleteBudget");
                }
            } else {
                PageService.disableActions(actions);
            }
            ContextMenuService.updateActions();
            $rootScope.$broadcast('PageService:changeService', {});
        };

        /**
         * Manage status names (labels) by statusId
         */
        function manageStatusName(statusId) {
            return BudgetCycleService.manageStatusName(statusId);
        }

        /**
         * Opens modal with details of budget cycle (with budgetCycleId)
         */
        var openModal = function(budgetCycleId,bOpen) {
            var modalInstance = $modal.open({
                template: '<budget-cycle-details id="budgetCycleId" open="open" close="close()"></budget-cycle-details>',
                windowClass: 'budget-cycle-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.budgetCycleId = budgetCycleId;
                        $scope.open = bOpen;
                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });
        };

        function add() {
            var modalInstance = $modal.open({
                template: '<dictionary-editor modal="modal" type="type" enable-insert="enableInsert" enable-actions="enableActions" enable-order="enableOrder" enable-description="enableDescription"> </dictionary-editor>',
                windowClass: 'dictionary-editor-modals softpro-modals',
                backdrop: 'static',
                size: 'lg',
                controller: ['$scope', '$modalInstance',
                    function($scope, $modalInstance) {
                        $scope.modal = $modalInstance;
                        $scope.type = "category";
                        ///////////////////// Properties to manage dictionary ///////////
                        $scope.enableInsert = false;
                        $scope.enableActions = false; // enable/disable buttons: 'edit', 'delete' and 'save'
                        $scope.enableOrder = true;
                        $scope.enableDescription = true;
                        $scope.close = function() {
                            $modalInstance.close();
                        };
                    }
                ]
            });

            modalInstance.result.then(function(data) {
                //resetView();
                //manageDictionaryHeaders();
            }, function() {

            });
        }


        /******************************************************** FILTERS ********************************************************/
        var filters = ['byMainWordFilter', 'byModelFilter'];
        var filterFunction = FilterService.buildFilterFunction(filters);
        FilterService.doFilter($scope, filterFunction);

        $rootScope.$watch(function() {
            return $rootScope.filter.byWord;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
            hidePopUp();
        });

        $rootScope.$watch(function() {
            return $rootScope.currentModel;
        }, function() {
            FilterService.doFilter($scope, filterFunction);
            hidePopUp();
        });


        /******************************************************** CUSTOM CELLS ********************************************************/
        function itemFormatter(panel, r, c, cell) {
            if (panel.cellType == wijmo.grid.CellType.Cell) {

                var col = panel.columns[c],
                    html = cell.innerHTML;
                switch (col.name) {
                    case 'status':
                        var status = panel.rows[r].dataItem.status;
                        html = "" + $scope.manageStatusName(status);
                        break;
                    case 'index':
                        html = "" + (r + 1);
                        break;
                    case 'periodFromVisId':
                        var periodFromVisId = panel.rows[r].dataItem.periodFromVisId;
                        html = '<button type="button" class="btn btn-info btn-xs periods" ng-click="editPeriod($event, \'from\'); $event.stopPropagation();">' + periodFromVisId + '</button>';
                        break;
                    case 'periodToVisId':
                        var periodToVisId = panel.rows[r].dataItem.periodToVisId;
                        html = '<button type="button" class="btn btn-info btn-xs periods" ng-click="editPeriod($event, \'to\'); $event.stopPropagation();">' + periodToVisId + '</button>';
                        break;
                    case 'category':
                    	var category = panel.rows[r].dataItem['category'];
                        if (category === 'M') {
                            html = '<div class="bg-danger" ng-click="editCategory($event);">M</button>';
                        } else if (category === 'F') {
                            html = '<div class="bg-success" ng-click="editCategory($event);">F</button>';
                        } else if (category === 'B') {
                            html = '<div class="bg-info" ng-click="editCategory($event);">B</button>';
                        }
                        break;
                    case 'buttons':
                        status = panel.rows[r].dataItem.status;
                        html = '<button type="button" class="btn btn-warning btn-xs" ng-click="open(false); $event.stopPropagation();">Open</button>';
                        html += ' <button type="button" class="btn btn-danger btn-xs ' + ((status == 2) ? "" : "disabled") + '" ng-click="deleteBudget(); $event.stopPropagation();">Delete</button>';
                        break;
                    case 'checkbox':
                    	var flex = $scope.ctx.flex;
                    	var budgetcycleId = flex.rows[r]._data.budgetCycleId;
                    	var index = checkedBudgetCycleIds.indexOf(budgetcycleId);
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

        /******************************************************** POP UP - UPDATE PERIOD ********************************************************/
        var popup = angular.element(".popover");
        var popupCloseBtn = angular.element(".popover .close");
        var popupCategory = angular.element(".popoverCategory");
        var popupCategoryCloseBtn = angular.element(".popoverCategory .close");
        
        popupCloseBtn.click(function() {
            hidePopUp();
        });
        popupCategoryCloseBtn.click(function() {
            hidePopUpCategory();
        });

        hidePopUp();
        hidePopUpCategory();

        function hidePopUp() {
            TweenLite.set(popup, {
                css: {
                    opacity: 0,
                    display: "none"
                }
            });
        }
        function hidePopUpCategory() {
            TweenLite.set(popupCategory, {
                css: {
                    opacity: 0,
                    display: "none"
                }
            });
        }

        function showPopUp() {
            TweenLite.to(popup, 0.3, {
                css: {
                    opacity: 100
                }
            });
        }
        function showPopUpCategory() {
            TweenLite.to(popupCategory, 0.3, {
                css: {
                    opacity: 100
                }
            });
        }

        function setPositionPopUpCategory(position) {
            TweenLite.set(popupCategory, {
                css: {
                    top: position.top - 75,
                    left: position.left - 65,
                    display: "block"
                }
            });
        }
        function setPositionPopUp(position) {
            TweenLite.set(popup, {
                css: {
                    top: position.top - 75,
                    left: position.left - 65,
                    display: "block"
                }
            });
        }

        function editPeriod($event, type) {
            var flex = $scope.ctx.flex;
            $scope.type = type;
            $scope.current = flex.collectionView ? flex.collectionView.currentItem : null;

            var offset = $($event.currentTarget).offset();
            setPositionPopUp(offset);
            showPopUp();
        }
        function editCategory($event){
            var flex = $scope.ctx.flex;
            $scope.current = flex.collectionView ? flex.collectionView.currentItem : null;
            
            $scope.selectCategoryValue = $scope.current.category=='M'?0: $scope.current.category=='F'?1:2;
            var offset = $($event.currentTarget).offset();
            setPositionPopUpCategory(offset);
            showPopUpCategory();
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

        $scope.$on("SubMenuController:open", function() {
            $scope.open();
        });

        $scope.$on("SubMenuController:create", function() {
            openModal(-1,1);
        });

        $scope.$on("SubMenuController:deleteBudget", function() {
            $scope.deleteBudget();
        });

        $scope.$on("SubMenuController:refresh", function() {
            BudgetCycleService.getBudgetCycles(true);
        });

        /******************************************************** UPDATE PERIODS ********************************************************/
        var editedBudgetCycleId = -1;
        var isPeriodToEdited = false;

        /**
         * Method is invoked when user clicks element which is passed to calendar-period
         * as translude template. We must know which budget cycle is edited
         * and which period was clicked
         */
        function changePeriod(budgetCycleId, type) {
            editedBudgetCycleId = budgetCycleId;
            isPeriodToEdited = (type == 'to') ? true : false;
        }

        /**
         * Method is invoked when user selects new period of budget cycle
         */
        function onPeriodChange(periodId, periodVisId) {
			swal({
				title: "Are you sure",
				text: "Are you sure you want to accept the changes for the selected budget cycles?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
		            var budgetCycleIds = [];
		            if(step==0)
		            	budgetCycleIds[0] = $scope.selectedBudgetCycleId;
		            else{
		            	for(var i=0; i < $scope.ctx.data.sourceCollection.length;i++){
		            		budgetCycleIds[i] = $scope.ctx.data.sourceCollection[i].budgetCycleId;
		            	}
		            }

		            updatePeriodsForBudgetCycle(budgetCycleIds,periodId, periodVisId);
				}else {
                    $scope.type = null;
                    var cv = $scope.ctx.flex.collectionView;
                    if (cv) {
                        cv.refresh();
                    }
                    Flash.create('success', 'You canceled');
                    hidePopUp();

				}
					
			});
        }

        /**
         * Method updates periods in edited budget cycle.
         */
        var updatePeriodsForBudgetCycle = function(budgetCycleIds,periodId, periodVisId) {
            if (editedBudgetCycleId == -1||budgetCycleIds.length<1) {
                return;
            }
            var budgetCycle = {};
            var isPeriodsChanged = false;
                var budgetCycleDB = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, budgetCycleIds[0], 'budgetCycleId');
                budgetCycle = angular.copy(budgetCycleDB);

                if (isPeriodToEdited) {
                    budgetCycle.periodToId = periodId;
                    budgetCycle.periodToVisId = periodVisId;
                    isPeriodsChanged = (budgetCycle.periodToId !== budgetCycleDB.periodToId);
                } else {
                    budgetCycle.periodFromId = periodId;
                    budgetCycle.periodFromVisId = periodVisId;
                    isPeriodsChanged = (budgetCycle.periodFromId !== budgetCycleDB.periodFromId);
                }

                if (isPeriodsChanged) {
                    BudgetCycleService.updatePeriodsForBudgetCycle(budgetCycle).success(function(response) {
                        if (response.success) {
                            //budgetCycleDB = angular.copy(budgetCycle);
                            if (isPeriodToEdited) {
                                budgetCycleDB.periodToId = periodId;
                                budgetCycleDB.periodToVisId = periodVisId;
                            } else {
                                budgetCycleDB.periodFromId = periodId;
                                budgetCycleDB.periodFromVisId = periodVisId;
                            }
                            $scope.type = null;
                            var cv = $scope.ctx.flex.collectionView;
                            if (cv) {
                                cv.refresh();
                                //$scope.$apply('ctx.flex.collectionView');
                            }
                            showSuccessMessage(budgetCycle);
                            budgetCycleIds.splice(0,1);
                            updatePeriodsForBudgetCycle(budgetCycleIds,periodId, periodVisId);
                        }
                        if (response.error) {
                            showErrorMessage(budgetCycle, response);
                        }
                        hidePopUp();
                    });
                }
        };
        var updateCategoryForBudgetCycle = function(budgetCycleIds) {
        	console.log($scope.selectCategoryValue);
        	console.log($scope.current);
        	  if (budgetCycleIds.length<1) {
                  return;
              }
        	var budgetCycle = {};
        	var budgetCycleId = budgetCycleIds[0];

            var budgetCycleDB = CoreCommonsService.findElementByKey($scope.ctx.data.sourceCollection, budgetCycleId, 'budgetCycleId');
            budgetCycle = angular.copy(budgetCycleDB);

        	var Category = $scope.selectCategoryValue==0?'M':$scope.selectCategoryValue==1?'F':'B';
        	budgetCycle.category = Category;
            BudgetCycleService.updatePeriodsForBudgetCycle(budgetCycle).success(function(response) {
                if (response.success) {
                    budgetCycleDB.category = Category;
                    $scope.type = null;
                    var cv = $scope.ctx.flex.collectionView;
                    if (cv) {
                        cv.refresh();
                    }
                    showSuccessMessage(budgetCycle);
                    budgetCycleIds.splice(0,1);
                    updateCategoryForBudgetCycle(budgetCycleIds);

                }
                if (response.error) {
                    showErrorMessage(budgetCycle, response);
                }
                hidePopUpCategory();
            });
        		
        }
        /**
         * Shows success pop up if update has been successful
         */
        var showSuccessMessage = function(budgetCycle) {
            var messageToReturn = 'Budget Cycle [ ' + budgetCycle.budgetCycleVisId + ' ] has been updated.';
            Flash.create('success', messageToReturn);
        };

        /**
         * Shows error pop up if update has not been completed
         */
        var showErrorMessage = function(budgetCycle, response) {
            var messageToReturn = response.message;
            Flash.create('danger', messageToReturn);
        };
        
        
        
        function allCheck(e){
            allImport = e.target.checked;
            var flex = $scope.ctx.flex;
        	checkedBudgetCycleIds = [];
            if(allImport){
            	for(var i=0;i<flex.rows.length; i++){
            		checkedBudgetCycleIds.push(flex.rows[i]._data.budgetCycleId);
            	}
            }
            $scope.bShow = checkedBudgetCycleIds.length>0;
            flex.invalidate(true);
        }
        function addImport(e,index){
            var flex = $scope.ctx.flex;
            var flag = e.target.checked;
            var selectedModelMapId = flex.rows[index]._data.budgetCycleId;
        	var indexInArray = checkedBudgetCycleIds.indexOf(selectedModelMapId);
            if(flag){
            	if(indexInArray<0)
            		checkedBudgetCycleIds.push(selectedModelMapId);
            }
            else {
            	checkedBudgetCycleIds.splice(indexInArray,1);
            } 
            $scope.bShow = checkedBudgetCycleIds.length>0;
            console.log(checkedBudgetCycleIds);
        }
        function save(){
        	console.log("save");
        	if(step==0){
        		$scope.btnNextText = "Back";
        		tempArrays = $scope.ctx.data.sourceCollection;;
        		var temp = [];
        		for(var i = 0 ; i < $scope.ctx.data.sourceCollection.length; i++){
        			var tempBudgetCycleId = $scope.ctx.data.sourceCollection[i].budgetCycleId;
        			if(checkedBudgetCycleIds.indexOf(tempBudgetCycleId)>-1)
        				temp.push($scope.ctx.data.sourceCollection[i]);
        		}
        		$scope.ctx.data.sourceCollection = temp;
        		var flex = $scope.ctx.flex;
        		flex.invalidate(true);
        		step++;
        	}else{
        		edit();
        	}
        }
        function onCategoryChange(){
        	
			swal({
				title: "Are you sure",
				text: "Are you sure you want to accept the changes for the selected budget cycles?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d9534f"
			}, function(isConfirm) {
				if (isConfirm) {
					 var budgetCycleIds = [];
					 if(step==0)
		            	budgetCycleIds[0] = $scope.selectedBudgetCycleId;
		             else{
		            	for(var i=0; i < $scope.ctx.data.sourceCollection.length;i++){
		            		budgetCycleIds[i] = $scope.ctx.data.sourceCollection[i].budgetCycleId;
		            	}
		             }
		            updateCategoryForBudgetCycle(budgetCycleIds);
				}else {
                    $scope.type = null;
                    var cv = $scope.ctx.flex.collectionView;
                    if (cv) {
                        cv.refresh();
                    }
                    Flash.create('success', 'You canceled');
                    hidePopUpCategory();

				}
					
			});

        	console.log($scope.selectCategoryValue);
        	console.log($scope.current);
        	
        }

    }
})();