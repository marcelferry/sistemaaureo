<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <div class="row">
				<c:if  test="${!empty userprofileList}">
                <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Usu�rios Cadastrados
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover" id="dataTables-example">
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email Acesso</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userprofileList}" var="usuario">
                        <tr>
                            <td>${usuario.pessoa.nome}</td>
                            <td>${usuario.username}</td>
                            <td>
                            	<form action="edit/${usuario.id}" method="post">
	                            	<button type="button" onclick="this.form.action = 'edit/${usuario.id}';submit();" class="btn btn-success btn-xs"  title="Editar"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
	                            	<button type="button" onclick="this.form.action = 'delete/${usuario.id}';submit();" class="btn btn-danger btn-xs"  title="Excluir"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
	                            	<button type="button" onclick="this.form.action = 'trocarsenha/${usuario.id}';submit();" class="btn btn-info btn-xs" title="Trocar Senha"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></button>
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
		                N�o h� pessoas cadastradas.
		            </div>
		        </div>
            </c:if>
            <form action="add" method="get"><input type="submit" class="btn btn-primary" value="Novo"/></form>
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