<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code="app.name" /></title>

    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">
    

</head>
<body>
<div class="container">
	<h2>Ocorreu um erro na aplicação</h2>
	 
	<br/>
	<br/>
	<br/>
	<div class="span6 offset3 center">
		<span class="alert alert-error">
			Contate-nos pelo email: <a href="mailto:sistemadegestaodemetas@gmail.com">sistemadegestaodemetas@gmail.com</a>.
		</span>
		<br/><br/>
	</div>
	
	<!--
    Failed URL: ${url}
    Exception:  ${exception.message}
    <c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
    </c:forEach>
    -->
	
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.js"></script>
</div>
</body>
</html>