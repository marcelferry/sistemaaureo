<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="meta" type="com.concafras.gestao.form.MetaForm" required="true" %>
<%@attribute name="index" type="java.lang.Integer" required="true" %>
<%@attribute name="count" type="java.lang.Integer" required="true" %>
<%@attribute name="prefix" type="java.lang.String" required="false" %>
<%@attribute name="pai" type="java.lang.Integer" required="false" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>

<c:if test="${not empty posicao}">
	<c:set var="posicao" value="${posicao + 1}" scope="request"/>
</c:if>

<c:if test="${empty posicao}">
	<c:set var="posicao" value="1" scope="request" />
</c:if>	

<c:if test="${empty pai}">
<tr class="treegrid-${posicao}">
</c:if>
<c:if test="${not empty pai}">
<tr class="treegrid-${posicao} treegrid-parent-${pai}">
</c:if>
	<td style="width: 30%">
		<form:hidden path="dependencias[${index}].id" value="${meta.id}"/>
		<form:hidden path="dependencias[${index}].atividade.id" value="${meta.atividade.id}"/>
		<form:hidden path="dependencias[${index}].atividade.descricao" value="${meta.atividade.descricao}"/>
		<form:hidden path="dependencias[${index}].atividade.tipoMeta" value="${meta.atividade.tipoMeta}"/>
		<form:hidden path="dependencias[${index}].situacaoAtual.situacao" value="${meta.situacaoAtual.situacao}"/>
		<form:hidden path="dependencias[${index}].situacaoAtual.dataSituacao" value="${meta.situacaoAtual.dataSituacao}"/>
		<form:hidden path="dependencias[${index}].situacaoAtual.responsavel.id" value="${meta.situacaoAtual.responsavel.id}"/>
		<form:hidden path="dependencias[${index}].situacaoAtual.responsavel.nome" value="${meta.situacaoAtual.responsavel.nome}"/>
		<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" value="${meta.situacaoAnterior.situacao}"/>
		<form:hidden path="dependencias[${index}].situacaoAnterior.dataSituacao" value="${meta.situacaoAnterior.dataSituacao}"/>
		<form:hidden path="dependencias[${index}].situacaoAnterior.responsavel.id" value="${meta.situacaoAnterior.responsavel.id}"/>
		<form:hidden path="dependencias[${index}].situacaoAnterior.responsavel.nome" value="${meta.situacaoAnterior.responsavel.nome}"/>
		<form:hidden path="dependencias[${index}].situacaoDesejada.situacao" value="${meta.situacaoDesejada.situacao}"/>
		<form:hidden path="dependencias[${index}].situacaoDesejada.dataSituacao" value="${meta.situacaoDesejada.dataSituacao}"/>
		<form:hidden path="dependencias[${index}].situacaoDesejada.responsavel.id" value="${meta.situacaoDesejada.responsavel.id}"/>
		<form:hidden path="dependencias[${index}].situacaoDesejada.responsavel.nome" value="${meta.situacaoDesejada.responsavel.nome}"/>
		${prefix}${index + 1} - ${meta.atividade.descricao}											
	</td>
	<c:if test="${! empty meta.dependencias}">
		<td colspan="5" style="width: 70%"></td>
	</c:if>
	<c:if test="${ empty meta.dependencias}">
	
	<!-- Primeiro Rodizio -->
	<c:if test="${meta.situacaoAnterior == null || meta.situacaoAnterior.situacao == null}">
	
		<!-- META É QUANTITATIVA -->
		<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
			<fmt:formatNumber value="${meta.situacaoAtual.realizado}" var="realizado" type="number" maxFractionDigits="0" />
			<form:label path="dependencias[${index}].situacaoAtual.realizado" cssClass="control-label">
				Realizado:
			</form:label>
			<form:input path="dependencias[${index}].situacaoAtual.realizado" value="${realizado}" class="form-control input-sm"/>
			<form:errors path="dependencias[${index}].situacaoAtual.realizado" cssClass="error-messages"/>
		</td><!-- /Coluna 2 -->
		
		<!--  Coluna 3 -->
		<td>
			&nbsp;
		</td><!-- /Coluna 3 -->
		
		</c:if>
	
		<!-- META É ACAO -->
		<c:if test="${meta.atividade.tipoMeta == 'META_EXECUCAO'}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
			<div class="radio">
			    <label>
			      <form:radiobutton path="dependencias[${index}].situacaoAtual.situacao" value="ATIVA" onclick="realizadoRadio(this);" cssErrorClass="error-input"/> Já Realiza
			    </label>
			  </div>
			
			<div class="radio">
			    <label>
			      <form:radiobutton path="dependencias[${index}].situacaoAtual.situacao" value="INATIVA" onclick="realizadoRadio(this);" cssErrorClass="error-input"/> Não Realiza
			    </label>
		  	</div>
		  	<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="error-messages"/>
		</td><!-- /Coluna 2 -->
		
		<!--  Coluna 3 -->
		<td style="width: 15%">
			<div class="form-group">
				<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="col-sm-12 control-label" style="text-align: left;">
					Quando:
				</form:label>
				<div class="col-sm-12">
					<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
					<form:input path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
						class="date-picker form-control input-sm"/>
				</div>
			</div>
			<form:errors element="div" path="dependencias[${index}].situacaoAtual.conclusao" cssClass="radio error-messages"/>
		</td><!-- /Coluna 3 --> 
		</c:if>
		
		
		<!-- META É IMPLANTACAO -->
		<c:if test="${meta.atividade.tipoMeta == 'META_IMPLANTACAO'}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
				<div class="radio">
				    <label>
				      <form:radiobutton path="dependencias[${index}].situacaoAtual.situacao" value="ATIVA" onclick="implantadoRadio(this);" cssErrorClass="error-input"/> Implantado 
				      <i class="fa fa-question-circle" data-toggle="popover" data-placement="right" data-content="Considera-se uma atividade implantada quando ela funciona de acordo com as   
					  diretrizes do centro espírita referencial (dúvidas consultar o livro CENTRO ESPÍRITA - ESCOLA DA ALMA – Editora Auta de Souza)." title="Implantado"></i>
				    </label>
				  </div>
				<div class="radio">
				    <label>
				      <form:radiobutton path="dependencias[${index}].situacaoAtual.situacao" value="NAOADERENTE" onclick="implantadoRadio(this);" cssErrorClass="error-input"/> Parcialmente 
				      <i class="fa fa-question-circle" data-toggle="popover" data-placement="right" data-content="Considera-se uma atividade parcialmente implantada quando está fora das diretrizes 
				      do centro espírita referencial ou está somente com parte implantada, ainda não esta completa." title="Parcialmente Implantado"></i>
				    </label>
				  </div>
				<div class="radio">
				    <label>
				      <form:radiobutton path="dependencias[${index}].situacaoAtual.situacao" value="INATIVA"  onclick="implantadoRadio(this);" cssErrorClass="error-input"/> Não Implantado 
				      <i class="fa fa-question-circle " data-toggle="popover" data-placement="right" data-content="Quando nenhum item da atividade esteja implantada." title="Não Implantado"></i>
				    </label>
			  	</div>
			  	<form:errors element="div" path="dependencias[${index}].situacaoAtual.situacao" cssClass="radio error-messages"/>
		</td><!-- /Coluna 2 -->
		
		<!--  Coluna 3 -->
		<td style="width: 15%">
			<div class="form-group">
				<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="col-sm-12 control-label" style="text-align: left;">
					Desde: <span style="font-weight: normal;"><i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Coloque uma data aproximada de quando a atividade foi implantada ou iniciada na entidade." title="Desde"></i></span>
				</form:label>
				<div class="col-sm-12">
					<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
					<form:input path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
						class="date-picker form-control input-sm"/>
				</div>
			</div>
			<form:errors element="div" path="dependencias[${index}].situacaoAtual.conclusao" cssClass="radio error-messages"/>
		</td><!-- /Coluna 3 -->
		</c:if>
	</c:if>
	
	
	<!-- Rodizio 2016 - Quando possui historico -->
	<c:if test="${meta.situacaoAnterior.situacao == 'IMPLANTADA' || meta.situacaoAnterior.situacao == 'IMPLPARCIAL'}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
			
			<div class="form-group">
				<label>
					<c:if test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
					Implantado:
					</c:if>
					<c:if test="${meta.situacaoAnterior.situacao == 'IMPLPARCIAL'}">
					Parcialmente:
					</c:if>
				
				</label>
				<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
				<c:if test="${conclusao != null}">
					<form:hidden path="dependencias[${index}].situacaoAnterior.conclusao" value="${conclusao}"/>
					<input value="${conclusao}" readonly="readonly" class="form-control input-sm"/>
				</c:if>
				<c:if test="${conclusao == null}">
					<form:input path="dependencias[${index}].situacaoAnterior.conclusao" value="${conclusao}" 
						class="date-picker form-control input-sm" cssErrorClass="date-picker form-control input-sm danger"/>
				</c:if>
			</div>
		</td><!-- /Coluna 2 -->
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<label>
				Situação Atividade:  
			</label>
			<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
			<select id="metas${index}.situacaoAtual.situacao" name="dependencias[${index}].situacaoAtual.situacao" class="form-control input-sm" onchange="situacao(this);">
			    <option value=""></option>
			        <c:forEach items="${situacaoList}" var="option">
			        	<c:choose>
						      <c:when test="${option eq meta.situacaoAnterior.situacao}">
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
			<div class="dateimplantado">
				<label>
				Desde:
				</label>
				<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
				<form:input path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" class="date-picker form-control input-sm" cssErrorClass="error-input"/>
			</div>
		</td><!-- /Coluna 3 -->
	</c:if>
	
	<c:if test="${meta.situacaoAnterior.situacao == 'PLANEJADA'}">
		<!-- Coluna 2 -->
		<td style="width: 15%">
			<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
			<label>
			Planejado:
			</label>
			<fmt:formatDate value="${meta.situacaoAnterior.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			<c:if test="${previsao != null}">
				<form:hidden path="dependencias[${index}].situacaoAnterior.previsao" value="${previsao}"/>
				<input value="${previsao}" readonly="readonly" class="form-control input-sm"/>
			</c:if>
			<c:if test="${previsao == null}">
				<form:input path="dependencias[${index}].situacaoAnterior.previsao" value="${previsao}" placeholder="mês/ano" class="date-picker form-control input-sm" cssErrorClass="error-input"/>
			</c:if>
		</td><!-- /Coluna 2 -->
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<label>
				Situação Meta:  
			</label>
			<select id="metas${index}.situacaoAtual.situacao" name="dependencias[${index}].situacaoAtual.situacao" class="form-control input-sm" onchange="situacao(this);">
			    <option value=""></option>
			        <c:forEach items="${situacaoList}" var="option">
			        	<c:choose>
						      <c:when test="${option eq meta.situacaoAtual.situacao}">
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
			<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<div class="dateimplantado">
				<label>
				Em:
				</label>
				<form:input path="dependencias[${index}].situacaoAnterior.conclusao" placeholder="mês/ano" value="${conclusao}" class="date-picker form-control input-sm" cssErrorClass="error-input"/>
			</div>
		</td><!-- /Coluna 3 -->
	</c:if>
	
	
	<c:if test="${meta.atividade.tipoMeta == 'META_IMPLANTACAO'}">
	<!-- Coluna 4 -->
	<td colspan="2" style="width: 15%">
	<c:choose>
		<c:when test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
			<div class="radio">
			    <label >
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" disabled="true" cssErrorClass="error-input"/> Não Planejado
			    </label>
			</div>
			<div class="radio">
			    <label>
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA"  disabled="true" cssErrorClass="error-input"/> Planejado
			    </label>
			  </div>
			  <form:errors element="div" path="dependencias[${index}].situacaoDesejada.situacao" cssClass="error"/>
			  <fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
			  <form:input path="dependencias[${index}].situacaoAtual.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control input-sm" disabled="true" cssErrorClass="error-input"/>
			  <form:errors element="div" path="dependencias[${index}].situacaoDesejada.previsao" cssClass="error"/>
		</c:when>
		<c:otherwise>
		<div class="form-group">   
			<div class="radio">
			    <label class="col-sm-12 form-label" style="text-align: left;" >
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" cssErrorClass="error-input"/> Não Planejado
			    </label>
			</div>
			<div class="radio">
			    <label class="col-sm-12 form-label" style="text-align: left;">
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" cssErrorClass="error-input"/> Planejado
			    </label>
			  </div>
		</div>
		<form:errors element="div" path="dependencias[${index}].situacaoDesejada.situacao" cssClass="radio error-messages"/>
		<div class="form-group">
			<fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
			<div class="col-sm-12">
				<form:input path="dependencias[${index}].situacaoDesejada.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control input-sm" cssErrorClass="error-input"/>
			</div>
			<div class="col-sm-12">
				<form:errors element="div" path="dependencias[${index}].situacaoDesejada.previsao" cssClass="error-messages"/>
			</div>
		</div>
		</c:otherwise>
	</c:choose>
	</td><!-- /Coluna 4 -->
	</c:if>
	
	<c:if test="${meta.atividade.tipoMeta == 'META_EXECUCAO'}">
	<!-- Coluna 4 -->
	<td colspan="2" style="width: 15%">
		<div class="form-group">   
			<div class="radio">
			    <label class="col-sm-12 form-label" style="text-align: left;" >
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" cssErrorClass="error-input"/> Não Planejado
			    </label>
			</div>
			<div class="radio">
			    <label class="col-sm-12 form-label" style="text-align: left;">
			      <form:radiobutton path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" cssErrorClass="error-input"/> Planejado
			    </label>
			  </div>
		</div>
		<form:errors element="div" path="dependencias[${index}].situacaoDesejada.situacao" cssClass="radio error-messages"/>
		<div class="form-group">
			<fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
			<div class="col-sm-12">
				<form:input path="dependencias[${index}].situacaoDesejada.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control input-sm" cssErrorClass="error-input"/>
			</div>
			<div class="col-sm-12">
				<form:errors element="div" path="dependencias[${index}].situacaoDesejada.previsao" cssClass="error-messages"/>
			</div>
		</div>
	</td><!-- /Coluna 4 -->
	</c:if>
	
	<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}"> 
	<!-- Coluna 4 -->
	<td colspan="2" style="width: 15%">
		<fmt:formatNumber value="${meta.situacaoDesejada.previsto}" var="previsto" type="number" maxFractionDigits="0" />
		<form:label path="dependencias[${index}].situacaoDesejada.previsto" cssClass="control-label">
			Planejado:
		</form:label>
		<form:input path="dependencias[${index}].situacaoDesejada.previsto" value="${previsto}" class="form-control input-sm"/>
		<form:errors element="div" path="dependencias[${index}].situacaoDesejada.previsto" cssClass="error"/>
	</td><!-- /Coluna 4 -->
	</c:if>
	
	<!-- Coluna 5 -->
	<td style="width: 25%">
	<div class="form-group">
		<div class="col-md-12">
			<c:set var="indexObs" value="${fn:length(meta.observacoes)}" scope="request"/>
			<c:if test="${indexObs > 0}">
			<ul class="list-group" id="observacoes${meta.id}">
				<script>
					var nivel${meta.id} = 1;
					var indexObs${meta.id} = ${fn:length(meta.observacoes)};
					var indexNivel1${meta.id} = ${index};
				</script>
			  <c:forEach items="${meta.observacoes}" var="anotacao" varStatus="idAnot">
			  	<c:if test="${(idAnot.count - 1) > idAnot.index}">
			  	<c:if test="${anotacao.sinalizador == 'VERDE'}">
			  		<c:set var="cssBehavior" value="list-group-item-success"/>
			  	</c:if>
			  	<c:if test="${anotacao.sinalizador == 'AMARELO'}">
			  		<c:set var="cssBehavior" value="list-group-item-warning"/>
			  	</c:if>
			  	<c:if test="${anotacao.sinalizador == 'VERMELHO'}}">
			  		<c:set var="cssBehavior" value="list-group-item-danger"/>
			  	</c:if>
			  	<li class="list-group-item ${cssBehavior}" title="${anotacao.texto}">
			  		<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].id" />
				  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].remove" />
				  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].responsavel.id" />
				  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].texto" />
				  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].data" />
				  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].sinalizador" />
			  		${anotacao.texto}
			  	</li>
			  	</c:if>
			  </c:forEach>
			</ul>
			</c:if>
			<div class="form-group">
				<form:hidden path="dependencias[${index}].observacoes[${indexObs}].id" />
			  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].remove" />
			  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].nivel" />
			  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].responsavel.id" />
			  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].data" />
			  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].sinalizador" />
			  	<div class="col-md-12">
			  	<form:label path="dependencias[${index}].observacoes[${indexObs}].texto">Comentário:</form:label>
				<form:textarea path="dependencias[${index}].observacoes[${indexObs}].texto" rows="5" class="form-control col-md-12 input-sm"/>
			  	</div>
			</div>
		</div>
		<!--  div class="col-md-2">
			<button type="button" class="btn btn-success btn-xs btnAnotacaoSuccess" data-toggle="modal" data-id="${meta.id}">
						<span class="glyphicon glyphicon-plus">
						</span>
			</button>
			<button type="button" class="btn btn-warning btn-xs btnAnotacaoWarning" data-toggle="modal" data-id="${meta.id}">
						<span class="glyphicon glyphicon-plus">
						</span>
			</button>
			<button type="button" class="btn btn-danger btn-xs btnAnotacaoDanger" data-toggle="modal" data-id="${meta.id}">
						<span class="glyphicon glyphicon-plus">
						</span>
			</button>
		</div-->
		<div class="col-sm-12">
			<form:errors element="div" path="dependencias[${index}].observacoes" cssClass="error-messages"/>
		</div>
	</div>
	</td><!-- /Coluna 3 -->
</tr>
</c:if>
<c:if test="${fn:length(meta.dependencias) > 0}">
	<c:set var="paiAtual" value="${posicao}" scope="page" />
	<spring:nestedPath path="dependencias[${index}]">
	    <c:forEach items="${meta.dependencias}" var="dependencia" varStatus="status">
	        <template:itemMeta meta="${dependencia}" index="${status.index}" count="${status.count}" pai="${paiAtual}" prefix="${prefix}${index+1}."/>
	    </c:forEach>
    </spring:nestedPath>
</c:if>
