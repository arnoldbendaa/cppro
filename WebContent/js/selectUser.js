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
dojo.require("dojox.grid._CheckBoxSelector");

var separator = "; ";

function userObject(rowId, userId, name, emailaddress)
{
    this.rowId = rowId;
    this.userId = userId;
    this.name = name;
    if(emailaddress != null)
        this.emailAddress = emailaddress;
    else
        this.emailAddress = ' ';
}
userObject.prototype.toString = userObjectToString;

function userObjectToString()
{
    return this.rowId;
}

var userLayout = [
    {
        type: "dojox.grid._CheckBoxSelector"
    },
    [
        {'name': 'Id', 'field': 'id', 'width': '250px'},
        {'name': 'Name', 'field': 'FullName', 'width': 'auto'},
        {'name': 'Mailable', 'field': 'EMailAddress', 'width': '50px', 'formatter':formatMailable}
    ]
];

var userGrid;

function formatMailable(value)
{
    if(value != null && value.length > 0)
        return "Y";
    else
        return "";
}

function init()
{
    var store = new dojo.data.ItemFileWriteStore({url: getStoreURL(), urlPreventCache:true, clearOnClose:true});
    var grid = new dojox.grid.EnhancedGrid({
               store: store,
               structure: userLayout,
               editable: false,
               autoHeight: false,
               autoWidth: false,
               plugins: {
                   pagination: {
                       //pageSizes: ["25"],
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

    dojo.connect(grid, "onDeselected", "deselectRow");
    dojo.connect(grid, "onSelected", "selectRow");

    /*append the new grid to the div*/
    dojo.byId("userGrid").appendChild(grid.domNode);

    /*Call startup() to render the grid*/
    grid.startup();

    userGrid = grid;

    getAlreadySelectedUserNames();
}

function setSelected(id)
{
    if(id == null)
        return;

    userGrid.store.fetch({query:{id:id}, onComplete: setItem });
}

function setItem(items, request)
{
    if(items.length == 1)
        userGrid.selection.addToSelection(items[0]);
}

function selectRow(row)
{
    id = userGrid.store.getValue(userGrid.getItem(row), "id");
    name = userGrid.store.getValue(userGrid.getItem(row), "FullName");
    address = userGrid.store.getValue(userGrid.getItem(row), "EMailAddress");
    newSelection = new userObject(row, id, name, address);
    addToSelected(newSelection);
}

function deselectRow(row)
{
    id = userGrid.store.getValue(userGrid.getItem(row), "id");
    oldSelection = new userObject(row, id);
    removeFromSelected(oldSelection);
}

function returnUserNames()
{
    var returnObject = getDocumentObjectFromObject(window.opener.document, 'message.toUser');
    var returnIDObject = getDocumentObjectFromObject(window.opener.document, 'message.toUser_VisID');
    var returnEmailObject = getDocumentObjectFromObject(window.opener.document, 'message.toUserEmailAddress');

    var name = "";
    var id = "";
    var email = "";

    for (i=0;i<mSelected.length;i++)
    {
        var userObject = mSelected[i];
        if (name.length > 0)
        {
            name = name + separator;
        }
        name = name + userObject.name;


        if (id.length > 0)
        {
            id = id + separator;
        }
        id = id + userObject.userId;

        if (email.length > 0)
        {
            email = email + separator;
        }
        email = email + userObject.emailAddress;
    }

    returnObject.value = name;
    returnIDObject.value = id;
    returnEmailObject.value = email;

    self.close();
}

function getAlreadySelectedUserNames()
{
    var visIdList = getDocumentObjectFromObject(window.opener.document, 'message.toUser_VisID');
    var id = "";

    if (visIdList.value.length > 0 && visIdList.value.indexOf(separator) > 0)
    {
        var idTokenizer = new StringTokenizer (visIdList.value, separator);
        while (idTokenizer.hasMoreTokens())
        {
            id = idTokenizer.nextToken();
            setSelected(id);
        }
    }
    else if (visIdList.value.length > 0)
    {
        id = visIdList.value;
        setSelected(id);
    }
}

function resizeGrid()
{
   userGrid.destroy(false);
   init();
}