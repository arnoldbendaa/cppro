<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
    <script type="text/javascript">
        //scripts dont seem to get in here as loaded in dojo tab but should work just can see source
    </script>
</head>

<body>
<div dojoType="cedardojo:CedarTitlePane" label="Model Instructions" labelNodeClass="titlePaneLabel" containerNodeClass="titlePaneContent">
    <table width="90%" style="padding-left:100px">
        <logic:equal value="0" property="modelId" name="biRequestForm">
            <tr><td>No Model Instructions requested</td></tr>
        </logic:equal>
        <logic:notEqual value="0" property="modelId" name="biRequestForm">
            <logic:empty name="biRequestForm" property="modelInstructions">
                <tr><td>No Instruction Available</td></tr>
            </logic:empty>
            <logic:iterate id="instruction" name="biRequestForm" property="modelInstructions" indexId="modelInstructionsId" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO">
                <tr><td>
                    <%String href = "javascript:openBI(" + instruction.getId() + ");";%>
                    <html:link href="<%=href%>">
                        <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif"/>
                        <bean:write name="instruction" property="identifier"/>
                    </html:link>
                </td></tr>
            </logic:iterate>
        </logic:notEqual>
    </table>
</div>

<div dojoType="cedardojo:CedarTitlePane" label="Cycle Instructions" labelNodeClass="titlePaneLabel" containerNodeClass="titlePaneContent">
    <table width="90%" style="padding-left:100px">
        <logic:equal value="0" property="budgetCycleId" name="biRequestForm">
            <tr><td>No Cycle Instructions requested</td></tr>
        </logic:equal>
        <logic:notEqual value="0" property="budgetCycleId" name="biRequestForm">
            <logic:empty name="biRequestForm" property="cycleInstructions">
                <tr><td>No Instruction Available</td></tr>
            </logic:empty>
            <logic:iterate id="instruction" name="biRequestForm" property="cycleInstructions" indexId="cycleInstructionsId" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO">
                <tr><td>
                    <%String href = "javascript:openBI(" + instruction.getId() + ");";%>
                    <html:link href="<%=href%>">
                        <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif"/>
                        <bean:write name="instruction" property="identifier"/>
                    </html:link>
                </td></tr>
            </logic:iterate>
        </logic:notEqual>
    </table>
</div>

<div dojoType="cedardojo:CedarTitlePane" label="Structure Element Instructions" labelNodeClass="titlePaneLabel" containerNodeClass="titlePaneContent">
    <table width="90%" style="padding-left:100px">
        <logic:equal value="0" property="structureElementId" name="biRequestForm">
            <tr><td>No Structure Element Instructions requested</td></tr>
        </logic:equal>
        <logic:notEqual value="0" property="structureElementId" name="biRequestForm">
            <logic:empty name="biRequestForm" property="structureElementInstructions">
                <tr><td>No Instruction Available</td></tr>
            </logic:empty>
            <logic:iterate id="instruction" name="biRequestForm" property="structureElementInstructions" indexId="seInstructionsId" type="com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO">
                <tr><td>
                    <%String href = "javascript:openBI(" + instruction.getId() + ");";%>
                    <html:link href="<%=href%>">
                        <img alt="" align="middle" hspace="1" src="<%=request.getContextPath()%>/images/bi.gif"/>
                        <bean:write name="instruction" property="identifier"/>
                    </html:link>
                </td></tr>
            </logic:iterate>
        </logic:notEqual>
    </table>
</div>
</body>