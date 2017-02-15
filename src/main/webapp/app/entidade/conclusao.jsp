<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div class="row">
	<div class="alert alert-block alert-success">
		<a class="close" data-dismiss="alert" href="#">X</a>
		<h4 class="alert-heading">Sucesso!</h4>
		Registro atualizado!
	</div>
	
	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
		<a href="/gestao/entidade/" class="btn btn-primary">Voltar a lista</a> 
	</c:if>
	<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
		<a href="/gestao/home/presidente/" class="btn btn-primary">Voltar ao Painel de Controle</a> 
	</c:if>
</div>

