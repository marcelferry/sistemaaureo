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
            <form:form method="post" action="save/${pessoa.id}"  commandName="pessoa" class="form-horizontal">
            <ul class="nav nav-tabs" id="tabpessoa" role="tablist">
			  <li class="active"><a href="#dadosgerais" role="tab" data-toggle="tab">Dados Gerais</a></li>
			  <li><a href="#documentos" role="tab" data-toggle="tab">Documentos</a></li>
			  <li><a href="#endereco" role="tab" data-toggle="tab">Endereço</a></li>
			  <li><a href="#contato" role="tab" data-toggle="tab">Contato</a></li>
			  <li><a href="#centro" role="tab" data-toggle="tab">Centro</a></li>
		 	  <li><a href="#observacoes" role="tab" data-toggle="tab">Observações</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="dadosgerais">
					<br/>
	                <div class="form-group">
	                	<form:hidden path="id"/>
			            <form:label class="col-sm-2 control-label" path="nomeCompleto">Nome Completo</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="nomeCompleto"  cssClass="form-control" />
	                	</div>
		            </div>
		            
		            <div class="form-group">
			            <form:label class="col-sm-2 control-label" path="nomeCracha">Nome Crachá</form:label>
	                	<div class="col-sm-5">
	                		<form:input path="nomeCracha"  cssClass="form-control" />
	                	</div>
		            </div>
	    
	    			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="dataNascimento">Nascimento</form:label>
	                	<div class="col-sm-3">
	                		<form:input path="dataNascimento"  cssClass="form-control datepicker data" />
	                	</div>
		            </div>
	    
	    			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="naturalidade">Naturalidade</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="naturalidade"  cssClass="form-control" />
	                	</div>
		            </div>
	    
	    			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="nacionalidade">Nacionalidade</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="nacionalidade"  cssClass="form-control" />
	                	</div>
		            </div>
		        </div>
	            <div class="tab-pane" id="documentos">
	            	<br/>
	            	 <div class="form-group">
			            <form:label class="col-sm-2 control-label" path="cpf">CPF</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="cpf"  cssClass="form-control cpf" />
	                	</div>
		            </div>
		            
	            	<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="documentoId">RG</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="documentoId"  cssClass="form-control" />
	                	</div>
		            </div>
		            
	    			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="orgaoEmissor">Orgão Emissor</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="orgaoEmissor"  cssClass="form-control" />
	                	</div>
			            <form:label class="col-sm-2 control-label" path="ufEmissor">UF Emissor</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="ufEmissor"  cssClass="form-control" />
	                	</div>
			            <form:label class="col-sm-2 control-label" path="dataEmissao">Data Emissão</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="dataEmissao"  cssClass="form-control datepicker data" />
	                	</div>
		            </div>
	    
		            <div class="form-group">
			            <form:label class="col-sm-2 control-label" path="estadoCivil">Estado Civil</form:label>
	                	<div class="col-sm-4">
		                	<select name="estadoCivil" id="estadoCivil" class="form-control" >
							    <option value=""></option>
							        <c:forEach items="${estadocivilList}" var="option">
							        
							        	<c:choose>
										      <c:when test="${option eq pessoa.estadoCivil}">
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
	            </div>
	            <div class="tab-pane" id="endereco">
	            	<br/>
		            <div class="form-group">
		            	<form:hidden path="endereco.id" />
		            	<form:hidden path="endereco.buscaCep" />
	                	<form:hidden path="endereco.cepUnico" />
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
	                		<form:input path="endereco.logradouro" cssClass="form-control" readonly="${pessoa.endereco.cepUnico == false && pessoa.endereco.buscaCep == true }"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.numero">Número</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.numero" cssClass="form-control" />
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.complemento">Complemento</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="endereco.complemento" cssClass="form-control" />
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.bairro">Bairro</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="endereco.bairro" cssClass="form-control" readonly="${pessoa.endereco.cepUnico == false && pessoa.endereco.buscaCep == true }"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="endereco.cidade.nome">Cidade</form:label>
		                <div class="col-sm-5">
			                <form:hidden path="endereco.cidade.id" />
							<form:input path="endereco.cidade.nome" cssClass="form-control" readonly="${ pessoa.endereco.buscaCep == true }" autocomplete="off"/>
						</div>
					</div>
		            
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cidade.estado.sigla">UF</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.cidade.estado.sigla" cssClass="form-control" readonly="${ pessoa.endereco.buscaCep == true }"/>
	                	</div>
		            </div>
	            </div>
	            <div class="tab-pane" id="contato">
	           	 	<br/>
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
		                    <c:forEach items="${pessoa.telefones}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${pessoa.telefones[loop.index].remove eq 1}">
		                                <tr id="telefones${loop.index}.wrapper" class="hidden">
		                            </c:when>
		                            <c:otherwise>
		                               <tr id="telefones${loop.index}.wrapper">
		                            </c:otherwise>
		                        </c:choose>
		               			 <td>
		               			    <form:hidden path="telefones[${loop.index}].id"/>
		                        	<select name="telefones[${loop.index}].tipo" id="telefones${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipotelefoneList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq pessoa.telefones[loop.index].tipo}">
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
		                            	<form:input path="telefones[${loop.index}].operadora" placeholder="Operadora"  class="form-control input-sm" />
		                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${pessoa.telefones[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
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
		                    <button id="addTelefones" type="button">Novo Telefone</button>
		                </div>
		              </div>
	     			<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="emails">Internet</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="emails">
	   						<tr>
	   							<td class="col-sm-2">Tipo</td>
	   							<td class="col-sm-8">Contato</td>
	   							<td class="col-sm-1">Principal</td>
	   							<td class="col-sm-1">Ação</td>
	   						</tr>
		                    <c:forEach items="${pessoa.emails}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${pessoa.emails[loop.index].remove eq 1}">
		                                <tr id="emails${loop.index}.wrapper" class="hidden">
		                            </c:when>
		                            <c:otherwise>
		                               <tr id="emails${loop.index}.wrapper">
		                            </c:otherwise>
		                        </c:choose>
		               			 <td>
		               			    <form:hidden path="emails[${loop.index}].id"/>
		                        	<select name="emails[${loop.index}].tipo" id="emails${loop.index}.tipo"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${tipocontatoList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq pessoa.emails[loop.index].tipo}">
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
			                            <!-- Generate the fields -->
			                           		 <form:checkbox path="emails[${loop.index}].principal" class="form-control input-sm" />
			                            </td>
		                            <td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${pessoa.emails[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
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
		                    <button id="addEmails" type="button">Novo Contato</button>
		                </div>
		              </div>
	              </div>
	              <div class="tab-pane" id="centro">
	              	<br/>
	              	<div class="form-group">
			            <label class="col-sm-2 col-xs-3 control-label" for="cidade">Cidade/Estado:</label>
	                	<div class="col-sm-4 col-xs-4">
	                		<div class="input-group">
	                			<input id="cidade.id" name="cidade.id" type="hidden">
		                		<input id="cidade" name="cidade" class="form-control" placeholder="Selecione a cidade" type="text">
		                		<span class="input-group-btn">
		                			<button class="btn btn-primary external" type="button" data-toggle="modal" data-action="http://localhost:8080/cidade/add?" data-target="#myModal">Novo</button>
		                		</span>
		                	</div>
		                </div>
		            </div>
					<div id="div_entidade" class="form-group">
			            <label class="col-sm-2 col-xs-3 control-label" for="entidade">Entidade:</label>
	                	<div class="col-sm-6 col-xs-9">
		                	<input id="entidade" name="entidade" class="form-control" placeholder="Selecione a entidade" type="text">
		                </div>
		            </div>
	              </div>
	              
	              <div class="tab-pane" id="observacoes">
	              	<br/>
	              	<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="anotacoes">Anotações</form:label>
	                	<div class="col-sm-10">
		    			<table class="table small" id="anotacoes">
	   						<tr>
	   							<td class="col-sm-2">Tipo</td>
	   							<td class="col-sm-8">Anotação</td>
	   							<td class="col-sm-2">Ação</td>
	   						</tr>
		                    <c:forEach items="${pessoa.anotacoes}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${pessoa.anotacoes[loop.index].remove eq 1}">
		                                <tr id="anotacoes${loop.index}.wrapper" class="hidden">
		                            </c:when>
		                            <c:otherwise>
		                               <tr id="anotacoes${loop.index}.wrapper">
		                            </c:otherwise>
		                        </c:choose>
		               			 <td>
		               			    <form:hidden path="anotacoes[${loop.index}].id"/>
		                        	<select name="anotacoes[${loop.index}].sinalizador" id="anotacoes${loop.index}.sinalizador"  class="form-control input-sm" >
									    <option value=""></option>
									        <c:forEach items="${sinalizadorList}" var="option">
									        	<c:choose>
												      <c:when test="${option eq pessoa.anotacoes[loop.index].sinalizador}">
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
		                           		 <form:input path="anotacoes[${loop.index}].texto" placeholder="Anotação"  class="form-control input-sm" />
		                            </td>
		                            <td>
		                            <!-- Add the remove flag -->
		                            <c:choose>
		                                <c:when test="${pessoa.anotacoes[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
		                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
		                            </c:choose>
		                            <form:hidden path="anotacoes[${loop.index}].remove" value="${hiddenValue}" />
		                            
		                            <button class="btn  btn-danger btn-xs  anotacoes.remove" data-index="${loop.index}">
		                            	<span class="glyphicon glyphicon-remove-sign">
			  							</span>
		                            </button>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </table>
		                    <button id="addAnotacoes" type="button">add</button>
		                </div>
		              </div>
		              </div>
	              </div>
	               <input type="submit" value="Salvar" class="btn btn-primary"/>
            </form:form>
        </div>
        
        <!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-body">
		      </div>
		      </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
        
        
 	<script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
   <script type="text/javascript" src="/js/custom/autocompleteentidade.js"></script>
  <script>

  var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";

  $(function() {
	  
	  completeCidade($('#endereco\\.cidade\\.nome'), $('#endereco\\.cidade\\.id'), baseUrl, function(item){
		  	$('#endereco\\.cidade\\.estado\\.sigla').attr('value', item.uf);
	        $('#endereco\\.cidade\\.estado\\.sigla').attr('readonly', true);
	        return item.name;
		});

	  completeCidade($('#cidade'), $('#cidade\\.id'), baseUrl);
	  completeEntidade($('#entidade'), $('#entidade\\.id'), baseUrl);
      

	  $('.external').on('click', function(e) {
	        e.preventDefault();
	        var url = $(this).attr('data-action');
	        $(".modal-body").html('<iframe width="100%" height="100%" frameborder="0" scrolling="yes" allowtransparency="true" src="'+url+'"></iframe>');
	 
	    });
	 
	    $('#myModal').on('show.bs.modal', function () {
	 
	        $(this).find('.modal-dialog').css({
	                  width:'40%x', //choose your width
	                  height:'100%', 
	                  'padding':'0'
	           });
	         $(this).find('.modal-content').css({
	                  height:'100%', 
	                  'border-radius':'0',
	                  'padding':'0'
	           });
	         $(this).find('.modal-body').css({
	                  width:'auto',
	                  height:'100%', 
	                  'padding':'0'
	           });
	    })
	  
	  
	  $('.cpf').mask('000.000.000-00', {reverse: true});
	  $('.cep').mask('00000-000');
	  $('.data').mask('00/00/0000');
      $('.datepicker').datepicker();

      $('#tabpessoa a:first').tab('show');


   // Start indexing at the size of the current list
      var indexT = ${fn:length(pessoa.telefones)};
      var indexE = ${fn:length(pessoa.emails)};
      var indexA = ${fn:length(pessoa.anotacoes)};

      // Add a new telefone
      $("#addTelefones").off("click").on("click", function() {
          $("#telefones tr:last").after(function() {
              var html = '<tr id="telefones' + indexT + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="telefones[' + indexT + '].tipo" id="telefones' + indexT + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipotelefoneList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" size="4" id="telefones' + indexT + '.ddd" name="telefones[' + indexT + '].ddd" placeholder="DDD"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="text" size="10" id="telefones' + indexT + '.numero" name="telefones[' + indexT + '].numero" placeholder="Número Telefone"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="text" id="telefones' + indexT + '.operadora" name="telefones[' + indexT + '].operador" placeholder="Operadora"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="telefones' + indexT + '.remove" name="telefones[' + indexT + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs telefones.remove" data-index="' + indexT + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#telefones" + indexT + "\\.wrapper").removeClass('hidden');
          $(".telefones\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#telefones" + index2remove + "\\.wrapper").addClass('hidden');
              $("#telefones" + index2remove + "\\.remove").val("1");
              return false;
          });
          indexT++;
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
              var html = '<tr id="emails' + indexE + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="emails[' + indexE + '].tipo" id="emails' + indexE + '.tipo" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${tipocontatoList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" id="emails' + indexE + '.contato" name="emails[' + indexE + '].contato" placeholder="Contato"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="checkbox" id="emails' + indexE + '.principal" name="emails[' + indexE + '].principal" class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="emails' + indexE + '.remove" name="emails[' + indexE + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs emails.remove" data-index="' + indexE + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#emails" + indexE + "\\.wrapper").removeClass('hidden');
          $(".emails\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
              $("#emails" + index2remove + "\\.remove").val("1");
              return false;
          });
          indexE++;
          return false;
      });

      // Remove an email
      $(".emails\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#emails" + index2remove + "\\.wrapper").addClass('hidden');
          $("#emails" + index2remove + "\\.remove").val("1");
          return false;
      });
      
      // Add a new email
      $("#addAnotacoes").off("click").on("click", function() {
          $("#anotacoes tr:last").after(function() {
              var html = '<tr id="anotacoes' + indexA + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="anotacoes[' + indexA + '].sinalizador" id="anotacoes' + indexA + '.sinalizador" class="form-control input-sm" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${sinalizadorList}" var="option">
				      	html += '<option value="${option}">';
				      	html += '<c:out value="${option}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';	
			  html += '</td><td>';                  
              html += '<input type="text" id="anotacoes' + indexA + '.texto" name="anotacoes[' + indexA + '].texto" placeholder="Anotação"  class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="anotacoes' + indexA + '.remove" name="anotacoes[' + indexA + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs anotacoes.remove" data-index="' + indexA + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#anotacoes" + indexA + "\\.wrapper").removeClass('hidden');
          $(".anotacoes\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#anotacoes" + index2remove + "\\.wrapper").addClass('hidden');
              $("#anotacoes" + index2remove + "\\.remove").val("1");
              return false;
          });
          indexA++;
          return false;
      });

      // Remove an email
      $(".anotacoes\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#anotacoes" + index2remove + "\\.wrapper").addClass('hidden');
          $("#anotacoes" + index2remove + "\\.remove").val("1");
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
  
  
  
        
