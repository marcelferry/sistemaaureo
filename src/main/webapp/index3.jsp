<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Sistema Áureo</title>

    <!-- Core CSS - Include with every page -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="/css/sb-admin.css" rel="stylesheet">

	<style>
		input {text-transform: none;}
	</style>
</head>

<body>

    <div class="container">
        <div class="row">
        	<div class="col-md-12 col-sm-12" style="text-align: center;">
        		<span style="font-size: 20px;">
        			<spring:message code="app.name" />
        		</span>
        	</div>
            <div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4">
                <div class="panel panel-primary" style="height: 550px; margin-top: 50px;">
                    <div class="panel-heading">
                        <h3 class="panel-title">Progresso do Sistema</h3>
                    </div>
                    <div class="panel-body">
                    	  <h4>O sistema será liberado por completo ao término da validação.</h4>
                          <div class="flot-chart">
                              <div class="flot-chart-content" style="height:400px;" id="flot-pie-chart-brasil"></div>
                          </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Core Scripts - Include with every page -->
    <script src="/js/jquery-1.10.2.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="/js/plugins/validation/jquery.validate.min.js"></script>
	<script src="/js/plugins/validation/additional-methods.min.js"></script>
	<script src="/js/plugins/validation/additional-methods-br.js"></script>
	
	        <!-- Page-Level Plugin Scripts - Flot -->
    <!--[if lte IE 8]><script src="js/excanvas.min.js"></script><![endif]-->
    <script src="/js/plugins/flot/jquery.flot.js"></script>
    <script src="/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="/js/plugins/flot/jquery.flot.pie.js"></script>
	
    <!-- SB Admin Scripts - Include with every page -->
    <script src="/js/sb-admin.js"></script>
    
    <script type="text/javascript">

    var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	

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
    
		    $.ajax({
		        type: "GET",
		        contentType: 'application/json; charset=utf-8',
		        dataType: 'json',
		        url: '/gestao/graphicData/statusValidadoGeralGraphicData/2015',
		        success: function (data) {
		        	console.log(data);
		        	$.each( data, function( key, item ) {
		        		console.log(status);
		                var resultList = item.statusValor.map(function (status) {
		                    console.log(status);
		                    var situacao = status.situacao;
		                    var cor = '#fff';
		                    
		                    if(situacao == 'NAO PREENCHIDOS'){
		                        //situacao = "<spring:message code="label.situacao.naoimplantada"/>";
		                    	cor = "#cb4b4b";
		                    } else if(situacao == 'VALIDADO'){
		                    	//situacao = "<spring:message code="label.situacao.implantada"/>";
		                    	cor = "#4da74d";
		                    } else if(situacao == 'NAO VALIDADO'){
		                    	//situacao = "<spring:message code="label.situacao.implantada"/>";
		                    	cor =  "#edc240";
		                    	
		                    } else if(situacao == 'INDEFINIDO'){
		                    	//situacao = "<spring:message code="label.situacao.naoinformada"/>";
		                    	cor = "#cb4bcc";
		                    } else if(situacao == 'A VENCER'){
		                    	//situacao = "<spring:message code="label.situacao.implantadaparcial"/>";
		                    	 cor = "#afd8f8";
		                    	
		                    }
		                    //situacao = situacao
		                    var bItem = { label: situacao, data: status.quantidade, color: cor };
		                    console.log(bItem);
		            		return bItem;//JSON.stringify(bItem);
		                });
		                
		            	var plotObj = $.plot($("#flot-pie-chart-brasil"), resultList, options);
		            	
		            	$("#flot-pie-chart-brasil").bind("plotclick", function(event, pos, obj){
		            	    if (!obj){return;}
		            	    
		            	    showMetas(obj.series.label);
		
		            	});
		            });
		
		        }
		    });

      });
    </script>

</body>

</html>
