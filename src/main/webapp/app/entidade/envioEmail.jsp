<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

      <div class="row"  id="target">
 
            <form id="formEnvio" method="post" action="enviar" class="form-horizontal">
            	<h4 style="color:#ff0000;">* Se não selecionar uma entidade, o email será enviado a todas as entidades cadastradas.</h4>
            	<div class="form-group hidden">
				  <label class="col-sm-2 col-xs-3 control-label"> Enviar para: </label>
				  <div class="col-sm-2 col-xs-3">
				    <label class="radio">
				      <input type="radio" name="para" id="send_to_all" value="all" checked>
				      Todos
				    </label>
				  </div>
				  <div class="col-sm-2 col-xs-3">
				    <label class="radio">
				      <input type="radio" name="para" id="send_to_one" value="one">
				      Uma entidade
				    </label>
				  </div>
				</div>
		        <div class="form-group hidden">
		            <label class="col-sm-2 col-xs-3 control-label" for="cidade">Cidade/Estado:</label>
                	<div id="scrollable-dropdown-menu" class="col-sm-3 col-xs-4">
                		<input type="hidden" id="cidadeId" name="cidade.id" />
	                	<input type="text" id="cidadeNome" class="form-control" placeholder="Digite ao menos 2 caracteres" type="text" onblur="limparOcultos(this, 'cidade.id')" autocomplete="off"/>
	                </div>
	            </div>
				<div id="div_entidade" class="form-group">
		            <label class="col-sm-2 col-xs-3 control-label" for="entidade.razaoSocial">Entidade:</label>
                	<div class="col-sm-4 col-xs-6">
                	    <input type="hidden" id="entidadeId" name="entidadeId"/>
	                	<input type="text" id="entidadeRazaoSocial" name="entidadeRazaoSocial" class="form-control" placeholder="Selecione a entidade" onblur="limparOcultos(this, 'entidade.id')" autocomplete="off"/>
	                </div>
	            </div>
				<div class="form-group">
		                <label class="col-sm-2 control-label">Assunto</label>
		                <div class="col-sm-10">
		                	<input type="text" id="assunto" name="assunto" class="form-control"/>
		                </div>
		            </div>
		        <div class="form-group small">
		        	<label class="col-sm-2 control-label">Códigos</label>
		        	<div class="col-sm-10">
			        	<div class="col-sm-12">Use os seguintes código para inserir informações dentro do seu email:</div>
			        	<div class="col-sm-6"><b>&#36;{email}</b> - Email do Presidente.</div>
			        	<div class="col-sm-6"><b>&#36;{presidente.nomeCompleto}</b> - Nome do Presidente.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.razaoSocial}</b> - Nome da Entidade.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.endereco.logradouro}</b> - Endereço Entidade.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.endereco.numero}</b> - Numero Entidade.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.endereco.bairro}</b> - Bairro Entidade.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.endereco.cep}</b> - CEP Entidade.</div>
			        	<div class="col-sm-6"><b>&#36;{entidade.endereco.cidade}</b> - Cidade Entidade.</div>
		        	</div>
		        </div>
                <div class="form-group">
		                <label class="col-sm-2 control-label" >Mensagem</label>
		                <div class="col-sm-10">
		                	<textarea id="mensagem" name="mensagem" rows="10" class="form-control summernote"></textarea>
		                </div>
		            </div>
                 <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <button type="submit" class="btn btn-primary btn-mini">Enviar</button>
					    </div>
					  </div>
            </form>
        </div>
        
<script type="text/javascript" src="/js/custom/autocompletecidade.js"></script>
<script type="text/javascript" src="/js/plugins/summernote/summernote.js"></script>
<script type="text/javascript">
var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
$(function(){

		$('.summernote').summernote({
			 height: 300
		});
			
	
		completeCidade($('#cidadeNome'), $('#cidadeId'), baseUrl, function(item){
		    $("#entidadeRazaoSocial").focus();
	        return item.name;
		}, true);
 		
 		$('#entidadeRazaoSocial').typeahead({
 			    source: function (query, process) {
 			        return $.ajax({
 			        	url: baseUrl + '/gestao/entidade/list',
 			            type: 'Get',
 			            data: { maxRows: 6, query: query, cidade: $('#cidadeId').val() },
 			            dataType: 'json',
 			            success: function (result) {

 			                var resultList = result.map(function (item) {
 			                	var aItem;
 	 			                if(item.presidente != null && item.dirigente != null)
 			                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: item.presidente.id, nomePresidente: item.presidente.nome, idDirigente: item.dirigente.id, nomeDirigente: item.dirigente.nome, emailDirigente: item.dirigente.email, telefoneDirigente: item.dirigente.telefone, idOutro: item.outro.id, nomeOutro: item.outro.nome, emailOutro: item.outro.email, telefoneOutro: item.outro.telefone };
 	 			                else
 	 			                if(item.presidente != null)
 			                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: item.presidente.id, nomePresidente: item.presidente.nome, idDirigente: '', nomeDirigente: '', emailDirigente: '', telefoneDirigente: '', idOutro: '', nomeOutro: '', emailOutro: '', telefoneOutro: '' };
 	 			                else
 	 			                if(item.dirigente != null)
 			                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: '', nomePresidente: ''};
 	 			                else
 			                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: '', nomePresidente: '' };
 	 			                	
 			                    return JSON.stringify(aItem);
 			                });

 			                return process(resultList);

 			            }
 			        });
 			    },

 				matcher: function (obj) {
 			        var item = JSON.parse(obj);
 			        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase());
 			    },

 			    sorter: function (items) {          
 			       var beginswith = [], caseSensitive = [], caseInsensitive = [], item;
 			        while (aItem = items.shift()) {
 			            var item = JSON.parse(aItem);
 			            if (!item.name.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(JSON.stringify(item));
 			            else if (~item.name.indexOf(this.query)) caseSensitive.push(JSON.stringify(item));
 			            else caseInsensitive.push(JSON.stringify(item));
 			        }

 			        return beginswith.concat(caseSensitive, caseInsensitive)

 			    },


 			    highlighter: function (obj) {
 			        var item = JSON.parse(obj);
 			        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
 			        var name = item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
 			            return '<strong>' + match + '</strong>'
 			        })
 			        
 			        var itm = ''
 		                 + "<div class='typeahead_wrapper'>"
 		                 //+ "<img class='typeahead_photo' src='" + name + "' />"
 		                 + "<div class='typeahead_labels'>"
 		                 + "<div class='typeahead_primary'>" + name + "</div>"
 		                 + "<div class='typeahead_secondary'>" + item.endereco + "</div>"
 		                 + "</div>"
 		                 + "</div>";
 		        	return itm;
 			    },

 			    updater: function (obj) {
 			        var item = JSON.parse(obj);
 			        $('#entidadeId').attr('value', item.id);
 			        return item.name;
 			    }
 			});


	 		$('#formEnvio').submit(function(event) {
				// Prevent the button from triggering a form submission.
				//evt.preventDefault();
				$('#target').loadingOverlay();

				// get the form data
		        // there are many ways to get this data using jQuery (you can use the class or id also)
		        var formData = {
		            'entidadeId'        : $('input[name=entidadeId]').val(),
		            'assunto'           : $('input[name=assunto]').val(),
		            'mensagem'          : $('textarea[name=mensagem]').val()
		        };
				
				$.ajax({
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					data : formData,
					url : '/gestao/entidade/enviar/',
					error : function(jqXHR, textStatus, errorThrown) {
						$('#target').loadingOverlay('remove');
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
						$('#target').loadingOverlay('remove');
						alert("Email enviado com sucesso!");
					}
				});

				event.preventDefault();
			});
         
     });

function limparOcultos(campo, oculto){
    if(campo.value == ''){
    	$(oculto).val('');
    }
}
</script>
