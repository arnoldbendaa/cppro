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
        .module('adminApp.dataTypesPage')
        .service('DataTypesPageService', DataTypesPageService);
    
    /* @ngInject */
    function DataTypesPageService($rootScope, $http, CoreCommonsService) {

		var self = this;
		var areDataTypesLoaded = false;
		var areDataTypesLoading = false;
		var areSubTypesLoaded = false;
		var areSubTypesLoading = false;
		var areMeasureClassesLoaded = false;
		var areMeasureClassesLoading = false;
		var url = $BASE_PATH + 'adminPanel/dataTypes';
		var dataTypes = new wijmo.collections.CollectionView();
		// var dataTypes = [];
		var subTypes = [];
		var measureClasses = [];
		var subTypeMeasureNumber = null;
		var subTypeVirtualNumber = null;
		var dataTypeDetails = {};
		var actions = {};
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.isPrintDisabled = true;
		self.isAskDisabled = false;
		self.getDataTypes = getDataTypes;
		self.getDataTypeDetails = getDataTypeDetails;
		self.createEmptyDataType = createEmptyDataType;
		self.getSubTypeNumber = getSubTypeNumber;
		self.getSubTypes = getSubTypes;
		self.getMeasureClasses = getMeasureClasses;
		self.save = save;
		self.deleteDataType = deleteDataType;
		self.getActions = getActions;
		activate();
		
		function activate(){
		    actions = [{
	            name: "Delete",
	            action: "deleteDataType",
	            disabled: true
	        }];
		}
	
		var getDataTypesFromDatabase = function() {
			areDataTypesLoaded = false;
			areDataTypesLoading = true;
			$http.get(url).success(function(data) {
				areDataTypesLoaded = true;
				areDataTypesLoading = false; 
				if (data && data.length >= 0) {
					// angular.copy(data, dataTypes);
					dataTypes.sourceCollection = data;
				}
			});
		};

		/**
         * Return dataTypes if were taken, otherwise call getting Data Types from database.
         */
		function getDataTypes(hardReload) {
			if ((!areDataTypesLoaded && !areDataTypesLoading) || hardReload) {
				getDataTypesFromDatabase();
			}
			return dataTypes;
		};

		/**
         * Get details of one Data Type (get always from database to have the newest version).
         * 
         * @param {Integer} dataTypeId [description]
         */
		function getDataTypeDetails(dataTypeId) {
			dataTypeDetails = {};
			if (dataTypeId != -1) {
				$http.get(url + "/" + dataTypeId).success(function(response) {
					angular.copy(response, dataTypeDetails);
				});
			}
			return dataTypeDetails;
		};

		/**
         * Create new DataType object to return to directive and have ready object for scope and template. Now "id" is always -1.
         * 
         * @param {Integer} id
         */
		function createEmptyDataType(dataTypeId) {
			var dataType = {
				dataTypeId: dataTypeId, // now dataTypeId=-1
				dataTypeVisId: "",
				dataTypeDescription: "",
				subType: 0,
				availableForImport: false,
				availableForExport: false,
				readOnlyFlag: false,
				measureClass: null,
				measureLength: null,
				measureScale: null,
				measureValidation: "",
				formulaExpr: ""
			};
			return dataType;
		};

		/**
         * Get SubTypes from backend service.
         */
		var getSubTypesByAjax = function() {
			areSubTypesLoaded = false;
			areSubTypesLoading = true;
			$http.get(url + "/subTypes").success(function(data) {
				areSubTypesLoaded = true;
				areSubTypesLoading = false;
				if (data && data.length >= 0) {
					subTypes.length = 0;
					angular.forEach(data, function(subType) {
						subTypes.push(subType);
						// get subType number of "Measure" and "Virtual"
						switch (subType.name) {
							case "Measure":
								subTypeMeasureNumber = parseInt(subType.index);
								break;
							case "Virtual":
								subTypeVirtualNumber = parseInt(subType.index);
								break;
							default:
								break;
						}
					});
				}
			});
		};

		/**
         * Get SubType number (index in Array). I.e. for "Measure" it's 4.
         * 
         * @param {String} name
         * @return {Integer}
         */
		function getSubTypeNumber(name) {
			switch (name) {
				case "Measure":
					return subTypeMeasureNumber;
				case "Virtual":
					return subTypeVirtualNumber;
			}
		};

		/**
         * Return subTypes if were taken, otherwise call getting subTypes from database.
         */
		function getSubTypes() {
			if (!areSubTypesLoaded && !areSubTypesLoading) {
				getSubTypesByAjax();
			}
			return subTypes;
		};

		/**
         * Get MeasureClasses from backend service.
         */
		var getMeasureClassesByAjax = function() {
			areMeasureClassesLoaded = false;
			areMeasureClassesLoading = true;
			$http.get(url + "/measureClasses").success(function(data) {
				areMeasureClassesLoaded = true;
				areMeasureClassesLoading = false;
				if (data && data.length >= 0) {
					measureClasses.length = 0;
					angular.forEach(data, function(measureClass) {
						measureClasses.push(measureClass);
					});
				}
			});
		};

		/**
         * Return measureClasses if were taken, otherwise call getting measureClasses from database.
         */
		function getMeasureClasses() {
			if (!areMeasureClassesLoaded && !areMeasureClassesLoading) {
				getMeasureClassesByAjax();
			}
			return measureClasses;
		};

		/**
         * Save data type details. It's "insert" (when dataType.id is -1) or "update" otherwise.
         * 
         * @param {Object} dataType [data type details]
         */
		function save(dataType) {
		    var insert;
		    if (dataType === null) {
				return;
			}
			var method = "";
			if (dataType.dataTypeId != -1) {
				// edit DataType
				method = "PUT";
				insert = false;
			} else {
				// create DataType
				method = "POST";
				insert = true;
			}
			// Send request as PUT (for insert) or POST (for update)
			$http({
				method: method,
				url: url + "/",
				data: dataType
			}).success(function(response) {
				if (response.success) {
					dataTypes.length = 0;
					// refresh listing
					if (insert){
	                    getDataTypesFromDatabase();
	                } else {
					var budgetCycleChange = CoreCommonsService.findElementByKey(dataTypes.sourceCollection, dataType.dataTypeId, 'dataTypeId');
	                budgetCycleChange.dataTypeVisId = dataType.dataTypeVisId;
	                budgetCycleChange.subTypeName = dataType.subTypeName;
	                budgetCycleChange.dataTypeDescription = dataType.dataTypeDescription;
	                budgetCycleChange.measureClassName = dataType.measureClassName;
	                dataTypes.refresh();
	                }
					var dataToReturn = response;
					// to response object add information if it was PUT or POST action
					dataToReturn.method = method;
					$rootScope.$broadcast('DataTypeDetails:close', dataToReturn);
				} else if (response.error) {
					$rootScope.$broadcast('DataTypesPageService:dataTypeDetailsSaveError', response);
				}
			})
			 .error(function(response) {
			 $rootScope.$broadcast('DataTypesPageService:dataTypeDetailsSaveError', response);
			 })
			;
		};

		/**
         * Delete Data Type
         * 
         * @param {Integer} dataTypeId
         */
		function deleteDataType(dataTypeId) {
			$http({
				method: 'DELETE',
				url: url + "/" + dataTypeId
			}).success(function(response) {
				if (response.success) {
					dataTypes.length = 0;
					// refresh listing
					getDataTypesFromDatabase();
					$rootScope.$broadcast('DataTypesPageService:dataTypeDetailsDeleteSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('DataTypesPageService:dataTypeDetailsDeleteError', response);
				}
			})
			// .error(function(response) {
			// $rootScope.$broadcast('DataTypesPageService:dataTypeDetailsDeleteError', response);
			// })
			;
		};

		/**
         * For top buttons
         */
		function getActions() {
			return actions;
		};
	}
})();