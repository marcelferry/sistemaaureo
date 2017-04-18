<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row">
	<form:form method="post" action="add" commandName="questaoInstituto" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="ciclo.id" />
		<form:hidden path="atividade.id" />
		<form:hidden path="instituto.id" />
		<form:hidden path="pai.id" />
		<form:hidden path="viewOrder" />

		<div class="form-group">
			<form:label class="col-sm-2 control-label" path="instituto.id">Instituto:</form:label>
			<div class="col-sm-6">
				<form:input path="instituto.descricao" cssClass="form-control input-sm" readonly="true"/>
			</div>
		</div>

		<div class="form-group">
			<form:label class="col-sm-2 control-label" path="descricao">Descrição:</form:label>
			<div class="col-sm-8">
				<form:input path="descricao" cssClass="form-control input-sm" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">Tipo de Campo:</label>
			<div class="col-sm-4">
				<form:select path="tipoCampo" cssClass="form-control input-sm" >
					  <form:option value="TEXT">Texto Aberto</form:option>
					  <form:option value="NUMBER">Numérico</form:option>
					  <form:option value="MONEY">Moeda</form:option>
					  <form:option value="SELECTUNIQUE">Seleção Única</form:option>
					  <form:option value="SELECTMULTIPLE">Seleção Múltipla</form:option>
					  <form:option value="DATE">Data</form:option>
				</form:select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input type="submit" value="Salvar" class="btn btn-primary" />
			</div>
		</div>

	</form:form>
</div>
<script type="text/javascript">
	$(function() {

	});
</script>