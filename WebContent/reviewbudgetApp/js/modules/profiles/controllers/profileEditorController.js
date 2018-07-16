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
		.module('app.profiles')
		.controller('ProfileEditorController', ProfileEditorController);

	/* @ngInject */
	function ProfileEditorController($scope, $rootScope, ProfileService, FormListService) {
		$scope.formLists = FormListService.getFormList();
		$scope.currentForm = null;

		if ($scope.profile) {
			$scope.currentForm = FormListService.findFormById($scope.profile.formId);
		} else if ($scope.formLists.length > 0) {
			$scope.currentForm = $scope.formLists[0];
		}

		$scope.profileDim1 = {
			dimStructureId: $scope.profile.structureId0,
			dimName: $scope.profile.elementLabel0,
			dimId: $scope.profile.structureElementId0
		};

		$scope.profileDim2 = {
			dimStructureId: $scope.profile.structureId1,
			dimName: $scope.profile.elementLabel1,
			dimId: $scope.profile.structureElementId1
		};

		$scope.profileDim3 = {
			dimName: $scope.profile.dataType
		};

		$scope.change = function(params) {
			$scope.currentForm = params;
		};

		$scope.selectDimension = function(dimensionNumber) {
			$rootScope.$broadcast('DimensionController:showDimension', {
				placeToSendTheResult: 'ProfilesController:openManagerCreateReciveDimensions',
				dimensionNumber: dimensionNumber
			});
		};

		$scope.selectDataType = function() {
			$rootScope.$broadcast('DimensionController:showDataTypeTreeView', {
				placeToSendTheResult: 'ProfilesController:openManagerCreateReciveDimensions'
			});
		};

		$scope.setDimentionsForProfile = function() {
			if ($scope.profileDim1 !== null) {
				$scope.profile.structureId0 = $scope.profileDim1.dimStructureId;
				$scope.profile.structureElementId0 = $scope.profileDim1.dimId;
				$scope.profile.elementLabel0 = $scope.profileDim1.dimName;
			}

			if ($scope.profileDim2 !== null) {
				$scope.profile.structureId1 = $scope.profileDim2.dimStructureId;
				$scope.profile.structureElementId1 = $scope.profileDim2.dimId;
				$scope.profile.elementLabel1 = $scope.profileDim2.dimName;
			}

			if ($scope.profileDim3 !== null) {
				$scope.profile.dataType = $scope.profileDim3.dimName;
			}
		};

		$scope.ok = function() {
			$scope.setDimentionsForProfile();
			$scope.profile.formId = $scope.currentForm.formId;

			switch ($scope.mode) {
				case 'create':
					ProfileService.create($scope.profile).success(function(data) {
						onSuccess(data);
					});
					break;
				case 'copy':
					ProfileService.copy($scope.profile).success(function(data) {
						onSuccess(data);
					});
					break;
				case 'edit':
					ProfileService.update($scope.profile).success(function(data) {
						onSuccess(data);
					});
					break;
			}
		};

		var onSuccess = function(data) {
			$rootScope.profiles = data;
			ProfileService.setProfiles(data);

			$scope.cancel();
		};

		$scope.cancel = function() {
			$rootScope.$broadcast('ProfileEditor:close');
		};

		$scope.resetDims = function() {
			$scope.profileDim1 = {
				dimStructureId: 0,
				dimName: null,
				dimId: 0
			};

			$scope.profileDim2 = {
				dimStructureId: 0,
				dimName: null,
				dimId: 0
			};

			$scope.profileDim3 = {
				dimName: null,
			};
		};

		$scope.$on('ProfilesController:openManagerCreateReciveDimensions', function(event, args) {
			switch (args.recivedDimension) {
				case 1:
					$scope.profileDim1 = args.dim;
					break;

				case 2:
					$scope.profileDim2 = args.dim;
					break;

				case 3:
					$scope.profileDim3 = args.dim;
					break;
			}
		});
	}

})();