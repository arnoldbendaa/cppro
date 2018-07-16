    <%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
    <jsp:include page="orExcel.jsp"></jsp:include>
    
<script>

function spreadEnable(spread){
	spread.getActiveSheet().options.isProtected = true;
}

</script>
</html>
