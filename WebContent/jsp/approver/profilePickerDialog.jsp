<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<script type="text/javascript">

</script>

<div dojoType="dijit.Dialog" id="profileDialog" title="DataEntry Profile Picker" style="display:none">
    <h3>Please select a Profile</h3>
    <table>
        <tr>
            <td><input id="profileCombo"></td>
            <td><cp:CPButton onclick="useProfile()">Ok</cp:CPButton></td>
            <td><cp:CPButton onclick="cancelProfile()">Cancel</cp:CPButton></td>
        </tr>
    </table>
</div>