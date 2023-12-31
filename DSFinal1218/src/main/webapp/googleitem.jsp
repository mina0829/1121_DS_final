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
#padding{
	padding: 0px 0px 15px 15px;
}
a {
	color: #FFFFFF; 
    font-size: 30px;
    text-decoration: none;
}
a:hover{
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
<!--把query結果放到網頁裡>
	<!--key值為title><-->
	<div style='position: absolute;margin-top:190px;margin-left:50px'>
		<%
		String[][] orderList = (String[][]) request.getAttribute("query");
		for (int i = 0; i < orderList.length; i++) {
			String s=orderList[i][1];
			// if (s.length() > 7) {
		            //s = s.substring(7);
		      //  }
			//s=s.substring(7);
		%>
		
		<a href='<%=s%>'>
                <img src='music.png' alt='Image'>
                <%=orderList[i][0]%> </a> <br><br>
		<br>
		<%
}
%>
	</div>
	<div>
	</div>
		<div>
		<input type='text' class="border-style" id="padding" name='keyword'
			style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -250px; margin-left: -400px; width: 800px; height: 25px'
			placeholder = '請輸入關鍵字' value='<%=request.getParameter("keyword")%>'/>
			 <img src='./logo.png' alt="LOGO" style="margin-left: 10px; max-width: 230px; max-height: 230%;"/>
	</div>

</form>
</body>
</html>
