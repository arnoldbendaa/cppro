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
adminApp.controller('MainController', ['$rootScope', '$scope', 'ModelService', 'ContextMenuService',
    function($rootScope, $scope, modelService, contextMenuService) {
        var getCurrentModel = function(models) {
            if (models && models.length > 0) {
                return models[0];
            }
            return null;
        };

        $scope.selectCurrentModel = function(model) {
            $scope.currentModel = model;
            $rootScope.currentModel = $scope.currentModel;
        };

        $scope.models = modelService.getFinanceCubeModels();
        $rootScope.filter = {
                byWord : ""
        };
        $scope.onFilterWordChange = function(word) {
            $rootScope.filter.byWord = word;
        };
        
//        contextMenuService.initialize($('#table'));
//        contextMenuService.updateActions('Details');
        
        $scope.onShowHandler = function(e) {
            // console.log("djaslkdjlaksjdalksdklaskl")
//        if ($(e.target).attr("id") == "spreadsheet_tabStrip") {
//            return false;
//        }
//        var offset = spreadElement.offset();
//        var x = e.pageX - offset.left;
//        var y = e.pageY - offset.top;
//
//        var sheet = $scope.spread.getActiveSheet();
//        sheet.isPaintSuspended(true);
//        var target = sheet.hitTest(x, y); // current selected cell (could be cell in headers viewport)
//
//        var inRowRange = false;
//        var inColumnRange = false;
//
//        var selections = sheet.getSelections();
//        for (var i = 0; i < selections.length; i++) {
//            var selection = selections[i];
//
//            inRowRange = (target.row >= selection.row && target.row < selection.row + selection.rowCount);
//            inColumnRange = (target.col >= selection.col && target.col < selection.col + selection.colCount);
//        }
//
//        if (inRowRange == false || inColumnRange == false) {
//            sheet.setActiveCell(target.row, target.col);
//        }
//        sheet.isPaintSuspended(false);
        return true;
    }
    }
]);