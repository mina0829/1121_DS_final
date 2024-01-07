<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style type="text/css">
    body {
        background-image: url('itembackground1.png');
        background-size: cover;
        margin: 0;
        padding: 0;
    }

    #padding {
        padding: 0px 0px 15px 15px;
    }

    a {
        color: #FFFFFF;
        font-size: 30px;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
        color: #CCCCCC;
    }

    a img {
        width: 30px;
        height: 30px;
        margin-right: 10px;
        border-radius: 50%;
    }

    .border-style {
        border-radius: 90px/90px;
    }
</style>
</head>
<body>

<form action='${requestUri}' method='get'>

	<!-- ParentChild Pairs-->
	<div style='position: absolute; margin-top: 200px; margin-left: 50px;'>
    	<%
    HashMap<String, ArrayList<String>> parentChildPairs = (HashMap<String, ArrayList<String>>) request.getAttribute("parentChildPairs");
    String[][] query = (String[][]) request.getAttribute("query");
    for (String[] pair : query) {
        String parent = pair[0];
        String parentURL = pair[1];
    %>
    <p><a href='<%=parentURL%>'><%=parent%></a></p>
    <ul>
        <% for (String child : parentChildPairs.get(parent)) { %>
        <li style='color: #FFFFFF; font-size: 28px; text-decoration: none;'>相關網站: <a href='<%=child%>'><%=child%></a></li>
        <% } %>
    </ul>
    <%
    } %>
	</div>
   
    <div>
        <input type='text' class="border-style" id="padding" name='keyword'
               style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -250px; margin-left: -400px; width: 800px; height: 25px'
               placeholder='請輸入關鍵字' value='<%=request.getParameter("keyword")%>'/>
        <img src='./logo.png' alt="LOGO" style="margin-left: 10px; max-width: 230px; max-height: 230%;"/>
    </div>
   
    
</form>
</body>
</html>
