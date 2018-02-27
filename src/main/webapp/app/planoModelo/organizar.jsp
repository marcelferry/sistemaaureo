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
       			<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Dados Gerais 
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<form:label path="instituto.descricao">Instituto/Comissõo:</form:label>
	                	<form:input path="instituto.descricao" cssClass="form-control" readonly="true"/>
	                </div>
	            </div>
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Metas
	                </div>
	                <!-- /.panel-heading -->
	                <form:form method="post" action="sort" modelAttribute="planoModeloForm">
	                <form:hidden path="organizar" />
	                <form:hidden path="instituto.id" />
	                <div class="panel-body">
		                		            
		             <div class="col-sm-12">
			                <div class="dd">
							    <ol class="dd-list">
							    	<c:forEach items="${planoModeloForm.itens}" var="atividade" varStatus="status">
							        <li class="dd-item dd3-item" data-id="${ atividade.id }">
							            <div class="dd-handle dd3-handle">
											
							            </div>
							            <div class="dd3-content">
							            	<form:hidden path="itens[${status.index}].viewOrder" value="${status.count}" />
											<form:hidden path="itens[${status.index}].id"/>
											<form:hidden path="itens[${status.index}].pai.id"/>
											${ atividade.descricao }
							            </div>
							            <c:if test="${! empty atividade.itens}">
							            <ol class="dd-list">
							            	<c:forEach items="${atividade.itens}" var="subAtividade" varStatus="status2">
							                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
							                    <div class="dd-handle dd3-handle">
								                    
							                    </div>
							                    <div class="dd3-content">
							                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].viewOrder" value="${status2.count}" />
													<form:hidden path="itens[${status.index}].itens[${status2.index}].id"/>
													<form:hidden path="itens[${status.index}].itens[${status2.index}].pai.id"/>
													${ subAtividade.descricao }
							                    </div>
							                    <c:if test="${! empty subAtividade.itens}">
									            <ol class="dd-list">
									            	<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status3">
									                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
									                    <div class="dd-handle dd3-handle">
									                    	
									                    </div>
									                    <div class="dd3-content">
									                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].viewOrder" value="${status3.count}" />
										                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].id"/>
															<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].pai.id"/>
									                        ${ subAtividade.descricao }
									                    </div>
									                    <c:if test="${! empty subAtividade.itens}">
											            <ol class="dd-list">
											            	<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status4">
											                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
											                    <div class="dd-handle dd3-handle">
											                    	
											                    </div>
											                    <div class="dd3-content">
											                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].viewOrder" value="${status4.count}" />
												                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].id"/>
																	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].pai.id"/>
											                        ${ subAtividade.descricao }
											                    </div>
											                    <c:if test="${! empty subAtividade.itens}">
													            <ol class="dd-list">
													            	<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status5">
													                <li class="dd-item dd3-item" data-id="${ subAtividade.id }">
													                    <div class="dd-handle dd3-handle">
													                    	
													                    </div>
													                    <div class="dd3-content">
													                    	<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].viewOrder" value="${status5.count}" />
														                    <form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].id"/>
																			<form:hidden path="itens[${status.index}].itens[${status2.index}].itens[${status3.index}].itens[${status4.index}].itens[${status5.index}].pai.id"/>
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
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/pdfmake.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/pdfmake-0.1.18/build/vfs_fonts.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.16/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/DataTables-1.10.16/media/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.5.1/js/dataTables.buttons.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.5.1/js/buttons.bootstrap.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.5.1/js/buttons.colVis.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.5.1/js/buttons.html5.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Buttons-1.5.1/js/buttons.print.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Responsive-2.2.1/js/dataTables.responsive.js"></script>
<script type="text/javascript" src="/js/plugins/dataTables/Select-1.2.5/js/dataTables.select.js"></script>
    
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

    	if (window.JSON) {
	 		$("#organizar").val(window.JSON.stringify( $('.dd').nestable('serialize') )); //, null, 2));
        }

    });

    </script>
	   