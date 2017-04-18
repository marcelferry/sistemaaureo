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
			Erro: [[${errorMessage}]]<br>
			Contate-nos pelo email: <a href="mailto:sistemadegestaodemetas@gmail.com">sistemadegestaodemetas@gmail.com</a>.
		
		</span>
		<br/><br/>
	</div>
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/exceptionhandler.js"></script>
</div>
</body>
</html>