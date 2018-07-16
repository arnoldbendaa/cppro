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
adminApp.filter('keyFilter', function() {
    return function(collection, id, key) {
        if (id == null || id == undefined || key == null) {
            return collection;
        }
        var keys = key.split('.');
        var filtered = [];
        angular.forEach(collection, function(element) {
            if (keys.length == 1 && element[keys[0]] == id) {
                filtered.push(element);
            }
            if (keys.length == 2 && element[keys[0]][keys[1]] == id) {
                filtered.push(element);
            }
            if (keys.length == 3 && element[keys[0]][keys[1]][keys[2]] == id) {
                filtered.push(element);
            }
        });
        return filtered;
    };
});
adminApp.filter('byMainWordFilter', ['$rootScope',
    function($rootScope) {
        function manageTerms(item, terms) {
            var termFound = false;
            for (var key in item) {
                var value = item[key];
                if (angular.isString(value) && value.toUpperCase().indexOf(terms) > -1) {
                    termFound = true;
                    break;
                }
                if (angular.isNumber(value)) {
                    var valueTmp = ""+value;
                    termFound = valueTmp.toUpperCase().indexOf(terms) > -1;
                    if (termFound)
                        break;
                }
                if (angular.isObject(value)) {
                    termFound = manageTerms(value, terms);
                    if (termFound)
                        break;
                }
            }
            return termFound;
        }

        return function(item) {
            var f = $rootScope.filter.byWord;
            if (f && item) {
                // split string into terms to enable multi-field searches such as 'us gadget red'
                var terms = f.toUpperCase().split(' ');

                // look for any term in any string field
                for (var i = 0; i < terms.length; i++) {
                    var termFound = manageTerms(item, terms[i]);

                    // fail if any of the terms is not found
                    if (!termFound) {
                        return false;
                    }
                }
            }
            // include item in view
            return true;
        };
    }
])
adminApp.filter('byModelFilter', ['$rootScope',
    function($rootScope) {
        return function(item) {
            if (item == null || !angular.isDefined(item)) {
                return false;
            }
            var cModel = $rootScope.currentModel;
            if (cModel == null || !angular.isDefined(cModel)) {
                return true;
            }
            if (angular.isDefined(item.model))
                return item.model.modelId == cModel.modelId;
            if (angular.isDefined(item.modelId))
                return item.modelId == cModel.modelId;
            return true;
        };
    }
])
adminApp.filter('byFinanceCubeFilter', ['$rootScope',
    function($rootScope) {
        return function(item) {
            if (item == null || !angular.isDefined(item)) {
                return false;
            }
            var cModel = $rootScope.currentModel;
            if (cModel == null || !angular.isDefined(cModel)) {
                return true;
            }
            if (angular.isDefined(item.financeCube))
                return item.financeCube.financeCubeId == cModel.financeCubeId;
            if (angular.isDefined(item.financeCubeId))
                return item.financeCubeId == cModel.financeCubeId;
            return true;
        };
    }
])