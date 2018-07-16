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
function getDocumentObjectByName(name)
{
	var items = document.getElementsByName(name);
	if(items.length > 0)
		return items[0];
	else
		return null;
}
function editVirement( requestId )
{
//	var address = 'virement.do?requestId='+requestId;
//	loadAddress( address )
    getDocumentObjectByName('userAction').value = 'edit';
    getDocumentObjectByName('requestId').value = requestId;
    getDocumentObjectByName('virementsForm').submit();
}
function authVirement( requestId )
{
    getDocumentObjectByName('userAction').value = 'auth';
    getDocumentObjectByName('requestId').value = requestId;
    getDocumentObjectByName('virementsForm').submit();
}
function viewVirement( requestId )
{
    getDocumentObjectByName('userAction').value = 'view';
    getDocumentObjectByName('requestId').value = requestId;
    getDocumentObjectByName('virementsForm').submit();
}
function deleteVirement( requestId )
{
	getDocumentObjectByName('userAction').value = 'delete';
	getDocumentObjectByName('requestId').value = requestId;
	getDocumentObjectByName('virementsForm').submit();
}
function newVirement()
{
	loadAddress('virement.do');
}
function refreshList()
{
	getDocumentObjectByName('virementsForm').submit();
}
function showTab(idx)
{
    showPanel(idx);
	getDocumentObject("currentTab").value = idx;
}