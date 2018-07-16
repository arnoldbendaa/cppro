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
		.module('coreApp.dictionary')
		.service('DictionaryService', DictionaryService);

	/* @ngInject */
	function DictionaryService($rootScope, $http, $q) {
		var self = this;
		var url = $BASE_APP_PATH + '/dictionary';

		var dictionaries = {};

		// TODO to think about loaders
		var loader = {
			isDictionariesLoadeding: false,
			isDictionariesLoaded: false
		};

		self.getLoader = getLoader;
		self.getDictionaries = getDictionaries;
		self.saveDictionaries = saveDictionaries;
		self.saveDictionary = saveDictionary;
		self.importDictionariesFromDatabase = importDictionariesFromDatabase;
		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {}

		function getLoader() {
			return loader;
		}

		function getDictionaries(type) {
			if (loader.isDictionariesLoaded) {
				return $q.when(dictionaries[type]);
			} else {
				if (angular.isDefined(type)) {
					return getDictionariesFromDatabase(type);
				} else {
					return getAllDictionariesFromDatabase();
				}
			}
		}

		function manageDictionaries(data, type) {
			var i;
			if (type) {
				if (!dictionaries[type]) {
					dictionaries[type] = [];
				}
				dictionaries[type].length = 0;
				for (i = 0; i < data.length; i++) {
					if (type == data[i].type) {
						dictionaries[type].push(data[i]);
					}
				}
			} else {
				for (i = 0; i < data.length; i++) {
					var typeDB = data[i].type;

					if (!dictionaries[typeDB]) {
						dictionaries[typeDB] = [];
					}
					dictionaries[typeDB].push(data[i]);
				}
			}
		}

		function importDictionariesFromDatabase(type) {
            
		    return $http({
                    method: "GET",
                    url: url + "/import/type/" + type
                })
                .then(onGetDictionaries)
                .catch(onErrorHandler);

            function onGetDictionaries(data, status, headers, config) {
                loader.isDictionariesLoaded = true;
                manageDictionaries(data.data, type);
                return dictionaries[type];
            }

            function onErrorHandler(message) {}
        }
		
		function getDictionariesFromDatabase(type) {
			return $http({
					method: "GET",
					url: url + "/type/" + type
				})
				.then(onGetDictionaries)
				.catch(onErrorHandler);

			function onGetDictionaries(data, status, headers, config) {
				//loader.isDictionariesLoaded = true;
				manageDictionaries(data.data, type);
				return dictionaries[type];
			}

			function onErrorHandler(message) {}
		}

		function getAllDictionariesFromDatabase() {
			return $http({
					method: "GET",
					url: url
				})
				.then(onGetDictionaries)
				.catch(onErrorHandler);

			function onGetDictionaries(data, status, headers, config) {
				//loader.isDictionariesLoaded = true;
				manageDictionaries(data.data);
				return dictionaries;
			}

			function onErrorHandler(message) {}
		}

		function saveDictionaries(dictionaries, type) {
			//loader.isDictionariesLoaded = false;
			return $http({
					method: "POST",
					url: url + "/save/type/" + type,
					data: dictionaries
				})
				.then(onSaveDictionaries)
				.catch(onErrorHandler);

			function onSaveDictionaries(data, status, headers, config) {
				//loader.isDictionariesLoaded = true;
				manageDictionaries(data.data, type);
				return data.data;
			}

			function onErrorHandler(message) {}
		}

		function saveDictionary(dictionary, type) {
			//loader.isDictionariesLoaded = false;
			return $http({
					method: "POST",
					url: url + "/update/type/" + type,
					data: dictionary
				})
				.then(onSaveDictionaries)
				.catch(onErrorHandler);

			function onSaveDictionaries(data, status, headers, config) {
				//loader.isDictionariesLoaded = true;
				//manageDictionaries(data.data, type);
				//return data.data;
			}

			function onErrorHandler(message) {}
		}



	}
})();