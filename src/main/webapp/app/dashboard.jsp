<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!-- Page-Level Plugin CSS - Dashboard -->
<link href="/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="/css/plugins/timeline/timeline.css" rel="stylesheet">

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
		<jsp:useBean id="now" class="java.util.Date"/>
		<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-default" style="height: 610px;">
						<div class="panel-heading">
						<c:if test="${CICLO_CONTROLE.dataAprovacao > now }" >
				        	Brasil ${CICLO_CONTROLE.cicloAnterior.ciclo}
				        </c:if>
				        <c:if test="${CICLO_CONTROLE.dataAprovacao <= now }" >
				        	Brasil ${CICLO_CONTROLE.ciclo}
				        </c:if>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="flot-chart">
								<div class="flot-chart-content" style="height: 550px;"
									id="flot-pie-chart-brasil"></div>
							</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>
			<ul class="nav nav-tabs" id="tabpessoa" role="tablist">
				<li class="active"><a href="#regioes" role="tab"
					data-toggle="tab"><spring:message code="label.regioes" /></a></li>
				<c:if test="${ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE' }">
				<li><a href="#institutos" role="tab" data-toggle="tab"><spring:message
							code="label.institutos" /></a></li>
				</c:if>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="regioes">
					<br />
					<!-- Inicio Graficos Regioes -->
					<div class="row ">
						<div class="col-md-4">
							<div class="panel panel-default" style="height: 450px;">
								<div class="panel-heading">Norte</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="flot-chart">
										<div class="flot-chart-content" style="height: 380px;"
											id="flot-pie-chart-10"></div>
									</div>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>
						<div class="col-md-4">
							<div class="panel panel-default" style="height: 450px;">
								<div class="panel-heading">Nordeste</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="flot-chart">
										<div class="flot-chart-content" style="height: 380px;"
											id="flot-pie-chart-20"></div>
									</div>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>

						<div class="col-md-4">
							<div class="panel panel-default" style="height: 450px;">
								<div class="panel-heading">Centro Oeste</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="flot-chart">
										<div class="flot-chart-content" style="height: 380px;"
											id="flot-pie-chart-50"></div>
									</div>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>
					</div>
					<div class="row ">

						<div class="col-md-4  col-md-offset-2">
							<div class="panel panel-default" style="height: 450px;">
								<div class="panel-heading">Sudeste</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="flot-chart">
										<div class="flot-chart-content" style="height: 380px;"
											id="flot-pie-chart-30"></div>
									</div>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>

						<div class="col-md-4">
							<div class="panel panel-default" style="height: 450px;">
								<div class="panel-heading">Sul</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="flot-chart">
										<div class="flot-chart-content" style="height: 380px;"
											id="flot-pie-chart-40"></div>
									</div>
								</div>
								<!-- /.panel-body -->
							</div>
							<!-- /.panel -->
						</div>
					</div>
					<!-- Termino Graficos Regioes -->
				</div>
				<c:if test="${ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE' }">
				<div class="tab-pane" id="institutos">
					<br />
					<!-- Início Graficos Institutos -->
					<div id="institutochart" class="row"></div>
					<!-- Termino Graficos Institutos -->
				</div>
				</c:if>
			</div>

		</c:if>

		<!-- Visão Presidente -->
		<c:if test="${not empty INSTITUICAO_CONTROLE}">
			<!-- Início Graficos Institutos -->
			<div id="institutochart" class="row"></div>

            <!--  c:if test="${CICLO_CONTROLE.inicioAjustes <= now && CICLO_CONTROLE.terminoAjustes >= now }"-->
				<!-- Termino Graficos Institutos -->
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-target="#collapseOne"
									href="#collapseOne" class=""> <spring:message
										code="titulo.preenchimento.situacao.atual" />
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse">
							<!-- /.panel-heading -->
							<div class="panel-body">
								<div id="presidentechart" class="row"></div>
								<!-- /.panel-body -->
							</div>
						</div>
						<!-- /.panel -->
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-target="#collapseTwo"
									href="#collapseTwo" class=""> <spring:message
										code="titulo.preenchimento.situacao.desejada" />
								</a>
							</h4>
						</div>
						<!-- /.panel-heading -->
						<div id="collapseTwo" class="panel-collapse">
							<div class="panel-body">
								<div id="presidentedesejadachart" class="row"></div>
								<!-- /.panel-body -->
							</div>
						</div>
					</div>
					<!-- /.panel -->
				</div>
			<!-- /c:if-->
		</c:if>
	</div>
</div>
	<!-- /.row -->
	<div class="ajax hidden">
		<button class="demo btn btn-primary btn-lg" data-toggle="modal">View
			Demo</button>
	</div>
	<div class="responsive hidden">
		<button class="demo btn btn-primary btn-lg" data-toggle="modal">Responsivo</button>
	</div>

	<div id="responsive" class="modal container fade" tabindex="-1"
		style="display: none;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 id="table-title" class="modal-title">Pendências</h4>
		</div>
		<div class="modal-body">
			<div class="content">
				<div class="table-responsive">
					<table id="tableEntidade"
						class="display table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>Previsao</th>
								<th>Entidade</th>
								<th>Cidade</th>
								<th>Instituto</th>
								<th>Meta</th>
							</tr>
						</thead>

						<tfoot>
							<tr>
								<th>Previsao</th>
								<th>Entidade</th>
								<th>Cidade</th>
								<th>Instituto</th>
								<th>Meta</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-default">Fechar</button>
		</div>
	</div>

	<div id="ajax-modal" class="modal fade" tabindex="-1" data-width="760"
		style="display: none;"></div>

	<!-- Page-Level Plugin Scripts - Dashboard -->
	<script src="/js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="/js/plugins/morris/morris.js"></script>

	<!-- Page-Level Plugin Scripts - Tables -->
	<script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>

	<!-- Page-Level Plugin Scripts - Flot -->
	<!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->
	<script src="/js/plugins/flot/jquery.flot.js"></script>
	<script src="/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="/js/plugins/flot/jquery.flot.pie.js"></script>

	<script src="/js/plugins/bootstrap-modal/bootstrap-modalmanager.js"></script>
	<script src="/js/plugins/bootstrap-modal/bootstrap-modal.js"></script>

	<!-- Page-Level Demo Scripts - Flot - Use for reference -->
	<sec:authorize
		access="( hasRole('ROLE_METAS_CONSELHO') || hasRole('ROLE_METAS_SECRETARIA')) && !hasRole('ROLE_METAS_PRESIDENTE')">
		<!-- script src="/js/demo/pie-demo.js"></script-->
	</sec:authorize>

	<script type="text/javascript">

    var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	

  //Flot Pie Chart
    $(function() {

    	$.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner = 
    	      '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
    	        '<div class="progress progress-striped active">' +
    	          '<div class="progress-bar" style="width: 100%;"></div>' +
    	        '</div>' +
    	      '</div>';

    	$.fn.modalmanager.defaults.resize = true;

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
                    hoverable: true,
                    clickable: true
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

    	<c:if test="${not empty INSTITUICAO_CONTROLE}">

    	$.ajax({
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: '/gestao/graphicData/statusContratadoPorInstitutoEntidadeGraphicData/${CICLO_CONTROLE.id}',
            beforeSend: function(){
            	$("#institutochart").block({ message: 'Carregando...'});
            },
			complete: function(){
            	$("#institutochart").unblock();            	
            },
            success: function (data) {
            	$.each( data, function( key, item ) {
            		var resultList = item.statusValor.map(function (status) {
                        var situacao = status.situacao;
                        var cor = '#fff';
                        
                        if(situacao == 'ATRASADO'){
                            situacao = "<spring:message code="label.situacao.atrasado"/>";
                        	cor = "#cb4b4b";
                        } else if(situacao == 'FEITO'){
                        	situacao = "<spring:message code="label.situacao.implantada"/>";
                        	cor = "#4da74d";
                        } else if(situacao == 'NO PRAZO'){
                        	situacao = "<spring:message code="label.situacao.noprazo"/>";
                        	cor =  "#edc240";
                        } else if(situacao == 'INDEFINIDO'){
                        	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                        	cor = "#cb4bcc";
                        } else if(situacao == 'A VENCER'){
                        	situacao = "<spring:message code="label.situacao.avencer"/>";
                        	 cor = "#afd8f8";
                        	
                        }
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                        return bItem;//JSON.stringify(bItem);
                    });
                    $("#institutochart").append(template(item.nomeInstituto, 'ins', key));
                	var plotObj = $.plot($("#flot-pie-chart-ins-" + key), resultList, options);

                	$("#flot-pie-chart-ins-" + key).bind("plotclick", function(event, pos, obj){
                	    if (!obj){return;}
                	    showMetas(item.nomeInstituto, obj.series.situacao, null, item.idinstituto, ${INSTITUICAO_CONTROLE.id} );

                	});
                });
            }        
	    });


        <!-- c:if test="${CICLO_CONTROLE.inicioAjustes <= now && CICLO_CONTROLE.terminoAjustes >= now }" -->
        $.ajax({
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: '/gestao/graphicData/statusAtualInstitutoGraphicData',
            beforeSend: function(){
            	$("#presidentechart").block({ message: 'Carregando...'});
            },
			complete: function(){
            	$("#presidentechart").unblock();            	
            },
            success: function (data) {
            	$.each( data, function( key, item ) {
            		var resultList = item.statusValor.map(function (status) {
                        var situacao = status.situacao;
                        var cor = '#fff';
                        
                        if(situacao == 'NAOIMPLANTADA'){
                            situacao = "<spring:message code="label.situacao.naoimplantada"/>";
                            cor = "#afd8f8";
                        } else if(situacao == 'IMPLANTADA'){
                        	situacao = "<spring:message code="label.situacao.implantada"/>";
                        	cor = "#4da74d";
                        } else if(situacao == 'NAOINFORMADA'){
                        	situacao = "<spring:message code="label.situacao.naoinformada"/>";
                        	cor = "#cb4b4b";
                        } else if(situacao == 'IMPLPARCIAL'){
                        	situacao = "<spring:message code="label.situacao.implantadaparcial"/>";
                        	cor =  "#edc240";
                        }
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor };
                        return bItem;//JSON.stringify(bItem);
                    });
                    $("#presidentechart").append(template(item.nomeInstituto, 'atu', key));
                	var plotObj = $.plot($("#flot-pie-chart-atu-" + key), resultList, options);
                });


            }

            
        });

        
        $.ajax({
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: '/gestao/graphicData/statusPropostoInstitutoGraphicData',
            beforeSend: function(){
            	$("#presidentedesejadachart").block({ message: 'Carregando...'});
            },
			complete: function(){
            	$("#presidentedesejadachart").unblock();            	
            },
            success: function (data) {
            	$.each( data, function( key, item ) {
            		var resultList = item.statusValor.map(function (status) {
                        var situacao = status.situacao;
                        var cor = '#fff';
                        var colors = ['#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2b3e50', '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d'];
                        
                        if(situacao == 'NAOPLANEJADA'){
	                        situacao = "<spring:message code="label.situacao.naoplanejada"/>";
	                        cor = colors[0];
	                    } else if(situacao == 'IMPLANTADA'){
	                    	situacao = "<spring:message code="label.situacao.implantada"/>";
	                    	cor = colors[1];;
	                    } else if(situacao == 'NAOINFORMADA'){
	                    	situacao = "<spring:message code="label.situacao.naoinformada"/>";
	                    	cor = colors[2];;
	                    } else if(situacao == 'PLANEJADA'){
	                    	situacao = "<spring:message code="label.situacao.planejada"/>";
	                    	cor = colors[3];;
	                    } else if(situacao == 'CANCELADA'){
	                    	situacao = "<spring:message code="label.situacao.planejadacancelada"/>";
	                    	cor = colors[4];;
	                    } else if(situacao == 'NAOIMPLANTADA'){
	                    	situacao = "<spring:message code="label.situacao.naoimplantada"/>";
	                    	cor = colors[5];;
	                    } else if(situacao == 'REPLANEJADA'){
	                    	situacao = "<spring:message code="label.situacao.replanejada"/>";
	                    	cor = colors[6];;
	                    } else if(situacao == 'IMPLPARCIAL'){
                        	situacao = "<spring:message code="label.situacao.implantadaparcial"/>";
                        	cor = colors[7];;
                        }
                        
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor };
                        return bItem;//JSON.stringify(bItem);
                    });
                    $("#presidentedesejadachart").append(template(item.nomeInstituto, 'des', key));
                	var plotObj = $.plot($("#flot-pie-chart-des-" + key), resultList, options);
                });

            }

            
        });

        <!--/c:if--><!-- Periodo de Metas -->
        </c:if><!-- Presidente -->

        <c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
        
        <c:if test="${CICLO_CONTROLE.dataAprovacao > now }" >
        var cicloChartBrasil = ${CICLO_CONTROLE.cicloAnterior.id};
        </c:if>
        <c:if test="${CICLO_CONTROLE.dataAprovacao <= now }" >
        var cicloChartBrasil = ${CICLO_CONTROLE.id};
        </c:if>

        $.ajax({
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
            url: '/gestao/graphicData/statusContratadoGeralGraphicData/' + cicloChartBrasil + '/${INSTITUTO_CONTROLE.id}',
            </c:if>
            <c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE'}">
            url: '/gestao/graphicData/statusContratadoGeralGraphicData/' + cicloChartBrasil,
            </c:if>
            beforeSend: function(){
            	$("#flot-pie-chart-brasil").block({ message: 'Carregando...'});
            },
			complete: function(){
            	$("#flot-pie-chart-brasil").unblock();            	
            },
            success: function (data) {
            	$.each( data, function( key, item ) {
            		var resultList = item.statusValor.map(function (status) {
                        var situacao = status.situacao;
                        var cor = '#fff';
                        var colors = ['#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2b3e50', '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d'];

                        if(situacao == 'ATRASADO'){
                            situacao = "<spring:message code="label.situacao.atrasado"/>";
                            cor = colors[0];
                        } else if(situacao == 'FEITO'){
                        	situacao = "<spring:message code="label.situacao.implantada"/>";
                        	cor = colors[1];
                        } else if(situacao == 'NO PRAZO'){
                        	situacao = "<spring:message code="label.situacao.noprazo"/>";
                        	cor = colors[2];
                        } else if(situacao == 'INDEFINIDO'){
                        	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                        	cor = colors[3];
                        } else if(situacao == 'A VENCER'){
                        	situacao = "<spring:message code="label.situacao.avencer"/>";
                        	cor = colors[4];
                        	
                        }
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                        return bItem;//JSON.stringify(bItem);
                    });
                    
                	var plotObj = $.plot($("#flot-pie-chart-brasil"), resultList, options);
                	
                	$("#flot-pie-chart-brasil").bind("plotclick", function(event, pos, obj){
                	    if (!obj){return;}
                	    <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
                	    showMetas(item.nomeInstituto, obj.series.situacao , null, ${INSTITUTO_CONTROLE.id } );
                	    </c:if>
                        <c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE'}">
                        showMetas(item.nomeInstituto, obj.series.situacao );
                        </c:if>
                	});
                });

            }
        });
        
            $.ajax({
                type: "GET",
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
                url: '/gestao/graphicData/statusContratadoPorRegiaoGraphicData/' + cicloChartBrasil + '/${INSTITUTO_CONTROLE.id}',
                </c:if>
                <c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE'}">
                url: '/gestao/graphicData/statusContratadoPorRegiaoGraphicData/' + cicloChartBrasil,
                </c:if>
                success: function (data) {
                	$.each( data, function( key, item ) {
                		var resultList = item.statusValor.map(function (status) {
                            var situacao = status.situacao;
                            var cor = '#fff';
                            
                            if(situacao == 'ATRASADO'){
                                situacao = "<spring:message code="label.situacao.atrasado"/>";
                            	cor = "#cb4b4b";
                            } else if(situacao == 'FEITO'){
                            	situacao = "<spring:message code="label.situacao.implantada"/>";
                            	cor = "#4da74d";
                            } else if(situacao == 'NO PRAZO'){
                            	situacao = "<spring:message code="label.situacao.noprazo"/>";
                            	cor =  "#edc240";
                            	
                            } else if(situacao == 'INDEFINIDO'){
                            	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                            	cor = "#cb4bcc";
                            } else if(situacao == 'A VENCER'){
                            	situacao = "<spring:message code="label.situacao.avencer"/>";
                            	 cor = "#afd8f8";
                            	
                            }
                            situacao = situacao + " ( " + status.quantidade + " )";
                            var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                            return bItem;//JSON.stringify(bItem);
                        });
                    	var plotObj = $.plot($("#flot-pie-chart-" + item.idinstituto), resultList, options);

                    	$("#flot-pie-chart-" + item.idinstituto).bind("plotclick", function(event, pos, obj){
                    	    if (!obj){return;}
                    	    
                    	    showMetas(item.nomeInstituto, obj.series.situacao, item.idinstituto);

                    	});
                    	
                    });

                }

            
        });
        
        <c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE'}">
            $.ajax({
                type: "GET",
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                url: '/gestao/graphicData/statusContratadoPorInstitutoGraphicData/' + cicloChartBrasil,
                success: function (data) {
                	$.each( data, function( key, item ) {
                		var resultList = item.statusValor.map(function (status) {
                            var situacao = status.situacao;
                            var cor = '#fff';
                            
                            if(situacao == 'ATRASADO'){
                                situacao = "<spring:message code="label.situacao.atrasado"/>";
                            	cor = "#cb4b4b";
                            } else if(situacao == 'FEITO'){
                            	situacao = "<spring:message code="label.situacao.implantada"/>";
                            	cor = "#4da74d";
                            } else if(situacao == 'NO PRAZO'){
                            	situacao = "<spring:message code="label.situacao.noprazo"/>";
                            	cor =  "#edc240";
                            } else if(situacao == 'INDEFINIDO'){
                            	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                            	cor = "#cb4bcc";
                            } else if(situacao == 'A VENCER'){
                            	situacao = "<spring:message code="label.situacao.avencer"/>";
                            	 cor = "#afd8f8";
                            	
                            }
                            situacao = situacao + " ( " + status.quantidade + " ) ";
                            var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                            return bItem;//JSON.stringify(bItem);
                        });
                        $("#institutochart").append(template(item.nomeInstituto, 'ins', key));
                    	var plotObj = $.plot($("#flot-pie-chart-ins-" + key), resultList, options);

                    	$("#flot-pie-chart-ins-" + key).bind("plotclick", function(event, pos, obj){
                    	    if (!obj){return;}
                    	    showMetas(item.nomeInstituto, obj.series.situacao, null, item.idinstituto);

                    	});
                    });

                }

            
        });
        
        </c:if>

        </c:if>
  
    });

    function template(instituto, tipo, indice){
        var html = '\
       	<div class="col-md-4">\
            <div class="panel panel-default" style="height: 450px;">\
                <div class="panel-heading">\
                    ' + instituto + '\
                </div>\
                <!-- /.panel-heading -->\
                <div class="panel-body">\
                    <div class="flot-chart">\
                        <div class="flot-chart-content" style="height:380px;" id="flot-pie-chart-'+tipo+'-'+indice+'"></div>\
                    </div>\
                </div>\
                <!-- /.panel-body -->\
            </div>\
            <!-- /.panel -->\
        </div>\
    </div>';
        return html;
    }

    var $modalResp = $('#responsive');

    function showMetas(titulo, status, regiao, instituto, entidade){
      // create the backdrop and wait for next modal to be triggered
      $('body').modalmanager('loading');

      var urlService;

      $("#table-title").html(titulo + (status != null ? " - " + status : "") );
      
	 if(regiao != undefined || regiao != null){
		 urlService = baseUrl + '/gestao/planodemetas/listaContratadoRegiaoData/${CICLO_CONTROLE.id}/' + regiao + '/' + status;
     } else if(instituto != undefined){
         if(entidade != null){
        	 urlService = baseUrl + '/gestao/planodemetas/listaContratadoEntidadeInstitutoData/${CICLO_CONTROLE.id}/'  + entidade + '/' + instituto + '/'  + status;
         } else {
    	 	urlService = baseUrl + '/gestao/planodemetas/listaContratadoInstitutoData/${CICLO_CONTROLE.id}/' + instituto + '/'  + status;
     	}
     } else {
    	 urlService = baseUrl + '/gestao/planodemetas/listaContratadoGeralData/${CICLO_CONTROLE.id}/'  + status
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
		        "fnDrawCallback": function ( oSettings ) 
				{
		    	  for ( var i=0, iLen=oSettings.aoData.length ; i<iLen ; i++ )
		          {
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
			  },
	          "aoColumns": [
	              { "mData": "previsao", 
	                  "sDefaultContent" : "",
	                  "mRender": function(data, type, full){
	                	  if(data != undefined){
		                	  var date = new Date(data);
		                	  return moment(date).format('MM/YYYY');
	                	  } else {
							  return "";
			              }
	                      //return full.previsao;
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
	              { "mData": "instituto", "sDefaultContent" : "", "visible" : ( (instituto == undefined) && ${empty INSTITUTO_CONTROLE} ) },
	              { "mData": "meta", "sDefaultContent" : "" }, 
	          ],
	          "fnInitComplete": function ( oSettings )
	          {
	              ///
	          }
		          
	      } );
        } else {
    	    RefreshTable('#tableEntidade', urlService, true);
		}

		$('#tableEntidade tbody tr').css( 'cursor', 'pointer' );

		$('#tableEntidade tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
		});

		$('#tableEntidade tbody').on( 'dblclick', 'tr', function () {
			 var meta = $("#tableEntidade").DataTable().row( this ).data().idMeta;
			 
		      $('body').modalmanager('loading');
		     
		      setTimeout(function(){
		         $modal.load('/gestao/metas/preview/' + meta , '', function(){
		          $modal.modal();
		        });
		      }, 1000);
		});

		$('#responsive').modal();
		
    }

    var $modal = $('#ajax-modal');
    
    $('.ajax .demo').on('click', function(){
      // create the backdrop and wait for next modal to be triggered
      $('body').modalmanager('loading');
     
      setTimeout(function(){
         $modal.load('/modal_ajax_test.html', '', function(){
          $modal.modal();
        });
      }, 1000);
    });
     
    $modal.on('click', '.update', function(){
      $modal.modal('loading');
      setTimeout(function(){
        $modal
          .modal('loading')
          .find('.modal-body')
            .prepend('<div class="alert alert-info fade in">' +
              'Updated!<button type="button" class="close" data-dismiss="alert">&times;</button>' +
            '</div>');
      }, 1000);
    });

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
