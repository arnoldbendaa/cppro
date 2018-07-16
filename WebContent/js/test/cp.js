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
// Globals....
var currentPanel;

function hover(tab)
{
    tab.style.backgroundColor = '#ffffc0';
    tab.style.color = '#1D5EAA';
}

function getCurrentPanel(parentFrame)
{
    if (parentFrame != null && typeof(parentFrame) != "undefined")
    {
        return parentFrame.currentPanel;
    }
    return currentPanel;
}

function setCurrentPanel(newpanel, parentFrame)
{
    if (parentFrame != null && typeof(parentFrame) != "undefined")
    {
        parentFrame.currentPanel = newpanel;
    }
    else
    {
        currentPanel = newpanel;
    }
}

function setState(tabNum, parentFrame)
{
    var documentToUse = document;

    if (parentFrame != null && typeof(parentFrame) != "undefined")
    {
        documentToUse = parentFrame.document;
    }
    if (tabNum == getCurrentPanel(parentFrame))
    {
        documentToUse.getElementById('tab' + tabNum).style.backgroundColor = '#ddddff';
        documentToUse.getElementById('tab' + tabNum).style.color = 'navy';
    }
    else
    {
        documentToUse.getElementById('tab' + tabNum).style.backgroundColor = '#FFFFF4';
        documentToUse.getElementById('tab' + tabNum).style.color = '#1D5EAA';
    }
}

function showPanel(newpanel, parentFrame)
{
    var documentToUse = document;

    if (parentFrame != null && typeof(parentFrame) != "undefined")
    {
        documentToUse = parentFrame.document;
    }
    var panel = getCurrentPanel(parentFrame);

    // Hide the current panel.
    if (panel != null && typeof(panel) != "undefined")
    {
        hidePanel(parentFrame);
    }

    // Show the selected panel.
    documentToUse.getElementById('tabpanel' + newpanel).style.display = 'block';

    // Update current panel.
    setCurrentPanel(newpanel, parentFrame);

    // Set current panel state.
    setState(newpanel, parentFrame);
}

function hidePanel(parentFrame)
{
    var documentToUse = document;

    if (parentFrame != null && typeof(parentFrame) != "undefined")
    {
        documentToUse = parentFrame.document;
    }
    var curPanel = getCurrentPanel(parentFrame);

    // Hide visible panel and unhighlight tab.
    if (documentToUse != null)
    {
        var tabpanel = getDocumentObjectFromObject(documentToUse, 'tabpanel' + curPanel);
        if (tabpanel != null)
        {
            tabpanel.style.display = 'none';
        }

        var tab = getDocumentObjectFromObject(documentToUse, 'tab' + curPanel);
        if (tab != null)
        {
            tab.style.backgroundColor = '#FFFFF4';
            tab.style.color = '#1D5EAA';
        }

    }
}

// Gets an object by name or by object
function getObj(elementId)
{
    if (typeof elementId == "string")
    {
        return document.getElementById(elementId);
    }

    return elementId;
}

//function used to get object by name that works across browser
function getDocumentObject(name)
{
    if (document.getElementById)
    {
        this.obj = document.getElementById(name);
    }
    else if (document.all)
    {
        this.obj = document.all[name];
    }
    else if (document.layers)
    {
        this.obj = document.layers[name];
    }

    return this.obj;
}

//function used to get object from within a object by name that works across browser
function getDocumentObjectFromObject(object, name)
{
    var obj;
    if (document.getElementById)
    {
        obj = object.getElementById(name);
    }
    else if (document.all)
    {
        obj = object.all[name];
    }
    else if (document.layers)
    {
        obj = object.layers[name];
    }

    return obj;
}

//function used to get height
//used when calculating what the div/iframe height should be
function getWindowHeight(windowObj)
{
    if (!window.innerHeight)
    {
        return window.document.body.clientHeight;
    }
    else
    {
        return window.innerHeight;
    }
}

//function used to get width
//used when calculating what the div/iframe width should be
function getWindowWidth(windowObj)
{
    if (!window.innerWidth)
    {
        return window.document.body.clientWidth;
    }
    else
    {
        return window.innerWidth;
    }
}

//function used to get the user to confirm an action
function confirmAction(actionUrl)
{
    if (confirm('Are you sure ?'))
        location = actionUrl;
}

function checkIE()
{
    var result = true;

    if (navigator == null ||
        navigator.appName.indexOf("Netscape") >= 0)
    {
        result = false;
    }

    return result;
}

function getStyleHeight(value)
{
    var result = 0;

    result = value.substring(0, value.length - 2);

    return result;
}

//functions moved into js file below here

function loadAddress(address)
{
    var url = getContextPath() + "/" + address;
    window.location.href = url;
}

function openMessage(type, key, source)
{
    //standard window params
    params = 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0';
    params = params + ',width=630,height=430,left=100,top=100';
    if (source == null)
        source = 'list';

    if (type == 'new')
    {
        url = getContextPath() + '/communicationNewSetup.do?MessageType=new';
        window.open(url, '_newMessage', params);
    }
    else
    {
        url = getContextPath() + '/communicationOpenSetup.do';
        url = url + '?messageId=' + key;
        url = url + '&source=' + source;
        window.open(url, '_blank', params);
    }
}

function openHelp()
{
    //standard window params
    params = 'toolbar=0,location=0,statusbar=0,menubar=0,resizable=1';
    window.open(getContextPath() + '/help/index.htm', 'cpHelp', params);
}

function logout()
{
    window.location.href = getContextPath() + '/testLogout.do';
}