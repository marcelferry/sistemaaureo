<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
<!--
.ui-datepicker { font-size: 9pt !important; }
-->
</style>
      <div class="row">
	            <form:form method="post" action="add" commandName="rodizioForm" class="form-horizontal">
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="ciclo">Ano</form:label>
		                <div class="col-sm-2">
		                	<form:input path="ciclo" cssClass="form-control" />
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="dataAprovacao">Data do Rodízio</form:label>
		                <div class="col-sm-3">
		                	<form:input path="dataAprovacao" cssClass="form-control datepicker" />
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="inicio">Data Início Vigência</form:label>
		                <div class="col-sm-3">
							<form:input path="inicio" cssClass="form-control datepicker" />
							
						</div>
					</div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="termino">Data Término Vigência</form:label>
		                <div class="col-sm-3">
							<form:input path="termino" cssClass="form-control datepicker" />
							
						</div>
					</div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="inicioAjustes">Data Início Ajustes</form:label>
		                <div class="col-sm-3">
							<form:input path="inicioAjustes" cssClass="form-control datepicker" />
							
						</div>
					</div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="terminoAjustes">Data Término Ajustes</form:label>
		                <div class="col-sm-3">
							<form:input path="terminoAjustes" cssClass="form-control datepicker" />
							
						</div>
					</div>
					
					<div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="cicloAnterior.id">Ciclo Anterior</form:label>
		                <div class="col-sm-3">
							<form:select path="cicloAnterior.id" class="form-control">
							    <option value=""></option>
							    <form:options items="${rodizioList}" itemValue="id" itemLabel="ciclo"/>
							</form:select>
							
						</div>
					</div>
					
					<div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="ativo">Ciclo Atual</form:label>
		                <div class="col-sm-3">
							<form:select path="ativo"  cssClass="form-control">
								<form:option value="false">Não</form:option>
								<form:option value="true">Sim</form:option>
							</form:select>
							
						</div>
					</div>
					
					
		            
	                <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <input type="submit" value="Incluir Rodízio" class="btn btn-primary"/>
					    </div>
					  </div>
	                
	            </form:form>
	    </div>
	<script type="text/javascript">
		$(function(){
			$('.datepicker').datepicker({
					format: "dd/mm/yyyy",
					autoclose: true,
					language: 'pt-BR'
			});
			
		});
	</script>