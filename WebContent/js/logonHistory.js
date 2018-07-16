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
dojo.require("dojo.date");
dojo.require("dojo.date.locale");
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Pagination");
dojo.require("dojox.grid.enhanced.plugins.Filter");
dojo.require("dojo.data.ItemFileWriteStore");

var logonLayout = [
    {'name': 'User Id', 'field': 'UserName', 'width': 'auto'},
    {'name': 'When', 'field': 'EventDate', 'width': '300px'},
    {'name': 'Event Type', 'field': 'EventType', 'width': '300px', 'formatter':formatEvent}
];

var logonGrid;

function init()
{
    var store = new dojo.data.ItemFileWriteStore({url: getStoreURL(), urlPreventCache:true, clearOnClose:true});
    var grid = new dojox.grid.EnhancedGrid({
               store: store,
               structure: logonLayout,
               editable: false,
               autoHeight: false,
               autoWidth: false,
               plugins: {
                   pagination: {
                       pageSizes: ["25", "50", "100", "200"],
                       description: true,
                       sizeSwitch: false,
                       pageStepper: true,
                       gotoButton: true,
                       /*page step to be displayed*/
                       maxPageStep: 4,
                       /*position of the pagination bar*/
                       position: "top"
                   },
                filter: {
                        // Show the closeFilterbarButton at the filter bar
                        closeFilterbarButton: false,
                        // Set the maximum rule count to 5
                        ruleCount: 3
                        // Set the name of the items
                        //itemsName: "songs"
                    }
               }
           });

    /*append the new grid to the div*/
    dojo.byId("logonGrid").appendChild(grid.domNode);

    /*Call startup() to render the grid*/
    grid.startup();

    logonGrid = grid;
}

function formatEvent(value)
{
    if (value == -2)
    {
        return 'Timed Out';
    }
    else if (value == -1)
    {
        return 'Logged Off';
    }
    else if (value == 1)
    {
        return 'Successful';
    }
    else if (value == 2)
    {
        return 'Failed';
    }
}