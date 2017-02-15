<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code="app.name"/></title>

    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">
    

</head>
<body>
<div class="container">
	<h2><spring:message code="titulo.erro.aplicacao" /></h2>
	<br/>
	<br/>
	<div class="span6 offset3 center">
		<span class="alert alert-error">
			Contate-nos pelo email: <a href="mailto:sistemadegestaodemetas@gmail.com">sistemadegestaodemetas@gmail.com</a>.
		</span>
		<br/>
	</div>
	<br/>
	<div class="accordion" id="accordion2">
	  <div class="accordion-group">
	    <div class="accordion-heading">
	      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
	        Detalhes do erro:
	      </a>
	    </div>
	    <div id="collapseOne" class="accordion-body collapse">
	      <div class="accordion-inner">
	      <textarea rows="10" cols="80">
	       <!--
		    Failed URL: ${url}
		    Exception:  ${exception.message}
		    <c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
		    </c:forEach>
		    -->
		   </textarea>
	      </div>
	    </div>
	  </div>
   </div>
	
	<!--
    Failed URL: ${url}
    Exception:  ${exception.message}
    <c:forEach items="${exception.stackTrace}" var="ste">    ${ste} 
    </c:forEach>
    -->
	
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script type="text/javascript">
    	$(".collapse").collapse();
    </script>
</div>
</body>
</html>