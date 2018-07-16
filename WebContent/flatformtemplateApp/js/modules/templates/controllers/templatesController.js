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
		.controller('TemplatesController', TemplatesController);

	/* @ngInject */
	function TemplatesController($scope, $rootScope, $http, $timeout, TemplatesService, SpreadSheetService, CoreCommonsService, FlatFormTemplateCommonsService, DrawingService, Flash) {

		// ------------------- resize left pane, right pane (with flex)
		var windowResizeNamespace = "templates";
		var rightPane = $(".templates.paneContainer .rightPane");
		var leftPane = $(".templates.paneContainer .leftPane");
		// get elements .getToCalcN
		var additionElements = [40];
		var flexToResize = null;
		var loader = $(".frontend-cover.paneCover");
		var importBtn = $(".btn-file input");
		$timeout(function() {
			FlatFormTemplateCommonsService.allowResizingPane(leftPane, rightPane, null, flexToResize, additionElements, windowResizeNamespace);
		}, 0);
		$scope.readyTrees = {};
		$scope.template = null;
		$scope.availableOperations = {};
		$scope.displayMode = "main"; // what to show in right pane; options: "main", renameForm", "spreadsheet"
		$scope.spreadLoader = SpreadSheetService.getSpreadLoader();

		$scope.showHideLoader = showHideLoader;
		$scope.readyTree = readyTree;
		$scope.upload = upload;
		$scope.importToDirectory = importToDirectory;

		$scope.exportOne = exportOne;
		$scope.doExport = doExport;

		$scope.exitSpreadSheetEditor = exitSpreadSheetEditor;
		$scope.selectNode = selectNode;
		$scope.prepareEditTreeElement = prepareEditTreeElement;
		$scope.prepareRenameTreeElement = prepareRenameTreeElement;
		$scope.cancelRenameTreeElement = cancelRenameTreeElement;
		$scope.renameTreeElement = renameTreeElement;
		$scope.insertTreeElement = insertTreeElement;
		$scope.insertTreeElementOnPosition = insertTreeElementOnPosition;
		$scope.deleteTreeElement = deleteTreeElement;
		$scope.resetAvailableOperations = resetAvailableOperations;
		$scope.exportMany = exportMany;
		activate();

		/************************************************** IMPLEMENTATION *************************************************************************/

		function activate() {
			for (var i = 1; i <= 1; i++) {
				additionElements.push(rightPane.find(".getToCalc" + i));
			}
			$scope.readyTrees = {
				templates: false,
			};
			$rootScope.$broadcast('veil:hide');
			FlatFormTemplateCommonsService.showHideMenuLoader(true);
			CoreCommonsService.askIfReload = false;
			$scope.templatesTreeAjaxUrl = TemplatesService.getTemplatesUrl();
			$scope.typesConfig = FlatFormTemplateCommonsService.getTypesConfig("templates"); // types for jsTree icons
			$scope.templateTypes = TemplatesService.getTemplateTypes();
			$scope.editTreeElement = TemplatesService.getEmptyEditTreeElement();

			$scope.availableOperations = { // to enable/disable jsTree Context Menu operations and Buttons above tree
				createDirectory: false,
				createTemplateDependent: false,
				createTemplateAfter: true,
				createTemplateBefore: true,
				rename: false,
				import: false,
				export: false,
				edit: false,
				delete: false
			};
		}

		// sometimes Veil (loader) doesn't disappear, so we hide it when tree is ready too
		$scope.$watch("readyTrees", function(newValue, oldValue) {
			if (newValue.templates) {
				$rootScope.$broadcast('veil:hide');
				$scope.showHideLoader(false);
				FlatFormTemplateCommonsService.showHideMenuLoader(false);
			}
		}, true);

		// watch jsTree buttons states and set visibility for context menu actions
		$scope.$watch("availableOperations", function(newValue, oldValue) {
			$scope.contextMenu.CreateDirectory._disabled = !newValue.createDirectory;
			$scope.contextMenu.CreateTemplateDependent._disabled = !newValue.createTemplateDependent;
			$scope.contextMenu.CreateTemplateAfter._disabled = !newValue.createTemplateAfter;
			$scope.contextMenu.CreateTemplateBefore._disabled = !newValue.createTemplateBefore;
			$scope.contextMenu.Rename._disabled = !newValue.rename;
			$scope.contextMenu.Import._disabled = !newValue.import;
			$scope.contextMenu.Export._disabled = !newValue.export;
			$scope.contextMenu.Edit._disabled = !newValue.edit;
			$scope.contextMenu.Delete._disabled = !newValue.delete;

		}, true);

		$scope.$watch("displayMode", function(newValue, oldValue) {
			if (newValue == "renameForm" || newValue == "spreadsheet") {
				$scope.showHideLoader(true, false);
			} else {
				$scope.showHideLoader(false);
			}
		}, true);

		angular.element(window).on('keydown', function(event) {
			if (event.keyCode === 113 && $scope.displayMode === "spreadsheet") { // F2
				SpreadSheetService.startEdit();
			}
		});



		function showHideLoader(show, withIcon) {
			FlatFormTemplateCommonsService.showHidePaneLoader(loader, show, withIcon);
		};

		function readyTree(e, data) {
			$scope.jsTreeDomElement = angular.element("js-tree#manageTemplatesTree");
			$scope.tree = $($scope.jsTreeDomElement).jstree();
			$scope.treeInst = $.jstree.reference($scope.jsTreeDomElement);
			$scope.tree.settings.core.multiple = false;
			$scope.readyTrees.templates = true;
			$scope.$apply();

			// handle double click (edit)
			$scope.jsTreeDomElement.on("dblclick", "li a", function() {
				if ($(this).find("i.directory").length == 0) {
					$scope.prepareEditTreeElement();
				}
			});
		};

		// import one xls (xlsx) by spreadsheet editor
		function upload(files) {
			$rootScope.$broadcast('veil:show');

			if ($scope.select !== undefined && $scope.select != null && $scope.template != null && $scope.displayMode == "spreadsheet" && files.length > 0) {
				var file = files[0];
				var spreadSheetLoader = SpreadSheetService.getSpreadLoader();
				spreadSheetLoader.isJsonLoaded = false;

				TemplatesService.upload(file).then(function(response) {
					if (response) {
						$scope.template.workbook = response.data.workbook;
						$scope.template.jsonForm = response.data.jsonForm;
						$scope.displayMode = "spreadsheet";
						SpreadSheetService.setJsonForm(response.data.jsonForm);
						DrawingService.redraw();
						$rootScope.$broadcast('veil:hide');
						CoreCommonsService.askIfReload = true;
					} else {
						console.log("Template doesn't exist.");
					}
				});
			}
		};

		// import many xls (xlsx) by jsTree
		function importToDirectory(files) {
			if (files.length === 0) {
				return;
			}
			$rootScope.$broadcast('veil:show');

			if ($scope.select !== undefined && $scope.select != null && files.length > 0) {
				var directory = $scope.select.original;
				TemplatesService.importToDirectory(files, directory).then(function(response) {
					var obj = $scope.select;
					var inst = $scope.treeInst;
					for (var i = 0; i < response.data.length; i++) {
						var insertedData = response.data[i];
						if (insertedData.templateUUID !== undefined && insertedData.templateUUID !== null && insertedData.templateUUID !== "") {
							var newNodeId = inst.create_node(obj, insertedData, "last", function(new_node) {});
							inst.deselect_all();
							$timeout(function() {
								inst.select_node(newNodeId);
							}, 0);
						}
					}
					$rootScope.$broadcast('veil:hide');
					$scope.showHideLoader(false);
				});
			}
		};

		function doExport() {
			if ($scope.select.original.type === TemplatesService.mapType(0)) {
				exportMany();
			} else {
				exportOne();
			}
		}

		// export one xls (xlsx) by spreadsheet editor
		function exportOne() {
			if (CoreCommonsService.askIfReload) {
				swal({
					title: "Changes aren't saved.",
					text: "You can't export unsaved Template",
					type: "warning",
				});
			} else {
				TemplatesService.exportToXls($scope.select.original.templateUUID);
			}
		};

		// export many xls (xlsx) by jsTree
		function exportMany() {
			TemplatesService.exportMany($scope.select.original.templateUUID);
		};

		$scope.$on("TemplatesController:exitSpreadSheetEditor", function() {
			$scope.exitSpreadSheetEditor();
		});

		function exitSpreadSheetEditor() {
			if (CoreCommonsService.askIfReload) {
				swal({
					title: "Changes aren't saved.",
					text: "Leave editor discarding changes?",
					type: "warning",
					showCancelButton: true,
					confirmButtonColor: "#d9534f",
					confirmButtonText: "Leave",
					cancelButtonText: "Stay"
				}, function(isConfirm) {
					if (isConfirm) {
						reallyExitSpreadSheetEditor(true);
					}
				});
			} else {
				reallyExitSpreadSheetEditor(false);
			}

		};

		function reallyExitSpreadSheetEditor(ifApply) {
			$scope.displayMode = "main";
			CoreCommonsService.askIfReload = false;
			if (ifApply) {
				$scope.$apply();
			}
		};

		function selectNode(e, data) {
			$scope.select = data.node;
			if ($scope.select.original.parentUUID !== null) { // if not root
				$scope.availableOperations.delete = true;
				$scope.availableOperations.rename = true;
				$scope.availableOperations.createTemplateAfter = true;
				$scope.availableOperations.createTemplateBefore = true;
			} else { // if root
				$scope.availableOperations.delete = false;
				$scope.availableOperations.rename = false;
				$scope.availableOperations.createTemplateAfter = false;
				$scope.availableOperations.createTemplateBefore = false;
			}

			if ($scope.select.original.type == TemplatesService.mapType(0)) { // if directory
				$scope.availableOperations.createDirectory = true;
				$scope.availableOperations.createTemplateDependent = true;
				$scope.availableOperations.edit = false;
				$scope.availableOperations.import = true;
			} else { // if not directory
				$scope.availableOperations.createDirectory = false;
				$scope.availableOperations.createTemplateDependent = false;
				$scope.availableOperations.edit = true;
				$scope.availableOperations.import = false;
			}
			$scope.availableOperations.export = $scope.select !== null;

			$scope.editTreeElement = TemplatesService.getEmptyEditTreeElement();

			$scope.displayMode = "main";

			$scope.$apply();
		};

		function prepareEditTreeElement() {
			$scope.displayMode = "main";
			if ($scope.select.original.type == TemplatesService.mapType(0)) { // if directory
				swal({
					title: "You can't edit",
					text: "Directory.",
					type: "warning",
				});
			} else {
				var uuid = $scope.select.original.templateUUID;
				fetchTemplate(uuid);
			}
		};

		function prepareRenameTreeElement(data) {
			if ($scope.select.original.parentUUID !== null) { // if not root
				$scope.editTreeElement.templateUUID = $scope.currentUUID = $scope.select.original.templateUUID;
				$scope.editTreeElement.visId = $scope.select.original.visId;
				$scope.editTreeElement.description = $scope.select.original.description;
				$scope.editTreeElement.versionNum = $scope.select.original.versionNum;
				$scope.editTreeElement.parentUUID = $scope.select.original.parentUUID;
				$scope.editTreeElement.type = $scope.select.original.type;
			} else {
				$scope.editTreeElement = TemplatesService.getEmptyEditTreeElement();
				$scope.currentUUID = null;
				swal({
					title: "You can't rename",
					text: "main directory.",
					type: "warning",
				});
			}
			$scope.displayMode = "renameForm";
			if (data !== undefined) { // if function is called from ContextMenu
				$scope.$apply();
			}
		};

		function cancelRenameTreeElement() {
			$scope.displayMode = "main";
		};

		function renameTreeElement() {
			$scope.showHideLoader(true);
			TemplatesService.renameTreeElement($scope.editTreeElement);
		};

		$scope.$on("TemplatesService:renameSuccess", function(e, responseData) {
			if (responseData.success) {
				var node = $scope.tree.get_node($scope.select);
				var newVisId = $scope.editTreeElement.visId;
				var newDescription = $scope.editTreeElement.description;
				var text = (newDescription === null || newDescription === "") ? newVisId : newVisId + " - " + newDescription;
				if ($scope.tree.rename_node(node, text)) {
					$scope.select.original.visId = newVisId;
					$scope.select.original.description = newDescription;
					$scope.select.original.text = text;
					$scope.select.original.versionNum++;
					$scope.currentUUID = null;
					$scope.editTreeElement = {};
					$scope.displayMode = "main";
				}
			}
			$scope.showHideLoader(false);
		});

		function insertTreeElement(type) {
			$scope.showHideLoader(true);
			var obj = $scope.select;
			if (obj.type != TemplatesService.mapType(0)) {
				swal({
					title: "You can't create",
					text: "new element here.",
					type: "warning",
				});
			} else {
				var newNode = TemplatesService.generateEmptyTemplate();
				newNode.parentUUID = obj.original.templateUUID;
				newNode.tree_index = obj.children.length;
				newNode.type = type;
				if (type === TemplatesService.mapType(0)) {
					newNode.jsonForm = null;
				} else {
					fillNewNode(newNode);
				}
				var responseData = {
					obj: obj
				};
				TemplatesService.insertTreeElement(newNode, responseData, true);
			}
		};

		/**
		 * Insert tree element after or before selected element.
		 */
		function insertTreeElementOnPosition(type, after) {
			$scope.showHideLoader(true);
			var obj = $scope.select;
			var parentId = obj.parent;
			var parentNode = $scope.treeInst.get_node(parentId);
			var position = parentNode.children.indexOf($scope.select.id) + 1;
			var newNode = TemplatesService.generateEmptyTemplate();
			newNode.parentUUID = obj.original.parentUUID;
			newNode.tree_index = position + (after ? 0 : -1);
			newNode.type = type;
			if (type === TemplatesService.mapType(0)) {
				newNode.jsonForm = null;
			} else {
				fillNewNode(newNode);
			}
			var responseData = {
				obj: obj
			};
			TemplatesService.insertTreeElement(newNode, responseData, false);
		};

		function fillNewNode(newNode) {
			var emptyJsonForm = SpreadSheetService.getEmptyJsonForm();
			newNode.jsonForm = emptyJsonForm;
			var workbook = {};
			var worksheet = {};
			worksheet.name = "Sheet1";
			worksheet.cells = [];
			worksheet.properties = {};
			workbook.worksheets = [worksheet];
			workbook.properties = {};
			newNode.workbook = workbook;
		}

		$scope.$on("TemplatesService:insertSuccess", function(e, responseData, dependent) {
			var insertedData = responseData.data;
			var inst = $scope.treeInst;
			var newNode = responseData.newNode;
			var obj = responseData.obj;
			if (insertedData.templateUUID !== undefined && insertedData.templateUUID !== null && insertedData.templateUUID !== "") {
				newNode.templateUUID = insertedData.templateUUID;
				if (newNode.description != "") {
					newNode.text = newNode.visId + " - " + newNode.description;
				} else {
					newNode.text = newNode.visId;
				}
				if (dependent) {
					var newNodeId = inst.create_node(obj, newNode, "last", function(new_node) {});
				} else {
					var newNodeId = inst.create_node(obj.parent, newNode, newNode.tree_index, function(new_node) {});
				}
				inst.deselect_all();
				$timeout(function() {
					inst.select_node(newNodeId);
					$scope.prepareRenameTreeElement();
				}, 0);
			}
			$scope.showHideLoader(false);
		});

		function deleteTreeElement() {
			$scope.showHideLoader(true);
			var inst = $scope.treeInst;
			var obj = $scope.select;
			if (obj.original.parentUUID === null) {
				swal({
					title: "You can't delete",
					text: "main directory.",
					type: "warning",
				});
				$scope.showHideLoader(false);
			} else {
				var message = "";
				if (obj.type == TemplatesService.mapType(0)) {
					message = "Directory \"" + obj.text + "\" with its all Directories and Templates?";
				} else {
					message = "Template\"" + obj.text + "\"?";
				}
				swal({
					title: "Are you sure",
					text: "you want to delete " + message,
					type: "warning",
					showCancelButton: true,
					confirmButtonColor: "#d9534f"
				}, function(isConfirm) {
					if (isConfirm) {
						var responseData = {
							obj: obj
						};
						TemplatesService.deleteTreeElement(responseData);
					} else {
						$scope.showHideLoader(false);
					}
				});
			}
		};

		$scope.$on("TemplatesService:deleteSuccess", function(e, responseData) {
			if (responseData.data.success) {
				$scope.treeInst.delete_node(responseData.obj);
				$scope.editTreeElement = TemplatesService.getEmptyEditTreeElement();
				$scope.currentUUID = null;
				$scope.resetAvailableOperations();
				$scope.select = null;
			}
			$scope.showHideLoader(false);
		});

		function resetAvailableOperations() {
			angular.forEach($scope.availableOperations, function(value, key) {
				$scope.availableOperations[key] = false;
			});
		}

		// fetch details to prepare spreadsheet editor
		function fetchTemplate(uuid) {
			$scope.spreadLoader.isJsonLoaded = false;

			$rootScope.$broadcast('veil:show');
			TemplatesService.fetchTemplate(uuid).then(function(template) {
				if (template) {
					$scope.template = template;
					$scope.displayMode = "spreadsheet";
					SpreadSheetService.setJsonForm(template.jsonForm);
					DrawingService.redraw();
					$rootScope.$broadcast('veil:hide');
				} else {
					console.log("Template doesn't exist.");
				}
			});
		}

		$scope.$on("TemplatesService:fullUpdateSuccess", function(e, template) {
			CoreCommonsService.askIfReload = false;
			var node = $scope.tree.get_node($scope.select);
			var newVisId = template.visId;
			var newDescription = template.description;

			var text = (newDescription === null || newDescription === "") ? newVisId : newVisId + " - " + newDescription;
			if ($scope.tree.rename_node(node, text)) {
				$scope.select.original.visId = newVisId;
				$scope.select.original.description = newDescription;
				$scope.select.original.text = text;
				$scope.select.original.versionNum++;
				$scope.template.versionNum++;

				var newType = $scope.template.type;
				$scope.select.original.type = newType;
				$scope.tree.set_type($scope.select, newType);
			}
		});

		$scope.$on("TemplatesService:updateIndexSuccess", function(e, moveEvent, node) {
			node.original.parentUUID = moveEvent.newParentUUID;
		});

		$scope.$on("TemplatesService:updateIndexProblem", function(e, moveEvent) {
			Flash.create('danger', "Move element operation failed!");
			$scope.treeInst.refresh();
		});

		/**
		 * Move tree node with required properties
		 *
		 * @param {[type]} e [description]
		 * @param {[type]} data [description]
		 * @return {[type]} [description]
		 */
		$scope.dnd = function(e, data) {
			console.log("e", e);
			console.log("data", data);

			var newParentObject = findParent(data.node.parent, data.instance._model.data);

			var moveEvent = {
				templateUUID: data.node.original.templateUUID,
				oldParentUUID: data.node.original.parentUUID,
				newParentUUID: newParentObject.original.templateUUID,
				newIndex: data.position,
				oldIndex: data.old_position
			};

			TemplatesService.updatePostion(moveEvent, data.node);
		};

		/**
		 * This function returns parent for selected node
		 *
		 * @param {[type]} parentId [description]
		 * @param {[type]} availableTreeNodes [description]
		 * @return {[type]} [description]
		 */
		var findParent = function(parentId, availableTreeNodes) {
			var parentObject = null;
			angular.forEach(availableTreeNodes, function(treeNode) {


				if (treeNode.parent != null) {
					if (parentId === treeNode.id) {
						parentObject = treeNode;
						return false;
					}
				}
			});
			return parentObject;
		};

		/**
		 * This scope is showing context menu
		 *
		 * @type {Object}
		 */
		$scope.contextMenu = {
			"CreateDirectory": {
				"label": "Create Directory",
				"action": function(data) {
					$scope.insertTreeElement(TemplatesService.mapType(0));
				}
			},
			"CreateTemplateDependent": {
				"label": "Create Template Dependent",
				"action": function(data) {
					$scope.insertTreeElement(TemplatesService.mapType(1));
				},
				separator_after: true
			},
			"CreateTemplateBefore": {
				"label": "Create Template Before",
				"action": function(data) {
					$scope.insertTreeElementOnPosition(TemplatesService.mapType(1), false);
				}
			},
			"CreateTemplateAfter": {
				"label": "Create Template After",
				"action": function(data) {
					$scope.insertTreeElementOnPosition(TemplatesService.mapType(1), true);
				},
				separator_after: true
			},
			"Rename": {
				"label": "Rename",
				"action": function(data) {
					$scope.prepareRenameTreeElement(data);
				}
			},
			"Edit": {
				"label": "Edit",
				"action": function(data) {
					$scope.prepareEditTreeElement(data);
				}
			},
			"Delete": {
				"label": "Delete",
				"action": function(data) {
					$scope.deleteTreeElement();
				},
				separator_after: true,
			},
			"Import": {
				"label": "Import",
				"action": function(data) {
					importBtn.trigger("click");
				}
			},
			"Export": {
				"label": "Export",
				"action": function(data) {
					$scope.exportMany(data);
				},
				separator_after: true
			},
		};

	}
})();