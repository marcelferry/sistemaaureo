<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<style>
.inner_table {
    height: 400px;
    overflow-y: auto;
    border: solid gray 1px;
}

.panel-group .panel-heading + .panel-collapse > .panel-body {
  border: 1px solid #ddd;
}
.panel-group,
.panel-group .panel,
.panel-group .panel-heading,
.panel-group .panel-heading a,
.panel-group .panel-title,
.panel-group .panel-title a,
.panel-group .panel-body,
.panel-group .panel-group .panel-heading + .panel-collapse > .panel-body {
  border-radius: 2px;
  border: 0;
}
.panel-group .panel-heading {
  padding: 0;
}
.panel-group .panel-heading a {
  display: block;
  /* background: #668bb1; */
  background: #f0ad4e;
  color: #ffffff;
  padding: 15px;
  text-decoration: none;
  font-weight: bold;
  position: relative;
}
.panel-group .panel-heading a.collapsed {
  /* background: #f0ad4e; */
  background: #668bb1;
  color: inherit;
}
.panel-group .panel-heading a:after {
  content: '-';
  position: absolute;
  right: 20px;
  top:5px;
  font-size:30px;
}
.panel-group .panel-heading a.collapsed:after {
  content: '+';
}
.panel-group .panel-collapse {
  margin-top: 5px !important;
}
.panel-group .panel-body {
  background: #ffffff;
  padding: 15px;
}
.panel-group .panel {
  background-color: transparent;
}
.panel-group .panel-body p:last-child,
.panel-group .panel-body ul:last-child,
.panel-group .panel-body ol:last-child {
  margin-bottom: 0;
}



<c:if test="${!erros}">
.child {
	display: none;
}
</c:if>

.dateimplantado{
	display: none;
}

.ui-helper-hidden-accessible { display:none; }

.ui-datepicker { font-size: 9pt !important; }

#sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
#sortable li span { position: absolute; margin-left: -1.3em; }

.modal-header-verde {
    padding:9px 15px;
    border-bottom:1px solid #eee;
    background-color: #47a447;
    -webkit-border-top-left-radius: 5px;
    -webkit-border-top-right-radius: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
     border-top-left-radius: 5px;
     border-top-right-radius: 5px;
 }
 
.modal-header-amarelo {
    padding:9px 15px;
    border-bottom:1px solid #eee;
    background-color: #ed9c28;
    -webkit-border-top-left-radius: 5px;
    -webkit-border-top-right-radius: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
     border-top-left-radius: 5px;
     border-top-right-radius: 5px;
 }
 
.modal-header-vermelho {
    padding:9px 15px;
    border-bottom:1px solid #eee;
    background-color: #d2322d;
    -webkit-border-top-left-radius: 5px;
    -webkit-border-top-right-radius: 5px;
    -moz-border-radius-topleft: 5px;
    -moz-border-radius-topright: 5px;
     border-top-left-radius: 5px;
     border-top-right-radius: 5px;
 }

.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
}

</style>

<form:form method="post" action="add" commandName="planoMetasForm" class="form-vertical validado" role="form">
	<form:hidden path="id"/>
	<form:hidden path="fase"/>
	<form:hidden path="rodizio.id"/>
	<form:hidden path="instituto.id"/>
	<form:hidden path="facilitador.id"/>
	<form:hidden path="coordenador.id"/>
	<form:hidden path="nomeCoordenador"/>
	<form:hidden path="emailCoordenador"/>
	<form:hidden path="telefoneCoordenador"/>
	<form:hidden path="presidente.id"/>
	<form:hidden path="outro.id"/>
	<form:hidden path="contratante.id"/>
	<form:hidden path="nomeContratante"/>
	<form:hidden path="telefoneContratante"/>
	<form:hidden path="emailContratante"/>
	<form:hidden path="tipoContratante"/>
	<form:hidden path="evento"/>
	<form:hidden path="finalizado"/>
	<form:hidden path="validado"/>

	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>Instituto</legend>
				<div id="div_entidade" class="col-sm-12 small">
					<label class="col-sm-2 col-xs-3 control-label">Ano:</label>
					<div class="col-sm-3 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.rodizio.ciclo}</div>
					<c:if test="${planoMetasForm.evento == 'RODIZIO'}">
						<label class="col-sm-2 col-xs-3 control-label">Facilitador:</label>
						<div class="col-sm-5 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.facilitador.nomeCompleto} </div>
					</c:if>
				</div>
				<div id="div_entidade" class="col-sm-12 small">
					<label class="col-sm-2 col-xs-3 control-label">Instituto:</label>
					<div class="col-sm-3 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.instituto.descricao}</div>
					<label class="col-sm-2 col-xs-3 control-label">Dirigente Nacional:</label>
					<div class="col-sm-5 col-xs-9 control-label" style="text-align: left;">${planoMetasForm.instituto.dirigenteNacional.nomeCompleto}</div>
				</div>
			</fieldset>
			<fieldset>
				<legend>Entidade</legend>
				<div id="div_entidade" class="col-sm-12 small">
		            <label class="col-sm-2 col-xs-3 control-label" for="entidade.razaoSocial">Entidade:</label>
	               	<div class="col-sm-6 col-xs-9 control-label" style="text-align: left;">
	               	    <form:hidden path="entidade.id" />
	               	    <form:hidden path="entidade.razaoSocial" />
	               	    <span id="entidade.razaoSocial">${planoMetasForm.entidade.razaoSocial} - ${planoMetasForm.entidade.endereco.cidade.nome}/${planoMetasForm.entidade.endereco.cidade.estado.sigla}</span>
	                </div>
	            </div>
				<div id="div_endereco" class="col-sm-12 small">
		            <label class="col-sm-2 col-xs-3 control-label"><spring:message code="label.endereco" />:</label>
	                <div class="col-sm-3 col-xs-9 control-label" style="text-align: left;"><span id="entidade.endereco">${planoMetasForm.entidade.endereco.enderecoFormatado}</span></div>
		            <label class="col-sm-2 col-xs-3 control-label">Telefone:</label>
	                <div class="col-sm-5 col-xs-9 control-label" style="text-align: left;"><span id="entidade.telefone">${planoMetasForm.entidade.primeiroTelefone}&nbsp;</span></div>
	            </div>
	            <c:if test="${ ! empty planoMetasForm.presidente.nomeCompleto}">
	            <div id="div_presidente" class="col-sm-12 small">
		            <label class="col-sm-2 control-label" for="presidente.nome">Presidente:</label>
	               	<div class="col-sm-6 control-label" style="text-align: left;">
	               		<form:hidden path="presidente.id" />
	               		<form:hidden path="presidente.nomeCompleto" />
	               		<span id="presidente.nomeCompleto">${planoMetasForm.presidente.nomeCompleto}</span>
	                </div>
	            </div>
	            </c:if>
	            <c:if test="${ ! empty planoMetasForm.coordenador.nomeCompleto}">
	            <div id="div_coordenador" class="col-sm-12 small">
		            <label class="col-sm-2 control-label" for="coordenador.nome">Coordenador:</label>
	               	<div class="col-sm-6 control-label" style="text-align: left;">
	               		<form:hidden path="coordenador.nomeCompleto" />
	               		<span id="coordenador.nomeCompleto">${planoMetasForm.nomeCoordenador}</span>
	                </div>
	            </div>
	            </c:if>
	            <c:if test="${ empty planoMetasForm.coordenador.nomeCompleto && ! empty planoMetasForm.nomeCoordenador}">
	            <div id="div_coordenador" class="col-sm-12 small">
		            <label class="col-sm-2 control-label" for="coordenador.nome">Coordenador:</label>
	               	<div class="col-sm-6 control-label" style="text-align: left;">
	               		<span id="coordenador.nomeCoordenador">${planoMetasForm.nomeCoordenador}</span>
	                </div>
	            </div>
	            </c:if>
	            
	            <div id="div_outros" class="col-sm-12 small">
		            <label class="col-sm-2 control-label" for="outro.nome">Contratante:</label>
	               	<div class="col-sm-6 control-label" style="text-align: left;">
	     					<form:hidden path="tipoContratante"/>
	     					<c:if test="${ planoMetasForm.tipoContratante == 'PRESIDENTE' }">Presidente</c:if>
	     					<c:if test="${ planoMetasForm.tipoContratante == 'COORDENADOR' }">Coordenador</c:if>
	               		<c:if test="${ ! empty planoMetasForm.outro.nomeCompleto}">
	               			<form:hidden path="outro.id" />
	               			<form:hidden path="outro.nomeCompleto" />
	               			<b>Trabalhador</b> - ${planoMetasForm.outro.nomeCompleto}
	               		</c:if>
	                </div>
	            </div>
       		</fieldset>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>Plano de Metas</legend>
	            <div class="form-group">
	            	<a href="#" class="btn btn-primary expand-all ">Expandir Tudo</a>
	            </div>
	              
	            <div class="panel-group" id="accordion">
	               <c:forEach items="${planoMetasForm.dependencias}" var="meta" varStatus="status">
	                		 	<template:itemMeta meta="${meta}" index="${status.index}" count="${status.count}"/>
	                 	</c:forEach>
	                 	</div>
	                 	<div class="form-group">
					<div class="col-md-12">
						<c:set var="indexAnot" value="${fn:length(planoMetasForm.anotacoes)}" scope="request"/>
						<c:if test="${indexAnot > 0}">
						<script>
							var nivel = 1;
							var indexAnot = ${fn:length(planoMetasForm.anotacoes)};
						</script>
						<ul class="list-group" id="anotacoes">
						  <c:forEach items="${planoMetasForm.anotacoes}" var="anotacao" varStatus="idAnot">
						  	<c:if test="${(idAnot.count - 1) > idAnot.index}">
						  	<c:if test="${anotacao.sinalizador == 'VERDE'}">
						  		<li class="list-group-item list-group-item-success" title="${anotacao.texto}">
						  	</c:if>
						  	<c:if test="${anotacao.sinalizador == 'AMARELO'}">
						  		<li class="list-group-item list-group-item-warning" title="${anotacao.texto}">
						  	</c:if>
						  	<c:if test="${anotacao.sinalizador == 'VERMELHO'}}">
						  		<li class="list-group-item list-group-item-danger" title="${anotacao.texto}">
						  	</c:if>
						  		<form:hidden path="anotacoes[${idAnot.index}].id" />
							  	<form:hidden path="anotacoes[${idAnot.index}].remove" />
							  	<form:hidden path="anotacoes[${idAnot.index}].responsavel.id" />
							  	<form:hidden path="anotacoes[${idAnot.index}].texto" />
							  	<form:hidden path="anotacoes[${idAnot.index}].data" />
							  	<form:hidden path="anotacoes[${idAnot.index}].sinalizador" />
						  		${anotacao.texto}
						  	</li>
						  	</c:if>
						  </c:forEach>
						</ul>
						</c:if>
					</div>
					<div class="col-md-12">
							<form:hidden path="anotacoes[${indexAnot}].id" />
						  	<form:hidden path="anotacoes[${indexAnot}].remove" />
						  	<form:hidden path="anotacoes[${indexAnot}].nivel" />
						  	<form:hidden path="anotacoes[${indexAnot}].responsavel.id" />
						  	<form:hidden path="anotacoes[${indexAnot}].data" />
						  	<form:hidden path="anotacoes[${indexAnot}].sinalizador" />
						  	<form:label path="anotacoes[${indexAnot}].texto"><spring:message code="label.comentarios" />:</form:label>
							<form:textarea path="anotacoes[${indexAnot}].texto" rows="8" class="form-control col-md-12 input-sm"/>
					</div>
					<!--  div class="col-md-1">
						<button type="button" class="btn btn-success btn-xs btnAnotacaoSuccess" data-toggle="modal" data-id="${meta.id}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
						<!-- button type="button" class="btn btn-warning btn-xs btnAnotacaoWarning" data-toggle="modal" data-id="${meta.id}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
						<button type="button" class="btn btn-danger btn-xs btnAnotacaoDanger" data-toggle="modal" data-id="${meta.id}">
									<span class="glyphicon glyphicon-plus">
									</span>
						</button>
					</div-->
				</div>
				<br />
				<form:errors element="div" path="anotacoes" cssClass="error"/>
			</fieldset>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<fieldset>
	       		<div class="form-group pull-right">
		        		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR'}">
	                	<button type="button" class="btn btn-primary" onclick="finalizar();">Finalizar</button>
	                	</c:if>
	                	<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
	                	<button type="button" class="btn btn-primary" onclick="finalizar();">Validar</button>
	                	</c:if>
		        		<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
	                	<button type="button" class="btn btn-primary" onclick="enviar();">Salvar</button>
	                	</c:if>
	                	 <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE'}">
						<!-- button type="button" class="btn btn-success" onclick="fichaBranco();">Imprimir Ficha em Branco</button-->
						</c:if>
						<button type="button" class="btn btn-default" onclick="voltar();">Voltar</button>
	       		</div>
	       	</fieldset>
	    </div>
	</div>
</form:form>
          
<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA' || ROLE_CONTROLE == 'ROLE_METAS_FACILITADOR' }">
<form:form id="planoMetasFormVoltar" name="planoMetasFormVoltar" method="post" action="inicio" commandName="planoMetasForm" class="form-horizontal">
     <form:hidden path="fase" value="1"/>
     <form:hidden path="rodizio.id"/>
	 <form:hidden path="instituto.id"/>
	 <form:hidden path="facilitador.id"/>
</form:form>
</c:if>
          
<c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_PRESIDENTE' }">
<form:form id="planoMetasFormVoltar" name="planoMetasFormVoltar" method="get" action="/gestao/planodemetas/ciclo/${ CICLO_CONTROLE.id }" commandName="planoMetasForm" class="form-horizontal">
</form:form>
</c:if>
          
<div class="modal fade" id="addAnotacao" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog  modal-sm">
       <form method="post" action="add" class="form-horizontal" role="form">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                   <h4 class="modal-title" id="myModalLabel">Nova Anotação - <span id="tipoAnotacao"></span></h4>
               </div>
               <div class="modal-body">
                   <input id="metaId" type="hidden" name="id" placeholder="Id" value=""/>
                   <div class="form-group">
                       <label for="dataHora">Data/Hora:</label>
                       <input id="dataHora" name="dataHora" class="form-control input-sm"  value="21/04/2014 - 13:30" readonly="readonly"/>
                   </div>
                   <div class="form-group">
                       <label for="pessoa">Por:</label>
                       <input id="pessoa" name="pessoa" class="form-control input-sm" value="<sec:authentication property="principal.pessoa.nome" />" readonly="readonly"/>
                   </div>
                   <div class="form-group">
                       <label for="comentario">Comentário:</label>
                       <textarea id="comentario" name="comentario" class="form-control input-sm" placeholder="Seu Comentário" rows="3"></textarea>
                   </div>
               </div>
               <div class="modal-footer">
                   <button type="button" class="btn btn-primary btn-sm" id="createAnotacao">Salvar</button>
                   <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
               </div>
           </div><!-- /.modal-content -->
       </form>
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class='modal fade' id='modalFinalizacao' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
	<div class="modal-dialog modal-sm">
		 <div class="modal-content">
			<div class='modal-header'>
				<button type='button' class='close' data-dismiss='modal'
					aria-hidden='true'>×</button>
				<h3 id='myModalLabel'>Confirmação de envio de ficha incompleta</h3>
			</div>
			<div class='modal-body'>
				<p class='error-text'>Você confirma o envio da ficha de metas sem o preenchimento completo de todos os itens obrigatórios?</p>
				<!-- div class="form-group">
					<input type="checkbox" name="confirmCheck" value=""/> Sim, confirmo.
				</div-->
			</div>
			<div class='modal-footer'>
				<button class='btn btn-danger' data-dismiss='modal'
					aria-hidden='true'>Cancelar</button>
				<button class='btn btn-success' data-dismiss='modal'>SIM</button>
			</div>
		</div>
	</div>
</div>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script src="/js/jquery.ui.monthpicker.js"></script>
<script>
    var somethingChanged = false;

    function enviar(){
    	$(window).unbind('beforeunload');
    	document.getElementById('finalizado').value = false;
    	document.getElementById('validado').value = false;
    	document.getElementById('planoMetasForm').action = 'add';
    	document.getElementById('planoMetasForm').target = '_self';
        document.getElementById('planoMetasForm').submit();
    }

    function voltar(){
    	document.getElementById('planoMetasFormVoltar').submit();
    }

    function fichaBranco(){
    	document.getElementById('planoMetasForm').action = '/gestao/relatorio/imprimePreFichaBranco';
    	document.getElementById('planoMetasForm').target = '_blank';
    	document.getElementById('planoMetasForm').submit();
    }
    
     $(function() {

    	 $('.expand-all').click(function(){
    		    var $this = $(this);
    		    if($this.hasClass('collapse-all')){
    		        $this.text(' Expandir Todos');
    		        $('.panel-collapse.in').collapse('hide');
    		        $this.removeClass('collapse-all');
    		    } else {
    		        $this.text('Fechar Todos');
    		        $('.panel-collapse:not(".in")').collapse('show');
    		        $this.addClass('collapse-all');
    		    }
    		});

    		$('#accordion').on('show.bs.collapse hide.bs.collapse', function (obj) {
    		    var $this = $(this);
    		    var $expand = $('.expand-all');
    		    if(obj.type=="show"){
    		        $expand.text('Fechar Todos').addClass('collapse-all');
    		    }
    		    else{
    		        if($('.collapse.in').not(obj.target).length == 0){
    		            $expand.text('Expandir Todos').removeClass('collapse-all');
    		        }
    		    }
    		});

    		<c:if test="${erros}">
    			$('.expand-all').text('Fechar Todos');
		        $('.panel-collapse:not(".in")').collapse('show');
		        $('.expand-all').addClass('collapse-all');
    		</c:if>

    	 $("[data-toggle='popover']").popover({ trigger: "hover" });

    	 $('#modalFinalizacao .btn-success').click(function(e){
    	      e.preventDefault();
    	      //alert($('#myField').val());
    	      $(window).unbind('beforeunload');
    	      document.getElementById('finalizado').value = false;
    	      document.getElementById('validado').value = false;
    	      document.getElementById('planoMetasForm').action = 'add';
    	      document.getElementById('planoMetasForm').target = '_self';
              document.getElementById('planoMetasForm').submit();
              
    	      /*
    	      $.post('http://path/to/post', 
    	         $('#myForm').serialize(), 
    	         function(data, status, xhr){
    	           // do something here with response;
    	         });
    	      */
    	    });

		 $(".btnAnotacaoSuccess").click(function(){
		     $("#metaId").val($(this).data('id'));
		     $("#tipoAnotacao").text("Comentário");
		     $("#addAnotacao #dataHora").val( getDataAtual() ) ;
		     $('#addAnotacao #comentario').val('');
		     var $header = $('#addAnotacao').find(".modal-header");
		     $header.removeClass('modal-header-vermelho');
		     $header.removeClass('modal-header-amarelo');
		     $header.addClass('modal-header-verde');
		     $('#addAnotacao').modal('show');
		   });
		   
		 $(".btnAnotacaoWarning").click(function(){
		     $("#metaId").val($(this).data('id'));
		     $("#tipoAnotacao").text("Atenção");
		     $("#addAnotacao #dataHora").val( getDataAtual() ) ;
		     $('#addAnotacao #comentario').val('');
		     var $header = $('#addAnotacao').find(".modal-header");
		     $header.removeClass('modal-header-vermelho');
		     $header.removeClass('modal-header-verde');
		     $header.addClass('modal-header-amarelo');
		     $('#addAnotacao').modal('show');
		   });
		   
		 $(".btnAnotacaoDanger").click(function(){
		     $("#metaId").val($(this).data('id'));
		     $("#tipoAnotacao").text("Alerta");
		     $("#addAnotacao #dataHora").val( getDataAtual() ) ;
		     $('#addAnotacao #comentario').val('');
		     var $header = $('#addAnotacao').find(".modal-header");
		     $header.removeClass('modal-header-verde');
		     $header.removeClass('modal-header-amarelo');
		     $header.addClass('modal-header-vermelho');
		     $('#addAnotacao').modal('show');
		   });


		 $('#createAnotacao').click(function(){
			 	var id = $('#addAnotacao #metaId').val();
			 	var texto = $('#addAnotacao #comentario').val();
			 	var pessoa = $('#addAnotacao #pessoa').val();
			 	var dataHora = $('#addAnotacao #dataHora').val();
			 	var tipo = $("#tipoAnotacao").text();
			 	
			 	var $ul = $('#observacoes' + id);
	        	var $li = $('<li />').appendTo($ul);
	        	var sinalizador;

				$li.addClass('list-group-item');
				if(tipo == 'Comentário'){
					$li.addClass('list-group-item-success');
					sinalizador = 'VERDE';
				}
				if(tipo == 'Atenção'){
					$li.addClass('list-group-item-warning');
					sinalizador = 'AMARELO';
				}
				if(tipo == 'Alerta'){
					$li.addClass('list-group-item-danger');
					sinalizador = 'VERMELHO';
		 		}
				$li.attr('title', pessoa + ": " + texto + " - Em: " + dataHora );
				$li.text(texto);

				var indiceObs = window["indexObs"+id];

				var nivel = window["nivel"+id];
				if(nivel == 1){
				
					var indiceNivel1 = window["indexNivel1"+id];

					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.observacoes' + indiceObs + '.id',
					    name: 'metas['+ indiceNivel1 +'].observacoes[' + indiceObs + '].id',
						value: '0'
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.observacoes' + indiceObs + '.sinalizador',
					    name: 'metas['+ indiceNivel1 +'].observacoes[' + indiceObs + '].sinalizador',
						value: sinalizador
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.observacoes' + indiceObs + '.data',
					    name: 'metas['+ indiceNivel1 +'].observacoes[' + indiceObs + '].data',
						value: dataHora
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.observacoes' + indiceObs + '.texto',
					    name: 'metas['+ indiceNivel1 +'].observacoes[' + indiceObs + '].texto',
						value: texto
					}).appendTo($li);

				} else if(nivel == 2){
				
					var indiceNivel1 = window["indexNivel1"+id];
					var indiceNivel2 = window["indexNivel2"+id];

					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.observacoes' + indiceObs + '.id',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].observacoes[' + indiceObs + '].id',
						value: '0'
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.observacoes' + indiceObs + '.sinalizador',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].observacoes[' + indiceObs + '].sinalizador',
						value: sinalizador
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.observacoes' + indiceObs + '.data',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].observacoes[' + indiceObs + '].data',
						value: dataHora
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.observacoes' + indiceObs + '.texto',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].observacoes[' + indiceObs + '].texto',
						value: texto
					}).appendTo($li);

				} else if(nivel == 3){
				
					var indiceNivel1 = window["indexNivel1"+id];
					var indiceNivel2 = window["indexNivel2"+id];
					var indiceNivel3 = window["indexNivel2"+id];

					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.observacoes' + indiceObs + '.id',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].observacoes[' + indiceObs + '].id',
						value: '0'
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.observacoes' + indiceObs + '.sinalizador',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].observacoes[' + indiceObs + '].sinalizador',
						value: sinalizador
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.observacoes' + indiceObs + '.data',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].observacoes[' + indiceObs + '].data',
						value: dataHora
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.observacoes' + indiceObs + '.texto',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].observacoes[' + indiceObs + '].texto',
						value: texto
					}).appendTo($li);
					
				} else if(nivel == 4){
				
					var indiceNivel1 = window["indexNivel1"+id];
					var indiceNivel2 = window["indexNivel2"+id];
					var indiceNivel3 = window["indexNivel3"+id];
					var indiceNivel4 = window["indexNivel4"+id];

					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.observacoes' + indiceObs + '.id',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].observacoes[' + indiceObs + '].id',
						value: '0'
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.observacoes' + indiceObs + '.sinalizador',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].observacoes[' + indiceObs + '].sinalizador',
						value: sinalizador
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.observacoes' + indiceObs + '.data',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].observacoes[' + indiceObs + '].data',
						value: dataHora
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.observacoes' + indiceObs + '.texto',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].observacoes[' + indiceObs + '].texto',
						value: texto
					}).appendTo($li);
					
				} else if(nivel == 4){
				
					var indiceNivel1 = window["indexNivel1"+id];
					var indiceNivel2 = window["indexNivel2"+id];
					var indiceNivel3 = window["indexNivel3"+id];
					var indiceNivel4 = window["indexNivel4"+id];
					var indiceNivel5 = window["indexNivel5"+id];

					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.dependencias'+ indiceNivel5 +'.observacoes' + indiceObs + '.id',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].dependencias['+ indiceNivel5 +'].observacoes[' + indiceObs + '].id',
						value: '0'
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.dependencias'+ indiceNivel5 +'.observacoes' + indiceObs + '.sinalizador',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].dependencias['+ indiceNivel5 +'].observacoes[' + indiceObs + '].sinalizador',
						value: sinalizador
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.dependencias'+ indiceNivel5 +'.observacoes' + indiceObs + '.data',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].dependencias['+ indiceNivel5 +'].observacoes[' + indiceObs + '].data',
						value: dataHora
					}).appendTo($li);
					$('<input>').attr({
					    type: 'hidden',
					    id: 'metas' + indiceNivel1 + '.dependencias'+ indiceNivel2 +'.dependencias'+ indiceNivel3 +'.dependencias'+ indiceNivel4 +'.dependencias'+ indiceNivel5 +'.observacoes' + indiceObs + '.texto',
					    name: 'metas['+ indiceNivel1 +'].dependencias['+ indiceNivel2 +'].dependencias['+ indiceNivel3 +'].dependencias['+ indiceNivel4 +'].dependencias['+ indiceNivel5 +'].observacoes[' + indiceObs + '].texto',
						value: texto
					}).appendTo($li);

				}

				window["indexObs"+id] = window["indexObs"+id] + 1;
				
		        $('#addAnotacao ').modal('hide');
		    });

    	 $(".date-picker").monthpicker({
             showOn: "focus",
             buttonImage: "../../img/calendar.png",
             buttonImageOnly: true,
             pattern: 'mm/yyyy', // Default is 'mm/yyyy' and separator char is not mandatory
             monthNames: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
             monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez']
         });

     });

    	function implantadoRadio(source){
			var meta = $(source).data("meta");
			
			if($(source).val() == "IMPLANTADA"){
				//Desabilitar planejados
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('disabled', true);
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('checked', false);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('disabled', true);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('checked', false);
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', true);
				$("#situacaoDesejada_previsao_" + meta).val("");
				$("#situacaoDesejada_situacao_planejada_" + meta).closest('.form-group').removeClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').removeClass('has-error has-feedback');
			} else {
				//Habilitar planejado
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('disabled', false);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('disabled', false);
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', false);
				$("#situacaoDesejada_situacao_planejada_" + meta).closest('.form-group').addClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').addClass('has-error has-feedback');
				
			}

			if($(source).val() == "NAOIMPLANTADA"){
				$("#situacaoAtual_conclusao_" + meta).prop('disabled', true);
				$("#situacaoAtual_conclusao_" + meta).val("");
				$("#situacaoAtual_conclusao_" + meta).closest('.form-group').removeClass('has-error has-feedback');
				// Ocultar Data Implantacao
				$("#bloco_dataimplantado_" + meta).hide();
			} else if($(source).val() == "REPLANEJADA"){
				$("#bloco_datareplanejado_" + meta).show();
			} else {
				$("#situacaoAtual_conclusao_" + meta).prop('disabled', false);
				$("#situacaoAtual_conclusao_" + meta).prop('disabled', false);
				$("#situacaoAtual_conclusao_" + meta).closest('.form-group').addClass('has-error has-feedback');
				//Exibir Data Implantacao
				$("#bloco_dataimplantado_" + meta).show();
				$("#bloco_datareplanejado_" + meta).hide();
			}
        } 


    	function realizadoRadio(source){

    		var meta = $(source).data("meta");

    		if($(source).val() == "NAOIMPLANTADA"){
				$("#situacaoAtual_conclusao_" + meta).prop('disabled', true);
				$("#situacaoAtual_conclusao_" + meta).val("");
				$("#situacaoAtual_conclusao_" + meta).closest('.form-group').removeClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').addClass('has-error has-feedback');
			} else {
				$("#situacaoAtual_conclusao_" + meta).prop('disabled', false);
				$("#situacaoAtual_conclusao_" + meta).closest('.form-group').addClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').removeClass('has-error has-feedback');
			}

    		$("#situacaoDesejada_situacao_planejada_" + meta).closest('.form-group').addClass('has-error has-feedback');
            
        }
        
    	function planejadoRadio(source){

    		var meta = $(source).data("meta");

    		if($(source).val() == "NAOPLANEJADA"){
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', true);
				$("#situacaoDesejada_previsao_" + meta).val("");
				$("#situacaoDesejada_previsto_" + meta).prop('disabled', true);
				$("#situacaoDesejada_previsto_" + meta).val("");
				$("#situacaoDesejada_previsao_" + meta).closest('.form-group').removeClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').addClass('has-error has-feedback');
			} else {
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', false);
				$("#situacaoDesejada_previsto_" + meta).prop('disabled', false);
				$("#situacaoDesejada_previsao_" + meta).closest('.form-group').addClass('has-error has-feedback');
				$("#observacoes_" + meta).closest('.form-group').removeClass('has-error has-feedback');
			}

    		$("#situacaoDesejada_situacao_planejada_" + meta).closest('.form-group').addClass('has-error has-feedback');
            
        }
        
    	function situacao(source){
			var meta = $(source).data("meta");
			
			if($(source).val() == "IMPLANTADA"){
				//Desabilitar planejados
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('disabled', true);
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('checked', false);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('disabled', true);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('checked', false);
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', true);
				$("#situacaoDesejada_previsao_" + meta).val("");
			} else {
				//Habilitar planejado
				$("#situacaoDesejada_situacao_planejada_" + meta).prop('disabled', false);
				$("#situacaoDesejada_situacao_naoplanejada_" + meta).prop('disabled', false);
				$("#situacaoDesejada_previsao_" + meta).prop('disabled', false);
				
			}

			if($(source).val() == "IMPLANTADA" || $(source).val() == "IMPLPARCIAL"){
				//Exibir Data Implantacao
				$("#bloco_dataimplantado_" + meta).show();
			} else {
				// Ocultar Data Implantacao
				$("#bloco_dataimplantado_" + meta).hide();
			}
        } 

        function getDataAtual(){
        	var data = new Date();

        	// Guarda cada pedaço em uma variável
        	var dia     = data.getDate();           // 1-31
        	var dia_sem = data.getDay();            // 0-6 (zero=domingo)
        	var mes     = data.getMonth();          // 0-11 (zero=janeiro)
        	var ano2    = data.getYear();           // 2 dígitos
        	var ano4    = data.getFullYear();       // 4 dígitos
        	var hora    = data.getHours();          // 0-23
        	var min     = data.getMinutes();        // 0-59
        	var seg     = data.getSeconds();        // 0-59
        	var mseg    = data.getMilliseconds();   // 0-999
        	var tz      = data.getTimezoneOffset(); // em minutos

        	// Formata a data e a hora (note o mês + 1)
        	var str_data = zeroFill(dia, 2) + '/' + zeroFill((mes+1), 2) + '/' + ano4 + ' ' + zeroFill(hora,2) + ':' + zeroFill(min,2) + ':' + zeroFill(seg,2) ;
        	return str_data;
        }

        function zeroFill( number, width )
        {
          width -= number.toString().length;
          if ( width > 0 )
          {
            return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
          }
          return number + ""; // always return a string
        }

        function finalizar(){
            var erro = false;
            
            <c:if test="${ ROLE_CONTROLE == 'ROLE_METAS_SECRETARIA'}">
       	 		document.getElementById('validado').value = true;
       	 	</c:if>

       	 	document.getElementById('finalizado').value = true;
            <c:if test="${erros}">
            erro = true;
            </c:if>

            if(somethingChanged){
				erro = false;
           }
            
           if(erro){
        	   $('#modalFinalizacao').modal({
                   show: 'true'
               });
            } else {
            	$(window).unbind('beforeunload');
            	document.getElementById('planoMetasForm').action = 'add';
            	document.getElementById('planoMetasForm').target = '_self';
            	document.getElementById('planoMetasForm').submit();
            }
        }


        
        $(document).ready(function() { 
           $('input').change(function() { 
                somethingChanged = true; 
                $(window).bind('beforeunload', function(){
                	return 'Há dados alterados sem salvar \n Deseja mesmo sair?';
                });
           }); 
        });

        
    
    </script>