<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<logic:present scope="request" name="org.apache.struts.action.ERROR" >

<div class="bg-danger softproErrors">
    <html:errors />
</div>

</logic:present>

