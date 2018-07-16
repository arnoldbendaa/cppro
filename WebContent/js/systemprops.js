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
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Pagination");
dojo.require("dojox.grid.enhanced.plugins.Filter");
dojo.require("dojo.data.ItemFileWriteStore");

var propsLayout = [
    {'name': 'Property', 'field': 'id', 'width': '300px'},
    {'name': 'Value', 'field': 'Value', 'width': '300px'},
    {'name': 'Description', 'field': 'description', 'width': 'auto'}
];

var propertyGrid;

function init()
{
    var store = new dojo.data.ItemFileWriteStore({url: getStoreURL(), urlPreventCache:true, clearOnClose:true});
    var grid = new dojox.grid.EnhancedGrid({
               store: store,
               structure: propsLayout,
               editable: false,
               autoHeight: false,
               autoWidth: false,
               plugins: {
                   pagination: {
                       pageSizes: ["25", "50"],
                       description: true,
                       sizeSwitch: false,
                       pageStepper: true,
                       gotoButton: false,
                       /*page step to be displayed*/
                       maxPageStep: 2,
                       /*position of the pagination bar*/
                       position: "top"
                   },
                filter: {
                        // Show the closeFilterbarButton at the filter bar
                        closeFilterbarButton: false,
                        // Set the maximum rule count to 5
                        ruleCount: 5
                        // Set the name of the items
                        //itemsName: "songs"
                    }
               }
           });

    dojo.connect(grid, "onRowDblClick", "editProp");
    /*append the new grid to the div*/
    dojo.byId("propsGrid").appendChild(grid.domNode);

    /*Call startup() to render the grid*/
    grid.startup();

    propertyGrid = grid;
}

function editProp(evt)
{
    idx = evt.rowIndex;
    form = dojo.byId("editForm");
    dojo.byId("editProperty").value = propertyGrid.store.getValue(propertyGrid.getItem(idx), "id");
    form.submit();
}