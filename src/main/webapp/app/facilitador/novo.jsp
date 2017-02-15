<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

      <div class="row">
	            <form:form method="post" action="atividades" commandName="facilitadorForm" class="form-horizontal">
					<form:hidden path="rodizio.id"/>
					<form:hidden path="instituto.id"/>
					
					<div class="form-group">
						<form:label  class="col-sm-2 control-label"  path="rodizio.id">Rodízio:</form:label>
						<div class="col-sm-6">
		                	<form:select path="rodizio.id" class="form-control input-sm" >
							    <option value=""></option>
							    <form:options items="${rodizioList}"/>
							</form:select>
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="instituto.id">Instituto:</form:label>
		                <div class="col-sm-6">
							<form:hidden path="instituto.id"/>
			                <form:input path="instituto.descricao" placeholder="Selecione o Instituto/Comissão"  class="form-control"/>	
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="trabalhador.id">Trabalhador:</form:label>
		                <div class="col-sm-6">
							<form:hidden path="trabalhador.id"/>
			                <form:input path="trabalhador.nome" placeholder="Selecione o trabalhador"  class="form-control" autocomplete="off"/>
						</div>
					</div>
			         <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <input type="button" value="Salvar" onclick="this.form.action = 'add';submit();" class="btn btn-principal"/>
					    </div>
					  </div>
					  <!-- input type="submit" class="btn btn-primary btn-mini" value="Ver Facilitadores" /-->
					  <form:errors path="*" cssClass="error-message-list" element="div" />
	            </form:form>
	    </div>
	    <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
	    <script type="text/javascript" src="/js/custom/autocompleteinstituto.js"></script>
		<script type="text/javascript">
		$(function(){

			var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";

			completePessoa($('#trabalhador\\.nome'), $("#trabalhador\\.id"), baseUrl);
			completeInstituto($('#instituto\\.descricao'), $("#instituto\\.id"), baseUrl);
			
		});
	</script>