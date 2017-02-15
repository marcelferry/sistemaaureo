<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
				<c:if  test="${!empty estadoList}">
                <div class="panel panel-default">
	                <div class="panel-heading">
	                    Estados Cadastrados
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover" id="tabelaEstado">
                    <thead>
                    <tr>
                        <th>Cód.</th>
                        <th>Estado</th>
                        <th>Sigla</th>
                        <th>País</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${estadoList}" var="estado">
                        <tr>
                            <td>${estado.id}</td>
                            <td>${estado.nome}</td>
                            <td>${estado.sigla}</td>
                            <td>${estado.pais.nome}</td>
                            <td>
                            	<form action="edit/${estado.id}" method="post">
	                            	<input type="button" onclick="this.form.action = 'edit/${estado.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
	                            	<input type="button" onclick="this.form.action = 'delete/${estado.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
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
	            <c:if  test="${empty estadoList}">
            	<div class="panel panel-default">
	                <div class="panel-heading">
	                    Mensagem
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                Não há estados cadastradas.
		            </div>
		        </div>
            </c:if>
            <form action="add" method="get"><input type="submit" class="btn btn-primary" value="Novo"/></form>
	    </div>
	    
	    
	     <!-- Page-Level Plugin Scripts - Tables -->
    <script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#tabelaEstado').dataTable({
            "language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            }
        });
    });
    </script>