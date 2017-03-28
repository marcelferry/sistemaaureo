<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.typeahead_wrapper { display: block; height: 30px; }
.typeahead_photo { float: left; max-width: 30px; max-height: 30px; margin-right: 5px; }
.typeahead_labels { float: left; height: 30px; }
.typeahead_primary { font-weight: bold; }
.typeahead_secondary { font-size: .8em; margin-top: -5px; }
</style>

<style>
.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
.autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:22px}
.autocomplete-selected { background: #F0F0F0; }
.autocomplete-suggestions strong { font-weight: bold; color: #3399FF; }

</style>
      <div class="row">
	            <form:form method="post" action="add" commandName="comissao" class="form-horizontal">
					<div class="form-group">
		                <form:label class="col-sm-2 control-label" path="descricao">Nome</form:label>
		                <div class="col-sm-10">
		                	<form:input path="descricao" cssClass="form-control" />
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="nome">Abreviação</form:label>
		                <div class="col-sm-10">
		                	<form:input path="nome" cssClass="form-control"/>
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label  class="col-sm-2 control-label" path="objetivo">Objetivo</form:label>
		                <div class="col-sm-10">
		                	<form:textarea path="objetivo" cssClass="form-control" />
		                </div>
		            </div>
		            <div class="form-group">
		                <form:label   class="col-sm-2 control-label"  path="instituto.descricao">Instituto</form:label>
		                <div class="col-sm-10">
			                <form:hidden path="instituto.id" />
							<form:input path="instituto.descricao" cssClass="form-control" />
							
						</div>
					</div>
		            <div class="form-group">
		            	<div class="col-sm-offset-2 col-sm-10">
		            		<div class="checkbox">
	                			<form:checkbox path="rodizio" /> Tem sala no Rodizio
	                		</div>
	                	</div>
	                </div>
	                <div class="form-group">
		                <form:label class="col-sm-2 control-label" path="dirigenteNacional.id">Dirigente Nacional:</form:label>
		                <div class="col-sm-6">
							<form:hidden path="dirigenteNacional.id"/>
			                <form:input path="dirigenteNacional.nome" placeholder="Selecione o trabalhador"  class="form-control"/>	
						</div>
					</div>
	                <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <input type="submit" value="Incluir Comissão" class="btn btn-primary"/>
					    </div>
					  </div>
	                
	            </form:form>
	    </div>
	    <script type="text/javascript" src="/js/custom/autocompletepessoa.js"></script>
		<script type="text/javascript">
		$(function(){
			
			$("#instituto\\.descricao").autocomplete({
				source: function (request, response) {
					$.ajax({
			            dataType: "json",
			            type : 'Get',
			            url: '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/instituto/list',
			           	data: {	            
							maxRows: 6,
							query: request.term
						},
						success: function( data ) {
							response( $.map( data, function( item ) {
								return {
									label: item.descricao,
									value: item.id
								}
							}));
						}
					});
				},
				minLength: 3,
				open: function() {
					$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
			    close: function() {
					$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
				},
				select: function(event, ui) {
			        event.preventDefault();
			        $("#instituto\\.descricao").val(ui.item.label);
			        $("#instituto\\.id").val(ui.item.value);
			    },
			    focus: function(event, ui) {
			        event.preventDefault();
			        $("#instituto\\.descricao").val(ui.item.label);
			    }
			}); 

			
			var baseUrl = "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
			completePessoa($('#dirigenteNacional\\.nome'), $("#dirigenteNacional\\.id"), baseUrl );
		});

        </script>