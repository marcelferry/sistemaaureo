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

	<div class="panel panel-default" id="panelInstituto">
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
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>

	var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	var ciclo = ${CICLO_CONTROLE.id};
	var entidade = ${INSTITUICAO_CONTROLE.id};

	$(document).ready(function() {

		$("#tableInstituto").dataTable( {
    		"language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            },
            "bProcessing": true,
            "sAjaxSource": baseUrl + '/gestao/relatorio/metascontratadasporentidade/ciclo/' + ciclo + '/entidade/' + entidade,
            "aoColumns": [
                { "mData": "id", "visible": false },
                { "mData": "descricao" },
                {  "mData": null,
	  	              "mRender": function(data, type, full){
	                       return '<button type="button" class="btn btn-primary btn-xs ficha" data-instituto="' + full.id + '">Ver Ficha</button><button type="button" class="btn btn-warn btn-xs xls" data-instituto="' + full.id + '">Excel</button>';   // replace this with button 
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
