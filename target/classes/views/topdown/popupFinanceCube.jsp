<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<head>
    <title><bean:message key="cp.massupdate.popup.financecube.header.title"/></title>
    <bean:define id="listSize" type="Integer" property="size" name="massupdatePopupForm"></bean:define>
    <script language="javascript" type="text/javascript">
        function returnValue()
        {
            var name = "";
            var id = "";
            var select_obj;
            var name_obj;
            var id_obj;

            for (var i = 0; i < <%=listSize%>; i++)
            {
                select_obj = getDocumentObject("select_" + i);
                if (select_obj.checked)
                {
                    name_obj = getDocumentObject("selected_id_" + i);
                    name = name_obj.value;

                    id_obj = getDocumentObject("selected_visid_" + i);
                    id = id_obj.value;
                }
            }

            if (window.opener.setFinanceCube != null)
            {
                window.opener.setFinanceCube(name, id);
            }

			self.close();
        }

        function singleSelect(newSelection)
        {
            var select_obj;
            for (var i = 0; i < <%=listSize%>; i++)
            {
                if (i == newSelection)
                    continue;

                select_obj = getDocumentObject("select_" + i);
                select_obj.checked = false;
            }
        }
    </script>
</head>

<body>

<div dojoType="dijit.layout.BorderContainer" class="main" gutters="false">

    <div dojoType="dijit.layout.ContentPane" region="center" widgetId="fcClient" style="padding-left:20px;padding-right:18px;overflow:auto;">
        <table width="100%">
            <tr>
                <th>
                    <bean:message key="cp.massupdate.popup.model.heading1"/>
                </th>
                <th>
                    <bean:message key="cp.massupdate.popup.model.heading2"/>
                </th>
                <th>
                    <bean:message key="cp.massupdate.popup.model.heading3"/>
                </th>
            </tr>
            <logic:iterate id="line" name="massupdatePopupForm" property="selection" type="com.cedar.cp.utc.struts.topdown.PopupDTO" indexId="rowIndex">
                <tr>
                    <td align="center">
                        <input type="checkbox" id="select_<%=rowIndex%>" name="select_<%=rowIndex%>" onclick="singleSelect(<%=rowIndex%>)"/>
                    </td>
                    <td>
                        <bean:write name="line" property="visId"/>
                    </td>
                    <td>
                        <bean:write name="line" property="description"/>
                        <input type="hidden" id='<%="selected_id_" + rowIndex %>' value='<%=line.getId()%>' name='<%="selected_id_" + rowIndex %>'/>
                        <input type="hidden" id='<%="selected_visid_" + rowIndex %>' value='<%=line.getVisId()%>' name='<%="selected_visid_" + rowIndex %>'/>
                    </td>
                </tr>
            </logic:iterate>
        </table>
    </div>

    <div dojoType="dijit.layout.ContentPane" region="bottom" widgetId="fcFooter"  style="padding-left:20px">
        <table width="100%">
            <tr>
                <td colspan="2" width="60%" nowrap="true">&nbsp;</td>
                <td align="right" nowrap="true">
                    <cp:CPButton buttonType="button" onclick="returnValue()">
                        <bean:message key="cp.commumication.message.label.ok"/>
                    </cp:CPButton>
                    <cp:CPButton buttonType="button" onclick="self.close()">
                        <bean:message key="cp.commumication.message.label.close"/>
                    </cp:CPButton>
                </td>
                <td align="center">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
</div>
</body>