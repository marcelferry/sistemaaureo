<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
<!--
.ui-datepicker { font-size: 9pt !important; }
-->
</style>


      <div class="row">
            <form:form method="post" action="add" commandName="entidade" class="form-horizontal validado">
            		<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="tipoEntidade">Tipo Entidade</form:label>
	                	<div class="col-sm-4">
		                	<select name="tipoEntidade" id="tipoEntidade" class="form-control" data-rule-required="true" data-msg-required="Selecione o tipo da entidade">
							    <option value=""></option>
							        <c:forEach items="${tipoEntidadeList}" var="option">
							        
							        	<c:choose>
										      <c:when test="${option eq entidade.tipoEntidade}">
													<option selected="selected" value="${option}">
								                    <c:out value="${option}"></c:out>
								                </option>
										      </c:when>
										      <c:otherwise>
											      <option value="${option}">
								                    <c:out value="${option}"></c:out>
								                  </option>
										      </c:otherwise>
										</c:choose>
							        	
							        </c:forEach>
							</select>
		                </div>
		            </div>
					<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="cnpj">CNPJ</form:label>
	                	<div class="col-sm-5">
		                	<form:input path="cnpj" cssClass="form-control" 
		                	data-rule-cnpj="true"
		                	data-rule-remote="/gestao/entidade/validateCnpjUnique"
		                	data-msg-remote="CNPJ Já cadastrado."/>
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="razaoSocial">Razão Social</form:label>
	                	<div class="col-sm-10">
	                		<form:input path="razaoSocial" cssClass="form-control upper" 
	                			data-rule-required="true" data-msg-required="Informe o nome da entidade"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cep">CEP</form:label>
	                	<div class="col-sm-3">
	                		<div class="input-group">
	                			<form:input path="endereco.cep" cssClass="form-control cep" />
	                			<span class="input-group-btn">
		                			<button id="buscarcep" class="btn btn-primary external" type="button" >Buscar</button>
		                		</span>
		                	</div>
	                		<div id="mensagemErro" class="ocultar"></div>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.logradouro">Logradouro</form:label>
	                	<div class="col-sm-10">
	                		<form:input path="endereco.logradouro" cssClass="form-control upper" 
	                			data-rule-required="true" data-msg-required="Informe o endereço"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.numero">Número</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.numero" cssClass="form-control upper" 
	                			data-rule-required="true" data-msg-required="Informe o número"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.complemento">Complemento</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="endereco.complemento" cssClass="form-control upper" />
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.bairro">Bairro</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="endereco.bairro" cssClass="form-control upper" />
	                	</div>
		            </div>	            
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="endereco.cidade.nome">Cidade</form:label>
		                <div class="col-sm-5">
			                <form:hidden path="endereco.cidade.id" />
							<form:input path="endereco.cidade.nome" cssClass="form-control upper" 
							data-rule-required="true" 
	                		data-msg-required="Cidade é obrigatório"
	                		autocomplete="off"/>
						</div>
					</div>
		            
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cidade.estado.sigla">UF</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.cidade.estado.sigla" cssClass="form-control upper"
	                		data-rule-required="true" 
	                		data-msg-required="Estado é obrigatório"/>
	                	</div>
		            </div>
	            
		           <div class="form-group">
			            <form:label class="col-sm-2 control-label" path="telefones">Telefones</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="telefones">
	   						<tr>
	   							<td>Tipo</td>
	   							<td>DDD</td>
	   							<td>Número</td>
	   							<td>Operadora</td>
	   							<td>Ação</td>
	   						</tr>
		                    <c:forEach items="${entidade.telefones}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${entidade.telefones[loop.index].remove eq 1}">
		                            	<c:set var="cssTelefoneHidden" value="hidden"/>
		                            </c:when>
		                            <c:otherwise>
		                            	<c:set var="cssTelefoneHidden" value=""/>
		                            </c:otherwise>
		                        </c:choose>
		                        <tr id="telefones${loop.index}.wrapper" class=" ${cssTelefoneHidden}">
		               			 <td>
		               			    <form:hidden path="telefones[${loop.index}].id"/>
		                        	<select name="telefones[${loop.index}].tipo" id="telefones${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipotelefoneList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq entidade.telefones[loop.index].tipo}">
															<option selected="selected" value="${option}">
										                    <c:out value="${option}"></c:out>
										                </option>
												      </c:when>
												      <c:otherwise>
													      <option value="${option}">
										                    <c:out value="${option}"></c:out>
										                  </option>
												      </c:otherwise>
												</c:choose>
									        	
									        </c:forEach>
									</select>	
									</td>
		                            <td>
		                            <!-- Generate the fields -->
		                           		 <form:input path="telefones[${loop.index}].ddd" placeholder="DDD"  class="form-control input-sm" />
		                            </td>
		                            <td>
		                            	<form:input path="telefones[${loop.index}].numero" placeholder="Número Telefone" class="form-control input-sm" />
		                            </td>
		                            <td>
		                            	<form:input path="telefones[${loop.index}].operadora" placeholder="Operadora"  class="form-control input-sm upper" />
		                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${entidade.telefones[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
		                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
		                            </c:choose>
		                            <form:hidden path="telefones[${loop.index}].remove" value="${hiddenValue}" />
		                            
		                            <button class="btn  btn-danger btn-xs  telefones.remove" data-index="${loop.index}">
		                            	<span class="glyphicon glyphicon-remove-sign">
			  							</span>
		                            </button>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </table>
		                    <button id="addTelefones" type="button" class="btn btn-primary btn-xs">Novo</button>
		                </div>
		              </div>
	     			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="emails">Contatos Internet</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="emails">
	   						<tr>
	   							<td class="col-sm-2">Tipo</td>
	   							<td class="col-sm-8">Contato</td>
	   							<td class="col-sm-2">Ação</td>
	   						</tr>
		                    <c:forEach items="${entidade.emails}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${entidade.emails[loop.index].remove eq 1}">
		                               <c:set var="cssInternetHidden" value="hidden"/>
		                            </c:when>
		                            <c:otherwise>
		                               <c:set var="cssInternetHidden" value=""/>
		                            </c:otherwise>
		                        </c:choose>
		                        <tr id="emails${loop.index}.wrapper" class=" ${cssInternetHidden}">
		               			 <td>
		               			    <form:hidden path="emails[${loop.index}].id"/>
		                        	<select name="emails[${loop.index}].tipo" id="emails${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipocontatoList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq entidade.emails[loop.index].tipo}">
															<option selected="selected" value="${option}">
										                    <c:out value="${option}"></c:out>
										                </option>
												      </c:when>
												      <c:otherwise>
													      <option value="${option}">
										                    <c:out value="${option}"></c:out>
										                  </option>
												      </c:otherwise>
												</c:choose>
									        </c:forEach>
									</select>	
									</td>
		                            <td>
		                            <!-- Generate the fields -->
		                           		 <form:input path="emails[${loop.index}].contato" placeholder="Contato"  class="form-control input-sm" />
		                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${entidade.emails[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
		                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
		                            </c:choose>
		                            <form:hidden path="emails[${loop.index}].remove" value="${hiddenValue}" />
		                            
		                            <button class="btn  btn-danger btn-xs  emails.remove" data-index="${loop.index}">
		                            	<span class="glyphicon glyphicon-remove-sign">
			  							</span>
		                            </button>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </table>
		                    <button id="addEmails" type="button" class="btn btn-primary btn-xs">Novo</button>
		                </div>
		              </div>
	              
						<div class="form-group">
							<form:hidden path="presidente.id"/>
			                <form:hidden path="presidente.pessoa.id"/>
			                <form:hidden path="presidente.ativo" value="true"/>
			                <form:label  class="col-sm-2 control-label" path="presidente.pessoa.nome">Presidente Atual</form:label>
		                	<div class="col-sm-10">
		                		<form:input path="presidente.pessoa.nome" cssClass="form-control" />
		                	</div>
			            </div>
		         <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
                			<button type="submit" class="btn btn-primary btn-mini">Salvar</button>
                	</div>
				</div>
        	</form:form>
        </div>
        <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
        <script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
  <script>

  var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
  
  $(function() {
	  $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
	  $('.cep').mask('00000-000');
      $('.datepicker').datepicker({
			format: "dd/mm/yyyy",
			autoclose: true,
			language: 'pt-BR'
		});

      completePessoa($('#presidente\\.pessoa\\.nome'), $("#presidente\\.pessoa\\.id"), baseUrl );
	  completeCidade($('#endereco\\.cidade\\.nome'), $('#endereco\\.cidade\\.id'), baseUrl, function(item){
		  	$('#endereco\\.cidade\\.estado\\.sigla').val(item.uf);
	        $('#endereco\\.cidade\\.estado\\.sigla').attr('readonly', true);
	        return item.name;
		});

	  
   // Start indexing at the size of the current list
      var indexTelefones = ${fn:length(entidade.telefones)};
      var indexEmails = ${fn:length(entidade.emails)};

      // Add a new telefone
      $("#addTelefones").off("click").on("click", function() {
          $("#telefones tr:last").after(function() {
              var html = '<tr id="telefones' + indexTelefones + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="telefones[' + indexTelefones + '].tipo" id="telefones' + indexTelefones + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipotelefoneList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" size="4" id="telefones' + indexTelefones + '.ddd" name="telefones[' + indexTelefones + '].ddd" placeholder="DDD"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="text" size="10" id="telefones' + indexTelefones + '.numero" name="telefones[' + indexTelefones + '].numero" placeholder="Número Telefone"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="text" id="telefones' + indexTelefones + '.operadora" name="telefones[' + indexTelefones + '].operador" placeholder="Operadora"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="telefones' + indexTelefones + '.remove" name="telefones[' + indexTelefones + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs telefones.remove" data-index="' + indexTelefones + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#telefones" + indexTelefones + "\\.wrapper").removeClass('hidden');
          $(".telefones\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#telefones" + index2remove + "\\.wrapper").addClass('hidden');
              $("#telefones" + index2remove + "\\.remove").val("1");
              return false;
          });
          indexTelefones++;
          return false;
      });

      // Remove a telefone
      $(".telefones\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#telefones" + index2remove + "\\.wrapper").addClass('hidden');
          $("#telefones" + index2remove + "\\.remove").val("1");
          return false;
      });
      
      // Add a new email
      $("#addEmails").off("click").on("click", function() {
          $("#emails tr:last").after(function() {
              var html = '<tr id="emails' + indexEmails + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="emails[' + indexEmails + '].tipo" id="emails' + indexEmails + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipocontatoList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" id="emails' + indexEmails + '.contato" name="emails[' + indexEmails + '].contato" placeholder="Contato"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="emails' + indexEmails + '.remove" name="emails[' + indexEmails + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs emails.remove" data-index="' + indexEmails + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#emails" + indexEmails + "\\.wrapper").removeClass('hidden');
          $(".emails\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
              $("#emails" + index2remove + "\\.remove").val("1");
              return false;
          });
          indexEmails++;
          return false;
      });

      // Remove an email
      $(".emails\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
          $("#emails" + index2remove + "\\.remove").val("1");
          return false;
      });

      /**
       * Atribuo ao elemento #cep, o evento blur
       * Blur, dispara uma ação, quando o foco
       * sair do elemento, no nosso caso cep
       */
      $("#buscarcep").click(function(){
          /**
           * Resgatamos o valor do campo #cep
           * usamos o replace, pra remover tudo que não for numérico,
           * com uma expressão regular
           */
          var cep     = $("#endereco\\.cep").val().replace(/[^0-9]/, '');
           //Armazena a referência da div#mensagemErro
          var msgErro = $("#mensagemErro");
           
          /**
           * Por padrão, vamos ocultar
           * div#boxCampos e também #mensagemErro
           */
          msgErro.addClass('ocultar');
           
          //Verifica se não está vazio
          if(cep !== ""){
               //Cria variável com a URL da consulta, passando o CEP
               var url = 'http://cep.correiocontrol.com.br/'+cep+'.json';
                
               /**
                * Fazemos um requisição a URL, como vamos retornar json,
                * usamos o método $.getJSON;
                * Que é composta pela URL, e a função que vai retorna os dados
                * o qual passamos a variável json, para recuperar os valores
                */
               $.getJSON(url, function(json){
                      //Atribuimos o valor aos inputs
                      $("#endereco\\.logradouro").val(json.logradouro);
                      $("#endereco\\.bairro").val(json.bairro);
                      $("#endereco\\.cidade\\.nome").val(json.localidade);
                      $("#endereco\\.cidade\\.estado\\.sigla").val(json.uf);
                      
                      if($("#endereco\\.logradouro").val().trim() != '' ){
	                         $("#endereco\\.logradouro").attr('readonly', true);
	                         $("#endereco\\.bairro").attr('readonly', true);
	                         $("#endereco\\.cepUnico").val(false);
                      } else {
                    	  $("#endereco\\.logradouro").attr('readonly', false);
	                      $("#endereco\\.bairro").attr('readonly', false);
                    	  $("#endereco\\.cepUnico").val(true);
                      }
                      $("#endereco\\.buscaCep").val(true);
                      $("#endereco\\.cidade\\.id").val('');
                      $("#endereco\\.cidade\\.nome").attr('readonly', true);                       
                      $("#endereco\\.cidade\\.estado\\.sigla").attr('readonly', true);
                      /**
                       * Removemos a classe ocultar, para mostrar os campos
                       * preenchidos
                       */
                       
                      //Usamos o método fail, caso não retorne nada
                  }).fail(function(){
                   //Não retornando um valor válido, ele mostra a mensagem
                   msgErro.removeClass('ocultar').html('CEP inexistente');
                    $("#endereco\\.cepUnico").val(false);
                    $("#endereco\\.buscaCep").val(false);
                    $("#endereco\\.logradouro").attr('readonly', false);
                    $("#endereco\\.bairro").attr('readonly', false);
	                $("#endereco\\.cidade\\.nome").attr('readonly', false);                       
	                $("#endereco\\.cidade\\.estado\\.sigla").attr('readonly', false);
                   
              });
          }
      });
      
  });
  </script>