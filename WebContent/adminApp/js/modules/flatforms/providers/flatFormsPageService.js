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
/**
 * Flat forms page service implementation
 */
(function() {
    'use strict';

    angular
        .module('adminApp.flatFormsPage')
        .service('FlatFormsPageService', FlatFormsPageService);

    /* @ngInject */
    function FlatFormsPageService($rootScope, $http, Upload, Flash) {

		var self = this;
		var areFlatFormsLoaded = false;
		var areFlatFormsLoading = false;
		var url = $BASE_PATH + 'adminPanel';
		var flatForms = new wijmo.collections.CollectionView();

		var actions = [{
			name: "Delete",
			action: "deleteFlatForm",
			disabled: false
		}, {
			name: "Export",
			action: "exported",
			disabled: false
		}, {
			name: "Import",
			action: "imported",
			disabled: false
		}, {
	       		name: "Divider",
	        	action: null,
	        	disabled: true,
	        	isDivider: true
	    	}, {
			name: "Form Deployment (web)",
			action: "deployWeb",
			disabled: false
		}, {
			name: "Form UnDeployment (web)",
			action: "undeployWeb",
			disabled: false
		}, {
	        	name: "Divider",
	        	action: null,
	        	disabled: true,
	        	isDivider: true
	    	}, {
			name: "Form Deployment (mobile)",
			action: "deployMobile",
			disabled: false
		}, {
			name: "Form UnDeployment (mobile)",
			action: "undeployMobile",
			disabled: false
		}];
		self.getActions = getActions;
		self.getFlatForms = getFlatForms;
		self.deleteFlatForm = deleteFlatForm;
		self.importFlatForms = importFlatForms;
		self.getFilePath = getFilePath;
		self.deployFlatForm = deployFlatForm;
		self.undeployFlatForm = undeployFlatForm;
		
		/**
		 * For top buttons
		 */
		function getActions() {
			return actions;
		}

		/**
		 * Return flat forms if cached, otherwise call getFlatFormsFromDatabase() method
		 */
		function getFlatForms(hardReload) {
			if ((!areFlatFormsLoaded && !areFlatFormsLoading) || hardReload) {
				getFlatFormsFromDatabase();
			}
			return flatForms;
		}

		/**
		 * Load all flat forms from database
		 */
		var getFlatFormsFromDatabase = function() {
			areFlatFormsLoaded = false;
			areFlatFormsLoading = true;
			$http.get(url + "/flatForms").success(function(data) {
				areFlatFormsLoaded = true;
				areFlatFormsLoading = false;
				if (data && data.length > 0) {
					flatForms.sourceCollection = data;
				}
			});
		};

		/**
		 * Delete selected flat form
		 */
		function deleteFlatForm(flataFormId, flatFormVisId) {
			$http.delete(url + "/flatForms/" + flataFormId).success(function(data) {
				if (data.success == false) {
					Flash.create('danger', data.message);
				} else {
					Flash.create('success', 'Flat Form "' + flatFormVisId + '" deleted.');
				}
				getFlatFormsFromDatabase();
			});
		}

		function importFlatForms(file) {
			Upload.upload({
				method: "POST",
				url: url + "/flatForms/import",
				file: file

			}).success(function(response) {
				if (response.success) {
					Flash.create('success', 'Import success');
					$rootScope.$broadcast('FlatFormsImport:close', response);
					getFlatFormsFromDatabase();
				} else if (response.error) {
					$rootScope.$broadcast('FlatFormsImport:flatFormImportError', response);
				};
			});
		}

		/**
		 * Get file path for export Flat Forms.
		 */
		function getFilePath() {
			return $BASE_PATH + 'adminPanel/flatForms/export';
		}

		function deployFlatForm(flatFormDeployment) {
			return $http({
					method: "POST",
					url: url + "/flatForms/deploy",
					data: flatFormDeployment
				}).then(onDeployFlatForm)
				.catch(function(message) {});

			function onDeployFlatForm(data, status, headers, config) {
				return data.data;
			}
		}

		/**
		 * Undeploy Flat Form
		 */
		function undeployFlatForm(flatFormUndeployment, flatFormVisId) {
			$http.post(url + "/flatForms/undeploy", flatFormUndeployment).success(function(data) {
				if (data.success) {
					Flash.create('success', 'Flat Form = ' + flatFormVisId + ' has been undeployed.');
					$rootScope.$broadcast('flatFormsUnDeploy:close');
				} else {
					Flash.create('danger', data.message);
				}
			});
		}
	}
})();