<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
      
            <form:form method="post" action="save/${userprofile.id}" commandName="userprofile" class="form-horizontal">
            	<form:hidden path="id" />
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="username">Nome Usuário</form:label>
                	<div class="col-sm-4">
                		<form:input path="username"  cssClass="form-control" />
                	</div>
	            </div>
	            <div class="form-group">
	            	<form:hidden path="pessoa.id"/>
	                <form:label  class="col-sm-2 control-label" path="pessoa.nome">Caravaneiro</form:label>
                	<div class="col-sm-10">
                		<form:input path="pessoa.nome" cssClass="form-control" />
                	</div>
                </div>
                <div class="form-group">
		            <form:label class="col-sm-2 control-label" path="password">Senha</form:label>
                	<div class="col-sm-6">
                		<form:password path="password"  cssClass="form-control" />
                	</div>
	            </div>
    
                 <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
                			<input type="submit" value="Salvar" class="btn btn-primary btn-mini"/>
                	</div>
				</div>
            </form:form>
        </div>   
        <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
  <script>

  var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
  
  $(function() {
	  
      completePessoa($('#pessoa\\.nome'), $("#pessoa\\.id"), baseUrl );   
  });
 </script>