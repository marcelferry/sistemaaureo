<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags/input/"  prefix="input"%>
<%@taglib tagdir="/WEB-INF/tags/display/"  prefix="display"%>

    <div class="row">
      <div class="col-md-12">
        <h4>${metaForm.descricao}</h4>
        <form:form method="post" action="save/${metaForm.id}" commandName="metaForm" class="form-horizontal" onsubmit="return validaForm(this); return false;">
        	<form:hidden path="id"/>
        	<form:hidden path="previsao"/>
        	<form:hidden path="previsto"/>
        	<form:hidden path="tipoSituacao"/>
        	<form:hidden path="situacaoDesejada.responsavel.id" value="${user.pessoa.id}"/>
        	
        	<table class="table">
        		<tr>
        			<td class="col-md-2"><label class="control-label">Instituição</label></td>
        			<td class="col-md-10" colspan="3">${metaForm.entidade.razaoSocial}</td>
        		</tr>
        		<tr>
        			<td><label class="control-label">Cidade/Estado</label></td>
        			<td colspan="3">${metaForm.entidade.cidade}/${metaForm.entidade.uf}</td>
        		</tr>
        		<tr>
        			<td class="col-md-2"><label class="control-label">Instituto/Comissão</label></td>
        			<td class="col-md-10" colspan="3">${metaForm.instituto.descricao}</td>
        		</tr>
        		<tr>
        			<td><label class="control-label">Meta/Atividade</label></td>
        			<td colspan="3">${metaForm.descricaoCompleta}</td>
        		</tr>
        		<tr>
        			<td class="col-md-2"><label class="control-label">Estado Atual</label></td>
        			<td class="col-md-4">${metaForm.situacao}</td>
<c:if test="${metaForm.tipoSituacao == 'PRECONTRATAR' || metaForm.tipoSituacao == 'CONTRATAR' || metaForm.tipoSituacao == 'REPLANEJAR'}">
<c:if test="${metaForm.tipoMeta != 'META_QUANTITATIVA'}">
        			<td class="col-md-2"><label class="control-label">Previsão</label></td>
        			<td class="col-md-4"><fmt:formatDate value="${metaForm.previsao}" pattern="MMM/yyyy"/></td>
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA'}">
        			<td class="col-md-2"><label class="control-label">Previsto</label></td>
        			<td class="col-md-4">${metaForm.previsto}</td>
</c:if>
</c:if>
        		</tr>
<c:if test="${ editMode }">
        		<tr>
        			<td class="col-md-2"><label class="control-label">Novo Status</label></td>
        			<td class="col-md-4">
        				<form:select path="situacaoDesejada.tipoSituacao" cssClass="form-control">
        					<form:option value="" label="---" />
        					<form:options />
        				</form:select>
        			</td>
<c:if test="${metaForm.tipoMeta != 'META_QUANTITATIVA'}">
        			<td class="col-md-2">
        				<label id="previsaolbl" class="control-label">Previsão</label>
        				<label id="conclusaolbl" class="control-label">Conclusão</label>
        			</td>
        			<td class="col-md-4">
        				<input:monthYear id="situacaoDesejadaprevisao" name="situacaoDesejada.previsao" />
        				<input:monthYear id="situacaoDesejadaconclusao" name="situacaoDesejada.conclusao" />
        			</td>
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA'}">
        			<td class="col-md-2">
						<label id="previsaolbl" class="control-label">Previsto</label>
        				<label id="conclusaolbl" class="control-label">Realizado</label>
        			</td>
        			<td class="col-md-4">
        				<form:input path="situacaoDesejada.previsto" />
        				<form:input path="situacaoDesejada.realizado" />
        			</td>
</c:if>
        		</tr>

				<tr>
					<c:set var="indexObs" value="${fn:length(metaForm.observacoes)}" scope="request"/>
					<td><form:label path="observacoes[${indexObs}].texto">Comentário:</form:label></td>
					<td colspan="3">
					  	<form:hidden path="observacoes[${indexObs}].nivel" value="META_POSRODIZIO"/>
					  	<form:hidden path="observacoes[${indexObs}].responsavel.id"  value="${user.pessoa.id}"/>
					  	<form:hidden path="observacoes[${indexObs}].sinalizador" value="VERDE"/>
						<form:textarea path="observacoes[${indexObs}].texto" rows="5" class="form-control col-md-12 input-sm"/>
					</td>
				</tr>
</c:if>
        	</table>
<c:if test="${ editMode }">
        	 <div class="form-group">
			    <div class="col-sm-offset-2 pull-right col-sm-10">
			      <input type="submit" value="Salvar" class="btn btn-primary"/>
			    </div>
			  </div>
</c:if>
        	<hr/>
        	<h3>Anotações</h3>
        	<table class="table small">
        		<thead>
	        		<tr>
	        			<th class="col-md-2">Data</th>
	        			<th class="col-md-4">Responsável</th>
	        			<th class="col-md-6">Texto</th>
	        		</tr>
        		</thead>
        		<tbody>
						<c:forEach items="${metaForm.observacoes}" var="observacao">
						<tr>
							<td><fmt:formatDate value="${observacao.data}" pattern="dd/MM/yyyy"/></td>
							<td>${observacao.responsavel.nome}</td>
							<td>${observacao.texto}</td>
						</tr>
						</c:forEach>
        		</tbody>
        	</table>
        	<hr/>
        	<h3>Histórico Metas</h3>
        	<table class="table small">
        		<thead>
	        		<tr>
	        			<th class="col-md-2">Data</th>
	        			<th class="col-md-2">Evento</th>
	        			<th class="col-md-6">Responsável</th>
	        			<th class="col-md-2">Situação</th>
	        		</tr>
        		</thead>
        		<tbody>
						<c:forEach items="${metaForm.historico}" var="historico">
						<tr>
							<td><fmt:formatDate value="${historico.dataSituacao}" pattern="dd/MM/yyyy"/></td>
							<td>${historico.tipoSituacao}</td>
							<td>${historico.responsavel.nome}<sup style="color: red;">${historico.tipoResponsavel}</sup></td>
							<td>
<c:if test="${metaForm.tipoMeta != 'META_QUANTITATIVA'}">
								${historico.situacao}
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA' && ( historico.tipoSituacao == 'INICIAL' || historico.tipoSituacao == 'CONCLUIR' || historico.tipoSituacao == 'CONCLUIR_PARCIALMENTE')}">
								${historico.realizado}
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA' && ( historico.tipoSituacao == 'PRECONTRATAR' || historico.tipoSituacao == 'CONTRATAR' || historico.tipoSituacao == 'REPLANEJAR')}">
								${historico.previsto}
</c:if>
							</td>
						</tr>
						</c:forEach>
        		</tbody>
        	</table>
        	<hr/>
        	<h3>Contatos</h3>
        	<table class="table small">
        		<thead>
	        		<tr>
	        			<th class="col-md-2">Tipo</th>
	        			<th class="col-md-4">Nome</th>
	        			<th class="col-md-3">Telefones</th>
	        			<th class="col-md-3">Emails</th>
	        		</tr>
        		</thead>
        		<tbody>
						<c:forEach items="${listContatos}" var="contato">
						<tr>
							<td>${contato.tipo}</td>
							<td>${contato.nomeCompleto}</td>
							<td>
							<c:forEach items="${contato.telefones}" var="telefone">
								${ telefone } <br/>
							</c:forEach>
							</td>
							<td>
							<c:forEach items="${contato.emails}" var="email">
								${ email }  <br/>
							</c:forEach>
							</td>
						</tr>
						</c:forEach>
        		</tbody>
        	</table>
		</form:form>
      </div>
    </div>
    <script>

    $(function() {
	    $("#situacaoDesejada\\.tipoSituacao option[value='INICIAL']").attr("disabled","disabled");
	    $("#situacaoDesejada\\.tipoSituacao").children("option[value='INICIAL']").remove();
	    $("#situacaoDesejada\\.tipoSituacao option[value='PRECONTRATAR']").attr("disabled","disabled");
	    $("#situacaoDesejada\\.tipoSituacao").children("option[value='PRECONTRATAR']").remove();
	    $("#situacaoDesejada\\.tipoSituacao option[value='CONTRATAR']").attr("disabled","disabled");
	    $("#situacaoDesejada\\.tipoSituacao").children("option[value='CONTRATAR']").remove();
	    $("#situacaoDesejada\\.tipoSituacao option[value='AVALIAR']").attr("disabled","disabled");
	    $("#situacaoDesejada\\.tipoSituacao").children("option[value='AVALIAR']").remove();

	    var conclusaolbl = $("#conclusaolbl");
		var previsaolbl = $("#previsaolbl");
<c:if test="${metaForm.tipoMeta != 'META_QUANTITATIVA'}">
		var campoconclusao = $("#situacaoDesejadaconclusao");
		var campoprevisao = $("#situacaoDesejadaprevisao");
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA'}">
		var campoconclusao = $("#situacaoDesejada\\.realizado");
		var campoprevisao = $("#situacaoDesejada\\.previsto");
</c:if>
		conclusaolbl.hide();
		previsaolbl.hide();
		campoconclusao.hide();
		campoprevisao.hide();

	    $("#situacaoDesejada\\.tipoSituacao ").change(function(){         
	    	var value = $("#situacaoDesejada\\.tipoSituacao option:selected").val();
			if(value == 'REPLANEJAR'){
				conclusaolbl.hide();
				previsaolbl.show();
				campoconclusao.hide();
				campoconclusao.val('');
				campoprevisao.show();
			}
			if(value == 'CONCLUIR'){
				conclusaolbl.show();
				previsaolbl.hide();
				campoconclusao.show();
				campoprevisao.hide();
				campoprevisao.val('');
				//TODO: definir o valor de previsto para realizado em caso de conclusao
			}
			if(value == 'CONCLUIR_PARCIALMENTE'){
				conclusaolbl.show();
				previsaolbl.hide();
				campoconclusao.show();
				campoprevisao.hide();
				campoprevisao.val('');
			}	
			if(value == 'CANCELAR' || value == ''){
				conclusaolbl.hide();
				previsaolbl.hide();
				campoconclusao.hide();
				campoconclusao.val('');
				campoprevisao.hide();
				campoprevisao.val('');
			}	
	    });
    });


    function validaForm(form0){
<c:if test="${metaForm.tipoMeta != 'META_QUANTITATIVA'}">
    	return validaFormData(form0);
</c:if>
<c:if test="${metaForm.tipoMeta == 'META_QUANTITATIVA'}">
    	return validaFormNumerico(form0);
</c:if>
    }
    
    function validaFormNumerico(form0){
    	var value = $("#situacaoDesejada\\.tipoSituacao option:selected").val();
    	var campoObservacoes = $("#observacoes${indexObs}\\.texto");
    	if(value == 'REPLANEJAR'){
    		var campoNovaPrevisao = $("#situacaoDesejada\\.previsto");
    		var novaPrevisao = campoNovaPrevisao.val();
    		
			if(novaPrevisao == ""){
				campoNovaPrevisao.closest("tr").addClass("danger");
   	   			alert("Em caso de replanejamento, campo Previsto é obrigatório.");	
   	   			return false;			
			}
    		
   			if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de replanejamento, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   			return false;
   	   		}

   	   		return true;
		}
		if(value == 'CONCLUIR'){
			var previsto = $("#previsto").val();
    		var campoconclusao = $("#situacaoDesejada\\.realizado");
    		var conclusao = campoconclusao.val();

    		if(conclusao == ""){
    			campoconclusao.closest("tr").addClass("danger");
   	   			alert("Em caso de Conclusão/Implantação, campo Realizado é obrigatório.");	
   	   			return false;			
			}

			if(conclusao < previsto){
				campoconclusao.closest("tr").addClass("danger");
   	   			alert("Em caso de Conclusão/Implantação, campo Realizado deve ser igual ou maior do que o campo Previsto. Caso tenha feito menos do que o previsto utilize a opção Concluir_Parcialmente.");	
   	   			return false;
			}

    		return true;
		}
		if(value == 'CONCLUIR_PARCIALMENTE'){
			
			var previsto = $("#previsto").val();
    		var campoconclusao = $("#situacaoDesejada\\.realizado");
    		var conclusao = campoconclusao.val();

    		if(conclusao >= previsto){
    			campoconclusao.closest("tr").addClass("danger");
   	   			alert("O campo Realizado é maior ou igual ao campo Previsto. Utilize a opção 'Concluir' neste caso");	
   	   			return false;
			}
			
    		if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de conclusão/implantação parcial, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   			return false;
   	   		}
   	   		return true;
		}	
		if(value == 'CANCELAR' || value == ''){
			if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de cancelamento de uma meta, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   			return false;
   	   		}
   	   		return true;
		}	

		return false;
    	
    }
    
    function validaFormData(form0){
    	var self = form0;
    	var value = $("#situacaoDesejada\\.tipoSituacao option:selected").val();
    	var previsao = $("#previsao").val();
    	var campoObservacoes = $("#observacoes${indexObs}\\.texto");
    	if(value == 'REPLANEJAR'){
    		var campoNovaPrevisao = $("#situacaoDesejadaprevisao");
    		var novaPrevisao = campoNovaPrevisao.val();

    		if(novaPrevisao == ""){
				campoNovaPrevisao.closest("tr").addClass("danger");
   	   			alert("Em caso de replanejamento, campo Previsão é obrigatório.");		
   	   			return false;		
			}

    		var onedate = previsao.split("/");
    		var twodate = novaPrevisao.split("/");

    		var onemonth = onedate[0];
    		var oneyear = onedate[1];

    		var twomonth = twodate[0];
    		var twoyear = twodate[1];
    		

    		if (new Date(oneyear, onemonth) > new Date(twoyear, twomonth)){
    			campoNovaPrevisao.closest("tr").addClass("danger");
   			  alert('Nova data prevista deve ser maior que a data prevista atual.');
   			  return false;
   			}

   			if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de replanejamento, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   			return false;
   	   		}

   	   		return true;
		}
		if(value == 'CONCLUIR'){
			var campoconclusao = $("#situacaoDesejadaconclusao");
    		var conclusao = campoconclusao.val();

    		if(conclusao == ""){
    			campoconclusao.closest("tr").addClass("danger");
   	   			alert("Em caso de Conclusão/Implantação, campo Conclusão é obrigatório.");		
   	   			return false;		
			}

    		var onedate = previsao.split("/");
    		var twodate = conclusao.split("/");

    		var onemonth = onedate[0];
    		var oneyear = onedate[1];

    		var twomonth = twodate[0];
    		var twoyear = twodate[1];

    		if (new Date(oneyear, onemonth) < new Date(twoyear, twomonth)){
   			  alert('Você está concluindo isto antes do prazo previsto. Parabéns!');
   			}

   			return true;
		}
		if(value == 'CONCLUIR_PARCIALMENTE'){
			var campoconclusao = $("#situacaoDesejadaconclusao");
    		var conclusao = campoconclusao.val();

    		if(conclusao == ""){
    			campoconclusao.closest("tr").addClass("danger");
   	   			alert("Em caso de Conclusão/Implantação parcial, campo Conclusão é obrigatório.");		
   	   			return false;		
			}
			
    		if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de conclusão/implantação parcial, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   			return false;
   	   		}

   	   		return true;
		}	
		if(value == 'CANCELAR' || value == ''){
			if(campoObservacoes.val().length < 30){
   	   			alert("Em caso de cancelamento de uma meta, uma justificativa deve ser informada no campo observações com pelo menos 30 caracteres.");
   	   		}

   	   		return true;
		}	

		return false;
    	
    }
    </script>
    