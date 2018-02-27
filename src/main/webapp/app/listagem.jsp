<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<link href="/css/plugins/bootstrap-modal/bootstrap-modal-bs3patch.css" rel="stylesheet" />
<link href="/css/plugins/bootstrap-modal/bootstrap-modal.css" rel="stylesheet" />

<style>
<!--
.panel-heading a:after {
	font-family: 'Glyphicons Halflings';
	content: "\e114";
	float: right;
	color: grey;
}

.panel-heading a.collapsed:after {
	content: "\e080";
}
-->
</style>
<div class="row">
	<div class="col-md-12">
		<!-- Visão Presidente -->
		<c:if test="${not empty INSTITUICAO_CONTROLE || not empty INSTITUTO_CONTROLE}">
			<div class="table-responsive">
				<table id="tableEntidade"
					class="display table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<th>Ciclo</th>
							<th>Previsao</th>
							<th>Entidade</th>
							<th>Cidade</th>
							<th>Instituto</th>
							<th>Meta</th>
							<th>Status</th>
							<th>Ação</th>
						</tr>
					</thead>

					<tfoot>
						<tr>
							<th>Ciclo</th>
							<th>Previsao</th>
							<th>Entidade</th>
							<th>Cidade</th>
							<th>Instituto</th>
							<th>Meta</th>
							<th>Status</th>
							<th>Ação</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</c:if>
	</div>
	<!-- /.row -->
	
	<form id="metasForm" name="metasForm" method="get" action="/gestao/metas/editar"></form>

	<div id="ajax-modal" class="modal fade" tabindex="-1" data-width="760"
		style="display: none;"></div>

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

	<script src="/js/plugins/bootstrap-modal/bootstrap-modalmanager.js"></script>
	<script src="/js/plugins/bootstrap-modal/bootstrap-modal.js"></script>


	<script type="text/javascript">

    $(function() {
    	<c:if test="${not empty INSTITUICAO_CONTROLE || not empty INSTITUTO_CONTROLE}">
    		<c:if test="${not empty INSTITUICAO_CONTROLE}">
        		showMetas("Contratado", null, null, null, ${INSTITUICAO_CONTROLE.id});
        	</c:if>
    		<c:if test="${not empty INSTITUTO_CONTROLE}">		
        		showMetas("Contratado", null, null, ${INSTITUTO_CONTROLE.id}, null);
        	</c:if>
        </c:if>
    });

    function showMetas(titulo, status, regiao, instituto, entidade){
      // create the backdrop and wait for next modal to be triggered
      //$('body').modalmanager('loading');

      var urlService;

      $("#table-title").html(titulo + (status != null ? " - " + status : "") );
      
	 if(regiao != undefined || regiao != null){
		 urlService = BASEURL + '/gestao/planodemetas/listaContratadoRegiaoData/${CICLO_CONTROLE.id}/' + regiao + '/' + status;
     } else if(instituto != undefined){
         if(entidade != null){
        	 urlService = BASEURL + '/gestao/planodemetas/listaContratadoEntidadeInstitutoData/${CICLO_CONTROLE.id}/'  + entidade + '/' + instituto + '/'  + status;
         } else {
        	 if(status != null){
    	 		urlService = BASEURL + '/gestao/planodemetas/listaContratadoInstitutoData/${CICLO_CONTROLE.id}/' + instituto + '/'  + status;
        	 } else {
    	 		urlService = BASEURL + '/gestao/planodemetas/listaContratadoInstitutoData/${CICLO_CONTROLE.id}/' + instituto;
        	 }
     	}
     } else if(entidade != null){
         if(status != null){
    	 	urlService = BASEURL + '/gestao/planodemetas/listaContratadoEntidadeData/${CICLO_CONTROLE.id}/'  + entidade + '/'  + status;
         } else {
        	urlService = BASEURL + '/gestao/planodemetas/listaContratadoEntidadeData/${CICLO_CONTROLE.id}/'  + entidade;
         }
     } else {
    	 urlService = BASEURL + '/gestao/planodemetas/listaContratadoGeralData/${CICLO_CONTROLE.id}/'  + status
     } 
 
      if ( ! $.fn.DataTable.isDataTable( '#tableEntidade' ) ) {
      
	      $("#tableEntidade").dataTable( {
	  		"language": {
	              "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
	          },
	          "bProcessing": true,
	          "bSort": false,
	          "sAjaxSource": urlService,
	          "fnServerData": function ( sSource, aoData, fnCallback ) {
		            $.ajax( {
		                "dataType": 'json',
		                "type": "GET",
		                "url": sSource,
		                "data": aoData,
		                "success": fnCallback,
		                "timeout": 30000,   // optional if you want to handle timeouts (which you should)
		                "error": handleAjaxError // this sets up jQuery to give me errors
		            } );
		        },
		        //"dom": 'Bfrtip',
		        "buttons": [
		            'copy', 'excel', 'pdf', 'print'
		        ],
	          "aoColumns": [
	        	  { "mData": "ciclo", "visible" : ${not empty INSTITUICAO_CONTROLE || not empty INSTITUTO_CONTROLE} },
	              { "mData": "previsao", 
	                  "sDefaultContent" : "",
	                  "mRender": function(data, type, full){
	                	  if(data != undefined){
		                	  var date = new Date(data);
		                	  return moment(date).format('MM/YYYY');
	                	  } else {
							  return "";
			              }
	                   } 
	              },
	              { "mData": "entidade", "visible" : ${empty INSTITUICAO_CONTROLE} },
	              { "mData": null, 
	                  "sDefaultContent" : "",
	                  "mRender": function(data, type, full){
	                      var retorno = "";
	                      if(full.cidade != undefined){
	                          retorno += full.cidade;
	                          if(full.uf != undefined){
	                              retorno += '/' + full.uf;
	                          }
	                      }
	                      return retorno;
	                   },
	                   "visible" : ${empty INSTITUICAO_CONTROLE} 
	              },
	              { "mData": "instituto", "sDefaultContent" : "", "visible" : ${empty INSTITUTO_CONTROLE} },
	              { "mData": "meta", "sDefaultContent" : "" }, 
	              { "mData": "status", "sDefaultContent" : "",
		              "mRender": function(data){
			          		if(data == 'ATRASADO'){
				          		return 'Atrasado'; 
				          	}    
			          		if(data == 'A VENCER'){
				          		return 'A Vencer'; 
				          	}    
			          		if(data == 'NO PRAZO'){
				          		return 'No prazo'; 
				          	}    
			          }
	              }, 
	              { "mData": null,
	  	              "mRender": function(data, type, full){
		  	              if(full.acao != 'PRECONTRATAR'){
	                       	return '<button type="button" class="btn btn-primary btn-xs editar" data-meta="' + full.idMeta + '">Editar</button>';   // replace this with button 
	                      } else if(full.acao != 'CONTRATAR'){
	                    	return '<button type="button" class="btn btn-primary btn-xs preview" data-meta="' + full.idMeta + '">Detalhes</button>';   // replace this with button 
		                  }
	  	              }
               		}
	          ],
	          fnInitComplete: function ( oSettings )
	          {
	              for ( var i=0, iLen=oSettings.aoData.length ; i<iLen ; i++ )
	              {
	            	  //oSettings.aoData[i].nTr.className += " "+oSettings.aoData[i]._aData[0];
	                    if ( oSettings.aoData[i]._aData.status == "ATRASADO" )
	                    {
	                    	oSettings.aoData[i].nTr.className += " danger";
	                    }
	                    else if ( oSettings.aoData[i]._aData.status == "NO PRAZO" )
	                    {
	                    	oSettings.aoData[i].nTr.className += " warning";
	                    }
	                    else if ( oSettings.aoData[i]._aData.status == "A VENCER" )
	                    {
	                    	oSettings.aoData[i].nTr.className += " info";
	                    }
	              }
	          }
		          
	      } );
        } else {
    	    RefreshTable('#tableEntidade', urlService, true);
		}

		$('#tableEntidade tbody tr').css( 'cursor', 'pointer' );

		$('#tableEntidade tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tableEntidade tbody').on( 'click', '.editar', function() { 
			
			var meta = $(this).data('meta');

			document.getElementById('metasForm').action = '/gestao/metas/edit/${CICLO_CONTROLE.id}/' + meta;
			document.getElementById('metasForm').submit();
		});

		$('#tableEntidade tbody').on( 'click', '.preview', function() { 
			
			var meta = $(this).data('meta');

			$('body').modalmanager('loading');
		     
		      setTimeout(function(){
		         $modal.load('/gestao/metas/preview/${CICLO_CONTROLE.id}/' + meta , '', function(){
		          $modal.modal();
		        });
		      }, 1000);
		});

		$('#tableEntidade tbody').on( 'dblclick', 'tr', function () {
			 // create the backdrop and wait for next modal to be triggered
			 var meta = $("#tableEntidade").DataTable().row( this ).data().idMeta;
			 
		      $('body').modalmanager('loading');
		     
		      setTimeout(function(){
		         $modal.load('/gestao/metas/preview/${CICLO_CONTROLE.id}/' + meta , '', function(){
		          $modal.modal();
		        });
		      }, 1000);
		});
    }

    var $modal = $('#ajax-modal');

    function handleAjaxError( xhr, textStatus, error ) {
	    if ( textStatus === 'timeout' ) {
	        alert( 'Servidor esta demorando para retornar os dados.' );
	    }
	    else {
	        alert( 'Um erro ocorreu no servidor. Tente novamente em alguns minutos.' );
	    }
	    myDataTable.fnProcessingIndicator( false );
	}

    function RefreshTable(tableId, urlData, clean)
	 {

    	if(clean) $(tableId).dataTable().fnClearTable(this);
    	
	   $.getJSON(urlData, null, function( json )
	   {
	     table = $(tableId).dataTable();
	     oSettings = table.fnSettings();

	     table.fnClearTable(this);

	     for (var i=0; i<json.aaData.length; i++)
	     {
	       table.oApi._fnAddData(oSettings, json.aaData[i]);
	     }

	     oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
	     table.fnDraw();
	   });
	 }

    </script>
