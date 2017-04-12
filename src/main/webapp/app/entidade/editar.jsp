<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
<!--
.ui-datepicker { font-size: 9pt !important; }
-->
</style>

      <div class="row">
            <form:form method="post" action="save/${entidade.id}" commandName="entidade" class="form-horizontal validado" role="form">
            <ul class="nav nav-tabs" id="tabpessoa" role="tablist">
			  <li class="active"><a href="#dadosgerais" role="tab" data-toggle="tab">Dados Gerais</a></li>
			  <li><a href="#endereco" role="tab" data-toggle="tab">Endereço</a></li>
			  <li><a href="#contato" role="tab" data-toggle="tab">Contato</a></li>
			  <li><a href="#diretoria" role="tab" data-toggle="tab">Diretoria</a></li>
			  <li><a href="#institutos" role="tab" data-toggle="tab">Institutos</a></li>
			  <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' }">
		 	  <li><a href="#observacoes" role="tab" data-toggle="tab">Observações</a></li>
		 	  </c:if>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="dadosgerais">
					<br/>
					<form:hidden path="id" />
					<form:hidden path="endereco.id" />
					<div class="form-group">
			            <form:label class="col-sm-2 control-label" path="tipoEntidade">Tipo Entidade</form:label>
	                	<div class="col-sm-3">
		                	<select name="tipoEntidade" id="tipoEntidade" class="form-control"
		                		data-rule-required="true" 
	                			data-msg-required="Tipo Entidade é obrigatório" >
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
	                	<div class="col-sm-4">
		                	<form:input path="cnpj" cssClass="form-control" 
		                	data-rule-cnpj="true" />
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="razaoSocial">Razão Social</form:label>
	                	<div class="col-sm-7">
	                		<form:input path="razaoSocial" cssClass="form-control" 
	                			data-msg-required="Nome da instituição é obrigatório."
								data-rule-required="true"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="nomeFantasia">Nome Fantasia</form:label>
	                	<div class="col-sm-7">
	                		<form:input path="nomeFantasia" cssClass="form-control" />
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="dataFundacao">Data Fundação</form:label>
	                	<div class="col-sm-4">
	                		<form:input path="dataFundacao" cssClass="form-control datepicker" />
	                	</div>
		            </div>
		        </div>
				<div class="tab-pane" id="endereco">
					<br/>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cep">CEP</form:label>
	                	<div class="col-sm-3">
	                		<div class="input-group">
	                			<form:hidden path="endereco.buscaCep" />
	                			<form:hidden path="endereco.cepUnico" />
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
	                	<div class="col-sm-7">
	                		<form:input path="endereco.logradouro" cssClass="form-control" readonly="${entidade.endereco.cepUnico == false && entidade.endereco.buscaCep == true }"
	                			data-msg-required="Endereço é obrigatório."
								data-rule-required="true" autocomplete="off"/>
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.numero">Número</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.numero" cssClass="form-control" 
	                			data-msg-required="Número é obrigatório."
								data-rule-required="true" />
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
	                		<form:input path="endereco.bairro" cssClass="form-control" readonly="${entidade.endereco.cepUnico == false && entidade.endereco.buscaCep == true }"
	                			data-msg-required="Bairro é obrigatório."
								data-rule-required="true" />
	                	</div>
		            </div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="endereco.cidade.nome">Cidade</form:label>
		                <div class="col-sm-5">
			                <form:hidden path="endereco.cidade.id" />
							<form:input path="endereco.cidade.nome" cssClass="form-control" readonly="${entidade.endereco.buscaCep == true }"
								data-msg-required="Cidade é obrigatória."
								data-rule-required="true" autocomplete="off"/>
						</div>
					</div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="endereco.cidade.estado.sigla">UF</form:label>
	                	<div class="col-sm-2">
	                		<form:input path="endereco.cidade.estado.sigla" cssClass="form-control" readonly="${entidade.endereco.buscaCep == true }"
	                			data-msg-required="Estado é obrigatório."
								data-rule-required="true"/>
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
		                    <c:forEach items="${entidade.telefones}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${entidade.telefones[loop.index].remove eq 1}">
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
		                            	<form:select path="telefones[${loop.index}].operadora" class="form-control input-sm" >
		                            		<form:option value="">Selecione</form:option>
		                            		<form:option value="CLARO">Claro</form:option>
		                            		<form:option value="OI">Oi</form:option>
		                            		<form:option value="TIM">Tim</form:option>
		                            		<form:option value="VIVO">Vivo</form:option>
		                            		<form:option value="ALGAR">ALGAR</form:option>
		                            		<form:option value="NEXTEL">NEXTEL</form:option>
		                            		<form:option value="MVNO">MVNO's</form:option>
		                            		<form:option value="Sercomtel">Sercomtel</form:option>
		                            	</form:select>
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
			            <form:label class="col-sm-2 control-label" path="emails">Internet</form:label>
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
	              </div>
					<div class="tab-pane" id="diretoria">
						<br/>
						<div class="form-group">
							<form:hidden path="presidente.id"/>
			                <form:hidden path="presidente.pessoa.id"/>
			                <form:hidden path="presidente.ativo" value="true"/>
			                <form:label  class="col-sm-2 control-label" path="presidente.pessoa.nome">Presidente Atual</form:label>
		                	<div class="col-sm-10">
		                		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_ADMIN'}">
		                			<form:input path="presidente.pessoa.nome" cssClass="form-control" autocomplete="off" onblur="limparOcultos(this, 'presidente.pessoa.id'); limparId(this, 'presidente.pessoa.id');"/>
		                		</c:if>
		                		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
		                			<form:input path="presidente.pessoa.nome" cssClass="form-control" readonly="true"/>
		                		</c:if>
		                	</div>
			            </div>
			            <div class="form-group">
			            	<fmt:formatDate value="${entidade.presidente.inicioMandato}" var="inicioMandato" type="date" pattern="dd/MM/yyyy" />
			                <form:label  class="col-sm-2 control-label" path="presidente.inicioMandato" >Início Mandato</form:label>
		                	<div class="col-sm-2">
		                		<form:input path="presidente.inicioMandato" value="${ inicioMandato }" cssClass="form-control datepicker" />
		                	</div>
			            </div>
			            <div class="form-group">
			            	<fmt:formatDate value="${entidade.presidente.terminoMandato}" var="terminoMandato" type="date" pattern="dd/MM/yyyy" />
			                <form:label  class="col-sm-2 control-label" path="presidente.terminoMandato">Término Mandato</form:label>
		                	<div class="col-sm-2">
		                		<form:input path="presidente.terminoMandato" value="${ terminoMandato }"  cssClass="form-control datepicker" />
		                	</div>
			            </div>
			            
			            <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_ADMIN'}">
						<div class="form-group">
				            <form:label class="col-sm-2 control-label" path="presidentes">Histórico Presidentes</form:label>
		                	<div class="col-sm-10">
			    			<table class="table small" id="presidentes">
		   						<tr>
		   							<td class="col-sm-7">Nome</td>
		   							<td class="col-sm-2">Inicio Mandato</td>
		   							<td class="col-sm-2">Término Mandato</td>
		   							<td class="col-sm-1">Ativo</td>
		   						</tr>
			                    <c:forEach items="${entidade.presidentes}" varStatus="loop">
			                        <!-- Add a wrapping div -->
			                        <c:choose>
			                            <c:when test="${entidade.presidentes[loop.index].remove eq 1}">
			                                <tr id="presidentes${loop.index}.wrapper" class="hidden">
			                            </c:when>
			                            <c:otherwise>
			                               <tr id="presidentes${loop.index}.wrapper">
			                            </c:otherwise>
			                        </c:choose>
			               			 <td>
			               			 	    <form:hidden path="presidentes[${loop.index}].id"/>
			               			 		<form:hidden path="presidentes[${loop.index}].pessoa.id"/>
			                        		<form:input path="presidentes[${loop.index}].pessoa.nome" placeholder="Selecione o trabalhador"  class="form-control input-sm" readonly="true"/>	
										</td>
			                            <td>
			                            <!-- Generate the fields -->
			                            	<fmt:formatDate value="${entidade.presidentes[loop.index].inicioMandato}" var="inicioMandato" type="date" pattern="dd/MM/yyyy" />
			                           		 <form:input path="presidentes[${loop.index}].inicioMandato" value="${ inicioMandato }" class="form-control input-sm datepicker" />
			                            </td>
			                            <td>
			                            <!-- Generate the fields -->
			                            	<fmt:formatDate value="${entidade.presidentes[loop.index].terminoMandato}" var="terminoMandato" type="date" pattern="dd/MM/yyyy" />
			                           		 <form:input path="presidentes[${loop.index}].terminoMandato" value="${ terminoMandato }" class="form-control input-sm datepicker" />
			                            </td>
			                            <td>
			                            <!-- Generate the fields -->
			                           		 <form:checkbox path="presidentes[${loop.index}].ativo" class="form-control input-sm" />
			                            </td>
			                            <td>
			                            <!-- Add the remove flag -->
			                            <c:choose>
			                                <c:when test="${entidade.presidentes[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
			                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
			                            </c:choose>
			                            <form:hidden path="presidentes[${loop.index}].remove" value="${hiddenValue}" />
			                            
			                            <button class="btn  btn-danger btn-xs presidentes.remove" data-index="${loop.index}">
			                            	<span class="glyphicon glyphicon-remove-sign">
				  							</span>
			                            </button>
			                            </td>
			                        </tr>
			                    </c:forEach>
			                    </table>
			                    <button id="addPresidentes" type="button" class="btn btn-primary btn-xs">Novo</button>
			                </div>
			              </div>
			              </c:if>
		        	</div>
					<div class="tab-pane" id="institutos">
						<br/>
						<div class="form-group">
				            <form:label class="col-sm-2 control-label" path="dirigentes">Dirigentes</form:label>
		                	<div class="col-sm-10">
			    			<table class="table small" id="dirigentes">
		   						<tr>
		   							<td class="col-sm-3">Instituto</td>
		   							<td class="col-sm-4">Nome</td>
		   							<td class="col-sm-2">Inicio Mandato</td>
		   							<td class="col-sm-2">Término Mandato</td>
		   							<td class="col-sm-1">Ativo</td>
		   						</tr>
			                    <c:forEach items="${entidade.dirigentes}" varStatus="loop">
			                        <!-- Add a wrapping div -->
			                        <c:choose>
			                            <c:when test="${entidade.dirigentes[loop.index].remove eq 1}">
			                                <tr id="dirigentes${loop.index}.wrapper" class="hidden">
			                            </c:when>
			                            <c:otherwise>
			                               <tr id="dirigentes${loop.index}.wrapper">
			                            </c:otherwise>
			                        </c:choose>
			                        	<td>
			               			 	    <form:hidden path="dirigentes[${loop.index}].id"/>
			               			 		<form:hidden path="dirigentes[${loop.index}].instituto.id" 
			               			 			data-msg-required="Selecione um instituto"
												data-rule-required="true"/>
			                        		<form:input path="dirigentes[${loop.index}].instituto.descricao" placeholder="Selecione o Instituto/Comissão"  class="form-control input-sm" readonly="true"/>
										</td>
			               			 	<td>
			               			 	    <form:hidden path="dirigentes[${loop.index}].id"/>
			               			 		<form:hidden path="dirigentes[${loop.index}].trabalhador.id"
			               			 			data-msg-required="Selecione um trabalhador previamente cadastrado"
												data-rule-required="true"/>
			                        		<form:input path="dirigentes[${loop.index}].trabalhador.nome" placeholder="Selecione o trabalhador"  class="form-control input-sm" readonly="true"/>	
										</td>
			                            <td>
			                            <!-- Generate the fields -->
			                            	<fmt:formatDate value="${entidade.dirigentes[loop.index].inicioMandato}" var="inicioMandato" type="date" pattern="dd/MM/yyyy" />
			                           		 <form:input path="dirigentes[${loop.index}].inicioMandato" value="${ inicioMandato }" class="form-control input-sm datepicker" />
			                            </td>
			                            <td>
			                            <!-- Generate the fields -->
			                            	<fmt:formatDate value="${entidade.dirigentes[loop.index].terminoMandato}" var="terminoMandato" type="date" pattern="dd/MM/yyyy" />
			                           		 <form:input path="dirigentes[${loop.index}].terminoMandato" value="${ terminoMandato }" class="form-control input-sm datepicker" />
			                            </td>
			                            <td>
			                            <!-- Generate the fields -->
			                           		 <form:checkbox path="dirigentes[${loop.index}].ativo" class="form-control input-sm" />
			                            </td>
			                            <td>
			                            <!-- Add the remove flag -->
			                            <c:choose>
			                                <c:when test="${entidade.dirigentes[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
			                                <c:otherwise><c:set var="hiddenValue" value="0" /></c:otherwise>
			                            </c:choose>
			                            <form:hidden path="dirigentes[${loop.index}].remove" value="${hiddenValue}" />
			                            
			                            <button class="btn  btn-danger btn-xs dirigentes.remove" data-index="${loop.index}">
			                            	<span class="glyphicon glyphicon-remove-sign">
				  							</span>
			                            </button>
			                            </td>
			                        </tr>
			                    </c:forEach>
			                    </table>
			                    <button id="addDirigentes" type="button" class="btn btn-primary btn-xs">Novo</button>
			                </div>
			              </div>
		        	</div>
		        	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' }">
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
		                    <c:forEach items="${entidade.anotacoes}" varStatus="loop">
		                        <!-- Add a wrapping div -->
		                        <c:choose>
		                            <c:when test="${entidade.anotacoes[loop.index].remove eq 1}">
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
												      <c:when test="${option eq entidade.anotacoes[loop.index].sinalizador}">
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
		                                <c:when test="${entidade.anotacoes[loop.index].remove eq 1}"><c:set var="hiddenValue" value="1" /></c:when>
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
		                    <button id="addAnotacoes" type="button" class="btn btn-primary btn-xs">Nova Anotação</button>
		                </div>
		              </div>
		              </div>
		               </c:if>
		        	</div>
		        <!--  /div-->
	            <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
                			<button type="button" onclick="enviar();" class="btn btn-primary btn-mini">Salvar</button>
                		</div>
				</div>
            </form:form>
        </div>
  <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
  <script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
  <script type="text/javascript" src="/js/custom/autocompleteinstituto.js"></script>
  
  <script>

  var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";

  function enviar(){
	var breakOut = false
	$('input[name^="dirigentes"]').each(function() {
			if( $(this).attr("name").endsWith("trabalhador.id")){
				if($(this).val() == '') {
			    	alert("É preciso 'selecionar' um trabalhador já cadastrado para dirigente de comissão!!");
			    	$(this).closest('tr').addClass('has-error');
			    	breakOut = true;
			    	return false;
				}
			}
	});
	
	if(breakOut) {
	    breakOut = false;
	    return false;
	} else {
    	document.getElementById('entidade').submit();
	}
  }
  
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
		  	$('#endereco\\.cidade\\.estado\\.sigla').attr('value', item.uf);
	        $('#endereco\\.cidade\\.estado\\.sigla').attr('readonly', true);
	        return item.name;
		});

   // Start indexing at the size of the current list
      var indexTelefones = ${fn:length(entidade.telefones)};
      var indexEmails = ${fn:length(entidade.emails)};
      var indexA = ${fn:length(entidade.anotacoes)};
      <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_ADMIN'}">
      var indexPresidentes = ${fn:length(entidade.presidentes)};
      </c:if>
      var indexDirigentes = ${fn:length(entidade.dirigentes)};

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
              html += '<select id="telefones' + indexTelefones + '.operadora" name="telefones[' + indexTelefones + '].operadora" class="form-control input-sm" >';
              html += '<option value="">Selecione</option>';
              html += '<option value="CLARO">Claro</option>';
              html += '<option value="OI">Oi</option>';
              html += '<option value="TIM">TIM</option>';
              html += '<option value="VIVO">Vivo</option>';
              html += '<option value="ALGAR">ALGAR</option>';
              html += '<option value="NEXTEL">NEXTEL</option>';
              html += '<option value="MVNO">MVNO\'s</option>';
              html += '<option value="Sercomtel">Sercomtel</option>';
              html += '<select>';
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

      <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_ADMIN'}">
   		// Add a new presidente
      $("#addPresidentes").off("click").on("click", function() {
          $("#presidentes tr:last").after(function() {
              var html = '<tr id="presidentes' + indexPresidentes + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<input type="hidden" id="presidentes' + indexPresidentes + '.pessoa.id" name="presidentes[' + indexPresidentes + '].pessoa.id" value="" />';
           	  html += '<input type="text" id="presidentes' + indexPresidentes + '.pessoa.nome" name="presidentes[' + indexPresidentes + '].pessoa.nome" data-index="' + indexPresidentes + '" placeholder="Selecione o trabalhador"  class="form-control input-sm pessoa"  autocomplete="off"/>';
			  html += '</td><td>';                  
              html += '<input type="text" id="presidentes' + indexPresidentes + '.inicioMandato" name="presidentes[' + indexPresidentes + '].inicioMandato" class="form-control input-sm datepicker" />';
              html += '</td><td>';
              html += '<input type="text" id="presidentes' + indexPresidentes + '.terminoMandato" name="presidentes[' + indexPresidentes + '].terminoMandato" class="form-control input-sm datepicker" />';
              html += '</td><td>';
              html += '<input type="checkbox" id="presidentes' + indexPresidentes + '.ativo" name="presidentes[' + indexPresidentes + '].ativo" class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden" id="presidentes' + indexPresidentes + '.remove" name="presidentes[' + indexPresidentes + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs presidentes.remove" data-index="' + indexPresidentes + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          $("#presidentes" + indexPresidentes + "\\.wrapper").removeClass('hidden');
          $(".presidentes\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#presidentes" + index2remove + "\\.wrapper").addClass('hidden');
              $("#presidentes" + index2remove + "\\.remove").val("1");
              return false;
          });

          $('#presidentes' + indexPresidentes + '\\.pessoa\\.nome').typeahead({
	  		    source: function (query, process) {
	  		        return $.ajax({
	  		            url: baseUrl + '/pessoa/list',
	  		            type: 'Get',
	  		            data: { maxRows: 6, query: query },
	  		            dataType: 'json',
	  		            success: function (result) {
	  		                var resultList = result.map(function (item) {
	  		                    var aItem = { id: item.id, name: item.nome };
	  		                    return JSON.stringify(aItem);
	  		                });
	
	  		                return process(resultList);
	
	  		            }
	  		        });
	  		    },
	  			matcher: function (obj) {
	  		        var item = JSON.parse(obj);
	  		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase())
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
	  		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
	  		        return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	  		            return '<strong>' + match + '</strong>'
	  		        })
	  		    },
	  		    updater: function (obj) {
	  		        var item = JSON.parse(obj);
	  		        var indice = this.$element.data('index');
	  		        $("#presidentes" + indice + "\\.pessoa\\.id").val(item.id);
	  		        return item.name;
	  		    }
	  		});

          $('.datepicker').datepicker({
				format: "dd/mm/yyyy",
				autoclose: true,
				language: 'pt-BR'
			});

          indexPresidentes++;
          return false;
      });

      // Remove an presidente
      $(".presidentes\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#presidentes" + index2remove + "\\.wrapper").addClass('hidden');
          $("#presidentes" + index2remove + "\\.remove").val("1");
          return false;
      });
      //XXXXXXXXXXXXXXXXXXXXXX FIM PRESIDENTES XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
      </c:if>
      
      // Add a new dirigente
      $("#addDirigentes").off("click").on("click", function() {
          $("#dirigentes tr:last").after(function() {
              var html = '<tr id="dirigentes' + indexDirigentes + '.wrapper" class="hidden">';  
              html += '<td>';
              html += '<select name="dirigentes[' + indexDirigentes + '].instituto.id" id="dirigentes' + indexDirigentes + '.instituto.id" class="form-control input-sm" data-msg-required="Selecione um instituto" data-rule-required="true" >';
           	  html += '<option value=""></option>';
			        <c:forEach items="${institutoList}" var="option">
				      	html += '<option value="${option.id}">';
				      	html += '<c:out value="${option.descricao}"></c:out>';
				      	html += '</option>';
			        </c:forEach>
			  html += '</select>';
			  html += '</td><td>';                  
              html += '<input type="hidden"   id="dirigentes' + indexDirigentes + '.trabalhador.id"      name="dirigentes[' + indexDirigentes + '].trabalhador.id" value="" data-msg-required="Selecione um trabalhador previamente " data-rule-required="true"/>';
           	  html += '<input type="text"     id="dirigentes' + indexDirigentes + '.trabalhador.nome"    name="dirigentes[' + indexDirigentes + '].trabalhador.nome" data-index="' + indexDirigentes + '" placeholder="Selecione o trabalhador"  class="form-control input-sm trabalhador" autocomplete="off"/>';
			  html += '</td><td>';                  
              html += '<input type="text"     id="dirigentes' + indexDirigentes + '.inicioMandato"       name="dirigentes[' + indexDirigentes + '].inicioMandato" class="form-control input-sm datepicker" />';
              html += '</td><td>';
              html += '<input type="text"     id="dirigentes' + indexDirigentes + '.terminoMandato"      name="dirigentes[' + indexDirigentes + '].terminoMandato" class="form-control input-sm datepicker" />';
              html += '</td><td>';
              html += '<input type="checkbox" id="dirigentes' + indexDirigentes + '.ativo"               name="dirigentes[' + indexDirigentes + '].ativo" class="form-control input-sm" />';
              html += '</td><td>';
              html += '<input type="hidden"   id="dirigentes' + indexDirigentes + '.remove"              name="dirigentes[' + indexDirigentes + '].remove" value="0" />';
              html += '<button class="btn btn-danger btn-xs dirigentes.remove" data-index="' + indexDirigentes + '"><span class="glyphicon glyphicon-remove-sign"></span></button>';                    
              html += "</td></tr>";
              return html;
          });
          
          $("#dirigentes" + indexDirigentes + "\\.wrapper").removeClass('hidden');

          $(".dirigentes\\.remove").off("click").on("click", function() {
              var index2remove = $(this).data("index");
              $("#dirigentes" + index2remove + "\\.wrapper").addClass('hidden');
              $("#dirigentes" + index2remove + "\\.remove").val("1");
              return false;
          });

          completePessoa($('#dirigentes' + indexDirigentes + '\\.trabalhador\\.nome'), $("#dirigentes" + indexDirigentes + "\\.trabalhador\\.id"), baseUrl);
          completeInstituto($('#dirigentes' + indexDirigentes + '\\.instituto\\.descricao'), $("#dirigentes" + indexDirigentes + "\\.instituto\\.id"), baseUrl);

          $('.datepicker').datepicker({
				format: "dd/mm/yyyy",
				autoclose: true,
				language: 'pt-BR'
			});
          
          indexDirigentes++;
          return false;
      });

      // Remove an dirigente
      $(".dirigentes\\.remove").off("click").on("click", function() {
          var index2remove = $(this).data("index");
          $("#dirigentes" + index2remove + "\\.wrapper").addClass('hidden');
          $("#dirigentes" + index2remove + "\\.remove").val("1");
          return false;
      });
      //XXXXXXXXXXXXXXXXXXXXXX FIM DIRIGENTES XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

      $('.pessoa').typeahead({
		    source: function (query, process) {
		        return $.ajax({
		            url: baseUrl + '/pessoa/list',
		            type: 'Get',
		            data: { maxRows: 6, query: query },
		            dataType: 'json',
		            success: function (result) {
		                var resultList = result.map(function (item) {
		                    var aItem = { id: item.id, name: item.nome };
		                    return JSON.stringify(aItem);
		                });
		                return process(resultList);
		            }
		        });
		    },
			matcher: function (obj) {
		        var item = JSON.parse(obj);
		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase())
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
		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
		        return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
		            return '<strong>' + match + '</strong>'
		        })
		    },
		    updater: function (obj) {
		        var item = JSON.parse(obj);
		        var indice = this.$element.data('index');
		        $("#presidentes" + indice + "\\.pessoa\\.id").val(item.id);
		        return item.name;
		    }
		});
		
      $('.trabalhador').typeahead({
		    source: function (query, process) {
		        return $.ajax({
		            url: baseUrl + '/pessoa/list',
		            type: 'Get',
		            data: { maxRows: 6, query: query },
		            dataType: 'json',
		            success: function (result) {
		                var resultList = result.map(function (item) {
		                    var aItem = { id: item.id, name: item.nome };
		                    return JSON.stringify(aItem);
		                });
		                return process(resultList);
		            }
		        });
		    },
			matcher: function (obj) {
		        var item = JSON.parse(obj);
		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase())
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
		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
		        return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
		            return '<strong>' + match + '</strong>'
		        })
		    },
		    updater: function (obj) {
		        var item = JSON.parse(obj);
		        var indice = this.$element.data('index');
		        $("#dirigentes" + indice + "\\.trabalhador\\.id").val(item.id);
		        return item.name;
		    }
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