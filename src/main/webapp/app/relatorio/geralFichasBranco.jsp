<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
	<c:if test="${!empty institutoList}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<spring:message code="label.institutos.cadastrados" />
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
						<table class="table table-bordered table-striped table-hover"
							id="tabelaInstituto">
							<thead>
								<tr>
									<sec:authorize access="hasRole('ROLE_ADMIN')">
			                        <th class="col-md-1"><spring:message code="label.codigo.resumido" /></th>
			                        </sec:authorize>
									<th class="col-md-9">Nome</th>
									<th class="col-md-2">&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${institutoList}" var="instituto">
									<tr data-id="${instituto.id}">
										<sec:authorize access="hasRole('ROLE_ADMIN')">
			                            <td>${instituto.id}</td>
			                            </sec:authorize>
										<td>${instituto.descricao}</td>
										<td>
											<a href="javascript:void(0);" onclick="$(this).next('form').submit();">Fichas</a>
			                                <form id="fichas${instituto.id}" method="post" action="/gestao/planodemetas/imprimePreFichaBranco" target="_blank" >
											     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											     <input type="hidden" name="fase" value="1"/>
											     <input type="hidden" name="rodizio.id" value="${CICLO_CONTROLE.id}"/>
												 <input type="hidden" name="instituto.id" value="${instituto.id}"/>
												 <input type="hidden" name="entidade.id"/>
												 <input type="hidden" name="evento" value="PRERODIZIO"/>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
					<a class="btn btn-primary" href="javascript:void(0);" onclick="$(this).next('form').submit();"><spring:message code="menu.relatorios.fichasembranco" /></a>
	            	<form method="post" action="/gestao/planodemetas/imprimeTodasFichaBrancoXLS" id="planoMetasForm" name="planoMetasForm" class="form-horizontal" target="_blank">
						<input type="hidden" id="rodizio.id" name="rodizio.id" value="${CICLO_CONTROLE.id}"/>
					</form>
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
