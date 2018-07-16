<%@ page import="com.cedar.cp.utc.struts.approver.ReviewBudgetForm,
                 com.cedar.cp.api.model.ReviewBudgetDetails,
                 java.util.List,
                 java.util.Iterator,
                 com.cedar.cp.api.model.ReviewBudgetRow,
                 com.cedar.cp.api.model.ReviewBudgetCellValue,
                 com.cedar.cp.util.xmlform.inputs.StructureElementReference,
                 com.cedar.cp.util.xmlform.Column,
                 com.cedar.cp.util.NumberFormatter,
                 com.cedar.cp.utc.struts.approver.LoadtestForm"%>
<%@ page language="java" %>
<%@ include file="/jsp/system/noCachingAllowed.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<body>
    <html:form action="loadtest" styleId="loadtest">
        <table>
            <tr>
                <td align="right">cpContextId</td>
                <td><html:text name="loadtestForm" property="contextId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">userId</td>
                <td><html:text name="loadtestForm" property="userId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">topNodeId</td>
                <td><html:text name="loadtestForm" property="topNodeId" readonly="true"/></td>
            </tr>
            <tr>
                <td align="right">organisationId</td>
                <td><html:text name="loadtestForm" property="organisationId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">modelId</td>
                <td><html:text name="loadtestForm" property="modelId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">budgetCycleId</td>
                <td><html:text name="loadtestForm" property="budgetCycleId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">selectionString</td>
                <td><html:text name="loadtestForm" property="selectionString" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">dataType</td>
                <td><html:text name="loadtestForm" property="dataType" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">layoutId</td>
                <td><html:text name="loadtestForm" property="layoutId" readonly="true" /></td>
            </tr>
            <tr>
                <td align="right">financeCubeId</td>
                <td><html:text name="loadtestForm" property="financeCubeId" readonly="true" /></td>
            </tr>
        </table>
        <table id="formData">
            <%
                /*
                 * NOTE !!!!!
                 * This has been written without struts tags to improve performance !!
                 */
                LoadtestForm form = (LoadtestForm) pageContext.getAttribute( "loadtestForm", PageContext.REQUEST_SCOPE );
                List formData = form.getFormData();
                for (Iterator rowIter = formData.iterator(); rowIter.hasNext();)
                {
                    ReviewBudgetRow row = (ReviewBudgetRow) rowIter.next();
                    List colData = row.getColumnData();
                    ReviewBudgetCellValue firstCell = (ReviewBudgetCellValue) colData.get( 0 );
                    StructureElementReference structRef = (StructureElementReference) firstCell.getValue();
                    // Don't output blank management codes
                    if (!structRef.isJobLabelBlankMgmtCode())
                    {
                        out.print( "<tr>" );
                        out.print( "<td>" + structRef.getId() + "</td>" );
                        out.print( "<td>" + firstCell.getValue() + "</td>" );
                        out.print( "<td>" + row.getLevel() + "</td>" );
                        out.println( "</tr>" );
                    }
                }
            %>
        </table>
    </html:form>
</body>