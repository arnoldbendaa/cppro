<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
 <head>
  <link href="./css/commons.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <title>Product List</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Group Report List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>Code</th>
          <th>Name</th>
          <th>Price</th>
          <th>Edit</th>
          <th>Delete</th>
       </tr>
       <c:forEach items="${productList}" var="product" >
          <tr>
             <td>${product.code}</td>
             <td>${product.name}</td>
             <td>${product.price}</td>
             <td>
                <a href="editProduct?code=${product.code}">Edit</a>
             </td>
             <td>
                <a href="deleteProduct?code=${product.code}">Delete</a>
             </td>
          </tr>
       </c:forEach>
    </table>
 
    <a href="createProduct" >Create Report</a>
    
    <a href="excelview.html">Edit ExcelSheet</a>
 
  <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>Company Key</th>
          <th>Company value</th>
          <th>Company Name</th>
          <th>Edit</th>
          <th>Delete</th>
       </tr>
       <c:forEach items="${productList}" var="product" >
          <tr>
             <td>${product.code}</td>
             <td>${product.name}</td>
             <td>${product.price}</td>
             <td>
                <a href="editProduct?code=${product.code}">Edit</a>
             </td>
             <td>
                <a href="deleteProduct?code=${product.code}">Delete</a>
             </td>
          </tr>
       </c:forEach>
    </table>
 
  <ul>
         <li>Budget Cycles - Access a list of budget cycles.</li>
         <li>Budget Limits - Set / View Budget Limits.</li>
         <li>Budget Transfers - Authorise budget transfers.</li>
         <li>Budget Transfers - Maintain budget transfers.</li>
         <li>Budget Transfers Query - Query budget transfers.</li>
         <li>Diagnostic Lists - Additional lists for diagnostics.</li>
         <li>Form Report - Show list of finance forms.</li>
         <li>Logon History - Show the logon history.</li>
         <li>Report Definitions - Show the list of Report Definitions.</li>
         <li>Task Reports - Show the list of background task reports.</li>
         <li>Tasks - Show the list of background tasks.</li>
         <li>Top Down Budgeting - Apply rule based changes to cells.</li>
         
      </ul>
 
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>