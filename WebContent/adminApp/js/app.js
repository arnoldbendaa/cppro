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
var adminApp = angular.module('adminApp', [
    'angular-loading-bar',
    'ngFileUpload',
    'coreApp',
    'adminApp.commons',
    'adminApp.menu',
    'adminApp.homePage',
    'adminApp.admin',
    'adminApp.model',
    'adminApp.budgetCycle',
    'adminApp.dataEditor',
    'adminApp.dimension',
    'adminApp.recalculateBatch',
    'adminApp.notesPage',
    'adminApp.systemPropertiesPage',
    'adminApp.loggedHistoryPage',
    'adminApp.loggedUsersPage',
    'adminApp.dataTypesPage',
    'adminApp.rolesPage',
    'adminApp.usersPage',
    'adminApp.taskViewerPage',
    'adminApp.financeCubesPage',
    'adminApp.flatFormsPage',
    'adminApp.slideShow',
    'adminApp.budgetInstructionsPage',
    'adminApp.externalSystem',
    'adminApp.modelMappings',
    'adminApp.authentication',
    'adminApp.hierarchiesPage',
    'adminApp.import',
    'adminApp.profilesPage',
    'adminApp.changeManagement',
    'adminApp.report',
    'adminApp.modelUserSecurity',
    'adminApp.userModelSecurity',
    'adminApp.financeCubeFormulaPage',
    'adminApp.dashboardPage',
	'gcspreadsheets',


]);