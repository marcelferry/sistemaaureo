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
									<th class="col-md-8">Nome</th>
									<th class="col-md-2">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${institutoList}" var="instituto">
									<tr>
										<sec:authorize access="hasRole('ROLE_ADMIN')">
			                            <td>${instituto.id}</td>
			                            </sec:authorize>
										<td>${instituto.descricao}</td>
										<td>
											<input type="button" data-id="${instituto.id}" class="btn btn-success btn-xs facilitadores" value="Facilitadores"/>
										</td>
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
		$('#tabelaInstituto').dataTable({
			"language" : {
				"url" : "/js/plugins/dataTables/dataTablesPortuguese.json"
			}
		});

		$('#tabelaInstituto tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tabelaInstituto tbody').on( 'dblclick', '.facilitadores', function () {
			$('#instituto\\.id').val($(this).data('id'));
			$('#facilitadorForm').submit();
	    });
	});
</script>
