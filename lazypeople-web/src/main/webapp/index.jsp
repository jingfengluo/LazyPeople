<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	hello
	
	<form action="<%=request.getContextPath()%>/action/login" method="post">
		userName:<input type="text" name="username"/>
		userName:<input type="text" name="password"/>
		<button type="submit">submit</button>
	</form>
</body>
</html>