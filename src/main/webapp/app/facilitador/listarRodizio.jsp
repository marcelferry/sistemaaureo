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
		<div class="panel panel-default">
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
									<th class="col-md-6">Ano</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${rodizioList}" var="rodizio">
									<tr data-id="${rodizio.id}">
										<td>${rodizio.ciclo}</td>
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
		<div class="panel panel-default">
			<div class="panel-heading">Mensagem</div>
			<!-- /.panel-heading -->
			<div class="panel-body">Não há Rodízios cadastradas.</div>
		</div>
	</c:if>
</div>

<!-- Page-Level Plugin Scripts - Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	$(document).ready(function() {

		$('#tabelaRodizio').dataTable({
			"language" : {
				"url" : "/js/plugins/dataTables/dataTablesPortuguese.json"
			}
		});

		$('#tabelaRodizio tbody tr').css( 'cursor', 'pointer' );

		$('#tabelaRodizio tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tabelaRodizio tbody').on( 'dblclick', 'tr', function () {
			$('#rodizio\\.id').val($(this).data('id'));
			$('#facilitadorForm').submit();
	    });
	});
</script>
