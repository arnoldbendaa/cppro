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
		.module('flatFormTemplateApp.generate')
		.controller('GenerateController', GenerateController);

	/* @ngInject */
	function GenerateController($rootScope, $scope, $timeout, GenerateService, CoreCommonsService, FlatFormTemplateCommonsService, TemplatesService, ConfigurationsService) {
		var templatesTreeInstance, configurationsTreeInstance;
		var selectedTemplate, selectedConfiguration;
		CoreCommonsService.askIfReload = false;
		$scope.readyTrees = {};
		$scope.isInvalid = true;
		$scope.validation = {}
		$scope.generator = {};
		$scope.activateNode = activateNode;
		$scope.readyTree = readyTree;
		activate();
		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			initResize();
			onInitBothTrees();
			onGeneratorChanged();
			$scope.readyTrees = {
				templates: false,
				configurations: false,
			};
			//$scope.templatesTree = TemplatesService.getTemplatesForGenerate();
			$scope.templatesTreeAjaxUrl = TemplatesService.getTemplatesForGenerateUrl();
			$scope.templatesTypesConfig = FlatFormTemplateCommonsService.getTypesConfig("templates"); // types for jsTree Templates icons
			//$scope.configurationsTree = ConfigurationsService.getConfigurationsFromDatabaseForGenerate();
			$scope.configurationsTreeAjaxUrl = ConfigurationsService.getConfigurationsFromDatabaseForGenerateUrl();
			$scope.configurationsTypesConfig = FlatFormTemplateCommonsService.getTypesConfig("configurations"); // types for jsTree Configurations icons
			$scope.financeCubes = GenerateService.getFinanceCubes();
			$scope.validation = {
				templates: "",
				configurations: "",
				financeCubes: ""
			}
			$scope.generator = {
				templateUUID: null,
				configurationUUID: null,
				financeCubeModels: [],
				name: null,
				description: null,
				override: true,
				lastRequest: false
			};
			$rootScope.$broadcast('veil:hide');
		}

		function initResize() {
			// ------------------- resize left pane, right pane (with flex)
			var windowResizeNamespace = "generate";
			var rightPane = $(".generate .paneContainer .rightPane");
			var leftPane = $(".generate .paneContainer .leftPane");
			var additionElements = [];
			var flexToResize = null;

			$timeout(function() {
				FlatFormTemplateCommonsService.allowResizingPane(leftPane, rightPane, null, flexToResize, additionElements, windowResizeNamespace);
			}, 0);
		}

		function onInitBothTrees() {
			FlatFormTemplateCommonsService.showHideMenuLoader(true);
			// sometimes Veil (loader) doesn't disappear, so we hide it when both trees are ready too
			$scope.$watch("readyTrees", function(newValue, oldValue) {
				if (newValue.templates && newValue.configurations) {
					FlatFormTemplateCommonsService.showHideMenuLoader(false);
				}
			}, true);
		}

		function onGeneratorChanged() {
			$scope.$watch('generator.financeCubeModels.length', function(length) {
				if (length > 0) {
					$scope.validation.financeCubes = "";
				}
			});

			$scope.$watch('generator.templateUUID', function(uuid) {
				if (uuid) {
					$scope.validation.templates = "";
				}
				$scope.validation.configurations = "";
			});

			$scope.$watch('generator.configurationUUID', function(uuid) {
				if (uuid) {
					$scope.validation.configurations = "";
				}
			});
		}

		function activateNode(e, data) {
			var result = null;
			var selectedNodes = data.instance.get_selected(true);

			if (selectedNodes.length !== 0) {
				data.instance.deselect_all();
				data.instance.select_node(data.node);
				result = data.node.original;
			}

			if ($(e.currentTarget).attr('id') === "selectTemplatesTree") {
				selectedTemplate = result;
				if (result) {
					$scope.generator.templateUUID = result.templateUUID;
					$scope.generator.name = result.visId;
					$scope.generator.description = result.description;
				} else {
					$scope.generator.templateUUID = null;
					$scope.generator.name = "";
					$scope.generator.description = "";
				}
			} else if ($(e.currentTarget).attr('id') === "selectConfigurationsTree") {
				selectedConfiguration = result;
				$scope.generator.configurationUUID = (result) ? result.configurationUUID : result;
			}

			$scope.$apply('generator');
		}

		function readyTree(e, data) {
			var element = $(e.currentTarget);
			var tree = $(element).jstree();
			tree.settings.core.multiple = false;

			if ($(e.currentTarget).attr('id') === "selectTemplatesTree") {
				templatesTreeInstance = $.jstree.reference(element);
				$scope.readyTrees.templates = true;
			} else if ($(e.currentTarget).attr('id') === "selectConfigurationsTree") {
				configurationsTreeInstance = $.jstree.reference(element);
				$scope.readyTrees.configurations = true;
			}
			$scope.$apply();
		}

		function isGeneratorValid() {
			var isValid = true;
			isValid = isTemplateValid() && isValid;
			isValid = isConfigurationValid() && isValid;
			isValid = isNameValid() && isValid;
			isValid = isDescriptionValid() && isValid;
			isValid = isFinanceCubeValid() && isValid;
			return isValid;
		}

		function isTemplateValid() {
			var isValid = $scope.generator.templateUUID !== undefined && $scope.generator.templateUUID !== null;
			if (isValid === false) {
				$scope.validation.templates = "Please select a template."
			}
			return isValid;
		}

		function isConfigurationValid() {
			var isValid = true;
			var isTemplateChosen = isTemplateValid();
			if (isTemplateChosen) {
				if (selectedTemplate.type === "hierarchy based") {
					//if (selectedTemplate.type === TemplatesService.mapType(2)) {
					isValid = $scope.generator.configurationUUID !== undefined && $scope.generator.configurationUUID !== null;
				}
			}
			if (isValid === false) {
				$scope.validation.configurations = "Please select a configuration for selected template."
			}
			return isValid;
		}

		function isNameValid() {
			return $scope.generator.name !== undefined && $scope.generator.name !== null && $scope.generator.name !== "";
		}

		function isDescriptionValid() {
			return $scope.generator.description !== undefined && $scope.generator.description !== null && $scope.generator.description !== "";
		}

		function isFinanceCubeValid() {
			var isValid = true;

			var financeCubeModels = $scope.generator.financeCubeModels;
			if (!financeCubeModels || financeCubeModels.length === 0) {
				isValid = false;
				$scope.validation.financeCubes = "Please select at least one finance cube."
				return isValid;
			}

			var companies = [];
			for (var i = 0; i < financeCubeModels.length; i++) {
				var modelVisId = financeCubeModels[i].model.modelVisId;
				var company = getCompanyFromModelVisId(modelVisId);
				if (company === null) {
					isValid = false;
					break;
				}
				if (companies.indexOf(company) !== -1) {
					isValid = false;
					$scope.validation.financeCubes = "Flat forms can't have the same name. Companies for finance cubes are repeated."
					break;
				} else {
					companies.push(company);
				}
			}

			function getCompanyFromModelVisId(modelVisId) {
				var company = null;
				if (modelVisId.indexOf("/") !== -1) {
					company = modelVisId.split("/")[0];
				}
				if (modelVisId === "global") {
					company = "G";
				}
				return company;
			}
			if (isValid) {
				$scope.validation.financeCubes = "";
			}
			return isValid;
		}

		function clearValidation() {
			// clear previous validation messages
			angular.forEach($scope.validation, function(message, field) {
				$scope.validation[field] = "";
			});
		};

		$scope.doGenerate = function() {
			if (isGeneratorValid() == false) {
				return;
			}

			doFirstGenerateRequest($scope.generator);

			function doFirstGenerateRequest(generator) {
				GenerateService.generate(generator).then(function(response) {
					if (response.success) {
						if (response.message) {
							onSuccessResponse(response.message);
						}
					} else if (response.error && !generator.lastRequest) {
						swal({
							title: "Are you sure",
							text: "to override flat forms =" + response.message + " ?",
							type: "warning",
							showCancelButton: true,
							confirmButtonColor: "#d9534f"
						}, function(isConfirm) {
							if (isConfirm) {
								generator.override = isConfirm;
								generator.lastRequest = true;
								doLastGenerateRequest(generator);
							}
						});
					}
				});
			}

			function doLastGenerateRequest(generator) {
				GenerateService.generate(generator).then(function(response) {

					if (response.success) {
						onSuccessResponse(response.message);
						generator.lastRequest = false;
						generator.override = false;
					} else if (response.error) {

					}
				});
			}

			function onSuccessResponse(message) {
				clearValidation();

				swal({
					title: "Success",
					text: message,
					type: "info"
				}, function(isConfirm) {
					if (isConfirm) {

					}
				});
			}
		};
	}

})();