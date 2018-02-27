<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<div class="col-md-12">
	<c:if  test="${!empty pessoaList}">
		<div class="panel panel-primary">
        	<div class="panel-heading">
               Pessoas Cadastrados
           	</div>
           
           	<!-- /.panel-heading -->
         	<div class="panel-body">
           		<div class="small">
            		<c:if test="${ROLE_CONTROLE != 'ROLE_METAS_PRESIDENTE' }">
            		<table id="example" class="display table table-bordered table-striped table-hover">
				        <thead>
				            <tr>
				                <th><spring:message code="label.codigo.resumido" /></th>
				     			<th>Nome</th>
				     			<th>Email</th>
				     			<th>Cidade</th>
				     			<th>&nbsp;</th>
				            </tr>
				        </thead> 
				        <tfoot>
				            <tr>
				                <th><spring:message code="label.codigo.resumido" /></th>
				     			<th>Nome</th>
				     			<th>Email</th>
				     			<th>Cidade</th>
				     			<th>&nbsp;</th>
				            </tr>
				        </tfoot>      
				  	</table>
					</c:if>
					
					<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
      				<table class="table table-bordered table-striped table-hover" id="dataTables-example">
						<thead>
							<tr>
							    <th>Nome</th>
							    <th>Email</th>
							    <th>Cidade</th>
							    <th>&nbsp;</th>
							</tr>
						</thead>
              			<tbody>
							<c:forEach items="${pessoaList}" var="pessoa">
							<tr>
							    <td>${pessoa.nomeCompleto}</td>
								<td>${pessoa.primeiroEmail}</td>
								<td>${pessoa.endereco.cidade.nome}/${pessoa.endereco.cidade.estado.sigla}</td>
								<td>
								<form action="edit/${pessoa.id}" method="post">
									<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
									<button type="button" onclick="this.form.action = '../editBase/${pessoa.id}';submit();" class="btn btn-success btn-xs">Editar</button>&nbsp
									<button type="button" onclick="this.form.action = '../deleteBase/${pessoa.id}';submit();" class="btn btn-danger btn-xs">Excluir</button>
									</c:if>
									<c:if test="${ROLE_CONTROLE != 'ROLE_METAS_PRESIDENTE' }">
									<button type="button" onclick="this.form.action = 'edit/${pessoa.id}';submit();" class="btn btn-success btn-xs">Editar</button>&nbsp
									<button type="button" onclick="this.form.action = 'delete/${pessoa.id}';submit();" class="btn btn-danger btn-xs">Excluir</button>
									</c:if>
								</form>
								</td>
							</tr>
							</c:forEach>
              			</tbody>
					</table>
          			</c:if>
     			</div>
    		</div>
    	</div>
	</c:if>
	</div>
</div>

<div class="row">
	<div class="col-md-12">	
		<c:if  test="${empty pessoaList}">
		<div class="panel panel-primary">
		    <div class="panel-heading">
		        Mensagem
		    </div>
		    <!-- /.panel-heading -->
			<div class="panel-body">
				<spring:message code="label.pessoas.cadastrados.vazio" />
			</div>
		</div>
		</c:if>
		
		<form action="" method="get">
			<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
			<button type="button" onclick="this.form.action = '/gestao/pessoa/addBase';submit();"  class="btn btn-primary"><spring:message code="button.novo" /></button>
			</c:if>
			<c:if test="${ROLE_CONTROLE != 'ROLE_METAS_PRESIDENTE' }">
			<button type="button" onclick="this.form.action = '/gestao/pessoa/add';submit();"  class="btn btn-primary"><spring:message code="button.novo" /></button>&nbsp;
			<button type="button" onclick="this.form.action = '/gestao/pessoa/addBase';submit();"  class="btn btn-primary"><spring:message code="button.rapido" /></button>
			</c:if>
			<sec:authorize access="hasRole('ROLE_AUREO')">
			<button type="button" onclick="this.form.action = 'upload';submit();"  class="btn btn-success"><spring:message code="button.upload" /></button>
			</sec:authorize>
		</form>
	</div>
</div>
	    
	    
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

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
$(document).ready(function() {
	//exibirModalAguarde();
 	$("#example").dataTable( {
 		"order": [[ 1, "asc" ]],
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
         "sAjaxSource": "/gestao/pessoa/listPagination",
         "aoColumns": [
             { "mData": "id", "visible": false },
             { "mData": "nome" },
             { "mData": "email", "sDefaultContent" : "" },
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
             { "mData": null,
            "mRender": function(data, type, full){
                     return '<form action="edit/' + full.id + '" method="post">' + 
                 				'<button type="button" onclick="this.form.action = \'edit/' + full.id + '\';submit();" class="btn btn-success btn-xs">Editar</button>&nbsp'+
               				'<button type="button" data-id="' + full.id + '" class="btn btn-danger btn-xs">Excluir</button>' + 
           				'</form>';   // replace this with button 
                    }
             }, 
		],
     } );
    
     $('#dataTables-example').DataTable({
         "language": {
             "url": "/js/plugins/dataTables/dataTablesPortuguese.json"
         }
     });
     
     $('#dataTables-example tbody') .on('click','.delete', function() {
         BootstrapDialog.confirm({
             title: 'WARNING',
             message: 'Warning! Drop your banana?',
             type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
             closable: false, // <-- Default value is false
             draggable: false, // <-- Default value is false
             btnCancelLabel: 'Do not drop it!', // <-- Default value is 'Cancel',
             btnOKLabel: 'Drop it!', // <-- Default value is 'OK',
             btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
             callback: function(result) {
                 // result will be true if button was click, while it will be false if users close the dialog directly.
                 if(result) {
                   return true;
                 }else {
                   return false;
                 }
             }
         });	
 	 });

 });
 </script>