<%@ page isErrorPage="true" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>

<head>
    <title>Collaborative Planning System Error</title>
</head>

<body >
	<h1 class="header1">Status Code 500</h1>
    <span class="syserror">
        An Error ocurred on the server, please try again!<br>
        <br>
        If the problem persists please contact your system administrator.<br>
        <br>
  	    Additional Information For Your System Administrator is:
<br><%= request.getAttribute( "javax.servlet.error.message" ) %>

<pre>
<%
    if (exception != null)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        out.print(sw);
        sw.close();
        pw.close();
    }
    else if (request.getAttribute( "javax.servlet.error.exception" ) != null)
    {
        ((Exception)request.getAttribute( "javax.servlet.error.exception" )).printStackTrace(new PrintWriter(out));
    }
%> 
</pre>
    </span>
</body>
