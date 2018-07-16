<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body>
<html:form action="statusMessageConfirm" styleId="statusMessageConfirm" >

<h1 class="header1"><bean:message key="cp.statechange.title" /></h1>

<jsp:include page="../system/errorsInc.jsp" />

<br/>
<bean:message key="cp.statechange.message" />
<br/>
<html:textarea property="message" rows="6" cols="80"  />
<br/>
<cp:CPButton buttonType="submit" property="statusMessageAction"><bean:message key="cp.all.ok" /></cp:CPButton>
<cp:CPButton buttonType="submit" property="statusMessageAction"><bean:message key="cp.all.cancel" /></cp:CPButton>

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