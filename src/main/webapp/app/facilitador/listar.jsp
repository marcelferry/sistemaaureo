<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<style>
</style>
      <div class="row" id="target">
      		<form:form method="get" action="add" commandName="facilitadorForm" class="form-horizontal" role="form">
      			<form:hidden path="rodizio.id" />
				<form:hidden path="instituto.id" />
      			<div class="panel panel-primary">
	                <div class="panel-heading">
	                    Dados Gerais 
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                	<div class="form-group small">
							<label class="col-sm-2 col-xs-3 control-label">Ciclo:</label>
							<div class="col-sm-8 col-xs-9 control-label" style="text-align: left;">${facilitadorForm.rodizio.ciclo}</div>
						</div>
						<div class="form-group small">
							<label class="col-sm-2 col-xs-3 control-label">Instituto/Comissão:</label>
							<div class="col-sm-8 col-xs-9 control-label" style="text-align: left;">${facilitadorForm.instituto.descricao}</div>
						</div>
	                </div>
	            </div>
	            <div class="panel panel-primary">
	                <div class="panel-heading">
	                    Facilitadores
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
		                <div class="table-responsive">
			                <table class="table table-bordered table-striped table-hover small" id="tablefacilitador">
			                    <thead>
		                    <tr>
		                        <th class="col-md-5">Facilitador</th>
		                        <th class="col-md-5">Email</th>
		                        <c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
		                        <th class="col-md-2"></th>
		                        </c:if>
		                    </tr>
		                    </thead>
		                    <tbody id="ComTratativas">
		                    <c:forEach items="${facilitadorForm.facilitadores}" var="facilitador" varStatus="status">
								<tr>
									<td>${facilitador.trabalhador.nome}</td>
									<td>${facilitador.trabalhador.primeiroEmail}</td>
		                        	<c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
									 <td>
		                            	<form action="edit/${facilitador.id}" method="post">
			                            	<div class="btn-group">
			                            		<button type="button" onclick="this.form.action = 'delete/${facilitador.id}';submit();" class="btn btn-danger btn-xs" title="Excluir"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
			                            		<button type="button" class="btn btn-primary btn-xs email" data-id="${facilitador.trabalhador.id}" title="Envio do convite para acesso." alt="Envio do convite para acesso."><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></button>
			                            		<button type="button" class="btn btn-default btn-xs senha" data-id="${facilitador.trabalhador.id}" title="Reset da Senha para 12345" alt="Reset da Senha para 12345"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></button>
		                            		</div>
		                            	</form>
		                            </td>
		                        	</c:if>
								</tr>
							</c:forEach>
		                    </tbody>
		                </table>
		            </div>
		            <c:if test="${ROLE_CONTROLE == 'ROLE_METAS_CONSELHO' || ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
		            <div class="col-md-12">
		            	<div class="col-md-2">
				            <input type="submit" class="btn btn-primary btn-mini" value="Novo Facilitador"/>
			         	</div>
		         	</div>
		         	</c:if>
		        </div>
		        </div>
		        </form:form>
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
   	$('.email').click(function(evt) {
   	  	// Prevent the button from triggering a form submission.
   	  	evt.preventDefault();
   	  	exibirModalAguarde();
	    $.ajax({
	        type: "GET",
	        contentType: 'application/json; charset=utf-8',
	        dataType: 'json',
	        context: $(this),
	        url: '/gestao/email/facilitador/sendConvite/' + $(this).data('id') ,
	        error: function(jqXHR, textStatus, errorThrown) 
			{
	        	concluirModalAguarde();
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
				concluirModalAguarde();
				alert("Email enviado com sucesso!");
	        }
		});
    });

    $('.senha').click(function(evt) {
		exibirModalAguarde();
		$.ajax({
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			context : $(this),
			url : '/gestao/userprofile/redefinirSenha/' + $(this).data('id') + '/false',
			error : function(jqXHR, textStatus, errorThrown) {
				concluirModalAguarde();
				var exceptionVO = jQuery.parseJSON(jqXHR.responseText);

				$('#errorModal')
					.find('.modal-header h3').html( jqXHR.status + ' error').end()
					.find('.modal-body p>strong').html(exceptionVO.clazz).end()
					.find('.modal-body p>em').html(exceptionVO.method).end()
					.find('.modal-body p>span').html(exceptionVO.message).end()
					.modal('show');

			},
			success : function(response) {
				var valid = response === true || response === "true";
				concluirModalAguarde();
				alert("Senha definida com sucesso!");
			}
		});
	});
});
</script>
	   