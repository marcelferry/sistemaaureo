<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
<div class="row">
	<c:if test="${!empty rodizioList}">
		<div class="panel panel-primary">
			<div class="panel-heading">Rodízios</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
					<form:form method="post" commandName="facilitadorForm"
						action="institutos">
						<form:hidden path="fase" value="${fase}" />
						<form:hidden path="rodizio.id" />
						<table class="table table-bordered table-striped table-hover"
							id="tabelaRodizio">
							<thead>
								<tr>
									<th class="col-md-8">Ano</th>
									<th class="col-md-2">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${rodizioList}" var="rodizio">
									<tr>
										<td>${rodizio.ciclo}</td>
										<td>
											<input type="button" data-id="${rodizio.id}" class="btn btn-success btn-xs institutos" value="Institutos"/>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--  input type="submit" class="btn btn-primary btn-mini"
							value="Ver Institutos" /-->
						<form:errors path="*" cssClass="errorblock" element="div" />
					</form:form>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${empty rodizioList}">
		<div class="panel panel-primary">
			<div class="panel-heading">Mensagem</div>
			<!-- /.panel-heading -->
			<div class="panel-body">Não há Rodízios cadastradas.</div>
		</div>
	</c:if>
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

		$('#tabelaRodizio').dataTable({
			"language" : {
				"url" : "/js/plugins/dataTables/dataTablesPortuguese.json"
			}
		});

		$('#tabelaRodizio tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tabelaRodizio tbody').on( 'click', '.institutos', function () {
			$('#rodizio\\.id').val($(this).data('id'));
			$('#facilitadorForm').submit();
	    });
	});
</script>
