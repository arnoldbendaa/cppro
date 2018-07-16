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
		.module('lookupCurrencyApp.currency')
		.service('CurrencyService', CurrencyService);

	/* @ngInject */
	function CurrencyService($rootScope, $http, $q, Upload, CoreCommonsService) {
		var self = this;
		var url = $BASE_PATH + 'lookupTable/lookup/currency';

		var currencies = [];
		var loader = {
			isCurrenciesLoaded: false,
			isCurrenciesSaving: false,
			isCurrenciesSaved: false
		};
		self.getLoader = getLoader;
		self.getCurrencies = getCurrencies;
		self.saveCurrencies = saveCurrencies;
		self.upload = upload;
		self.updateCurrencyName = updateCurrencyName;
		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {

		}

		function getLoader() {
			return loader;
		}

		function getCurrencies(company, year) {
			if (loader.isCurrenciesLoaded) {
				return $q.when(currencies);
			} else {
				if (angular.isDefined(company) && angular.isDefined(year)) {
					return getCurrenciesFromDatabase(company, year);
				} else {
					return getAllCurrenciesFromDatabase();
				}
			}
		}

		function getCurrenciesFromDatabase(company, year) {
			return $http({
					method: "GET",
					url: url + "/company/" + company + "/year/" + year
				})
				.then(onGetCurrencies)
				.catch(onErrorHandler);

			function onGetCurrencies(data, status, headers, config) {
				loader.isCurrenciesLoaded = true;
				currencies = data.data;
				return currencies;
			}

			function onErrorHandler(message) {}
		}

		function getAllCurrenciesFromDatabase() {
			return $http({
					method: "GET",
					url: url
				})
				.then(onGetCurrencies)
				.catch(onErrorHandler);

			function onGetCurrencies(data, status, headers, config) {
				loader.isCurrenciesLoaded = true;
				currencies = data.data;
				return currencies;
			}

			function onErrorHandler(message) {}
		}

		function saveCurrencies(currencies) {
			return $http({
					method: "POST",
					url: url + "/save",
					data: currencies
				})
				.then(onSaveCurrencies)
				.catch(onErrorHandler);

			function onSaveCurrencies(data, status, headers, config) {
				manageCurrencies(currencies);
				manageCurrencies(data.data);

				loader.isCurrenciesSaving = false;
				loader.isCurrenciesSaved = true;
				return data.data;
			}

			function onErrorHandler(message) {}
		}

		/**
		 * Updates collection of new data currencies. If currency stored in collection has value which is equals to null,
		 * function removes it from collection (like in database)
		 */
		function manageCurrencies(data) {
			var i, currency;
			for (i = 0; i < data.length; i++) {
				if (data[i].currencyUUID) {
					currency = CoreCommonsService.findElementByKey(currencies, data[i].currencyUUID, "currencyUUID");
					if (currency) {
						currency.fieldValue = data[i].fieldValue;
					} else {
						currencies.push(data[i]);
					}
				}
			}
			currency = null;
			for (i = 0; i < currencies.length; i++) {
				currency = currencies[i];
				if (currency.fieldValue === null) {
					var index = currencies.indexOf(currency);
					currencies.splice(index, 1);
					i--;
				}
			}
		}

		function upload(file) {
			return Upload.upload({
					url: url + "/upload",
					method: 'POST',
					file: file
				})
				.then(onImportCurrencies)
				.catch(onErrorHandler);

			function onImportCurrencies(data, status, headers, config) {
				manageCurrencies(data.data.currencies);
				return data.data;
			}

			function onErrorHandler(message) {}
		};

		function updateCurrencyName(changes) {
			var changes = changes
			angular.forEach(changes, function(change) {
				change.oldValue = change.oldValue.replace("currency|", "");
				change.newValue = change.newValue.replace("currency|", "");
			});
			return $http({
				method: "POST",
				url: url + "/update",
				data: changes
			})
		};
	}
})();