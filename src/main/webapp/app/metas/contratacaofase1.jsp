<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<fmt:setBundle basename="messages" var="i18n" />

<style>

<c:if test="${!erros}">
#div_planometas, #div_botoes, #div_outros, #div_outros2, #div_coordenador, #div_coordenador2, #div_presidente, #div_endereco, #div_entidade {
	display:none;
}
</c:if>

.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
    .border-radius( 5px );
}

#scrollable-dropdown-menu .tt-dropdown-menu {
  max-height: 150px;
  overflow-y: auto;
}

</style>

<form:form method="post" action="atividades" commandName="planoMetasForm" class="form-horizontal validado" role="form">
	<form:hidden path="id"/>
	<form:hidden path="fase"/>
	<form:hidden path="rodizio.id"/>
	<form:hidden path="instituto.id"/>
	<form:hidden path="facilitador.id"/>
	<form:hidden path="evento"/>
	<form:hidden path="validado"/>
	<form:hidden path="finalizado"/>
	<form:hidden path="emailPresidente"/>
	<form:hidden path="telefonePresidente"/>
	<form:hidden path="nomeContratante"/>
	
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>Instituto</legend>
				<div class="form-group small">
					<label class="col-sm-2 col-xs-3 control-label">Ano:</label>
					<div class="col-sm-3 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.rodizio.ciclo}</div>
					<c:if test="${planoMetasForm.evento == 'RODIZIO'}">
					<label class="col-sm-2 col-xs-3 control-label">Facilitador:</label>
					<div class="col-sm-5 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.facilitador.nome} </div>
					</c:if>
				</div>
				<div class="form-group small">
					<label class="col-sm-2 col-xs-3 control-label">Instituto:</label>
					<div class="col-sm-3 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.instituto.descricao}</div>
					<label class="col-sm-2 col-xs-3 control-label">Dirigente Nacional:</label>
					<div class="col-sm-5 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.instituto.dirigenteNacional.nome}</div>
				</div>
			</fieldset>
		</div>
	</div>
	
	<!-- Validação Secretaria -->
	<c:if test="${planoMetasForm.fase == 4}">
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>Entidade</legend>
				<div class="form-group small">
		            <label class="col-sm-2 col-xs-3 control-label" for="entidade.razaoSocial">Entidade:</label>
	              	<div class="col-sm-4 col-xs-6">
	              	    <form:hidden path="entidade.id"/>
	              	    <form:hidden path="entidade.razaoSocial" />
	               		<div class="control-label" style="text-align: left;">${ planoMetasForm.entidade.razaoSocial }</div>
	               </div>
	           </div>
		       <div  class="form-group small">
		            <label class="col-sm-2 col-xs-3 control-label"><spring:message code="label.endereco" />:</label>
		            <label class="col-sm-4 col-xs-9 control-label" style="text-align: left;"><span id="entidade.endereco">${ planoMetasForm.entidade.endereco.enderecoFormatado }</span></label>
		            <c:if test="${ planoMetasForm.entidade.primeiroTelefone != 'N/A' }">
		            <label class="col-sm-2 col-xs-3 control-label">Telefone:</label>
		            <label class="col-sm-4 col-xs-9 control-label" style="text-align: left;"><span id="entidade.telefone">${ planoMetasForm.entidade.primeiroTelefone }</span></label>
		           	</c:if> 
		   		</div>
		        <div class="form-group">
					<label class="col-sm-2 col-xs-3 control-label"></label>
					<form:errors path="tipoContratante" cssClass="error" />
				</div>
		     	<div class="form-group">
		        	<label class="col-sm-2 control-label" for="presidente.nome">Presidente:</label>
		            <div class="col-sm-6">
		            	<div class="input-group">
		     				<span class="input-group-addon"> 
		               			<form:radiobutton path="tipoContratante" value="PRESIDENTE" data-rule-required="true" data-msg-required="Selecione o contratante"/>
		               		</span>
		               		<form:hidden path="presidente.id" />
		               		<form:input path="presidente.nome" class="form-control  input-sm" placeholder="Selecione o presidente da entidade" onblur="limparOcultos(this, 'presidente.id')" autocomplete="false"/>
							<!--<span class="input-group-btn">
		               			<button class="btn btn-primary" type="button" >Novo</button>
		               		</span>-->
		               	</div>
		          	</div>
		            <div class="col-sm-2 col-xs-3">
						<form:errors path="presidente.id" cssClass="error" />
					</div>
		      	</div>
		        <div class="form-group">
		        	<label class="col-sm-2 control-label" for="coordenador.nome">Coordenador:</label>
	            	<div class="col-sm-6">
	              		<div class="input-group">
	     					<span class="input-group-addon">
		               			<form:radiobutton path="tipoContratante" value="COORDENADOR"/>
		               		</span>
		               		<form:hidden path="coordenador.id" />
		               		<spring:message code="hint.selecione.coordenador" var="hint_selecione_coordenador" />
		               		<form:input path="nomeCoordenador" class="form-control input-sm" placeholder="${hint_selecione_coordenador}" onblur="limparOcultos(this, 'coordenador.id')" autocomplete="false"/>
							<!--<span class="input-group-btn">
		               			<button class="btn btn-primary" type="button" >Novo</button>
		               		</span>-->
	                	</div>
	            	</div>
	           		<div class="col-sm-2 col-xs-3">
						<form:errors path="coordenador.id" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-3 col-sm-offset-2">
		            	<form:input path="emailCoordenador" class="form-control input-sm" placeholder="Email do Coordenador" />
		           	</div>
		          	<div class="col-sm-3">
		           		<form:input path="telefoneCoordenador" class="form-control input-sm" placeholder="Telefone do Coordenador" />
		          	</div>
		      	</div>
		      	<div class="form-group">
		        	<label class="col-sm-2 control-label" for="outro.nome">Outro:</label>
		            <div class="col-sm-6">
		            	<div class="input-group">
		     				<span class="input-group-addon">
		               			<form:radiobutton path="tipoContratante" value="OUTRO"/>
		               		</span>
		               		<form:hidden path="outro.id" />
		               		<spring:message code="hint.selecione.responsavel" var="hint_selecione_responsavel" />
		               		<c:if test="${not empty planoMetasForm.outro.id }">
		               		<form:input path="outro.nome" class="form-control input-sm" placeholder="${hint_selecione_responsavel}" onblur="limparOcultos(this, 'outro.id')" autocomplete="false"/>
							</c:if>
		               		<c:if test="${ empty planoMetasForm.outro.id }">
		               		<form:input path="outro.nome"  value="${ planoMetasForm.nomeContratante }" class="form-control input-sm" placeholder="${hint_selecione_responsavel}" onblur="limparOcultos(this, 'outro.id')" autocomplete="false"/>
							</c:if>
							<!--<span class="input-group-btn">
		               			<button class="btn btn-primary" type="button" >Novo</button>
		               		</span>-->
		             	</div>
		        	</div>
		            <div class="col-sm-2 col-xs-3">
						<form:errors path="outro.id" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-3 col-sm-offset-2">
		            	<form:input path="emailContratante" class="form-control input-sm" placeholder="Email do Contratante" />
		             </div>
		          	<div class="col-sm-3">
		           		<form:input path="telefoneContratante" class="form-control input-sm" placeholder="Telefone do Contratante" />
		          	</div>
		        </div>
		       	<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
		            	<input id="btnSalvar" type="submit" value="Iniciar Rodizio" class="btn btn-primary"/>
		          	</div>
				</div>
         	</fieldset>
        </div>
    </div>
	</c:if>
		
		
	<c:if test="${ (ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' ) && planoMetasForm.fase == 2}">
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>Entidade</legend>
				<!-- Parte do facilitador -->
				<div class="form-group">
		            <label class="col-sm-2 col-xs-3 control-label" for="cidade">Cidade:</label>
		              	<div id="scrollable-dropdown-menu" class="col-sm-3 col-xs-4">
		              		<form:hidden path="cidade.id" />
		               	<form:input path="cidade.nome" class="form-control" placeholder="Digite ao menos 2 caracteres" type="text" onblur="limparOcultos(this, 'cidade.id')" autocomplete="off"/>
		               </div>
		           </div>
				<div id="div_entidade" class="form-group">
		            <label class="col-sm-2 col-xs-3 control-label" for="entidade.razaoSocial">Entidade:</label>
		              	<div class="col-sm-4 col-xs-6">
		              	    <form:hidden path="entidade.id"/>
		               	<form:input path="entidade.razaoSocial" class="form-control" placeholder="Selecione a entidade" onblur="limparOcultos(this, 'entidade.id')" autocomplete="off"/>
		               </div>
		               <div class="col-sm-2 col-xs-3">
							<form:errors path="entidade.id" cssClass="error" />
					</div>
		           </div>
				<div id="div_endereco" class="form-group">
		            <label class="col-sm-2 col-xs-3 control-label"><spring:message code="label.endereco"/>:</label>
		               <label class="col-sm-4 col-xs-9 control-label" style="text-align: left;"><span id="entidade.endereco">&nbsp;</span></label>
		            <label class="col-sm-2 col-xs-3 control-label">Telefone:</label>
		               <label class="col-sm-4 col-xs-9 control-label" style="text-align: left;"><span id="entidade.telefone">&nbsp;</span></label>
		           </div>
		           <div class="form-group">
					<label class="col-sm-2 col-xs-3 control-label"></label>
					<form:errors path="tipoContratante" cssClass="error" />
				</div>
		           <div id="div_presidente" class="form-group">
		            <label class="col-sm-2 control-label" for="presidente.nome">Presidente:</label>
		              	<div class="col-sm-6">
		               	<div class="input-group">
		     					<span class="input-group-addon">
		               			<form:radiobutton path="tipoContratante" value="PRESIDENTE" data-rule-required="true" data-msg-required="Selecione o contratante"/>
		               		</span>
		               		<form:hidden path="presidente.id" />
		               		<form:input path="presidente.nome" class="form-control  input-sm" placeholder="Selecione o presidente da entidade" onblur="limparOcultos(this, 'presidente.id')" autocomplete="false"/>
							<!--<span class="input-group-btn">
		               			<button class="btn btn-primary" type="button" >Novo</button>
		               		</span>-->
		                </div>
		               </div>
		               <div class="col-sm-2 col-xs-3">
							<form:errors path="presidente.id" cssClass="error" />
					</div>
		           </div>
		           <div id="div_coordenador">
		           	<div class="form-group">
			            <label class="col-sm-2 control-label" for="coordenador.nome">Coordenador:</label>
		               	<div class="col-sm-6">
		               		<div class="input-group">
		      					<span class="input-group-addon">
		                			<form:radiobutton path="tipoContratante" value="COORDENADOR"/>
		                		</span>
		                		<form:hidden path="coordenador.id" />
		                		<spring:message code="hint.selecione.coordenador" var="hint_selecione_coordenador" />
		                		<form:input path="nomeCoordenador" class="form-control input-sm" placeholder="${hint_selecione_coordenador}" onblur="limparOcultos(this, 'coordenador.id')" autocomplete="false"/>
								<!--<span class="input-group-btn">
		                			<button class="btn btn-primary" type="button" >Novo</button>
		                		</span>-->
			                </div>
		                </div>
		                <div class="col-sm-2 col-xs-3">
								<form:errors path="coordenador.id" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 col-sm-offset-2">
		              			<form:input path="emailCoordenador" class="form-control input-sm" placeholder="Email do Coordenador" />
		              		</div>
		           		<div class="col-sm-3">
		            			<form:input path="telefoneCoordenador" class="form-control input-sm" placeholder="Telefone do Coordenador" />
		           		</div>
		          		</div>
		           </div>
		           <div id="div_outros" >
		           	<div class="form-group">
			            <label class="col-sm-2 control-label" for="outro.nome">Outro:</label>
		               	<div class="col-sm-6">
		               		<div class="input-group">
		      					<span class="input-group-addon">
		                			<form:radiobutton path="tipoContratante" value="OUTRO"/>
		                		</span>
		                		<form:hidden path="outro.id" />
		                		<spring:message code="hint.selecione.responsavel" var="hint_selecione_responsavel" />
		                		<c:if test="${not empty planoMetasForm.outro.id }">
		                		<form:input path="outro.nome" class="form-control input-sm" placeholder="${hint_selecione_responsavel}" onblur="limparOcultos(this, 'outro.id')" autocomplete="false"/>
								</c:if>
		                		<c:if test="${ empty planoMetasForm.outro.id }">
		                		<form:input path="outro.nome"  value="${ planoMetasForm.nomeContratante }" class="form-control input-sm" placeholder="${hint_selecione_responsavel}" onblur="limparOcultos(this, 'outro.id')" autocomplete="false"/>
								</c:if>
								<!--<span class="input-group-btn">
		                			<button class="btn btn-primary" type="button" >Novo</button>
		                		</span>-->
			                </div>
		                </div>
		                <div class="col-sm-2 col-xs-3">
								<form:errors path="outro.id" cssClass="error" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 col-sm-offset-2">
		              			<form:input path="emailContratante" class="form-control input-sm" placeholder="Email do Contratante" />
		              		</div>
		           		<div class="col-sm-3">
		            			<form:input path="telefoneContratante" class="form-control input-sm" placeholder="Telefone do Contratante" />
		           		</div>
		          		</div>
		           </div>
		           <div  id="div_botoes" class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
		              			<button id="btnSalvar" type="submit" class="btn btn-primary">Iniciar Rodizio</button>
		              	</div>
					</div>
			</fieldset>
		</div>
	</div>
	</c:if>
</form:form>
  
<script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
<script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>

<script>



	function updateCoordenador(item){
		$('#emailCoordenador').val(item.email);
		return item.name;
	}
	
   $(function() {

  	 completePessoa($('#presidente\\.nome'), $("#presidente\\.id"), BASEURL );
  	 completePessoa($('#nomeCoordenador'), $("#coordenador\\.id"), BASEURL , updateCoordenador);
  	 completePessoa($('#outro\\.nome'), $("#outro\\.id"), BASEURL );
  	 completeCidade($('#cidade\\.nome'), $('#cidade\\.id'), BASEURL , function(item){
		$("#div_entidade").fadeIn('slow');
	    $("#entidade\\.razaoSocial").focus();
        return item.name;
	}, true);
	
	$('#entidade\\.razaoSocial').typeahead({
		    source: function (query, process) {
		        return $.ajax({
		        	url: BASEURL + '/gestao/entidade/list',
		            type: 'Get',
		            data: { maxRows: 6, query: query, cidade: $('#cidade\\.id').val(), instituto: $('#instituto\\.id').val(), rodizio: $('#rodizio\\.id').val() },
		            dataType: 'json',
		            global: false,
		            success: function (result) {

		                var resultList = result.map(function (item) {
		                	var aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone };

		                	if(item.presidente != null && item.presidente.id != null ){
		                		aItem['idPresidente'] = item.presidente.id; 
		                		aItem['nomePresidente'] = item.presidente.nome;
 	 			            } else {
 	 			         		aItem['idPresidente'] = ''; 
	                		aItem['nomePresidente'] = '';
 	 	 	 			    }

		                	if( item.dirigente != null && item.dirigente.nome != null ){
		                		aItem['idDirigente'] = item.dirigente.id; 
		                		aItem['nomeDirigente'] = item.dirigente.nome;
		                		aItem['emailDirigente'] = item.dirigente.email;
		                		aItem['telefoneDirigente'] = item.dirigente.telefone;
 	 			            } else {
 	 			         		aItem['idDirigente'] = ''; 
	                		aItem['nomeDirigente'] = '';
	                		aItem['emailDirigente'] = '';
	                		aItem['telefoneDirigente'] = '';
 	 	 	 			    }
 	 	 	 			    
		                	if( item.outro != null && item.outro.nome != null ){
		                		aItem['idOutro'] = item.outro.id; 
		                		aItem['nomeOutro'] = item.outro.nome;
		                		aItem['emailOutro'] = item.outro.email;
		                		aItem['telefoneOutro'] = item.outro.telefone;
 	 			            } else {
 	 			         		aItem['idOutro'] = ''; 
	                		aItem['nomeOutro'] = '';
	                		aItem['emailOutro'] = '';
	                		aItem['telefoneOutro'] = '';
 	 	 	 			    }
 	 	 	 			   
		                    return JSON.stringify(aItem);
		                });

		                return process(resultList);

		            }
		        });
		    },

			matcher: function (obj) {
		        var item = JSON.parse(obj);
		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase());
		    },

		    sorter: function (items) {          
		       var beginswith = [], caseSensitive = [], caseInsensitive = [], item;
		        while (aItem = items.shift()) {
		            var item = JSON.parse(aItem);
		            if (!item.name.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(JSON.stringify(item));
		            else if (~item.name.indexOf(this.query)) caseSensitive.push(JSON.stringify(item));
		            else caseInsensitive.push(JSON.stringify(item));
		        }

		        return beginswith.concat(caseSensitive, caseInsensitive)

		    },


		    highlighter: function (obj) {
		        var item = JSON.parse(obj);
		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
		        var name = item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
		            return '<strong>' + match + '</strong>'
		        })
		        
		        var itm = ''
	                 + "<div class='typeahead_wrapper'>"
	                 //+ "<img class='typeahead_photo' src='" + name + "' />"
	                 + "<div class='typeahead_labels'>"
	                 + "<div class='typeahead_primary'>" + name + "</div>"
	                 + "<div class='typeahead_secondary'>" + item.endereco + "</div>"
	                 + "</div>"
	                 + "</div>";
	        	return itm;
		    },

		    updater: function (obj) {
		        var item = JSON.parse(obj);
		           $('#entidade\\.id').attr('value', item.id);
		           $('#entidade\\.endereco').text(item.endereco);
		           $('#entidade\\.telefone').text(item.telefone);
		           $('#entidade\\.telefone').text(item.telefone);
		           $('#presidente\\.id').val(item.idPresidente);
		           $('#presidente\\.nome').val(item.nomePresidente);
		           if(item.nomeDirigente != undefined && item.nomeDirigente != '' ){
			           $('#coordenador\\.id').val(item.idDirigente);
			           $('#nomeCoordenador').val(item.nomeDirigente);
			           $('#emailCoordenador').val(item.emailDirigente);
			           $('#telefoneCoordenador').val(item.telefoneDirigente);
		           } else {
		        	   $('#coordenador\\.id').val("");
			           $('#nomeCoordenador').val("");
			           $('#emailCoordenador').val("");
			           $('#telefoneCoordenador').val("");
		           }
		          if(item.nomeOutro != undefined && item.nomeOutro != '' ){
			           $('#outro\\.id').val(item.idOutro);
			           $('#outro\\.nome').val(item.nomeOutro);
			           $('#emailContratante').val(item.emailOutro);
			           $('#telefoneContratante').val(item.telefoneOutro);
	           		} else {
	           			$('#outro\\.id').val("");
				           $('#outro\\.nome').val("");
				           $('#emailContratante').val("");
				           $('#telefoneContratante').val("");
	           		}
		           $("#div_coordenador").fadeIn('slow');
	           $("#div_coordenador2").fadeIn('slow'); 
			       $("#div_endereco").fadeIn('slow');
			       $("#div_presidente").fadeIn('slow');
			       $("#div_outros").fadeIn('slow');
			       $("#div_outros2").fadeIn('slow');
 			   $("#div_botoes").fadeIn('slow');
	       
		        return item.name;
		    }
		});
       
   });

   function limparOcultos(campo, oculto){
       if(campo.value == ''){
       	$(oculto).val('');
       }
   }
  
  </script>