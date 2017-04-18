<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="meta" type="com.concafras.gestao.form.MetaForm" required="true" %>
<%@attribute name="index" type="java.lang.Integer" required="true" %>
<%@attribute name="count" type="java.lang.Integer" required="true" %>
<%@attribute name="prefix" type="java.lang.String" required="false" %>
<%@attribute name="pai" type="java.lang.Integer" required="false" %>
<%@attribute name="prioridade" type="java.lang.Boolean" required="false" %>

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
	<c:set var="cssPanel" value="panel-primary"/>
</c:if>
<c:if test="${not empty pai}">
	<c:set var="cssPanel" value="panel-default"/>
</c:if>

<c:if test="${index == 0}">
	<c:set var="cssIn" value="in"/>
</c:if>

<c:if test="${! empty meta.id}">
	<c:set var="metaId" value="${meta.id}"/>
</c:if>
<c:if test="${empty meta.id}">
	<c:set var="metaId" value="${meta.atividade.id}"/>
</c:if>

<div class="panel panel-green">


	<c:if test="${(prioridade == false || meta.atividade.prioridade == null )}">
	<form:hidden path="dependencias[${index}].id" value="${meta.id}"/>
	<form:hidden path="dependencias[${index}].descricaoCompleta" value="${meta.descricaoCompleta}"/>
	<form:hidden path="dependencias[${index}].atividade.id" value="${meta.atividade.id}"/>
	<form:hidden path="dependencias[${index}].atividade.descricao" value="${meta.atividade.descricao}"/>
	<form:hidden path="dependencias[${index}].atividade.tipoMeta" value="${meta.atividade.tipoMeta}"/>
	<form:hidden path="dependencias[${index}].atividade.prioridade" value="${meta.atividade.prioridade}"/>
	<form:hidden path="dependencias[${index}].situacaoAtual.ciclo.id" value="${meta.situacaoAtual.ciclo.id}"/>
	<form:hidden path="dependencias[${index}].situacaoAtual.tipoSituacao" value="${meta.situacaoAtual.tipoSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoAtual.dataSituacao" value="${meta.situacaoAtual.dataSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoAtual.responsavel.id" value="${meta.situacaoAtual.responsavel.id}"/>
	<form:hidden path="dependencias[${index}].situacaoAtual.responsavel.nome" value="${meta.situacaoAtual.responsavel.nome}"/>
	<form:hidden path="dependencias[${index}].situacaoAnterior.ciclo.id" value="${meta.situacaoAnterior.ciclo.id}"/>
	<form:hidden path="dependencias[${index}].situacaoAnterior.tipoSituacao" value="${meta.situacaoAnterior.tipoSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoAnterior.dataSituacao" value="${meta.situacaoAnterior.dataSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoAnterior.responsavel.id" value="${meta.situacaoAnterior.responsavel.id}"/>
	<form:hidden path="dependencias[${index}].situacaoAnterior.responsavel.nome" value="${meta.situacaoAnterior.responsavel.nome}"/>
	<form:hidden path="dependencias[${index}].situacaoDesejada.ciclo.id" value="${meta.situacaoDesejada.ciclo.id}"/>
	<form:hidden path="dependencias[${index}].situacaoDesejada.tipoSituacao" value="${meta.situacaoDesejada.tipoSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoDesejada.dataSituacao" value="${meta.situacaoDesejada.dataSituacao}"/>
	<form:hidden path="dependencias[${index}].situacaoDesejada.responsavel.id" value="${meta.situacaoDesejada.responsavel.id}"/>
	<form:hidden path="dependencias[${index}].situacaoDesejada.responsavel.nome" value="${meta.situacaoDesejada.responsavel.nome}"/>
	</c:if>
	
	<div class="panel-heading">
		<h4 class="panel-title small">
			<a data-toggle="collapse" data-target="#collapse${metaId}" class="collapsed small">
				${prefix}${index + 1} - ${meta.atividade.descricao}
			</a>
		</h4>
	</div>
	<div id="collapse${metaId}" class="panel-collapse collapse">
		<div class="panel-body">
			<c:if test="${ (prioridade == true && meta.atividade.prioridade > 0 )}">
			<div class="alert alert-danger"  style="height:50px;">
				<h4>Meta Prioritária - Responder no bloco superior!</h4>
			</div>
			</c:if>
			<c:if test="${ ( empty meta.dependencias && (prioridade == false || meta.atividade.prioridade == null ) ) || ( prioridade == false &&  ( meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO' ) ) }">
			<div class="row">
			
			<!-- Primeiro Rodizio -->
			<c:if test="${meta.situacaoAnterior == null || meta.situacaoAnterior.situacao == null}">
				<!-- META É QUANTITATIVA -->
				<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Hoje</strong></label>
						<fmt:formatNumber value="${meta.situacaoAtual.realizado}" var="realizado" type="number" maxFractionDigits="0" />
						<form:label path="dependencias[${index}].situacaoAtual.realizado" cssClass="control-label">
							Realizado:
						</form:label>
						<form:input path="dependencias[${index}].situacaoAtual.realizado" value="${realizado}" class="form-control "/>
						<form:errors path="dependencias[${index}].situacaoAtual.realizado" cssClass="error-messages"/>
					</div><!-- /Coluna 2 -->
				</div>
				
				<div class="col-sm-2">
					<!--  Coluna 3 -->
					<div class="form-group">
						&nbsp;
					</div><!-- /Coluna 3 -->
				</div>
				</c:if>
			
				<!-- META É ACAO -->
				<c:if test="${meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO' }">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Hoje</strong></label>
						<div class="radio">
						    <label>
						      <form:radiobutton id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" data-meta="${metaId}" value="IMPLANTADA" onclick="realizadoRadio(this);" /> Realizado
						    </label>
						  </div>
						
						<div class="radio">
						    <label>
						      <form:radiobutton id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" data-meta="${metaId}" value="NAOIMPLANTADA" onclick="realizadoRadio(this);" /> Não Realizado
						    </label>
					  	</div>
					  	<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="error-messages"/>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!--  Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>&nbsp;</strong></label>
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="col-sm-12 control-label" style="text-align: left;">
							Quando:
						</form:label>
						<div class="col-sm-12">
							<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
							<div class="input-group">
								<form:input  id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
									class="date-picker form-control "/>
								<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
							</div>
						</div>
						<form:errors element="div" path="dependencias[${index}].situacaoAtual.conclusao" cssClass="radio error-messages"/>
					</div>
					<!-- /Coluna 3 --> 
				</div>
				</c:if>
				
				
				<!-- META É IMPLANTACAO -->
				<c:if test="${meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO'}">
					<div class="col-sm-2">
						<!--  Coluna 2 -->
						<div class="form-group">
							<label class="control-label"><strong>Como está Hoje?</strong></label>
							<div>
								<div class="radio">
								    <label>
								      <form:radiobutton id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" value="IMPLANTADA" data-meta="${metaId}" onclick="implantadoRadio(this);" /> Implantado 
								      <i class="fa fa-question-circle" data-toggle="popover" data-placement="right" data-content="Considera-se uma atividade implantada quando ela funciona de acordo com as   
									  diretrizes do centro espírita referencial (dúvidas consultar o livro CENTRO ESPÍRITA - ESCOLA DA ALMA – Editora Auta de Souza)." title="Implantado"></i>
								    </label>
								  </div>
								<div class="radio">
								    <label>
								      <form:radiobutton id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" value="IMPLPARCIAL" data-meta="${metaId}"  onclick="implantadoRadio(this);" /> Parcialmente 
								      <i class="fa fa-question-circle" data-toggle="popover" data-placement="right" data-content="Considera-se uma atividade parcialmente implantada quando está fora das diretrizes 
								      do centro espírita referencial ou está somente com parte implantada, ainda não esta completa." title="Parcialmente Implantado"></i>
								    </label>
								  </div>
								<div class="radio">
								    <label>
								      <form:radiobutton id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" value="NAOIMPLANTADA" data-meta="${metaId}"   onclick="implantadoRadio(this);" /> Não Implantado 
								      <i class="fa fa-question-circle " data-toggle="popover" data-placement="right" data-content="Quando nenhum item da atividade esteja implantada." title="Não Implantado"></i>
								    </label>
							  	</div>
						  	</div>
						  	<form:errors element="div" path="dependencias[${index}].situacaoAtual.situacao" cssClass="radio error-messages"/>
						</div><!-- /Coluna 2 -->
					</div>
				
				<div class="col-sm-2">
				<!--  Coluna 3 -->
					<div class="form-group">
						<label class="control-label">&nbsp;</label>
						<div class="form-group">
							<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label" style="text-align: left;">
								Desde: <span style="font-weight: normal;"><i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Coloque uma data aproximada de quando a atividade foi implantada ou iniciada na entidade." title="Desde"></i></span>
							</form:label>
							<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
							<div class="input-group">
									<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
										class="date-picker form-control "/>
									<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
							</div>
							<form:errors element="div" path="dependencias[${index}].situacaoAtual.conclusao" cssClass="radio error-messages"/>
						</div>
					</div><!-- /Coluna 3 -->
				</div>
				</c:if>
			</c:if>
			
			
			<!-- Rodizio 2016 - Quando possui historico -->
			<c:if test="${( meta.situacaoAnterior.situacao == 'IMPLANTADA' || meta.situacaoAnterior.situacao == 'IMPLPARCIAL' )  && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' )}">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label>
								<c:if test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
								<font color="#800000">Implantado</font> em:
								</c:if>
								<c:if test="${meta.situacaoAnterior.situacao == 'IMPLPARCIAL'}">
								<font color="#800000">Parcialmente</font> em:
								</c:if>
								<c:if test="${meta.situacaoAnterior.conclusao != null}">
									<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
									<form:hidden path="dependencias[${index}].situacaoAnterior.conclusao" value="${conclusao}"/>
									<font color="#800000">${conclusao}</font>
								</c:if>	
								<c:if test="${meta.situacaoAnterior.conclusao == null}">
									N/I
									<i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Data não informado no ato do rodízio" title="Não informado"></i>
								</c:if>						
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacao(this);">
						    <option value=""></option>
						    <form:option value="IMPLANTADA">Implantada</form:option>
							<form:option value="IMPLPARCIAL">Imp. Parcial</form:option>
							<form:option value="NAOIMPLANTADA">Não Implantada</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_dataimplantado_${metaId}" class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								cssClass="date-picker form-control " cssErrorClass="date-picker form-control  error-input"/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
						
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
					<!-- /Coluna 3 -->
				</div>
			</c:if>

			<c:if test="${(meta.situacaoAnterior.situacao == 'IMPLANTADA' || meta.situacaoAnterior.situacao == 'IMPLPARCIAL') && (meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO')}">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label>
								<c:if test="${meta.situacaoAnterior.situacao == 'IMPLANTADA'}">
								<font color="#800000">Realizada</font> em:
								</c:if>
								<c:if test="${meta.situacaoAnterior.conclusao != null}">
									<fmt:formatDate value="${meta.situacaoAnterior.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
									<form:hidden path="dependencias[${index}].situacaoAnterior.conclusao" value="${conclusao}"/>
									<font color="#800000">${conclusao}</font>
								</c:if>	
								<c:if test="${meta.situacaoAnterior.conclusao == null}">
									N/I
									<i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Data não informado no ato do rodízio" title="Não informado"></i>
								</c:if>						
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacaoAcao(this);">
						    <option value=""></option>
						    <form:option value="IMPLANTADA">Realizado</form:option>
							<form:option value="NAOIMPLANTADA">Não Realizado</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_dataimplantado_${metaId}" class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								cssClass="date-picker form-control " cssErrorClass="date-picker form-control  error-input"/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
						
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
					<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${meta.situacaoAnterior.situacao == 'NAOIMPLANTADA' && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO') }">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label class="control-label warning">
								<font color="#800000">Não Implantado!</font>
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacao(this);">
						    <option value=""></option>
							<form:option value="IMPLANTADA">Implantada</form:option>
							<form:option value="IMPLPARCIAL">Imp. Parcial</form:option>
							<form:option value="NAOIMPLANTADA">Não Implantada</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div  id="bloco_dataimplantado_${metaId}"  class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								class="date-picker form-control "/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
				<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${meta.situacaoAnterior.situacao == 'NAOIMPLANTADA' && ( meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO')}">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label class="control-label warning">
								<font color="#800000">Não Implantado!</font>
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacaoAcao(this);">
						    <option value=""></option>
							<form:option value="IMPLANTADA">Realizado</form:option>
							<form:option value="NAOIMPLANTADA">Não Realizado</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div  id="bloco_dataimplantado_${metaId}"  class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								class="date-picker form-control "/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
				<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${meta.situacaoAnterior.situacao == 'CANCELADA' && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' ) }">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label class="control-label warning">
								<font color="#800000">Planejada/Cancelada!</font>
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacao(this);">
						    <option value=""></option>
							<form:option value="IMPLANTADA">Implantada</form:option>
							<form:option value="IMPLPARCIAL">Imp. Parcial</form:option>
							<form:option value="NAOIMPLANTADA">Não Implantada</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div  id="bloco_dataimplantado_${metaId}"  class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								class="date-picker form-control "/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
				<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${meta.situacaoAnterior.situacao == 'CANCELADA' && ( meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO' ) }">
				<div class="col-sm-2">
					<!--  Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<div class="form-group">
							<label class="control-label warning">
								<font color="#800000">Planejada/Cancelada!</font>
							</label>
						</div>
					</div><!-- /Coluna 2 -->
				</div>
			
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacaoAcao(this);">
						    <option value=""></option>
							<form:option value="IMPLANTADA">Realizada</form:option>
							<form:option value="NAOIMPLANTADA">Não Realizada</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div  id="bloco_dataimplantado_${metaId}"  class="form-group dateimplantado">
						<form:label path="dependencias[${index}].situacaoAtual.conclusao" cssClass="control-label">Desde:</form:label>
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" 
								class="date-picker form-control "/>
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
				<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${(meta.situacaoAnterior.situacao == 'PLANEJADA' || meta.situacaoAnterior.situacao == 'REPLANEJADA' )  && meta.atividade.tipoMeta == 'META_QUANTITATIVA' }">
				<div class="col-sm-2">
					<!-- Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<label class="control-label">
							<font color="#800000">Meta</font>: 
							<c:if test="${meta.situacaoAnterior.previsto != null}">
								<form:hidden path="dependencias[${index}].situacaoAnterior.previsto" />
								<fmt:formatNumber type="number" maxFractionDigits="0" value="${meta.situacaoAnterior.previsto}" var="previsto"/>
								<font size="20">${ previsto }</font>
							</c:if> 
							<c:if test="${meta.situacaoAnterior.previsto == null}">
								<font size="20">N/I</font>
								<i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Valor não informado no ato do rodízio" title="Não informado"></i>
							</c:if>	
						</label>
					</div><!-- /Coluna 2 -->
				</div>
				
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<label>Realizado:</label>
						<form:input path="dependencias[${index}].situacaoAtual.realizado" placeholder="Qtde. Realizada" class="form-control" />
						<form:errors path="dependencias[${index}].situacaoAtual.realizado" cssClass="label label-danger"/>
					</div>
					
					<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${(meta.situacaoAnterior.situacao == 'PLANEJADA' || meta.situacaoAnterior.situacao == 'REPLANEJADA' ) && ( meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO') }">
				<div class="col-sm-2">
					<!-- Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<label class="control-label">
							<font color="#800000">
								<c:if test="${meta.situacaoAnterior.situacao == 'PLANEJADA'}">
									Planejado
								</c:if>
								<c:if test="${meta.situacaoAnterior.situacao == 'REPLANEJADA'}">
									Replanejado
								</c:if>
							</font> para: 
							<c:if test="${meta.situacaoAnterior.previsao != null}">
								<fmt:formatDate value="${meta.situacaoAnterior.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
								<form:hidden path="dependencias[${index}].situacaoAnterior.previsao" value="${previsao}"/>
								${previsao}
							</c:if> 
							<c:if test="${meta.situacaoAnterior.previsao == null}">
								N/I
								<i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Data não informado no ato do rodízio" title="Não informado"></i>
							</c:if>	
						</label>
					</div><!-- /Coluna 2 -->
				</div>
				
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacaoAcao(this);">
						    <option value=""></option>
					    	<form:option value="IMPLANTADA">Realizado</form:option>
							<form:option value="REPLANEJADA">Replanejado</form:option>
							<form:option value="CANCELADA">Não Realizado</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_dataimplantado_${metaId}" class="form-group dateimplantado">
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<label>Em:</label>
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" class="date-picker form-control " />
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_datareplanejado_${metaId}" class="form-group dateimplantado">
						<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
						<label>Para:</label>
						<div class="input-group">
							<form:input id="situacaoAtual_previsao_${metaId}" path="dependencias[${index}].situacaoAtual.previsao" placeholder="mês/ano" value="${previsao}" class="date-picker form-control " />
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.previsao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
					<c:if test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
					<script type="text/javascript">
						$("#bloco_datareplanejado_${metaId}").show();
					</script>
					</c:if>
					<!-- /Coluna 3 -->
				</div>
			</c:if>
			
			<c:if test="${(meta.situacaoAnterior.situacao == 'PLANEJADA' || meta.situacaoAnterior.situacao == 'REPLANEJADA' )  && ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' ) }">
				<div class="col-sm-2">
					<!-- Coluna 2 -->
					<div class="form-group">
						<label class="control-label"><strong>Como estava em ${meta.situacaoAnterior.ciclo.ciclo}?</strong></label>
						<form:hidden path="dependencias[${index}].situacaoAnterior.situacao" />
						<label class="control-label">
							<font color="#800000">
								<c:if test="${meta.situacaoAnterior.situacao == 'PLANEJADA'}">
									Planejado
								</c:if>
								<c:if test="${meta.situacaoAnterior.situacao == 'REPLANEJADA'}">
									Replanejado
								</c:if>
							</font> para:  
							<c:if test="${meta.situacaoAnterior.previsao != null}">
								<fmt:formatDate value="${meta.situacaoAnterior.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
								<form:hidden path="dependencias[${index}].situacaoAnterior.previsao" value="${previsao}"/>
								${previsao}
							</c:if> 
							<c:if test="${meta.situacaoAnterior.previsao == null}">
								N/I
								<i class="fa fa-question-circle" data-toggle="popover" data-placement="top" data-content="Data não informado no ato do rodízio" title="Não informado"></i>
							</c:if>	
						</label>
					</div><!-- /Coluna 2 -->
				</div>
				
				<div class="col-sm-2">
					<!-- Coluna 3 -->
					<div class="form-group">
						<label class="control-label"><strong>Como está Hoje?</strong></label>
						<form:select data-meta="${metaId}" id="situacaoAtual_situacao_${metaId}" path="dependencias[${index}].situacaoAtual.situacao" class="form-control " onchange="situacao(this);">
						    <option value=""></option>
					    	<form:option value="IMPLANTADA">Implantada</form:option>
							<form:option value="IMPLPARCIAL">Imp. Parcial</form:option>
							<form:option value="REPLANEJADA">Replanejada</form:option>
							<form:option value="CANCELADA">Cancelada</form:option>
						</form:select>
						<form:errors path="dependencias[${index}].situacaoAtual.situacao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_dataimplantado_${metaId}" class="form-group dateimplantado">
						<fmt:formatDate value="${meta.situacaoAtual.conclusao}" var="conclusao" type="date" pattern="MM/yyyy" />
						<label>Em:</label>
						<div class="input-group">
							<form:input id="situacaoAtual_conclusao_${metaId}" path="dependencias[${index}].situacaoAtual.conclusao" placeholder="mês/ano" value="${conclusao}" class="date-picker form-control " />
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.conclusao" cssClass="label label-danger"/>
					</div>
					<div id="bloco_datareplanejado_${metaId}" class="form-group dateimplantado">
						<fmt:formatDate value="${meta.situacaoAtual.previsao}" var="previsao" type="date" pattern="MM/yyyy" />
						<label>Para:</label>
						<div class="input-group">
							<form:input id="situacaoAtual_previsao_${metaId}" path="dependencias[${index}].situacaoAtual.previsao" placeholder="mês/ano" value="${previsao}" class="date-picker form-control " />
							<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
						</div>
						<form:errors path="dependencias[${index}].situacaoAtual.previsao" cssClass="label label-danger"/>
					</div>
					<c:if test="${meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == 'IMPLPARCIAL'}">
					<script type="text/javascript">
						$("#bloco_dataimplantado_${metaId}").show();
					</script>
					</c:if>
					<c:if test="${meta.situacaoAtual.situacao == 'REPLANEJADA'}">
					<script type="text/javascript">
						$("#bloco_datareplanejado_${metaId}").show();
					</script>
					</c:if>
					<!-- /Coluna 3 -->
				</div>
			</c:if>

			<c:if test="${ ( meta.atividade.tipoMeta == 'META_IMPLANTACAO' || meta.atividade.tipoMeta == 'GRUPO_IMPLANTACAO' ) }">
			<div class="col-sm-2">
				<!-- Coluna 4 -->
				<c:choose>
					<c:when test="${meta.situacaoAnterior.situacao == 'IMPLANTADA' && (meta.situacaoAtual.situacao == 'IMPLANTADA' || meta.situacaoAtual.situacao == null ) }">
						<div class="form-group">
							<label class="control-label">Situação Planejada</label>
							<div>
								<div class="radio">
								    <label >
								      <form:radiobutton id="situacaoDesejada_situacao_naoplanejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" data-meta="${metaId}"  onclick="planejadoRadio(this);" disabled="true" /> Não Planejado
								    </label>
								</div>
								<div class="radio">
								    <label>
								      <form:radiobutton id="situacaoDesejada_situacao_planejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" data-meta="${metaId}"  onclick="planejadoRadio(this);" disabled="true" /> Planejado
								    </label>
								  </div>
							</div>
							<form:errors path="dependencias[${index}].situacaoDesejada.situacao" cssClass="label label-danger"/>
						</div>
						<div class="form-group">
							  <fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
							  <div class="input-group">
							  		<form:input id="situacaoDesejada_previsao_${metaId}" path="dependencias[${index}].situacaoAtual.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control " disabled="true" />
									<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
							  </div>
							  <form:errors path="dependencias[${index}].situacaoDesejada.previsao" cssClass="label label-danger"/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<label class="control-label">Situação Planejada</label>
							<div>   
								<div class="radio">
								    <label >
								      <form:radiobutton id="situacaoDesejada_situacao_naoplanejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" data-meta="${metaId}"  onclick="planejadoRadio(this);" /> Não Planejado
								    </label>
								</div>
								<div class="radio">
								    <label >
								      <form:radiobutton id="situacaoDesejada_situacao_planejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" data-meta="${metaId}" onclick="planejadoRadio(this);" /> Planejado
								    </label>
								  </div>
							</div>
							<form:errors path="dependencias[${index}].situacaoDesejada.situacao" cssClass="label label-danger"/>
						</div>
						<div class="form-group">
							<fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
							<div class="input-group">
								<form:input id="situacaoDesejada_previsao_${metaId}" path="dependencias[${index}].situacaoDesejada.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control " cssErrorClass="date-picker form-control  error-input" />
								<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
							</div>
							<form:errors path="dependencias[${index}].situacaoDesejada.previsao" cssClass="label label-danger"/>
						</div>
					</c:otherwise>
				</c:choose>
				<!-- /Coluna 4 -->
			</div>
			</c:if>
			
			<c:if test="${( meta.atividade.tipoMeta == 'META_EXECUCAO' || meta.atividade.tipoMeta == 'GRUPO_EXECUCAO' )}">
			<div class="col-sm-2">
				<!-- Coluna 4 -->
				<div class="form-group">
					<label class="control-label">Situação Planejada</label>
					<div>
						<div class="radio">
						    <label >
						      <form:radiobutton id="situacaoDesejada_situacao_naoplanejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" data-meta="${metaId}" onclick="planejadoRadio(this);" /> Não Planejado
						    </label>
						</div>
						<div class="radio">
						    <label >
						      <form:radiobutton id="situacaoDesejada_situacao_planejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" data-meta="${metaId}" onclick="planejadoRadio(this);" /> Planejado
						    </label>
						</div>
					</div> 
					<form:errors path="dependencias[${index}].situacaoDesejada.situacao" cssClass="label label-danger"/>
				</div>
				<div class="form-group">
					<fmt:formatDate value="${meta.situacaoDesejada.previsao}" var="planejado" type="date" pattern="MM/yyyy" />
					<div class="input-group">
						<form:input id="situacaoDesejada_previsao_${metaId}" path="dependencias[${index}].situacaoDesejada.previsao" placeholder="mês/ano" value="${planejado}"  class="date-picker form-control " />
						<span class="input-group-addon"><i class="fa fa-calendar"><jsp:text /></i></span>
					</div>
					<form:errors path="dependencias[${index}].situacaoDesejada.previsao" cssClass="label label-danger"/>
				</div>
				<!-- /Coluna 4 -->
			</div>
			</c:if>
			
			<c:if test="${meta.atividade.tipoMeta == 'META_QUANTITATIVA'}"> 
			<div class="col-sm-2">
				<!-- Coluna 4 -->
				<div class="form-group">
					<label class="control-label">Situação Planejada</label>
					<div>
						<div class="radio">
						    <label >
						      <form:radiobutton id="situacaoDesejada_situacao_naoplanejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="NAOPLANEJADA" data-meta="${metaId}" onclick="planejadoRadio(this);" /> Não Planejado
						    </label>
						</div>
						<div class="radio">
						    <label >
						      <form:radiobutton id="situacaoDesejada_situacao_planejada_${metaId}" path="dependencias[${index}].situacaoDesejada.situacao" value="PLANEJADA" data-meta="${metaId}" onclick="planejadoRadio(this);" /> Planejado
						    </label>
						</div>
					</div>
					<form:errors path="dependencias[${index}].situacaoDesejada.situacao" cssClass="label label-danger"/>
				</div>
				<div class="form-group"> 
					<fmt:formatNumber value="${meta.situacaoDesejada.previsto}" var="previsto" type="number" maxFractionDigits="0" />
					<form:input id="situacaoDesejada_previsto_${metaId}" path="dependencias[${index}].situacaoDesejada.previsto" value="${previsto}" class="form-control "/>
					<form:errors path="dependencias[${index}].situacaoDesejada.previsto" cssClass="label label-danger"/>
				</div><!-- /Coluna 4 -->
			</div>
			</c:if>
			
			
			<div class="col-sm-4">
				<!-- Coluna 5 -->
				<div class="form-group">
					<div class="col-md-12">
						<c:set var="indexObs" value="${fn:length(meta.observacoes) - 1}" scope="request"/>
						<script>
							var nivel${metaId} = 1;
							var indexObs${metaId} = ${fn:length(meta.observacoes)};
							var indexNivel1${metaId} = ${index};
						</script>
						<c:if test="${indexObs gt 0}">
						<ul class="list-group" id="observacoes${metaId}">
						  <c:forEach items="${meta.observacoes}" var="anotacao" varStatus="idAnot">
						  	<c:if test="${idAnot.index lt indexObs}">
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
							  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].ciclo.id" />
							  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].responsavel.id" />
							  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].texto" />
							  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].data" />
							  	<form:hidden path="dependencias[${index}].observacoes[${idAnot.index}].sinalizador" />
							  	<fmt:formatDate value="${anotacao.data}" var="dataAnotacao" type="date" pattern="dd/MM/yyyy" />
						  		${dataAnotacao} - ${anotacao.texto}
						  	</li>
						  	</c:if>
						  </c:forEach>
						</ul>
						</c:if>
						<c:if test="${indexObs lt 0}">
							<c:set var="indexObs" value="0" scope="request"/>
						</c:if>
						<div class="form-group">
							<form:hidden path="dependencias[${index}].observacoes[${indexObs}].id" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].remove" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].ciclo.id" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].nivel" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].responsavel.id" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].data" />
						  	<form:hidden path="dependencias[${index}].observacoes[${indexObs}].sinalizador" />
						  	<form:label path="dependencias[${index}].observacoes[${indexObs}].texto">Comentário:</form:label>
							<form:textarea id="observacoes_${metaId}" path="dependencias[${index}].observacoes[${indexObs}].texto" rows="5" maxlength="4000" class="form-control col-md-12 "/>
							<div class="col-md-12" style="font-size:11px;color:#ff8080; text-align:right;">Restam <div style="display:inline;" id="caracteresRestantes_${metaId}">4000</div> caracteres disponíveis.</div>
							<script>
							var textarea${metaId}=document.getElementById("observacoes_${metaId}");
							var caracteresRestantes${metaId}=document.getElementById("caracteresRestantes_${metaId}");
							textarea${metaId}.oninput = function(e){
							    caracteresRestantes${metaId}.innerHTML=(4000-this.value.length);
							}
							</script>
						</div>
					</div>
					<!--  div class="col-md-2">
						<button type="button" class="btn btn-success btn-xs btnAnotacaoSuccess" data-toggle="modal" data-id="${metaId}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
						<button type="button" class="btn btn-warning btn-xs btnAnotacaoWarning" data-toggle="modal" data-id="${metaId}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
						<button type="button" class="btn btn-danger btn-xs btnAnotacaoDanger" data-toggle="modal" data-id="${metaId}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
					</div-->
					<div class="col-sm-12">
						<form:errors path="dependencias[${index}].observacoes" cssClass="label label-danger"/>
					</div>
				</div><!-- /Coluna 3 -->
			</div>	
		</div>
		</c:if>
		
		<c:if test="${fn:length(meta.dependencias) > 0}">
			<c:set var="paiAtual" value="${posicao}" scope="page" />
			<div class="panel-group" id="accordion${posicao}">
			<spring:nestedPath path="dependencias[${index}]">
			    <c:forEach items="${meta.dependencias}" var="dependencia" varStatus="status">
			        <template:itemMeta meta="${dependencia}" index="${status.index}" count="${status.count}" pai="${paiAtual}" prefix="${prefix}${index+1}." prioridade="${prioridade}"/>
			    </c:forEach>
		    </spring:nestedPath>
		    </div>
		</c:if>
		
		</div><!-- /div panel-body -->
	</div><!-- /dev collapse -->
</div>
