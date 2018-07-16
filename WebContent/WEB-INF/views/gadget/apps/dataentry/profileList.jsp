<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
	<script type="text/javascript">
		function profileReviewBudget(structureElement, ref, address)
		{
			var form;
			getDocumentObject('topNode').value = structureElement;
			getDocumentObject('profileRef').value = ref;

			if (structureElement == null)
				alert('An error has occured please contact your administrator');

			getDocumentObject('struc_id').value = "" + structureElement;
			getDocumentObject('selectedStructureElementId').value = "" + structureElement;

			form = getDocumentObject('gadgetProfileForm');
			form.action = "<%=request.getContextPath()%>" + address;
			form.target = "_blank";
			form.submit();
		}
	</script>
</head>
<body>

<html:form styleId="gadgetProfileForm" action="/gadgetDataEntryProfile" method="post" style="padding:0;margin:0;">
<div style="display:none;">
<html:hidden property="topNodeId" styleId="topNode"/>
<html:hidden property="modelId" />
<html:hidden property="budgetCycleId" />
<input type="hidden" name="structureElementId" id="struc_id" />
<input type="hidden" name="selectedStructureElementId" id="selectedStructureElementId" />
<input type="hidden" name="profileRef" id="profileRef" />
</div>
<table width="100%" class="portal" cellspacing="0">
	<tr>
		<th class="head" align="left">Data Entry Profiles</th>
	</tr>
	<logic:iterate id="profile" name="gadgetProfileForm" property="profiles">
		<tr>
			<td>
				<a href='javascript:profileReviewBudget( <bean:write name="gadgetProfileForm" property="topNodeId" />, "<bean:write name="profile" property="dataEntryProfileEntityRef.tokenizedKey"/>", 
					<logic:present scope="session" name="cpContext">
			  			<logic:equal value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
			  			 <% // new features enabled %>
			  			 "/reviewBudget/"
			  			</logic:equal>
			  			<logic:notEqual value="true" name="cpContext" scope="session" property="newFeaturesEnabled">
			  			<% // new features disabled %>
			  			"/reviewBudget.do"
			  			</logic:notEqual>
			  		</logic:present>
				 )'>
					<bean:write name="profile" property="dataEntryProfileEntityRef"/> -
					<bean:write name="profile" property="description"/>
				</a>
			</td>
		</tr>
	</logic:iterate>
</table>
</html:form>
</body>

