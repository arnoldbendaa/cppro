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
	'use_strict';

	angular
		.module('lookupParametersApp.parameters')
		.service('ParametersService', ParametersService);

	/* @ngInject */
	function ParametersService($rootScope, $http, $q, CoreCommonsService) {
		var self = this;
		var url = $BASE_PATH + 'lookupTable/lookup/parameters';

		var parameters = [];
		var dimensions = [];
		var loader = {
			isParametersLoaded: false,
			isParametersSaving: false,
			isParametersSaved: false
		};

		self.getLoader = getLoader;
		self.getParameters = getParameters;
		self.getDimensions = getDimensions;
		self.saveParameters = saveParameters;
		self.importDimensions = importDimensions;
		self.updateFieldName = updateFieldName;

		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {

		}

		function getLoader() {
			return loader;
		}

		function getCurrency(company) {
			return $http({
					method: "GET",
					url: url + "/currency/" + company
				})
				.then(onGetParameters)
				.catch(onErrorHandler);

			function onGetCurrency(data, status, headers, config) {
				//loader.isParametersLoaded = true;
				currency = data.data;
				return currency;
			}

			function onErrorHandler(message) {}
		}

		function getParameters(company) {
			if (loader.isParametersLoaded) {
				return $q.when(parameters);
			} else {
				if (angular.isDefined(company)) {
					return getParametersFromDatabase(company);
				} else {
					return getAllParametersFromDatabase();
				}
			}
		}

		function getParametersFromDatabase(company) {
			return $http({
					method: "GET",
					url: url + "/company/" + company
				})
				.then(onGetParameters)
				.catch(onErrorHandler);

			function onGetParameters(data, status, headers, config) {
				//loader.isParametersLoaded = true;
				parameters = data.data;
				return parameters;
			}

			function onErrorHandler(message) {}
		}

		function getAllParametersFromDatabase() {
			return $http({
					method: "GET",
					url: url
				})
				.then(onGetParameters)
				.catch(onErrorHandler);

			function onGetParameters(data, status, headers, config) {
				//loader.isParametersLoaded = true;
				parameters = data.data;
				return parameters;
			}

			function onErrorHandler(message) {}
		}

		function saveParameters(parameters) {
			return $http({
					method: "POST",
					url: url + "/save",
					data: parameters
				})
				.then(onSaveParameters)
				.catch(onErrorHandler);

			function onSaveParameters(data, status, headers, config) {
			    manageParameters(parameters);
			    manageParameters(data.data);

				loader.isParametersSaving = false;
				loader.isParametersSaved = true;
				return data.data;
			}

			function onErrorHandler(message) {}
		}

		/**
         * Updates collection of new data parameters. If parameter stored in collection has value which is equals to null,
         * function removes it from collection (like in database)
         */
		function manageParameters(data) {
            var i, parameter;
            for (i = 0; i < data.length; i++) {
            	if (data[i].parameterUUID) {
            	   parameter = CoreCommonsService.findElementByKey(parameters, data[i].parameterUUID, "parameterUUID");
         			if (parameter) {
         			   parameter.fieldValue = data[i].fieldValue;
         			} else {
         				parameters.push(data[i]);
         			}
         		}
         	}
         	parameter = null;
         	for (i = 0; i < parameters.length; i++) {
         	   parameter = parameters[i];
         		if (parameter.fieldValue === null) {
         			var index = parameters.indexOf(parameter);
         			parameters.splice(index, 1);
         			i--;
         		}
         	}
	    }

		function importDimensions(company) {
			return $http({
					method: "GET",
					url: url + "/import/dimensions/company/" + company
				})
				.then(onImportDimensions);

			function onImportDimensions(data, status, headers, config) {
				parameters = data.data;
				return data.data;
			}
		}

		function getDimensions(company) {
			return $http({
					method: "GET",
					url: url + "/dimensions/company/" + company
				})
				.then(onGetDimensions);

			function onGetDimensions(data, status, headers, config) {
				dimensions = data.data;
				return data.data;
			}
		}

		function updateFieldName(changes) {
			var changes = changes
			angular.forEach(changes, function(change) {
				change.oldValue = change.oldValue.replace("fields|", "");
				change.newValue = change.newValue.replace("fields|", "");
			});
			return $http({
					method: "POST",
					url: url + "/update",
					data: changes
				})
				//.then();
				//.catch(onErrorHandler);

			// function onSaveParameters(data, status, headers, config) {
			// 	// manageParameters(parameters);
			// 	// manageParameters(data.data);

			// 	loader.isParametersSaving = false;
			// 	loader.isParametersSaved = true;
			// 	return data.data;
			// }
		}
	}
})();