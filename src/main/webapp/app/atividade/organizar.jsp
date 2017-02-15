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
	                	<form:label path="instituto.descricao">Instituto/Comissõo:</form:label>
	                	<form:input path="instituto.descricao" cssClass="form-control" readonly="true"/>
	                </div>
	            </div>
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    Atividades
	                </div>
	                <!-- /.panel-heading -->
	                <form:form method="post" action="sort" modelAttribute="atividadeForm">
	                <form:hidden path="organizar" />
	                <form:hidden path="idInstituto" />
	                <div class="panel-body">
		                		            
		             <div class="col-sm-12">
			                <div class="dd">
							    <ol class="dd-list">
							    	<c:forEach items="${atividadeForm.atividades}" var="atividade" varStatus="status">
							        <li class="dd-item dd3-item" data-id="${ atividade.id }">
							            <div class="dd-handle dd3-handle">
											
							            </div>
							            <div class="dd3-content">
							            	<form:hidden path="atividades[${status.index}].viewOrder" value="${status.count}" />
											<form:hidden path="atividades[${status.index}].id"/>
											<form:hidden path="atividades[${status.index}].pai.id"/>
											${ atividade.descricao }
							            </div>
							            <c:if test="${! empty atividade.subAtividades}">
							            <ol class="dd-list">
							            	<c:forEach items="${atividade.subAtividades}" var="subAtividade" varStatus="status2">
							                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
							                    <div class="dd-handle dd3-handle">
								                    
							                    </div>
							                    <div class="dd3-content">
							                    	<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].viewOrder" value="${status2.count}" />
													<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].id"/>
													<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].pai.id"/>
													${ subAtividade.descricao }
							                    </div>
							                    <c:if test="${! empty subAtividade.subAtividades}">
									            <ol class="dd-list">
									            	<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status3">
									                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
									                    <div class="dd-handle dd3-handle">
									                    	
									                    </div>
									                    <div class="dd3-content">
									                    	<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].viewOrder" value="${status3.count}" />
										                    <form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].id"/>
															<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].pai.id"/>
									                        ${ subAtividade.descricao }
									                    </div>
									                    <c:if test="${! empty subAtividade.subAtividades}">
											            <ol class="dd-list">
											            	<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status4">
											                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
											                    <div class="dd-handle dd3-handle">
											                    	
											                    </div>
											                    <div class="dd3-content">
											                    	<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].viewOrder" value="${status4.count}" />
												                    <form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].id"/>
																	<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].pai.id"/>
											                        ${ subAtividade.descricao }
											                    </div>
											                    <c:if test="${! empty subAtividade.subAtividades}">
													            <ol class="dd-list">
													            	<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status5">
													                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
													                    <div class="dd-handle dd3-handle">
													                    	
													                    </div>
													                    <div class="dd3-content">
													                    	<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].subAtividades[${status5.index}].viewOrder" value="${status5.count}" />
														                    <form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].subAtividades[${status5.index}].id"/>
																			<form:hidden path="atividades[${status.index}].subAtividades[${status2.index}].subAtividades[${status3.index}].subAtividades[${status4.index}].subAtividades[${status5.index}].pai.id"/>
													                        ${ subAtividade.descricao }
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
	    
	    
	     <!-- Page-Level Plugin Scripts - Tables -->
    <script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
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
	   