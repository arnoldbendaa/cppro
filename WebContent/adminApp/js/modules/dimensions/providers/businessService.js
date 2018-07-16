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
        .module('adminApp.dimension')
        .service('BusinessService', BusinessService);
    
    /* @ngInject */
    function BusinessService($rootScope, $http, CoreCommonsService) {

		var self = this;
		var areBusinessesLoaded = false;
		var areBusinessesLoading = false;
		var url = $BASE_PATH + 'adminPanel/dimensions/businesses';
		var businesses = new wijmo.collections.CollectionView();
		var businessDetails = {};
		var actions = {};
		self.isCreateDisabled = false;
		self.isOpenDisabled = true;
		self.isPrintDisabled = true;
		self.isAskDisabled = false;
		self.clearCache = clearCache;
		self.getBusinesses = getBusinesses;
		self.getBusinessDetails = getBusinessDetails;
		self.createEmptyBusiness = createEmptyBusiness;
		self.save = save;
		self.deleteBusiness = deleteBusiness;
		self.getActions = getActions;
		activate();
		
		function activate(){
		    actions = [{
	            name: "Delete",
	            action: "deleteBusiness",
	            disabled: true
	        }];    
		}
		
		/**
         * Clear list of Business Dimensions.
         */
        function clearCache() {
            areBusinessesLoaded = false;
            areBusinessesLoading = false;
        }

		var getBusinessesFromDatabase = function() {
			areBusinessesLoaded = false;
			areBusinessesLoading = true;
			$http.get(url).success(function(data) {
				areBusinessesLoaded = true;
				areBusinessesLoading = false;
				if (data && data.length >= 0) {
					businesses.sourceCollection = data;
				}
			});
		};

		/**
		 * Return businesses if were taken, otherwise call getting Businesses from database.
		 */
		function getBusinesses(hardReload) {
			if ((!areBusinessesLoaded && !areBusinessesLoading) || hardReload) {
				getBusinessesFromDatabase();
			}
			return businesses;
		}

		/**
		 * Get details of one Business (get always from database to have the newest version).
		 * @param  {Integer} businessId [description]
		 */
		function getBusinessDetails(businessId) {
			businessDetails = {};
			if (businessId != -1) {
				$http.get(url + "/" + businessId).success(function(response) {
					angular.copy(response, businessDetails);
				});
			}
			return businessDetails;
		}

		/**
		 * Create new Dmension Business object to return to directive and have ready object for scope and template. Now "id" is always -1.
		 * @param  {Integer} id
		 */
		function createEmptyBusiness(id) {
			var business = {
				dimensionId: id, // now id=-1
				dimensionVisId: "",
				model: {
					modelId: null,
					modelVisId: "Unassigned",
				},
				dimensionDescription: "",
				type: 2,
				sequence: -1,
				hierarchies: -1,
				externalSystemRefName: "N/A",
				dimensionElements: [],
				readOnly: false,
				inUseLabel: null,
				augentMode: null,
				versionNum: -1,
				submitChangeManagementRequest: false,
			};
			return business;
		}

		/**
		 * Save business details. It's "insert" (when business.id is -1) or "update" otherwise.
		 * @param  {Object} business [business details]
		 */
		function save(business) {
			if (business === null) {
				return;
			}
			var insert;
			var method  = "";
			if (business.dimensionId != -1) {
				// edit Dimension
				method = "PUT";
				insert = false;
			} else {
				// create Dimension
				method = "POST";
				insert = true;
			}
			// Send request as PUT (for insert) or POST (for update)
			$http({
				method: method,
				url: url + "/",
				data: business
			}).success(function(response) {
				if (response.success) {
					businesses.length = 0;
					// refresh listing
					if (insert){
					    getBusinessesFromDatabase();
                    } else {
					var businessChange = CoreCommonsService.findElementByKey(businesses.sourceCollection, business.dimensionId, 'dimensionId');
					businessChange.model.modelVisId = business.model.modelVisId;
					businessChange.dimensionVisId = business.dimensionVisId;
					businessChange.dimensionDescription = business.dimensionDescription;
                    businesses.refresh();
                    }
					var dataToReturn = response;
					// to response object add information if it was PUT or POST action
					dataToReturn.method = method;
					$rootScope.$broadcast('BusinessDetails:close', dataToReturn);
				} else if (response.error) {
					$rootScope.$broadcast('BusinessService:businessDetailsSaveError', response);
				}
			});
		}

		/**
		 * Delete Dimension
		 * @param  {Integer} dimensionId
		 */
		function deleteBusiness(dimensionId) {
			$http({
				method: 'DELETE',
				url: url + "/" + dimensionId
			}).success(function(response) {
				if (response.success) {
					businesses.length = 0;
					// refresh listing
					getBusinessesFromDatabase();
					$rootScope.$broadcast('BusinessService:businessDetailsDeleteSuccess');
				} else if (response.error) {
					$rootScope.$broadcast('BusinessService:businessDetailsDeleteError', response);
				}
			});
		}

		/**
		 * For top buttons
		 */
		function getActions() {
			return actions;
		}
	}
})();