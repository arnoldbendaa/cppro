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
		.module('coreApp.commons')
		.service('CoreCommonsService', CoreCommonsService);

	/* @ngInject */
	function CoreCommonsService($rootScope, $timeout, $cookies, $cookieStore, $modalStack, SessionService) {
		var self = this;
		var url = $BASE_PATH;

		self.askIfReload = false;
		self.globalSettings = {};

		self.getCookie = getCookie;
		self.setCookie = setCookie;
		self.allowResizingModal = allowResizingModal;
		self.calcAdditionalModalSubstract = calcAdditionalModalSubstract;
		self.resizeModelChooser = resizeModelChooser;
		self.resizeModalElement = resizeModalElement;
		self.closeTab = closeTab;
		self.changeModalParams = changeModalParams;
		self.saveGridParamsInCookie = saveGridParamsInCookie;
		self.resizedColumn = resizedColumn;
		self.sortedColumn = sortedColumn;
		self.alignColumns = alignColumns;
		self.initializeSorting = initializeSorting;
		self.initializeColumnsParameters = initializeColumnsParameters;
		self.findElementByKey = findElementByKey;
		self.initializeResizeFlexGrid = initializeResizeFlexGrid;
		self.adjustScreen = adjustScreen;
		self.setModalFullHeight = setModalFullHeight;
		//SpreadJS
		// http://tech-chec.blogspot.com/2009/03/convert-excel-column-letter-to-number.html (modified)
		var alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		self.getColumnIndexByName = getColumnIndexByName;
		self.getColumnNameByIndex = getColumnNameByIndex;

		activate();

		function activate() {
			self.globalSettings = {
				colIndexParams: {
					width: 50, // default width for first column in flex grid
					align: "left" // default align for first column in flex grid
				},
				colButtonsParams: {
					align: "center" // default align for Actions column in flex grid
				},
				colParams: {
					minWidth: 15, // minimum width for every column in flex grid
				}
			};
		}


		/************************************************** IMPLEMENTATION *************************************************************************/



		function getCookie(cookieName) {
			return $cookieStore.get(cookieName);
		}

		function setCookie(cookieName, cookieValue) {
			$cookieStore.put(cookieName, cookieValue);
		}

		window.onbeforeunload = function() {
			// if sessions has expired, don't ask if close application
			var sessionExpiry = SessionService.getSessionParams()[0];
			var utcTime = SessionService.getSessionParams()[1];
			if (utcTime > (sessionExpiry + 10000)) { // 10 extra seconds to make sure
				self.askIfReload = false;
			}
			// ask if close application (if necessary)
			if (self.askIfReload) {
				return "Are you sure you want to close application and discard changes?";
			}
		};


		/**
		 * Function is called after opening modal. What to do:
		 * Get cookie or create empty one.
		 * Set columns width, columns content alignment, sort by one column.
		 * If there is any element [elementToResize] which should be resized during resizing modal, do it for the first time.
		 * Bind resizing and dragging modal.
		 * @param  {[type]} modalDialog        [description]
		 * @param  {[type]} elementToResize    mainly it is flex grid
		 * @param  {[type]} additionalElements the rest of elements which height must be summed up to substract form modal-body height (to calculate elementToResize height)
		 * @param  {[type]} ctx                [description]
		 * @return {[type]}                    [description]
		 */
		function allowResizingModal(modalDialog, elementToResize, additionalElements, ctx) {
			// get or define cookie
			var cookieName = $(modalDialog).children(".modal-content").children().attr("data-cookie");
			var currentCookie = null;
			if (cookieName !== undefined) {
				if ($cookieStore.get(cookieName) === undefined) {
					$cookieStore.put(cookieName, {});
				} else {
					currentCookie = $cookieStore.get(cookieName);
				}
			}
			if (ctx !== undefined) {
				// set minimum width for all flex grid columns
				// set column width based on cookie or global settings
				self.initializeColumnsParameters(ctx, currentCookie);
				// align text in some columns based on cookie
				self.alignColumns(ctx.flex);
				// initialize sorting based on cookie
				self.initializeSorting(ctx, currentCookie);
			}

			var isElementToResize = false;
			// TODO $.isArray(myArray) - is Object or array?
			if (typeof elementToResize !== "undefined" && elementToResize !== null) {
				if ($.isArray(elementToResize)) {
					if ($(elementToResize).length > 0) {
						isElementToResize = true;
					}
				} else {
					if ($(elementToResize).length > 0) {
						isElementToResize = true;
					} else {
						// Sometimes elementToResize wasn't loaded on time and now we've got only its name, so we can get it again by selector name.
						var newElementToResize = $(elementToResize.selector);
						if (newElementToResize.length > 0) {
							elementToResize = newElementToResize;
							isElementToResize = true;
						}
					}
				}
			}
			// resize modal element for the first time
			// or: just reset modalHeight (if there is no element to be resized with modal and there is no cookie - so it's nothing more to calculate)
			if (isElementToResize) {
				// Resize modal element twice:
				// For FlexGrid: Timeout for appropriate flex grid data refresh (problem when we initialize sorting based on cookie).
				// For other modals: Sometimes data comes later than modal is resizing.
				resizeModalElementWithTimeout(modalDialog, elementToResize, additionalElements, ctx, currentCookie, 0);
				resizeModalElementWithTimeout(modalDialog, elementToResize, additionalElements, ctx, currentCookie, 1000);
			} else {
				if (currentCookie === null || currentCookie.height === undefined) {
					// On modal open modalDialog is almost 100% height (in css rules) - to right calculate one resizable element in modal. 
					// After calculations we can change modal height to auto (if there is no height defined in cookie).
					$(modalDialog).height("auto");
				}
				switchModalScrollbarY(modalDialog, true);
			}
			// move modal if is outside browser window
			$timeout(function() {
				moveModal(modalDialog);
			}, 0);
			// bind resizing for this modal
			$(modalDialog).resizable({
				containment: "parent",
				minHeight: 300,
				minWidth: 300,
				cancel: ".modalReset",
				start: function(evevnt, ui) {
					// On modal load maxHight is calculated. Since first resizing it just can be almost 100%.
					$(modalDialog).css({
						maxHeight: "calc(100% - 1px)",
					});
					// Try to resize model-chooser if is set (i.e. before creasting Finance Cube or Budget Cycle)
					self.resizeModelChooser(250);
				},
				resize: function(event, ui) {
					// try to resize one modal element if is set (mainly it is flex grid)
					if (isElementToResize) {
						self.resizeModalElement(modalDialog, elementToResize, additionalElements, ctx);
						// self.resizeModalElement(modalDialog, $(".budget-cycle-xml-forms .unselected-forms .xml-forms"), [$(".unselected-forms .xml-forms-buttons"), $(".unselected-forms .forms-info"), 80]);
					}
					//$rootScope.$broadcast("CoreCommonsService:ModalResize");
				},
				stop: function(event, ui) {
					if (cookieName !== undefined) {
						saveModalParamsInCookie(modalDialog, cookieName);
					}
				}
			});
			// bind dragging for this modal
			$(modalDialog).draggable({
				handle: ".modal-header, .modal-footer",
				containment: "parent",
				cancel: ".modalReset .modal-header, .modalReset .modal-footer",
				stop: function(event, ui) {
					moveModal(modalDialog);
					if (ctx !== undefined) {
						ctx.data.refresh();
					}
					if (cookieName !== undefined) {
						saveModalParamsInCookie(modalDialog, cookieName);
					}
				}
			});
		}

		var resizeModalElementWithTimeout = function(modalDialog, elementToResize, additionalElements, ctx, currentCookie, timeout) {
			var currentTimeout = (timeout !== undefined) ? timeout : 0;
			$timeout(function() {
				if (currentCookie !== null && currentCookie.height !== undefined) {
					self.resizeModalElement(modalDialog, elementToResize, additionalElements, ctx, true, currentCookie.height);
				} else {
					self.resizeModalElement(modalDialog, elementToResize, additionalElements, ctx, true);
				}
				switchModalScrollbarY(modalDialog, true);
			}, currentTimeout);
		};

		var switchModalScrollbarY = function(modalDialog, showScrollbarY) {
			var overflowY = (showScrollbarY) ? 'auto' : 'hidden';
			$(modalDialog).find(".modal-body").css({
				overflowY: overflowY
			});
		};

		var moveModal = function(modalDialog) {
			// move modal to left if it's not all in window
			var modalPositionLeft = $(modalDialog).position().left;
			var modalWidth = $(modalDialog).outerWidth(true);
			var windowWidth = $(window).width();
			var modalRightEdgePosition = modalPositionLeft + modalWidth;
			if (modalRightEdgePosition > windowWidth) {
				var difference = modalRightEdgePosition - windowWidth; // what width is modal part which is outside browser window?
				$(modalDialog).css("left", modalPositionLeft - difference); // move modal left as much as width is its part outside browser window
			}
		};

		function calcAdditionalModalSubstract(additionalElements) {
			var additionalModalSubstract = 0;
			angular.forEach(additionalElements, function(elem) {
				// Sometimes elem wasn't loaded on time and now we've got only its name, so we can get it again by selector name.
				if (typeof elem == "number") {
					additionalModalSubstract += elem;
				} else {
					additionalModalSubstract += $(elem.selector).outerHeight(true);
					// $(elem.selector).addClass("testYellowBg");
				}
			});
			return additionalModalSubstract;
		}

		// Try to resize model-chooser if is set (i.e. before creasting Finance Cube or Budget Cycle)
		// TODO remove after moving ModalChooser to separate modal
		function resizeModelChooser(timeout) {
			$timeout(function() {
				if ($(".model-chooser .list").length > 0) {
					$(".model-chooser .list").css("maxHeight", "100%");
				}
			}, timeout);
		}

		function setModalFullHeight(cookieName, modalDialog) {
			var cookie = getCookie(cookieName);
			if (cookie.height === undefined) {
				$(modalDialog).height("100%");
				return true;
			} else {
				return undefined;
			}
		}

		// Resize one modal element [elementToResize] during resizing modal or after modal load.
		function resizeModalElement(modalDialog, elementToResize, additionalElements, ctx, resetModalHeight, cookieParamsHeight) {
			// calc possible body height
			var mh = modalDialog.find(".modal-header");
			var mf = modalDialog.find(".modal-footer");
			var mb = modalDialog.find(".modal-body");
			var modalTop = $(modalDialog).position().top;
			var modalBottom = modalTop;
			var toCut = modalBottom + modalTop + mh.outerHeight(true) + mf.outerHeight(true) + (mb.outerHeight(true) - mb.height()) + (modalDialog.outerHeight(true) - modalDialog.height());
			var modalBodyPossibleHeight = $(window).height() - toCut;
			// resize elementToResize
			var modalBody = null;
			var additionalModalSubstract = 0;
			// Try to get modal-body. If there is more than one in modal-content, get this one which has class "useInModalHeightCalc"
			if ($(modalDialog).find(".modal-body").length > 1) {
				modalBody = $(modalDialog).find(".modal-body.useInModalHeightCalc");
			} else {
				modalBody = $(modalDialog).find(".modal-body");
			}
			if ($.isArray(elementToResize)) {
				angular.forEach(elementToResize, function(value, key) {
					additionalModalSubstract = 0;
					if (typeof additionalElements[key] != "undefined") {
						additionalModalSubstract = self.calcAdditionalModalSubstract(additionalElements[key]);
					}

					// TODO two options necessary?			
					var calcHeight = 0;
					var calcHeight = modalBody.height() - additionalModalSubstract;
					// if (cookieParamsHeight === undefined) {
					// 	calcHeight = modalBodyPossibleHeight - additionalModalSubstract;
					// } else {
					// 	calcHeight = modalBody.height() - additionalModalSubstract;
					// }

					$(value.selector).css({
						maxHeight: (calcHeight > 49) ? calcHeight : 49, // flex grid should be min 49px height to not hide table headers
					});
				});
			} else {
				if (typeof additionalElements != "undefined") {
					additionalModalSubstract = self.calcAdditionalModalSubstract(additionalElements);
				}
				var calcHeight = modalBody.height() - additionalModalSubstract; //var calcHeight = modalBodyPossibleHeight - additionalModalSubstract;
				if (elementToResize.length === 0) {
					elementToResize = $(elementToResize.selector);
				}
				$(elementToResize).css({
					maxHeight: (calcHeight > 49) ? calcHeight : 49, // flex grid should be min 49px height to not hide table headers
				});
			}
			if (ctx !== undefined && ctx.data !== undefined) {
				ctx.data.refresh();
			}
			$timeout(function() {
				if (cookieParamsHeight === undefined && resetModalHeight !== undefined) {
					// On modal open modalDialog is almost 100% height (in css rules) - to right calculate one resizable element in modal. 
					// After calculations we can change modal height to auto (if there is no height defined in cookie).
					$(modalDialog).height("auto");
				}
			}, 1000);

			// Sometimes (i.e. when modal contains some flexGrids) we can't forward more than one flex to allowResizingModal() function. 
			// In that situation we can receive broadcast in our directive and refresh all ctx.data manually (look at modelMappingsDetails.js)
			$rootScope.$broadcast("CoreCommonsService:CanTryToRefreshFlex");
		}

		// Save in cookie main modal parameters: size and position.
		function saveModalParamsInCookie(modalDialog, cookieName) {
			var allParams = $cookieStore.get(cookieName);
			if (allParams !== undefined) {
				allParams.width = $(modalDialog).width();
				allParams.height = $(modalDialog).height();
				allParams.left = $(modalDialog).position().left;
				allParams.top = $(modalDialog).position().top;
				$timeout(function() { // timeout is necessary to pass asynchro
					$cookieStore.put(cookieName, allParams);
				}, 0);
			}
		}

		function closeTab($timeout) {
			$timeout(function() {
				/*http://www.dotnetvishal.com/2013/01/close-current-browser-tab-using.html*/
				var win = window.open("", "_self"); /* url = "" or "about:blank"; target="_self" */
				var tryClose = win.close();
				// Can't close if page wasn't opened by script - then win.close() is undefined.
				// But win.close() is undefined also when: page was opened by script, user tried to close page, but he clicked "Stay on this page".
				// In this situation we don't show sweetAlert - we recognize this when we check if document.referrer and document.URL are different (= page was POSTed, so opened by script).
				if (tryClose === undefined && document.referrer.split("#")[0] == document.URL.split("#")[0]) {
					if (typeof(swal) === "function") {
						swal("Sorry, can't close.", "Try to close window manually.", "warning");
					}
				}
			}, 250);
		}

		// Get (from cookie) modal size and position and set it to modal-dialog in DOM.
		function changeModalParams(modalDialog, cookieName) {
			var params = $cookieStore.get(cookieName);
			if (params !== undefined && params.height !== undefined) {
				var newParams = {};
				newParams.maxHeight = "calc(100% - 1px)"; // On modal load maxHight is calculated. Since first resizing it just can be almost 100%.
				if (params.width !== undefined) {
					newParams.width = params.width;
				}
				if (params.height !== undefined) {
					newParams.height = params.height;
				}
				if (params.left !== undefined) {
					newParams.left = params.left;
				}
				if (params.top !== undefined) {
					newParams.top = params.top;
				}
				if (params.left !== undefined || params.top !== undefined) {
					newParams.position = 'absolute';
					newParams.marginTop = 0;
					newParams.marginBottom = 0;
				}
				$(modalDialog).css(newParams);
				switchModalScrollbarY(modalDialog, false);
			}
		}

		// Save in cookie columns width and table sorting. It's for modal and for listing flex grid.
		function saveGridParamsInCookie(cookieName, params) {
			var allParams = {};
			// get cookie params or create empty cookie
			if ($cookieStore.get(cookieName) !== undefined) {
				allParams = angular.copy($cookieStore.get(cookieName));
			} else {
				$cookieStore.put(cookieName, {});
			}
			// try to set params
			if (params.columns !== undefined) {
				allParams.columns = params.columns;
			}
			if (params.sortByColumn !== undefined) {
				allParams.sortByColumn = params.sortByColumn;
			}
			if (params.sortDirection !== undefined) {
				allParams.sortDirection = params.sortDirection;
			}
			$timeout(function() { // timeout is necessary to pass asynchro
				$cookieStore.put(cookieName, allParams);
			}, 0);
		}

		// Called after resizing flex grid column (in modal or in listing);
		function resizedColumn(args, cookieName) {
			var columns = args.panel.columns;
			var columnWidths = [];
			angular.forEach(columns, function(value, key) {
				columnWidths[value.index] = (value.width > 0) ? value.width : "*";
			});
			var paramsToSet = {
				columns: columnWidths,
			};
			self.saveGridParamsInCookie(cookieName, paramsToSet);
		}

		// Called after change sorting flex grid column (in modal or in listing);
		function sortedColumn(args, cookieName) {
			var columns = args.panel.columns;
			var found = false;
			angular.forEach(columns, function(value, key) {
				if (!found && value.currentSort !== null) {
					var paramsToSet = {
						sortByColumn: value.binding, // "binding" not "name" bacause i.e. for dimensionElementId binding="dimensionElementId" but name="index" (and it's correct!)
						sortDirection: (value.currentSort == "+") ? true : false,
					};
					self.saveGridParamsInCookie(cookieName, paramsToSet);
					found = true;
				}
			});
		}

		// align text in some columns based on cookie
		function alignColumns(flex) {
			if (flex !== undefined && flex !== null) {
				angular.forEach(flex.columns, function(value, key) {
					if (value.name == "index") {
						value.align = self.globalSettings.colIndexParams.align;
					} else if (value.name == "buttons") {
						value.align = self.globalSettings.colButtonsParams.align;
					}
				});
			}
		}

		// initialize sorting based on cookie
		function initializeSorting(ctx, currentCookie) {
			if (currentCookie !== undefined && currentCookie !== null) {
				if (currentCookie.sortByColumn !== undefined && currentCookie.sortDirection !== undefined) {
					var sortBy = new wijmo.collections.SortDescription(currentCookie.sortByColumn, currentCookie.sortDirection);
					ctx.data.sortDescriptions.push(sortBy);
					$timeout(function() { // timeout is necessary to pass asynchro
						ctx.data.refresh();
					}, 0);
				}
			}
		}

		// set minimum width for all flex grid columns
		// set column width based on cookie or global settings
		function initializeColumnsParameters(ctx, currentCookie) {
			if (ctx.flex !== undefined && ctx.flex !== null) {
				angular.forEach(ctx.flex.columns, function(value, key) {
					value.allowDragging = false;
					if (currentCookie !== undefined && currentCookie !== null && currentCookie.columns !== undefined && currentCookie.columns[value.index] !== undefined) {
						value.width = currentCookie.columns[value.index];
						if (value.minWidth === undefined) {
							value.minWidth = self.globalSettings.colParams.minWidth;
						}
					} else if (value.name == "index") {
						value.width = self.globalSettings.colIndexParams.width;
					} else if (value.name == "buttons") {
						// min-width is set in template - separetely for each view,
						// so if tehre is no cookie info, we can set width the same as minWidth
						value.width = value.minWidth;
					} else {
						value.width = "*";
					}
					$timeout(function() { // timeout is necessary to pass asynchro
						ctx.data.refresh();
					}, 0);
				});
			}
		}

		/**
		 * Returns element from collection which has the same Id (or other key) as our current element,
		 * I.e. in DataTypes if currentMeasureClass.id=0, we will get object {id: 0, name: "String"}.
		 * Current element must be reference (not copy) to one of the objects from collection.
		 * It's important, 'cause if it will be copy, Angular won't link current object with object in collection (and i.e. in dropdown list).
		 * @param  {Object} elements [list of elements which builds collection (i.e. dropdown list)]
		 * @param  {Integer} keyValue [key of current element]
		 * @param  {String} keyName [name of field in object - "id", "index", "name", "PK" or sth else]
		 * @return {Object}          [description]
		 */
		function findElementByKey(elements, keyValue, keyName, returnFirstIfNothingMatches) {
			var foundElement = null;
			if (elements === false) {
				return null;
			}
			if (typeof keyValue === "undefined") {
				return foundElement;
			}
			angular.forEach(elements, function(element) {
				if (element[keyName] != undefined && element[keyName] != null && element[keyName] == keyValue) {
					foundElement = element;
					return;
				}
			});
			if (foundElement === null && elements.length > 0 && returnFirstIfNothingMatches) {
				foundElement = elements[0];
			}
			return foundElement;
		}

		//------------------------- resizing flex grid ----------------------------------------

		// calc some elements height to resize flex grid
		var mainCtx = null;
		var cookieForListing = null;
		var fullHeightFlag = $(window).height();
		var toSubtract = 0;
		var additionalSubstract = 0;
		/**
		 * [initializeResizeFlexGrid description]
		 * @param  {[type]} flex               		flex (mainly $scope.ctx.flex from controller)
		 * @param  {[type]} additionalElements 		array with DOM elements, ie. [angular.element(".data-editor-filter"), angular.element(".data-editor-buttons")]
		 */
		function initializeResizeFlexGrid(ctx, currentCookie, additionalElements) {
			var timeout = 0;
			toSubtract = 0;
			additionalSubstract = 0;
			var mc = $("#main-content");
			var pa = $("#main-content .page");
			var ph = $("#main-content .page h1.text-center");
			toSubtract += (mc.outerHeight(true) - mc.height()); // calc margin/padding top/bottom and borders of #main-content
			toSubtract += (pa.outerHeight(true) - pa.height()); // calc margin/padding top/bottom and borders of .page.ng-scope
			toSubtract += (ph.outerHeight(true)); // get height, paddings, borders and margins of .page.ng-scope h1.text-center
			// If additional elements are set, wait some time to let them fill with request data and load completely to DOM (then we can calc their height). 
			if (typeof additionalElements != "undefined") {
				timeout = 1000;
			}
			$timeout(function() {
				if (typeof additionalElements != "undefined") {
					additionalSubstract = 0;
					angular.forEach(additionalElements, function(elem) {
						additionalSubstract += elem.outerHeight(true);
					});
				}
				mainCtx = ctx;
				// double click to open modal
				if (mainCtx.flex !== undefined) {
					mainCtx.flex.hostElement.addEventListener('dblclick', function(e) {
						$rootScope.$broadcast('SubMenuController:open');
					});
				}
				cookieForListing = currentCookie;
				fullHeightFlag = $(window).height();
				self.adjustScreen();
			}, timeout);
		}

		// Function is called after flex load or after page resize.
		function adjustScreen() {
			if (mainCtx.flex) {
				var fgrid = $(mainCtx.flex.hostElement); // $(".wj-flexgrid");
				var fgridSpaces = (fgrid.outerHeight(true) - fgrid.height()); // calc margin/padding top/bottom and borders of flexgrid
				fgrid.height("auto").css("overflow", "hidden");
				var newMaxHeight = fullHeightFlag - toSubtract - additionalSubstract - fgridSpaces - 10;
				fgrid.css("max-height", newMaxHeight);
				// align text in some columns based on cookie
				self.alignColumns(mainCtx.flex);
				// initialize sorting based on cookie
				self.initializeSorting(mainCtx, cookieForListing);
				// set minimum width for all flex grid columns
				// set column width based on cookie or global settings				
				self.initializeColumnsParameters(mainCtx, cookieForListing);
				$timeout(function() { // timeout is necessary to pass asynchro
					mainCtx.data.refresh();
				}, 1000);
			}
		}

		$(document).ready(function() {
			$(window).resize(function() {
				// move modal (if opened) if is outside browser window
				if ($modalStack.getTop() !== undefined) {
					var modal = $modalStack.getTop().value.modalDomEl; // curretnt modal on the top
					var modalDialog = modal.find(".modal-dialog");
					moveModal(modalDialog);
				}
				// resize flex grid on listing
				if (mainCtx && mainCtx.flex) {
					var actualFullHeight = $(window).height();
					if (fullHeightFlag != actualFullHeight) {
						fullHeightFlag = actualFullHeight;
						self.adjustScreen();
					}
				}
			});
		});

		//------------------------- end of resizing flex grid ----------------------------------------



		/************************************************** EVENTS *********************************************************************/



		// ask if reload page after change location (mainly if modal is open or sweet alert is open)
		$rootScope.$on("$locationChangeStart", function(event, next, current) {
			if ($(".modal").length > 0 && !confirm('Are you sure you want to change the page?')) {
				event.preventDefault();
			} else if ($(".showSweetAlert").length > 0 && !confirm('Are you sure you want to change the page?')) {
				event.preventDefault();
			} else {
				// try to click on cancel button in modal or sweet alert
				$timeout(function() {
					$.each($(".modal .modal-footer button"), function(value, key) {
						var text = $(key).text();
						if ($(key).attr("ng-click") == "close()" || text == "Close" || text == "close" || text == "Cancel" || text == "cancel") {
							$(key).click();
						}
					});
					$.each($(".showSweetAlert button"), function(value, key) {
						if ($(key).hasClass("cancel")) {
							$(key).click();
						}
					});
				}, 0);
			}
		});

		// manage main loader in all modals - used after "save" requests
		var modalCoverHtml = '<div class="frontend-cover modal-main-cover" style="display: none"><i class="fa fa-refresh fa-2x fa-spin"></i></div>';

		$rootScope.$on("modal:blockAllOperations", function() {
			if ($modalStack.getTop() !== undefined) {
				var modal = $modalStack.getTop().value.modalDomEl; // curretnt modal on the top
				if (modal.find(".modal-main-cover").length === 0) {
					$modalStack.getTop().value.keyboard = false; // block closing modal on ESC press
					modal.find(".modal-content").prepend(modalCoverHtml); // add loader templatee
					modal.find(".modal-main-cover").fadeIn();
					modal.find(".ui-resizable-handle").hide(); // prevent resizeing
				}
			}
		});

		$rootScope.$on("modal:unblockAllOperations", function() {
			if ($modalStack.getTop() !== undefined) {
				var modal = $modalStack.getTop().value.modalDomEl; // current modal on the top
				if (modal.find(".modal-main-cover").length > 0) {
					$modalStack.getTop().value.keyboard = true; // unblock closing modal on ESC press
					modal.find(".modal-content .modal-main-cover").remove(); // remove loader template - line below makes the same but with test fadeOut 
					// modal.find(".modal-content .modal-main-cover").fadeOut(function() { modal.find(".modal-content .modal-main-cover").remove() }); // TODO remove: TEST ONLY remove loader template
					modal.find(".ui-resizable-handle").show(); // allow resizeing
				}
			}
		});



		/************************************************** SpreadJS *********************************************************************/



		function getColumnIndexByName(c) {
			var num, ltr;
			if (c.length == 1) {
				num = alphabet.indexOf(c) + 1;
			} else {
				for (var i = 0; i < c.length; i++) {
					ltr = c.substr(i, 1);
					if (i === 0) {
						num = (alphabet.length * (alphabet.indexOf(ltr) + 1));
					} else {
						num += alphabet.indexOf(ltr) + 1;
					}
				}
			}
			return num - 1;
		}


		//Changes integer to excel-like column index starting with 1 which is equal to A.

		//http://tech-chec.blogspot.com/2009/03/convert-excel-column-letter-to-number.html (modified)
		function getColumnNameByIndex(n) {
			var letter;
			if (n > alphabet.length) {
				var x1 = n % alphabet.length;
				var x2 = ((n - x1) / alphabet.length);
				if (x1 === 0) {
					letter = alphabet.substr(x2 - 2, 1) + alphabet.substr(alphabet.length - 1, 1);
				} else {
					letter = alphabet.substr(x2 - 1, 1) + alphabet.substr(x1 - 1, 1);
				}
			} else {
				letter = alphabet.substr(n - 1, 1);
			}
			return letter;
		}
	}
})();