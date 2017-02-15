<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div class="row">
		<div class="panel panel-default">
			<div class="panel-heading">Cidades Cadastrados</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-bordered table-striped table-hover"
						id="example">
						<thead>
							<tr>
								<th>Cod.</th>
								<th>Cidade</th>
								<th>Estado</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						
						<tfoot>
							<tr>
								<th>Cod.</th>
								<th>Cidade</th>
								<th>Estado</th>
								<th>&nbsp;</th>
							</tr>
						</tfoot>
	
					</table>
				</div>
			</div>
		</div>
	
		<form action="add" method="get">
			<input type="submit" class="btn btn-primary" value="Novo" />
		</form>
	</div>
	
	
	<!-- Page-Level Plugin Scripts - Tables -->
    <script src="/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
   	//Plug-in to fetch page data 
   	jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
   	{
   		return {
   			"iStart":         oSettings._iDisplayStart,
   			"iEnd":           oSettings.fnDisplayEnd(),
   			"iLength":        oSettings._iDisplayLength,
   			"iTotal":         oSettings.fnRecordsTotal(),
   			"iFilteredTotal": oSettings.fnRecordsDisplay(),
   			"iPage":          oSettings._iDisplayLength === -1 ?
   				0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
   			"iTotalPages":    oSettings._iDisplayLength === -1 ?
   				0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
   		};
   	};
       
       $(document).ready(function() {

       	var table = $("#example").dataTable( {
       		"language": {
                   "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
               },
               "bProcessing": true,
               "bServerSide": true,
               "sort": "position",
               //bStateSave variable you can use to save state on client cookies: set value "true" 
               "bStateSave": false,
               //Default: Page display length
               "iDisplayLength": 10,
               //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
               "iDisplayStart": 0,
               "fnDrawCallback": function () {
                   //Get page numer on client. Please note: number start from 0 So
                   //for the first page you will see 0 second page 1 third page 2...
                   //Un-comment below alert to see page number
               	//alert("Current page number: "+this.fnPagingInfo().iPage);    
               },         
               "sAjaxSource": "/gestao/cidade/listPagination",
               "aoColumns": [
                   { "mData": "id" },
                   { "mData": "nome" },
                   { "mData": "estado.sigla", "sDefaultContent" : "" },
                   { "mData": null}, 
               ],
               "columnDefs": [ 
                   {   
                     "aTargets":[3],  // this your column of action
                     "mData": null, 
                     "mRender": function(data, type, full){
                      		return '<form action="edit/' + full.id + '" method="post">' + 
   		               				'<input type="button" onclick="this.form.action = \'edit/' + full.id + '\';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp'+
   		             				'<input type="button" onclick="this.form.action = \'delete/' + full.id + '\';submit();" class="btn btn-danger btn-xs" value="Excluir"/> ' + 
   		                		'</form>';   // replace this with button 
                     }
                   }
               ],
           } );
    });
    </script>