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
        .module('coreApp.editor')
        .service('FormatService', FormatService);

    function FormatService() {
        var self = this;

        var formats = {
            "General": ['General'],
            "Number": ['0', '0.00', '#,##0', '#,##0.00', '_(* #,##0_);_(* (#,##0);_(* "-"_);_(@_)', '_(* #,##0.00_);_(* (#,##0.00);_(* "-"??_);_(@_)', '#,##0;(#,##0);.', '#,##0.0;(#,##0.0);.', '###0;(###0);', '#,##0 ;(#,##0) ;', '#,##0.0 ;(#,##0.0) ;', '##,##0.00 ;(##,##0.00) ;.', '#,##0.00;(#,##0.00);', '###0;(###0) ;', '###0;(###0);.', '##,##0.00 ;(##,##0.00) ;', '#,##0.00;(#,##0.00);.', '#,##0.00;(#,##0.00); ', '#,##0;[Red](#,##0);.', '#,##0;(#,##0);-', '#,##0;[Red](#,##0);-', '#,##0.0', '#,##0.0;[Red](#,##0.0);.', ';;', '"£"#,##0;-"£"#,##0', '"£"#,##0;[Red]-"£"#,##0', '"£"#,##0.00;-"£"#,##0.00', '"£"#,##0.00;[Red]-"£"#,##0.00', '_-* #,##0_-;-* #,##0_-;_-* "-"_-;_-@_-', '_-"£"* #,##0_-;-"£"* #,##0_-;_-"£"* "-"_-;_-@_-', '_-* #,##0.00_-;-* #,##0.00_-;_-* "-"??_-;_-@_-', '_-"£"* #,##0.00_-;-"£"* #,##0.00_-;_-"£"* "-"??_-;_-@_-', '"£"#,##0_);("£"#,##0)', '"£"#,##0_);[Red]("£"#,##0)', '"£"#,##0.00_);("£"#,##0.00)', '"£"#,##0.00_);[Red]("£"#,##0.00)', '_("£"* #,##0_);_("£"* (#,##0);_("£"* "-"_);_(@_)', '_("£"* #,##0.00_);_("£"* (#,##0.00);_("£"* "-"??_);_(@_)'],
            "Currency": ['$#,##0_);($#,##0)', '$#,##0_);[Red]($#,##0)', '$#,##0.00_);($#,##0.00)', '$#,##0.00_);[Red]($#,##0.00)', '#,##0_);(#,##0)', '#,##0_);[Red](#,##0)', '#,##0.00_);(#,##0.00)', '#,##0.00_);[Red](#,##0.00)', '_($* #,##0_);_($* (#,##0);_($* "-"_);_(@_)', '_($* #,##0.00_);_($* (#,##0.00);_($* "-"??_);_(@_)'],
            "Date": ['dd-MMM-yy', 'm/d/yyyy', 'd-mmm-yy', 'd-mmm', 'mmm-yy', 'd mmmm yyyy', 'd mmm yy', '[$-809]dd mmmm yyyy', 'dd-mmm-yyyy'],
            "Time": ['h:mm AM/PM', 'h:mm:ss AM/PM', 'h:mm', 'h:mm:ss', 'm/d/yyyy h:mm', 'mm:ss', '[h]:mm:ss', 'mm:ss.0'],
            "Percent": ['0%', '0.00%', '#,##0.0%;(#,##0.0)%; ', '#,##0%;(#,##0)%;0', '#,##0%;(#,##0)%;0%'],
            "Fraction": ['# ?/?', '# ??/??'],
            "Scientific": ['0.00E+00', '##0.0E+0'],
            "Text": ['@']
        };
        var categories = [];

        self.getCategoryForFormat = getCategoryForFormat;
        self.getFormatsForCategory = getFormatsForCategory;
        self.getFormats = getFormats;
        self.getCategories = getCategories;

        activate();

        /////////////////////////////////////////////////////////////

        function activate() {
            manageCategories();
            manageGeneralItems();
        }

        function manageCategories() {
            categories.length = 0;
            angular.forEach(formats, function(formats, category) {
                categories.push(category);
            });
        }

        function manageGeneralItems() {
            angular.forEach(formats, function(format, category) {
                if (category != "General") {
                    angular.forEach(format, function(item) {
                        formats.General.push(item);
                    });
                }
            });
        }

        function getCategoryForFormat(format) {
            var isFormatFounded = false;
            var foundedCategory = (categories.length > 0) ? categories[0] : null;
            angular.forEach(formats, function(formatTmp, category) {
                if (category != "General") {
                    angular.forEach(formatTmp, function(item) {
                        if (item == format) {
                            foundedCategory = category;
                            isFormatFounded = true;
                            return;
                        }
                    });
                }
                if (isFormatFounded) {
                    return;
                }
            });
            return foundedCategory;
        }

        function getFormatsForCategory(category) {
            return formats[category];
        }

        function getFormats() {
            return formats;
        }

        function getCategories() {
            return categories;
        }
    }

})();