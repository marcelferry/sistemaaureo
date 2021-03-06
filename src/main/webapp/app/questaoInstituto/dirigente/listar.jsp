<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
.child {
	/*display: none;*/
}
</style>

      <div class="row">
      			<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Dados Gerais 
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<form:label path="instituto.descricao">Instituto/Comiss�o:</form:label>
	                	<form:input path="instituto.descricao" cssClass="form-control" readonly="true"/>
	                </div>
	            </div>
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Metas
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover small" id="dataTables-example">
			                    <thead>
		                    <tr>
		                        <th class="col-md-7">ID - Meta</th>
		                        <th class="col-md-5"></th>
		                    </tr>
		                    </thead>
		                    <tbody id="ComTratativas">
		                    <c:forEach items="${planoModeloForm.itens}" var="atividade" varStatus="status">
								<tr id="atividade${status.count}_pai">
									<td>
										<c:if test="${! empty atividade.itens}">
											<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("atividade${status.count}_atividade${status.count}", this)' /> 
										</c:if>
										<c:if test="${ empty atividade.itens}">
											<img width="20" height="20" src="/img/spacer.png">
										</c:if>${atividade.viewOrder} - ${atividade.descricao}</td>
									 <td>
									 	<c:if test="${atividade.tipoMeta != 'GRUPO_METAS'}">
		                            	<form action="edit/${atividade.id}" method="post">
		                            		<div class="col-md-12">
			                            		<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/implantadas/${atividade.id}';submit();" class="btn btn-success btn-xs" value="Implantadas"/>
				                            	</div>
				                            	<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/planejadas/${atividade.id}';submit();" class="btn btn-primary btn-xs" value="Planejadas"/>
			                            		</div>
				                            	<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/naoplanejadas/${atividade.id}';submit();" class="btn btn-danger btn-xs" value="N�o Planejadas"/>
			                            		</div>
		                            		</div>
		                            	</form>
		                            	</c:if>
		                            </td>
								</tr>
								<c:forEach items="${atividade.itens}" var="subAtividade" varStatus="status2">
									<tr id="subatividade${status2.count}_atividade${status.count}" class="child">
										<td>
											<img width="20" height="20" src="/img/spacer.png">
											<c:if test="${! empty subAtividade.itens}">
												<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("subatividade${status2.count}_atividade${status.count}", this)' /> 
											</c:if>
											<c:if test="${ empty subAtividade.itens}">
												<img width="20" height="20" src="/img/spacer.png">
											</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
										<td>
										<c:if test="${subAtividade.tipoMeta != 'GRUPO_METAS'}">
		                            	<form action="edit/${subAtividade.id}" method="post">
			                            	<div class="col-md-12">
			                            		<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/implantadas/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Implantadas"/>
				                            	</div>
				                            	<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/planejadas/${subAtividade.id}';submit();" class="btn btn-primary btn-xs" value="Planejadas"/>
			                            		</div>
				                            	<div class="col-md-4">
				                            		<input type="button" onclick="this.form.action = 'listar/naoplanejadas/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="N�o Planejadas"/>
			                            		</div>
		                            		</div>
		                            	</form>
		                            	</c:if>
		                            	</td>
									</tr>
									<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status3">
										<tr id="ssubatividade${status3.count}_subatividade${status2.count}" class="child">
											<td>
												<img width="20" height="20" src="/img/spacer.png">
												<img width="20" height="20" src="/img/spacer.png">
												<c:if test="${! empty subAtividade.itens}">
													<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("ssubatividade${status3.count}_subatividade${status2.count}", this)' /> 
												</c:if>
												<c:if test="${ empty subAtividade.itens}">
													<img width="20" height="20" src="/img/spacer.png">
												</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
											<td>
											<c:if test="${subAtividade.tipoMeta != 'GRUPO_METAS'}">
			                            	<form action="edit/${subAtividade.id}" method="post">
				                            	<div class="col-md-12">
				                            		<div class="col-md-4">
					                            		<input type="button" onclick="this.form.action = 'listar/implantadas/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Implantadas"/>
					                            	</div>
					                            	<div class="col-md-4">
					                            		<input type="button" onclick="this.form.action = 'listar/planejadas/${subAtividade.id}';submit();" class="btn btn-primary btn-xs" value="Planejadas"/>
				                            		</div>
					                            	<div class="col-md-4">
					                            		<input type="button" onclick="this.form.action = 'listar/naoplanejadas/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="N�o Planejadas"/>
				                            		</div>
			                            		</div>
			                            	</form>
			                            	</c:if>
			                            	</td>
										</tr>
										<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status4">
											<tr id="sssubatividade${status4.count}_ssubatividade${status3.count}" class="child">
												<td>
													<img width="20" height="20" src="/img/spacer.png">
													<img width="20" height="20" src="/img/spacer.png">
													<img width="20" height="20" src="/img/spacer.png">
													<c:if test="${! empty subAtividade.itens}">
														<img src="/img/details_open.png"  onclick='showChildren_ComTratativas("sssubatividade${status4.count}_ssubatividade${status3.count}", this)' /> 
													</c:if>
													<c:if test="${ empty subAtividade.itens}">
														<img width="20" height="20" src="/img/spacer.png">
													</c:if>${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
												<td>
												<c:if test="${subAtividade.tipoMeta != 'GRUPO_METAS'}">
				                            	<form action="edit/${subAtividade.id}" method="post">
					                            	<div class="col-md-12">
					                            		<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/implantadas/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Implantadas"/>
							                            	</div>
							                            	<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/planejadas/${subAtividade.id}';submit();" class="btn btn-primary btn-xs" value="Planejadas"/>
						                            		</div>
							                            	<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/naoplanejadas/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="N�o Planejadas"/>
						                            		</div>
				                            		</div>
				                            	</form>
				                            	</c:if>
				                            	</td>
											</tr>
											<c:forEach items="${subAtividade.itens}" var="subAtividade" varStatus="status5">
												<tr id="ssssubatividade${status5.count}_sssubatividade${status4.count}" class="child">
													<td>
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">
														<img width="20" height="20" src="/img/spacer.png">${subAtividade.viewOrder} - ${subAtividade.descricao}</td>
													<td>
													<c:if test="${subAtividade.tipoMeta != 'GRUPO_METAS'}">
					                            	<form action="edit/${subAtividade.id}" method="post">
						                            	<div class="col-md-12">
						                            		<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/implantadas/${subAtividade.id}';submit();" class="btn btn-success btn-xs" value="Implantadas"/>
							                            	</div>
							                            	<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/planejadas/${subAtividade.id}';submit();" class="btn btn-primary btn-xs" value="Planejadas"/>
						                            		</div>
							                            	<div class="col-md-4">
							                            		<input type="button" onclick="this.form.action = 'listar/naoplanejadas/${subAtividade.id}';submit();" class="btn btn-danger btn-xs" value="N�o Planejadas"/>
						                            		</div>
					                            		</div>
					                            	</form>
					                            	</c:if>
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
		            <div class="col-md-12 hidden">
		            	<div class="col-md-2">
		            		<form:form method="get" action="add" commandName="planoModeloForm">
				                	<form:hidden path="instituto.id" />
				            		<input type="submit" class="btn btn-primary btn-mini" value="Nova Meta"/>
				         	</form:form>
			         	</div>
			         	<div class="col-md-2">
				            <form:form method="post" action="organizar" commandName="planoModeloForm">
				                	<form:hidden path="instituto.id" />
				            		<input type="submit" class="btn btn-primary btn-mini" value="Organizar"/>
				         	</form:form>
				         </div>
				         
				         <div class="col-md-2">
			            	<form:form method="get" action="upload" commandName="planoModeloForm">
			            		<form:hidden path="instituto.id" />
				            	<input type="button" class="btn btn-success btn-mini" onclick="submit();" value="Upload"/>
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
	   