
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<form:form method="post" action="/gestao/planodemetas/inicio" commandName="planoMetasForm" class="form-horizontal validado">
<div class="row">
	<div class="col-md-12">
   		<form:hidden path="fase"/>
       	
        <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
        <form:hidden path="evento" value="PRERODIZIO"/>
        <div class="form-group">
			<label  class="col-sm-2 control-label">Entidade:</label>
			<div class="col-sm-6">
             	${ INSTITUICAO_CONTROLE.razaoSocial }
			</div>
		</div>
		</c:if>
	
		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
        <form:hidden path="evento" value="RODIZIO"/>
        </c:if>

		<div class="form-group">
			<form:label  class="col-sm-2 control-label"  path="rodizio.id"><spring:message code="label.rodizio" />:</form:label>
			<div class="col-sm-2">
			<c:if test="${empty CICLO_CONTROLE}">
	             <form:select path="rodizio.id" class="form-control input-sm" 
	             		data-msg-required="Selecione um ano do rodízio"
						data-rule-required="true">
				    <option value=""></option>
				    <form:options items="${rodizioList}"/>
				</form:select>
			</c:if>
			<c:if test="${!empty CICLO_CONTROLE}">
			<form:hidden path="rodizio.id" />
			${CICLO_CONTROLE.ciclo}
			</c:if>
			</div>
			<div class="col-sm-2">
				<form:errors path="rodizio.id" cssClass="error" />
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
		<div class="form-group">
			<form:hidden path="instituto.id"/>
			<c:if  test="${!empty institutoList}">
		          <div class="panel panel-primary">
		              <div class="panel-heading">
		                  Institutos Cadastrados
		              </div>
		              <!-- /.panel-heading -->
		              <div class="panel-body">
		               <div class="table-responsive">
		                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
		                    <thead>
			                    <tr>
			                        <!-- th>ID</th-->
			                        <th class="col-md-6">Nome</th>
			                        <th class="col-md-2">Já iniciado</th>
			                        <th class="col-md-2">Finalizado(Rodízio)</th>
			                        <th class="col-md-2">&nbsp;</th>
			                    </tr>
		                    </thead>
		                    <tbody>
		                    <c:forEach items="${preContratoList}" var="precontrato">
		                        <tr>
		                            <!-- td>${instituto.id}</td-->
		                            <td>${precontrato.instituto.descricao}</td>
		                            <td>${precontrato.inicializado?"Sim":"Não"}</td>
		                            <td>${precontrato.finalizadoRodizio?"Sim":"Não"}</td>
		                            <td>
		                            	<c:if test="${precontrato.finalizadoRodizio}">
			                            <input type="button" class="btn btn-danger btn-xs" value="Finalizado"/>&nbsp
		                            	</c:if>
		                            	<c:if test="${!precontrato.finalizadoRodizio}">
			                            <input type="button" onclick="iniciar(${precontrato.instituto.id})" class="btn btn-success btn-xs" value="Continuar"/>&nbsp
		                            	</c:if>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </tbody>
		                </table>
		            </div>
		           </div>
		       </div>
		   </c:if>
		</div>
	</c:if>
	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR'}">
		<form:hidden path="instituto.id"/>
		<form:hidden path="instituto.descricao"/>
		<form:hidden path="facilitador.id"/>
		<form:hidden path="facilitador.nome"/>
	</c:if>
	<c:if test="${ ROLE_CONTROLE == 'ROLE_ADMIN'}">
		<div class="form-group">
		             <form:label class="col-sm-2 control-label" path="facilitador.id">Facilitador:</form:label>
		             <div class="col-sm-6">
				<form:select path="facilitador.id" class="form-control input-sm" >
				    <option value=""></option>
				    <form:options items="${facilitadorList}"/>
				</form:select>
			</div>
		</div>
	</c:if>

	<c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_SECRETARIA' && ROLE_CONTROLE != 'ROLE_METAS_PRESIDENTE'}">
	    <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		    	<div class="col-sm-2">
			      <!-- input type="submit" value="Iniciar" class="btn btn-primary btn-mini"/ -->
			      </div>
			     
		    </div>
		 </div>
	 </c:if>
	</div>
</div>
</form:form>
<script type="text/javascript">
	function iniciar(instituto){
		$("#instituto\\.id").val(instituto);
		$("#planoMetasForm").submit();
	}
</script>