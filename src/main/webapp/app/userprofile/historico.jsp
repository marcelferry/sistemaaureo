<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- Page-Level Plugin CSS - Dashboard -->
<link href="/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="/css/plugins/timeline/timeline.css" rel="stylesheet">

<link href="/css/plugins/bootstrap-modal/bootstrap-modal-bs3patch.css"
	rel="stylesheet" />
<link href="/css/plugins/bootstrap-modal/bootstrap-modal.css"
	rel="stylesheet" />

	<div class="row">
		<div class="col-md-12">
			<div class="table-responsive">
				<table id="tableEntidade"
					class="display table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<th>Data</th>
							<th>Nome</th>
							<th>Email</th>
							<th>IP</th>
							<th>Status</th>
						</tr>
					</thead>
	
					<tfoot>
						<tr>
							<th>Data</th>
							<th>Nome</th>
							<th>Email</th>
							<th>IP</th>
							<th>Status</th>
						</tr>
					</tfoot>
				</table>
			</div>
	</div>
	<!-- /.row -->

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

	<!-- script src="https://cdn.datatables.net/buttons/1.1.0/js/dataTables.buttons.min.js"></script-->

	<script src="/js/plugins/bootstrap-modal/bootstrap-modalmanager.js"></script>
	<script src="/js/plugins/bootstrap-modal/bootstrap-modal.js"></script>


	<script type="text/javascript">

    $(function() {
        carregarHistorico();
    });

    function carregarHistorico(){
      var urlService;
     
      urlService = BASEURL + '/gestao/userprofile/listaHistoricoLogin';
     
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
	              { "mData": null, 
	                  "sDefaultContent" : "",
	                  "mRender": function(data, type, full){
	                	  var date = new Date(full.loggedIn);
	                      var month = "" + (date.getMonth() + 1);
	                      var day = "" + date.getDate();
	                      return date;//(day.length > 1 ? day : "0" + day) + "/" + (month.length > 1 ? month : "0" + month) + "/" + date.getFullYear();
	                   } 
	              },
	              { "mData": "user.pessoa.nomeCompleto", "sDefaultContent" : "" },
	              { "mData": "user.username", "sDefaultContent" : "" },
	              { "mData": "userIp", "sDefaultContent" : "" },
	              { "mData": "status", "sDefaultContent" : "" },
	          ]   
	      } );
        } else {
    	    RefreshTable('#tableEntidade', urlService, true);
		}

		$('#tableEntidade tbody tr').css( 'cursor', 'pointer' );

		$('#tableEntidade tbody tr').bind('selectstart', function(event) {
		    event.preventDefault();
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

	<!-- Page-Level Demo Scripts - Dashboard - Use for reference -->
	<script src="/js/demo/dashboard-demo.js"></script>