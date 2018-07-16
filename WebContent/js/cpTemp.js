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
 * This file is to be delete when the port of the apps to fulcrum is complete
 */

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

function addOverFlow()
{
	dojo.byId("mainFrame").style.overflow = 'auto';
}