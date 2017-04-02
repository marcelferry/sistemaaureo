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
		<div class="panel panel-primary" id="panelRodizio">
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
			<div class="panel panel-primary hidden" id="panelEntidade">
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
						     			<th><spring:message code="label.graficos" /></th>
						            </tr>
						        </thead> 
						        
						        <tfoot>
						            <tr>
						                <th><spring:message code="label.codigo.resumido" /></th>
						     			<th>Nome</th>
						     			<th>Cidade</th>
						     			<th>Fichas</th>
						     			<th><spring:message code="label.graficos" /></th>
						            </tr>
						        </tfoot>      
						    </table>
			             </div>
		        	</div>
		        </div>
		        
		        <div class="panel panel-primary hidden" id="panelInstituto">
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
		        
	<c:if test="${empty rodizioList}">
		<div class="panel panel-primary">
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

			rodizio = $(this).data('id');
			ciclo   = $(this).data('ciclo');
					
			$("#tableEntidade").dataTable( {
	    		"language": {
	                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
	            },
	            "bProcessing": true,
	            "sAjaxSource": baseUrl + '/gestao/relatorio/${action}/' + $(this).data('ciclo'),
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
	                { "mData": "count", "sDefaultContent" : "" },
	                { "mData": null,
	  	              "mRender": function(data, type, full){
	                       return '<button type="button" class="btn btn-primary btn-xs atual" data-entidade="' + full.id + '">Atual</button>&nbsp' + '<button type="button" class="btn btn-primary btn-xs proposto" data-entidade="' + full.id + '">Proposto</button>';   // replace this with button 
	                      }
               		}, 
	            ]
	        } );

			$('#tableEntidade tbody tr').css( 'cursor', 'pointer' );

			$('#tableEntidade tbody tr').bind('selectstart', function(event) {
			    event.preventDefault();
			});

			$('#tableEntidade tbody').on( 'click', '.atual', function() {
		  	  	 $.ajax({
			        type: "GET",
			        contentType: 'application/json; charset=utf-8',
			        dataType: 'json',
			        url: '/gestao/graphicData/statusAtualInstitutoGraphicData/' + ciclo + '/' + $(this).data('entidade'),
			        success: function (data) {
			        	console.log(data);
			        	$("#presidentechart").empty();
			        	$.each( data, function( key, item ) {
			        		console.log(status);
			                var resultList = item.statusValor.map(function (status) {
			                    console.log(status);
			                    var situacao = status.situacao;
			                    var cor = '#fff';
			                    
			                    if(situacao == 'NAOIMPLANTADA'){
			                        situacao = "Não Implantada";
			                        cor = "#afd8f8";
			                    } else if(situacao == 'IMPLANTADA'){
			                    	situacao = "Implantada";
			                    	cor = "#4da74d";
			                    } else if(situacao == 'NAOINFORMADA'){
			                    	situacao = "Não Preenchido";
			                    	cor = "#cb4b4b";
			                    } else if(situacao == 'IMPLPARCIAL'){
			                    	situacao = "Divergente da Proposta";
			                    	cor =  "#edc240";
			                    }
			                    var bItem = { label: situacao, data: status.quantidade, color: cor };
			                    console.log(bItem);
			            		return bItem;//JSON.stringify(bItem);
			                });
			                console.log(key);
			                $("#presidentechart").append(template(item.nomeInstituto, 'atu' , key));
			            	console.log(resultList);
			            	var plotObj = $.plot($("#flot-pie-chart-atu-" + key), resultList, options);
			            });
			        	$('#modalRelatorioAtual').modal({
			                   show: 'true'
			               });
			            
			        }
			    });
			});

			$('#tableEntidade tbody').on( 'click', '.proposto', function() { 
				$.ajax({
			        type: "GET",
			        contentType: 'application/json; charset=utf-8',
			        dataType: 'json',
			        url: '/gestao/graphicData/statusPropostoInstitutoGraphicData/' + ciclo + '/' + $(this).data('entidade'),
			        success: function (data) {
			        	console.log(data);
			        	$("#presidentedesejadachart").empty();
			        	$.each( data, function( key, item ) {
			        		console.log(status);
			                var resultList = item.statusValor.map(function (status) {
			                    console.log(status);
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
			                    console.log(bItem);
			            		return bItem;//JSON.stringify(bItem);
			                });
			                console.log(key);
			                $("#presidentedesejadachart").append(template(item.nomeInstituto, 'des', key));
			            	console.log(resultList);
			            	var plotObj = $.plot($("#flot-pie-chart-des-" + key), resultList, options);
			        	});	
			        	$('#modalRelatorioProposto').modal({
			                   show: 'true'
			               });
			            
			        }
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
		            "sAjaxSource": baseUrl + '/gestao/relatorio/${action}/' + ciclo + '/' + entidade,
		            "aoColumns": [
		                { "mData": "id", "visible": false },
		                { "mData": "descricao" },
		                {  "mData": null,
			  	              "mRender": function(data, type, full){
			                       return '<button type="button" class="btn btn-primary btn-xs ficha" data-instituto="' + full.id + '">Ficha</button>';   // replace this with button 
			                      }
		               		},
		            ]
		        } );

				$('#tableInstituto tbody').on( 'click', '.ficha', function() { 
					$('#rodizio\\.id').val(rodizio);
					$('#instituto\\.id').val( $(this).data('instituto') );
					$('#entidade\\.id').val(entidade);
					//$('#facilitador\\.id').val();
					$('#evento').val('PRERODIZIO');
					$('#planoMetasForm').submit();
				} );
			});

			
			
	    });
	});

	 function template(instituto, tipo, indice){
	        var html = '\
	       	<div class="col-md-4">\
	            <div class="panel panel-primary" style="height: 420px;">\
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

	
</script>
