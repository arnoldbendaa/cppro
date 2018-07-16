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
		.module('app.dimensions')
		.directive('dimensionTreeView', dimensionTreeView);

	/* @ngInject */
	function dimensionTreeView($compile, dimensionFactory) {

		var directive = {
			restrict: 'A',
			link: link
		};

		return directive;

		function link(scope, element, attrs) {
			//tree id
			var treeId = attrs.treeId;

			//tree model
			var treeModel = attrs.treeModel;

			//node id
			var nodeId = attrs.nodeId || 'element.id';

			//node name
			var nodeName = attrs.nodeName || 'element.name';

			//node description
			var nodeDescription = attrs.nodeDescription || 'element.description';

			//children
			var nodeChildren = attrs.nodeChildren || 'element.children';

			//selectable
			var selectable = attrs.selectable || 'element.selectable';

			//leaf
			var leaf = attrs.nodeLeaf || false;

			//tree template
			// var template =
			//     '<ul>' +
			//     '<li data-ng-repeat="node in ' + treeModel + '" class="treeNode">' +
			//     '<i class="collapsed" data-ng-show="!node.loading && (node.collapsed || !node.' + nodeChildren + '.length) && !node.' + leaf + '" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
			//     '<i class="expanded" data-ng-show="!node.loading && !node.collapsed && node.' + nodeChildren + '.length && !node.' + leaf + '" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
			//     '<i class="normal" data-ng-show="!node.loading && !node.selected && node.' + leaf + '" data-ng-click="' + treeId + '.selectnodeName(node)"></i> ' +
			//     '<i class="loading" data-ng-show="node.selected!=\'selected\' && node.loading"></i> ' +
			//     '<i class="check" data-ng-show="!node.loading && node.selected==\'selected\' && node.' + leaf + '" data-ng-click="' + treeId + '.selectnodeName(node)"></i> ' +
			//     '<span data-ng-class="node.selected" data-ng-click="' + treeId + '.selectnodeName(node)">{{node.' + nodeName + '}} - {{node.' + nodeDescription + '}}</span>' +
			//     '<div data-ng-hide="node.collapsed" data-dimension-tree-view data-tree-id="' + treeId + '" data-tree-model="node.' + nodeChildren + '" data-node-id=' + nodeId + ' data-node-name=' + nodeName + ' data-node-description=' + nodeDescription + ' data-node-children=' + nodeChildren + '  data-node-leaf="' + leaf + '"></div>' +
			//     '</li>' +
			//     '</ul>';

			var template =
				'<ul>' +
				'<li data-ng-repeat="node in ' + treeModel + '" class="treeNode">' +
				'<i class="collapsed" data-ng-show="!node.loading &&  node.' + selectable + ' && (node.collapsed || !node.' + nodeChildren + '.length) && !node.' + leaf + '" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
				'<i class="expanded" data-ng-show="!node.loading && !node.collapsed && node.' + nodeChildren + '.length && !node.' + leaf + '" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
				'<i class="normal" data-ng-show="!node.loading && !node.selected && node.' + leaf + '" data-ng-click="' + treeId + '.selectnodeName(node)"></i> ' +
				'<i class="loading" data-ng-show="node.selected!=\'selected\' && node.loading"></i> ' +
				'<i class="check" data-ng-show="!node.loading && node.selected==\'selected\' && node.' + leaf + '" data-ng-click="' + treeId + '.selectnodeName(node)"></i> ' +
				'<span data-ng-class="node.selected" data-ng-click="' + treeId + '.selectnodeName(node)">{{node.' + nodeName + '}} - {{node.' + nodeDescription + '}}</span>' +
				'<div data-ng-hide="node.collapsed" data-dimension-tree-view data-tree-id="' + treeId + '" data-tree-model="node.' + nodeChildren + '" data-node-id=' + nodeId + ' data-node-name=' + nodeName + ' data-node-description=' + nodeDescription + ' data-node-children=' + nodeChildren + '  data-node-leaf="' + leaf + '"></div>' +
				'</li>' +
				'</ul>';

			//check tree id, tree model
			if (treeId && treeModel) {

				//create tree object if not exists
				scope[treeId] = scope[treeId] || {};

				//if node head clicks,
				scope[treeId].selectNodeHead = scope[treeId].selectNodeHead || function(selectedNode) {
					if (!selectedNode.element.selectable)
						return;

					var dimensionId = selectedNode.element.id;
					var leaf = selectedNode.element.leaf || false;
					var hasChildren = selectedNode.children.length || false;
					var structureId = selectedNode.element.structureId || false;

					//get children from service if node isn`t leaf, hasn`t children and has structureId
					if (!leaf && !hasChildren && structureId) {
						selectedNode.loading = true;
						var dimensionType = '';
						if (treeModel == 'accountTreeModel') {
							dimensionType = 'ACCOUNT';
						} else if (treeModel == 'bussinessTreeModel') {
							dimensionType = 'BUSSINESS';
						} else if (treeModel == 'calendarTreeModel') {
							dimensionType = 'CALENDAR';
						}
						dimensionFactory.getDimensionChildren(dimensionType, dimensionId, structureId)
							.success(function(data) {
								selectedNode.children = data;
								//expand node
								selectedNode.loading = undefined;
								selectedNode.collapsed = false;
							});
					} else {
						//Collapse or Expand
						selectedNode.collapsed = !selectedNode.collapsed;
					}
				};

				//if node label clicks,
				scope[treeId].selectnodeName = scope[treeId].selectnodeName || function(selectedNode) {
					if (!selectedNode.element.fullRights)
						return;

					if (selectedNode.element.selectable) {
						//remove highlight from previous node
						if (scope[treeId].currentNode && scope[treeId].currentNode.selected) {
							scope[treeId].currentNode.selected = undefined;
						}

						//select node only if it is different than the current or current is not selected
						if (scope[treeId].currentNode != selectedNode) {
							//set highlight to selected node
							selectedNode.selected = 'selected';
							scope.selectedElement.id = selectedNode.element.id;
							scope.selectedElement.structureId = selectedNode.element.structureId;
							scope.selectedElement.name = selectedNode.element.name;
							scope.selectedElement.description = selectedNode.element.description;
							scope.selectedElement.leaf = selectedNode.element.leaf;
							scope.selectedElement.selectable = selectedNode.element.selectable;

							//set currentNode
							scope[treeId].currentNode = selectedNode;
						} else {
							//unset currentNode
							scope[treeId].currentNode = undefined;
						}
					}
				};

				//Rendering template.
				element.empty();
				element.append($compile(template)(scope));

				setTimeout(function() {
					$('.selected').click();
				}, 100);
			}
		}
	}
})();