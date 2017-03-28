<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      <div class="row">
 
            <form:form method="post" action="add" commandName="instituto" class="form-horizontal">
				<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="descricao">Nome</form:label>
		                <div class="col-sm-10">
		                	<form:input path="descricao"  cssClass="form-control"/>
		                </div>
		            </div>
		         <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="nome">Abreviação</form:label>
		                <div class="col-sm-10">
		                	<form:input path="nome" cssClass="form-control"/>
		                </div>
		            </div>
                <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="objetivo">Objetivo</form:label>
		                <div class="col-sm-10">
		                	<form:textarea path="objetivo"  cssClass="form-control"/>
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="dirigenteNacional.id">Dirigente Nacional:</form:label>
		                <div class="col-sm-6">
							<form:hidden path="dirigenteNacional.id"/>
			                <form:input path="dirigenteNacional.nome" placeholder="Selecione o trabalhador"  class="form-control"/>	
						</div>
					</div>
                <div class="form-group">
	            	<div class="col-sm-offset-2 col-sm-10">
	            		<div class="checkbox">
                			<form:checkbox path="rodizio" /> Participa do Rodizio
                		</div>
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
        <script type="text/javascript">
		$(function(){
			var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
			completePessoa($('#dirigenteNacional\\.nome'), $("#dirigenteNacional\\.id"), baseUrl );
		});
        </script>
