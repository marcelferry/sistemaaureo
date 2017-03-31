<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
	            <c:if  test="${!empty comissaoList}">
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Comiss�es Cadastrados
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
			                    <thead>
		                    <tr>
		                        <th>ID</th>
		                        <th>Nome</th>
		                        <th>Instituto</th>
		                        <th>Sala no Rodizio</th>
		                        <th>&nbsp;</th>
		                    </tr>
		                    </thead>
		                    <tbody>
		                    <c:forEach items="${comissaoList}" var="comissao">
		                        <tr>
		                            <td>${comissao.id}</td>
		                            <td>${comissao.descricao}</td>
		                            <td>${comissao.instituto.descricao}</td>
		                            <td>${comissao.rodizio?"Sim":"N�o"}</td>
		                            <td>
		                            	<form action="edit/${comissao.id}" method="post">
			                            	<input type="button" onclick="this.form.action = 'edit/${comissao.id}';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp
			                            	<input type="button" onclick="this.form.action = 'delete/${comissao.id}';submit();" class="btn btn-danger btn-xs" value="Excluir"/>
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
	            <c:if  test="${empty comissaoList}">
            	<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Mensagem
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                N�o h� Comiss�es cadastradas.
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
	   