<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row" id="target">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">Entidades Cadastrados</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="small">
					<table id="example" class="display table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th class="col-md-1">Cód</th>
								<th class="col-md-4">Nome</th>
								<th class="col-md-4">Presidente</th>
								<th class="col-md-2">Cidade</th>
								<th class="col-md-1">&nbsp;</th>
							</tr>
						</thead>
	
						<tfoot>
							<tr>
								<th>Cód</th>
								<th>Nome</th>
								<th>Presidente</th>
								<th>Cidade</th>
								<th>&nbsp;</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-12 pull-right">
		<form action="" method="get">
			<button type="button" onclick="this.form.action = 'add';submit();" class="btn btn-primary">Novo</button>
			<button type="button" onclick="this.form.action = 'addBase';submit();" class="btn btn-primary">Rápido</button>
			<button type="button" onclick="this.form.action = '/gestao/email/entidade/envio';submit();" class="btn btn-primary">Enviar email</button>
			<button type="button" onclick="this.form.action = '/gestao/email/entidade/sendLembreteTodos/${CICLO_CONTROLE.id}';submit();" class="btn btn-primary">Enviar email</button>
		</form>
	</div>
	
	<form id="formActions" action="" method="post">
	</form>
</div>


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


<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
//Plug-in to fetch page data 
jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
	return {
		"iStart" : oSettings._iDisplayStart,
		"iEnd" : oSettings.fnDisplayEnd(),
		"iLength" : oSettings._iDisplayLength,
		"iTotal" : oSettings.fnRecordsTotal(),
		"iFilteredTotal" : oSettings.fnRecordsDisplay(),
		"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
		"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
				.ceil(oSettings.fnRecordsDisplay()
						/ oSettings._iDisplayLength)
	};
};

$(document).ready(function() {
	exibirModalAguarde();
	var table = $("#example").DataTable({
		"language" : {
			"url" : "/js/plugins/dataTables/dataTablesPortuguese.json"
		},
		"bProcessing" : false,
		"bServerSide" : true,
		"searchDelay": 500,
		"sort" : "position",
		//bStateSave variable you can use to save state on client cookies: set value "true" 
		"bStateSave" : false,
		//Default: Page display length
		"iDisplayLength" : 10,
		//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
		"iDisplayStart" : 0,
		"fnInitComplete" : concluirModalAguarde,
		"fnDrawCallback" : function() {
			//Get page numer on client. Please note: number start from 0 So
			//for the first page you will see 0 second page 1 third page 2...
			//Un-comment below alert to see page number
			//alert("Current page number: "+this.fnPagingInfo().iPage);    
		},
		"sAjaxSource" : "/gestao/entidade/listPagination",
		"aoColumns" : [
				{ "mData" : "id" },
				{ "mData" : "razaoSocial" },
				{ "mData" : "presidente.nome", "sDefaultContent" : "" },
				{ "mData" : null, "sDefaultContent" : "",
					"mRender" : function(data, type, full) {
						var retorno = "";
						if (full.cidade != undefined) {
							retorno += full.cidade;
							if (full.uf != undefined) {
								retorno += '/'
										+ full.uf;
							}
						}
						return retorno;
					} 
				}, 
				{ "mData" : null }, ],
		"columnDefs" : [ {
			"aTargets" : [ 4 ], // this your column of action
			"mData" : null,
			"mRender" : function(data, type, full) {
				if (full.presidente != undefined && full.presidente.id != undefined) {
					return '<div class="btn-group pull-right">'
							+ '<button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">'
							+ '    <i class="glyphicon glyphicon-fire"></i> Ações <span class="caret"></span>'
							+ '</button>'
				    		+ '<ul class="dropdown-menu" role="menu">'
							+ '<li><a type="button" class="btn-sm edit" data-id="' + full.id + '" title="Editar este registro"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Editar</a></li>'
							+ '<li><a type="button" class="btn-sm delete" data-id="' + full.id  + '" title="Excluir este registro"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Excluir</a></li>'
							+ '<li><a type="button" class="btn-sm email" data-pessoa="' + full.presidente.id + '" data-id="' + full.id + '" title="Enviar convite de acesso para o presidente." alt="Enviar convite de acesso para o presidente."><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Enviar convite</a></li>'
							+ '<li><a type="button" class="btn-sm lembrete" data-pessoa="' + full.presidente.id + '" data-id="' + full.id + '" title="Enviar Lembrete" alt="Enviar Lembrete"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Enviar lembrete</a></li>'
							+ '<li><a type="button" class="btn-sm senha" data-pessoa="' + full.presidente.id + '" title="Redefinir senha do presidente para 123456" alt="Redefinir senha do presidente para 12345"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span> Redefinir senha</a></li>'
							+ '</ul>'
						+ '</div>'; // replace this with button 
				} else {
					return '<div class="btn-group pull-right">'
							+ '<button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">'
							+ '    <i class="glyphicon glyphicon-fire"></i> Ações <span class="caret"></span>'
							+ '</button>'
				    		+ '<ul class="dropdown-menu" role="menu">'
				    		+ '<li><a type="button" class="btn-sm edit" data-id="' + full.id + '" title="Editar este registro"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Editar</a></li>'
							+ '<li><a type="button" class="btn-sm delete" data-id="' + full.id  + '" title="Excluir este registro"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Excluir</a></li>'
							+ '</ul>'
						+ '</div>'; // replace this with button 
				}
			}
		} ],
	});
	
	$('#example tbody').on('click','.edit', function() {
		$('#formActions').attr( 'action', 'edit/' + $(this).data('id') );
		$('#formActions').submit();
	});
	
	$('#example tbody').on('click','.delete', function() {
		$('#formActions').attr( 'action', 'delete/' + $(this).data('id') );
		$('#formActions').submit();
	});

	$('#example tbody') .on('click','.email', function() {
		exibirModalAguarde();
		$.ajax({
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			context : $(this),
			url : '/gestao/email/entidade/sendConvite/' + $(this).data('id') + '/' + escape($(this).data('pessoa')),
			error : function(jqXHR, textStatus, errorThrown) {
				concluirModalAguarde();
				var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
				$('#errorModal')
					.find('.modal-header h3').html(jqXHR.status + ' error').end()
					.find('.modal-body p>strong').html(exceptionVO.clazz).end()
					.find('.modal-body p>em').html(exceptionVO.method).end()
					.find('.modal-body p>span').html(exceptionVO.message).end()
					.modal('show');

			},
			success : function(response) {
				var valid = response === true || response === "true";
				concluirModalAguarde();
				alert("Email enviado com sucesso!");
			}
		});
	});
	
	$('#example tbody') .on('click','.lembrete', function() {
		exibirModalAguarde();
		$.ajax({
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			context : $(this),
			url : '/gestao/email/entidade/sendLembrete/${ CICLO_CONTROLE.id }' + '/' + $(this).data('id') + '/' + escape($(this).data('pessoa')),
			error : function(jqXHR, textStatus, errorThrown) {
				concluirModalAguarde();
				var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
				$('#errorModal')
					.find('.modal-header h3').html(jqXHR.status + ' error').end()
					.find('.modal-body p>strong').html(exceptionVO.clazz).end()
					.find('.modal-body p>em').html(exceptionVO.method).end()
					.find('.modal-body p>span').html(exceptionVO.message).end()
					.modal('show');

			},
			success : function(response) {
				var valid = response === true || response === "true";
				concluirModalAguarde();
				alert("Email enviado com sucesso!");
			}
		});
	});

	$('#example tbody').on('click','.senha', function() {
		// Prevent the button from triggering a form submission.
		exibirModalAguarde();
		$.ajax({
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			context : $(this),
			url : '/gestao/userprofile/redefinirSenha/' + $(this).data('pessoa') + '/false',
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