<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="meta" type="com.concafras.gestao.form.MetaForm" required="true" %>
<%@attribute name="index" type="java.lang.Integer" required="true" %>
<%@attribute name="count" type="java.lang.Integer" required="true" %>
<%@attribute name="pai" type="java.lang.Integer" required="false" %>
<%@attribute name="nivel" type="java.lang.Integer" required="false" %>
<%@attribute name="prefix" type="java.lang.String" required="false" %>
<%@attribute name="branco" type="java.lang.Boolean" required="false" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>

<c:if test="${empty controlequebra}">
	<c:set var="controlequebra" value="15" scope="request" />
</c:if>	

<c:if test="${not empty posicao}">
	<c:set var="posicao" value="${posicao + 1}" scope="request"/>
</c:if>

<c:if test="${empty posicao}">
	<c:set var="posicao" value="1" scope="request" />
</c:if>	

<c:if test="${ (!branco && ( controlequebra == 50 || controlequebra == 51 )) || (branco && ( controlequebra == 38 || controlequebra == 39)) }">
			</tbody>
		</table>
		<div style="page-break-after: always; width: 100%; text-align: center; margin-top: 5px;">${meta.atividade.instituto.descricao}</div>
		<div style="text-align: center; width: 100%;">
			<h3><spring:message code="titulo.fichacentroespirita" /></h3>
		</div>
		<table  border="0" cellpadding="5" cellspacing="0" width="100%">
			<tr>
				<td width="15%"><b>Instituto:</b></td>
				<td width="35%" colspan="4"><b>${meta.atividade.instituto.descricao}</b></td>
				<td width="15%"><b>Dir.Nac.:</b></td>
				<td width="35%" colspan="4">${meta.atividade.instituto.dirigenteNacional.nomeCompleto}</td>
			</tr>
		</table>
		<table border="0" cellpadding="5" cellspacing="0" class="metas"  width="100%" style="margin-top: 5px;">
			<thead>
				<tr>
					<th style="width: 35%" class="bg-primary">Atividade</th>
					<th style="width: 20%" colspan="2" class="bg-primary"><spring:message code="label.situacao.atual" /></th>
					<th style="width: 20%" colspan="2" class="bg-primary">Planejamento</th>
					<th style="width: 25%" class="bg-primary"><spring:message code="label.observacoes" /></th>
				</tr>
			</thead>
			<tbody>
		<c:set var="controlequebra" value="1" scope="request" />
</c:if>

<c:if test="${ meta.atividade.prioridade > 0 }">
	<c:set var="cssPrioridade" value="style='background:#cccccc;'" />
</c:if>
<tr ${cssPrioridade}>
	<c:if test="${! empty meta.dependencias && (meta.atividade.tipoMeta != 'GRUPO_IMPLANTACAO' && meta.atividade.tipoMeta != 'GRUPO_EXECUCAO') }">
		<c:if test="${not empty controlequebra}">
			<c:set var="controlequebra" value="${controlequebra + 1}" scope="request"/>
		</c:if>
		<td colspan="6" style="width: 100%">
			${prefix}${index + 1} - ${meta.atividade.descricao}													
		</td>
	</c:if>

	<c:if test="${ empty meta.dependencias || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO'  }">
	<c:if test="${not empty controlequebra}">
		<c:set var="controlequebra" value="${controlequebra + 2}" scope="request"/>
	</c:if>
	<!-- Primeiro Rodizio -->
	
	<td style="width: 35%">
		${prefix}${index + 1} - ${meta.atividade.descricao}												
	</td>
	
	<!-- META É ACAO -->
	<c:if test="${meta.situacaoAtual == null}">
	<!--  Coluna 2 -->
	<td style="width: 20%">
				Um novo teste não Realizado
	</td>
	</c:if>
	
	<!-- Primeiro Rodizio -->
	<c:if test="${meta.situacaoAnterior == null || meta.situacaoAnterior.situacao == null}">
	
		<!-- META É QUANTITATIVA -->
		<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}">
		<!--  Coluna 2 -->
		<td colspan="2" style="width: 20%">
			<fmt:formatNumber value="${meta.situacaoAtual.realizado}" var="realizado" type="number" maxFractionDigits="0" />
			Realizado: ${realizado}
		</td>
		
		</c:if>
	
		<!-- META É ACAO -->
		<c:if test="${meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO'}">
		<!--  Coluna 2 -->
		<td colspan="2" style="width: 20%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
					Realizou em: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'NAOIMPLANTADA'}">
					Não Realizou
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; R &nbsp;) &nbsp;&nbsp;&nbsp; ( NR ) &nbsp;&nbsp;&nbsp;<br/><br/>
			        EM: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>&nbsp;
		</td>
		</c:if>
		
		
		<!-- META É IMPLANTACAO -->
		<c:if test="${meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO'}">
		<!--  Coluna 2 -->
		<td colspan="2" style="width: 20%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
			       Implantado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
			    </c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
			       Parcialmente: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
			    </c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'NAOIMPLANTADA'}">
			        Não Implantado
			    </c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>&nbsp;
		</td>
		</c:if>
		
	</c:if>
	
	
	<!-- Rodizio 2016 - Quando possui historico -->
	<c:if test="${( meta.situacaoAnterior.situacao == 'IMPLANTADA' || meta.situacaoAnterior.situacao == 'IMPLPARCIAL' )  && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO'  || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' )}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
			    <c:when test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
				Implantado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAnterior.situacao == 'IMPLPARCIAL'}">
				Parcialmente: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Implantado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
				Parcialmente: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
				<c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	<c:if test="${( meta.situacaoAnterior.situacao == 'IMPLANTADA' || meta.situacaoAnterior.situacao == 'IMPLPARCIAL' )  && (meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO')}">
		<!--  Coluna 2 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
			    <c:when test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
				Realizada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; RE &nbsp;) &nbsp;&nbsp;&nbsp; ( NR )<br/><br/>
			        EM: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Realizada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
				<c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; RE &nbsp;) &nbsp;&nbsp;&nbsp; ( NR )<br/><br/>
			        EM: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	<c:if test="${(meta.situacaoAnterior.situacao == 'PLANEJADA' || meta.situacaoAnterior.situacao == 'REPLANEJADA' )  && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO') }">
		<!-- Coluna 2 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAnterior.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			Planejado: ${previsao}
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Implantado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
				Parcialmente: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
				Replanejada: ${previsao}
			       <c:if test="${empty previsao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	<c:if test="${(meta.situacaoAnterior.situacao == 'PLANEJADA' || meta.situacaoAnterior.situacao == 'REPLANEJADA' )  && (meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO') }">
		<!-- Coluna 2 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAnterior.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			Planejado: ${previsao}
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Realizado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
				Replanejada: ${previsao}
			       <c:if test="${empty previsao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	<c:if test="${ ( meta.situacaoAnterior.situacao == 'NAOPLANEJADA' || meta.situacaoAnterior.situacao == 'NAOIMPLANTADA'  )  && ( meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO') }">
		<!-- Coluna 2 -->
		<td style="width: 15%">
			Não Planejado/Implantado
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Realizado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
				Realizado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
				Replanejada: ${previsao}
			       <c:if test="${empty previsao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	<c:if test="${ ( meta.situacaoAnterior.situacao == 'NAOPLANEJADA' || meta.situacaoAnterior.situacao == 'NAOIMPLANTADA'  )  && (meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO') }">
		<!-- Coluna 2 -->
		<td style="width: 15%">
			Não Planejado/Implantado
		</td>
		<!-- Coluna 3 -->
		<td style="width: 15%">
			<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
			<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
			<c:choose>
				<c:when test="${meta.situacaoAtual.situacao == 'IMPLANTADA'}">
				Implantado: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
				Parcialmente: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
				Replanejada: ${previsao}
			       <c:if test="${empty previsao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:when test="${meta.situacaoAtual.situacao == 'CANCELADA'}">
				Cancelada: ${conclusao}
			       <c:if test="${empty conclusao}">
			       N/I
			       </c:if>
				</c:when>
			    <c:otherwise>
			    	<c:if test="${!branco}">
			        Não Informado
			        </c:if>
			        <c:if test="${branco}">
			        (&nbsp; I &nbsp;) &nbsp;&nbsp;&nbsp; ( IP ) &nbsp;&nbsp;&nbsp; ( NI )<br/><br/>
			        DESDE: _____/______.
			        </c:if>
			    </c:otherwise>
			</c:choose>
		</td>
	</c:if>
	
	<c:if test="${!(meta.atividade.tipoMeta == 'META_QUANTITATIVA')}">  
	<!-- Coluna 4 -->
	<td colspan="2" style="width: 20%">
		<c:choose>
		    <c:when test="${meta.situacaoDesejada.situacao == 'NAOPLANEJADA'}">
		       Não Planejado
		    </c:when>
		    <c:when test="${meta.situacaoDesejada.situacao == 'PLANEJADA'}">
		        <fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
		        Planejado: ${planejado}
		    </c:when>
		    <c:otherwise>
		        <c:if test="${!branco}">
		        Não Informado
		        </c:if>
		        <c:if test="${branco}">
		        (&nbsp; PLAN &nbsp;) &nbsp;&nbsp; (NAO PLAN) <br/><br/>
		        META: _____/______.
		        </c:if>
		    </c:otherwise>
		</c:choose>
	</td>
	</c:if>
	<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}"> 
	<!-- Coluna 4 -->
	<td colspan="2" style="width: 20%">
		<fmt:formatNumber value="${meta.situacaoDesejada.previsto}" var="previsto" type="number" maxFractionDigits="0" />
		Planejado: ${previsto}
	</td>
	</c:if>
	
	<!-- Coluna 5 -->
	<td style="width: 25%">
	<div class="col-md-10">
		  <c:forEach items="${meta.observacoes}" var="anotacao" varStatus="idAnot">
		  		${anotacao.texto}
		  </c:forEach>
	</div>
	</td>
	</c:if>
</tr>
<c:if test="${fn:length(meta.dependencias) > 0}">
	<c:set var="paiAtual" value="${posicao}" scope="page" />
	<c:set var="novoNivel" value="${nivel + 1}"/>
	<spring:nestedPath path="dependencias[${index}]">
	    <c:forEach items="${meta.dependencias}" var="dependencia" varStatus="status">
	        <template:itemMetaImpressao meta="${dependencia}" index="${status.index}" count="${status.count}" pai="${paiAtual}" nivel="${ novoNivel }" prefix="${prefix}${index+1}." branco="${branco}"/>
	    </c:forEach>
    </spring:nestedPath>
</c:if>
