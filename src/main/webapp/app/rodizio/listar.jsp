<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

      <div class="row">
	            <c:if  test="${!empty rodizioList}">
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    <spring:message code="label.ciclos.cadastrados" />
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
			                    <thead>
		                    <tr>
		                        <th class="col-md-2"><spring:message code="label.codigo.resumido" /></th>
		                        <th class="col-md-2"><spring:message code="label.ciclo" /></th>
		                        <th class="col-md-2"><spring:message code="label.data.fechamento.ciclo" /></th>
		                        <th class="col-md-2"><spring:message code="label.data.inicio.ajustes" /></th>
		                        <th class="col-md-2"><spring:message code="label.data.termino.ajustes" /></th>
		                        <th class="col-md-2">&nbsp;</th>
		                    </tr>
		                    </thead>
		                    <tbody>
		                    <c:forEach items="${rodizioList}" var="rodizio">
		                    	<fmt:formatDate value="${rodizio.dataAprovacao}" var="dataRodizio" type="date" pattern="dd/MM/yyyy" />
		                    	<fmt:formatDate value="${rodizio.inicioAjustes}" var="inicioAjustes" type="date" pattern="dd/MM/yyyy" />
		                    	<fmt:formatDate value="${rodizio.terminoAjustes}" var="terminoAjustes" type="date" pattern="dd/MM/yyyy" />
		                        <tr>
		                            <td>${rodizio.id}</td>
		                            <td>${rodizio.ciclo}</td>
		                            <td>${dataRodizio}</td>
		                            <td>${inicioAjustes}</td>
		                            <td>${terminoAjustes}</td>
		                            <td>
		                            	<form action="edit/${rodizio.id}" method="post">
		                            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			                            	<button type="button" onclick="this.form.action = 'edit/${rodizio.id}';submit();" class="btn btn-success btn-xs" >Editar</button>&nbsp
			                            	<button type="button" onclick="this.form.action = 'delete/${rodizio.id}';submit();" class="btn btn-danger btn-xs" >Excluir</button>
		                            	</form>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </tbody>
		                </table>
		            </div>
		        </div>
		        </div>
	            </c:if>
	            <c:if  test="${empty rodizioList}">
            	<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Mensagem
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <spring:message code="label.ciclos.cadastrados.vazio" />
		            </div>
		        </div>
            </c:if>
            <form action="add" method="get">
            	<button type="submit" class="btn btn-primary">Novo</button>
            </form>
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
        $('#dataTables-example').dataTable({
            "language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            }
        });
    });
    </script>
	   