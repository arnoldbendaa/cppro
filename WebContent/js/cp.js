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
/*
 * Collaborative Planning scriptslets
 */
// Gets an object by name or by object

function shouldParse() {
	var result = true;

	if (dojo.isFF || dojo.isSafari || dojo.isOpera) {
		result = false;
	} else if (dojo.isIE) {
		if (navigator.userAgent.indexOf('MSIE 8.0') == -1)
			result = false;
	}

	if (top.skipRemove)
		return skipRemove();

	return result;
}

function getObj(elementId) {
	if (typeof elementId == "string") {
		return document.getElementById(elementId);
	}

	return elementId;
}

//function used to get object by name that works across browser
function getDocumentObject(name) {
	if (document.getElementById) {
		this.obj = document.getElementById(name);
	} else if (document.all) {
		this.obj = document.all[name];
	} else if (document.layers) {
		this.obj = document.layers[name];
	}

	return this.obj;
}

//function used to get object from within a object by name that works across browser
function getDocumentObjectFromObject(object, name) {
	var obj;
	if (document.getElementById) {
		obj = object.getElementById(name);
	} else if (document.all) {
		obj = object.all[name];
	} else if (document.layers) {
		obj = object.layers[name];
	}

	return obj;
}

//function used to get height
//used when calculating what the div/iframe height should be
function getWindowHeight(windowObj) {
	if (!window.innerHeight) {
		return window.document.body.clientHeight;
	} else {
		return window.innerHeight;
	}
}

//function used to get width
//used when calculating what the div/iframe width should be
function getWindowWidth(windowObj) {
	if (!window.innerWidth) {
		return window.document.body.clientWidth;
	} else {
		return window.innerWidth;
	}
}

dojo.addOnUnload(function() {
	if (window.unloading)
		unloading();
});


dojo.addOnLoad(function() {
	var cpParentNode;
	var cpObjectNode;

	//had to remove parse on load because of a ff error
	if (checkBrowserOk()) {
		dojo.query("object", document).forEach(
			function(objectTag) {
				if (!shouldParse()) {
					cpParentNode = objectTag.parentNode;
					cpObjectNode = cpParentNode.removeChild(objectTag);
				}
			}
		);
		dojo.parser.parse();

		if (!shouldParse()) {
			if (cpParentNode != null)
				cpParentNode.appendChild(cpObjectNode);
		}
	}
});

/**
 * call back from basejapplet if we need it in a page.
 */
function appletComplete() {

}

//helper method to check if we are running in httpunit or not
function checkBrowserOk() {
	var result = true;

	if (navigator == null ||
		navigator.userAgent.indexOf("httpunit") >= 0) {
		result = false;
	}

	return result;
}

function dblclk() {
	var check = dojo.byId("dblclkValue");
	if (check.value == 0) {
		check.value = check.value + 1;
		return false;
	} else {
		dblclkMessage(check.value);
		check.value = check.value + 1;
		return true;
	}
}

function dblclkMessage(value) {
	alert("You have already submitted this request " + value + " times");
}

//function used to get the user to confirm an action
function confirmAction(actionUrl) {
	if (confirm('Are you sure ?'))
		location = actionUrl;
}

//functions moved into js file below here
function loadAddress(address, target) {
	myUrl = getContextPath() + "/" + address;
	if (target == null) {
		window.location.href = myUrl;
	} else {
		windowObj = window.open(myUrl, target);
		windowObj.focus(); //this seems to fail but leave it in for now
	}
}

function openMessage(type, key, source) {
	//standard window params
	var params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1';
	params = params + ',width=630,height=430,left=100,top=100';
	if (source == null)
		source = 'list';

	var url = getContextPath();
	if (type == 'new') {
		url = url + '/communicationNewSetup.do?MessageType=new';
		window.open(url, '_newMessage', params);
	} else {
		url = url + '/communicationOpenSetup.do?messageId=' + key;
		url = url + '&source=' + source;
		window.open(url, '_blank', params);
	}
}

function openHelp() {
	//standard window params
	params = 'toolbar=0,location=0,statusbar=0,menubar=0,resizable=1';
	window.open(getContextPath() + '/help/index.htm', 'cpHelp', params);
}

function logout() {
	window.location.href = getContextPath() + '/logout.do';
}

function getApplet_byId(id) {
	applet = null;
	if (dojo.isIE) {
		applet = dojo.byId("ie_" + id);
	} else {
		applet = dojo.byId("ff_" + id);
	}

	if (applet == null)
		alert("null applet");

	return applet
}

function closeAppletWindow(name) {
	try {
		var applet = getApplet_byId(name);
		var parent = applet.parentNode;
		parent.removeChild(applet);
	} catch (e) {
		//do nothing just try to close
	}
	self.close();
}

/**
 * A few dojo helpers to copy value
 */
function copyValue(obj, id) {
	var target = dojo.byId(id);
	if (obj.type == 'checkbox') {
		target.checked = obj.checked;
	} else {
		target.value = obj.value;
	}
}

function showDialog(id) {
	var obj = dijit.byId(id);
	if (obj != null && obj.show)
		obj.show();
}

function closeDialog(id) {
	var obj = dijit.byId(id);
	if (obj != null && obj.hide)
		obj.hide();
}

/*
 * still working on this seems to cause some issues with dialog
 */
function evelJSONData(data) {
	var response = dojo.fromJson(data);
	if (response.error) {
		alert("Error during processing : " + response.error.value);
		return null;
	} else {
		return response;
	}
}

function refreshTab(id) {
	var tab = dijit.byId(id);
	if (tab != null)
		tab.refresh();
}

/**
 * used for keeping track of selected items in a list
 * @param id
 */
var mSelected;

function addToSelected(id) {
	if (mSelected == null)
		mSelected = new Array();

	mSelected.push(id);
}

function removeFromSelected(id) {
	if (mSelected == null)
		mSelected = new Array();

	mSelected.pop(id);
}

function clearSelection() {
	mSelected = new Array();
}

function doLaunch(url) {
	var params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1';
	params = params + ',width=1024,height=768,left=10,top=10';

	newWindow = window.open(url, '_cpAppletLaunch', params);
	newWindow.focus();
}

function setContentHref(node, url) {
	myNode = dijit.byId(node);
	if (node == 'roadMapId') {
		document.getElementsByClassName("cpChangeLogIcon")[0].style.color = "rgb(255, 255, 255)";
	}
	myNode.set('href', url);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function showAndHide(budgetCycleId, structureElementId) {
	var button = document.getElementById('button_' + budgetCycleId);
	var url = getContextPath();
	url += '/budgetCycleStatus.do?budgetCycleId=' + budgetCycleId + '&structureElementId=' + structureElementId + '&addId' + structureElementId;
	var elementId = "bc" + budgetCycleId;
	var iframe = document.getElementById(elementId);

	if (button.innerHTML == '+') {
		if (iframe.src === window.location.href) {
			iframe.src = url;
		}
		//iframe.height = '158px';
		iframe.style.display = "block";
		iframe.style.float = 'none';
		button.innerHTML = '-';
	} else if (button.innerHTML == '-') {
		//iframe.src = '';
		//iframe.height = '0px';
		iframe.style.display = "none";
		iframe.style.float = 'left';
		button.innerHTML = '+';
	}

}

function autoResize(id) {
	//its fixing the auto juping (scrolling) iframes
	window.scrollTo(0, 0);

	function iframeRef(frameRef) {
		return frameRef.contentWindow ? frameRef.contentWindow.document : frameRef.contentDocument
	}

	var inside = iframeRef(document.getElementById(id));
	var rows = 0;
	if (inside.getElementById('totalsTable') !== null) {
		rows = inside.getElementById('totalsTable').rows.length;
	}

	var newheight = (28 * rows) + 1;
	if (newheight < 100) {
		newheight = 135;
	}
	if (rows === 0) {
		newheight = 0;
	}
	var newwidth;
	var form = document.getElementById(id);

	document.getElementById(id).height = (newheight) + "px";
}

///////////// window.top


dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.store.Memory");

var profileCombo;
var profileStore;
var profileMemory;

function setContentHrefTop(node, url) {
	myNode = dijit.byId(node);
	myNode.set('href', url);
	myNode = dijit.byId(node).show();
}

function checkAll() {
	var lsit = document.getElementsByTagName("*");
	var elem2;
	for (var i = 0; i < lsit.length; i++) {
		elem2 = lsit.item(i);
		if (typeof elem2.id != 'undefined') {
			if (elem2.id.substr(0, 7) == 'contact' && elem2.id != 'contactAll') {
				elem2.checked = document.getElementById('contactAll').checked;
			}
		}
	}
}

function callMessaging() {
	var list = "";
	var lsit = document.getElementsByTagName("*");
	var elem2;
	var n = 0;
	for (var i = 0; i < lsit.length; i++) {
		elem2 = lsit.item(i);
		if (typeof elem2.name != 'undefined' && elem2.name != '') {
			if (elem2.name.substr(0, 7) == 'contact' && elem2.id != 'contactAll') {
				if (elem2.checked) {
					if (n++ > 0) {
						list += ","
					};
					list += elem2.id.substr(7, elem2.length);
				}
			}
		}
	}
	var params = 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0';
	params = params + ',width=630,height=430,left=100,top=100';
	var url = getContextPath() + '/communicationNewSetup.do?pageSource=approvers&list=' + list;
	//dojo.byId("data").focus();
	window.open(url, '_blank', params);
}

function reviewProfile(modelId, structureElement, addId, budgetCycleId, modelName, cycleName, modelId) {
	showProfile(modelId, budgetCycleId);
	reviewBudget(structureElement, addId, oldId, false, budgetCycleId, modelName, cycleName, modelId);
}

function showProfile(modelId, budgetCycleId) {
	loadCombo(modelId, budgetCycleId);
	dijit.byId("profileDialog").show();
}


function loadCombo(modelId, budgetCycleId) {
	//if (!profileStore) {
	//var budgetCycleId = dojo.query("[name^='budgetCycleId']").attr("value");
	myURL = getContextPath() + '/jsonList.do?CustomType=DEProfileforUser&ModelId=' + modelId + '&budgetCycleId=' + budgetCycleId;
	profileStore = new dojo.data.ItemFileReadStore({
		url: myURL,
		preventCache: true
	});
	// }
	profileStore.fetch();

	if (!profileCombo) {

		profileCombo = new dijit.form.ComboBox({
			id: "profileCombo",
			name: "prog",
			store: profileStore,
			searchAttr: "id",
			labelAttr: "value"
		}, "profileCombo");
	} else {
		profileCombo.set('store', profileStore);
	}

}

function profileLoad() {

}

function profileLoadError() {
	alert("load error");
}

function useProfile() {
	dojo.byId("profileRef").value = profileCombo.item.key;
	closeDialog();
	submit();
}

function editProfiles() {
	dojo.byId("profileRef").value = 'editProfile';
	closeDialog();
	submit();
}


function cancelProfile() {
	dojo.byId("profileRef").value = '';
	resetForm();
	closeDialog();
}

function closeDialog() {
	dijit.byId("profileCombo").reset();
	dijit.byId("profileDialog").hide();
}

function submit(form) {
	if (form == null)
		form = getDocumentObject('budgetCycleStatusForm');
	form.submit();
	resetForm(form);
}

function resetForm(form) {
	if (form == null)
		form = getDocumentObject('budgetCycleStatusForm');
	//reset attributes
	form.action = getContextPath() + "/budgetCycleStatus.do";
	form.target = "_self";
	dojo.byId("profileRef").value = '';
}

function reviewBudget(structureElement, addId, oldId, doSubmit, budgetCycleId, modelName, cycleName, modelId) {
	reviewBudgetVersion(structureElement, addId, oldId, doSubmit, "/reviewBudget/", budgetCycleId, modelName, cycleName, modelId);
}



function reviewBudgetVersion(structureElement, addId, oldId, doSubmit, address, budgetCycleId, modelName, cycleName, modelId) {
	var form;
	if (doSubmit == null)
		doSubmit = true;

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
	form.action = getContextPath() + address;

	if (cycleName !== undefined) {
		form.elements["submitCycleName"].value = cycleName;
		form.elements["submitModelName"].value = modelName;
	}
	if (modelId !== undefined) {
		form.elements["modelId"].value = modelId;
	}
	form.elements["budgetCycleId"].value = budgetCycleId;


	if (isNewWindow()) {
		dojo.byId("full").value = "true";
		//myTarget = '_' + dojo.byId("submitModelName").value;
		//myTarget.replace(" ", "_");
		//form.target = myTarget;
		form.target = "_blank";
	}

	if (doSubmit)
		submit(form);

	if (dijit.byId("profileCombo") !== undefined) {
		dijit.byId("profileCombo").reset();
	}

}

function isNewWindow() {
	return true;
}



var showHideDescription = function(id) {
	var el = document.getElementById("road-map-level2-" + id);
	el.style.display = el.style.display == 'none' ? 'block' : 'none';
}

var showHideBoardsDiv = function(id) {
	var el = document.getElementById(id);
	el.style.display = el.style.display == 'none' ? 'block' : 'none';
}