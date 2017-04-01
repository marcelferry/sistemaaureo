<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
td.detais-controls {
	cursor: pointer;
}
tr.shown td.detais-controls {
}



</style>
      <div class="row">
       			<div class="panel panel-default">
	                <div class="panel-heading">
	                    Dados Gerais 
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<form:label path="instituto.descricao">Instituto/Comissão:</form:label>
	                	<form:input path="instituto.descricao" cssClass="form-control" readonly="true"/>
	                </div>
	            </div>
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    Atividades
	                </div>
	                <!-- /.panel-heading -->
	                <form:form method="post" action="process" modelAttribute="atividadeForm">
	                <div class="panel-body">
		                <div class="table-responsive">
		                	<form:hidden path="idInstituto" />
			                <table class="table table-bordered table-striped table-hover display small" id="dataTables-example">
			                    <thead>
		                    <tr valign="top">
		                        <th class="col-md-1">Cod.</th>
		                        <th class="col-md-4">Atividade</th>
		                        <th class="col-md-1">Frequência</th>
		                        <th class="col-md-1">Dia Semana</th>
		                        <th class="col-md-1">Hora Iní­cio</th>
		                        <th class="col-md-1">Hora Fim</th>
		                        <th class="col-md-2">&nbsp;</th>
		                    </tr>
		                    </thead>
		                    <tbody  id="ComTratativas">
		                    <c:forEach items="${atividadeForm.atividades}" var="atividade" varStatus="status">
		                    	<fmt:formatDate value="${atividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
								<fmt:formatDate value="${atividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
								<tr id="atividade${status.count}_pai">
									<td colspan="2">
										<div class="form-group">
											<form:hidden path="atividades[${status.index}].viewOrder" value="${status.count}" />
											<form:hidden path="atividades[${status.index}].id"/>
											<label id="orderView" for="inputKey" class="col-sm-1 control-label"><span>${status.count}</span></label>
											<div class="col-sm-10">
												<form:input path="atividades[${status.index}].descricao" cssClass="form-control input-sm" />
											</div>
										</div>
									</td>
									<td>
									<form:select path="atividades[${status.index}].frequencia" class="form-control input-sm" >
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
									</td>
									<td>
									<form:select path="atividades[${status.index}].diaSemana" class="form-control input-sm">
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
									</form:select></td>
									<td>
										<form:input path="atividades[${status.index}].horaInicio" value="${horaInicio}" class="form-control input-sm"/>
									</td>
									<td>
										<form:input path="atividades[${status.index}].horaTermino" value="${horaTermino}" class="form-control input-sm"/>
									</td>
									<td>
										<button type="button" class="btn btn-primary btn-xs" title="Subir um nÃ­vel">
	  										<span class="glyphicon glyphicon-chevron-up up">
	  										</span>
										</button>
										<button type="button" class="btn btn-primary btn-xs" title="Descer um nÃ­vel">
	  										<span class="glyphicon glyphicon-chevron-down down">
	  										</span>
										</button>
										<button type="button" class="btn btn-primary btn-xs">
	  										<span class="glyphicon glyphicon-indent-right">
	  										</span>
										</button>
										<form:form action="delete/${atividade.id}" method="post">
											<button type="submit" class="btn btn-primary btn-xs">
		  										<span class="glyphicon glyphicon-remove-sign">
		  										</span>
											</button>
										</form:form>
									</td>
								</tr>
								
								<!-- SubAtividades -->
								<c:forEach items="${atividade.subAtividades}" var="subAtividade" varStatus="status2">
									<tr id="subatividade${status2.count}_atividade${status.count}" class="child">
										<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
										<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
										<td colspan="2">
											<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].viewOrder" value="${status2.count}" />
											<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].id"/>
											<div class="form-group">
												<label for="inputKey" class="col-sm-2 control-label"><img width="20" height="20" src="/img/spacer.png"><span>${status2.count}</span></label>
												<div class="col-sm-9">
													<form:input path="atividades[${status.index}].subAtividades[${status2.index}].descricao" cssClass="form-control input-sm" />
												</div>
											</div>
										</td>
										<td>
										<form:select path="atividades[${status.index}].subAtividades[${status2.index}].frequencia" class="form-control input-sm">
										    <option value=""></option>
										        <c:forEach items="${frequenciaList}" var="option">
											        <c:choose>
													      <c:when test="${option eq subAtividade.frequencia}">
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
										</td>
										<td>
										<form:select path="atividades[${status.index}].subAtividades[${status2.index}].diaSemana" class="form-control input-sm">
										    <option value=""></option>
										        <c:forEach items="${semanaList}" var="option">
										         	<c:choose>
													      <c:when test="${option eq subAtividade.diaSemana}">
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
										</form:select></td>
										<td>
											<form:input path="atividades[${status.index}].subAtividades[${status2.index}].horaInicio" value="${horaInicio}" class="form-control input-sm"/>
										</td>
										<td>
											<form:input path="atividades[${status.index}].subAtividades[${status2.index}].horaTermino" value="${horaTermino}" class="form-control input-sm"/>
										</td>
										<td>
											<button type="button" class="btn btn-primary btn-xs" title="Subir um nÃ­vel">
		  										<span class="glyphicon glyphicon-chevron-up up">
		  										</span>
											</button>
											<button type="button" class="btn btn-primary btn-xs" title="Descer um nÃ­vel">
		  										<span class="glyphicon glyphicon-chevron-down down">
		  										</span>
											</button>
											<button type="button" class="btn btn-primary btn-xs" title="Desagrupar">
		  										<span class="glyphicon glyphicon-indent-left">
		  										</span>
											</button>
											<button type="button" class="btn btn-primary btn-xs" title="Tornar filho">
		  										<span class="glyphicon glyphicon-indent-right">
		  										</span>
											</button>
											<form:form action="delete/${subAtividade.id}" method="post">
												<button type="submit" class="btn btn-primary btn-xs">
			  										<span class="glyphicon glyphicon-remove-sign">
			  										</span>
												</button>
											</form:form>
										</td>
									</tr>
									<!-- SubAtividades -->
									<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status3">
										<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
										<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
										<tr id="ssubatividade${status3.count}_subatividade${status2.count}" class="child">
											<td><img width="20" height="20" src="/img/spacer.png"><input id="atividades${status.index}.subAtividades${status2.index}.subAtividades${status3.index}.viewOrder" name="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].viewOrder" value="${status3.count}" size="5" class="form-control"/></td>
											<td>
												<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].id"/>
												<form:input path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].descricao" cssClass="form-control"/>
											</td>
											<td>
											<select name="atividades[${status.index}]..subAtividades[${status2.index}]subAtividades[${status3.index}].frequencia" id="frequenciaLst" class="form-control">
											    <option value=""></option>
											        <c:forEach items="${frequenciaList}" var="option">
												        <c:choose>
														      <c:when test="${option eq subAtividade.frequencia}">
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
											<form:select path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].diaSemana" class="form-control input-sm">
											    <option value=""></option>
											        <c:forEach items="${semanaList}" var="option">
											        	<c:choose>
														      <c:when test="${option eq subAtividade.diaSemana}">
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
											</form:select></td>
											<td>
												<form:input path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].horaInicio" value="${horaInicio}" class="form-control input-sm"/>
											</td>
											<td>
												<form:input path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].horaTermino" value="${horaTermino}" class="form-control input-sm"/>
											</td>
											<td>
												<button type="button" class="btn btn-primary btn-xs" title="Subir um nÃ­vel">
			  										<span class="glyphicon glyphicon-chevron-up up">
			  										</span>
												</button>
												<button type="button" class="btn btn-primary btn-xs" title="Descer um nÃ­vel">
			  										<span class="glyphicon glyphicon-chevron-down down">
			  										</span>
												</button>
												<button type="button" class="btn btn-primary btn-xs" title="Desagrupar">
			  										<span class="glyphicon glyphicon-indent-left">
			  										</span>
												</button>
												<form:form action="delete/${subAtividade.id}" method="post">
													<button type="submit" class="btn btn-primary btn-xs">
				  										<span class="glyphicon glyphicon-remove-sign">
				  										</span>
													</button>
												</form:form>
											</td>
										</tr>
									</c:forEach>
									<!-- fim subAtividades -->
								</c:forEach>
								<!-- fim subAtividades -->
							</c:forEach>
		                    </tbody>
		                </table>
		                <input type="button" class="btn btn-primary" value="Add" name="btnAdd" id="btnAdd"/>
		                <input type="submit" class="btn btn-primary" value="Salvar"/>
		                
		            </div>
		            
		        </div>
		         </form:form>
		        </div>
	    </div>
	    
	   
<!-- Page-Level Plugin Scripts - Tables -->
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/pdfmake.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/vfs_fonts.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.13/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.13/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/dataTables.buttons.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.colVis.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.html5.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.2.4/js/buttons.print.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Responsive-2.1.1/js/dataTables.responsive.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Select-1.2.0/js/dataTables.select.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    
    $(document).ready(function() {
		var i = 1;

        $('#btnAdd').click(function(){
        	$('#dataTables-example tr:last').clone().
        	find('input').each(function() {
            	$(this).attr({
                	'id' : function(_, id) { return id + i},
                	'name' : function(_, name) { 
                		var inicio = name.substring(0, name.indexOf("["));
                		var meio = name.substring(name.indexOf("[") + 1, name.indexOf("]") );
                		var fim = name.substring(name.indexOf("]") + 1);
                		meio = eval(meio) + 1;
                    	return inicio + '[' + meio + ']' + fim;
                    },
                	'value' : ''
            	});
            	$(this).val('');
        	}).end().
        	find('select').each(function() {
            	$(this).attr({
                	'id' : function(_, id) { return id + i},
                	'name' : function(_, name) { 
                		var inicio = name.substring(0, name.indexOf("["));
                		var meio = name.substring(name.indexOf("[") + 1, name.indexOf("]") );
                		var fim = name.substring(name.indexOf("]") + 1);
                		meio = eval(meio) + 1;
                    	return inicio + '[' + meio + ']' + fim;
                    }
            	});
            	$(this).val(0);
        	}).end().appendTo('#dataTables-example');
        	$('#dataTables-example tr:last').find('input:first').val('0');	
        	i++;
        });
    	
        $(".up,.down").click(function(){
            var row = $(this).parents("tr:first");
            if ($(this).is(".up")) {
                if(row.index() > 0){
             	var orderNovo = row.prev().find("span:first").text();
             	var orderAtual = row.find("span:first").text();
             	row.prev().find("span:first").text(orderAtual);
                 row.find("span:first").text(orderNovo) ;
                 row.insertBefore(row.prev());
                }
            } else {
            	var orderNovo = row.next().find("span:first").text();
            	var orderAtual = row.find("span:first").text();
            	row.next().find("span:first").text(orderAtual);
                row.find("span:first").text(orderNovo) ;
                row.insertAfter(row.next());
            }
        });

    });

    </script>
	   