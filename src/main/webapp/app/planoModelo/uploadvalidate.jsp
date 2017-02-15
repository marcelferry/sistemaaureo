<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      <div class="row">
      
      <div class="panel panel-default">
	                <div class="panel-heading">
	                    Atividades
	                </div>
	                <!-- /.panel-heading -->
	                <form:form method="post" action="processImport" modelAttribute="planoModeloForm">
	                <form:hidden path="organizar" />
	                <form:hidden path="instituto.id" />
	                <div class="panel-body">
		                		            
		             <div class="col-sm-12">
			                <div class="dd">
							    <ol class="dd-list">
							    	<c:forEach items="${listAtividades}"  var="atividade" varStatus="status">
							        <li class="dd-item dd3-item" data-id="${status.count}">
							            <div class="dd-handle dd3-handle">
											
							            </div>
							            <div class="dd3-content">
							            	<form:hidden path="itens[${status.index}].viewOrder" value="${status.count}" />
											<form:hidden path="itens[${status.index}].id"/>
											<form:hidden path="itens[${status.index}].descricao" value="${atividade.nome}"/>
											${atividade.viewOrder} - ${atividade.nome}
							            </div>
							            <c:if test="${! empty atividade.lista}">
							            <ol class="dd-list">
							            	<c:forEach items="${atividade.lista}" var="subAtividade" varStatus="status2">
							                <li class="dd-item dd3-item" data-id="${status2.index}">
							                    <div class="dd-handle dd3-handle">
								                    
							                    </div>
							                    <div class="dd3-content">
							                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].viewOrder" value="${status2.count}" />
													<form:hidden path="itens[${status.index}].itens[${status2.index}].id"/>
													<form:hidden path="itens[${status.index}].itens[${status2.index}].descricao" value="${subAtividade.nome}"/>
													${subAtividade.viewOrder} - ${subAtividade.nome}
							                    </div>
							                    <c:if test="${! empty subAtividade.lista}">
									            <ol class="dd-list">
									            	<c:forEach items="${subAtividade.lista}" var="subAtividade" varStatus="status3">
									                <li class="dd-item dd3-item" data-id="${status3.index}">
									                    <div class="dd-handle dd3-handle">
									                    	
									                    </div>
									                    <div class="dd3-content">
									                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].viewOrder" value="${status3.count}" />
										                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].id"/>
															<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].descricao" value="${subAtividade.nome}"/>
									                        ${subAtividade.viewOrder} - ${subAtividade.nome}
									                    </div>
									                    <c:if test="${! empty subAtividade.lista}">
											            <ol class="dd-list">
											            	<c:forEach items="${subAtividade.lista}" var="subAtividade" varStatus="status4">
											                <li class="dd-item dd3-item" data-id="${status4.index}">
											                    <div class="dd-handle dd3-handle">
											                    	
											                    </div>
											                    <div class="dd3-content">
											                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].viewOrder" value="${status4.count}" />
												                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].id"/>
																	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].descricao" value="${subAtividade.nome}"/>
											                        ${subAtividade.viewOrder} - ${subAtividade.nome}
											                    </div>
											                    <c:if test="${! empty subAtividade.lista}">
													            <ol class="dd-list">
													            	<c:forEach items="${subAtividade.lista}" var="subAtividade" varStatus="status5">
													                <li class="dd-item dd3-item" data-id="${status5.index}">
													                    <div class="dd-handle dd3-handle">
													                    	
													                    </div>
													                    <div class="dd3-content">
													                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].viewOrder" value="${status5.count}" />
														                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].id"/>
																			<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].descricao" value="${subAtividade.nome}"/>
													                        ${subAtividade.viewOrder} - ${subAtividade.nome}
													                    </div>
													                </li>
													                </c:forEach>
													            </ol>
													            </c:if>
											                </li>
											                </c:forEach>
											            </ol>
											            </c:if>
									                </li>
									                </c:forEach>
									            </ol>
									            </c:if>
							                </li>
							                </c:forEach>
							            </ol>
							            </c:if>
							        </li>
							        </c:forEach>
							    </ol>
							</div>
		                </div>
		               <input type="submit" class="btn btn-primary" value="Salvar"/>
		            
		        </div>
		         </form:form>
		        </div>
        </div>
        
         <script>
    
    $(document).ready(function() {

    	$('.dd').nestable();

    	$('.dd').on('change', function() {
    	 	if (window.JSON) {
    	 		$("#organizar").val(window.JSON.stringify( $('.dd').nestable('serialize') )); //, null, 2));
            } else {
               alert('JSON browser support required for this demo.');
            }
    	 	
    	});

    });

    </script>
