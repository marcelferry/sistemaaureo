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

	<div class="panel panel-primary" id="panelInstituto">
		<div class="panel-heading">Institutos</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<div class="table-responsive">
				<table id="tableInstituto"
					class="display table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<th>Cód</th>
							<th>Nome</th>
							<th>&nbsp;</th>
						</tr>
					</thead>

					<tfoot>
						<tr>
							<th>Cód</th>
							<th>Nome</th>
							<th>&nbsp;</th>
						</tr>
					</tfoot>
				</table>
				<a class="btn btn-primary col-md-3" href="javascript:void(0);" onclick="$(this).next('form').submit();"><spring:message code="menu.relatorios.fichasembranco" /> HTML/PDF</a>
            	<form method="post" action="/gestao/relatorio/imprimeTodasFicha" id="planoMetasForm" name="planoMetasForm" class="form-horizontal" target="_blank">
					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				     <input type="hidden" id="fase" name="fase" value="1"/>
				     <input type="hidden" id="rodizio.id" name="rodizio.id" value="${CICLO_CONTROLE.id}"/>
					 <input type="hidden" id="instituto.id" name="instituto.id"/>
					 <input type="hidden" id="entidade.id" name="entidade.id" value="${INSTITUICAO_CONTROLE.id}"/>
					 <input type="hidden" id="facilitador.id" name="facilitador.id"/>
					 <input type="hidden" id="evento" name="evento"/>
				</form>
				<sec:authorize access="hasRole('ROLE_METAS_SECRETARIA')">
				<a class="btn btn-success col-md-3" href="javascript:void(0);" onclick="$(this).next('form').submit();"><spring:message code="menu.relatorios.fichasembranco" /> XLS</a>
            	<form method="post" action="/gestao/relatorio/imprimeTodasFicha/XLS" id="planoMetasForm" name="planoMetasForm" class="form-horizontal" target="_blank">
					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				     <input type="hidden" id="fase" name="fase" value="1"/>
				     <input type="hidden" id="rodizio.id" name="rodizio.id" value="${CICLO_CONTROLE.id}"/>
					 <input type="hidden" id="instituto.id" name="instituto.id"/>
					 <input type="hidden" id="entidade.id" name="entidade.id" value="${INSTITUICAO_CONTROLE.id}"/>
					 <input type="hidden" id="facilitador.id" name="facilitador.id"/>
					 <input type="hidden" id="evento" name="evento"/>
				</form>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>

<form method="post" action="/gestao/relatorio/imprimeFichaRodizio" id="planoMetasForm" name="planoMetasForm" class="form-horizontal" target="_blank">
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
     <input type="hidden" id="fase" name="fase" value="1"/>
     <input type="hidden" id="rodizio.id" name="rodizio.id"/>
	 <input type="hidden" id="instituto.id" name="instituto.id"/>
	 <input type="hidden" id="entidade.id" name="entidade.id"/>
	 <input type="hidden" id="facilitador.id" name="facilitador.id"/>
	 <input type="hidden" id="evento" name="evento"/>
</form>


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
<!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>

	var ciclo = ${CICLO_CONTROLE.id};
	var entidade = ${INSTITUICAO_CONTROLE.id};

	$(document).ready(function() {

		$("#tableInstituto").dataTable( {
    		"language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            },
            "bProcessing": true,
            "sAjaxSource": BASEURL + '/gestao/relatorio/metascontratadasporentidade/ciclo/' + ciclo + '/entidade/' + entidade,
            "aoColumns": [
                { "mData": "id", "visible": false },
                { "mData": "descricao" },
                {  "mData": null,
	  	              "mRender": function(data, type, full){
	                       return '<button type="button" class="btn btn-primary btn-xs ficha" data-instituto="' + full.id + '">Ver Ficha</button>'; 
	                              //'<button type="button" class="btn btn-warn btn-xs xls" data-instituto="' + full.id + '">Excel</button>';   // replace this with button 
	                      }
               		},
            ]
        } );

		$('#tableInstituto tbody').on( 'click', '.ficha', function() { 
			$('#rodizio\\.id').val(ciclo);
			$('#instituto\\.id').val( $(this).data('instituto') );
			$('#entidade\\.id').val(entidade);
			//$('#facilitador\\.id').val();
			$('#evento').val('PRERODIZIO');
			$('#planoMetasForm').attr("action", "/gestao/relatorio/imprimeFichaRodizio");
			$('#planoMetasForm').submit();
		} );
		
		$('#tableInstituto tbody').on( 'click', '.xls', function() { 
			$('#rodizio\\.id').val(ciclo);
			$('#instituto\\.id').val( $(this).data('instituto') );
			$('#entidade\\.id').val(entidade);
			//$('#facilitador\\.id').val();
			$('#evento').val('PRERODIZIO');
			$('#planoMetasForm').attr("action","/gestao/relatorio/imprimeFichaRodizio/XLS");
			$('#planoMetasForm').submit();
		} );
	});
</script>
