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
var currentMenu;
var currentMenuItem;
var currentMenuItems;
var currentReport;
var currentReportItem;
var currentReportItems;

/**
 * Sets a Cookie with the given name and value.
 *
 * name       Name of the cookie
 * value      Value of the cookie
 * [expires]  Expiration date of the cookie (default: end of current session)
 * [path]     Path where the cookie is valid (default: path of calling document)
 * [domain]   Domain where the cookie is valid
 *              (default: domain of calling document)
 * [secure]   Boolean value indicating if the cookie transmission requires a
 *              secure transmission
 */
function setCookie(name, value, expires, path, domain, secure)
{
    document.cookie= name + "=" + escape(value) +
        ((expires) ? "; expires=" + expires.toGMTString() : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
}

/**
 * Gets the value of the specified cookie.
 *
 * name  Name of the desired cookie.
 *
 * Returns a string containing value of specified cookie,
 *   or null if cookie does not exist.
 */
function getCookie(name)
{
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1)
    {
        begin = dc.indexOf(prefix);
        if (begin != 0) return null;
    }
    else
    {
        begin += 2;
    }
    var end = document.cookie.indexOf(";", begin);
    if (end == -1)
    {
        end = dc.length;
    }
    return unescape(dc.substring(begin + prefix.length, end));
}

/**
 * Deletes the specified cookie.
 *
 * name      name of the cookie
 * [path]    path of the cookie (must be same as path used to create cookie)
 * [domain]  domain of the cookie (must be same as domain used to create cookie)
 */
function deleteCookie(name, path, domain)
{
    if (getCookie(name))
    {
        document.cookie = name + "=" +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}

function setWelcomePanel(id)
{
    deleteCookie("welcomePanel");
	setCookie("welcomePanel", id);
}

function getWelcomePanel()
{
	return getCookie("welcomePanel");
}

function setMassUpdatePanel(id)
{
    deleteCookie("massUpdatePanel");
	setCookie("massUpdatePanel", id);
}

function getMassUpdatePanel()
{
    return getCookie("massUpdatePanel");
}

function setBudgetLimitPanel(id)
{
    deleteCookie("budgetLimitPanel");
	setCookie("budgetLimitPanel", id);
}

function getBudgetLimitPanel()
{
    return getCookie("budgetLimitPanel");
}

function setCurrentMenu(id)
{
	currentMenu = id;
}

function getCurrentMenu()
{
	return currentMenu;
}

function setCurrentMenuItem(id)
{
	currentMenuItem = id;
}

function getCurrentMenuItem()
{
	return currentMenuItem;
}

function setCurrentMenuItems(id)
{
	currentMenuItems = id;
}

function getCurrentMenuItems()
{
	return currentMenuItems;
}

function setCurrentReport(id)
{
	currentReport = id;
}

function getCurrentReport()
{
	return currentReport;
}

function setCurrentReportItem(id)
{
	currentReportItem = id;
}

function getCurrentReportItem()
{
	return currentReportItem;
}

function setCurrentReportItems(id)
{
	currentReportItems = id;
}

function getCurrentReportItems()
{
	return currentReportItems;
}

// Returns a formatted number in style 10,000.00
function formatNumber(num)
{
	num = num.toString().replace(/\$|\,/g, '');
	if(isNaN(num))
		num = "0";
    var neg = (parseFloat(num) != Math.abs(parseFloat(num)));
	num = Math.abs(num);
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();

	if(cents < 10)
		cents = "0" + cents;

	for(var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));

	return (((neg)?'-':'') + num + '.' + cents);
}

// Parse a formatted number into a float object
function parseNumber(value)
{
	var num = value.toString().replace(/\$|\,/g, '');
	return parseFloat(num);
}

