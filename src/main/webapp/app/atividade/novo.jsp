<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
.autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:22px}
.autocomplete-selected { background: #F0F0F0; }
.autocomplete-suggestions strong { font-weight: bold; color: #3399FF; }
</style>
      <div class="row">
	            <form:form method="post" action="add" commandName="atividade" class="form-horizontal">
          			<fmt:formatDate value="${atividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
					<fmt:formatDate value="${atividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
					<form:hidden path="id"/>
					<form:hidden path="instituto.id"/>
					<form:hidden path="pai.id"/>
					<form:hidden path="viewOrder"/>
					
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="instituto.id">Instituto:</form:label>
		                <div class="col-sm-6">
							<form:select path="instituto.id" class="form-control input-sm" >
							    <option value=""></option>
							    <form:options items="${institutoList}"/>
							</form:select>
						</div>
					</div>
					
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="descricao">Descrição:</form:label>
		                <div class="col-sm-10">
							<form:input path="descricao" cssClass="form-control input-sm" />
						</div>
					</div>
					
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="frequencia">Frequência:</form:label>
		                <div class="col-sm-4">
							<form:select path="frequencia" class="form-control input-sm" >
							    <option value=""></option>
							        <c:forEach items="${frequenciaList}" var="option">
							        
							        	<c:choose>
										      <c:when test="${option eq atividade.frequencia}">
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
							</form:select>
						</div>
					</div>
					
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="diaSemana">Dia Semana:</form:label>
		                <div class="col-sm-4">
							<form:select path="diaSemana" class="form-control input-sm">
							    <option value=""></option>
							        <c:forEach items="${semanaList}" var="option">
							        	<c:choose>
										      <c:when test="${option eq atividade.diaSemana}">
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
							</form:select>
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="horaInicio">Início:</form:label>
		                <div class="col-sm-4">
							<form:input path="horaInicio" value="${horaInicio}" class="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="horaTermino">Término:</form:label>
		                <div class="col-sm-4">
							<form:input path="horaTermino" value="${horaTermino}" class="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="grupo">Cabeçalho de Grupo:</form:label>
		                <div class="col-sm-4">
							<form:checkbox path="grupo" class="form-control input-sm"/>
						</div>
					</div>
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="acao">Ação (Sem frequência):</form:label>
		                <div class="col-sm-4">
							<form:checkbox path="acao" class="form-control input-sm"/>
						</div>
					</div>
					
	                <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <input type="submit" value="Incluir Atividade" class="btn btn-default"/>
					    </div>
					  </div>
	                
	            </form:form>
	    </div>
	<script type="text/javascript">
		$(function(){

		});
	</script>