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
// configure our routes
adminApp.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: $BASE_TEMPLATE_PATH + 'home/views/home.html'
        })
        .when('/dashboard/hierarchy/', {
            templateUrl: (($ROLES.indexOf("Dashboard Hierarchy Open") > -1) || ($ROLES.indexOf("Dashboard Hierarchy Edit") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dashboard/views/hierarchyForm.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/dashboard/freeform/', {
            templateUrl: (($ROLES.indexOf("Dashboard Free Form Open") > -1) || ($ROLES.indexOf("Dashboard Free Form Edit") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dashboard/views/freeForm.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/admin/database/', {
            templateUrl: (($ROLES.indexOf("Database") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'admin/views/database.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/admin/task-group/', {
            templateUrl: (($ROLES.indexOf("Database") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'admin/views/taskGroup.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/batch-recalculate/', {
            templateUrl: (($ROLES.indexOf("Batch Recalculate") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'recalculateBatches/views/recalculateBatch.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/budget-cycles/', {
            templateUrl: (($ROLES.indexOf("Budget Cycles") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'budgetCycles/views/budgetCycles.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/data-editor/', {
            templateUrl: (($ROLES.indexOf("Data Editor") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dataEditor/views/dataEditor.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/notes/', {
            templateUrl: (($ROLES.indexOf("Notes") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'notes/views/notes.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/system-properties/', {
            templateUrl: (($ROLES.indexOf("System Properties") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'systemProperties/views/systemProperties.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/user/logged-in-users/', {
            templateUrl: (($ROLES.indexOf("Logged in Users") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'loggedUsers/views/loggedUsers.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/user/logged-history/', {
            templateUrl: (($ROLES.indexOf("Login History") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'loggedHistory/views/loggedHistory.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/data-types/', {
            templateUrl: (($ROLES.indexOf("Data Types") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dataTypes/views/dataTypes.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/xml-forms/flat-forms/', {
            templateUrl: (($ROLES.indexOf("Flat Form Editor") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'flatforms/views/flatForms.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/profiles/web/', {
            templateUrl: (($ROLES.indexOf("Web Profiles") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'profiles/views/webProfiles.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/profiles/mobile/', {
            templateUrl: (($ROLES.indexOf("Mobile Profiles") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'profiles/views/mobileProfiles.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/monitors/task/', {
            templateUrl: (($ROLES.indexOf("Task") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'monitors/views/taskViewer.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/finance-cubes/', {
            templateUrl: (($ROLES.indexOf("Finance Cubes") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'financeCubes/views/financeCubes.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/finance-cube-formula/', {
            templateUrl: (($ROLES.indexOf("Finance Forms") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'financeCubeFormula/views/financeCubeFormula.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/user/roles/', {
            templateUrl: (($ROLES.indexOf("Roles") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'roles/views/roles.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/user/users/', {
            templateUrl: (($ROLES.indexOf("All Users") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'users/views/users.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/dimensions/account/', {
            templateUrl: (($ROLES.indexOf("Account") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dimensions/views/accounts.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/dimensions/business/', {
            templateUrl: (($ROLES.indexOf("Business") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dimensions/views/businesses.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/dimensions/calendar/', {
            templateUrl: (($ROLES.indexOf("Calendar") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'dimensions/views/calendars.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/models/', {
            templateUrl: (($ROLES.indexOf("Models") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'models/views/models.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/budget-instructions/', {
            templateUrl: (($ROLES.indexOf("Budget Instructions") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'budgetInstructions/views/budgetInstructions.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/input-output/external-systems/', {
            templateUrl: (($ROLES.indexOf("External Systems") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'externalSystems/views/externalSystems.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/input-output/model-mappings/', {
            templateUrl: (($ROLES.indexOf("Model Mappings") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'modelMappings/views/modelMappings.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/security/authentication/', {
            templateUrl: (($ROLES.indexOf("Authentication") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'authentications/views/authentications.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/hierarchies/account/', {
            templateUrl: (($ROLES.indexOf("Hierarchies Account") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'hierarchies/views/accounts.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/hierarchies/business/', {
            templateUrl: (($ROLES.indexOf("Hierarchies Business") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'hierarchies/views/business.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/input-output/import/', {
            templateUrl: (($ROLES.indexOf("Import") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'importtasks/views/import.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/input-output/change-management/', {
            templateUrl: (($ROLES.indexOf("Change Management") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'changeManagement/views/changeManagements.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/report/internal-user-destinations/', {
            templateUrl: (($ROLES.indexOf("Internal Users Destinations") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'reports/views/internalDestinations.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/report/external-user-destinations/', {
            templateUrl: (($ROLES.indexOf("External User Destinations") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'reports/views/externalDestinations.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/security/responsibility-areas/models-users/', {
            templateUrl: (($ROLES.indexOf("Model/Users") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'modelUserSecurity/views/modelUserSecurity.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/security/responsibility-areas/users-models/', {
            templateUrl: (($ROLES.indexOf("User/Models") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'userModelSecurity/views/userModelSecurity.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })
        .when('/xml-forms/slide-show',{
        	templateUrl: (($ROLES.indexOf("xml-forms/slide-show") > -1) || ($ROLES.indexOf("SystemAdministrator") > -1)) ? $BASE_TEMPLATE_PATH + 'slideShow/views/slideShow.html' : $BASE_TEMPLATE_PATH + 'error/accessRestrict.html'
        })

        .otherwise({
            templateUrl: $BASE_TEMPLATE_PATH + 'error/views/pageNotFound.html'
        });
});