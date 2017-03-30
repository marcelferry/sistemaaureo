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
		<div class="panel panel-default" id="panelRodizio">
			<div class="panel-heading"><spring:message code="label.rodizio" /></div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>Dois clique no ciclo para detalhar as entidades</h4>
				<div class="table-responsive">
					<form:form method="post" commandName="relatorioForm" action="${action}">
						<table class="table table-bordered table-striped table-hover"
							id="tabelaRodizio">
							<thead>
								<tr>
									<th class="col-md-6">Ciclo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${rodizioList}" var="rodizio">
									<tr data-id="${rodizio.id}" data-ciclo="${rodizio.ciclo}">
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
			<div class="panel panel-default hidden" id="panelEntidade">
	                <div class="panel-heading">
	                    Entidades
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<h4>Dois clique na entidade para detalhar os institutos preenchidos</h4>
		                <div class="table-responsive">
		                <table id="tableEntidade" class="display table table-bordered table-striped table-hover">
						        <thead>
						            <tr>
						                <th><spring:message code="label.codigo.resumido" /></th>
						     			<th>Nome</th>
						     			<th>Cidade</th>
						     			<th>Fichas</th>
						     			<th></th>
						            </tr>
						        </thead> 
						        
						        <tfoot>
						            <tr>
						                <th><spring:message code="label.codigo.resumido" /></th>
						     			<th>Nome</th>
						     			<th>Cidade</th>
						     			<th>Fichas</th>
						     			<th></th>
						            </tr>
						        </tfoot>      
						    </table>
			             </div>
		        	</div>
		        </div>
		        
		        <div class="panel panel-default hidden" id="panelInstituto">
	                <div class="panel-heading">
	                    Institutos
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
		                <table id="tableInstituto" class="display table table-bordered table-striped table-hover">
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
		        <div class="panel panel-default hidden" id="panelMetas">
	                <div class="panel-heading">
	                    Metas
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                 <div class="table-responsive">
		                <table id="tableMetas" class="display table table-bordered">
					        <thead>
					            <tr>
					     			<th>Previsao</th>
					     			<th>Entidade</th>
					     			<th>Cidade</th>
					     			<th>Instituto</th>
					     			<th>Meta</th>
					     			<th>Status</th>
					            </tr>
					        </thead> 
					        
					        <tfoot>
					            <tr>
					     			<th>Previsao</th>
					     			<th>Entidade</th>
					     			<th>Cidade</th>
					     			<th>Instituto</th>
					     			<th>Meta</th>
					     			<th>Status</th>
					            </tr>
					        </tfoot>      
					    </table>
		             </div>
		        	</div>
		        </div>
		        
	<c:if test="${empty rodizioList}">
		<div class="panel panel-default">
			<div class="panel-heading">Mensagem</div>
			<!-- /.panel-heading -->
			<div class="panel-body">Não há Rodízios cadastradas.</div>
		</div>
	</c:if>
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


<!-- Modal -->
<div class="modal fade" id="modalRelatorioAtual" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" style="width: 800px;">
       <div class="modal-content" style="width: 800px;">
        	<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"><spring:message code="titulo.preenchimento.situacao.atual"/></h3>
            </div>
			<div class="modal-body">
				<div id="presidentechart" class="row"></div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- Modal -->
<div class="modal fade" id="modalRelatorioProposto" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" style="width: 800px;">
       <div class="modal-content" style="width: 800px;">
       		<div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"><spring:message code="titulo.preenchimento.situacao.desejada"/></h3>
            </div>
			<div class="modal-body">
				<div id="presidentedesejadachart" class="row"></div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Page-Level Plugin Scripts - Tables -->
<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->
<script src="/js/plugins/flot/jquery.flot.js"></script>
<script src="/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/js/plugins/flot/jquery.flot.pie.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>

	var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	var rodizio = null;
	var ciclo = null;
	var entidade = null;

	$(document).ready(function() {


    	var options = {
                series: {
                    pie: {
                        show: true,
                        offset: {
                        	top: 30
                        }
                    }
                },
                grid: {
                    hoverable: true
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                    shifts: {
                        x: 20,
                        y: 0
                    },
                    defaultTheme: true
                }
            };

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
			$('#panelRodizio').hide();
			$('#panelEntidade').removeClass('hidden');

			ciclo   = $(this).data('id');
					
			$("#tableEntidade").dataTable( {
	    		"language": {
	                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
	            },
	            "bProcessing": true,
	            "sAjaxSource": baseUrl + '/gestao/relatorio/metascontratadasporentidade/ciclo/' + ciclo + '/entidades',
	            "aoColumns": [
	                { "mData": "id" , "visible": false },
	                { "mData": "razaoSocial" },
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
	                     } 
	                },
	                { "mData": "count", "visible": false , "sDefaultContent" : "" },
	                { "mData": null,
	  	              "mRender": function(data, type, full){
	                       return '<button type="button" class="btn btn-primary btn-xs metas" data-entidade="' + full.id + '">Metas</button>' + 
	                              '<button type="button" class="btn btn-primary btn-xs fichas" data-entidade="' + full.id + '">Fichas</button>';   // replace this with button 
	                      }
               		}, 
	            ]
	        } );

			$('#tableEntidade tbody tr').css( 'cursor', 'pointer' );

			$('#tableEntidade tbody tr').bind('selectstart', function(event) {
			    event.preventDefault();
			});

			$('#tableEntidade tbody').on( 'click', '.proposto', function() { 
				$.ajax({
			        type: "GET",
			        contentType: 'application/json; charset=utf-8',
			        dataType: 'json',
			        url: '/gestao/graphicData/statusContratadoInstitutoGraphicData/' + ciclo + '/' + $(this).data('entidade'),
			        success: function (data) {
			        	$("#presidentedesejadachart").empty();
			        	$.each( data, function( key, item ) {
			                var resultList = item.statusValor.map(function (status) {
			                    var situacao = status.situacao;
			                    var cor = '#fff';
			                    
			                    if(situacao == 'NAOPLANEJADA'){
			                        situacao = "<spring:message code="label.situacao.naoplanejada"/>";
			                        cor = "#afd8f8";
			                    } else if(situacao == 'IMPLANTADA'){
			                    	situacao = "<spring:message code="label.situacao.implantada"/>";
			                    	cor = "#4da74d";
			                    } else if(situacao == 'NAOINFORMADA'){
			                    	situacao = "<spring:message code="label.situacao.naoinformada"/>";
			                    	cor = "#cb4b4b";
			                    } else if(situacao == 'PLANEJADA'){
			                    	situacao = "<spring:message code="label.situacao.planejada"/>";
			                    	cor =  "#edc240";
			                    }
			                    var bItem = { label: situacao, data: status.quantidade, color: cor };
			            		return bItem;//JSON.stringify(bItem);
			                });
			                $("#presidentedesejadachart").append(template(item.nomeInstituto, 'des', key));
			            	var plotObj = $.plot($("#flot-pie-chart-des-" + key), resultList, options);
			        	});	
			        	$('#modalRelatorioProposto').modal({
			                   show: 'true'
			               });
			            
			        }
			    });
			});

			$('#tableEntidade tbody').on( 'click', '.metas', function() { 
				
				$('#panelEntidade').hide();
				$('#panelMetas').removeClass('hidden');
				
				var urlService;

			    urlService = baseUrl + '/gestao/planodemetas/listaContratadoEntidadeData/' + ciclo + '/' + $(this).data('entidade');
			      
			      if ( ! $.fn.DataTable.isDataTable( '#tableMetas' ) ) {
			      
				      $("#tableMetas").dataTable( {
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
				          "aoColumns": [
				              { "mData": null, 
				                  "sDefaultContent" : "",
				                  "mRender": function(data, type, full){
				                	  var date = new Date(full.previsao);
				                      var month = date.getMonth() + 1;
				                      return (month.length > 1 ? month : "0" + month) + "/" + date.getFullYear();
				                      //return full.previsao;
				                   } 
				              },
				              { "mData": "entidade" },
				              { "mData": null, 
				            	  "visible": false ,
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
				                   } 
				              },
				              { "mData": "instituto", "sDefaultContent" : "" },
				              { "mData": "meta", "sDefaultContent" : "" }, 
				              { "mData": "status", "sDefaultContent" : "", "visible": false}, 
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
		    	    RefreshTable('#tableMetas', urlService, true);
				}

				$('#tableMetas tbody tr').css( 'cursor', 'pointer' );

				$('#tableMetas tbody tr').bind('selectstart', function(event) {
				    event.preventDefault();
				});

			});
			

			$('#tableEntidade tbody').on( 'dblclick', 'tr', function () {
				$('#panelEntidade').hide();
				$('#panelInstituto').removeClass('hidden');
				
				entidade = $("#tableEntidade").DataTable().row( this ).data().id;

				$("#tableInstituto").dataTable( {
		    		"language": {
		                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
		            },
		            "bProcessing": true,
		            "sAjaxSource": baseUrl + '/gestao/relatorio/metascontratadasporentidade/ciclo/'  + ciclo + '/entidade/' + entidade,
		            "aoColumns": [
		                { "mData": "id", "visible": false },
		                { "mData": "descricao" },
		                {  "mData": null,
			  	              "mRender": function(data, type, full){
			                       return '<button type="button" class="btn btn-primary btn-xs ficha" data-instituto="' + full.id + '">Fichas</button>'+
			                              '<button type="button" class="btn btn-primary btn-xs metas" data-instituto="' + full.id + '">Metas</button>';   // replace this with button 
			                      }
		               		},
		            ]
		        } );

				$('#tableInstituto tbody').on( 'click', '.ficha', function() { 
					$('#rodizio\\.id').val('${CICLO_CONTROLE.id}');
					$('#instituto\\.id').val( $(this).data('instituto') );
					$('#entidade\\.id').val(entidade);
					//$('#facilitador\\.id').val();
					$('#evento').val('RODIZIO');
					$('#planoMetasForm').submit();
				} );

				$('#tableInstituto tbody').on( 'click', '.metas', function() { 

					$('#panelInstituto').hide();
					$('#panelMetas').removeClass('hidden');
					
					var urlService;

				    urlService = baseUrl + '/gestao/planodemetas/listaContratadoEntidadeInstitutoData/' + ciclo + '/' + entidade +  '/' + $(this).data('instituto') ;
				      
				      
				      if ( ! $.fn.DataTable.isDataTable( '#tableMetas' ) ) {
				      
					      $("#tableMetas").dataTable( {
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
					          "aoColumns": [
					              { "mData": null, 
					                  "sDefaultContent" : "",
					                  "mRender": function(data, type, full){
					                	  var date = new Date(full.previsao);
					                      var month = date.getMonth() + 1;
					                      return (month.length > 1 ? month : "0" + month) + "/" + date.getFullYear();
					                      //return full.previsao;
					                   } 
					              },
					              { "mData": "entidade" },
					              { "mData": null, 
					            	  "visible": false ,
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
					                   } 
					              },
					              { "mData": "instituto", "sDefaultContent" : "" },
					              { "mData": "meta", "sDefaultContent" : "" }, 
					              { "mData": "status", "sDefaultContent" : "" , "visible": false }, 
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
					                    	oSettings.aoData[i].nTr.className += " info";
					                    }
					                    else if ( oSettings.aoData[i]._aData.status == "A VENCER" )
					                    {
					                    	oSettings.aoData[i].nTr.className += " success";
					                    }
					              }
					          }
					      } );
			        } else {
			    	    RefreshTable('#tableMetas', urlService, true);
					}

					$('#tableMetas tbody tr').css( 'cursor', 'pointer' );

					$('#tableMetas tbody tr').bind('selectstart', function(event) {
					    event.preventDefault();
					});
				} );
			});
			

			
			
	    });
	});

	 function template(instituto, tipo, indice){
	        var html = '\
	       	<div class="col-md-4">\
	            <div class="panel panel-default" style="height: 420px;">\
	                <div class="panel-heading">\
	                    ' + instituto + '\
	                </div>\
	                <!-- /.panel-heading -->\
	                <div class="panel-body">\
	                    <div class="flot-chart">\
	                        <div class="flot-chart-content" style="height:330px;" id="flot-pie-chart-'+ tipo + '-' +indice+'"></div>\
	                    </div>\
	                </div>\
	                <!-- /.panel-body -->\
	            </div>\
	            <!-- /.panel -->\
	        </div>\
	    </div>';
	        return html;
	    }

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
	    	
	    	$modalResp.modal('loading');
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
		     $modalResp.modal('loading');
		   });
		 }

	
</script>
