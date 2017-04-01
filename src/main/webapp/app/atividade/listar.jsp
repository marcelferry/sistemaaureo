<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>
.child {
	display: none;
}
</style>

      <div class="row">
      			<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Dados Gerais 
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<form:label path="instituto.descricao">Instituto/Comissão:</form:label>
	                	<form:input path="instituto.descricao" cssClass="form-control" readonly="true"/>
	                </div>
	            </div>
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Atividades
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover small" id="dataTables-example">
			                    <thead>
		                    <tr>
		                        <th class="col-md-5">ID - Atividade</th>
		                        <th class="col-md-2">Frequência</th>
		                        <th class="col-md-2">Dia Semana</th>
		                        <sec:authorize access="hasRole('ROLE_AUREO')">
			                        <th class="col-md-1">Hora Início</th>
			                        <th class="col-md-1">Hora Fim</th>
		                        </sec:authorize>
		                        <th class="col-md-1"></th>
		                    </tr>
		                    </thead>
		                    <tbody id="ComTratativas">
		                    <c:forEach items="${atividadeForm.atividades}" var="atividade" varStatus="status">
								<tr id="atividade${status.count}_pai">
									<fmt:formatDate value="${atividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
									<fmt:formatDate value="${atividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
									<td>
										<c:if test="${! empty atividade.subAtividades}">
											<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("atividade${status.count}_atividade${status.count}", this)' /> 
										</c:if>
										<c:if test="${ empty atividade.subAtividades}">
											<img width="20" height="20" src="/img/spacer.png">
										</c:if>${atividade.viewOrder} - ${atividade.descricao}</td>
									<td>${atividade.frequencia}</td>
									<td>${atividade.diaSemana}</td>
									<sec:authorize access="hasRole('ROLE_AUREO')">
										<td>${horaInicio}</td>
										<td>${horaTermino}</td>
									</sec:authorize>
									 <td>
		                            	<form action="edit/${atividade.id}" method="post">
		                            		<div class="col-md-12">
			                            		<div class="col-md-6">
				                            		<input type="button" onclick="this.form.action = 'edit/${atividade.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
				                            	</div>
				                            	<div class="col-md-6">
				                            		<input type="button" onclick="this.form.action = 'delete/${atividade.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
			                            		</div>
		                            		</div>
		                            	</form>
		                            </td>
								</tr>
								<c:forEach items="${atividade.subAtividades}" var="subAtividade" varStatus="status2">
									<tr id="subatividade${status2.count}_atividade${status.count}" class="child">
										<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
										<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
										<td>
											<img width="20" height="20" src="/img/spacer.png">
											<c:if test="${! empty subAtividade.subAtividades}">
												<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("subatividade${status2.count}_atividade${status.count}", this)' /> 
											</c:if>
											<c:if test="${ empty subAtividade.subAtividades}">
												<img width="20" height="20" src="/img/spacer.png">
											</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
										<td>${subAtividade.frequencia}</td>
										<td>${subAtividade.diaSemana}</td>
										 <sec:authorize access="hasRole('ROLE_AUREO')">
										<td>${horaInicio}</td>
										<td>${horaTermino}</td>
										</sec:authorize>
										<td>
		                            	<form action="edit/${subAtividade.id}" method="post">
			                            	<div class="col-md-12">
			                            		<div class="col-md-6">
				                            		<input type="button" onclick="this.form.action = 'edit/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
				                            	</div>
				                            	<div class="col-md-6">
				                            		<input type="button" onclick="this.form.action = 'delete/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
			                            		</div>
		                            		</div>
		                            	</form>
		                            	</td>
									</tr>
									<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status3">
										<tr id="ssubatividade${status3.count}_subatividade${status2.count}" class="child">
											<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
											<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
											<td>
												<img width="20" height="20" src="/img/spacer.png">
												<img width="20" height="20" src="/img/spacer.png">
												<c:if test="${! empty subAtividade.subAtividades}">
													<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("ssubatividade${status3.count}_subatividade${status2.count}", this)' /> 
												</c:if>
												<c:if test="${ empty subAtividade.subAtividades}">
													<img width="20" height="20" src="/img/spacer.png">
												</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
											<td>${subAtividade.frequencia}</td>
											<td>${subAtividade.diaSemana}</td>
											 <sec:authorize access="hasRole('ROLE_AUREO')">
											<td>${horaInicio}</td>
											<td>${horaTermino}</td>
											</sec:authorize>
											<td>
			                            	<form action="edit/${subAtividade.id}" method="post">
				                            	<div class="col-md-12">
				                            		<div class="col-md-6">
					                            		<input type="button" onclick="this.form.action = 'edit/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
					                            	</div>
					                            	<div class="col-md-6">
					                            		<input type="button" onclick="this.form.action = 'delete/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
				                            		</div>
			                            		</div>
			                            	</form>
			                            	</td>
										</tr>
										<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status4">
											<tr id="sssubatividade${status4.count}_ssubatividade${status3.count}" class="child">
												<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
												<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
												<td>
													<img width="20" height="20" src="/img/spacer.png">
													<img width="20" height="20" src="/img/spacer.png">
													<img width="20" height="20" src="/img/spacer.png">
													<c:if test="${! empty subAtividade.subAtividades}">
														<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("sssubatividade${status4.count}_ssubatividade${status3.count}", this)' /> 
													</c:if>
													<c:if test="${ empty subAtividade.subAtividades}">
														<img width="20" height="20" src="/img/spacer.png">
													</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
												<td>${subAtividade.frequencia}</td>
												<td>${subAtividade.diaSemana}</td>
												 <sec:authorize access="hasRole('ROLE_AUREO')">
												<td>${horaInicio}</td>
												<td>${horaTermino}</td>
												</sec:authorize>
												<td>
				                            	<form action="edit/${subAtividade.id}" method="post">
					                            	<div class="col-md-12">
					                            		<div class="col-md-6">
						                            		<input type="button" onclick="this.form.action = 'edit/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
						                            	</div>
						                            	<div class="col-md-6">
						                            		<input type="button" onclick="this.form.action = 'delete/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
					                            		</div>
				                            		</div>
				                            	</form>
				                            	</td>
											</tr>
											<c:forEach items="${subAtividade.subAtividades}" var="subAtividade" varStatus="status5">
												<tr id="ssssubatividade${status5.count}_sssubatividade${status4.count}" class="child">
													<fmt:formatDate value="${subAtividade.horaInicio}" var="horaInicio" type="date" pattern="HH:mm" />
													<fmt:formatDate value="${subAtividade.horaTermino}" var="horaTermino" type="date" pattern="HH:mm" />
													<td>
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<c:if test="${! empty subAtividade.subAtividades}">
															<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("ssssubatividade${status5.count}_sssubatividade${status4.count}", this)' /> 
														</c:if>
														<c:if test="${ empty subAtividade.subAtividades}">
															<img width="20" height="20" src="/img/spacer.png">
														</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
													<td>${subAtividade.frequencia}</td>
													<td>${subAtividade.diaSemana}</td>
													 <sec:authorize access="hasRole('ROLE_AUREO')">
													<td>${horaInicio}</td>
													<td>${horaTermino}</td>
													</sec:authorize>
													<td>
					                            	<form action="edit/${subAtividade.id}" method="post">
						                            	<div class="col-md-12">
						                            		<div class="col-md-6">
							                            		<input type="button" onclick="this.form.action = 'edit/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
							                            	</div>
							                            	<div class="col-md-6">
							                            		<input type="button" onclick="this.form.action = 'delete/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
						                            		</div>
					                            		</div>
					                            	</form>
					                            	</td>
												</tr>
											</c:forEach>
										</c:forEach>
									</c:forEach>
								</c:forEach>
							</c:forEach>
		                    </tbody>
		                </table>
		            </div>
		            <div class="col-md-12">
		            	<div class="col-md-2">
		            		<form:form method="get" action="add" commandName="atividadeForm">
			                	<form:hidden path="idInstituto" />
			            		<input type="submit" class="btn btn-primary btn-mini" value="Nova Atividade"/>
				         	</form:form>
			         	</div>
			         	<div class="col-md-2">
				            <form:form method="post" action="editar" commandName="atividadeForm">
			                	<form:hidden path="idInstituto" />
			            		<input type="submit" class="btn btn-primary btn-mini" value="Editar Todos"/>
				         	</form:form>
				         </div>
			         	<div class="col-md-2">
				            <form:form method="post" action="organizar" commandName="atividadeForm">
			                	<form:hidden path="idInstituto" />
			            		<input type="submit" class="btn btn-primary btn-mini" value="Organizar"/>
				         	</form:form>
				         </div>
			         	<div class="col-md-2">
			            	<form:form method="get" action="upload" commandName="atividadeForm">
			            		<form:hidden path="idInstituto" />
				            	<input type="button" onclick="submit();" class="btn btn-success" value="Upload"/>
				            </form:form>
			         	</div>
		         	</div>
		        </div>
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
//         $('#dataTables-example').dataTable({
//             "language": {
//                 "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
//             }
//         });
    });


    var colunaLinks = 0;

        function naoExibir_ComTratativas(idbox) {
            var nodes = document.getElementById(idbox).childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var obj = nodes[i];
                if (obj.id) {
                    obj.style.display = "block";
                    break;
                }
            }
        }

        function getElements_ComTratativas(idbox) {
            var nodes = document.getElementById(idbox).childNodes;
            var names = new Array();
            for (var i = 0; i < nodes.length; i++) {
                var obj = nodes[i];
                if (obj.id)
                    names.push(nodes[i].id);
            }
            return names;
        }

        function showChildren_ComTratativas(elem, img) {
            var SEP = "_";
             
            var children = getElements_ComTratativas('ComTratativas'); 
            for (var i = 0; i < children.length; i++) {
                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
                    var localObj = document.getElementById(children[i]);
                    var status = localObj.style.display;
                    if (status == "table-row") {
    					localObj.style.display = "none";
    					hideChildren_ComTratativas(localObj.id, children, img);
    					img.src = img.src.replace("details_close.png","details_open.png");
                    } else {
                        localObj.style.display = "table-row";
    					img.src = img.src.replace("details_open.png","details_close.png");
                    }
    				reOpenNodes(localObj);
                }
            }
        }
    	function reOpenNodes(obj){
    		var nodeTd = obj.getElementsByTagName("td")[colunaLinks];
    		var imgs = nodeTd.getElementsByTagName("img");
    		
    		for (var j = 0; j < imgs.length; j++) {
    			img2 = imgs[j];
    			img2.src = img2.src.replace("details_close.png","details_open.png");
    		}
    		
    		var SEP = "_";
            var children = getElements_ComTratativas('ComTratativas'); 
    		elem = obj.id;
            for (var i = 0; i < children.length; i++) {
    		//alert(children[i].split(SEP)[1] +"  "+parent.id.split(SEP)[1]);
                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
    			 var localObj = document.getElementById(children[i]);
                    return reOpenNodes(localObj);//rever
    			}
    		}
    		
    	}

        function hideChildren_ComTratativas(elem, children, img) {
            var SEP = "_"; 

            for (var i = 0; i < children.length; i++) {

                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
                    var localObj = document.getElementById(children[i]);
                    var status = localObj.style.display;
                    if (status == "table-row") {
                        localObj.style.display = "none";
                        hideChildren_ComTratativas(localObj.id, children, img);
    					img.src = img.src.replace("details_close.png","details_open.png");
                    }
                }
            }
        }

    
    </script>
	   