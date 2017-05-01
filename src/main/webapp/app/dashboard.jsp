<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!-- Page-Level Plugin CSS - Dashboard -->
<link href="/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="/css/plugins/timeline/timeline.css" rel="stylesheet">

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
					<div class="panel panel-primary" style="height: 610px;">
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
			<ul class="nav nav-tabs" id="tabsecretaria" role="tablist">
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
							<div class="panel panel-primary" style="height: 450px;">
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
							<div class="panel panel-primary" style="height: 450px;">
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
							<div class="panel panel-primary" style="height: 450px;">
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
							<div class="panel panel-primary" style="height: 450px;">
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
							<div class="panel panel-primary" style="height: 450px;">
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
			<ul class="nav nav-tabs" id="tabpresidente" role="tablist">
				<li class="active"><a href="#ultimas" role="tab" data-toggle="tab">Metas Vencidas</a></li>
				<li><a href="#metascontratadas" role="tab" data-toggle="tab">Metas Contratadas</a></li>
				<li><a href="#situacaoatual" role="tab" data-toggle="tab">Situação Atual</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="ultimas">
					<div class="col-md-12">
						<h4>Metas Vencidas</h4>
						<div id="metasvencidastable"></div>
						<h4>Metas a Vencer</h4>
						<div id="metasvencertable"></div>
					</div>
				</div>
				<div class="tab-pane" id="metascontratadas">
					<div class="col-md-12">
						<h4>Situação das Metas Contratadas</h4>
						<div id="institutochart"></div>
					</div>
				</div>
				<div class="tab-pane" id="situacaoatual">
					<div class="col-md-12">
						<h4>Situação Atual das Atividades</h4>
						<div id="presidentedesejadachart"></div>
					</div>
				</div>
				<div class="tab-pane hidden" id="#situacaodesejada">
					<div class="col-md-12">
						<h4><spring:message code="titulo.preenchimento.situacao.atual" /></h4>
						<div id="presidentechart"></div>
					</div>
				</div>
			</div>

            <!--  c:if test="${CICLO_CONTROLE.inicioAjustes <= now && CICLO_CONTROLE.terminoAjustes >= now }"-->
			<!-- /c:if-->
		</c:if>
	</div>
</div>

<div id="modals" class="hidden"></div>

<!-- Page-Level Plugin Scripts - Dashboard -->
<script src="/js/plugins/morris/raphael-2.1.0.min.js"></script>
<script src="/js/plugins/morris/morris.js"></script>

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

<!-- Page-Level Plugin Scripts - Flot -->
<!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->
<script src="/js/plugins/flot/jquery.flot.js"></script>
<script src="/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/js/plugins/flot/jquery.flot.pie.js"></script>

<!-- Page-Level Demo Scripts - Flot - Use for reference -->
<sec:authorize
	access="( hasRole('ROLE_METAS_CONSELHO') || hasRole('ROLE_METAS_SECRETARIA')) && !hasRole('ROLE_METAS_PRESIDENTE')">
	<!-- script src="/js/demo/pie-demo.js"></script-->
</sec:authorize>

<script type="text/javascript">
  //Flot Pie Chart
    $(function() {
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

    	
    	showMetas('metasvencertable', null, 'NO PRAZO', null, null, '${INSTITUICAO_CONTROLE.id}');
    	showMetas('metasvencidastable', null, 'ATRASADO', null, null, '${INSTITUICAO_CONTROLE.id}');    	
    	
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
                    $("#institutochart").append(templateGrafico(item.nomeInstituto, 'ins', key));
                	var plotObj = $.plot($("#flot-pie-chart-ins-" + key), resultList, options);

                	$("#flot-pie-chart-ins-" + key).bind("plotclick", function(event, pos, obj){
                	    if (!obj){return;}
                	    showMetasPopup(item.nomeInstituto, obj.series.situacao, null, item.idinstituto, '${INSTITUICAO_CONTROLE.id}' );

                	});
                });
            	if($.isEmptyObject(data)){
            		$("#institutochart").html(
            				'<div class="alert alert-danger alert-dismissable">'+ 
            					'<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>' +
  								'<h4>Não há dados para o período selecionado</h4>' + 
							'</div>');
            	}
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
                    $("#presidentechart").append(templateGrafico(item.nomeInstituto, 'atu', key));
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
                    $("#presidentedesejadachart").append(templateGrafico(item.nomeInstituto, 'des', key));
                	var plotObj = $.plot($("#flot-pie-chart-des-" + key), resultList, options);
                });

            }

            
        });

        <!--/c:if--><!-- Periodo de Metas -->
        </c:if><!-- Presidente -->

        <c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
        
        <c:if test="${CICLO_CONTROLE.dataAprovacao > now }" >
        var cicloChartBrasil = '${CICLO_CONTROLE.cicloAnterior.id}';
        </c:if>
        <c:if test="${CICLO_CONTROLE.dataAprovacao <= now }" >
        var cicloChartBrasil = '${CICLO_CONTROLE.id}';
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
                            cor = colors[7];
                        } else if(situacao == 'FEITO'){
                        	situacao = "<spring:message code="label.situacao.implantada"/>";
                        	cor = colors[6];
                        } else if(situacao == 'NO PRAZO'){
                        	situacao = "<spring:message code="label.situacao.noprazo"/>";
                        	cor = colors[5];
                        } else if(situacao == 'INDEFINIDO'){
                        	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                        	cor = colors[4];
                        } else if(situacao == 'A VENCER'){
                        	situacao = "<spring:message code="label.situacao.avencer"/>";
                        	cor = colors[1];
                        }
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                        return bItem;//JSON.stringify(bItem);
                    });
                    
                	var plotObj = $.plot($("#flot-pie-chart-brasil"), resultList, options);
                	
                	$("#flot-pie-chart-brasil").bind("plotclick", function(event, pos, obj){
                	    if (!obj){return;}
                	    <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_DIRIGENTE'}">
                	    showMetasPopup(item.nomeInstituto, obj.series.situacao , null, ${INSTITUTO_CONTROLE.id } );
                	    </c:if>
                        <c:if test="${ ROLE_CONTROLE != 'ROLE_METAS_DIRIGENTE'}">
                        showMetasPopup(item.nomeInstituto, obj.series.situacao );
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
                        var colors = ['#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2b3e50', '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d'];

                        if(situacao == 'ATRASADO'){
                            situacao = "<spring:message code="label.situacao.atrasado"/>";
                            cor = colors[7];
                        } else if(situacao == 'FEITO'){
                        	situacao = "<spring:message code="label.situacao.implantada"/>";
                        	cor = colors[6];
                        } else if(situacao == 'NO PRAZO'){
                        	situacao = "<spring:message code="label.situacao.noprazo"/>";
                        	cor = colors[5];
                        } else if(situacao == 'INDEFINIDO'){
                        	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                        	cor = colors[4];
                        } else if(situacao == 'A VENCER'){
                        	situacao = "<spring:message code="label.situacao.avencer"/>";
                        	cor = colors[1];
                        }
                        situacao = situacao + " ( " + status.quantidade + " )";
                        var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                        return bItem;//JSON.stringify(bItem);
                    });
                	var plotObj = $.plot($("#flot-pie-chart-" + item.idinstituto), resultList, options);

                	$("#flot-pie-chart-" + item.idinstituto).bind("plotclick", function(event, pos, obj){
                	    if (!obj){return;}
                	    
                	    showMetasPopup(item.nomeInstituto, obj.series.situacao, item.idinstituto);

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
                            var colors = ['#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2b3e50', '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d'];

                            if(situacao == 'ATRASADO'){
                                situacao = "<spring:message code="label.situacao.atrasado"/>";
                                cor = colors[7];
                            } else if(situacao == 'FEITO'){
                            	situacao = "<spring:message code="label.situacao.implantada"/>";
                            	cor = colors[6];
                            } else if(situacao == 'NO PRAZO'){
                            	situacao = "<spring:message code="label.situacao.noprazo"/>";
                            	cor = colors[5];
                            } else if(situacao == 'INDEFINIDO'){
                            	situacao = "<spring:message code="label.situacao.datanaoinformada"/>";
                            	cor = colors[4];
                            } else if(situacao == 'A VENCER'){
                            	situacao = "<spring:message code="label.situacao.avencer"/>";
                            	cor = colors[1];
                            }
                            situacao = situacao + " ( " + status.quantidade + " ) ";
                            var bItem = { label: situacao, data: status.quantidade, color: cor, situacao: status.situacao };
                            return bItem;//JSON.stringify(bItem);
                        });
                        $("#institutochart").append(templateGrafico(item.nomeInstituto, 'ins', key));
                    	var plotObj = $.plot($("#flot-pie-chart-ins-" + key), resultList, options);

                    	$("#flot-pie-chart-ins-" + key).bind("plotclick", function(event, pos, obj){
                    	    if (!obj){return;}
                    	    showMetasPopup(item.nomeInstituto, obj.series.situacao, null, item.idinstituto);

                    	});
                    });

                }

            
        });
        
        </c:if>

        </c:if>
  
    });

    function templateGrafico(instituto, tipo, indice){
        var html = '\
       	<div class="col-lg-4 col-md-6 col-sm-12"">\
            <div class="panel panel-primary" style="height: 450px;">\
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
    
    function templateTabela(conteudo){
    	var html = '\
    <div id="modal-content" class="content">\
		<div class="table-responsive">\
			<table id="tableEntidade'+ conteudo + '"\
				class="display table table-bordered table-striped table-hover">\
				<thead>\
					<tr>\
						<th>Previsão</th>\
						<th>Entidade</th>\
						<th>Cidade</th>\
						<th>Instituto</th>\
						<th>Meta</th>\
					</tr>\
				</thead>\
				<tfoot>\
					<tr>\
						<th>Previsão</th>\
						<th>Entidade</th>\
						<th>Cidade</th>\
						<th>Instituto</th>\
						<th>Meta</th>\
					</tr>\
				</tfoot>\
			</table>\
		</div>\
	</div>';
		return html;
    }
    
    function defineUrlMetas(status, regiao, instituto, entidade){
    	var urlService;

        if(regiao != undefined || regiao != null){
  		 	urlService = BASEURL + '/gestao/planodemetas/listaContratadoRegiaoData/${CICLO_CONTROLE.id}/' + regiao + '/' + status;
        } else if(instituto != undefined){
           	if(entidade != null){
          	 	urlService = BASEURL + '/gestao/planodemetas/listaContratadoEntidadeInstitutoData/${CICLO_CONTROLE.id}/'  + entidade + '/' + instituto + '/'  + status;
           	} else {
      	 		urlService = BASEURL + '/gestao/planodemetas/listaContratadoInstitutoData/${CICLO_CONTROLE.id}/' + instituto + '/'  + status;
       		}
       	} else if(instituto == undefined){ 
       		if(entidade != null){
          	 	urlService = BASEURL + '/gestao/planodemetas/listaContratadoEntidadeData/${CICLO_CONTROLE.id}/'  + entidade + '/'  + status;
           	} else {
      	 		urlService = BASEURL + '/gestao/planodemetas/listaContratadoGeralData/${CICLO_CONTROLE.id}/' + status;
       		}
       	} else {
      	 	urlService = BASEURL + '/gestao/planodemetas/listaContratadoGeralData/${CICLO_CONTROLE.id}/'  + status
       	} 
       
        return urlService;
    }
    
    function showMetas(conteudo, titulo, status, regiao, instituto, entidade){
    	var urlService = defineUrlMetas(status, regiao, instituto, entidade);
        
        $('#' + conteudo).html(templateTabela(conteudo));
        var tituloJanela = titulo + (status != null ? " - " + status : "");
   	 	var elementName =  '#tableEntidade'+conteudo;
    
        populateTable(elementName, urlService, instituto, function ( oSettings ) {
         	  concluirModalAguarde();
        });
    }

    function showMetasPopup(titulo, status, regiao, instituto, entidade){
      
      var urlService = defineUrlMetas(status, regiao, instituto, entidade);
     
     $('#modals').html(templateTabela(''));
     var tituloJanela = titulo + (status != null ? " - " + status : "");
	 var elementName =  '#tableEntidade';
 
      populateTable(elementName, urlService, instituto, function ( oSettings ) {
      	  concluirModalAguarde();
      	  setTimeout(() => {
      		  BootstrapDialog.show({
      			  size: BootstrapDialog.SIZE_WIDE,	        			  
	                  title: tituloJanela,
	                  message: $('#modal-content')
	              });
			  }, 500); 
        });
    }
    
    function populateTable(elementName, urlService, instituto, onCompleteCallback) {
    	
    	if ( ! $.fn.DataTable.isDataTable( elementName ) ) {
      	  exibirModalAguarde();
  	      $(elementName).DataTable( {
  	  		"language": {
  	              "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
  	          },
  	          "bProcessing": false,
  	          "bSort": false,
  	          "sAjaxSource": urlService,
  	          "fnServerData": function ( sSource, aoData, fnCallback ) {
  		            $.ajax( {
  		                "dataType": 'json',
  		                "type": "GET",
  		                "url": sSource,
  		                "data": aoData,
  		                "globals": false,
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
  	          "fnInitComplete": onCompleteCallback 
  	      } );
          } else {
      	    RefreshTable(elementName, urlService, true, onCompleteCallback);
  		}

  		$(elementName + ' tbody tr').css( 'cursor', 'pointer' );

  		$(elementName + ' tbody tr').bind('selectstart', function(event) {
  		    event.preventDefault();
  		});

  		$(elementName + ' tbody').on( 'dblclick', 'tr', function () {
  			 var meta = $(elementName).DataTable().row( this ).data().idMeta;
  			 
  		      $('body').modalmanager('loading');
  		     
  		      setTimeout(function(){
  		         $modal.load('/gestao/metas/preview/' + meta , '', function(){
  		          $modal.modal();
  		        });
  		      }, 1000);
  		});
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

    function RefreshTable(tableId, urlData, clean, onCompleteCallback)
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
	     
	     onCompleteCallback(); 
	   });
	 }

    </script>
