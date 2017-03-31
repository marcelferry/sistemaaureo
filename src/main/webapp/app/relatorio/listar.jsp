<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

      <div class="row"  id="target">
      		<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Entidades
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
		                <table id="example" class="display table table-bordered table-striped table-hover">
						        <thead>
						            <tr>
						                <th>Cód</th>
						     			<th>Nome</th>
						     			<th>Cidade</th>
						     			<th>Fichas</th>
						            </tr>
						        </thead> 
						        
						        <tfoot>
						            <tr>
						                <th>Cód</th>
						     			<th>Nome</th>
						     			<th>Presidente</th>
						     			<th>Cidade</th>
						     			<th>Fichas</th>
						            </tr>
						        </tfoot>      
						    </table>
			             </div>
		        	</div>
		        </div>
            
            <form action="" method="get">
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
            "sAjaxSource": "/gestao/entidade/listPagination",
            "aoColumns": [
                { "mData": "id" },
                { "mData": "razaoSocial" },
                { "mData": "presidente.nome", "sDefaultContent" : "" },
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
                { "mData": null}, //, "sDefaultContent" : "<input type=\"button\" class=\"btn btn-success btn-xs\" value=\"Editar\"/>&nbsp<input type=\"button\" class=\"btn btn-danger btn-xs\" value=\"Excluir\"/>" },
            ],
            "columnDefs": [ 
                {   
                  "aTargets":[4],  // this your column of action
                  "mData": null, 
                  "mRender": function(data, type, full){
                      if(full.presidente != undefined && full.presidente.id != undefined){
                	  return '<form action="edit/' + full.id + '" method="post">' + 
		         				'<input type="button" onclick="this.form.action = \'edit/' + full.id + '\';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp'+
			       				'<input type="button" onclick="this.form.action = \'delete/' + full.id + '\';submit();" class="btn btn-danger btn-xs" value="Excluir"/>&nbsp' + 
			       				'<input type="button" class="btn btn-primary btn-xs email" data-id="' + full.presidente.id + '" data-entidade="' + full.id + '" value="Email"/>'
			          		'</form>';   // replace this with button 
                      } else {
                   		return '<form action="edit/' + full.id + '" method="post">' + 
		               				'<input type="button" onclick="this.form.action = \'edit/' + full.id + '\';submit();" class="btn btn-success btn-xs" value="Editar"/>&nbsp'+
		             				'<input type="button" onclick="this.form.action = \'delete/' + full.id + '\';submit();" class="btn btn-danger btn-xs" value="Excluir"/>' + 
		                		'</form>';   // replace this with button 
                      }
                  }
                }
            ],
        } );

    	$('#example tbody').on( 'click', 'button', function () {
        	console.log($(this).parents('tr'));
        	console.log($(table));
        	console.log($(table).row( $(this).parents('tr') ));
        	console.log(table.row( $(this).parents('tr') ).data());
            var data = table.row( $(this).parents('tr') ).data();
            alert( data[0] +"'s salary is: "+ data[ 1 ] );
        } );
        
        $('#dataTables-example').dataTable({
            "language": {
                "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
            }
        });

        $('#example tbody').on( 'click', '.email', function() {
        	  // Prevent the button from triggering a form submission.
        	  //evt.preventDefault();
        	  $('#target').loadingOverlay();
    		    $.ajax({
    		        type: "GET",
    		        contentType: 'application/json; charset=utf-8',
    		        dataType: 'json',
    		        context: $(this),
    		        url: '/gestao/entidade/sendConvite/' + $(this).data('id') + '/' + escape($(this).data('entidade')),
    		        error: function(jqXHR, textStatus, errorThrown) 
    				{
    		        	$('#target').loadingOverlay('remove');
    					var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
    				   
    					$('#errorModal')
    					.find('.modal-header h3').html(jqXHR.status+' error').end()
    					.find('.modal-body p>strong').html(exceptionVO.clazz).end()
    					.find('.modal-body p>em').html(exceptionVO.method).end()
    					.find('.modal-body p>span').html(exceptionVO.message).end()
    					.modal('show');
    				   
    				},
    		        success: function ( response ) {
    					var valid = response === true || response === "true";
    					$('#target').loadingOverlay('remove');
    					alert("Email enviado com sucesso!");
    		        }
    		    });
        
      });
          
        
    });
    </script>