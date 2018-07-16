<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp"  %>

<head>
</head>
<body>

<html:form action="/communicationNewSetup">


<table border="1" width="590px" halign="center">
    <thead>
     <tr bgcolor="GRAY">
         <th colspan="2">Budget Responsibility Area</th>
         <th colspan="2">Assigned Ownership</th>
         <th>Read/Write</th>
         <th>Contact</th>
    </tr>
    <tr>
         <th>Identifier</th>
         <th>Description</th>
         <th>User Id</th>
         <th>Name</th>
         <th></th>
         <th><INPUT TYPE="checkbox" id="contactAll" NAME="contactAll" onchange="checkAll()" title="Select/de-select all"/>  </th>
    </tr>

    </thead>
      <div dojoType="dijit.layout.ContentPane" region="center" widgetId="assignMain" style="padding-left:20px">
            <logic:iterate id="assignment" name="assignmentForm" property="assignments" type="com.cedar.cp.utc.struts.admin.report.assignment.AssignmentDTO" offset="assignmentForm.maxResult">
                <tr>
                    <td><bean:write name="assignment" property="elementId"/></td>
                    <td><bean:write name="assignment" property="elementDescription"/></td>
                    <td><bean:write name="assignment" property="userId"/></td>
                    <td><bean:write name="assignment" property="fullName"/></td>
                    <td align="center"><bean:write name="assignment" property="read"/></td>
                    <td align="center"><INPUT TYPE="checkbox" id="contact<bean:write name="assignment" property="userId"/>" NAME="contact<bean:write name="assignment" property="userId"/>" VALUE="<bean:write name="assignment" property="userId"/>"></td>
                </tr>
            </logic:iterate>
    </div>
    <tr>
        <td align="right" colspan="6">
            <cp:CPButton onclick="callMessaging()" title="Create Message to holders">
                Contact
            </cp:CPButton>
        </td>
    </tr>
</table>
</body>
    </html:form>
