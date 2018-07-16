<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>
<bean:define id="logLevel" scope="application" name="cpSystemProperties" property="logLevel"/>
<cp:applet nameID='<%=request.getParameter("app_name")%>' width='<%=request.getParameter("app_width")%>' height='<%=request.getParameter("app_height")%>'
		   appClass='<%=request.getParameter("app_class")%>' requestContext='<%=request.getContextPath()%>'
		   extraParamNames='<%=request.getParameter("app_params")%>' extraParamValues='<%=request.getParameter("app_values")%>' logLevel="<%=logLevel.toString()%>"/>