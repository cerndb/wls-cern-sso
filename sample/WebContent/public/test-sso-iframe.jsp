<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Public landing page</title>
</head>
<body>
	<iframe src="<%=request.getContextPath()%>/secure/redirectServlet?action=<%=request.getContextPath() %>/secure/simpleUserPrincipal"></iframe>
	<p>This is a public landing page.</p>
	<p>
		Hello <strong><%=request.getRemoteUser()%></strong>
	</p>
</body>
</html>