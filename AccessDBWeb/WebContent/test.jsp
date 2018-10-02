<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<sql:query var="rs" dataSource="jdbc/sample">
  select id, firstname, lastname, phone, email from users
</sql:query>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>DB Test</title>
</head>
<body>
	<h2>Results</h2>
	<c:forEach var="row" items="${rs.rows}"> ID ${row.id}<br />
                      Name ${row.firstname}  ${row.lastname}<br />
                      Password ${row.phone}<br />
                       email ${row.email}<br />
	</c:forEach>
</body>
</html>
