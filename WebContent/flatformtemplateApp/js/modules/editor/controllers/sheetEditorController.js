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
		.module('flatFormTemplateApp.editor')
		.controller('SheetEditorController', SheetEditorController);

	/* @ngInject */
	function SheetEditorController($scope, $modal, $timeout, CoreCommonsService, TemplatesService) {
		function manageImportantSheetData() {
			var sheets = [];
			for (var i = 0; i < $scope.spread.getSheetCount(); i++) {
				var sheet = $scope.spread.getSheet(i);
				var reducedSheet = {
					name: sheet.name(),
					originalName: sheet.name(),
					isVisible: sheet.visible()
				};
				sheets.push(reducedSheet);
			}
			return sheets;
		};

		$scope.manageSheetVisibility = function() {
			openModal();
		};

		function openModal() {
			var cookieName = "flatFormTemplate_modal_sheetEditor";
			var sheets = manageImportantSheetData();
			var modalInstance = $modal.open({
				template: '<sheet-editor modal="modal" sheets="sheets" cookie-name="cookieName"></sheet-editor>',
				windowClass: 'softpro-modals',
				backdrop: 'static',
				size: 'lg',
				controller: ['$scope', '$modalInstance',
					function($scope, $modalInstance) {
						$scope.modal = $modalInstance;
						$scope.sheets = sheets;
						$scope.cookieName = cookieName;
					}
				]
			});

			modalInstance.result.then(function(sheets) {
				saveSheets(sheets);
			}, function() {

			});
		};

		function saveSheets(sheets) {
			var spread = $scope.spread;
			spread.suspendPaint ();

			var originalSheet;
			var name;
			var sheet;

            var template = TemplatesService.getTemplate();

			for (var i = 0; i < spread.getSheetCount(); i++) {
				originalSheet = spread.getSheet(i);
				name = originalSheet.name();

				sheet = CoreCommonsService.findElementByKey(sheets, name, 'originalName');
				if (sheet === null) {
                    // remove worksheet properties from template
                    var worksheet = CoreCommonsService.findElementByKey(template.workbook.worksheets, originalSheet.name(), 'name');
                    if (worksheet !== null) {
                        TemplatesService.deleteWorksheetProperties(template.workbook.worksheets, worksheet);
                    }
                    // remove sheet from spread
					spread.removeSheet(i);
					i--;
				}
			}

			for (i = 0; i < spread.getSheetCount(); i++) {
				originalSheet = spread.getSheet(i);
				name = originalSheet.name();
				sheet = CoreCommonsService.findElementByKey(sheets, name, 'originalName');
				if (sheet !== null) {
                    // update name in template worksheet properties
                    var worksheet = CoreCommonsService.findElementByKey(template.workbook.worksheets, originalSheet.name(), 'name');
                    if (worksheet !== null) {
                        TemplatesService.updateWorksheetPropertyName(worksheet, sheet.name);
                    }
                    // update name in spread sheet
					originalSheet.visible(sheet.isVisible);
					originalSheet.name(sheet.name);
				}
			}

			for (i = 0; i < sheets.length; i++) {
				sheet = sheets[i];
				if (sheet.originalName === null) {
                    // insert worksheet properties in template worksheets
                    TemplatesService.insertWorksheetProperties(template.workbook.worksheets, sheet.name);
                    // insert spread sheet
					var newSheet = new GC.Spread.Sheets.Worksheet(sheet.name);
					newSheet.options.frozenlineColor = ("rgba(255, 255, 255, 0)"); // hide frozen line		
					spread.addSheet(i, newSheet);
				}
			}

			for (i = 0; i < spread.getSheetCount(); i++) {
				originalSheet = spread.getSheet(i);

				sheet = CoreCommonsService.findElementByKey(sheets, originalSheet.name(), 'originalName');
				var index = sheets.indexOf(sheet);
				if (index != -1) {
					var originalSheets = spread.sheets;
					originalSheets.splice(i, 1);
					originalSheets.splice(index, 0, originalSheet);
				}
			}

			var isActiveSheetChoosed = false;
			for (i = 0; i < spread.getSheetCount(); i++) {
				sheet = spread.getSheet(i);
				if (isActiveSheetChoosed === false && sheet.visible()) {
					isActiveSheetChoosed = true;
					spread.setActiveSheet(sheet.name());
					spread.startSheetIndex(i);
					break;
				}
			}
			spread.resumePaint();
		};
		
		$scope.$on('SpreadSheetController:SheetTabClick', function(e, info){
		    console.log(e, info)
		    if (info.sheetName == null) {
		        //dodajemy do workbooka nowego sheeta
	              // New button Clicked, new sheet added.
                // Now there is no event to capture Adding new sheet - http://wijmo.com/topic/spreadjs-capturing-the-add-sheets-event/
                $timeout(function() {
                    console.log("nowy name",  $scope.spread.getActiveSheet().name());
                    
                    var template = TemplatesService.getTemplate();
                    
                    TemplatesService.insertWorksheetProperties(template.workbook.worksheets, $scope.spread.getActiveSheet().name());
                }, 1000);
                // tell CoreCommonsService to ask if reload page (before trying closing)
                CoreCommonsService.askIfReload = true;
		    }
		});
	}

})();