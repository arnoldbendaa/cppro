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
		.module('flatFormTemplateApp.templates')
		.service('TemplatesService', TemplatesService);

	function TemplatesService($rootScope, $http, $timeout, Upload, FlatFormTemplateCommonsService) {
		var self = this;
		var url = $BASE_PATH + 'flatFormTemplate/templates/';
		var templates = [];
		var template = {};
		self.templateTypes = [];
		self.getTemplatesUrl = getTemplatesUrl;
		self.getTemplatesForGenerateUrl = getTemplatesForGenerateUrl;
		self.getTemplateTypes = getTemplateTypes;
		self.fetchTemplate = fetchTemplate;
		self.getTemplate = getTemplate;
		self.upload = upload;
		self.importToDirectory = importToDirectory;
		self.exportToXls = exportToXls;
		self.exportMany = exportMany;
		self.generateEmptyTemplate = generateEmptyTemplate;
		self.getEmptyEditTreeElement = getEmptyEditTreeElement;
		self.insertTreeElement = insertTreeElement;
		self.renameTreeElement = renameTreeElement;
		self.deleteTreeElement = deleteTreeElement;
		self.save = save;
		self.updatePostion = updatePostion;
		self.mapType = mapType;
		self.updateWorksheetPropertyName = updateWorksheetPropertyName;
		self.deleteWorksheetProperties = deleteWorksheetProperties;
		self.insertWorksheetProperties = insertWorksheetProperties;

		/************************************************** IMPLEMENTATION *************************************************************************/

		function getTemplatesUrl() {
			return url;
		}

		function getTemplatesForGenerateUrl() {
			return url + "generate";
		}

		function getTemplateTypes() {
			self.templateTypes = FlatFormTemplateCommonsService.getTemplateTypes();
			return self.templateTypes;
		}

		function fetchTemplate(uuid) {
			return $http.get(url + uuid)
				.then(onFetchTemplate)
				.catch(function(message) {});

			function onFetchTemplate(data, status, headers, config) {
				template = data.data;
				return template;
			}
		}

		function getTemplate() {
			return template;
		}

		function upload(file) {
			return Upload.upload({
				url: url + "upload",
				method: 'POST',
				file: file
			});
		};

		function importToDirectory(files, directory) {
			return Upload.upload({
				url: url + "importToDirectory",
				method: 'POST',
				fileFormDataName: "files",
				file: files,
				data: directory
			});
		};

		function exportToXls(templateUUID) {
			$rootScope.$broadcast('veil:show');
			var exportUrl = url + 'exportToXls?templateUUID=' + templateUUID;
			// start downloading the excel file
			$.fileDownload(exportUrl)
				.done(function() {
					$rootScope.$broadcast('veil:hide');
				})
				.fail(function() {
					swal({
						title: "File export failed.",
						type: "warning",
					});
					$rootScope.$broadcast('veil:hide');
				});
		};

		function exportMany(templateUUID) {
			$rootScope.$broadcast('veil:show');
			var exportUrl = url + 'exportMany?templateUUID=' + templateUUID;
			// start downloading zip file
			$.fileDownload(exportUrl)
				.done(function() {
					$rootScope.$broadcast('veil:hide');
				})
				.fail(function() {
					swal({
						title: "Zip file export failed.",
						type: "warning",
					});
					$rootScope.$broadcast('veil:hide');
				});
		};

		function generateEmptyTemplate() {
			var emptyTemplate = {
				templateUUID: null,
				visId: "New " + Date.now(),
				description: "",
				versionNum: 0,
				parentUUID: null,
				type: self.mapType(1),
				tree_index: 0
			};
			return emptyTemplate;
		}

		function getEmptyEditTreeElement() {
			var editTreeElement = {
				templateUUID: null,
				visId: null,
				description: null,
				versionNum: null,
				parentUUID: null,
				type: self.mapType(0),
				tree_index: 0
			};
			return editTreeElement;
		}

		function insertTreeElement(newNode, responseData, dependent) {
			$http.post(url, newNode).success(function(data) {
				responseData.data = data;
				responseData.newNode = newNode;
				$rootScope.$broadcast("TemplatesService:insertSuccess", responseData, dependent);
			});
		}

		function renameTreeElement(editedNode) {
			$http.put(url + "rename/", editedNode).success(function(data) {
				$rootScope.$broadcast("TemplatesService:renameSuccess", data);
			});
		}

		function deleteTreeElement(responseData) {
			$http.delete(url + responseData.obj.original.templateUUID).success(function(data) {
				responseData.data = data;
				$rootScope.$broadcast("TemplatesService:deleteSuccess", responseData);
			});
		}

		function save(template) {
			return $http({
					method: "PUT",
					url: url,
					data: template
				})
				.then(onSaveTemplate)
				.catch(onErrorHandler);

			function onSaveTemplate(data, status, headers, config) {
				$rootScope.$broadcast("TemplatesService:fullUpdateSuccess", template);
				return data.data;
			}

			function onErrorHandler(message) {
				$rootScope.$broadcast("TemplatesService:updateIndexProblem", template);
			}
		}

		function updatePostion(moveEvent, node) {
			return $http({
					method: "PUT",
					url: url + "index",
					data: moveEvent
				})
				.then(onSaveTemplate)
				.catch(onErrorHandler);

			function onSaveTemplate(data, status, headers, config) {
				$rootScope.$broadcast("TemplatesService:updateIndexSuccess", moveEvent, node);
			}

			function onErrorHandler(message) {
				$rootScope.$broadcast("TemplatesService:updateIndexProblem", moveEvent);
			}
		}

		function mapType(templateType) {
			if (typeof templateType == "string") {
				return self.templateTypes.indexOf(templateType);
			} else if (typeof templateType == "number") {
				if (templateType < 0 || templateType > self.templateTypes.length) {
					return "";
				}
				return self.templateTypes[templateType];
			}
		}
	};

	/*---------------------------------------------------------------------------------------------------*/

	function updateWorksheetPropertyName(worksheet, newName) {
		worksheet.name = newName;
	};

	function deleteWorksheetProperties(worksheets, worksheet) {
		var index = worksheets.indexOf(worksheet);
		worksheets.splice(index, 1);
	};

	function insertWorksheetProperties(worksheets, newName) {
		worksheets.push({
			cells: [],
			name: newName,
			properties: {}
		});
	};
})();