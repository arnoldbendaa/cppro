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
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojox.grid._CheckBoxSelector");

function messageObject(rowId, messageId, messageUserId) {
    this.rowId = rowId;
    this.messageId = messageId;
    this.messageUserId = messageUserId;
}
messageObject.prototype.toString = messageObjectToString;

function messageObjectToString() {
    return this.rowId;
}

function getSelectedPane() {
    var tabbedPane = dijit.byId("commsTabContainer");
    return tabbedPane.selectedChildWidget;
}

function getSelectedGrid() {
    if (getSelectedPane().id == "inBox") {
        return inBoxMessageGrid;
    } else {
        return sentBoxMessageGrid;
    }
}

function refreshMailContent(popupWindow) {
    refreshTab();

    if (popupWindow != null) {
        popupWindow.close();
    }
}

var inBoxMessageGrid;
var sentBoxMessageGrid;

var inBoxlayout = [{
        type: "dojox.grid._CheckBoxSelector"
    },
    [{
        'name': 'Id',
        'field': 'id',
        'width': '35px'
    }, {
        'name': ' ',
        'field': 'Attach',
        'width': '20px',
        'formatter': formatAttach
    }, {
        'name': ' ',
        'field': 'Read',
        'width': '20px',
        'formatter': formatRead
    }, {
        'name': 'From',
        'field': 'From',
        'width': '150px'
    }, {
        'name': 'Subject',
        'field': 'Subject',
        'width': 'auto'
    }, {
        'name': 'Date',
        'field': 'Date',
        'width': '80px'
    }, {
        'name': 'Time',
        'field': 'Time',
        'width': '50px'
    }]
];

var sentBoxlayout = [{
        type: "dojox.grid._CheckBoxSelector"
    },
    [{
        'name': 'Id',
        'field': 'id',
        'width': '35px'
    }, {
        'name': ' ',
        'field': 'Attach',
        'width': '20px',
        'formatter': formatAttach
    }, {
        'name': 'To',
        'field': 'To',
        'width': 'auto'
    }, {
        'name': 'Subject',
        'field': 'Subject',
        'width': 'auto'
    }, {
        'name': 'Date',
        'field': 'Date',
        'width': '80px'
    }, {
        'name': 'Time',
        'field': 'Time',
        'width': '50px'
    }]
];

function init() {
    if (getSelectedPane().id == "inBox") {
        initInbox();
    } else {
        initSentItems();
    }
}

function initTabGrid(gridId, parentId, layout) {
    var store = new dojo.data.ItemFileWriteStore({
        url: getStoreURL(),
        urlPreventCache: true,
        clearOnClose: true
    });
    var grid = new dojox.grid.EnhancedGrid({
        id: gridId,
        store: store,
        structure: layout,
        editable: false,
        autoHeight: false,
        autoWidth: false,
        //columnReordering:true,
        plugins: {
            pagination: {
                pageSizes: ["25", "50", "75", "100"],
                description: true,
                sizeSwitch: false,
                pageStepper: true,
                gotoButton: true,
                /*page step to be displayed*/
                maxPageStep: 4,
                /*position of the pagination bar*/
                position: "top"
            }
        }
    });

    dojo.connect(grid, "onRowDblClick", "openMessageItem");
    dojo.connect(grid, "onDeselected", "deselectRow");
    dojo.connect(grid, "onSelected", "selectRow");

    var menu = dijit.byId("gridMenu");
    menu.bindDomNode(grid.domNode);
    dojo.connect(menu, "_openMyself", this, function(e) {
        dojo.forEach(menu.getChildren(), function(entry, i) {
            entry.attr('disabled', false);
            if (entry.attr("id") == "openMenuItem") {
                if (mSelected.length != 1)
                    entry.attr('disabled', true);
            }

            if (entry.attr("id") == "deleteMenuItem") {
                if (mSelected.length == 0)
                    entry.attr('disabled', true);

                if (mSelected.length <= 1)
                    entry.setLabel("Delete Message");
                else
                    entry.setLabel("Delete Messages");
            }
        });
    });

    /*append the new grid to the div*/
    dojo.byId(parentId).appendChild(grid.domNode);

    /*Call startup() to render the grid*/
    grid.startup();

    if (gridId == "inBoxMesageGrid")
        inBoxMessageGrid = grid;
    else
        sentBoxMessageGrid = grid;
}

function initInbox() {
    initTabGrid("inBoxMesageGrid", "inBoxGrid", inBoxlayout);
}

function initSentItems() {
    initTabGrid("sentBoxMesageGrid", "sentBoxGrid", sentBoxlayout);
}

function refreshTab(newUrl) {
    var grid = getSelectedGrid();

    if (grid == null) {
        if (getSelectedPane().id == "inBox") {
            initInbox();
        } else {
            initSentItems();
        }
    } else {
        grid.selection.clear();
        if (grid.store.save) {
            grid.store.save();
        }
        grid.store.close();
        if (newUrl != null) {
            grid.store.url = getStoreURL();
        }
        grid._refresh();
    }
}

function formatAttach(value) {
    if (value) {
        return '<img src="/cppro/images/attach.gif">';
    } else {
        return '&nbsp;';
    }
}

function formatRead(value) {
    if (value) {
        return '<img src="/cppro/images/message_read.gif">';
    } else {
        return '<img src="/cppro/images/message_notread.gif">';
    }
}

function openMessageItem(evt) {
    //var idx = evt.rowIndex;
    //var grid = getSelectedGrid();

    //openMessage("system", grid.store.getValue(grid.getItem(idx), "MessageId"));
    openMessage("system", mSelected[0].messageId);
}

function deselectRow(row) {
    var grid = getSelectedGrid();

    removeFromSelected(new messageObject(grid.store.getValue(grid.getItem(row), "id"), grid.store.getValue(grid.getItem(row), "MessageId"), grid.store.getValue(grid.getItem(row), "MessageUserId")));
}

function selectRow(row) {
    var grid = getSelectedGrid();

    addToSelected(new messageObject(grid.store.getValue(grid.getItem(row), "id"), grid.store.getValue(grid.getItem(row), "MessageId"), grid.store.getValue(grid.getItem(row), "MessageUserId")));
}

function emptyFolder() {
    var tabbedPane = dijit.byId("commsTabContainer");
    var contentPane = tabbedPane.selectedChildWidget;
    if (confirm("Do you wish to delete all messages in your " + contentPane.title + " ?")) {
        dojo.xhrGet({
            // The page that parses the POST request
            url: deleteURL,
            // Loads this function if everything went ok
            load: function(data) {
                refreshMailContent();
            },
            // Call this function if an error happened
            error: function(data) {},
            content: {
                folder: contentPane.id
            },
            mimetype: "text/json",
            preventCache: true
        });
    }
}

function selectAll() {
    var grid = getSelectedGrid();

    var select = dojo.byId('selectAll');
    if (select.innerHTML == "Select All") {
        grid.selection.selectRange(0, grid.rowCount - 1);
        select.innerHTML = 'De-Select All';
    } else {
        grid.selection.clear();
        select.innerHTML = 'Select All';
    }
}

function deleteSelected() {
    if (mSelected == null || mSelected.length == 0)
        return;

    var grid = getSelectedGrid();

    var message = "";
    if (mSelected.length > 10) {
        message = "Do you wish to delete the selected messages";
    } else {
        message = "Do you wish to delete message id(s) " + mSelected.join();
    }

    if (confirm(message)) {
        dojo.xhrGet({
            // The page that parses the POST request
            url: deleteURL,
            load: function(data) {
                refreshMailContent();
            },
            content: {
                messageIds: dojo.toJson(mSelected)
            },
            mimetype: "text/json",
            preventCache: true
        });
    }
}

function resetToggle() {
    clearSelection();
    dojo.byId('selectAll').innerHTML = 'Select All';
}

function clearSelected() {
    var grid = getSelectedGrid();
    grid.selection.clear();
}

function clearSort() {
    var grid = getSelectedGrid();
    grid.setSortIndex([]);
}