<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
	<c:if test="${!empty institutoList}">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<spring:message code="label.institutos.cadastrados" />
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
					<form:form method="post" commandName="facilitadorForm"
						action="listar">
						<form:hidden path="fase" />
						<form:hidden path="rodizio.id" />
						<form:hidden path="instituto.id" />
						<table class="table table-bordered table-striped table-hover"
							id="tabelaInstituto">
							<thead>
								<tr>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
			                        <th class="col-md-1"><spring:message code="label.codigo.resumido" /></th>
			                        </sec:authorize>
									<th class="col-md-9">Nome</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${institutoList}" var="instituto">
									<tr data-id="${instituto.id}">
										<sec:authorize access="hasRole('ROLE_ADMIN')">
			                            <td>${instituto.id}</td>
			                            </sec:authorize>
										<td>${instituto.descricao}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<form:errors path="*" cssClass="error-message-list" element="div" />
					</form:form>
				</div>
			</div>
		</div>
	</c:if>
</div>

<!-- Page-Level Plugin Scripts - Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	$(document).ready(function() {
		$('#tabelaInstituto').dataTable({
			"language" : {
				"url" : "/js/plugins/dataTables/dataTablesPortuguese.json"
			}
		});

		$('#tabelaInstituto tbody tr').css( 'cursor', 'pointer' );
		
		$('#tabelaInstituto tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tabelaInstituto tbody').on( 'dblclick', 'tr', function () {
			$('#instituto\\.id').val($(this).data('id'));
			$('#facilitadorForm').submit();
	    });
	});
</script>
