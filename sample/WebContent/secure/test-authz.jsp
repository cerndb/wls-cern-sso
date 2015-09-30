<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Authorization test JSP</title>
</head>
<body>
	<p>If you are seeing this JSP means that you are member of the
		it-dep-db-ims group</p>
	<p>
		Hello <strong>
			<%
				request.getUserPrincipal().getName();
			%>
		</strong>
	</p>
</body>
</html>