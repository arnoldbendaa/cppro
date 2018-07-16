<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="dev" scope="application" name="cpSystemProperties" property="dev"/>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/libs/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: false"></script>
<%--<script type="text/javascript" language="javascript" src="/dojo/1.6.1/dijit/dijit-all.js"></script>--%>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/js/dojo/ourDojo.js"></script>

<style type="text/css">
    @import "<%=request.getContextPath()%>/css/coa_dojo_1_9_3.css";
    @import "<%=request.getContextPath()%>/libs/dojo/dojox/grid/resources/Grid.css";
    @import "<%=request.getContextPath()%>/libs/dojo/dojox/grid/resources/soriaGrid.css";
    @import "<%=request.getContextPath()%>/libs/dojo/dojox/grid/enhanced/resources/EnhancedGrid.css";
    @import "<%=request.getContextPath()%>/libs/dojo/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
</style>
