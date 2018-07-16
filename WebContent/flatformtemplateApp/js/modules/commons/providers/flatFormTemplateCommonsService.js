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
		.module('flatFormTemplateApp.commons')
		.service('FlatFormTemplateCommonsService', FlatFormTemplateCommonsService);

	/* @ngInject */
	function FlatFormTemplateCommonsService() {
		var self = this;
		self.showHidePaneLoader = showHidePaneLoader;
		self.showHideMenuLoader = showHideMenuLoader;
		self.getTemplateTypes = getTemplateTypes;
		self.getTypesConfig = getTypesConfig;
		self.allowResizingPane = allowResizingPane;
		self.resizeFlex = resizeFlex;

		// ------------------- flatFormTemplateApp: Commons ------------------------------------------

		function showHidePaneLoader(loader, show, withIcon) {
			if (show) {
				var iconClass = "with-icon";
				if (withIcon !== undefined && withIcon == false) {
					iconClass = "without-icon";
				}
				loader.addClass("show").removeClass("with-icon").removeClass("without-icon").addClass(iconClass);
			} else {
				loader.removeClass("show").removeClass("with-icon").removeClass("without-icon");
			}
		}

		var flatFormTemplateMenuLoader = $(".flatFormTemplateApp .frontend-cover.menuCover");

		function showHideMenuLoader(show) {
			if (show) {
				flatFormTemplateMenuLoader.addClass("show");
			} else {
				flatFormTemplateMenuLoader.removeClass("show");
			}
		}

		// if change this array, change its copy in TemplateMapper.java too
		var templatesTypes = ["directory", "standard", "hierarchy based"];

		function getTemplateTypes() {
			return templatesTypes;
		};

		// icons for Templates and Configurations in FlatFormTemplateApp
		function getTypesConfig(moduleName) {
			var obj;
			if (moduleName == "templates") {
				obj = {
					"default": {
						"icon": "glyphicon glyphicon-file"
					}
				};
				obj[templatesTypes[0]] = {
					"icon": "directory",
				};
				obj[templatesTypes[1]] = {
					"icon": "template simple-template",
				};
				obj[templatesTypes[2]] = {
					"icon": "template hierarchy-template",
				};
			} else if (moduleName == "configurations") {
				obj = {
					"default": {
						"icon": "glyphicon glyphicon-file"
					},
					"configuration": {
						"icon": "template"
					},
					"directory": {
						"icon": "directory"
					},
				};
			}
			return obj;
		};

		// ------------------- end of flatFormTemplateApp: Commons -----------------------------------

		// ------------------- flatFormTemplateApp: resize left pane, right pane (with flex) ---------

		function resizeRightPane(leftPane, rightPane, ctx, flexToResize, additionalElements) {
			var leftPaneWidth = leftPane.outerWidth(true);
			rightPane.width("calc(100% - " + leftPaneWidth + "px)");
			self.resizeFlex(rightPane, ctx, flexToResize, additionalElements);
		};

		function allowResizingPane(leftPane, rightPane, ctx, flexToResize, additionalElements, windowResizeNamespace) {
			leftPane.resizable({
				handles: "e",
				minWidth: 100,
				containment: "parent",
				resize: function() {
					resizeRightPane(leftPane, rightPane, ctx, flexToResize, additionalElements);
				}
			});
			bindRes(leftPane, rightPane, ctx, flexToResize, additionalElements, windowResizeNamespace);
		};

		function resizeFlex(rightPane, ctx, flexToResize, additionalElements) {
			if (flexToResize != null && flexToResize.length > 0) {
				// flexToResize.height(calcFlexHeight(rightPane, additionalElements));
				flexToResize.css("maxHeight", calcFlexHeight(rightPane, additionalElements));
				ctx.flex.refresh();
				ctx.data.refresh();
			}
		}

		function calcFlexHeight(rightPane, additionalElements) {
			var additionalSubstract = 0;
			angular.forEach(additionalElements, function(elem) {
				if (typeof elem == "number") {
					additionalSubstract += elem;
				} else {
					additionalSubstract += elem.outerHeight(true);
				}
			});
			return rightPane.height() - additionalSubstract;
		};

		var windowResizeNamespaces = []; //"configurations", "templates" and others

		function bindRes(leftPane, rightPane, ctx, flexToResize, additionalElements, windowResizeNamespace) {
			windowResizeNamespaces.push(windowResizeNamespace);
			// unbind resize events for all views
			angular.forEach(windowResizeNamespaces, function(eventName) {
				$(window).unbind("resize." + eventName);
			});
			// bind resize event for current view
			$(window).on('resize.' + windowResizeNamespace, function(obj) {
				if (!$(obj.target).hasClass("leftPane") && !$(obj.target).hasClass("modal-dialog")) {
					// console.log("resize", obj.handleObj.namespace); // check if the correct view is resized
					leftPane.width(355);
					resizeRightPane(leftPane, rightPane, ctx, flexToResize, additionalElements);
				}
			});
		};

		// ------------------- end of flatFormTemplateApp: resize left pane, right pane (with flex) --
	}

})();