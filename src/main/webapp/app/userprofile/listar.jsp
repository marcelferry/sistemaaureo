<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
				<c:if  test="${!empty userprofileList}">
                <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Usuários Cadastrados
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                    <thead>
                    <tr>
                        <th>Usuário</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userprofileList}" var="usuario">
                        <tr>
                            <td>${usuario.username}</td>
                            <td>
                            	<form action="edit/${usuario.id}" method="post">
	                            	<input type="button" onclick="this.form.action = 'edit/${usuario.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
	                            	<input type="button" onclick="this.form.action = 'delete/${usuario.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
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
	            <c:if  test="${empty userprofileList}">
            	<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Mensagem
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                Não há pessoas cadastradas.
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
        $('#dataTables-example').dataTable({
            "language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            }
        });
    });
    </script>