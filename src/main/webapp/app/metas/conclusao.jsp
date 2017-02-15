<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div class="row">
	<div class="alert alert-block alert-success">
		<a class="close" data-dismiss="alert" href="#">X</a>
		<h4 class="alert-heading">Sucesso!</h4>
		Ficha de metas enviada!<br/>
		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR'}">
			<!--  Facilitador nessa fase de testes as avaliações não serão salvas -->
		</c:if>
	</div>
	
	<div class="form-group col-sm-4">
		<div class="col-sm-6">
		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
			<form:form method="post" action="inicio" commandName="planoMetasForm" class="form-horizontal">
			     <form:hidden path="fase" value="1"/>
			     <form:hidden path="rodizio.id"/>
				 <form:hidden path="instituto.id"/>
				 <form:hidden path="facilitador.id"/>
				<input id="btnSalvar" type="submit" value="Nova Entidade" class="btn btn-primary"/>
			</form:form>
		</c:if>
		<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
			<a href="/gestao/home/presidente/" class="btn btn-primary">Voltar ao menu</a> 
		</c:if>
		</div>
		<div class="col-sm-6 hidden">
		<form:form method="post" action="imprimeFichaRodizio" commandName="planoMetasForm" class="form-horizontal" target="_blank">
		     <form:hidden path="fase" value="1"/>
		     <form:hidden path="rodizio.id"/>
			 <form:hidden path="instituto.id"/>
			 <form:hidden path="entidade.id"/>
			 <form:hidden path="facilitador.id"/>
			 <form:hidden path="evento"/>
			<input id="btnSalvar" type="submit" value="Imprimir" class="btn btn-primary"/>
		</form:form>
		</div>
	</div>
</div>

