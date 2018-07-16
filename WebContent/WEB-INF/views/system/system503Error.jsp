<%@ page isErrorPage="true" %>

<head>
    <title>Collaborative Planning User Error</title>
</head>

<body >
	<h1 class="header1">Status Code 503</h1>
    <span class="syserror">
        An Error ocurred on the server.<br><br>
  	    Additional Information For Your System Administrator is: <%= request.getAttribute( "javax.servlet.error.message" ) %>
    </span>
</body>
