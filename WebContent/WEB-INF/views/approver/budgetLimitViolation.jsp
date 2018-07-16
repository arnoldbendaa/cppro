<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body>
<html:form action="budgetViolationConfirm" styleId="budgetViolationConfirmForm" >

<h1 class="header1"><bean:message key="cp.reviewBudget.violations" /></h1>

<jsp:include page="../system/errorsInc.jsp" />

<table class="groupcell" width="100%"  cellspacing="0" border="0">
<!-- headings -->
  <tr>
    <th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t1"/></th>
    <logic:iterate id="header" name="budgetViolationConfirmForm" property="violationHeading" >
        <th nowrap align="left" title="<bean:write name="header" property="description" />"  ><bean:write name="header" property="identifier" /></th>
    </logic:iterate>
	<th nowrap align="left" ><bean:message key="cp.budgetlimit.tab1.table.t2"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t3"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t4"/></th>
    <th nowrap align="right" ><bean:message key="cp.budgetlimit.tab1.table.t5"/></th>
  </tr>
  <!-- lines -->
  <logic:iterate id="lines" name="budgetViolationConfirmForm" property="violations"  type="com.cedar.cp.utc.struts.topdown.LimitsDTO" >
    <tr>
        <logic:iterate id="line" name="lines" property="structureElement" type="String" indexId="rowId" >
            <td ><bean:write name="line" /></td>
        </logic:iterate>
        <td align="right" ><bean:write name="lines" property="minString" format="#,##0.00" /></td>
        <td align="right" ><bean:write name="lines" property="maxString" format="#,##0.00" /></td>
        <td align="right" ><bean:write name="lines" property="currentValue" format="#,##0.00" /></td>
    </tr>
  </logic:iterate>
</table>
<br/>
<bean:message key="cp.budgetlimit.violation.reason" />
<br/>
<html:textarea property="reason" rows="6" cols="80"  />
<br/>
<cp:CPButton buttonType="submit" property="violationAction"><bean:message key="cp.budgetlimit.violation.ok" /></cp:CPButton>
<cp:CPButton buttonType="submit" property="violationAction"><bean:message key="cp.budgetlimit.violation.cancel" /></cp:CPButton>

<br/>
<html:hidden property="modelId" />
<html:hidden property="budgetCycleId"/>
<html:hidden property="structureId" />
<html:hidden property="structureElementId"/>
<br/>
<html:hidden property="oldUserCount" />
<html:hidden property="oldDepth" />
<html:hidden property="stateFilter"  />
<br/>
<html:hidden property="structureElementList" />
<html:hidden property="visIdList" />
<html:hidden property="oldDepthList" />
<html:hidden property="oldUserCountList" />
<html:hidden property="descriptionList" />
<br/>
<html:hidden property="addId" />
<html:hidden property="oldId" />

<!-- dont think we need topNode -->
<html:hidden property="pageSource" />
<html:hidden property="submitModelName" />
<html:hidden property="submitCycleName" />
<html:hidden property="state" />
<!-- just in case -->
<html:hidden property="bcId" />
<html:hidden property="seId" />

<html:hidden property="fromState" styleId="fromState" />
<html:hidden property="toState" styleId="toState" />
<input type="hidden" name="selectedStructureElementId" />

</html:form>
</body>