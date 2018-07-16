<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/cp.tld" prefix="cp" %>

<head>
<title>No Security</title>
</head>

<body >
    <br/>
    <br/>
    <FONT class="textnormal">
        &nbsp;&nbsp; Access has been refused to this page. &nbsp;
    </FONT>
    <cp:CPButton buttonType="button" onclick="javascript:document.location.href='<%=request.getContextPath()%>/homePage.do';" >Work Bench</cp:CPButton>
</body>