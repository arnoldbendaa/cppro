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
////\
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.store.Memory");

function contactBudgetUser(bcId, structureElementId, cascade, structureId, contact_approver) {
    var params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=100,top=100';

    var url = getContextPath() + '/communicationNewSetup.do?MessageType=new&pageSource=budgetState&budgetCycleId=' + bcId;
    url = url + '&structureElementId=' + structureElementId;
    url = url + '&cascade=' + cascade;
    url = url + '&structureId=' + structureId;
    url = url + '&contactApprover=' + contact_approver;
    window.open(url, '_blank', params);
}

function budgetStatus(structureElement, state, addId, oldId) {
    if (!isNewWindow() && dblclk())
        return;

    var form;
    var seField;
    var filterField;
    var addIdField;
    var oldIdField;

    seField = getDocumentObject('struc_id');
    seField.value = structureElement;

    if (state == null)
        state = 0;
    filterField = getDocumentObject('filter');
    filterField.value = state;

    addIdField = getDocumentObject('addId');
    if (addId == null)
        addId = "";
    addIdField.value = addId;
    oldIdField = getDocumentObject('oldId');
    if (oldId == null)
        oldId = "";
    oldIdField.value = oldId;

    form = getDocumentObject('budgetCycleStatusForm');
    //by arnold
    form.action = "/cppro/budgetCycleStatus.do";
    //end
    submit(form);
}

function workspaceBudget(structureElement, addId, oldId) {
    var form;
    getDocumentObject('topNode').value = 0;

    if (addId == null)
        addId = "";
    getDocumentObject('addId').value = addId;
    if (oldId == null)
        oldId = "";
    getDocumentObject('oldId').value = oldId;

    if (structureElement == null)
        alert('An error has occured please contact your administrator');

    getDocumentObject('struc_id').value = "" + structureElement;
    getDocumentObject('selectedStructureElementId').value = "" + structureElement;

    form = getDocumentObject('budgetCycleStatusForm');
    form.action = getContextPath() + "/budgetWorkspace.do";
    submit(form);
}

function changeState(state, bcId, seId, userid) {
    if (!isNewWindow() && dblclk())
        return;

    var form;

    if (state == null)
        alert('An error has occured please contact your administrator');
    getDocumentObject('state').value = "" + state;

    if (bcId == null)
        alert('An error has occured please contact your administrator');
    getDocumentObject('bcId').value = "" + bcId;

    if (seId == null)
        alert('An error has occured please contact your administrator');
    getDocumentObject('seId').value = "" + seId;

    getDocumentObject('addId').value = '';
    getDocumentObject('oldId').value = '';
    if (userid != null) {
        getDocumentObject('lastActionedBy').value = userid;
    }

    form = getDocumentObject('budgetCycleStatusForm');
    form.action = getContextPath() + "/changeBudgetCycleState.do";
    submit(form);
}

function massSubmit(state, bcId, seId) {
    if (!isNewWindow() && dblclk())
        return;

    if (confirm("This will submit your budget and agree all child nodes ignoring Budget Limits \nDo you wish to continue ? ")) {
        var form;

        if (state == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('state').value = "" + state;

        if (bcId == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('bcId').value = "" + bcId;

        if (seId == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('seId').value = "" + seId;

        getDocumentObject('addId').value = '';
        getDocumentObject('oldId').value = '';

        getDocumentObject('fromState').value = '10'; //indicate all
        getDocumentObject('toState').value = '4'; //agreed

        form = getDocumentObject('budgetCycleStatusForm');
        form.action = getContextPath() + "/changeBudgetCycleState.do";
        submit(form);
    }
}

function massReject(state, bcId, seId) {
    if (!isNewWindow() && dblclk())
        return;

    if (confirm("This will reject all the child nodes that have been agreed.\nDo you wish to continue ? ")) {
        var form;

        if (state == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('state').value = "" + state;

        if (bcId == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('bcId').value = "" + bcId;

        if (seId == null)
            alert('An error has occured please contact your administrator');
        getDocumentObject('seId').value = "" + seId;

        getDocumentObject('addId').value = '';
        getDocumentObject('oldId').value = '';

        getDocumentObject('fromState').value = '4'; //indicate all
        getDocumentObject('toState').value = '2'; //agreed

        form = getDocumentObject('budgetCycleStatusForm');
        form.action = getContextPath() + "/changeBudgetCycleState.do";
        submit(form);
    }
}

function checkBudgetActivity(elementId, addId, oldId) {
    getDocumentObject('CCId').value = elementId;
    if (addId == null)
        addId = '';
    if (oldId == null)
        oldId = '';
    getDocumentObject('addId').value = addId;
    getDocumentObject('oldId').value = oldId;

    var form = getDocumentObject('budgetCycleStatusForm');
    form.action = getContextPath() + "/budgetCycleActivityList.do";
    form.target = "_blank";
    submit(form);
}

var reviewClick;
var chooseProfileClick;
var braFocus;


var startClick;
var submitClick;
var agreeClick;
var rejectClick;
var massSubmitClick;
var massRejectClick;

function connectMenu(anchor, modelId, budgetCycleId, structureId, elementId, childId, actionedBy,
    showStart, showSubmit, showAgree, showReject, showMSubmit, showMReject, newFeaturesEnabled, modelName, cycleName) {
    if (showMSubmit == null) {
        showMSubmit = false;
        showMReject = false;
    }

    disableAll = false;
    if (!showStart && !showSubmit && !showAgree && !showReject && !showMSubmit && !showMReject)
        disableAll = true;

    if (showAgree)
        showReject = true;

    menu = dijit.byId("actionMenu");
    // when we right-click anywhere on the tree, make sure we open the menu
    menu.bindDomNode(anchor);

    dojo.connect(menu, "_openMyself", this, function(e) {
        dojo.forEach(menu.getChildren(), function(menuWidget) {
            if (menuWidget.id == 'menuReview') {
                if (reviewClick != null) {
                    dojo.disconnect(reviewClick);
                    reviewClick = null;
                }

                if (reviewClick == null) {
                    reviewClick = dojo.connect(menuWidget, "onClick", function(evt) {
                        window.top.reviewBudget(childId, elementId, null, true, budgetCycleId, modelName, cycleName, modelId);
                    });
                }
            } else if (menuWidget.id == "menuChooseProfile") {


                if (chooseProfileClick != null) {
                    dojo.disconnect(chooseProfileClick);
                    chooseProfileClick = null;
                }

                if (chooseProfileClick == null) {
                    chooseProfileClick = dojo.connect(menuWidget, "onClick", function(evt) {
                        window.top.reviewProfile(modelId, childId, elementId, budgetCycleId, modelName, cycleName, modelId);
                    });
                }
            } else if (menuWidget.id == 'menuReview2') {
                if (reviewClick != null) {
                    dojo.disconnect(reviewClick);
                    reviewClick = null;
                }

                if (reviewClick == null) {
                    reviewClick = dojo.connect(menuWidget, "onClick", function(evt) {
                        window.top.reviewBudget(childId, elementId, null, true, budgetCycleId, modelName, cycleName, modelId);
                    });
                }
            } else if (menuWidget.id == "menuChooseProfile") {


                if (chooseProfileClick != null) {
                    dojo.disconnect(chooseProfileClick);
                    chooseProfileClick = null;
                }

                if (chooseProfileClick == null) {
                    chooseProfileClick = dojo.connect(menuWidget, "onClick", function(evt) {
                        window.top.reviewProfile(modelId, childId, elementId, budgetCycleId);
                    });
                }
            } else if (menuWidget.id == "menuTopBRA") {
                //BRA showing on the top of page (above iframes)
                if (braFocus != null) {
                    dojo.disconnect(braFocus);
                    braFocus = null;
                }

                if (braFocus == null) {
                    braURL = getContextPath() + '/assignment.do?bcId=' + budgetCycleId + '&structureId=' + structureId + '&elemId=' + childId;
                    braFocus = dojo.connect(menuWidget, "onClick", function(evt) {
                        window.top.setContentHrefTop("topMenuBRA", braURL)
                    });
                }
            } else if (menuWidget.id == "workFlow") {
                menuWidget.set("disabled", disableAll);
                if (!disableAll) {
                    wfMenu = menuWidget.popup;
                    dojo.forEach(wfMenu.getChildren(), function(wfWidget) {
                        wfWidget.set('disabled', false);
                        if (wfWidget.id == "menuStart") {
                            if (!showStart) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (startClick != null) {
                                    dojo.disconnect(startClick);
                                    startClick = null;
                                }

                                if (startClick == null) {
                                    startClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        changeState(1, budgetCycleId, childId)
                                    });
                                }
                            }
                        } else if (wfWidget.id == "menuSubmit") {
                            if (!showSubmit) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (submitClick != null) {
                                    dojo.disconnect(submitClick);
                                    submitClick = null;
                                }

                                if (submitClick == null) {
                                    submitClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        changeState(3, budgetCycleId, childId)
                                    });
                                }
                            }
                        } else if (wfWidget.id == "menuAgree") {
                            if (!showAgree) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (agreeClick != null) {
                                    dojo.disconnect(agreeClick);
                                    agreeClick = null;
                                }

                                if (agreeClick == null) {
                                    agreeClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        changeState(4, budgetCycleId, childId)
                                    });
                                }
                            }
                        } else if (wfWidget.id == "menuReject") {
                            if (!showReject) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (rejectClick != null) {
                                    dojo.disconnect(rejectClick);
                                    rejectClick = null;
                                }

                                if (rejectClick == null) {
                                    rejectClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        changeState(2, budgetCycleId, childId, actionedBy)
                                    });
                                }
                            }
                        } else if (wfWidget.id == "menuMassSubmit") {
                            if (!showMSubmit) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (massSubmitClick != null) {
                                    dojo.disconnect(massSubmitClick);
                                    massSubmitClick = null;
                                }

                                if (massSubmitClick == null) {
                                    massSubmitClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        massSubmit(3, budgetCycleId, childId)
                                    });
                                }
                            }
                        } else if (wfWidget.id == "menuMassReject") {
                            if (!showMReject) {
                                wfWidget.set("disabled", true);
                            } else {
                                if (massRejectClick != null) {
                                    dojo.disconnect(massRejectClick);
                                    massRejectClick = null;
                                }

                                if (massRejectClick == null) {
                                    massRejectClick = dojo.connect(wfWidget, "onClick", function(evt) {
                                        massReject(10, budgetCycleId, childId)
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    });
}